package cn.xihan.qdds

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.highcapable.yukihookapi.hook.factory.current
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.android.LinearLayoutClass
import com.highcapable.yukihookapi.hook.type.java.CharSequenceClass
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.UnitType

/**
 * 自动化选项
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 * @param [configurations] 配置
 * @suppress Generate Documentation
 */
fun PackageParam.automatizationOption(
    versionCode: Int,
    configurations: List<SelectedModel>,
) {
    configurations.filter { it.selected }.takeIf { it.isNotEmpty() }?.forEach { selected ->
        when (selected.title) {
            "自动签到" -> autoSignIn(versionCode)
            "自动领取阅读积分" -> receiveReadingCreditsAutomatically(versionCode)
            "自动领取章末红包" -> receivedReadingPageEndHongBaoAutomatically(versionCode)
//            "自动跳过启动页" -> autoSkipSplash(versionCode)
        }
    }
}


/**
 * 自动签到
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.autoSignIn(
    versionCode: Int
) {
    when (versionCode) {
        in 1196..1299 -> {
            /*
            DexKitBridge.create(appInfo.sourceDir)?.use { bridge ->
                bridge.findClass {
                    searchPackages = listOf("com.qidian.QDReader.ui.view.bookshelfview")
                    matcher {
                        className =
                            "com.qidian.QDReader.ui.view.bookshelfview.CheckInReadingTimeViewNew"
                        methods {
                            add {
                                modifiers = Modifier.PROTECTED
                                returnType = "void"
                                paramCount = 2
                            }
                        }
                        usingStrings = listOf("BookShelfCheckIn", "btnCheckIn", "newCheckin")
                    }
                }.firstNotNullOfOrNull { classData ->
                    classData.findMethod {
                        matcher {
                            modifiers = Modifier.PROTECTED
                            returnType = "void"
                            paramTypes = listOf(
                                "com.qidian.QDReader.repository.entity.checkin.CheckInData",
                                "boolean"
                            )
                        }
                    }.firstNotNullOfOrNull { methodData ->
                            methodData.className.toClass().method {
                                name = methodData.methodName
                                paramCount(2)
                                returnType = UnitType
                            }.hook().after {
                                val qDUIButtons =
                                    instance.getViews("com.qd.ui.component.widget.QDUIButton".toClass())
                                qDUIButtons.filter { button ->
                                    button.getViews<TextView>()
                                        .any { textView -> textView.text == "签到" }
                                }.filterIsInstance<View>().performClick()
                            }
                        }
                }
            }

             */

            "com.qidian.QDReader.ui.modules.bookshelf.view.BookShelfCheckInView".toClass().method {
                name = "updateCheckIn"
                paramCount(2)
                returnType = UnitType
            }.hook().after {
                val binding = instance.getParam<Any>("binding") ?: return@after
                val qDUIButtons =
                    binding.getViews("com.qd.ui.component.widget.QDUIButton".toClass())
                qDUIButtons.filter { button ->
                    button.getViews<TextView>().any { textView -> textView.text == "签到" }
                }.filterIsInstance<View>().randomDelayPerformClick()
            }
        }

        else -> "自动签到".printlnNotSupportVersion(versionCode)
    }

}

/**
 * 自动领取阅读积分
 * 需打开阅读时长页面，如果有积分则自动领取
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.receiveReadingCreditsAutomatically(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            /**
             * 自动领取今日阅读时长积分
             */
            "com.qidian.QDReader.ui.view.readtime.ReadTimeTodayCardView".toClass().method {
                name = "addBubbleView"
                paramCount(4)
                returnType = UnitType
            }.hook().after {
                val bubbleViewMap = instance.getParam<HashMap<*, *>>("bubbleViewMap")
                bubbleViewMap?.values?.forEach { view ->
                    view.safeCast<LinearLayout>()?.postRandomDelay { performClick() }
                }
            }


            /**
             * 自动领取每周阅读时长宝箱
             */
            "com.qidian.QDReader.ui.view.readtime.ReadTimeWeekCardView".toClass().method {
                name = "bindBox"
                paramCount(2)
                returnType = IntType
            }.hook().after {
                args[1].safeCast<List<*>>() ?: return@after
                val viewMap = instance.getParam<Map<*, *>>("_\$_findViewCache") ?: return@after
                val frameLayouts = viewMap.values.filterIsInstance<FrameLayout>()
                val linearLayout = frameLayouts.flatMap { it.findViewsByType(LinearLayoutClass) }
                    .filterIsInstance<LinearLayout>()
                linearLayout.forEach {
                    it.postRandomDelay { performClick() }
                }
            }

            /**
             * 自动领取每日任务奖励
             */
            "com.qidian.QDReader.ui.activity.ReadTimeMainPageActivity".toClass().method {
                name = "changeSubmitStatus"
                paramCount(2)
                returnType = UnitType
            }.hook().after {
                val button = args[0].safeCast<LinearLayout>() ?: return@after
                button.current {
                    val text = method {
                        name = "getText"
                        emptyParam()
                        returnType = CharSequenceClass
                    }.call()

                    if ("领取" == text) {
                        button.postRandomDelay { performClick() }
                    }
                }
            }

            /**
             * 自动领取(开始)阅读PK场
             */
            "com.qidian.QDReader.ui.view.readtime.ReadTimePKCardView".toClass().method {
                name = "bindData"
                paramCount(1)
                returnType = UnitType
            }.hook().after {
                val button = instanceClass?.method {
                    name = "getQdButtonBottom"
                    emptyParam()
                    returnType = "com.qd.ui.component.widget.QDUIButton".toClass()
                }?.get(instance)?.call().safeCast<LinearLayout>() ?: return@after

                button.current {
                    val text = method {
                        name = "getText"
                        emptyParam()
                        returnType = CharSequenceClass
                    }.call()
                    val list = listOf(
                        "领取奖励", "开启新一周PK", "匹配对手"
                    )
                    if (text in list) {
                        button.postRandomDelay { performClick() }
                    }
                }
            }

        }

        else -> "自动领取阅读积分".printlnNotSupportVersion(versionCode)
    }

}

/**
 * # 自动领取章末红包
 * * 需在阅读页面打开章末红包，如果有红包则自动领取
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.receivedReadingPageEndHongBaoAutomatically(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.modules.interact.InteractHBContainerView".toClass().method {
                name = "showContent"
                emptyParam()
                returnType = UnitType
            }.hook().after {
                val data = instance.getParam<Any>("mHongBaoData")?.getParam<Any>("hongBaoData")
                    ?.getParam<MutableList<*>>("data") ?: return@after
                if (data.isEmpty()) return@after
                instanceClass?.method {
                    name = "showRewardVideo"
                    emptyParam()
                    returnType = UnitType
                }?.get(instance)?.call()
            }
        }

        else -> "自动领取章末红包".printlnNotSupportVersion(versionCode)
    }
}

/**
 *  自动跳过启动页
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.autoSkipSplash(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.activity.SplashActivity".toClass().method {
                name = "onCreate"
                param(BundleClass)
                returnType = UnitType
            }.hook().after {
                instance.current {
                    method {
                        name = "go2Main"
                        paramCount(1)
                        returnType = UnitType
                    }.call(false)

                    method {
                        name = "finish"
                        emptyParam()
                        returnType = UnitType
                    }.call()
                }
            }
        }

        else -> "自动跳过启动页".printlnNotSupportVersion(versionCode)
    }
}

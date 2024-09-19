package cn.xihan.qdds.hook

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import cn.xihan.qdds.util.SelectedModel
import cn.xihan.qdds.util.findViewsByType
import cn.xihan.qdds.util.getParam
import cn.xihan.qdds.util.getViews
import cn.xihan.qdds.util.postRandomDelay
import cn.xihan.qdds.util.printlnNotSupportVersion
import cn.xihan.qdds.util.randomDelayPerformClick
import cn.xihan.qdds.util.safeCast
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
 * @since 7.9.354-1296 ~ 1499
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
        }
    }
}


/**
 * 自动签到
 * @since 7.9.354-1296 ~ 1499
 * @param [versionCode] 版本代码
 */
fun PackageParam.autoSignIn(
    versionCode: Int
) {
    when (versionCode) {
        in 1296..1499 -> {

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
 * @since 7.9.354-1296 ~ 1499
 * @param [versionCode] 版本代码
 */
fun PackageParam.receiveReadingCreditsAutomatically(versionCode: Int) {
    when (versionCode) {
        in 1296..1499 -> {
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

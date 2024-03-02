package cn.xihan.qdds

import android.widget.LinearLayout
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.UnitType
import org.luckypray.dexkit.DexKitBridge
import java.lang.reflect.Modifier

/**
 * 广告相关功能
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 * @param [configurations] 配置
 * @suppress Generate Documentation
 */
fun PackageParam.advOption(
    versionCode: Int,
    configurations: List<SelectedModel>,
    bridge: DexKitBridge
) {
    configurations.filter { it.selected }.takeIf { it.isNotEmpty() }?.forEach { selected ->
        when (selected.title) {
            "闪屏广告" -> disableSplashAd(versionCode, bridge)
            "GDT广告" -> disableGDTAD(versionCode, bridge)
            "主页-每日阅读广告" -> disableDailyReadAd(versionCode)
            "主页-书架活动弹框" -> disableBookshelfActivityPopup(versionCode)
            "主页-书架顶部广告" ->   disableBookshelfTopAd(versionCode)
            "主页-书架浮窗活动" -> disableBookshelfFloatWindow(versionCode)
            "主页-书架底部导航栏广告" -> disableBottomNavigationCenterAd(versionCode)
            "我-中间广告" -> disableAccountCenterAd(versionCode)
            "阅读页-浮窗广告" -> disableReaderPageFloatAd(versionCode)
            "阅读页-打赏小剧场" -> disableReadPageRewardTheater(versionCode)
            "阅读页-章末底部月票打赏红包" -> disableReaderPageBottom(versionCode)
            "阅读页-最后一页-弹框广告" -> disableReadPageNewestPageWindowBannerAd(versionCode)
        }
    }

    disableReadPageChapterEnd(
        versionCode,
        disableAll = configurations.isSelectedByTitle("阅读页-章末一刀切"),
        disableBookRecommend = configurations.isSelectedByTitle("阅读页-章末新人推书"),
        disableBookComment = configurations.isSelectedByTitle("阅读页-章末本章说"),
        disableChapterEndWelfare = configurations.isSelectedByTitle("阅读页-章末福利"),
        disableChapterEndRewardAd = configurations.isSelectedByTitle("阅读页-章末广告"),
        disableVoteTicketSpecialLine = configurations.isSelectedByTitle("阅读页-章末求票")
    )
}

/**
 * 禁用每日导读广告
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本号
 */
fun PackageParam.disableDailyReadAd(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.activity.DailyReadingActivity".toClass().method {
                name = "getADInfo"
                emptyParam()
                returnType = UnitType
            }.hook().intercept()
        }

        else -> "禁用每日导读广告".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用书架活动弹出窗口
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.disableBookshelfActivityPopup(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.activity.MainGroupActivity".toClass().method {
                name = "doBKTAction"
                paramCount(1)
                returnType = UnitType
            }.hook().intercept()
        }

        else -> "禁用书架活动弹框".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用书架顶部广告
 * @since 7.9.306-1086 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.disableBookshelfTopAd(versionCode: Int) {
    when (versionCode) {
        in 1086..1299 -> {
            intercept(
                className = "com.qidian.QDReader.ui.modules.bookshelf.BookShelfOperationManager",
                methodName = "getBookShelfOperationRes"
            )
        }

        else -> "禁用书架顶部广告".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用底部导航中心广告
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.disableBottomNavigationCenterAd(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.activity.MainGroupActivity".toClass().method {
                name = "checkAdTab"
                emptyParam()
                returnType = UnitType
            }.hook().intercept()
        }

        else -> "禁用底部导航栏中心广告".printlnNotSupportVersion(versionCode)
    }
}

/**
 *  禁用书架右下角浮窗
 * @param [versionCode] 版本代码
 */
fun PackageParam.disableBookshelfFloatWindow(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.modules.bookshelf.QDBookShelfRebornFragment".toClass().method {
                name = "updateFloatingAd"
                emptyParam()
                returnType = UnitType
            }.hook().intercept()
        }

        else -> "禁用书架右下角浮窗".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用我-中心广告
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.disableAccountCenterAd(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.fragment.main_group.QDUserAccountRebornFragment".toClass()
                .method {
                    name = "loadADData"
                    paramCount(1)
                }.hook().intercept()
        }

        else -> "禁用我-中心广告".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用阅读页面浮动广告
 * @since 7.9.334-1196
 * @param [versionCode] 版本代码
 */
fun PackageParam.disableReaderPageFloatAd(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.readerengine.view.QDSuperEngineView".toClass().method {
                name = "setReadMenuData"
                paramCount(1)
                returnType = UnitType
            }.hook().intercept()

            "com.qidian.QDReader.ui.activity.QDReaderActivity".toClass().apply {
                method {
                    param(
                        "com.qidian.QDReader.ui.activity.QDReaderActivity".toClass(),
                        "com.qidian.QDReader.repository.entity.ReadMenuData".toClass()
                    )
                    returnType = UnitType
                }.hook().intercept()

                method {
                    name = "getReadMenuData"
                    emptyParam()
                    returnType = UnitType
                }.hook().intercept()
            }
        }

        else -> "禁用阅读页面浮动广告".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用阅读页面底部月票打赏红包
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.disableReaderPageBottom(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.readerengine.view.QDSuperEngineView".toClass().method {
                name = "initInteractionBarView"
                returnType = UnitType
            }.hook().after {
                val mInteractionBarView =
                    instance.getView<LinearLayout>("mInteractionBarView")
                mInteractionBarView?.setVisibilityIfNotEqual()
            }
        }
    }
}

/**
 * 禁用阅读页面打赏剧场
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.disableReadPageRewardTheater(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.activity.chapter.list.NewParagraphCommentListActivity".toClass()
                .method {
                    name = "getParagraphTip"
                    emptyParam()
                    returnType = UnitType
                }.hook().intercept()
        }

        else -> "移除阅读页-打赏小剧场".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用阅读页面最新页面窗口横幅广告
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.disableReadPageNewestPageWindowBannerAd(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.bll.manager.QDBKTManager".toClass().method {
                paramCount(5)
                returnType = UnitType
            }.hook().intercept()
        }
    }
}

/**
 * 禁用阅读页-章末相关广告
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 * @param [disableAll] 一刀切
 * @param [disableBookRecommend] 禁用推荐书籍
 * @param [disableBookComment] 禁用本章说
 * @param [disableChapterEndWelfare] 禁用章末福利
 * @param [disableChapterEndRewardAd] 禁用章末广告
 * @param [disableVoteTicketSpecialLine] 禁用章末求票
 * @suppress Generate Documentation
 */
fun PackageParam.disableReadPageChapterEnd(
    versionCode: Int,
    disableAll: Boolean = false,
    disableBookRecommend: Boolean = false,
    disableBookComment: Boolean = false,
    disableChapterEndWelfare: Boolean = false,
    disableChapterEndRewardAd: Boolean = false,
    disableVoteTicketSpecialLine: Boolean = false
) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.readerengine.manager.ChapterProvider".toClass().apply {

                if (disableAll) {
                    /**
                     * 一刀切 insertChapterEndSpecialLine
                     */
                    method {
                        name = "insertChapterEndSpecialLine"
                        returnType = UnitType
                    }.hook().intercept()
                }
                if (disableBookRecommend) {
                    /**
                     * 新人推书 insertBookRecommend
                     */
                    method {
                        name = "insertBookRecommend"
                        returnType = UnitType
                    }.hook().intercept()
                }
                if (disableBookComment) {
                    /**
                     * 本章说 insertChapterComment
                     */
                    method {
                        name = "insertChapterComment"
                        returnType = UnitType
                    }.hook().intercept()
                }
                if (disableChapterEndWelfare) {
                    /**
                     * 章末福利 insertChapterEndWelfare
                     */
                    method {
                        name = "insertChapterEndWelfare"
                        returnType = UnitType
                    }.hook().intercept()
                }
                if (disableChapterEndRewardAd) {
                    /**
                     * 章末广告 insertRewardAd
                     */
                    method {
                        name = "insertRewardAd"
                        returnType = UnitType
                    }.hook().intercept()
                }
                if (disableVoteTicketSpecialLine) {
                    /**
                     * 章末求票 insertVoteTicketSpecialLine
                     */
                    method {
                        name = "insertVoteTicketSpecialLine"
                        returnType = UnitType
                    }.hook().intercept()
                }

            }
        }

        else -> "移除阅读页-章末相关".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用闪屏广告
 * @since 7.9.334-1196
 * @param [versionCode] 版本代码
 */
fun PackageParam.disableSplashAd(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1196..1299 -> {

            bridge.apply {
                findClass {
                    searchPackages = listOf("com.qidian.QDReader.bll.splash")
                    matcher {
                        usingStrings = listOf("localLabels=")
                    }
                }.firstNotNullOfOrNull { classData ->
                    classData.findMethod {
                        matcher {
                            modifiers = Modifier.PUBLIC
                            paramTypes = listOf("android.content.Context")
                            returnType = "void"
                            usingStrings = listOf("localLabels=")
                        }
                    }.firstNotNullOfOrNull { methodData ->
                        methodData.className.toClass().method {
                            name = methodData.methodName
                            paramCount(1)
                            returnType = UnitType
                        }.hook().intercept()
                    }
                }

                findClass {
                    excludePackages = listOf("com")
                    matcher {
                        usingStrings = listOf(
                            "SettingSplashEnableGDT",
                            "SettingSplashGDTShowMaxCountOnDay",
                            "SettingSplashGDTShowBeginTime",
                            "SettingSplashGDTShowEndTime"
                        )
                    }
                }.firstNotNullOfOrNull { classData ->
                    classData.methods.filter { it.isMethod }.forEach { methodData ->
                        if ("boolean" == methodData.returnTypeName) {
                            returnFalse(methodData.className, methodData.methodName)
                        } else if ("void" == methodData.returnTypeName) {
                            intercept(
                                methodData.className,
                                methodData.methodName,
                                methodData.paramTypeNames.size
                            )
                        }
                    }
                }
            }
        }

        else -> "禁用闪屏广告活动".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用GDT广告
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode]
 */
fun PackageParam.disableGDTAD(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1196..1299 -> {

            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.component.abtest")
                matcher {
                    methods {
                        add {
                            modifiers = Modifier.PUBLIC
                            returnType = "boolean"
                        }
                    }
                    usingStrings = listOf("videolast-ad", "video-redpopup-ad")
                }
            }.firstNotNullOfOrNull { classData ->
                classData.apply {
                    findMethod {
                        matcher {
                            returnType = "boolean"
                            usingStrings = listOf("videolast-ad")
                        }
                    }.firstNotNullOfOrNull {
                        returnFalse(it.className, it.methodName)
                    }

                    findMethod {
                        matcher {
                            returnType = "boolean"
                            usingStrings = listOf("video-redpopup-ad")
                        }
                    }.firstNotNullOfOrNull {
                        returnFalse(it.className, it.methodName)
                    }
                }
            }

            intercept(
                className = "com.qq.e.tg.tangram.TangramAdManager",
                methodName = "init",
                paramCount = 3
            )

            "com.qq.e.comm.managers.GDTADManager".toClass().apply {
                method {
                    name = "initPlugin"
                    emptyParam()
                    returnType = UnitType
                }.hook().intercept()

                method {
                    name = "initWith"
                    paramCount(2)
                }.hook().replaceAny { true }

            }
        }

        else -> "禁用GDT广告".printlnNotSupportVersion(versionCode)
    }

}

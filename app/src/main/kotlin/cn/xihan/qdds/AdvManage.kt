package cn.xihan.qdds

import android.view.View
import android.widget.LinearLayout
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.ContextClass
import com.highcapable.yukihookapi.hook.type.android.ViewClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.ListClass
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2022/8/28 16:15
 * @介绍 :
 */
/**
 * 广告相关配置
 */
fun PackageParam.advOption(versionCode: Int, configurations: List<OptionEntity.SelectedModel>) {
    configurations.filter { it.selected }.takeIf { it.isNotEmpty() }?.forEach { selected ->
        when (selected.title) {
            "GDT(TX)广告" -> disableAd(versionCode)
            "检查更新" -> disableUpdate(versionCode)
            "主页-每日阅读广告" -> disableDailyReadAd(versionCode)
            "主页-书架活动弹框" -> disableBookshelfActivityPopup(versionCode)
            "主页-书架浮窗活动" -> disableBookshelfFloatWindow(versionCode)
            "主页-书架底部导航栏广告" -> disableBottomNavigationCenterAd(versionCode)
            "我-中间广告"-> disableAccountCenterAd(versionCode)
            "阅读页-浮窗广告" -> disableReadPageFloatAd(versionCode)
            "阅读页-打赏小剧场" -> disableReadPageRewardTheater(versionCode)
            "阅读页-章末底部月票打赏红包" -> hideReadPageBottom(versionCode)
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
 */
fun PackageParam.disableDailyReadAd(versionCode: Int) {
    when (versionCode) {
        in 812..999 -> {
            findClass("com.qidian.QDReader.ui.activity.DailyReadingActivity").hook {
                injectMember {
                    method {
                        name = "getADInfo"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "禁用每日导读广告".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用书架活动弹框
 * 上级调用:com.qidian.QDReader.component.config.QDAppConfigHelper$Companion.getBKTData
 * activityPopupBean.getData()
 */
fun PackageParam.disableBookshelfActivityPopup(versionCode: Int) {
    when (versionCode) {
        in 804..900 -> {
            findClass("com.qidian.QDReader.repository.entity.config.ActivityPopupBean").hook {
                injectMember {
                    method {
                        name = "getData"
                        emptyParam()
                        returnType = ListClass
                    }
                    afterHook {
                        result = result.safeCast<MutableList<*>>()?.also { it.clear() }
                    }
                }
            }
        }

        in 906..999 -> {
            findClass("com.qidian.QDReader.ui.activity.MainGroupActivity").hook {
                injectMember {
                    method {
                        name = "doBKTAction"
                        paramCount(1)
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "移除书架活动弹框".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用书架右下角浮窗
 */
fun PackageParam.disableBookshelfFloatWindow(versionCode: Int) {
    when (versionCode) {
        in 758..768 -> {
            findClass("com.qidian.QDReader.ui.fragment.QDBookShelfPagerFragment").hook {
                injectMember {
                    method {
                        name = "loadBookShelfAd"
                    }
                    intercept()
                }

                injectMember {
                    method {
                        name = "onViewInject"
                        param(View::class.java)
                    }
                    afterHook {
                        instance.getViews(
                            *arrayOf(
                                "imgAdIconClose",
                                "layoutImgAdIcon",
                                "imgBookShelfActivityIcon"
                            ).toPairs()
                        ).hideViews()
                    }
                }
            }
        }

        in 772..812 -> {
            findClass("com.qidian.QDReader.ui.fragment.QDBookShelfPagerFragment").hook {
                injectMember {
                    method {
                        name = "loadBookShelfAd"
                    }
                    intercept()
                }

                injectMember {
                    method {
                        name = "showBookShelfHoverAd"
                    }
                    intercept()
                }

                injectMember {
                    method {
                        name = "onViewInject"
                        param(View::class.java)
                    }
                    afterHook {
                        instance.getView<LinearLayout>(
                            "layoutImgAdIcon"
                        )?.setVisibilityIfNotEqual()
                    }
                }

            }
        }

        in 827..999 -> {

            findClass("com.qidian.QDReader.ui.modules.bookshelf.QDBookShelfRebornFragment").hook {
                injectMember {
                    method {
                        name = "updateFloatingAd"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }
            }

            findClass("com.qidian.QDReader.ui.fragment.QDBookShelfPagerFragment").hook {
                injectMember {
                    method {
                        name = "loadBookShelfAd"
                    }
                    intercept()
                }

                injectMember {
                    method {
                        name = "showBookShelfHoverAd"
                    }
                    intercept()
                }

                injectMember {
                    method {
                        name = "onViewInject"
                        param(ViewClass)
                    }
                    afterHook {
                        instance.getView<LinearLayout>(
                            "layoutImgAdIcon"
                        )?.setVisibilityIfNotEqual()
                    }
                }

            }
        }

        else -> "移除书架右下角浮窗".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 移除底部导航栏中心广告
 * 上级调用位置:com.qidian.QDReader.ui.activity.MainGroupActivity.checkAdTab()
 */
fun PackageParam.disableBottomNavigationCenterAd(versionCode: Int) {
    when (versionCode) {
        in 758..999 -> {
            findClass("com.qidian.QDReader.ui.activity.MainGroupActivity").hook {
                injectMember {
                    method {
                        name = "checkAdTab"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "移除底部导航栏中心广告".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 移除我-中心广告
 */
fun PackageParam.disableAccountCenterAd(versionCode: Int) {
    when (versionCode) {
        in 758..896 -> {
            findClass("com.qidian.QDReader.ui.fragment.QDAccountFragment").hook {
                injectMember {
                    method {
                        name = "loadADData"
                        returnType = UnitType
                    }
                    intercept()
                }
            }

            findClass("com.qidian.QDReader.ui.fragment.QDUserAccountFragment").hook {
                injectMember {
                    method {
                        name = "loadADData"
                        returnType = UnitType
                    }
                    intercept()
                }
            }

            if (versionCode >= 868) {
                findClass("com.qidian.QDReader.ui.fragment.main_group.QDUserAccountRebornFragment").hook {
                    injectMember {
                        method {
                            name = "loadADData"
                            paramCount(1)
                        }
                        intercept()
                    }

                }
            }
        }

        in 896..999 -> {
            findClass("com.qidian.QDReader.ui.fragment.main_group.QDUserAccountRebornFragment").hook {
                injectMember {
                    method {
                        name = "loadADData"
                        paramCount(1)
                    }
                    intercept()
                }

            }
        }

        else -> "移除我-中心广告".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用阅读页-浮窗广告
 */
fun PackageParam.disableReadPageFloatAd(versionCode: Int) {
    when (versionCode) {
        in 958..999 -> {
            findClass("com.qidian.QDReader.readerengine.view.QDSuperEngineView").hook {
                injectMember {
                    method {
                        name = "setReadMenuData"
                        paramCount(1)
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }
    }

    val hookMethodName = when (versionCode) {
        812 -> "O0"
        in 827..834 -> "K"
        in 842..843 -> "M"
        in 850..868 -> "I"
        872 -> "G"
        878 -> "j1"
        in 884..890 -> "g1"
        in 896..906 -> "c1"
        in 916..924 -> "h1"
        in 932..944 -> "B0"
        in 950..958 -> "t1"
        in 970..994 -> "s1"
        else -> null
    }
    hookMethodName?.let {
        findClass("com.qidian.QDReader.ui.activity.QDReaderActivity").hook {
            injectMember {
                method {
                    name = it
                    param(
                        "com.qidian.QDReader.ui.activity.QDReaderActivity".toClass(),
                        "com.qidian.QDReader.repository.entity.ReadMenuData".toClass()
                    )
                }
                intercept()
            }

            injectMember {
                method {
                    name = "getReadMenuData"
                    emptyParam()
                    returnType = UnitType
                }
                intercept()
            }
        }
    } ?: "移除阅读页-浮窗广告".printlnNotSupportVersion(versionCode)
}

/**
 * 禁用阅读页-打赏小剧场
 */
fun PackageParam.disableReadPageRewardTheater(versionCode: Int) {
    when (versionCode) {
        in 812..999 -> {
            findClass("com.qidian.QDReader.ui.activity.chapter.list.NewParagraphCommentListActivity").hook {
                injectMember {
                    method {
                        name = "getParagraphTip"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "移除阅读页-打赏小剧场".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用阅读页-最新页面弹框广告
 * positionMask
 */
fun PackageParam.disableReadPageNewestPageWindowBannerAd(versionCode: Int) {
    when (versionCode) {
        in 896..994 -> {
            findClass("com.qidian.QDReader.bll.manager.QDBKTManager").hook {
                injectMember {
                    method {
                        name = "c"
                        paramCount(5)
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }
    }
}

/**
 * 禁用阅读页-章末相关
 * @param versionCode 版本号
 * @param disableAll 一刀切
 * @param disableBookRecommend 禁用推荐书籍
 * @param disableBookComment 禁用本章说
 * @param disableChapterEndWelfare 禁用章末福利
 * @param disableChapterEndRewardAd 禁用章末广告
 * @param disableVoteTicketSpecialLine 禁用章末求票
 */
fun PackageParam.disableReadPageChapterEnd(
    versionCode: Int,
    disableAll: Boolean = false,
    disableBookRecommend: Boolean = false,
    disableBookComment: Boolean = false,
    disableChapterEndWelfare: Boolean = false,
    disableChapterEndRewardAd: Boolean = false,
    disableVoteTicketSpecialLine: Boolean = false,
) {
    when (versionCode) {
        in 812..999 -> {
            findClass("com.qidian.QDReader.readerengine.manager.ChapterProvider").hook {
                if (disableAll) {
                    /**
                     * 一刀切 insertChapterEndSpecialLine
                     */
                    injectMember {
                        method {
                            name = "insertChapterEndSpecialLine"
                            returnType = UnitType
                        }
                        intercept()
                    }
                }
                if (disableBookRecommend) {
                    /**
                     * 新人推书 insertBookRecommend
                     */
                    injectMember {
                        method {
                            name = "insertBookRecommend"
                            returnType = UnitType
                        }
                        intercept()
                    }
                }
                if (disableBookComment) {
                    /**
                     * 本章说 insertChapterComment
                     */
                    injectMember {
                        method {
                            name = "insertChapterComment"
                            returnType = UnitType
                        }
                        intercept()
                    }
                }
                if (disableChapterEndWelfare) {
                    /**
                     * 章末福利 insertChapterEndWelfare
                     */
                    injectMember {
                        method {
                            name = "insertChapterEndWelfare"
                            returnType = UnitType
                        }
                        intercept()
                    }
                }
                if (disableChapterEndRewardAd) {
                    /**
                     * 章末广告 insertRewardAd
                     */
                    injectMember {
                        method {
                            name = "insertRewardAd"
                            returnType = UnitType
                        }
                        intercept()
                    }
                }
                if (disableVoteTicketSpecialLine) {
                    /**
                     * 章末求票 insertVoteTicketSpecialLine
                     */
                    injectMember {
                        method {
                            name = "insertVoteTicketSpecialLine"
                            returnType = UnitType
                        }
                        intercept()
                    }
                }
            }
        }

        else -> "移除阅读页-章末相关".printlnNotSupportVersion(versionCode)
    }
}

/**
 * Hook 禁用广告
 */
fun PackageParam.disableAd(versionCode: Int) {
    when (versionCode) {
        in 758..924 -> {
            findClass("com.qq.e.comm.constants.CustomPkgConstants").hook {
                injectMember {
                    method {
                        name = "getAssetPluginName"
                        emptyParam()
                        returnType = StringClass
                    }
                    replaceTo("")
                }
            }

            findClass("com.qq.e.comm.b").hook {
                injectMember {
                    method {
                        name = "a"
                        param(ContextClass)
                        returnType = BooleanType
                    }
                    replaceToFalse()
                }
            }

            /*
            findClass("com.qidian.QDReader.component.api.a").hook {
                injectMember {
                    method {
                        name = "b"
                        returnType = UnitType
                    }
                    intercept()
                }
            }

             */

            findClass("com.qidian.QDReader.bll.helper.QDInternalAdHelper").hook {
                injectMember {
                    method {
                        name = "analyzeAdInfo"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        in 932..999 -> {
            findClass("com.qq.e.comm.managers.GDTADManager").hook {
                injectMember {
                    method {
                        name = "initPlugin"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }

                injectMember {
                    method {
                        name = "initWith"
                        paramCount(2)
                    }
                    replaceAny {
                        true
                    }
                }
            }

            findClass("com.qidian.QDReader.bll.helper.QDInternalAdHelper").hook {
                injectMember {
                    method {
                        name = "analyzeAdInfo"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "禁用广告".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 禁用检查更新
 * 上级调用:com.qidian.QDReader.ui.activity.MainGroupActivity.onCreate(android.os.Bundle)
 */
fun PackageParam.disableUpdate(versionCode: Int) {
    /**
     * 也可全局搜索 "UpgradeCommon"、"checkUpdate:"
     */
    val methodMap = mapOf(
        "needHookClass" to when (versionCode) {
            in 758..788 -> "com.qidian.QDReader.util.z4"
            in 792..796 -> "com.qidian.QDReader.util.i5"
            in 800..834 -> "com.qidian.QDReader.util.l5"
            in 842..878 -> "com.qidian.QDReader.util.m5"
            884 -> "com.qidian.QDReader.util.k5"
            in 890..900 -> "com.qidian.QDReader.util.l5"
            in 906..970 -> "com.qidian.QDReader.util.m5"
            in 980..994 -> "com.qidian.QDReader.util.k5"
            else -> null
        },
        "needHookMethod" to when (versionCode) {
            in 758..878 -> "b"
            in 884..994 -> "judian"
            else -> null
        },
        "needHookMethod2" to when (versionCode) {
            in 758..878 -> "a"
            in 884..994 -> "search"
            else -> null
        }
    )

    methodMap["needHookClass"]?.hook {
        injectMember {
            method {
                name = methodMap["needHookMethod"]!!
                returnType = UnitType
            }
            intercept()
        }

        injectMember {
            method {
                name = methodMap["needHookMethod2"]!!
                returnType = UnitType
            }
            intercept()
        }

    }


    /*
    /**
     * 上级调用:com.qidian.QDReader.ui.activity.MainGroupActivity.checkUpdate()
     */
    val neddHookClass2 = when (versionCode) {
        in 758..808 -> "w4.h"
        812 -> "t4.h"
        827 -> "v4.h"
        in 834..843 -> "t4.h"
        in 850..868 -> "u4.h"
        872 -> "s4.h"
        else -> null
    }
    neddHookClass2?.hook {
        injectMember {
            method {
                name = "l"
                returnType = UnitType
            }
            intercept()
        }
    }

     */
    when (versionCode) {
        in 758..999 -> {

            findClass("com.qidian.QDReader.ui.activity.MainGroupActivity").hook {
                injectMember {
                    method {
                        name = "checkUpdate"
                        returnType = UnitType
                    }
                    intercept()
                }
            }

            findClass("com.qidian.QDReader.ui.fragment.QDFeedListPagerFragment").hook {
                injectMember {
                    method {
                        name = "checkAppUpdate"
                        returnType = UnitType
                    }
                    intercept()
                }
            }

            findClass("com.tencent.upgrade.core.UpdateCheckProcessor").hook {
                injectMember {
                    method {
                        name = "checkAppUpgrade"
                        returnType = UnitType
                    }
                    intercept()
                }
            }

            findClass("com.tencent.upgrade.core.UpgradeManager").hook {
                injectMember {
                    method {
                        name = "init"
                        returnType = UnitType
                    }
                    intercept()
                }
            }

            findClass("com.qidian.QDReader.ui.activity.AboutActivity").hook {
                injectMember {
                    method {
                        name = "updateVersion"
                        returnType = UnitType
                    }
                    intercept()
                }

                injectMember {
                    method {
                        name = "getVersionNew"
                        returnType = UnitType
                    }
                    intercept()
                }


            }
        }

        else -> "禁用检查更新".printlnNotSupportVersion(versionCode)
    }

    /**
     * SettingUpdateVersionNotifyTime
     */
    val needHookClass2 = when (versionCode) {
        970 -> "r4.d"
        980 -> "s4.f"
        994 -> "s4.d"
        else -> null
    }
    needHookClass2?.hook {
        injectMember {
            method {
                name = "run"
                emptyParam()
                returnType = UnitType
            }
            intercept()
        }
    } ?: "禁用检查更新弹框".printlnNotSupportVersion(versionCode)

}
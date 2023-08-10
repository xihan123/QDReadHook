package cn.xihan.qdds

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.ContextClass
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
fun PackageParam.advOption(versionCode: Int, optionValueSet: Set<Int>) {
    optionValueSet.forEach {
        when (it) {
            0 -> disableAd(versionCode)
            1 -> disableUpdate(versionCode)
            2 -> disableDailyReadAd(versionCode)
            3 -> disableBookshelfActivityPopup(versionCode)
            4 -> disableBookshelfFloatWindow(versionCode)
            5 -> disableBottomNavigationCenterAd(versionCode)
            6 -> disableAccountCenterAd(versionCode)
            7 -> disableReadPageFloatAd(versionCode)
            8 -> disableReadPageRewardTheater(versionCode)
            15 -> hideReadPageBottom(versionCode)
            17 -> disableReadPageNewestPageWindowBannerAd(versionCode)
        }
    }
    disableReadPageChapterEnd(
        versionCode,
        disableAll = HookEntry.isEnableAdvOption(9),
        disableBookRecommend = HookEntry.isEnableAdvOption(10),
        disableBookComment = HookEntry.isEnableAdvOption(11),
        disableChapterEndWelfare = HookEntry.isEnableAdvOption(12),
        disableChapterEndRewardAd = HookEntry.isEnableAdvOption(13),
        disableVoteTicketSpecialLine = HookEntry.isEnableAdvOption(14)
    )
}

/**
 * 禁用每日导读广告
 */
fun PackageParam.disableDailyReadAd(versionCode: Int) {
    when (versionCode) {
        in 812..980 -> {
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
                        val list = result as? MutableList<*>
                        list?.clear()
                        result = list
                    }
                }
            }
        }
        in 906..980 -> {
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
                        val imgAdIconClose = instance.getView<ImageView>(
                            "imgAdIconClose"
                        )
                        imgAdIconClose?.visibility = View.GONE
                        val layoutImgAdIcon = instance.getView<LinearLayout>(
                            "layoutImgAdIcon"
                        )
                        layoutImgAdIcon?.visibility = View.GONE

                        val imgBookShelfActivityIcon = instance.getView<ImageView>(
                            "imgBookShelfActivityIcon"
                        )
                        imgBookShelfActivityIcon?.visibility = View.GONE
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
                        val layoutImgAdIcon = instance.getView<LinearLayout>(
                            "layoutImgAdIcon"
                        )
                        layoutImgAdIcon?.visibility = View.GONE
                    }
                }

            }
        }

        in 827..980 -> {

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
                        param(View::class.java)
                    }
                    afterHook {
                        val layoutImgAdIcon = instance.getView<LinearLayout>(
                            "layoutImgAdIcon"
                        )
                        layoutImgAdIcon?.visibility = View.GONE
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
        in 758..980 -> {
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

        in 896..980 -> {
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
    when(versionCode){
        958 -> {
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
        in 812..980 -> {
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
        in 896..958 -> {
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
        in 812..980 -> {
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

        in 932..980 -> {
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
    val needHookClass = when (versionCode) {
        in 758..788 -> "com.qidian.QDReader.util.z4"
        in 792..796 -> "com.qidian.QDReader.util.i5"
        in 800..834 -> "com.qidian.QDReader.util.l5"
        in 842..878 -> "com.qidian.QDReader.util.m5"
        884 -> "com.qidian.QDReader.util.k5"
        in 890..900 -> "com.qidian.QDReader.util.l5"
        in 906..958 -> "com.qidian.QDReader.util.m5"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 758..878 -> "b"
        in 884..958 -> "judian"
        else -> null
    }
    val needHookMethod2 = when (versionCode) {
        in 758..878 -> "a"
        in 884..958 -> "search"
        else -> null
    }
    needHookClass?.hook {
        needHookMethod?.let {
            injectMember {
                method {
                    name = it
                    returnType = UnitType
                }
                intercept()
            }
        }

        needHookMethod2?.let {
            injectMember {
                method {
                    name = it
                    returnType = UnitType
                }
                intercept()
            }
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
        in 758..980 -> {

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
}
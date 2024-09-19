package cn.xihan.qdds.hook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import cn.xihan.qdds.BuildConfig
import cn.xihan.qdds.service.Collect
import cn.xihan.qdds.ui.MainActivity
import cn.xihan.qdds.util.CustomEditText
import cn.xihan.qdds.util.Option.initOption
import cn.xihan.qdds.util.Option.initialize
import cn.xihan.qdds.util.Option.optionEntity
import cn.xihan.qdds.util.Option.updateOptionEntity
import cn.xihan.qdds.util.Option.writeTextFile
import cn.xihan.qdds.util.Utils.saveImageFromUrl
import cn.xihan.qdds.util.alertDialog
import cn.xihan.qdds.util.appModule
import cn.xihan.qdds.util.copyToClipboard
import cn.xihan.qdds.util.getName
import cn.xihan.qdds.util.getParam
import cn.xihan.qdds.util.getParamList
import cn.xihan.qdds.util.getSystemContext
import cn.xihan.qdds.util.getVersionCode
import cn.xihan.qdds.util.getView
import cn.xihan.qdds.util.getViews
import cn.xihan.qdds.util.isSelectedByTitle
import cn.xihan.qdds.util.loge
import cn.xihan.qdds.util.okButton
import cn.xihan.qdds.util.printlnNotSupportVersion
import cn.xihan.qdds.util.safeCast
import cn.xihan.qdds.util.setParams
import cn.xihan.qdds.util.toast
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.factory.registerModuleAppActivities
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.android.ContextClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.ListClass
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.MapClass
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import org.json.JSONObject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.lazyModules
import org.luckypray.dexkit.DexKitBridge
import java.io.File
import java.lang.reflect.Modifier


/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2023/10/7 23:56
 * @介绍 : Hook 入口
 * @suppress Generate Documentation
 */
@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit, KoinComponent {

    init {
        System.loadLibrary("dexkit")
    }

    override fun onInit() = YukiHookAPI.configs {
        YLog.Configs.apply {
            tag = "yuki"
            isEnable = BuildConfig.DEBUG
        }
    }

    override fun onHook() = YukiHookAPI.encase {
        if (QD_PACKAGE_NAME !in packageName) return@encase
        loadApp(name = packageName) {
            val versionCode by lazy { getSystemContext().getVersionCode(packageName) }
            initOption()

            onAppLifecycle {
                onCreate {
                    startKoin {
                        androidLogger()
                        androidContext(this@onCreate)
                        lazyModules(appModule)
                    }
                    initialize(this)
                }
            }

            if (optionEntity.allowDisclaimers) {
                DexKitBridge.create(appInfo.sourceDir).use { bridge ->
                    mainFunction(versionCode = versionCode, bridge = bridge)
                }
            }

            "com.qidian.QDReader.ui.activity.MoreActivity".toClass().apply {
                method {
                    name = "initWidget"
                    emptyParam()
                    returnType = UnitType
                }.hook().after {
                    // 获取 MoreActivity 实例
                    val readMoreSetting = instance.getView<RelativeLayout>("readMoreSetting")
                    // 获取 readMoreSetting 子控件
                    val readMoreSettingChild = readMoreSetting?.getChildAt(0).safeCast<TextView>()
                    readMoreSettingChild?.text = "阅读设置/模块设置(长按)"
                    readMoreSetting?.setOnLongClickListener {
                        instance<Activity>().apply {
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        true
                    }
                }

                method {
                    name = "onCreate"
                    param(BundleClass)
                    returnType = UnitType
                }.hook().after {
                    instance<Activity>().registerModuleAppActivities()
                }

            }

        }
    }

    private fun PackageParam.mainFunction(versionCode: Int, bridge: DexKitBridge) {

        if (optionEntity.mainOption.enablePostToShowImageUrl) {
            postToShowImageUrl(versionCode, bridge)
        }

        if (optionEntity.mainOption.enableUnlockMemberBackground) {
            unlockMemberBackground(versionCode)
        }

        if (optionEntity.mainOption.enableFreeAdReward) {
            freeAdReward(versionCode)
        }

        if (optionEntity.mainOption.enableIgnoreFreeSubscribeLimit) {
            ignoreFreeSubscribeLimit(versionCode, bridge)
        }

        if (optionEntity.mainOption.enableExportEmoji) {
            exportEmoji(versionCode)
        }

        if (optionEntity.mainOption.enableOldDailyRead) {
            oldDailyRead(versionCode, bridge)
        }

        newDiscoveryPage(
            versionCode = versionCode,
            enabled = optionEntity.mainOption.enableNewFeedDiscovery
        )

        if (optionEntity.mainOption.enableCustomIMEI) {
            customIMEI(versionCode, bridge)
        }

        if (optionEntity.mainOption.enableFixDouYinShare) {
            fixDouYinShare(versionCode, bridge)
        }

        if (optionEntity.mainOption.enableCollectService) {
            collect(versionCode = versionCode)
        }

        if (optionEntity.cookieOption.enableCookie) {
            cookie(versionCode, bridge)
        }

        debug(versionCode)


        if (optionEntity.readPageOption.enableReadTimeFactor) {
            readingTimeSpeedFactor(
                versionCode = versionCode,
                speedFactor = optionEntity.readPageOption.speedFactor,
                bridge = bridge
            )
        }

        if (optionEntity.readPageOption.enableRedirectReadingPageBackgroundPath) {
            redirectReadingPageBackgroundPath(versionCode, bridge)
        }

        if (optionEntity.startImageOption.enableRedirectLocalStartImage) {
            customLocalStartImage(versionCode)
        }

        advOption(versionCode, optionEntity.advOption, bridge)

        interceptOption(versionCode, optionEntity.interceptOption, bridge)

        homeOption(versionCode, optionEntity.viewHideOption.homeOption.configurations, bridge)

        hideBottom(
            versionCode = versionCode,
            hideRedDot = optionEntity.viewHideOption.enableHideRedDot,
            hideNavigation = optionEntity.viewHideOption.homeOption.enableCaptureBottomNavigation,
            bridge = bridge
        )

        if (optionEntity.viewHideOption.selectedOption.enableSelectedHide) {
            selectedOption(versionCode)
        }

        if (optionEntity.viewHideOption.selectedOption.enableSelectedTitleHide) {
            selectedTitleOption(versionCode)
        }

        searchOption(versionCode, optionEntity.viewHideOption.searchOption, bridge)

        if (optionEntity.viewHideOption.accountOption.enableHideAccountRightTopRedDot) {
            accountRightTopRedDot(versionCode)
        }

        if (optionEntity.viewHideOption.accountOption.enableHideAccount) {
            accountViewHide(versionCode)
        }

        if (optionEntity.viewHideOption.enableHideRedDot) {
            hideRedDot(versionCode)
        }

        if (optionEntity.viewHideOption.enableHideBookDetail) {
            bookDetailHide(
                versionCode = versionCode,
                isNeedHideCqzs = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "出圈指数"
                ),
                isNeedHideRybq = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "荣誉标签"
                ),
                isNeedHideQqGroups = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "QQ群"
                ),
                isNeedHideSyq = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "书友圈"
                ),
                isNeedHideSyb = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "书友榜"
                ),
                isNeedHideYpjz = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "月票金主"
                ),
                isNeedHideActivityBanner = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "页面轮播广告"
                ),
                isNeedHideTopBanner = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "顶部轮播广告1|2"
                ),
                isNeedHideCenterAd = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "本书看点|中心广告"
                ),
                isNeedHideFloatAd = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "浮窗广告"
                ),
                isNeedHideBookRecommend = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "同类作品推荐"
                ),
                isNeedHideBookRecommend2 = optionEntity.viewHideOption.bookDetailOptions.isSelectedByTitle(
                    "看过此书的人还看过"
                )
            )
        }

        readingPageChapterCorrelation(
            versionCode = versionCode,
            enableShowReaderPageChapterSaveRawPictures = optionEntity.readPageOption.enableShowReaderPageChapterSaveRawPicture,
            enableShowReaderPageChapterSavePictureDialog = optionEntity.readPageOption.enableShowReaderPageChapterSavePictureDialog,
            enableShowReaderPageChapterSaveAudioDialog = optionEntity.readPageOption.enableShowReaderPageChapterSaveAudioDialog,
            enableCopyReaderPageChapterComment = optionEntity.readPageOption.enableCopyReaderPageChapterComment,
            bridge = bridge
        )

        if (optionEntity.viewHideOption.enableHideLastPage) {
            readBookLastPage(
                versionCode = versionCode,
                shieldAlsoRead = optionEntity.shieldOption.configurations.isSelectedByTitle("阅读-最后一页-看过此书的人还看过"),
                shieldSimilarRecommend = optionEntity.shieldOption.configurations.isSelectedByTitle(
                    "阅读-最后一页-同类作品推荐"
                ),
                shieldRecommendation = optionEntity.shieldOption.configurations.isSelectedByTitle("阅读-最后一页-推荐"),
                hideCircle = optionEntity.viewHideOption.bookLastPageOptions.isSelectedByTitle(
                    "书友圈"
                ),
                hideAlsoRead = optionEntity.viewHideOption.bookLastPageOptions.isSelectedByTitle(
                    "看过此书的人还看过"
                ),
                hideRecommendation = optionEntity.viewHideOption.bookLastPageOptions.isSelectedByTitle(
                    "推荐"
                ),
                hideSimilarRecommend = optionEntity.viewHideOption.bookLastPageOptions.isSelectedByTitle(
                    "同类作品推荐"
                ),
                hideBookList = optionEntity.viewHideOption.bookLastPageOptions.isSelectedByTitle(
                    "收录此书的书单"
                ),
                hideTryRead = optionEntity.viewHideOption.bookLastPageOptions.isSelectedByTitle(
                    "试读"
                ),
                hideAdView = optionEntity.advOption.isSelectedByTitle("阅读页-最后一页-中间广告")
            )
        }

        if (optionEntity.viewHideOption.readPageOptions.enableCaptureBookReadPageView) {
            hideReadPage(versionCode, bridge)
        }

        if (optionEntity.startImageOption.enableCustomStartImage) {
            customStartImage(versionCode)
        }

        if (optionEntity.startImageOption.enableCaptureTheOfficialLaunchMapList) {
            captureTheOfficialLaunchMapList(versionCode)
        }

        if (optionEntity.bookshelfOption.enableCustomBookShelfTopImage) {
            customBookShelfTopImage(versionCode)
        }

        shieldOption(versionCode, optionEntity.shieldOption.configurations, bridge)

        if (optionEntity.shieldOption.enableQuickShieldDialog) {
            quickShield(versionCode)
        }

        automatizationOption(
            versionCode = versionCode, optionEntity.automatizationOption
        )
    }

    companion object {
        const val QD_PACKAGE_NAME = "com.qidian.QDReader"
    }

}

/**
 * 解锁会员卡专属背景
 * @since 7.9.354-1296 ~ 1499
 * @param [versionCode] 版本代码
 */
fun PackageParam.unlockMemberBackground(versionCode: Int) {
    when (versionCode) {
        in 1296..1499 -> {
            "com.qidian.QDReader.ui.activity.QDReaderThemeDetailActivity".toClass().method {
                name = "updateViews"
                param(ListClass)
                returnType = UnitType
            }.hook().before {
                val list = args[0].safeCast<MutableList<*>>()
                list?.forEach {
                    it?.let {
                        if (it.javaClass.name == "com.qidian.QDReader.repository.dal.store.ReaderThemeEntity") {
                            val themeType = it.getParam<Long>("themeType")
                            if (themeType == 102L) {
                                it.setParams(
                                    "themeType" to 101L, "haveStatus" to 1
                                )
                            }
                        }
                    }
                }
            }
        }

        else -> "解锁会员卡专属背景".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 免广告领取奖励
 * @since 7.9.354-1296 ~ 1499
 * @param [versionCode] 版本代码
 */
fun PackageParam.freeAdReward(versionCode: Int) {
    when (versionCode) {
        in 1296..1499 -> {

            "com.qidian.QDReader.framework.webview.l".toClass().method {
                name = "judian"
                paramCount(3)
                returnType = UnitType
            }.hook().before {
                args(1).set(JSONObject("{\"status\":2}"))
            }

            "com.qidian.QDReader.repository.entity.Reward".toClass().method {
                name = "getRewardId"
                emptyParam()
                returnType = LongType
            }.hook().replaceTo(1L)

            "com.qidian.QDReader.ui.modules.interact.InteractHBContainerView".toClass().method {
                param(
                    "com.qidian.QDReader.ui.modules.interact.InteractHBContainerView".toClass(),
                    "kotlin.jvm.internal.Ref\$ObjectRef".toClass(),
                    "java.lang.Integer".toClass()
                )
                returnType = UnitType
            }.hook().before {
                args(2).set(5)
            }

            "com.qq.e.tg.ADActivity".toClass().method {
                name = "onCreate"
                param(BundleClass)
                returnType = UnitType
            }.hook().before {
                instance<Activity>().finish()
            }
        }

        else -> "免广告领取奖励".printlnNotSupportVersion(versionCode)
    }

}

/**
 * 忽略限免批量订阅限制
 * @since 7.9.354-1296 ~ 1499
 * @param [versionCode] 版本代码
 */
fun PackageParam.ignoreFreeSubscribeLimit(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1296..1499 -> {
            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.component.bll.manager")
                matcher {
                    methods {
                        add {
                            modifiers = Modifier.PRIVATE
                            returnType = "int"
                            paramCount = 3
                        }
                    }
                    usingStrings = listOf("IsFreeLimit", "HasCopyRight")
                }
            }.firstNotNullOfOrNull { classData ->
                classData.findMethod {
                    matcher {
                        returnType = "int"
                        paramTypes = listOf(
                            "com.qidian.QDReader.framework.network.qd.QDHttpResp",
                            "org.json.JSONObject",
                            "long"
                        )
                        usingStrings = listOf("IsFreeLimit")
                    }
                }.firstNotNullOfOrNull { methodData ->
                    methodData.className.toClass().method {
                        name = methodData.methodName
                        paramCount(3)
                        returnType = IntType
                    }.hook().before {
                        val jb = args[1].safeCast<JSONObject>()
                        jb?.optJSONObject("Data")?.apply {
                            put("BoolBatchSubscribe", true)
                            put("IsFreeLimit", -1)
                            put("DownloadTips", "")
                            put("IsFreeLimitMsg", "")
                        }
                        args(1).set(jb)
                    }
                }
            }
        }

        else -> "忽略限免批量订阅限制".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 一键导出表情包
 * @since 7.9.354-1296 ~ 1499
 * @param [versionCode] 版本代码
 */
fun PackageParam.exportEmoji(versionCode: Int) {
    when (versionCode) {
        in 1296..1499 -> {
            "com.qidian.QDReader.ui.activity.QDStickersDetailActivity".toClass().method {
                param(
                    "com.qidian.QDReader.ui.activity.QDStickersDetailActivity".toClass(),
                    "com.qidian.QDReader.repository.entity.dressup.StickersBean".toClass()
                )
                returnType = UnitType
            }.hook().after {
                val stickersBean = args[1] ?: return@after
                val viewMap = args[0]?.getParam<Map<*, View>>("_\$_findViewCache") ?: return@after
                val mName = stickersBean.getParam<String>("mName")
                val faceList = stickersBean.getParam<MutableList<*>>("mFaceList")
                val context = args[0] ?: return@after
                if (mName.isNullOrBlank() || faceList.isNullOrEmpty()) {
                    return@after
                }
                val imageMap = mutableMapOf<String, String>()
                val iterator = faceList.iterator()
                while (iterator.hasNext()) {
                    val item = iterator.next() ?: continue
                    val url = item.getParam<String>("mImage")
                    val text = item.getParam<String>("mText")
                    if (!url.isNullOrBlank() && !text.isNullOrBlank()) {
                        imageMap[text] = url
                    }
                }
                val topBar = viewMap.values.firstOrNull { "topBar" == it.getName() }
                    .safeCast<RelativeLayout>()
                if (topBar != null) {
                    val layoutParams = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                        addRule(RelativeLayout.CENTER_VERTICAL)
                        setMargins(0, 0, 20, 0)
                    }
                    val textView = TextView(topBar.context).apply {
                        text = "导出"
                        setOnClickListener {
                            topBar.context.exportEmojiDialog(
                                context = topBar.context,
                                imageTitle = mName,
                                imageList = imageMap,
                            )
                        }
                    }
                    textView.layoutParams = layoutParams
                    topBar.addView(textView)
                } else {
                    context.safeCast<Context>()?.toast("未找到topBar控件")
                }
            }
        }

        else -> "一键导出表情包".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 导出表情符号对话框
 * @since 7.9.354-1296
 * @param [context] 上下文
 * @param [yWImageLoader] Y wimage装载机
 * @param [imageList] 图像列表
 * @suppress Generate Documentation
 */
private fun Context.exportEmojiDialog(
    context: Context, imageTitle: String, imageList: Map<String, String>
) {
    alertDialog {
        title = "一键导出表情包"
        message = "导出 《${imageTitle}》 表情包 ${imageList.size} 张\n导出至: ${
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path
        }/QDReader/$imageTitle"
        okButton {
            saveImageFromUrl(context, imageTitle, imageList)
            it.dismiss()
        }
        negativeButton("取消") {
            it.dismiss()
        }
        build()
        show()
    }
}

/**
 * 发帖显示图片直链
 * @since 7.9.354-1296 ~ 1499
 * @param [versionCode] 版本代码
 */
fun PackageParam.postToShowImageUrl(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1296..1499 -> {
            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.ui.dialog")
                matcher {
                    methods {
                        add {
                            paramTypes = listOf(
                                "com.qidian.QDReader.repository.entity.upload.UploadImageResult",
                                "com.qidian.QDReader.repository.entity.upload.UploadImageResult"
                            )
                        }
                    }
                }
            }.firstNotNullOfOrNull { classData ->
                classData.name.toClass().method {
                    name = "dismiss"
                    emptyParam()
                    returnType = UnitType
                    superClass()
                }.hook().after {
                    val lists = instance.getParamList<List<*>>().takeUnless { it.isEmpty() }
                        ?.filterNot { it[0] is String }
                    lists?.firstOrNull()?.let { urlList ->
                        urlList.mapNotNull { it?.getParam<String>("mAccessUrl") }
                            .let { accessUrls ->
                                instance.getViews<TextView>().firstNotNullOfOrNull { it.context }
                                    ?.showUrlListDialog(accessUrls)
                            }
                    }
                }

            }
        }

        else -> "发帖显示图片直链".printlnNotSupportVersion(versionCode)
    }
}

/**
 * “显示url列表”对话框
 * @since 7.9.354-1296
 * @param [urls] url
 * @suppress Generate Documentation
 */
private fun Context.showUrlListDialog(urls: List<String>) {
    val customEditText = CustomEditText(
        context = this, value = urls.joinToString("\n")
    )
    alertDialog {
        title = "urlList"
        customView = customEditText
        positiveButton("复制全部") {
            context.copyToClipboard(urls.joinToString("\n"))
        }
        build()
        show()
    }
}

/**
 * 启用旧版每日导读
 * @since 7.9.354-1296 ~ 1499
 * @param [versionCode] 版本代码
 */
fun PackageParam.oldDailyRead(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1296..1499 -> {

            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.flutter")
                matcher {
                    usingStrings = listOf("flutterEntryPath", "RoutePath", "Params")
                }
            }.filter { "DailyReadingMainPageActivity" in it.name }
                .firstNotNullOfOrNull { classData ->
                    classData.findMethod {
                        matcher {
                            modifiers = Modifier.PUBLIC
                            paramCount = 3
                            returnType = "void"
                        }
                    }.firstNotNullOfOrNull { methodData ->
                        methodData.className.toClass().method {
                            name = methodData.methodName
                            paramCount(methodData.paramTypeNames.size)
                            returnType = UnitType
                        }.hook().replaceUnit {
                            val context = args[0].safeCast<Context>() ?: return@replaceUnit
                            val map = args[2].safeCast<Map<String, String>>() ?: return@replaceUnit
                            val instance by lazyClassOrNull("com.qidian.QDReader.ui.activity.DailyReadingActivity")
                            instance?.method {
                                name = "openDailyReading"
                                paramCount(2)
                                returnType = UnitType
                            }?.get(instance)?.call(context, map.values.first().toLong())
                        }
                    }
                }
        }


        else -> "启用旧版每日导读".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 启用新发现页
 * @since 7.9.354-1296 ~ 1499
 * @param [versionCode] 版本代码
 */
fun PackageParam.newDiscoveryPage(versionCode: Int, enabled: Boolean = false) {
    when (versionCode) {
        in 1296..1499 -> {
            "com.qidian.QDReader.repository.entity.config.AppConfigBean".toClass().method {
                name = "getNewFeedsDiscover"
                emptyParam()
                returnType = IntType
            }.hook().replaceTo(if (enabled) 1 else 0)
        }

        else -> "启用新发现页".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 启用自定义imei
 * @since 7.9.354-1296 ~ 1499
 * @param [versionCode] 版本代码
 */
fun PackageParam.customIMEI(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1296..1499 -> {
            bridge.apply {

                findClass {
                    searchPackages = listOf("com.tencent.nywbeacon.qimei")
                    matcher {
                        usingStrings = listOf(
                            "QIMEI_DENGTA",
                            "qimei_v2",
                            "Q_V3",
                            "local_qimei use qimei local: null"
                        )
                    }
                }.firstNotNullOfOrNull { classData ->
                    classData.findMethod {
                        matcher {
                            returnType = "java.lang.String"
                            usingStrings = listOf(
                                "QIMEI_DENGTA",
                                "qimei_v2",
                                "Q_V3",
                                "local_qimei use qimei local: null"
                            )
                        }
                    }.firstNotNullOfOrNull { methodData ->
                        methodData.className.toClass().method {
                            name = methodData.methodName
                            param(ContextClass)
                            returnType = StringClass
                        }.hook()
                            .replaceTo("""{"A3":"${optionEntity.mainOption.qimei}","A153":"${optionEntity.mainOption.qimei}"}""")
                    }
                }

                findClass {
                    matcher {
                        usingStrings = listOf(
                            "HUAWEI_OAID", "0821CAAD409B8402", "BEACON_QIMEI", "BEACON_QIMEI_36"
                        )
                    }
                }.firstNotNullOfOrNull { classData ->
                    classData.findMethod {
                        matcher {
                            returnType = "java.lang.String"
                            usingStrings = listOf(
                                "BEACON_QIMEI", "BEACON_QIMEI_36"
                            )
                        }
                    }.forEach { methodData ->
                        methodData.className.toClass().method {
                            name = methodData.methodName
                            emptyParam()
                            returnType = StringClass
                        }.hook().replaceTo(optionEntity.mainOption.qimei)
                    }
                }
            }
        }

        else -> "启用默认IMEI".printlnNotSupportVersion(versionCode)
    }
}

fun PackageParam.cookie(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1296..1499 -> {
            "com.qidian.QDReader.repository.entity.user_account.UserAccountItemBean".toClass()
                .method {
                    name = "getUserBasicInfo"
                    emptyParam()
                    returnType =
                        "com.qidian.QDReader.repository.entity.user_account.UserBasicInfo".toClass()
                }.hook().after {
                    val userId = result?.getParam<Int>("userId")
                    if (userId != null && userId > 0) {
                        optionEntity.cookieOption.uid = userId
                        updateOptionEntity()
                    }
                }

            "com.qidian.QDReader.component.util.FockUtil".toClass().method {
                name = "getH"
                paramCount(3)
                returnType = MapClass
            }.hook().after {
                val map = result?.safeCast<Map<*, *>>()
                if (map != null) {
                    map["Cookie"].safeCast<String>()?.let {
                        optionEntity.cookieOption.cookie = it
                        updateOptionEntity()
                    }
                    map["User-Agent"].safeCast<String>()?.let {
                        optionEntity.cookieOption.ua = it
                        updateOptionEntity()
                    }
                }
            }
        }
    }
}

fun PackageParam.debug(versionCode: Int) {
    when (versionCode) {
        in 1296..1499 -> {
            if (optionEntity.cookieOption.enableDebug) {
                "com.yuewen.fock.Fock".toClass().method {
                    name = "sign"
                    paramCount(1)
                    returnType = StringClass
                }.hook().after {
                    "参数: ${
                        args.first().safeCast<String>()
                    }, 返回值: ${result.safeCast<String>()}".writeTextFile()
                }
            }

            "a.c".toClass().method {
                name = "signParams"
                paramCount(8)
            }.hook().after {
                val uid = args[3].safeCast<String>() ?: "空"
                val qimei = args[4].safeCast<String>()?.ifBlank { "空" }
                if (optionEntity.mainOption.enableCollectService) {
                    if (uid.toLongOrNull() != null && Collect.userId == 0L) {
//                        "uid: $uid".loge()
                        Collect.userId = uid.toLong()
                    }
                }
                if (optionEntity.cookieOption.enableCookie) {
                    optionEntity.cookieOption.qimei = qimei ?: ""
                    updateOptionEntity()
                }
                if (optionEntity.cookieOption.enableDebug) {
                    StringBuilder().apply {
                        append("参数: ${args[1].safeCast<String>()?.ifBlank { "空" }}")
                        append(", 时间戳: ${args[2].safeCast<String>()?.ifBlank { "空" }}")
                        append(", UID: $uid")
                        append(", QIMEI: $qimei")
                        append(", Type: ${args[6].safeCast<Int>()}")
                        append(", 返回值: ${
                            result.safeCast<ByteArray>()
                                ?.let { com.qidian.common.lib.util.c.search(it) }
                        }")
                    }.toString().writeTextFile()
                }
            }
        }
    }
}

/**
 * 修复抖音分享
 * @param versionCode Int
 * @param bridge DexKitBridge
 * @since 7.9.354-1296 ~ 1499
 *
 */
fun PackageParam.fixDouYinShare(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1296..1499 -> {
            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.util")
                matcher {
                    usingStrings = listOf(
                        "awamlwk7qtxrss9v",
                        "dyAuthorCallback",
                    )
                }
            }.firstNotNullOfOrNull { classData ->
                classData.findMethod {
                    matcher {
                        paramTypes = listOf(
                            "java.lang.String", "org.json.JSONObject", "android.app.Activity"
                        )
                        returnType = "void"
                        usingStrings = listOf(
                            "hashTags", "openID"
                        )
                    }
                }.firstNotNullOfOrNull { methodData ->
                    methodData.className.toClass().method {
                        name = methodData.methodName
                        paramCount(methodData.paramTypeNames.size)
                        returnType = UnitType
                    }.hook().before {
                        val path = args.first().safeCast<String>()
                            ?: run { "路径为空".loge();return@before }
                        val file = File(path)
                        val newFile = File(
                            file.parent, "video.mp4"
                        )
                        file.renameTo(newFile)
                        args(0).set(newFile.absolutePath)
                    }
                }

            }
        }
    }
}

fun PackageParam.collect(versionCode: Int) {
    when (versionCode) {
        in 1296..1499 -> {
            "com.qidian.QDReader.ui.activity.QDBookDetailActivity".toClass().method {
                param(
                    "com.qidian.QDReader.ui.activity.QDBookDetailActivity".toClass(),
                    BooleanType,
                    "com.qidian.QDReader.repository.entity.BookDetail".toClass()
                )
                returnType = UnitType
            }.hook().after {
                args[2]?.let {
                    val author = it.getParam<Any>("authorInfo")?.getParam<String>("author")
                    val bookInfo = it.getParam<Any>("baseBookInfo") ?: return@after
                    val bookId = bookInfo.getParam<Long>("bookId") ?: return@after
                    val bookName = bookInfo.getParam<String>("bookName") ?: return@after
                    val bookStatus = bookInfo.getParam<String>("bookStatus") ?: return@after
                    val bookDesc = bookInfo.getParam<String>("description") ?: return@after
                    val bookWordCount = bookInfo.getParam<Long>("wordsCnt") ?: return@after
                    val bookCategory = bookInfo.getParam<String>("categoryName") ?: return@after
                    val bookSubCategory =
                        bookInfo.getParam<String>("subCategoryName") ?: return@after
                    if (author != null && bookId > 0L) {
                        Collect.sendBook(
                            bookId = bookId,
                            bookName = bookName,
                            bookStatus = bookStatus,
                            bookDesc = bookDesc,
                            bookWordCount = bookWordCount,
                            bookCategory = bookCategory,
                            bookSubCategory = bookSubCategory,
                            bookAuthor = author
                        )
                    }
                }
            }

            "com.qidian.QDReader.ui.modules.bookshelf.BookShelfViewModel".toClass().method {
                name = "getBookShelfInfoFlow"
                paramCount(2)
                returnType = "java.lang.Object".toClass()
            }.hook().after {
                val bookIdString = args[0].safeCast<String>() ?: return@after
                val bookIds = bookIdString.split(",").mapNotNull { it.toLongOrNull() }
                if (bookIds.isNotEmpty()) {
                    Collect.sendBookShelf(bookIds)
                }
            }
        }
    }
}

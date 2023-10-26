package cn.xihan.qdds

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.factory.registerModuleAppActivities
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.ListClass
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.UnitType
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import org.json.JSONObject
import org.luckypray.dexkit.DexKitBridge
import java.lang.reflect.Modifier

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2023/10/7 23:56
 * @介绍 : Hook 入口
 * @suppress Generate Documentation
 */
@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit {

    init {
        if (optionEntity.allowDisclaimers) {
            System.loadLibrary("dexkit")
        }
    }

    override fun onInit() = YukiHookAPI.configs {
        YLog.Configs.apply {
            tag = "yuki"
            isEnable = BuildConfig.DEBUG
        }
    }

    override fun onHook() = YukiHookAPI.encase {

        loadApp(name = QD_PACKAGE_NAME) {

            if (optionEntity.allowDisclaimers) {
                DexKitBridge.create(appInfo.sourceDir)?.use { bridge ->
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

        if (optionEntity.mainOption.enableStartCheckingPermissions) {
            startCheckingPermissions(versionCode)
        }

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

        if (optionEntity.startImageOption.enableRedirectLocalStartImage) {
            customLocalStartImage(versionCode)
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
        val QD_PACKAGE_NAME by lazy {
            optionEntity.mainOption.packageName.ifBlank { "com.qidian.QDReader" }
        }

        val versionCode by lazy { getSystemContext().getVersionCode(QD_PACKAGE_NAME) }

        val optionEntity by lazy {
            readOptionEntity()
        }

        /**
         * 需要屏蔽的作者列表
         */
        private val authorList by lazy {
            optionEntity.shieldOption.authorList
        }

        /**
         * 需要屏蔽的书名关键词列表
         */
        private val bookNameList by lazy {
            optionEntity.shieldOption.bookNameList
        }

        /**
         * 需要屏蔽的书籍类型列表
         */
        private val bookTypeList by lazy {
            optionEntity.shieldOption.bookTypeList
        }

        /**
         * 判断是否需要屏蔽
         * @param bookName 书名-可空
         * @param authorName 作者名-可空
         * @param bookType 书类型-可空
         */
        fun isNeedShield(
            bookName: String? = null, authorName: String? = null, bookType: Set<String>? = null
        ): Boolean {/*
            if (BuildConfig.DEBUG) {
                "bookName: $bookName\nauthorName:$authorName\nbookType:$bookType".loge()
            }

             */

            bookNameList.takeIf { it.isNotEmpty() }?.let { bookNameList ->
                bookName.takeUnless { it.isNullOrBlank() }?.let { bookName ->
                    if (bookNameList.any { it in bookName }) {
                        return true
                    }
                }
            }

            authorList.takeIf { it.isNotEmpty() }?.let { authorList ->
                authorName.takeUnless { it.isNullOrBlank() }?.let { authorName ->
                    if (authorName in authorList) {
                        return true
                    }
                }
            }

            bookTypeList.takeIf { it.isNotEmpty() }?.let { list ->
                bookType.takeUnless { it.isNullOrEmpty() }?.let { type ->
                    val bookTypes = type.filter { it.isNotBlank() || it.length > 1 }.toSet()
                    bookTypes.takeIf { it.isNotEmpty() }?.let { types ->
                        if (optionEntity.shieldOption.enableBookTypeEnhancedBlocking) {
                            if (types.any { list.any { it1 -> it1 in it } }) {
                                return true
                            }
                        } else {
                            if (types.any { it in list }) {
                                return true
                            }
                        }
                    }
                }
            }

            return false
        }

        /**
         * 解析需要屏蔽的书籍列表
         */
        fun parseNeedShieldList(list: MutableList<*>): List<*> {
            val iterator = list.iterator()
            while (iterator.hasNext()) {
                runAndCatch {
                    val jb = iterator.next().toJSONString().parseObject()
                    val bookName =
                        jb.getStringWithFallback("bookName") ?: jb.getStringWithFallback("itemName")
                    val authorName = jb.getStringWithFallback("authorName")
                    val categoryName = jb.getStringWithFallback("categoryName")
                    val subCategoryName = jb.getStringWithFallback("subCategoryName")
                        ?: jb.getStringWithFallback("itemSubName")
                    val tagName = jb.getStringWithFallback("tagName")
                    val array = jb.getJSONArrayWithFallback("AuthorTags")
                        ?: jb.getJSONArrayWithFallback("tags")
                        ?: jb.getJSONArrayWithFallback("tagList")
                    val tip = jb.getStringWithFallback("tip")
                    val bookTypeArray = mutableSetOf<String>()
                    if (categoryName != null) {
                        bookTypeArray += categoryName
                    }
                    if (subCategoryName != null) {
                        bookTypeArray += subCategoryName
                    }
                    if (tagName != null) {
                        bookTypeArray += tagName
                    }
                    if (tip != null) {
                        bookTypeArray += tip
                    }
                    if (!array.isNullOrEmpty()) {
                        for (i in array.indices) {
                            val tag = array.getString(i)
                            if ("{" in tag) {
                                tag.parseObject()?.getStringWithFallback("tagName")?.let {
                                    bookTypeArray += it
                                }
                            } else {
                                array.getString(i)?.let {
                                    bookTypeArray += it
                                }
                            }
                        }
                    }
                    if (isNeedShield(bookName, authorName, bookTypeArray)) {
                        iterator.remove()
                    }
                }
            }
            return list
        }

        /**
         * 解析需要屏蔽的漫画列表
         */
        fun parseNeedShieldComicList(list: MutableList<*>): List<*> {
            val iterator = list.iterator()
            while (iterator.hasNext()) {
                runAndCatch {
                    val jb = iterator.next().toJSONString().parseObject()
                    val comicName = jb.getStringWithFallback("comicName")
                    val authorName =
                        jb.getStringWithFallback("authorName") ?: jb.getStringWithFallback("Author")
                    val categoryName = jb.getStringWithFallback("categoryName")
                    val subCategoryName = jb.getStringWithFallback("subCategoryName")
                    val tagName = jb.getStringWithFallback("tagName")
                    val extraTag = jb.getStringWithFallback("extraTag")
                    val array = jb.getJSONArrayWithFallback("authorTags")
                        ?: jb.getJSONArrayWithFallback("tags")
                        ?: jb.getJSONArrayWithFallback("tagList")
                    val bookTypeArray = mutableSetOf<String>()
                    if (categoryName != null) {
                        bookTypeArray += categoryName
                    }
                    if (subCategoryName != null) {
                        bookTypeArray += subCategoryName
                    }
                    if (tagName != null) {
                        bookTypeArray += tagName
                    }
                    if (extraTag != null) {
                        bookTypeArray += extraTag
                    }
                    if (!array.isNullOrEmpty()) {
                        for (i in array.indices) {
                            array.getString(i)?.let {
                                bookTypeArray += it
                            }
                        }
                    }
                    if (isNeedShield(comicName, authorName, bookTypeArray)) {
                        iterator.remove()
                    }
                }
            }
            return list
        }

        /**
         * 解析精选标题返回需要删除的列表
         */
        fun getNeedShieldTitleList(): List<Int> {
            val type = mapOf(
                1000 to "男生",
                1001 to "女生",
                1002 to "胶囊",
                1003 to "漫画",
                1004 to "听书",
                1005 to "完本",
                1010 to "全部",
                0x3F3 to "7-11岁",
                0x3F4 to "12-15岁",
                0x3F5 to "16-18岁",
            )
            val needShieldTitleList =
                optionEntity.viewHideOption.selectedOption.selectedTitleConfigurations.filter {
                    it.selected && it.title in type.values
                }
            return needShieldTitleList.mapNotNull { type.filterValues { it1 -> it1 == it.title }.keys.firstOrNull() }
        }

        /**
         * 添加屏蔽的书名和作者 然后更新
         * @param bookName 书名
         * @param authorName 作者
         */
        fun addShieldBook(
            bookName: String = "", authorName: String = ""
        ) {
            when {
                bookName.isNotBlank() -> {
                    optionEntity.shieldOption.bookNameList += bookName
                }

                authorName.isNotBlank() -> {
                    optionEntity.shieldOption.authorList += authorName
                }
            }
            updateOptionEntity()
        }

    }

}

/**
 * 开始检查权限
 * @since 7.9.306-1030
 * @param [versionCode] 版本代码
 * @suppress Generate Documentation
 */
fun PackageParam.startCheckingPermissions(versionCode: Int) {
    when (versionCode) {
        in 1030..1099 -> {
            "com.qidian.QDReader.ui.activity.SplashActivity".toClass().apply {
                val hook = method {
                    name = "go2Where"
                    emptyParam()
                    returnType = UnitType
                }.hook {
                    replaceUnit {
                        instance<Activity>().requestPermissionDialog()
                    }
                }

                val hook2 = method {
                    name = "go2Main"
                    paramCount(1)
                    returnType = UnitType
                }.hook {
                    replaceUnit {
                        instance<Activity>().requestPermissionDialog()
                    }
                }

                method {
                    name = "onCreate"
                    param(BundleClass)
                    returnType = UnitType
                }.hook().after {
                    instance<Activity>().apply {
                        // 判断权限
                        val permission = XXPermissions.isGranted(
                            this, if (this.applicationInfo.targetSdkVersion > 30) arrayOf(
                                Permission.MANAGE_EXTERNAL_STORAGE,
                                Permission.REQUEST_INSTALL_PACKAGES
                            ) else Permission.Group.STORAGE.plus(Permission.REQUEST_INSTALL_PACKAGES)
                        )
                        if (permission) {
                            hook.remove()
                            hook2.remove()
                        }
                    }
                }
            }
        }

        else -> "startCheckingPermissions".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 解锁会员卡专属背景
 * @since 7.9.306-1030 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.unlockMemberBackground(versionCode: Int) {
    when (versionCode) {
        in 1030..1099 -> {
            "com.qidian.QDReader.ui.activity.QDReaderThemeDetailActivity".toClass().method {
                name = "updateViews"
                param(ListClass)
                returnType = UnitType
            }.hook().after {
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
 * @since 7.9.306-1030 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.freeAdReward(versionCode: Int) {
    when (versionCode) {
        in 1030..1099 -> {

            "com.qidian.QDReader.framework.webview.g".toClass().method {
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
                args.printArgs().loge()
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
 * @since 7.9.306-1030 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.ignoreFreeSubscribeLimit(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1030..1099 -> {
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
                classData.getMethods().findMethod {
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
                        jb?.optJSONObject("Data")?.put("IsFreeLimit", -1)
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
 * @since 7.9.306-1030 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.exportEmoji(versionCode: Int) {
    when (versionCode) {
        in 1030..1099 -> {
            "com.qidian.QDReader.ui.activity.QDStickersDetailActivity".toClass().method {
                param(
                    "com.qidian.QDReader.ui.activity.QDStickersDetailActivity".toClass(),
                    "com.qidian.QDReader.repository.entity.dressup.StickersBean".toClass()
                )
                returnType = UnitType
            }.hook().after {
                val stickersBean = args[1] ?: return@after
                val viewMap = args[0]?.getParam<Map<*, View>>("_\$_findViewCache") ?: return@after
                val faceList = stickersBean.getParam<MutableList<*>>("mFaceList")
                val yWImageLoader = "com.yuewen.component.imageloader.YWImageLoader".toClassOrNull()
                val context = args[0] ?: return@after
                if (faceList.isNullOrEmpty() || yWImageLoader == null) {
                    return@after
                }
                val imageList = mutableListOf<String>()
                val iterator = faceList.iterator()
                while (iterator.hasNext()) {
                    val image = iterator.next()?.getParam<String>("mImage")
                    if (!image.isNullOrBlank()) {
                        imageList += image
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
                                context = context,
                                imageList = imageList,
                                yWImageLoader = yWImageLoader
                            )
                        }
                    }
                    textView.layoutParams = layoutParams
                    topBar.addView(textView)
                } else {
                    "一键导出表情包".printlnNotSupportVersion(versionCode)
                }

            }
        }

        else -> "一键导出表情包".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 导出表情符号对话框
 * @since 7.9.306-1030
 * @param [context] 上下文
 * @param [yWImageLoader] Y wimage装载机
 * @param [imageList] 图像列表
 * @suppress Generate Documentation
 */
private fun Context.exportEmojiDialog(
    context: Any, yWImageLoader: Class<*>, imageList: List<String>
) {
    alertDialog {
        title = "一键导出表情包"
        message = "导出表情包 ${imageList.size} 张"
        okButton {
            imageList.forEach { imageUrl ->
                yWImageLoader.method {
                    name = "saveBitmap"
                    paramCount(6)
                    returnType = UnitType
                }.get(yWImageLoader).call(
                    context,
                    imageUrl,
                    "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/QDReader/Pictures",
                    "",
                    true,
                    null
                )
            }
            toast("导出成功")
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
 * @since 7.9.306-1030 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.postToShowImageUrl(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1030..1099 -> {
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
 * @since 7.9.306-1030
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
 * @since 7.9.306-1050 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.oldDailyRead(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1050..1099 -> {

            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.flutter")
                matcher {
                    usingStrings = listOf("flutterEntryPath", "RoutePath", "Params")
                }
            }.filter { "DailyReadingMainPageActivity" in it.name }
                .firstNotNullOfOrNull { classData ->
                    classData.getMethods().findMethod {
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
                            val stringArray =
                                args[2].safeCast<Array<String>>() ?: return@replaceUnit
                            val instance by lazyClassOrNull("com.qidian.QDReader.ui.activity.DailyReadingActivity")
                            instance?.method {
                                name = "openDailyReading"
                                paramCount(2)
                                returnType = UnitType
                            }?.get(instance)?.call(context, stringArray.first().toLong())
                        }
                    }
                }
        }


        else -> "启用旧版每日导读".printlnNotSupportVersion(versionCode)
    }
}
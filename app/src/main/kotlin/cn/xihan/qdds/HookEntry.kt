package cn.xihan.qdds

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import cn.xihan.qdds.HookEntry.Companion.NOT_SUPPORT_OLD_LAYOUT_VERSION_CODE
import cn.xihan.qdds.HookEntry.Companion.optionEntity
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import com.google.android.material.appbar.AppBarLayout
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.current
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.factory.registerModuleAppActivities
import com.highcapable.yukihookapi.hook.log.YukiHookLogger
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.android.ViewClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.CharSequenceClass
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.JSONObjectClass
import com.highcapable.yukihookapi.hook.type.java.ListClass
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import de.robv.android.xposed.XposedHelpers
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.CopyOnWriteArrayList

/**
 * @项目名 : BaseHook
 * @作者 : MissYang
 * @创建时间 : 2022/7/4 16:32
 * @介绍 :
 */
@InjectYukiHookWithXposed//(modulePackageName = "cn.xihan.qdds", entryClassName = "HookEntryInit")
class HookEntry : IYukiHookXposedInit {

    override fun onInit() {
        YukiHookAPI.configs {
            YukiHookLogger.Configs.tag = "yuki"
            YukiHookLogger.Configs.isEnable = BuildConfig.DEBUG
        }
    }

    override fun onHook() = YukiHookAPI.encase {

        loadApp(name = QD_PACKAGE_NAME) {

            if (optionEntity.allowDisclaimers) {
                mainFunction(versionCode = versionCode)
            }

            /**
             * 开启OkHttp3 日志拦截器
             *//*
            findClass("com.qidian.QDReader.framework.network.common.QDHttpLogInterceptor").hook {
                injectMember {
                    method {
                        name = "c"
                        param(BooleanType)
                    }
                    beforeHook{
                        args(0).setTrue()
                    }
                }
            }

             */

            /**
             * 调试-查看跳转关键词
             *//*
            findClass("com.qidian.QDReader.other.ActionUrlProcess").hook {
                /*
                injectMember {
                    method {
                        name = "processOpenBookListReborn"
                        param(ContextClass, JSONObjectClass)
                    }
                    afterHook {
                        printCallStack(instanceClass.name)
                        val s = args[1] as? JSONObject
                        loggerE(msg = "s: $s")
                    }
                }

                 */

                injectMember {
                    method {
                        name = "processSinceV650"
                        param(ContextClass, StringClass, JSONObjectClass)
                    }
                    afterHook {
                        //printCallStack(instance.javaClass.name)
                        val s = args[1] as? String
                        val jb = args[2] as? JSONObject
                        loggerE(msg = "s: $s\njb: $jb")
                    }
                }

                injectMember {
                    method {
                        name = "processComicSquare"
                        param(ContextClass, StringClass, JSONObjectClass)
                    }
                    afterHook {
                        //printCallStack(instance.javaClass.name)
                        val s = args[1] as? String
                        val jb = args[2] as? JSONObject
                        loggerE(msg = "s: $s\njb: $jb")
                    }
                }
            }

             */

            /**
             * 调试-打印返回数据
             *//*
            findClass("com.qidian.QDReader.framework.network.qd.QDHttpResp").hook {
                injectMember {
                    constructor {
                        param(BooleanType, IntType, IntType, StringClass, LongType)
                    }
                    afterHook {
                        val s = args[3] as? String
                        if (!s.isNullOrBlank()) {
                            if (s.contains("MsgList")) {
                                instance.printCallStack()
                            }
                            loggerE(msg = "5 data: $s")
                        }
                    }

                }

                injectMember {
                    constructor {
                        param(BooleanType, BitmapClass, StringClass)
                    }
                    afterHook {
                        val s = args[2] as? String
                        if (!s.isNullOrBlank()) {
                            loggerE(msg = "3 data: $s")
                        }
                    }
                }
            }

             */

            /**
             * 调试-开启GDT日志
             *//*
            findClass("com.qq.e.comm.util.GDTLogger").hook {
                injectMember {
                    method {
                        name = "isEnableReportLog"
                        emptyParam()
                        returnType = BooleanType
                    }
                    replaceToTrue()
                }
            }

             */

            /*
            findClass("com.qidian.QDReader.ui.fragment.QDUserAccountFragment").hook {
                injectMember {
                    method {
                        name = "loadData"
                        emptyParam()
                        returnType = UnitType
                    }
                    afterHook {
                        val mLatestUserAccountDataBean =
                            instance.getParam<Any>("mLatestUserAccountDataBean")
                        val member = mLatestUserAccountDataBean?.getParam<Any>("Member")
                        member?.let {
                            XposedHelpers.setIntField(it, "IsMember", 1)
                            XposedHelpers.setIntField(it, "MemberType", 2)
                        }

                        val userBasicInfo =
                            mLatestUserAccountDataBean?.getParam<Any>("UserBasicInfo")
                        userBasicInfo?.let {
                            val userId = it.getParam<Long>("UserId")
                            val nickName = it.getParam<String>("NickName")
                            if (userId != null && nickName != null) {
                                val writeSuccess = readOptionFileAndWriteCustomText(
                                    mapOf(
                                        "userId" to userId,
                                        "nickName" to nickName
                                    )
                                )
                                "userId: $userId\nnickName: $nickName".loge()
                                "writeSuccess: $writeSuccess".loge()
                            }
                        }
                    }
                }
            }

             */

            /*
            findClass("okhttp3.OkHttpClient").hook {
                injectMember {
                    constructor {
                        paramCount(1)
                    }
                    beforeHook {
                        val builder = args.getOrNull(0) ?: return@beforeHook
                        val firstUserInterceptorCls =
                            builder.getParam<ArrayList<*>>("interceptors")?.getOrNull(0)?.javaClass
                                ?: return@beforeHook
                        "name: ${firstUserInterceptorCls.name}".loge()

                    }
                }
            }

             */

        }

    }

    private fun PackageParam.mainFunction(versionCode: Int) {


        if (optionEntity.mainOption.enableAutoSign) {
            autoSignIn(versionCode, optionEntity.bookshelfOption.enableOldLayout)
        }

        if (optionEntity.mainOption.enableReceiveReadingCreditsAutomatically) {
            receiveReadingCreditsAutomatically(versionCode)
        }

        if (optionEntity.mainOption.enablePostToShowImageUrl) {
            postToShowImageUrl(versionCode)
        }

        if (optionEntity.mainOption.enableLocalCard) {
            enableLocalCard(versionCode)
        }

        if (optionEntity.mainOption.enableUnlockMemberBackground) {
            unlockMemberBackground(versionCode)
        }

        if (optionEntity.mainOption.enableFreeAdReward) {
            freeAdReward(versionCode)
        }

        if (optionEntity.mainOption.enableIgnoreFreeSubscribeLimit) {
            ignoreFreeSubscribeLimit(versionCode)
        }

        if (optionEntity.mainOption.enableExportEmoji) {
            exportEmoji(versionCode)
        }

        if (optionEntity.mainOption.enableForceTrialMode) {
            forceTrialMode(versionCode)
        }

        if (optionEntity.hideBenefitsOption.enableHideWelfare) {
            hideWelfare(versionCode)
        }

        if (optionEntity.bookshelfOption.enableOldLayout && versionCode < NOT_SUPPORT_OLD_LAYOUT_VERSION_CODE) {
            enableOldLayout(versionCode)
        }

        readerPageChapterReviewPictures(
            versionCode = versionCode,
            enableShowReaderPageChapterSaveRawPictures = optionEntity.readPageOption.enableShowReaderPageChapterSaveRawPicture,
            enableShowReaderPageChapterSavePictureDialog = optionEntity.readPageOption.enableShowReaderPageChapterSavePictureDialog,
            enableShowReaderPageChapterSaveAudioDialog = optionEntity.readPageOption.enableShowReaderPageChapterSaveAudioDialog,
            enableCopyReaderPageChapterComment = optionEntity.readPageOption.enableCopyReaderPageChapterComment,
        )

        if (optionEntity.readPageOption.enableReadTimeDouble) {
            readTimeDouble(
                versionCode = versionCode,
                enableVIPChapterTime = optionEntity.readPageOption.enableVIPChapterTime,
                doubleSpeed = optionEntity.readPageOption.doubleSpeed
            )
        }

        if (optionEntity.readPageOption.enableCustomReaderThemePath) {
            customReadBackgroundPath(versionCode)
        }

        advOption(versionCode, optionEntity.advOption.configurations)

        interceptOption(versionCode, optionEntity.interceptOption.configurations)

        if ((optionEntity.viewHideOption.homeOption.configurations.any { it.selected })) {
            homeOption(
                versionCode, optionEntity.viewHideOption.homeOption.configurations
            )
        }

        if (optionEntity.viewHideOption.homeOption.enableCaptureBottomNavigation) {
            hideBottomNavigation(versionCode)
        }

        if (optionEntity.viewHideOption.selectedOption.enableSelectedHide) {
            selectedOption(versionCode)
        }

        if (optionEntity.viewHideOption.selectedOption.enableSelectedTitleHide) {
            selectedTitleOption(versionCode)
        }

        if (optionEntity.viewHideOption.enableSearchHideAllView) {
            hideSearchAllView(versionCode)
        }

        if (optionEntity.viewHideOption.enableDisableQSNModeDialog) {
            removeQSNYDialog(versionCode)
        }

        if (optionEntity.viewHideOption.enableHideComicBannerAd) {
            comicHideBannerAd(versionCode)
        }

        if (optionEntity.viewHideOption.accountOption.enableHideAccountRightTopRedDot) {
            accountRightTopRedDot(versionCode)
        }

        if (optionEntity.viewHideOption.findOption.enableHideFindItem) {
            findViewHide(versionCode)
        }

        if (optionEntity.viewHideOption.accountOption.enableHideAccount) {
            accountViewHide(versionCode)
        }

        if (optionEntity.viewHideOption.enableHideRedDot) {
            hideRedDot(versionCode)
        }

        if (versionCode >= 868) {
            newOldLayout(
                versionCode = versionCode,
                enableNewUserAccount = optionEntity.viewHideOption.accountOption.enableNewAccountLayout,
                enableNewStore = optionEntity.mainOption.enableNewStore,
                enableNewBookShelfLayout = optionEntity.bookshelfOption.enableNewBookShelfLayout
            )
        }

        if (optionEntity.viewHideOption.bookDetailOptions.enableHideBookDetail) {
            bookDetailHide(
                versionCode = versionCode,
                isNeedHideCqzs = optionEntity.viewHideOption.bookDetailOptions.configurations.isSelectedByTitle("出圈指数"),
                isNeedHideRybq = optionEntity.viewHideOption.bookDetailOptions.configurations.isSelectedByTitle("荣誉标签"),
                isNeedHideQqGroups = optionEntity.viewHideOption.bookDetailOptions.configurations.isSelectedByTitle("QQ群"),
                isNeedHideSyq = optionEntity.viewHideOption.bookDetailOptions.configurations.isSelectedByTitle("书友圈"),
                isNeedHideSyb = optionEntity.viewHideOption.bookDetailOptions.configurations.isSelectedByTitle("书友榜"),
                isNeedHideYpjz = optionEntity.viewHideOption.bookDetailOptions.configurations.isSelectedByTitle("月票金主"),
                isNeedHideCenterAd = optionEntity.viewHideOption.bookDetailOptions.configurations.isSelectedByTitle("本书看点|中心广告"),
                isNeedHideFloatAd = optionEntity.viewHideOption.bookDetailOptions.configurations.isSelectedByTitle("浮窗广告"),
                isNeedHideBookRecommend = optionEntity.viewHideOption.bookDetailOptions.configurations.isSelectedByTitle("同类作品推荐"),
                isNeedHideBookRecommend2 = optionEntity.viewHideOption.bookDetailOptions.configurations.isSelectedByTitle("看过此书的人还看过")
            )
        }

        if (optionEntity.viewHideOption.bookLastPageOptions.enableHideBookLastPage) {
            readBookLastPage(
                versionCode = versionCode,
                shieldAlsoRead = isEnableShieldOption(16),
                shieldSimilarRecommend = isEnableShieldOption(17),
                shieldRecommendation = isEnableShieldOption(18),
                hideCircle = optionEntity.viewHideOption.bookLastPageOptions.configurations.isEnabled(
                    optionEntity.viewHideOption.bookLastPageOptions.configurations[0].title
                ),
                hideAlsoRead = optionEntity.viewHideOption.bookLastPageOptions.configurations.isEnabled(
                    optionEntity.viewHideOption.bookLastPageOptions.configurations[1].title
                ),
                hideRecommendation = optionEntity.viewHideOption.bookLastPageOptions.configurations.isEnabled(
                    optionEntity.viewHideOption.bookLastPageOptions.configurations[2].title
                ),
                hideSimilarRecommend = optionEntity.viewHideOption.bookLastPageOptions.configurations.isEnabled(
                    optionEntity.viewHideOption.bookLastPageOptions.configurations[3].title
                ),
                hideBookList = optionEntity.viewHideOption.bookLastPageOptions.configurations.isEnabled(
                    optionEntity.viewHideOption.bookLastPageOptions.configurations[4].title
                ),
                hideTryRead = optionEntity.viewHideOption.bookLastPageOptions.configurations.isEnabled(
                    optionEntity.viewHideOption.bookLastPageOptions.configurations[5].title
                ),
                hideAdView = optionEntity.advOption.configurations.isSelectedByTitle("阅读页-最后一页-中间广告")
            )
        }

        if (optionEntity.replaceRuleOption.enableReplace) {
            enableReplace(versionCode)
        }

        if (optionEntity.startImageOption.enableCustomStartImage) {
            customStartImage(versionCode)
        }

        if (optionEntity.startImageOption.enableCaptureTheOfficialLaunchMapList) {
            captureTheOfficialLaunchMapList(versionCode)
        }

        if (optionEntity.startImageOption.enableCustomLocalStartImage) {
            customLocalStartImage(versionCode)
        }

        if (optionEntity.bookshelfOption.enableCustomBookShelfTopImage) {
            customBookShelfTopImage(versionCode)
        }

        splashPage(
            versionCode = versionCode,
            isEnableSplash = optionEntity.splashOption.enableSplash,
            isEnableCustomSplash = optionEntity.splashOption.enableCustomSplash
        )

        if (optionEntity.shieldOption.shieldOptionValueSet.isNotEmpty()) {
            shieldOption(versionCode, optionEntity.shieldOption.shieldOptionValueSet)
        }

        if (optionEntity.shieldOption.enableQuickShieldDialog) {
            quickShield(versionCode)
        }

        findClass("com.qidian.QDReader.ui.activity.MoreActivity").hook {
            injectMember {
                method {
                    name = "initWidget"
                    emptyParam()
                    returnType = UnitType
                }
                afterHook {
                    safeRun {
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
                }
            }

            injectMember {
                method {
                    name = "onCreate"
                    param(BundleClass)
                }
                afterHook {
                    instance<Activity>().registerModuleAppActivities()
                }
            }
        }
    }

    companion object {

        /**
         * 起点包名
         */
        val QD_PACKAGE_NAME by lazy {
            optionEntity.mainOption.packageName.ifBlank { "com.qidian.QDReader" }
        }

        val versionCode by lazy { getSystemContext().getVersionCode(QD_PACKAGE_NAME) }

        /**
         * 不支持旧版布局的版本号
         */
        const val NOT_SUPPORT_OLD_LAYOUT_VERSION_CODE = 800

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
         * 判断是否启用了屏蔽配置的选项
         * @param optionValue 选项的值
         */
        fun isEnableShieldOption(optionValue: Int) =
            optionValue in optionEntity.shieldOption.shieldOptionValueSet

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

            authorList.takeIf { it.isNotEmpty() }?.let { list ->
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
         * 解析关键词组
         * @param it 关键词组
         */
        fun parseKeyWordOption(it: String = ""): MutableSet<String> =
            it.split(";").filter { it.isNotBlank() }.map { it.replace(Regex(pattern = "\\s+"), "") }
                .toMutableSet()

        /**
         * 解析需要屏蔽的书籍列表
         */
        fun parseNeedShieldList(list: MutableList<*>): List<*> {
            val iterator = list.iterator()
            while (iterator.hasNext()) {
                safeRun {
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
                safeRun {
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
            return needShieldTitleList.map { type.filterValues { it1 -> it1 == it.title }.keys.first() }
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

        val optionEntity by lazy {
            readOptionEntity()
        }

    }

}

fun PackageParam.autoSignIn(
    versionCode: Int, isEnableOldLayout: Boolean = false,
) {
    when {
        versionCode >= NOT_SUPPORT_OLD_LAYOUT_VERSION_CODE -> {
            newAutoSignIn(versionCode)
        }

        else -> {
            if (isEnableOldLayout) {
                oldAutoSignIn(versionCode)
            } else {
                newAutoSignIn(versionCode)
            }
        }
    }

}

/**
 * 老版布局自动签到
 */
@Deprecated("老版布局已经不支持")
fun PackageParam.oldAutoSignIn(versionCode: Int) {
    when (versionCode) {
        in 758..800 -> {
            findClass("com.qidian.QDReader.ui.view.bookshelfview.CheckInReadingTimeView").hook {
                injectMember {
                    method {
                        name = "S"
                    }
                    afterHook {
                        val m = instance.getView<TextView>(
                            "m"
                        )
                        val l = instance.getView<LinearLayout>(
                            "l"
                        )
                        m?.let { mtv ->
                            if (mtv.text == "签到" || mtv.text == "签到领奖") {
                                l?.performClick()
                            }
                        }
                    }
                }
            }
        }

        else -> "自动签到".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 新版布局自动签到
 */
fun PackageParam.newAutoSignIn(versionCode: Int) {
    when (versionCode) {
        in 758..834 -> {
            findClass("com.qidian.QDReader.ui.view.bookshelfview.CheckInReadingTimeViewNew").hook {
                injectMember {
                    method {
                        name = "E"
                    }
                    afterHook {
                        val s = instance.getView<LinearLayout>(
                            "s"
                        )
                        val qd = instance.getParam<Any>(
                            "s"
                        )
                        qd?.let { qdv ->
                            val e = qdv.getView<TextView>(
                                "e"
                            )
                            e?.let { etv ->
                                if (etv.text == "签到") {
                                    s?.performClick()
                                }
                            }
                        }
                    }
                }
            }

            findClass("com.qidian.QDReader.ui.modules.bookshelf.QDBookShelfRebornFragment").hook {
                injectMember {
                    method {
                        name = "getBinding"
                        returnType = "m6.q".toClass()
                    }
                    afterHook {
                        result?.let {
                            val c = it.getParam<AppBarLayout>("c")
                            c?.setVisibilityIfNotEqual()
                            val e = it.getParam<Any>("e")
                            e?.let { it1 ->
                                val binding = it1.getParam<Any>("binding")
                                val d = binding?.getParam<LinearLayout>("d")
                                val e1 = d?.getParam<TextView>("e")
                                e1?.let { tv ->
                                    if (tv.text == "签到") {
                                        d.performClick()
                                    }
                                }/*
                                // 隐藏每日导读方案2
                                val h = binding?.getParam<TextView>("h")
                                val parent = h?.parent as? ViewGroup
                                parent?.visibility = View.GONE

                                 */

                            }

                        }
                    }
                }
            }
        }

        in 842..994 -> {
            val needHookMethod = when (versionCode) {
                in 842..878 -> "E"
                in 884..994 -> "B"
                else -> null
            }
            if (needHookMethod != null) {
                /**
                 * BookShelfCheckIn
                 */
                findClass("com.qidian.QDReader.ui.view.bookshelfview.CheckInReadingTimeViewNew").hook {
                    injectMember {
                        method {
                            name = needHookMethod
                        }
                        afterHook {
                            val qDUIButtons =
                                instance.getViews("com.qd.ui.component.widget.QDUIButton".toClass())
                            qDUIButtons.filter { button ->
                                button.getViews<TextView>()
                                    .any { textView -> textView.text == "签到" }
                            }.forEach { button ->
                                (button as View).performClick()
                            }
                        }
                    }
                }
            }

            findClass("com.qidian.QDReader.ui.modules.bookshelf.view.BookShelfCheckInView").hook {
                injectMember {
                    method {
                        name = "updateCheckIn"
                        paramCount(2)
                        returnType = UnitType
                    }
                    afterHook {
                        val binding = instance.getParam<Any>("binding") ?: return@afterHook
                        val qDUIButtons =
                            binding.getViews("com.qd.ui.component.widget.QDUIButton".toClass())
                        qDUIButtons.filter { button ->
                            button.getViews<TextView>().any { textView -> textView.text == "签到" }
                        }.forEach { button ->
                            (button as View).performClick()
                        }

                        /*
                                        // 隐藏每日导读方案2
                                        val h = binding?.getParam<TextView>("h")
                                        val parent = h?.parent as? ViewGroup
                                        parent?.visibility = View.GONE

                                         */
                    }
                }
            }


        }

        else -> "自动签到".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 新旧布局
 * @param versionCode 版本号
 * @param enableNewUserAccount 是否启用新版我的
 * @param enableNewStore 是否启用新版精选
 */
fun PackageParam.newOldLayout(
    versionCode: Int,
    enableNewUserAccount: Boolean = false,
    enableNewStore: Boolean = false,
    enableNewBookShelfLayout: Boolean = false
) {
    val methodMap = mapOf(
        "needHookClass" to when (versionCode) {
            868 -> "r4.a\$a"
            in 872..878 -> "p4.a\$a"
            in 884..900 -> "l4.search\$search"
            in 906..916 -> "o4.search\$search"
            924 -> "p4.search\$search"
            in 932..938 -> "s4.search\$search"
            in 944..950 -> "r4.search\$search"
            958 -> "p4.search\$search"
            970 -> "o4.search\$search"
            980 -> "com.qidian.QDReader.component.abtest.ABTestConfigHelper\$search"
            else -> null
        }, "needHookNewUserAccountMethod" to when (versionCode) {
            868 -> "n"
            872 -> "m"
            878 -> "o"
            884 -> "m"
            890 -> "n"
            else -> null
        }, "needHookBookStoreV2Method" to when (versionCode) {
            872 -> "d"
            878 -> "e"
            884 -> "b"
            in 890..900 -> "c"
            else -> null
        }, "needHookMethod" to when (versionCode) {
            in 827..850 -> "b"
            in 868..872 -> "c"
            878 -> "d"
            884 -> "a"
            in 890..900 -> "b"
            else -> null
        }, "needHookGDTGameMethod" to when (versionCode) {
            in 896..900 -> "U"
            in 906..916 -> "W"
            924 -> "Y"
            in 932..944 -> "d0"
            in 950..958 -> "e0"
            970 -> "f0"
            in 980..994 -> "i0"
            else -> null
        }
    )


    methodMap["needHookClass"]?.hook {
        if (methodMap["needHookNewUserAccountMethod"] == null) {
            if (versionCode in 868..890) {
                "新版我的布局".printlnNotSupportVersion(versionCode)
            }
        } else {
            injectMember {
                method {
                    name = methodMap["needHookNewUserAccountMethod"]!!
                    emptyParam()
                    returnType = BooleanType
                }
                if (enableNewUserAccount) {
                    replaceToTrue()
                } else {
                    replaceToFalse()
                }
            }

        }

        if (methodMap["needHookBookStoreV2Method"] == null) {
            if (versionCode in 868..900) {
                "新旧精选布局".printlnNotSupportVersion(versionCode)
            }
        } else {
            injectMember {
                method {
                    name = methodMap["needHookBookStoreV2Method"]!!
                    emptyParam()
                    returnType = BooleanType
                }
                if (enableNewStore) {
                    replaceToTrue()
                } else {
                    replaceToFalse()
                }
            }
        }

        if (methodMap["needHookMethod"] == null) {
            if (versionCode in 827..900) {
                "新版书架布局".printlnNotSupportVersion(versionCode)
            }
        } else {
            injectMember {
                method {
                    name = methodMap["needHookMethod"]!!
                    emptyParam()
                    returnType = BooleanType
                }
                if (enableNewBookShelfLayout) {
                    replaceToTrue()
                } else {
                    replaceToFalse()
                }
            }

        }

        if (methodMap["needHookGDTGameMethod"] != null) {
            injectMember {
                method {
                    name = methodMap["needHookGDTGameMethod"]!!
                    emptyParam()
                    returnType = BooleanType
                }
                replaceToFalse()
            }
        }
    }
}

/**
 * Hook 启用本地至尊卡
 */
fun PackageParam.enableLocalCard(versionCode: Int) {
    when (versionCode) {
        in 758..896 -> {

            findClass("com.qidian.QDReader.repository.entity.UserAccountDataBean\$MemberBean").hook {
                injectMember {
                    method {
                        name = "getMemberType"
                    }
                    replaceTo(2)
                }

                injectMember {
                    method {
                        name = "getIsMember"
                    }
                    replaceTo(1)
                }
            }

            findClass("com.qidian.QDReader.repository.entity.config.MemberBean").hook {
                injectMember {
                    method {
                        name = "getMemberType"
                        emptyParam()
                        returnType = IntType
                    }
                    replaceTo(2)
                }

                injectMember {
                    method {
                        name = "isMember"
                        emptyParam()
                        returnType = IntType
                    }
                    replaceTo(1)
                }

                injectMember {
                    method {
                        name = "getExpireTime"
                        returnType = LongType
                    }
                    replaceTo(4102329600000)
                }
            }

            if (versionCode >= 868) {
                findClass("com.qidian.QDReader.repository.entity.user_account.Member").hook {
                    injectMember {
                        method {
                            name = "isMember"
                            emptyParam()
                        }
                        replaceTo(1)
                    }

//                    injectMember {
//                        method {
//                            name = "isAuto"
//                            emptyParam()
//                            returnType = IntType
//                        }
//                        replaceTo(1)
//                    }
                }
            }
        }

        in 896..999 -> {
            findClass("com.qidian.QDReader.repository.entity.user_account.Member").hook {
                injectMember {
                    method {
                        name = "isMember"
                        emptyParam()
                    }
                    replaceTo(1)
                }

//                    injectMember {
//                        method {
//                            name = "isAuto"
//                            emptyParam()
//                            returnType = IntType
//                        }
//                        replaceTo(1)
//                    }
            }

            findClass("com.qidian.QDReader.repository.entity.config.MemberBean").hook {
                injectMember {
                    method {
                        name = "getMemberType"
                        emptyParam()
                        returnType = IntType
                    }
                    replaceTo(1)
                }

                injectMember {
                    method {
                        name = "getExpireTime"
                        emptyParam()
                        returnType = LongType
                    }
                    replaceTo(4102329600000L)
                }
            }

        }

        else -> "启用本地至尊卡".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 解锁会员卡专属背景
 */
fun PackageParam.unlockMemberBackground(versionCode: Int) {
    when (versionCode) {
        in 827..999 -> {
            findClass("com.qidian.QDReader.ui.activity.QDReaderThemeDetailActivity").hook {
                injectMember {
                    method {
                        name = "updateViews"
                        param(ListClass)
                        returnType = UnitType
                    }
                    afterHook {
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
            }
        }

        else -> "解锁会员卡专属背景".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 免广告领取奖励
 */
fun PackageParam.freeAdReward(versionCode: Int) {
    when (versionCode) {
        in 854..896 -> {
            findClass("com.qq.e.comm.managers.plugin.PM").hook {
                injectMember {
                    method {
                        name = "getPluginClassLoader"
                        emptyParam()
                        returnType = "java.lang.ClassLoader".toClass()
                    }
                    afterHook {
                        val classLoader = result.safeCast<ClassLoader>()
                        classLoader?.let {
                            /**
                             * com.qq.e.comm.plugin.tangramrewardvideo.g.K() : void
                             *  int v = this.p.aQ()
                             */
                            findClass(
                                "com.qq.e.comm.plugin.tangramrewardvideo.c.b", it
                            ).hook {
                                injectMember {
                                    method {
                                        name = "p"
                                        emptyParam()
                                        returnType = IntType
                                    }
                                    replaceTo(0)
                                }
                            }

                            /**
                             * com.qq.e.comm.plugin.tangramrewardvideo.f.onAfterCreate
                             */
                            findClass("com.qq.e.comm.plugin.tangramrewardvideo.f", it).hook {
                                injectMember {
                                    method {
                                        name = "ao"
                                        emptyParam()
                                        returnType = UnitType
                                    }
                                    afterHook {

                                        val r = instance.getParam<Any>("R")
                                        val g = r?.getParam<Any>("g")
                                        val d = g?.getView<ImageView>("d")

                                        d?.postDelayed(
                                            {
                                                instance.current {
                                                    method {
                                                        name = "s"
                                                        emptyParam()
                                                    }.call()

                                                    method {
                                                        name = "onBackPressed"
                                                        emptyParam()
                                                    }.call()
                                                }
                                            },
                                            (optionEntity.mainOption.freeAdRewardAutoExitTime * 1000).toLong()
                                        )
                                    }
                                }
                            }

                            findClass(
                                "com.qq.e.comm.plugin.tangramrewardvideo.widget.j", it
                            ).hook {
                                injectMember {
                                    method {
                                        name = "b"
                                        paramCount(1)
                                        returnType = UnitType
                                    }
                                    afterHook {
                                        val h = instance.getParam<TextView>("h")
                                        h?.let { tvh ->
                                            if (tvh.text == "跳过视频") {
                                                tvh.performClick()
                                            }
                                        }
                                    }
                                }
                            }

                            /*
                            findClass("com.qq.e.comm.plugin.webview.a.v", it).hook {
                                injectMember {
                                    method {
                                        name = "handleAction"
                                        paramCount(6)
                                        returnType =
                                            "com.qq.e.comm.plugin.webview.a.j".toClass(it)
                                    }
                                    afterHook {
                                        instance.current {
                                            method {
                                                name = "handleAction"
                                                paramCount(6)
                                            }.call(
                                                args[0],
                                                args[1],
                                                args[2],
                                                "onVideoClose",
                                                args[4],
                                                args[5]
                                            )
                                        }
                                    }
                                }
                            }

                             */

                            findClass(
                                "com.qq.e.comm.plugin.tangramrewardvideo.c.a", it
                            ).hook {
                                injectMember {
                                    method {
                                        name = "aQ"
                                        emptyParam()
                                        returnType = IntType
                                    }
                                    replaceTo(-0x64)
                                }
                            }

                            /*
                            findClass("com.qq.e.comm.plugin.i.e", it).hook {
                                injectMember {
                                    method {
                                        name = "a"
                                        param(StringClass, StringClass, IntType)
                                        returnType = IntType
                                    }
                                    afterHook {
                                        args(2).set(0)
                                    }
                                }

                            }

                             */

                            findClass(
                                "com.qq.e.comm.plugin.tangramrewardvideo.g", it
                            ).hook {
                                injectMember {
                                    method {
                                        name = "L"
                                        emptyParam()
                                        returnType = IntType
                                    }
                                    replaceTo(0)
                                }
                            }


                        }
                    }
                }
            }

            findClass("com.qq.e.comm.managers.setting.SM").hook {
                injectMember {
                    method {
                        name = "getInteger"
                        param(StringClass, IntType)
                        returnType = IntType
                    }
                    afterHook {
                        args(1).set(0)
                    }
                }
            }
        }

        in 896..994 -> {
            /**
             * showRewardVideo
             * preloadRewardVideo
             */
            findClass("com.qidian.QDReader.framework.webview.g").hook {
                injectMember {
                    method {
                        name = "judian"
                        paramCount(3)
                        returnType = UnitType
                    }
                    beforeHook {
                        args(1).set(JSONObject("{\"status\":2}"))
                    }
                }
            }

            findClass("com.qidian.QDReader.repository.entity.Reward").hook {
                injectMember {
                    method {
                        name = "getRewardId"
                        emptyParam()
                        returnType = LongType
                    }
                    replaceTo(1L)
                }
            }

            findClass("com.qidian.QDReader.ui.modules.interact.InteractHBContainerView").hook {
                injectMember {
                    method {
                        name = "showGdtAD\$lambda-20\$lambda-19"
                        paramCount(3)
                        returnType = UnitType
                    }
                    beforeHook {
                        args(2).set(5)
                    }
                }
            }

            findClass("com.qq.e.tg.ADActivity").hook {
                injectMember {
                    method {
                        name = "onCreate"
                        param(BundleClass)
                        returnType = UnitType
                    }
                    beforeHook {
                        instance<Activity>().finish()
                    }
                }
            }
        }

        else -> "免广告领取奖励".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 忽略限时免费不能批量订阅限制
 * IsFreeLimit
 */
fun PackageParam.ignoreFreeSubscribeLimit(versionCode: Int) {
    val classMap = mapOf(
        "needHookClass" to when (versionCode) {
            in 854..878 -> "com.qidian.QDReader.component.bll.manager.e1"
            in 884..900 -> "com.qidian.QDReader.component.bll.manager.b1"
            906 -> "com.qidian.QDReader.component.bll.manager.y0"
            916 -> "com.qidian.QDReader.component.bll.manager.z0"
            924 -> "com.qidian.QDReader.component.bll.manager.a1"
            in 932..944 -> "com.qidian.QDReader.component.bll.manager.c1"
            950 -> "com.qidian.QDReader.component.bll.manager.b1"
            in 958..994 -> "com.qidian.QDReader.component.bll.manager.d1"
            else -> null
        }, "needHookMethod" to when (versionCode) {
            in 854..878 -> "n0"
            in 884..890 -> "k0"
            in 896..924 -> "l0"
            in 932..938 -> "p0"
            in 944..958 -> "q0"
            in 970..994 -> "r0"
            else -> null
        }
    )

    if (classMap["needHookClass"] == null || classMap["needHookMethod"] == null) {
        "忽略限时免费批量订阅限制".printlnNotSupportVersion(versionCode)
        return
    }
    classMap["needHookClass"]?.hook {
        injectMember {
            method {
                name = classMap["needHookMethod"]!!
                param(
                    "com.qidian.QDReader.framework.network.qd.QDHttpResp".toClass(),
                    JSONObjectClass,
                    LongType
                )
                returnType = IntType
            }
            beforeHook {
                val jb = args[1].safeCast<JSONObject>()
                safeRun {
                    jb?.optJSONObject("Data")?.put("IsFreeLimit", -1)
                    args(1).set(jb)
                }
            }
        }
    }
}

/**
 * 一键导出表情包
 */
fun PackageParam.exportEmoji(versionCode: Int) {
    when (versionCode) {
        in 884..994 -> {
            findClass("com.qidian.QDReader.ui.activity.QDStickersDetailActivity").hook {
                injectMember {
                    method {
                        name = "loadData\$lambda-5"
                        paramCount(2)
                        returnType = UnitType
                    }
                    afterHook {
                        val stickersBean = args[1] ?: return@afterHook
                        val faceList = stickersBean.getParam<MutableList<*>>("mFaceList")
                        val yWImageLoader =
                            "com.yuewen.component.imageloader.YWImageLoader".toClassOrNull()
                        val context = args[0] ?: return@afterHook
                        if (faceList.isNullOrEmpty() || yWImageLoader == null) {
                            return@afterHook
                        }
                        val imageList = mutableListOf<String>()
                        val iterator = faceList.iterator()
                        while (iterator.hasNext()) {
                            val image = iterator.next()?.getParam<String>("mImage")
                            if (!image.isNullOrBlank()) {
                                imageList += image
                            }
                        }
                        val topBarId = when (versionCode) {
                            884 -> 0x7F09176F
                            890 -> 0x7F091784
                            in 896..900 -> 0x7F091789
                            in 906..916 -> 0x7F0917F5
                            924 -> 0x7F0917F6
                            932 -> 0x7F09184E
                            938 -> 0x7F091863
                            944 -> 0x7F091876
                            950 -> 0x7F0918A1
                            958 -> 0x7F0918A8
                            970 -> 0x7F091931
                            980 -> 0x7F091970
                            994 -> 0x7F09199A
                            else -> null
                        }
                        if (topBarId != null) {
                            val topBar = context.findViewById<RelativeLayout>(topBarId)
                            if (topBar != null) {
                                val layoutParams = RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                                ).apply {
                                    addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                                    addRule(RelativeLayout.CENTER_VERTICAL)
                                    // padding right
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
                            }
                        } else {
                            "一键导出表情包".printlnNotSupportVersion(versionCode)
                        }
                    }
                }
            }
        }

        else -> "一键导出表情包".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 导出表情包对话框
 */
fun Context.exportEmojiDialog(
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
 * 试用模式弹框
 */
fun PackageParam.forceTrialMode(versionCode: Int) {
    val needHookClass = when (versionCode) {
        in 896..900 -> "com.qidian.QDReader.util.v4"
        in 906..970 -> "com.qidian.QDReader.util.w4"
        in 980..994 -> "com.qidian.QDReader.util.u4"
        else -> null
    }

    /**
     * tryuse
     * is_agree_privacy
     */
    val needHookMethod = when (versionCode) {
        in 896..970 -> "M"
        in 980..994 -> "L"
        else -> null
    }

    needHookClass?.hook {
        injectMember {
            method {
                name = needHookMethod!!
                paramCount(1)
                returnType = BooleanType
            }
            replaceToFalse()
        }
    } ?: "试用模式".printlnNotSupportVersion(versionCode)

}

/**
 * 隐藏福利列表
 */
fun PackageParam.hideWelfare(versionCode: Int) {
    when (versionCode) {
        in 906..999 -> {
            findClass("com.qidian.QDReader.ui.activity.QDSearchActivity").hook {
                injectMember {
                    method {
                        name = "getOperateKey"
                        paramCount(1)
                    }
                    afterHook {
                        val list = instance.getParam<CopyOnWriteArrayList<*>>("operateKeys")
                            ?: return@afterHook
                        if (list.isEmpty()) {
                            return@afterHook
                        }
                        val hideWriteArrayList =
                            mutableSetOf<OptionEntity.HideWelfareOption.HideWelfare>()
                        val iterator = list.iterator()
                        while (iterator.hasNext()) {
                            val adItem = iterator.next()
                            val adImage = adItem?.getParam<String>("ADImage")
                            val adText = adItem?.getParam<String>("ADText")
                            val actionUrl = adItem?.getParam<String>("ActionUrl")
                            if (!adImage.isNullOrBlank() && !adText.isNullOrBlank() && !actionUrl.isNullOrBlank()) {
                                hideWriteArrayList += OptionEntity.HideWelfareOption.HideWelfare(
                                    title = adText, imageUrl = adImage, actionUrl = actionUrl
                                )
                            }
                        }
                        if (hideWriteArrayList.isNotEmpty()) {
                            optionEntity.hideBenefitsOption.hideWelfareList.clear()
                            optionEntity.hideBenefitsOption.hideWelfareList.addAll(
                                hideWriteArrayList
                            )
                            updateOptionEntity()
                        }
                    }
                }
            }

            if (optionEntity.hideBenefitsOption.configurations[0].selected) {
                findClass("com.qidian.QDReader.ui.fragment.QDBrowserFragment").hook {
                    injectMember {
                        method {
                            name = "showMore"
                            paramCount(2)
                            returnType = UnitType
                        }
                        beforeHook {
                            val hideWelfareList = optionEntity.hideBenefitsOption.hideWelfareList
                            if (hideWelfareList.isEmpty()) {
                                return@beforeHook
                            }
                            val jsonArray = JSONArray()
                            hideWelfareList.forEachIndexed { index, hideWelfare ->
                                jsonArray.put(index, JSONObject().apply {
                                    put("icon", hideWelfare.imageUrl)
                                    put("text", hideWelfare.title)
                                    put("url", hideWelfare.actionUrl)
                                })
                            }
                            args(0).set((args[0] as JSONObject).put("otherItems", jsonArray))
                        }
                    }
                }
            }
        }

        else -> "隐藏福利列表".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 自动领取阅读积分
 */
fun PackageParam.receiveReadingCreditsAutomatically(versionCode: Int) {
    when (versionCode) {
        in 970..994 -> {
            /**
             * 自动领取今日阅读时长积分
             */
            findClass("com.qidian.QDReader.ui.view.readtime.ReadTimeTodayCardView").hook {
                injectMember {
                    method {
                        name = "addBubbleView"
                        paramCount(4)
                        returnType = UnitType
                    }
                    afterHook {
                        val bubbleViewMap = instance.getParam<HashMap<*, *>>("bubbleViewMap")
                        bubbleViewMap?.values?.forEach { view ->
                            view.safeCast<LinearLayout>()?.postRandomDelay { performClick() }
                        }
                    }
                }
            }

            /**
             * 自动领取每周阅读时长宝箱
             */
            findClass("com.qidian.QDReader.ui.view.readtime.ReadTimeWeekCardView").hook {
                injectMember {
                    method {
                        name = "bindBox"
                        paramCount(2)
                        returnType = IntType
                    }
                    afterHook {
                        args[1].safeCast<List<*>>() ?: return@afterHook

                        val pBarTagContainerId = when (versionCode) {
                            970 -> 0x7F091391
                            980 -> 0x7F0913D0
                            994 -> 0x7F0913F6
                            else -> null
                        }
                        if (pBarTagContainerId == null) {
                            "自动领取每周阅读时长宝箱".printlnNotSupportVersion(HookEntry.versionCode)
                            return@afterHook
                        }
                        val view = instanceClass.method {
                            name = "_\$_findCachedViewById"
                            paramCount(1)
                            returnType = ViewClass
                        }.get(instance).call(pBarTagContainerId).safeCast<FrameLayout>()

                        view?.let {
                            val count = it.childCount
                            for (i in 0..<count) {
                                val child = it.getChildAt(i)
                                if (child is LinearLayout) {
                                    child.postRandomDelay { performClick() }
                                }
                            }
                        }
                    }
                }
            }

            /**
             * 自动领取每日任务奖励
             */
            findClass("com.qidian.QDReader.ui.activity.ReadTimeMainPageActivity").hook {
                injectMember {
                    method {
                        name = "changeSubmitStatus"
                        paramCount(2)
                        returnType = UnitType
                    }
                    afterHook {
                        val button = args[0].safeCast<LinearLayout>() ?: return@afterHook
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
                }
            }

            /**
             * 自动领取(开始)阅读PK场
             */
            findClass("com.qidian.QDReader.ui.view.readtime.ReadTimePKCardView").hook {
                injectMember {
                    method {
                        name = "bindData"
                        paramCount(1)
                        returnType = UnitType
                    }
                    afterHook {
                        val button = instanceClass.method {
                            name = "getQdButtonBottom"
                            emptyParam()
                            returnType = "com.qd.ui.component.widget.QDUIButton".toClass()
                        }.get(instance).call().safeCast<LinearLayout>() ?: return@afterHook

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
            }

        }

        else -> "自动领取阅读积分".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 发帖显示图片直链
 * CirclePostEditActivity
 * showUpLoadingDialog
 */
fun PackageParam.postToShowImageUrl(versionCode: Int) {
    val needHookClass = when (versionCode) {
        970 -> "com.qidian.QDReader.ui.dialog.h9"
        980 -> "com.qidian.QDReader.ui.dialog.i9"
        994 -> "com.qidian.QDReader.ui.dialog.k9"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 970..994 -> "p"
        else -> null
    }
    if (needHookClass == null || needHookMethod == null) {
        "发帖显示图片直链".printlnNotSupportVersion(versionCode)
        return
    }

    needHookClass.hook {
        injectMember {
            method {
                name = needHookMethod
                emptyParam()
                returnType = UnitType
            }
            afterHook {
                val lists = instance.getParamList<List<*>>().takeUnless { it.isEmpty() }
                    ?.filterNot { it[0] is String }
                lists?.firstOrNull()?.let { urlList ->
                    urlList.mapNotNull { it?.getParam<String>("mAccessUrl") }.let { accessUrls ->
                            instance.getViews<TextView>().firstNotNullOfOrNull { it.context }
                                ?.showUrlListDialog(accessUrls)
                        }
                }
            }
        }
    }
}

/**
 * 显示直链地址对话框
 */
fun Context.showUrlListDialog(urls: List<String>) {
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
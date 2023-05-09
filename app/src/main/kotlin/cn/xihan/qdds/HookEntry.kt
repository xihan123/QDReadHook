package cn.xihan.qdds

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.view.View
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
import java.io.File
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

            if (optionEntity.mainOption.enableAutoSign) {
                autoSignIn(versionCode, optionEntity.bookshelfOption.enableOldLayout)
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

            if (optionEntity.mainOption.enableImportAudio) {
                importAudio(versionCode)
            }

            if (optionEntity.mainOption.enableForceTrialMode) {
                forceTrialMode(versionCode)
            }

            if (optionEntity.hideBenefitsOption.enableHideWelfare){
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

            if (optionEntity.advOption.advOptionSelectedList.isNotEmpty()) {
                advOption(versionCode, optionEntity.advOption.advOptionSelectedList)
            }

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
                bookDetailHide(versionCode)
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
                    hideAdView = isEnableAdvOption(16)
                )
            }

            if (optionEntity.replaceRuleOption.enableReplace) {
                enableReplace(versionCode)
            }

            customDeviceInfo(
                versionCode = versionCode,
                deviceInfoModel = optionEntity.replaceRuleOption.customDeviceInfo,
                enableCustomDeviceInfo = optionEntity.replaceRuleOption.enableCustomDeviceInfo,
                enableSaveOriginalDeviceInfo = optionEntity.replaceRuleOption.enableSaveOriginalDeviceInfo
            )

            if (optionEntity.startImageOption.enableCustomStartImage) {
                customStartImage(versionCode)
            }

            if (optionEntity.startImageOption.enableCaptureTheOfficialLaunchMapList) {
                captureTheOfficialLaunchMapList(versionCode)
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
                                    param(ContextClass, StringType, JSONObjectClass)
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
                                    param(ContextClass, StringType, JSONObjectClass)
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

            findClass("com.qidian.QDReader.ui.activity.MoreActivity").hook {
                injectMember {
                    method {
                        name = "initWidget"
                        emptyParam()
                        returnType = UnitType
                    }
                    afterHook {
                        safeRun {
                            val readMoreSetting =
                                instance.getView<RelativeLayout>("readMoreSetting")
                            // 获取 readMoreSetting 子控件
                            val readMoreSettingChild = readMoreSetting?.getChildAt(0) as? TextView
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

            /*

            findMethodAndPrint("com.qidian.QDReader.extras.x", false, MembersType.METHOD)

            findMethodAndPrint("com.qidian.QDReader.extras.y", false, MembersType.METHOD)

            findMethodAndPrint("ib.m0", false, MembersType.METHOD)

            findMethodAndPrint("com.qidian.QDReader.core.util.m", false, MembersType.METHOD)

            findMethodAndPrint("de.a",false, MembersType.METHOD)

            findMethodAndPrint("ie.a",false, MembersType.METHOD)

            findMethodAndPrint("ke.cihai",false, MembersType.METHOD)

            findMethodAndPrint("ke.a",false, MembersType.METHOD)

            findClass("java.net.HttpURLConnection").hook {
                injectMember {
                    allMembers(MembersType.CONSTRUCTOR)
                    beforeHook {
                        "url: ${args[0]}".loge()
//                        instance.printCallStack()
                    }
                }
            }

             */


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
         * 判断是否启用了广告配置的选项
         */
        fun isEnableAdvOption(optionValue: Int) =
            optionValue in optionEntity.advOption.advOptionSelectedList

        /**
         * 判断是否需要屏蔽
         * @param bookName 书名-可空
         * @param authorName 作者名-可空
         * @param bookType 书类型-可空
         */
        fun isNeedShield(
            bookName: String? = null,
            authorName: String? = null,
            bookType: Set<String>? = null,
        ): Boolean {/*
            if (BuildConfig.DEBUG) {
                loggerE(msg = "bookName: $bookName\nauthorName:$authorName\nbookType:$bookType")
            }
             */
            if (bookNameList.isNotEmpty()) {
                if (!bookName.isNullOrBlank() && bookNameList.any { it in bookName }) {
                    return true
                }
            }
            if (authorList.isNotEmpty()) {
                if (!authorName.isNullOrBlank() && authorList.any { authorName == it }) {
                    return true
                }
            }
            if (bookTypeList.isNotEmpty() && !bookType.isNullOrEmpty()) {
                if (optionEntity.shieldOption.enableBookTypeEnhancedBlocking) {
                    if (bookType.isNotEmpty() && bookType.any { bookTypeList.any { it1 -> it1 in it } }) {
                        return true
                    }
                } else {
                    if (bookType.isNotEmpty() && bookType.any { it in bookTypeList }) {
                        return true
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
                val item = iterator.next().toJSONString()
                val jb = item.parseObject()
                val bookName =
                    jb.getString("BookName") ?: jb.getString("bookName") ?: jb.getString("itemName")
                    ?: jb.getString("ItemName")
                val authorName = jb.getString("AuthorName") ?: jb.getString("authorName")
                val categoryName = jb.getString("CategoryName") ?: jb.getString("categoryName")
                val subCategoryName =
                    jb.getString("SubCategoryName") ?: jb.getString("subCategoryName")
                    ?: jb.getString("itemSubName") ?: jb.getString("ItemSubName")
                val tagName = jb.getString("TagName") ?: jb.getString("tagName")
                val array = jb.getJSONArray("AuthorTags") ?: jb.getJSONArray("tags")
                ?: jb.getJSONArray("Tags") ?: jb.getJSONArray("tagList")
                val tip = jb.getString("Tip") ?: jb.getString("tip")
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
                            val tags = tag.parseObject()
                            tags?.getString("tagName")?.let {
                                bookTypeArray += it
                            }
                            tags?.getString("TagName")?.let {
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
            return list
        }

        /**
         * 解析需要屏蔽的漫画列表
         */
        fun parseNeedShieldComicList(list: MutableList<*>): List<*> {
            val iterator = list.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next().toJSONString()
                val jb = item.parseObject()
                val comicName = jb.getString("ComicName") ?: jb.getString("comicName")
                val authorName = jb.getString("AuthorName") ?: jb.getString("authorName")
                ?: jb.getString("Author")
                val categoryName = jb.getString("CategoryName") ?: jb.getString("categoryName")
                val subCategoryName =
                    jb.getString("SubCategoryName") ?: jb.getString("subCategoryName")
                val tagName = jb.getString("TagName") ?: jb.getString("tagName")
                val extraTag = jb.getString("ExtraTag") ?: jb.getString("extraTag")
                val array = jb.getJSONArray("AuthorTags") ?: jb.getJSONArray("tags")
                ?: jb.getJSONArray("Tags") ?: jb.getJSONArray("tagList")
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

        val optionEntity = readOptionEntity()

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
                            c?.visibility = View.GONE
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

        in 842..906 -> {
            val needHookMethod = when (versionCode) {
                in 842..878 -> "E"
                in 884..906 -> "B"
                else -> null
            }
            val needHookVariableName1 = versionCode.QDUIButtonTextViewVariableName
            if (needHookMethod != null && needHookVariableName1 != null) {
                /**
                 * BookShelfCheckIn
                 */
                findClass("com.qidian.QDReader.ui.view.bookshelfview.CheckInReadingTimeViewNew").hook {
                    injectMember {
                        method {
                            name = needHookMethod
                        }
                        afterHook {
                            val v = instance.getView<LinearLayout>(
                                "v"
                            )
                            v?.let { qdv ->
                                val e = qdv.getView<TextView>(needHookVariableName1)
                                e?.let { etv ->
                                    if (etv.text == "签到") {
                                        v.performClick()
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 需要Hook的变量名
            val needHookVariableName = when (versionCode) {
                in 884..890 -> "a"
                in 896..906 -> "b"
                else -> null
            }
            if (needHookVariableName != null && needHookVariableName1 != null) {
                findClass("com.qidian.QDReader.ui.modules.bookshelf.view.BookShelfCheckInView").hook {
                    injectMember {
                        method {
                            name = "updateCheckIn"
                            paramCount(2)
                            returnType = UnitType
                        }
                        afterHook {
                            val binding = instance.getParam<Any>("binding")
                            val d = binding?.getParam<LinearLayout>(needHookVariableName)
                            val e1 = d?.getParam<TextView>(needHookVariableName1)
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
            } else {
                "新版书架自动签到".printlnNotSupportVersion(versionCode)
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
    val needHookClass = when (versionCode) {
        868 -> "r4.a\$a"
        in 872..878 -> "p4.a\$a"
        in 884..900 -> "l4.search\$search"
        906 -> "o4.search\$search"
        else -> null
    }

    /**
     * NEW_USER_ACCOUNT
     */
    val needHookNewUserAccountMethod = when (versionCode) {
        868 -> "n"
        872 -> "m"
        878 -> "o"
        884 -> "m"
        890 -> "n"
        else -> null
    }

    /**
     * BOOK_STORE_V2
     */
    val needHookBookStoreV2Method = when (versionCode) {
        872 -> "d"
        878 -> "e"
        884 -> "b"
        in 890..900 -> "c"
        else -> null
    }

    /**
     * 新版书架布局
     * 上级调用: com.qidian.QDReader.ui.activity.MainGroupActivity.onCreate
     * mFragmentPagerAdapter
     * BOOK_SHELF_REBORN
     */
    val needHookMethod = when (versionCode) {
        in 827..850 -> "b"
        in 868..872 -> "c"
        878 -> "d"
        884 -> "a"
        in 890..900 -> "b"
        else -> null
    }

    /**
     * gdt game
     */
    val needHookGDTGameMethod = when (versionCode) {
        in 896..900 -> "U"
        906 -> "W"
        else -> null
    }

    if (needHookClass == null) {
        "新旧布局".printlnNotSupportVersion(versionCode)
        return
    }
    needHookClass.hook {
        if (needHookNewUserAccountMethod == null) {
            if (versionCode in 868..890) {
                "新版我的布局".printlnNotSupportVersion(versionCode)
            }
        } else {
            injectMember {
                method {
                    name = needHookNewUserAccountMethod
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

        if (needHookBookStoreV2Method == null) {
            if (versionCode in 868..900) {
                "新旧精选布局".printlnNotSupportVersion(versionCode)
            }
        } else {
            injectMember {
                method {
                    name = needHookBookStoreV2Method
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

        if (needHookMethod == null) {
            if (versionCode in 827..900) {
                "新版书架布局".printlnNotSupportVersion(versionCode)
            }
        } else {
            injectMember {
                method {
                    name = needHookMethod
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

        if (needHookGDTGameMethod != null) {
            injectMember {
                method {
                    name = needHookGDTGameMethod
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

        in 896..950 -> {
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
        in 827..950 -> {
            findClass("com.qidian.QDReader.ui.activity.QDReaderThemeDetailActivity").hook {
                injectMember {
                    method {
                        name = "updateViews"
                        param(ListClass)
                        returnType = UnitType
                    }
                    afterHook {
                        val list = args[0] as? MutableList<*>
                        list?.forEach {
                            it?.let {
                                if (it::class.java.name == "com.qidian.QDReader.repository.dal.store.ReaderThemeEntity") {
                                    val themeType = it.getParam<Long>("themeType")
                                    if (themeType == 102L) {
                                        XposedHelpers.setLongField(it, "themeType", 101)
                                        XposedHelpers.setIntField(it, "haveStatus", 1)
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
                        val classLoader = result as? ClassLoader
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

        in 896..906 -> {
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
    val needHookClass = when (versionCode) {
        in 854..878 -> "com.qidian.QDReader.component.bll.manager.e1"
        in 884..900 -> "com.qidian.QDReader.component.bll.manager.b1"
        906 -> "com.qidian.QDReader.component.bll.manager.y0"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 854..878 -> "n0"
        in 884..890 -> "k0"
        in 896..906 -> "l0"
        else -> null
    }
    if (needHookClass == null || needHookMethod == null) {
        "忽略限时免费批量订阅限制".printlnNotSupportVersion(versionCode)
        return
    }
    needHookClass.hook {
        injectMember {
            method {
                name = needHookMethod
                param(
                    "com.qidian.QDReader.framework.network.qd.QDHttpResp".toClass(),
                    JSONObjectClass,
                    LongType
                )
                returnType = IntType
            }
            beforeHook {
                val jb = args[1] as? JSONObject
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
        in 884..906 -> {
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
                        val topBarViewId = when (versionCode) {
                            884 -> 0x7F09176F
                            890 -> 0x7F091784
                            in 896..900 -> 0x7F091789
                            906 -> 0x7F0917F5
                            else -> null
                        }
                        if (topBarViewId != null) {
                            val topBar = XposedHelpers.callMethod(
                                context, "findViewById", topBarViewId
                            ) as? RelativeLayout
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
 * 导入音频文件
 */
fun PackageParam.importAudio(versionCode: Int) {
    when (versionCode) {
        in 884..906 -> {
            findClass("com.qidian.QDReader.ui.fragment.reader.ParagraphDubbingFragment").hook {
                injectMember {
                    method {
                        name = "onViewInject"
                        param(ViewClass)
                        returnType = UnitType
                    }
                    afterHook {
                        val view = instanceClass.method {
                            name = "getView"
                            emptyParam()
                            returnType = ViewClass
                            superClass()
                        }.get(instance).call()
                        val mBtnStartViewId = when (versionCode) {
                            884 -> 0x7F090FFC
                            in 890..900 -> 0x7F091008
                            906 -> 0x7F091055
                            else -> null
                        } ?: return@afterHook
                        val button = XposedHelpers.callMethod(
                            view, "findViewById", mBtnStartViewId
                        ) as? LinearLayout
                        val qDUIButtonTextViewVariableName =
                            versionCode.QDUIButtonTextViewVariableName
                        if (qDUIButtonTextViewVariableName == null) {
                            "导入音频".printlnNotSupportVersion(versionCode)
                            return@afterHook
                        }
                        val e = button?.getView<TextView>(qDUIButtonTextViewVariableName)
                        if (e?.text == "点击配音") {
                            e.text = "点击配音/长按导入"
                            e.setOnLongClickListener {
                                e.context.importAudioFile { path ->
                                    if (path.isBlank()) {
                                        e.context.toast("路径不能为空")
                                    } else {
                                        instance.setParams(
                                            "mAudioFile" to File(path), "mAudioDuration" to 1
                                        )
                                        instanceClass.method {
                                            name = "setViewInRecorded"
                                            emptyParam()
                                            returnType = UnitType
                                        }.get(instance).call()
                                    }
                                }

                                true
                            }
                        }
                    }

                }
            }
        }

        else -> "导入音频文件".printlnNotSupportVersion(versionCode)

    }
}

/**
 * 导入音频文件
 */
fun Context.importAudioFile(action: (String) -> Unit) {
    val path =
        "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/QDReader/Audio"
    val file = File(path).apply {
        if (!exists()) {
            mkdirs()
        }
    }
    val files = file.listFiles()

    if (!files.isNullOrEmpty()) {
        singleChoiceSelector(items = files.map { it.name },
            checkIndex = 0,
            title = "导入配音固定路径为: $path",
            onItemSelected = { _, _, index ->
//            "onItemSelected: $it, $text, $index".loge()
                action(files[index].absolutePath)
            })
    } else {
        toast("固定路径为: $path, 但是没有文件")
    }

}

/**
 * 试用模式弹框
 */
fun PackageParam.forceTrialMode(versionCode: Int) {
    val needHookClass = when (versionCode) {
        in 896..900 -> "com.qidian.QDReader.util.v4"
        906 -> "com.qidian.QDReader.util.w4"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 896..906 -> "M"
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
fun PackageParam.hideWelfare(versionCode: Int){
    when(versionCode){
        906 -> {
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
                                    title = adText,
                                    imageUrl = adImage,
                                    actionUrl = actionUrl
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

            if (optionEntity.hideBenefitsOption.configurations[0].selected){
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
                                jsonArray.put(
                                    index,
                                    JSONObject().apply {
                                        put("icon", hideWelfare.imageUrl)
                                        put("text", hideWelfare.title)
                                        put("url", hideWelfare.actionUrl)
                                    })
                            }
                            args(0).set((args[0] as JSONObject).put("otherItems", jsonArray))
                        }
                    }

                    /*
                    injectMember {
                        method {
                            name = "lambda\$showMoreDialog\$10"
                            returnType = UnitType
                        }
                        beforeHook {
                            val args4 = args[4] ?: return@beforeHook
                            if ("com.qidian.QDReader.repository.entity.ShareMoreItem" == args4.javaClass.name) {
                                val iconUrl = args4.getParam<String>("iconUrl") ?: args4.getParam<String>("IconUrl")
                                val actionUrl = args4.getParam<String>("actionUrl") ?: args4.getParam<String>("ActionUrl")
                                val title = args4.getParam<String>("title") ?: args4.getParam<String>("Title")
                                "title: $title".loge()
                            }
                        }
                    }

                     */
                }
            }
        }

        else -> "隐藏福利列表".printlnNotSupportVersion(versionCode)
    }
}
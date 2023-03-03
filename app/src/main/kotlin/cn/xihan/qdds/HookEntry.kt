package cn.xihan.qdds

import android.app.Activity
import android.content.Intent
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
import com.highcapable.yukihookapi.hook.factory.registerModuleAppActivities
import com.highcapable.yukihookapi.hook.log.YukiHookLogger
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.JSONObjectClass
import com.highcapable.yukihookapi.hook.type.java.ListClass
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import de.robv.android.xposed.XposedHelpers
import org.json.JSONObject

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

            if (optionEntity.mainOption.enableIgnoreFansValueJumpLimit) {
                ignoreFansValueJumpLimit(versionCode)
            }

            if (optionEntity.mainOption.enableIgnoreFreeSubscribeLimit) {
                ignoreFreeSubscribeLimit(versionCode)
            }

            if (optionEntity.bookshelfOption.enableOldLayout && versionCode < NOT_SUPPORT_OLD_LAYOUT_VERSION_CODE) {
                enableOldLayout(versionCode)
            }

            newBookShelfLayout(versionCode, optionEntity.bookshelfOption.enableNewBookShelfLayout)

            readerPageChapterReviewPictures(
                versionCode = versionCode,
                enableShowReaderPageChapterSaveRawPictures = optionEntity.readPageOption.enableShowReaderPageChapterSaveRawPicture,
                enableShowReaderPageChapterSavePictureDialog = optionEntity.readPageOption.enableShowReaderPageChapterSavePictureDialog
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
                    enableNewStore = optionEntity.mainOption.enableNewStore
                )
            }

            if (optionEntity.viewHideOption.bookDetailOptions.enableHideBookDetail) {
                bookDetailHide(versionCode)
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

            if (optionEntity.bookFansValueOption.enableCustomBookFansValue) {
                customBookFansValue(versionCode)
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
                                    startActivity(Intent(this, MainActivity::class.java))/*
                                    safeRun {
                                        val linearLayout = CustomLinearLayout(this)
                                        val mainOptionTextView = CustomTextView(
                                            context = this, mText = "主设置", isBold = true
                                        ) {
                                            showMainOptionDialog()
                                        }
                                        val advOptionTextView = CustomTextView(
                                            context = this, mText = "广告相关设置", isBold = true
                                        ) {
                                            showAdvOptionDialog()
                                        }
                                        val shieldOptionTextView = CustomTextView(
                                            context = this, mText = "屏蔽相关设置", isBold = true
                                        ) {
                                            showShieldOptionDialog()
                                        }
                                        val splashOptionTextView = CustomTextView(
                                            context = this, mText = "闪屏相关设置", isBold = true
                                        ) {
                                            showSplashOptionDialog()
                                        }
                                        val viewHideOptionTextView = CustomTextView(
                                            context = this,
                                            mText = "隐藏控件相关设置",
                                            isBold = true
                                        ) {
                                            showHideOptionDialog()
                                        }
                                        val replaceOptionTextView = CustomTextView(
                                            context = this,
                                            mText = "替换相关设置",
                                            isBold = true
                                        ) {
                                            showReplaceOptionDialog()
                                        }
                                        val openSourceryOptionTextView = CustomTextView(
                                            context = this,
                                            mText = "开源地址及详细说明",
                                            isBold = true
                                        ) {
                                            val intent = Intent(Intent.ACTION_VIEW)
                                            intent.data =
                                                Uri.parse("https://github.com/xihan123/QDReadHook")
                                            startActivity(intent)
                                        }

                                        val explainTextView = CustomTextView(
                                            context = this,
                                            mText = "此软件仅限用于学习和研究,不得用于其他用途,否则后果自负.\nQD模块交流群: 727983520\n如果你喜欢该模块可以打赏或给此项目点个star,谢谢~".replaceSpan(
                                                "打赏",
                                                replacement = {
                                                    MyClickableSpan {
                                                        val intent = Intent(Intent.ACTION_VIEW)
                                                        intent.data =
                                                            Uri.parse("https://github.com/xihan123/QDReadHook#%E5%A6%82%E6%9E%9C%E8%A7%89%E5%BE%97%E8%BF%99%E4%B8%AA%E6%A8%A1%E5%9D%97%E5%AF%B9%E6%82%A8%E6%9C%89%E7%94%A8%E5%8F%AF%E6%89%AB%E6%8F%8F%E4%B8%8B%E6%96%B9%E4%BA%8C%E7%BB%B4%E7%A0%81%E9%9A%8F%E6%84%8F%E6%89%93%E8%B5%8F%E8%A6%81%E6%98%AF%E8%83%BD%E6%89%93%E8%B5%8F%E4%B8%AA-1024-%E5%B0%B1%E5%A4%AA%E4%BA%86%E6%82%A8%E7%9A%84%E6%94%AF%E6%8C%81%E5%B0%B1%E6%98%AF%E6%88%91%E6%9B%B4%E6%96%B0%E7%9A%84%E5%8A%A8%E5%8A%9B")
                                                        startActivity(intent)
                                                    }
                                                }
                                            ).replaceSpan("点个star", replacement = {
                                                MyClickableSpan {
                                                    val intent = Intent(Intent.ACTION_VIEW)
                                                    intent.data =
                                                        Uri.parse("https://github.com/xihan123/QDReadHook")
                                                    startActivity(intent)
                                                }
                                            }).replaceSpan("727983520", replacement = {
                                                MyClickableSpan {
                                                    joinQQGroup()
                                                }
                                            })
                                        ) {}
                                        explainTextView.textView.setTextIsSelectable(true)
                                        explainTextView.textView.movementMethod =
                                            ClickableMovementMethod.getInstance()
                                        linearLayout.apply {
                                            addView(mainOptionTextView)
                                            addView(advOptionTextView)
                                            addView(shieldOptionTextView)
                                            addView(splashOptionTextView)
                                            addView(viewHideOptionTextView)
                                            addView(replaceOptionTextView)
                                            addView(openSourceryOptionTextView)
                                            addView(explainTextView)
                                        }
                                        alertDialog {
                                            title = "模块版本: ${BuildConfig.VERSION_NAME}"
                                            customView = linearLayout

                                            positiveButton("重启起点") {
                                                restartApplication()
                                            }

                                            build()
                                            show()
                                        }
                                    }

                                     */
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
        ): Boolean {
            /*
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
                val authorName = jb.getString("AuthorName") ?: jb.getString("authorName")
                val categoryName = jb.getString("CategoryName") ?: jb.getString("categoryName")
                val subCategoryName =
                    jb.getString("SubCategoryName") ?: jb.getString("subCategoryName")
                    ?: jb.getString("itemSubName")
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
                        } else {
                            array += array.getString(i)
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
                        array += array.getString(i)
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
            bookName: String = "",
            authorName: String = ""
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

        in 842..878 -> {
            findClass("com.qidian.QDReader.ui.view.bookshelfview.CheckInReadingTimeViewNew").hook {
                injectMember {
                    method {
                        name = "E"
                    }
                    afterHook {
                        val v = instance.getView<LinearLayout>(
                            "v"
                        )
                        val qd = instance.getParam<Any>(
                            "v"
                        )
                        qd?.let { qdv ->
                            val e = qdv.getView<TextView>(
                                "e"
                            )
                            e?.let { etv ->
                                if (etv.text == "签到") {
                                    v?.performClick()
                                }
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
                        val binding = instance.getParam<Any>("binding")
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
    enableNewStore: Boolean = false
) {
    val needHookClass = when (versionCode) {
        868 -> "r4.a\$a"
        in 872..878 -> "p4.a\$a"
        else -> null
    }

    /**
     * NEW_USER_ACCOUNT
     */
    val needHookNewUserAccountMethod = when (versionCode) {
        868 -> "n"
        872 -> "m"
        878 -> "o"
        else -> null
    }

    /**
     * BOOK_STORE_V2
     */
    val needHookBookStoreV2Method = when (versionCode) {
        872 -> "d"
        878 -> "e"
        else -> null
    }

    if (needHookClass == null) {
        "新旧我的布局".printlnNotSupportVersion(versionCode)
        return
    }
    needHookClass.hook {
        if (needHookNewUserAccountMethod == null) {
            "新版我的布局".printlnNotSupportVersion(versionCode)
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
            "新旧精选布局".printlnNotSupportVersion(versionCode)
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
    }
}

/**
 * Hook 启用本地至尊卡
 */
fun PackageParam.enableLocalCard(versionCode: Int) {
    when (versionCode) {
        in 758..900 -> {

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

                    injectMember {
                        method {
                            name = "isAuto"
                            emptyParam()
                            returnType = IntType
                        }
                        replaceTo(1)
                    }
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
        in 827..900 -> {
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
        in 854..878 -> {
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

        else -> "免广告领取奖励".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 忽略粉丝值跳转加群限制
 * ValidateActionLimitUtil Limits
 */
fun PackageParam.ignoreFansValueJumpLimit(versionCode: Int) {
    when (versionCode) {
        in 854..878 -> {
            findClass("com.qidian.QDReader.util.ValidateActionLimitUtil\$a").hook {
                injectMember {
                    method {
                        name = "e"
                        param(JSONObjectClass, StringClass, IntType)
                        returnType = UnitType
                    }
                    beforeHook {
                        val jb = args[0] as? JSONObject
                        jb?.let {
                            safeRun {
                                it.put("Passed", 1)
                                it.optJSONArray("Limits")?.optJSONObject(0)?.put("Passed", 1)
                            }
                            args(0).set(it)
                        }
                    }
                }
            }
        }

        else -> "忽略粉丝值加群限制".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 忽略限时免费不能批量订阅限制
 * IsFreeLimit
 */
fun PackageParam.ignoreFreeSubscribeLimit(versionCode: Int) {
    when (versionCode) {
        in 854..878 -> {
            findClass("com.qidian.QDReader.component.bll.manager.e1").hook {
                injectMember {
                    method {
                        name = "n0"
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

        else -> "忽略限时免费批量订阅限制".printlnNotSupportVersion(versionCode)
    }
}

/*
/**
 * 主要配置弹框
 */
fun Context.showMainOptionDialog() {
    val linearLayout = CustomLinearLayout(this, isAutoWidth = false)
    val packageNameOption = CustomEditText(
        context = this,
        title = "包名设置",
        message = "一般默认即可,不建议更改",
        value = optionEntity.mainOption.packageName
    ) {
        optionEntity.mainOption.packageName = it
    }
    val enableAutoSignOption = CustomSwitch(
        context = this,
        title = "启用自动签到",
        isEnable = optionEntity.mainOption.enableAutoSign
    ) {
        optionEntity.mainOption.enableAutoSign = it
    }
    val enableOldLayoutOption = CustomSwitch(
        context = this,
        title = "启用旧版布局",
        isEnable = optionEntity.mainOption.enableOldLayout
    ) {
        optionEntity.mainOption.enableOldLayout = it
    }
    val enableOldBookShelfLayout = CustomSwitch(
        context = this,
        title = "启用旧版书架布局",
        isEnable = optionEntity.mainOption.enableOldBookShelfLayout
    ) {
        optionEntity.mainOption.enableOldBookShelfLayout = it
    }

    val enableLocalCardOption = CustomSwitch(
        context = this,
        title = "启用本地至尊卡",
        isEnable = optionEntity.mainOption.enableLocalCard
    ) {
        optionEntity.mainOption.enableLocalCard = it
    }

    val enableUnlockMemberBackgroundOption = CustomSwitch(
        context = this,
        title = "解锁会员卡专属背景",
        isEnable = optionEntity.mainOption.enableUnlockMemberBackground
    ) {
        optionEntity.mainOption.enableUnlockMemberBackground = it
    }

    val enableFreeAdRewardOption = CustomSwitch(
        context = this,
        title = "免广告领取奖励",
        isEnable = optionEntity.mainOption.enableFreeAdReward
    ) {
        optionEntity.mainOption.enableFreeAdReward = it
    }

    val freeAdRewardAutoExitTimeOption = CustomEditText(
        context = this,
        title = "免广告领取奖励自动退出时间",
        message = "单位为秒,默认为3秒,如不需要把此数值设定大一些",
        value = optionEntity.mainOption.freeAdRewardAutoExitTime.toString()
    ) {
        optionEntity.mainOption.freeAdRewardAutoExitTime = it.toInt()
    }

    val enableIgnoreFansValueJumpLimitOption = CustomSwitch(
        context = this,
        title = "忽略粉丝值跳转加群限制",
        isEnable = optionEntity.mainOption.enableIgnoreFansValueJumpLimit
    ) {
        optionEntity.mainOption.enableIgnoreFansValueJumpLimit = it
    }

    val enableIgnoreFreeSubscribeLimitOption = CustomSwitch(
        context = this,
        title = "忽略限时免费批量订阅限制",
        isEnable = optionEntity.mainOption.enableIgnoreFreeSubscribeLimit
    ) {
        optionEntity.mainOption.enableIgnoreFreeSubscribeLimit = it
    }

    val enableCustomReaderThemeOption = CustomSwitch(
        context = this,
        title = "启用自定义阅读页主题路径",
        isEnable = optionEntity.mainOption.enableCustomReaderThemePath
    ) {
        optionEntity.mainOption.enableCustomReaderThemePath = it
    }

    val visualizeReadingPageBackgroundColorAdjustmentOption = CustomTextView(
        context = this,
        mText = "可视化阅读页背景色调调整",
        isBold = true,
    ) {
        showVisualizeReadingPageBackgroundColorAdjustmentDialog()
    }
    linearLayout.apply {
        addView(packageNameOption)
        addView(enableAutoSignOption)
        addView(enableLocalCardOption)
        addView(enableFreeAdRewardOption)
        if (optionEntity.mainOption.enableFreeAdReward) {
            addView(freeAdRewardAutoExitTimeOption)
        }
        addView(enableIgnoreFansValueJumpLimitOption)
        addView(enableIgnoreFreeSubscribeLimitOption)
        if (versionCode < NOT_SUPPORT_OLD_LAYOUT_VERSION_CODE) {
            addView(enableOldLayoutOption)
        }
        if (versionCode > 827) {
            addView(enableOldBookShelfLayout)
            addView(enableUnlockMemberBackgroundOption)
            addView(enableCustomReaderThemeOption)
            if (optionEntity.mainOption.enableCustomReaderThemePath) {
                addView(visualizeReadingPageBackgroundColorAdjustmentOption)
            }
        }
    }
    alertDialog {
        title = "主要配置"
        customView = linearLayout
        okButton {
            updateOptionEntity()
        }
        negativeButton("返回") {
            it.dismiss()
        }

        build()
        show()
    }
}

 */

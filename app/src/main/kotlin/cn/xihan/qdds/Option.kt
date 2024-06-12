package cn.xihan.qdds

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.annotation.Keep
import cn.xihan.qdds.Option.logPath
import cn.xihan.qdds.Option.optionPath
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import java.io.File
import java.lang.ref.WeakReference

/**
 * 默认选项实体
 * @suppress Generate Documentation
 */
val defaultOptionEntity by lazy {
    OptionEntity()
}

/**
 * 默认空列表
 * @suppress Generate Documentation
 */
val defaultEmptyList by lazy { mutableListOf<SelectedModel>() }

/**
 * 读取选项
 * @return [File?]
 * @suppress Generate Documentation
 */
private fun provideOptionFile(): File {
    return File(optionPath).apply {
        parentFile?.mkdirs()
        if (!exists()) {
            createNewFile()
            writeText(defaultOptionEntity.toJSONString())
        }
    }
}

/**
 * 读取日志
 * @return File
 * @suppress Generate Documentation
 */
private fun provideLogFile(): File {
    return File(logPath).apply {
        parentFile?.mkdirs()
        if (!exists()) {
            createNewFile()
        }
    }
}

/**
 * 提供期权实体
 * @since 7.9.334-1196
 * @param [file] 文件
 * @return [OptionEntity]
 * @suppress Generate Documentation
 */
fun provideOptionEntity(file: File): OptionEntity = try {
    file.readText().parseObject<OptionEntity>().apply {
        val newAdvOptionConfigurations = defaultOptionEntity.advOption
        val newInterceptConfigurations = defaultOptionEntity.interceptOption
        val newViewHideOptionConfigurations =
            defaultOptionEntity.viewHideOption.homeOption.configurations
        val newBookDetailOptionConfigurations = defaultOptionEntity.viewHideOption.bookDetailOptions
        val newAutomaticReceiveOptionConfigurations = defaultOptionEntity.automatizationOption
        val newSearchOption = defaultOptionEntity.viewHideOption.searchOption
        advOption = advOption.merge(
            newAdvOptionConfigurations
        )
        interceptOption = interceptOption.merge(
            newInterceptConfigurations
        )

        viewHideOption.homeOption.configurations = viewHideOption.homeOption.configurations.merge(
            newViewHideOptionConfigurations
        )
        viewHideOption.bookDetailOptions = viewHideOption.bookDetailOptions.merge(
            newBookDetailOptionConfigurations
        )
        automatizationOption = automatizationOption.merge(
            newAutomaticReceiveOptionConfigurations
        )
        viewHideOption.searchOption = viewHideOption.searchOption.merge(
            newSearchOption
        )
    }
} catch (e: Throwable) {
    e.loge()
    defaultOptionEntity
}

object Option {

    lateinit var context: WeakReference<Context>

    val optionFile by lazy {
        provideOptionFile()
    }

    val logFile by lazy {
        provideLogFile()
    }

    val optionEntity by lazy {
        provideOptionEntity(optionFile)
    }

    /**
     * 基本路径
     * @suppress Generate Documentation
     */
    val basePath =
        "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/QDReader"

    /**
     * 重定向主题路径
     * @suppress Generate Documentation
     */
    val redirectThemePath = "${basePath}/ReaderTheme/"

    /**
     * 选项路径
     * @suppress Generate Documentation
     */
    val optionPath = "${basePath}/option.json"

    /**
     * 日志路径
     * @suppress Generate Documentation
     */
    val logPath = "${basePath}/log.txt"

    /**
     * 闪屏图片路径
     * @suppress Generate Documentation
     */
    val splashPath = "${basePath}/Splash/"

    /**
     * 图片路径
     * @suppress Generate Documentation
     */
    val picturesPath = "${basePath}/Pictures"

    /**
     * 音频路径
     * @suppress Generate Documentation
     */
    val audioPath = "${basePath}/Audio/"

    /**
     * 字体路径
     */
    val fontPath = "${basePath}/Font/"

    fun initialize(context: Context) {
        this.context = WeakReference(context)
        if (optionEntity.readPageOption.enableCustomFont) {
            moveToPrivateStorage(context)
        }
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
                val authorName = jb.getStringWithFallback("authorName") ?: jb.getStringWithFallback("author")
                val categoryName = jb.getStringWithFallback("categoryName")
                val subCategoryName = jb.getStringWithFallback("subCategoryName")
                    ?: jb.getStringWithFallback("itemSubName")
                val tagName = jb.getStringWithFallback("tagName")
                val array =
                    jb.getJSONArrayWithFallback("AuthorTags") ?: jb.getJSONArrayWithFallback("tags")
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
                val array =
                    jb.getJSONArrayWithFallback("authorTags") ?: jb.getJSONArrayWithFallback("tags")
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

    /**
     * 更新选项实体
     * @return [Boolean]
     * @suppress Generate Documentation
     */
    fun updateOptionEntity(): Boolean = try {
        optionFile.writeText(optionEntity.toJSONString())
        true
    } catch (e: Throwable) {
        e.loge()
        false
    }

    /**
     * 重置选项实体
     * @return [Boolean]
     * @suppress Generate Documentation
     */
    fun resetOptionEntity(): Boolean = try {
        optionFile.writeText(OptionEntity().toJSONString())
        true
    } catch (e: Throwable) {
        (e.message ?: "重置配置文件失败").loge()
        false
    }

    /**
     * 删除起点目录下所有文件
     */
    fun deleteAll() = context.get()?.apply {
        filesDir.parentFile?.listFiles()?.filterNot { it.isDirectory && it.name == "databases" }
            ?.removeAll()
        getExternalFilesDirs(null).firstNotNullOfOrNull {
            it.listFiles()?.removeAll()
        }
    }

    private fun Array<File>.removeAll() = runAndCatch {
        forEach {
            it.deleteRecursively()
        }
    }

    fun List<File>.removeAll() = runAndCatch {
        forEach {
            it.deleteRecursively()
        }
    }

    /**
     * 写入文本文件
     * @param [fileName] 文件名
     * @suppress Generate Documentation
     */
    fun String.writeTextFile(fileName: String = "test") {
        // 使用File类的构造函数，创建一个File对象
        val file = context.get()?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) ?: return
        // 使用generateSequence函数，创建一个序列，每次在文件名后面加上"-数字"
        generateSequence(0) { it + 1 }
            // 使用takeWhile函数，筛选出不存在的文件
            .takeWhile {
                !File(
                    file.parent, "${file.nameWithoutExtension}-$it.${file.extension}"
                ).exists()
            }
            // 使用firstOrNull函数，获取第一个元素，或者返回-1
            .firstOrNull() ?: (-1)
            // 使用let函数，对File对象进行操作
            .let {
                // 如果序列中有元素，则在文件名后面加上"-数字"
                file.renameTo(
                    File(
                        file.parent, "${file.nameWithoutExtension}-$it.${file.extension}"
                    )
                )
                // 确保父目录存在
                file.parentFile?.mkdirs()
                // 如果文件不存在，则创建新文件
                if (!file.exists()) file.createNewFile()
                // 将字符串写入到文件中
                file.writeText(this)
            }
    }

    /**
     * 将文本写入到文件末尾
     */
    fun String.writeTextFile() = logFile.appendText("\n$this")

    fun cleanLog(context: Context) {
        logFile.writeText("")
        context.toast("日志已清空")
    }

    fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

}

/**
 * 配置实体
 * 创建[OptionEntity]
 * @param [allowDisclaimers] 允许免责声明
 * @param [currentDisclaimersVersionCode] 当前免责声明版本代码
 * @param [latestDisclaimersVersionCode] 最新免责声明版本代码
 * @param [advOption] 广告选项
 * @param [mainOption] 主选项
 * @param [shieldOption] 屏蔽选项
 * @param [startImageOption] 启动图像选项
 * @param [bookshelfOption] 书架选项
 * @param [readPageOption] 阅读页面选项
 * @param [interceptOption] 拦截选项
 * @param [viewHideOption] 视图隐藏选项
 * @param [automatizationOption] 自动化选项
 * @suppress Generate Documentation
 */
@Keep
data class OptionEntity(
    var allowDisclaimers: Boolean = false,
    var currentDisclaimersVersionCode: Int = 0,
    var latestDisclaimersVersionCode: Int = 3,
    var advOption: List<SelectedModel> = listOf(
        SelectedModel("闪屏广告", true),
        SelectedModel("GDT广告"),
        SelectedModel("主页-每日阅读广告", true),
        SelectedModel("主页-书架顶部广告", true),
        SelectedModel("主页-书架活动弹框", true),
        SelectedModel("主页-书架浮窗活动", true),
        SelectedModel("主页-书架底部导航栏广告", true),
        SelectedModel("我-中间广告", true),
        SelectedModel("阅读页-浮窗广告"),
//        SelectedModel("阅读页-打赏小剧场"),
        SelectedModel("阅读页-章末一刀切"),
        SelectedModel("阅读页-章末新人推书"),
        SelectedModel("阅读页-章末本章说"),
        SelectedModel("阅读页-章末福利"),
        SelectedModel("阅读页-章末广告", true),
        SelectedModel("阅读页-章末求票"),
        SelectedModel("阅读页-章末底部月票打赏红包"),
        SelectedModel("阅读页-最后一页-中间广告", true),
        SelectedModel("阅读页-最后一页-弹框广告", true)
    ),
    var mainOption: MainOption = MainOption(),
    var cookieOption: CookieOption = CookieOption(),
    var shieldOption: ShieldOption = ShieldOption(),
    var startImageOption: StartImageOption = StartImageOption(),
    var bookshelfOption: BookshelfOption = BookshelfOption(),
    var readPageOption: ReadPageOption = ReadPageOption(),
    var interceptOption: List<SelectedModel> = listOf(
        SelectedModel("检测更新", true),
        SelectedModel("部分环境检测", true),
        SelectedModel("隐私政策更新弹框", true),
        SelectedModel("同意隐私政策弹框", true),
        SelectedModel("WebSocket", true),
        SelectedModel("青少年模式请求", true),
        SelectedModel("青少年模式弹框", true),
        SelectedModel("阅读页水印", true),
        SelectedModel("发帖图片水印", true),
        SelectedModel("自动跳转精选", true),
        SelectedModel("首次安装分析", true),
        SelectedModel("异步主GDT广告任务|com.qidian.QDReader.start.AsyncMainGDTTask", true),
        SelectedModel(
            "异步主游戏广告SDK任务|com.qidian.QDReader.start.AsyncMainGameADSDKTask", true
        ),
        SelectedModel(
            "异步主游戏下载任务|com.qidian.QDReader.start.AsyncMainGameDownloadTask", true
        ),
        SelectedModel(
            "异步子屏幕截图任务|com.qidian.QDReader.start.AsyncChildScreenShotTask", true
        ),
        SelectedModel("异步主用户操作任务|com.qidian.QDReader.start.AsyncMainUserActionTask", true),
        SelectedModel("异步有赞-SDK任务|com.qidian.QDReader.start.AsyncChildYouZanTask", true),
        SelectedModel("异步初始化KNOBS-SDK任务|com.qidian.QDReader.start.AsyncInitKnobsTask", true),
        SelectedModel(
            "异步子更新设备任务|com.qidian.QDReader.start.AsyncChildUpdateDeviceTask", true
        ),
        SelectedModel("异步子崩溃任务|com.qidian.QDReader.start.AsyncChildCrashTask", true),
        SelectedModel("异步子点播上传任务|com.qidian.QDReader.start.AsyncChildVODUploadTask", true),
        SelectedModel(
            "异步子青少年和网络回调任务|com.qidian.QDReader.start.AsyncChildTeenagerAndNetCallbackTask",
            true
        ),
        SelectedModel("异步主下载Sdk任务|com.qidian.QDReader.start.AsyncMainDownloadSdkTask", true),
        SelectedModel(
            "异步子自动跟踪器初始化任务|com.qidian.QDReader.start.AsyncChildAutoTrackerInitTask",
            true
        ),
        SelectedModel("异步子表情符号任务|com.qidian.QDReader.start.AsyncChildEmojiTask", true),
        SelectedModel("同步推送任务|com.qidian.QDReader.start.SyncPushTask", true),
        SelectedModel("同步Bugly-APM任务|com.qidian.QDReader.start.SyncBuglyApmTask", true),
        SelectedModel("同步挂钩通道任务|com.qidian.QDReader.start.SyncHookChannelTask", true),
        SelectedModel("同步正确任务|com.qidian.QDReader.start.SyncRightlyTask", true)
    ),
    var viewHideOption: ViewHideOption = ViewHideOption(),
    var automatizationOption: List<SelectedModel> = listOf(
        SelectedModel("自动签到"),
        SelectedModel("自动领取阅读积分"),
        SelectedModel("自动领取章末红包"),
//        SelectedModel("自动跳过启动页")
    )
) {

    /**
     * 主配置
     * @param packageName 包名
     * @param enablePostToShowImageUrl 启用发帖显示图片地址
     * @param enableFreeAdReward 启用免广告奖励
     * @param enableIgnoreFreeSubscribeLimit 启用忽略免费批量订阅限制
     * @param enableUnlockMemberBackground 启用解锁会员背景
     * @param enableHideAppIcon 启用隐藏应用图标
     * @param enableExportEmoji 启用导出表情包
     * @param enableOldDailyRead 启用旧的每日阅读
     * @param enableStartCheckingPermissions 启用检查权限
     * @param enableCustomIMEI 启用自定义IMEI
     * @param enableFixDouYinShare 启用修复抖音分享
     */
    @Keep
    data class MainOption(
        var packageName: String = "com.qidian.QDReader",
        var enablePostToShowImageUrl: Boolean = false,
        var enableFreeAdReward: Boolean = false,
        var enableIgnoreFreeSubscribeLimit: Boolean = false,
        var enableUnlockMemberBackground: Boolean = false,
        var enableHideAppIcon: Boolean = false,
        var enableExportEmoji: Boolean = false,
        var enableOldDailyRead: Boolean = false,
        var enableStartCheckingPermissions: Boolean = true,
        var enableCustomIMEI: Boolean = true,
        var qimei: String = "",
        var enableFixDouYinShare: Boolean = false
    )

    @Keep
    data class CookieOption(
        var enableCookie: Boolean = false,
        var enableDebug: Boolean = false,
        var uid: Int = 0,
        var ua: String = "",
        var cookie: String = ""
    )

    /**
     * 屏蔽配置
     * @param enableQuickShieldDialog 启用快速屏蔽弹窗
     * @param configurations 屏蔽配置值集合
     * @param authorList 屏蔽作者集合
     * @param bookTypeList 屏蔽书类集合
     * @param bookNameList 屏蔽书名集合
     * @param enableBookTypeEnhancedBlocking 启用书类型增强屏蔽
     */
    @Keep
    data class ShieldOption(
        var enableQuickShieldDialog: Boolean = false,
        var authorList: MutableSet<String> = mutableSetOf(),
        var bookNameList: MutableSet<String> = mutableSetOf(),
        var bookTypeList: Set<String> = setOf(),
        var configurations: List<SelectedModel> = listOf(
            SelectedModel("精选-主页面", true),
            SelectedModel("精选-分类", true),
            SelectedModel("精选-分类-全部作品", true),
            SelectedModel("精选-免费-免费推荐", true),
            SelectedModel("精选-免费-新书入库", true),
            SelectedModel("精选-畅销精选、主编力荐等更多", true),
            SelectedModel("精选-新书强推、三江推荐", true),
            SelectedModel("精选-排行榜", true),
            SelectedModel("精选-新书", true),
            SelectedModel("每日导读", true),
            SelectedModel("精选-漫画", true),
            SelectedModel("精选-漫画-其他", true),
            SelectedModel("分类-小编力荐、本周强推等更多", true)
        ),
        var enableBookTypeEnhancedBlocking: Boolean = false,
    )

    /**
     * 启动图配置
     * @param enableCustomStartImage 启用自定义启动图
     * @param enableRedirectLocalStartImage 启用重定向本地启动图
     * @param customStartImageUrlList 自定义启动图url列表
     * @param enableCaptureTheOfficialLaunchMapList 启用抓取官方启动图列表
     * @param officialLaunchMapList 官方启动图列表
     */
    @Keep
    data class StartImageOption(
        var enableCustomStartImage: Boolean = false,
        var enableRedirectLocalStartImage: Boolean = false,
        var enableCaptureTheOfficialLaunchMapList: Boolean = false,
        var customStartImageUrlList: Set<String> = emptySet(),
        var officialLaunchMapList: List<StartImageModel> = emptyList(),
    )

    /**
     * 书架配置
     * @param enableCustomBookShelfTopImage 启用自定义书架顶部图片
     * @param enableSameNightAndDay 启用夜间和日间相同
     * @param lightModeCustomBookShelfTopImageModel 亮色模式自定义书架顶部图片模型
     * @param darkModeCustomBookShelfTopImageModel 暗色模式自定义书架顶部图片模型
     */
    @Keep
    data class BookshelfOption(
        var enableCustomBookShelfTopImage: Boolean = false,
        var enableSameNightAndDay: Boolean = true,
        var lightModeCustomBookShelfTopImageModel: CustomBookShelfTopImageModel = CustomBookShelfTopImageModel(),
        var darkModeCustomBookShelfTopImageModel: CustomBookShelfTopImageModel = CustomBookShelfTopImageModel(),
    )

    /**
     * 阅读页配置
     * @param enableRedirectReadingPageBackgroundPath 启用自定义阅读页主题路径
     * @param enableCustomBookLastPage 启用自定义书籍最后一页
     * @param enableShowReaderPageChapterSaveRawPicture 启用显示阅读页章节保存原图
     * @param enableShowReaderPageChapterSavePictureDialog 启用显示阅读页章节保存图片对话框
     * @param enableShowReaderPageChapterSaveAudioDialog 启用显示阅读页章节保存音频对话框
     * @param enableCopyReaderPageChapterComment 启用复制阅读页章节评论
     * @param enableReadTimeFactor 启用阅读时间翻倍
     * @param timeFactor 阅读时间比例
     */
    @Keep
    data class ReadPageOption(
        var enableRedirectReadingPageBackgroundPath: Boolean = false,
        var enableCustomBookLastPage: Boolean = false,
        var enableShowReaderPageChapterSaveRawPicture: Boolean = false,
        var enableShowReaderPageChapterSavePictureDialog: Boolean = false,
        var enableShowReaderPageChapterSaveAudioDialog: Boolean = false,
        var enableCopyReaderPageChapterComment: Boolean = false,
        var enableCustomFont: Boolean = true,
        var enableReadTimeFactor: Boolean = false,
        var speedFactor: Int = 5
    )

    /**
     * 控件隐藏配置
     * @param enableHideRedDot 启用隐藏红点
     * @param enableHideBookDetail 启用隐藏书籍详情
     * @param enableHideLastPage 启用隐藏阅读页最后一页
     * @param searchOption 搜索配置
     * @param homeOption 首页配置
     * @param selectedOption 精选配置
     * @param accountOption 用户页面配置
     * @param bookDetailOptions 书籍详情配置
     * @param readPageOptions  阅读页配置
     * @param bookLastPageOptions 阅读最后一页配置
     */
    @Keep
    data class ViewHideOption(
        var enableHideRedDot: Boolean = false,
        var enableHideBookDetail: Boolean = false,
        var enableHideLastPage: Boolean = false,
        var homeOption: HomeOption = HomeOption(),
        var selectedOption: SelectedOption = SelectedOption(),
        var accountOption: AccountOption = AccountOption(),
        var readPageOptions: BookReadPageOptions = BookReadPageOptions(),
        var searchOption: List<SelectedModel> = listOf(
            SelectedModel("搜索历史"),
            SelectedModel("搜索发现"),
            SelectedModel("搜索排行榜"),
            SelectedModel("为你推荐")
        ),
        var bookDetailOptions: List<SelectedModel> = listOf(
            SelectedModel(title = "出圈指数"),
            SelectedModel(title = "荣誉标签"),
            SelectedModel(title = "QQ群"),
            SelectedModel(title = "书友圈"),
            SelectedModel(title = "书友榜"),
            SelectedModel(title = "月票金主"),
            SelectedModel(title = "本书看点|中心广告"),
            SelectedModel(title = "浮窗广告"),
            SelectedModel(title = "同类作品推荐"),
            SelectedModel(title = "看过此书的人还看过")
        ),
        var bookLastPageOptions: List<SelectedModel> = listOf(
            SelectedModel("书友圈"),
            SelectedModel("看过此书的人还看过"),
            SelectedModel("推荐"),
            SelectedModel("同类作品推荐"),
            SelectedModel("收录此书的书单"),
            SelectedModel("试读")
        )
    ) {

        /**
         * 主页配置
         * @param configurations 主页配置列表
         * @param bottomNavigationConfigurations 底部导航栏配置
         */
        @Keep
        data class HomeOption(
            var enableCaptureBottomNavigation: Boolean = false,
            var configurations: List<SelectedModel> = listOf(
                SelectedModel("主页顶部宝箱提示", true),
                SelectedModel("书架每日导读", true),
                SelectedModel("书架顶部标题", true)
            ),
            var bottomNavigationConfigurations: MutableList<SelectedModel> = mutableListOf()
        )

        /**
         * 精选配置
         * @param enableSelectedHide 启用隐藏精选
         * @param enableSelectedTitleHide 启用隐藏精选标题
         * @param configurations 精选配置列表
         * @param selectedTitleConfigurations 精选标题配置列表
         */
        @Keep
        data class SelectedOption(
            var enableSelectedHide: Boolean = false,
            var enableSelectedTitleHide: Boolean = false,
            var configurations: MutableList<SelectedModel> = mutableListOf(
                SelectedModel("轮播图"), SelectedModel("轮播消息")
            ),
            var selectedTitleConfigurations: MutableList<SelectedModel> = mutableListOf()
        )

        /**
         * 用户页面配置
         * @param enableHideAccount 启用隐藏用户页面
         * @param enableHideAccountRightTopRedDot 启用隐藏用户页面右上角红点
         * @param configurations 可用配置
         */
        @Keep
        data class AccountOption(
            var enableHideAccount: Boolean = false,
            var enableHideAccountRightTopRedDot: Boolean = false,
            var configurations: MutableList<SelectedModel> = mutableListOf()
        )

        /**
         * 书籍阅读页面配置
         * @param enableCaptureBookReadPageView 启用抓取阅读页面控件
         * @param configurations 配置集合
         */
        @Keep
        data class BookReadPageOptions(
            var enableCaptureBookReadPageView: Boolean = false,
            var configurations: MutableList<SelectedModel> = mutableListOf()
        )
    }
}

/**
 * 选定模型
 * 创建[SelectedModel]
 * @param [title] 标题
 * @param [selected] 选定
 * @suppress Generate Documentation
 */
@Keep
data class SelectedModel(
    var title: String = "",
    var selected: Boolean = false,
)

/**
 * 启动图像模型
 * 创建[StartImageModel]
 * @param [name] 名称
 * @param [paperId] 纸张id
 * @param [preImageUrl] 预图像url
 * @param [imageUrl] 图像url
 * @param [isUsed] 已使用
 * @suppress Generate Documentation
 */
@Keep
data class StartImageModel(
    var name: String = "",
    var paperId: Long = 0,
    var preImageUrl: String = "",
    var imageUrl: String = "",
    var isUsed: Boolean = false,
)

/**
 * 自定义书架顶部图像模型
 * 创建[CustomBookShelfTopImageModel]
 * @param [border01] border01
 * @param [font] 字体
 * @param [fontHLight] 字体指示灯
 * @param [fontLight] 字体指示灯
 * @param [fontOnSurface] 表面字体
 * @param [surface01] 表面01
 * @param [surfaceIcon] 曲面图标
 * @param [headImage] 头部图像
 * @suppress Generate Documentation
 */
@Keep
data class CustomBookShelfTopImageModel(
    var border01: String = "",
    var font: String = "",
    var fontHLight: String = "",
    var fontLight: String = "",
    var fontOnSurface: String = "",
    var surface01: String = "",
    var surfaceIcon: String = "",
    var headImage: String = "",
)

package cn.xihan.qdds

import android.os.Environment
import androidx.annotation.Keep
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import java.io.File


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
 * 闪屏图片路径
 * @suppress Generate Documentation
 */
val splashPath = "${basePath}/Splash/"

/**
 * 音频路径
 * @suppress Generate Documentation
 */
val audioPath = "${basePath}/Audio/"

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
    var latestDisclaimersVersionCode: Int = 2,
    var advOption: List<SelectedModel> = listOf(
        SelectedModel("闪屏广告"),
        SelectedModel("GDT广告"),
        SelectedModel("主页-每日阅读广告"),
        SelectedModel("主页-书架顶部广告"),
        SelectedModel("主页-书架活动弹框"),
        SelectedModel("主页-书架浮窗活动"),
        SelectedModel("主页-书架底部导航栏广告"),
        SelectedModel("我-中间广告"),
        SelectedModel("阅读页-浮窗广告"),
        SelectedModel("阅读页-打赏小剧场"),
        SelectedModel("阅读页-章末一刀切"),
        SelectedModel("阅读页-章末新人推书"),
        SelectedModel("阅读页-章末本章说"),
        SelectedModel("阅读页-章末福利"),
        SelectedModel("阅读页-章末广告"),
        SelectedModel("阅读页-章末求票"),
        SelectedModel("阅读页-章末底部月票打赏红包"),
        SelectedModel("阅读页-最后一页-中间广告"),
        SelectedModel("阅读页-最后一页-弹框广告")
    ),
    var mainOption: MainOption = MainOption(),
    var shieldOption: ShieldOption = ShieldOption(),
    var startImageOption: StartImageOption = StartImageOption(),
    var bookshelfOption: BookshelfOption = BookshelfOption(),
    var readPageOption: ReadPageOption = ReadPageOption(),
    var interceptOption: List<SelectedModel> = listOf(
        SelectedModel("检测更新"),
        SelectedModel("隐私政策更新弹框"),
        SelectedModel("同意隐私政策弹框"),
        SelectedModel("WebSocket"),
        SelectedModel("青少年模式请求"),
        SelectedModel("青少年模式弹框"),
        SelectedModel("阅读页水印"),
        SelectedModel("发帖图片水印"),
        SelectedModel("自动跳转精选"),
        SelectedModel("首次安装分析"),
        SelectedModel("异步主GDT广告任务|com.qidian.QDReader.start.AsyncMainGDTTask"),
        SelectedModel("异步主游戏广告SDK任务|com.qidian.QDReader.start.AsyncMainGameADSDKTask"),
        SelectedModel("异步主游戏下载任务|com.qidian.QDReader.start.AsyncMainGameDownloadTask"),
        SelectedModel("异步子屏幕截图任务|com.qidian.QDReader.start.AsyncChildScreenShotTask"),
        SelectedModel("异步主用户操作任务|com.qidian.QDReader.start.AsyncMainUserActionTask"),
        SelectedModel("异步有赞-SDK任务|com.qidian.QDReader.start.AsyncChildYouZanTask"),
        SelectedModel("异步初始化KNOBS-SDK任务|com.qidian.QDReader.start.AsyncInitKnobsTask"),
        SelectedModel("异步子更新设备任务|com.qidian.QDReader.start.AsyncChildUpdateDeviceTask"),
        SelectedModel("异步子崩溃任务|com.qidian.QDReader.start.AsyncChildCrashTask"),
        SelectedModel("异步子点播上传任务|com.qidian.QDReader.start.AsyncChildVODUploadTask"),
        SelectedModel("异步子青少年和网络回调任务|com.qidian.QDReader.start.AsyncChildTeenagerAndNetCallbackTask"),
        SelectedModel("异步主下载Sdk任务|com.qidian.QDReader.start.AsyncMainDownloadSdkTask"),
        SelectedModel("异步子自动跟踪器初始化任务|com.qidian.QDReader.start.AsyncChildAutoTrackerInitTask"),
        SelectedModel("异步子表情符号任务|com.qidian.QDReader.start.AsyncChildEmojiTask"),
        SelectedModel("同步推送任务|com.qidian.QDReader.start.SyncPushTask"),
        SelectedModel("同步Bugly-APM任务|com.qidian.QDReader.start.SyncBuglyApmTask"),
        SelectedModel("同步挂钩通道任务|com.qidian.QDReader.start.SyncHookChannelTask"),
        SelectedModel("同步正确任务|com.qidian.QDReader.start.SyncRightlyTask")
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
     * @param enableStartCheckingPermissions  启用启动时检查权限
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
        var enableStartCheckingPermissions: Boolean = true
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
            SelectedModel("精选-主页面"),
            SelectedModel("精选-分类"),
            SelectedModel("精选-分类-全部作品"),
            SelectedModel("精选-免费-免费推荐"),
            SelectedModel("精选-免费-新书入库"),
            SelectedModel("精选-畅销精选、主编力荐等更多"),
            SelectedModel("精选-新书强推、三江推荐"),
            SelectedModel("精选-排行榜"),
            SelectedModel("精选-新书"),
            SelectedModel("每日导读"),
            SelectedModel("精选-漫画"),
            SelectedModel("精选-漫画-其他"),
            SelectedModel("分类-小编力荐、本周强推等更多")
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
     * @param speedFactor 阅读时间倍速
     */
    @Keep
    data class ReadPageOption(
        var enableRedirectReadingPageBackgroundPath: Boolean = false,
        var enableCustomBookLastPage: Boolean = false,
        var enableShowReaderPageChapterSaveRawPicture: Boolean = false,
        var enableShowReaderPageChapterSavePictureDialog: Boolean = false,
        var enableShowReaderPageChapterSaveAudioDialog: Boolean = false,
        var enableCopyReaderPageChapterComment: Boolean = false,
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
                SelectedModel("主页顶部宝箱提示"),
                SelectedModel("主页顶部战力提示"),
                SelectedModel("书架每日导读"),
                SelectedModel("书架顶部标题"),
                SelectedModel("书架去找书")
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

/**
 * 读取选项
 * @param [errorAction] 错误操作
 * @return [File?]
 * @suppress Generate Documentation
 */
fun readOptionFile(errorAction: (String) -> Unit = {}): File? = try {
    File(optionPath).apply {
        parentFile?.mkdirs()
        if (!exists()) {
            createNewFile()
            writeText(defaultOptionEntity.toJSONString())
        }
    }
} catch (e: Throwable) {
    errorAction.invoke(e.message ?: "读取配置文件失败")
    e.loge()
    null
}

fun readOptionEntity(): OptionEntity {
    val file = readOptionFile {
        it.loge()
    } ?: return defaultOptionEntity
    return try {
        file.readText().parseObject<OptionEntity>().apply {
            val newAdvOptionConfigurations = defaultOptionEntity.advOption
            val newInterceptConfigurations = defaultOptionEntity.interceptOption
            val newViewHideOptionConfigurations =
                defaultOptionEntity.viewHideOption.homeOption.configurations
            val newBookDetailOptionConfigurations =
                defaultOptionEntity.viewHideOption.bookDetailOptions
            val newAutomaticReceiveOptionConfigurations = defaultOptionEntity.automatizationOption
            val newSearchOption = defaultOptionEntity.viewHideOption.searchOption
            advOption = advOption.merge(
                newAdvOptionConfigurations
            )
            interceptOption = interceptOption.merge(
                newInterceptConfigurations
            )

            viewHideOption.homeOption.configurations =
                viewHideOption.homeOption.configurations.merge(
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
}

/**
 * 写入选项
 * @param [optionEntity] 期权实体
 * @suppress Generate Documentation
 */
fun writeOptionFile(optionEntity: OptionEntity) = try {
    readOptionFile()?.writeText(optionEntity.toJSONString()) ?: false
    true
} catch (e: Throwable) {
    e.loge()
    false
}

/**
 * 更新选项实体
 * @return [Boolean]
 * @suppress Generate Documentation
 */
fun updateOptionEntity(): Boolean = writeOptionFile(HookEntry.optionEntity)

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

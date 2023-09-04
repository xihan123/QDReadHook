package cn.xihan.qdds

import android.os.Environment
import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.highcapable.yukihookapi.hook.log.loggerE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2022/8/28 16:13
 * @介绍 :
 */

/**
 * 配置模型
 * @param allowDisclaimers 是否允许免责声明
 * @param currentDisclaimersVersionCode 当前免责声明版本
 * @param latestDisclaimersVersionCode 最新免责声明版本
 * @param mainOption 主配置
 * @param advOption 广告配置
 * @param shieldOption 屏蔽配置
 * @param startImageOption 启动图配置
 * @param bookshelfOption 书架配置
 * @param readPageOption 阅读页配置
 * @param interceptOption 拦截配置
 * @param viewHideOption 控件隐藏配置
 * @param replaceRuleOption 替换配置
 * @param hideBenefitsOption 隐藏福利配置
 */
@Keep
@Serializable
data class OptionEntity(
    @SerialName("allowDisclaimers") var allowDisclaimers: Boolean = false,
    @SerialName("currentDisclaimersVersion") var currentDisclaimersVersionCode: Int = 0,
    @SerialName("latestDisclaimersVersion") var latestDisclaimersVersionCode: Int = 1,
    @SerialName("advOption") var advOption: AdvOption = AdvOption(),
    @SerialName("mainOption") var mainOption: MainOption = MainOption(),
    @SerialName("shieldOption") var shieldOption: ShieldOption = ShieldOption(),
    @SerialName("startImageOption") var startImageOption: StartImageOption = StartImageOption(),
    @SerialName("bookshelfOption") var bookshelfOption: BookshelfOption = BookshelfOption(),
    @SerialName("readPageOption") var readPageOption: ReadPageOption = ReadPageOption(),
    @SerialName("interceptOption") var interceptOption: InterceptOption = InterceptOption(),
    @SerialName("viewHideOption") var viewHideOption: ViewHideOption = ViewHideOption(),
    @SerialName("replaceRuleOption") var replaceRuleOption: ReplaceRuleOption = ReplaceRuleOption(),
    @SerialName("hideBenefitsOption") var hideBenefitsOption: HideWelfareOption = HideWelfareOption(),
) {
    /**
     * 广告配置
     * @param configurations 广告配置列表
     */
    @Keep
    @Serializable
    data class AdvOption(
        @SerialName("configurations") var configurations: MutableList<SelectedModel> = mutableListOf(
            SelectedModel("GDT(TX)广告"),
            SelectedModel("检查更新"),
            SelectedModel("主页-每日阅读广告"),
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
        )
    )

    /**
     * 主配置
     * @param packageName 包名
     * @param enableAutoSign 启用自动签到
     * @param enableReceiveReadingCreditsAutomatically 启用自动领取阅读积分
     * @param enablePostToShowImageUrl 启用发帖显示图片地址
     * @param enableLocalCard 启用本地至尊卡
     * @param enableFreeAdReward 启用免广告奖励
     * @param freeAdRewardAutoExitTime 免广告奖励自动退出时间
     * @param enableIgnoreFansValueJumpLimit 启用忽略粉丝值跳转限制
     * @param enableIgnoreFreeSubscribeLimit 启用忽略免费批量订阅限制
     * @param enableUnlockMemberBackground 启用解锁会员背景
     * @param enableHideAppIcon 启用隐藏应用图标
     * @param enableNewStore 启用新版精选
     * @param enableExportEmoji 启用导出表情包
     * @param enableForceTrialMode 启用强制试用模式
     * @param enableTestFunction 启用测试功能
     */
    @Keep
    @Serializable
    data class MainOption(
        @SerialName("packageName") var packageName: String = "com.qidian.QDReader",
        @SerialName("enableAutoSign") var enableAutoSign: Boolean = false,
        @SerialName("enableReceiveReadingCreditsAutomatically") var enableReceiveReadingCreditsAutomatically: Boolean = false,
        @SerialName("enablePostToShowImageUrl") var enablePostToShowImageUrl: Boolean = false,
        @SerialName("enableLocalCard") var enableLocalCard: Boolean = false,
        @SerialName("enableFreeAdReward") var enableFreeAdReward: Boolean = false,
        @SerialName("freeAdRewardAutoExitTime") var freeAdRewardAutoExitTime: Int = 3,
        @SerialName("enableIgnoreFansValueJumpLimit") var enableIgnoreFansValueJumpLimit: Boolean = false,
        @SerialName("enableIgnoreFreeSubscribeLimit") var enableIgnoreFreeSubscribeLimit: Boolean = false,
        @SerialName("enableUnlockMemberBackground") var enableUnlockMemberBackground: Boolean = false,
        @SerialName("enableHideAppIcon") var enableHideAppIcon: Boolean = false,
        @SerialName("enableNewStore") var enableNewStore: Boolean = false,
        @SerialName("enableExportEmoji") var enableExportEmoji: Boolean = false,
//        @SerialName("enableImportAudio") var enableImportAudio: Boolean = false,
        @SerialName("enableForceTrialMode") var enableForceTrialMode: Boolean = false,
        @SerialName("enableTestFunction") var enableTestFunction: Boolean = false,
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
    @Serializable
    data class ShieldOption(
        @SerialName("enableQuickShieldDialog") var enableQuickShieldDialog: Boolean = false,
        @SerialName("authorList") var authorList: MutableSet<String> = mutableSetOf(),
        @SerialName("bookNameList") var bookNameList: MutableSet<String> = mutableSetOf(),
        @SerialName("bookTypeList") var bookTypeList: Set<String> = emptySet(),
        @SerialName("configurations") var configurations: MutableList<SelectedModel> = mutableListOf(
            SelectedModel("搜索-发现(热词)"),
            SelectedModel("搜索-热门作品榜"),
            SelectedModel("搜索-人气标签榜"),
            SelectedModel("搜索-为你推荐"),
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
            SelectedModel("阅读-最后一页-看过此书的人还看过"),
            SelectedModel("阅读-最后一页-同类作品推荐"),
            SelectedModel("阅读-最后一页-推荐"),
            SelectedModel("分类-小编力荐、本周强推等更多")
        ),
        @SerialName("enableBookTypeEnhancedBlocking") var enableBookTypeEnhancedBlocking: Boolean = false,
    )

    /**
     * 启动图配置
     * @param enableCustomStartImage 启用自定义启动图
     * @param enableCustomLocalStartImage 启用自定义本地启动图
     * @param customStartImageUrlList 自定义启动图url列表
     * @param enableCaptureTheOfficialLaunchMapList 启用抓取官方启动图列表
     * @param officialLaunchMapList 官方启动图列表
     */
    @Keep
    @Serializable
    data class StartImageOption(
        @SerialName("enableCustomStartImage") var enableCustomStartImage: Boolean = false,
        @SerialName("enableCustomLocalStartImage") var enableCustomLocalStartImage: Boolean = false,
        @SerialName("customStartImageUrlList") var customStartImageUrlList: Set<String> = emptySet(),
        @SerialName("enableCaptureTheOfficialLaunchMapList") var enableCaptureTheOfficialLaunchMapList: Boolean = false,
        @SerialName("OfficialLaunchMapList") var officialLaunchMapList: MutableList<StartImageModel> = mutableListOf(),
    ) {

        /**
         * 启动图模型
         * @param name 名称
         * @param paperId id
         * @param preImageUrl 预览图url
         * @param imageUrl 图片url
         * @param isUsed 是否使用
         */
        @Keep
        @Serializable
        data class StartImageModel(
            @SerialName("name") var name: String = "",
            @SerialName("paperId") var paperId: Long = 0,
            @SerialName("preImageUrl") var preImageUrl: String = "",
            @SerialName("imageUrl") var imageUrl: String = "",
            @SerialName("isUsed") var isUsed: Boolean = false,
        )

    }

    /**
     * 书架配置
     * @param enableOldLayout 启用旧版布局
     * @param enableNewBookShelfLayout 启用旧版书架布局
     * @param enableCustomBookShelfTopImage 启用自定义书架顶部图片
     * @param enableSameNightAndDay 启用夜间和日间相同
     * @param lightModeCustomBookShelfTopImageModel 亮色模式自定义书架顶部图片模型
     * @param darkModeCustomBookShelfTopImageModel 暗色模式自定义书架顶部图片模型
     */
    @Keep
    @Serializable
    data class BookshelfOption(
        @SerialName("enableOldBookShelfLayout") var enableNewBookShelfLayout: Boolean = false,
        @SerialName("enableOldLayout") var enableOldLayout: Boolean = false,
        @SerialName("enableCustomBookShelfTopImage") var enableCustomBookShelfTopImage: Boolean = false,
        @SerialName("enableSameNightAndDay") var enableSameNightAndDay: Boolean = true,
        @SerialName("lightModeCustomBookShelfTopImageModel") var lightModeCustomBookShelfTopImageModel: CustomBookShelfTopImageModel = CustomBookShelfTopImageModel(),
        @SerialName("darkModeCustomBookShelfTopImageModel") var darkModeCustomBookShelfTopImageModel: CustomBookShelfTopImageModel = CustomBookShelfTopImageModel(),
    ) {

        /**
         * 自定义书架顶部图片模型
         * @param border01 边框1
         * @param font 字体
         * @param fontHLight 字体高亮
         * @param fontLight 字体亮色
         * @param fontOnSurface 字体表面
         * @param surface01 表面1
         * @param surfaceIcon 表面图标
         * @param headImage 顶部图片
         */
        @Keep
        @Serializable
        data class CustomBookShelfTopImageModel(
            @SerialName("border01") var border01: String = "",
            @SerialName("font") var font: String = "",
            @SerialName("fontHLight") var fontHLight: String = "",
            @SerialName("fontLight") var fontLight: String = "",
            @SerialName("fontOnSurface") var fontOnSurface: String = "",
            @SerialName("surface01") var surface01: String = "",
            @SerialName("surfaceIcon") var surfaceIcon: String = "",
            @SerialName("headImage") var headImage: String = "",
        )
    }

    /**
     * 阅读页配置
     * @param enableCustomReaderThemePath 启用自定义阅读页主题路径
     * @param enableCustomBookLastPage 启用自定义书籍最后一页
     * @param enableShowReaderPageChapterSaveRawPicture 启用显示阅读页章节保存原图
     * @param enableShowReaderPageChapterSavePictureDialog 启用显示阅读页章节保存图片对话框
     * @param enableShowReaderPageChapterSaveAudioDialog 启用显示阅读页章节保存音频对话框
     * @param enableCopyReaderPageChapterComment 启用复制阅读页章节评论
     * @param enableReadTimeDouble 启用阅读时间翻倍
     * @param enableVIPChapterTime 启用VIP章节时间
     * @param doubleSpeed 阅读时间倍速
     */
    @Keep
    @Serializable
    data class ReadPageOption(
        @SerialName("enableCustomReaderThemePath") var enableCustomReaderThemePath: Boolean = false,
        @SerialName("enableCustomBookLastPage") var enableCustomBookLastPage: Boolean = false,
        @SerialName("enableShowReaderPageChapterSaveRawPicture") var enableShowReaderPageChapterSaveRawPicture: Boolean = false,
        @SerialName("enableShowReaderPageChapterSavePictureDialog") var enableShowReaderPageChapterSavePictureDialog: Boolean = false,
        @SerialName("enableShowReaderPageChapterSaveAudioDialog") var enableShowReaderPageChapterSaveAudioDialog: Boolean = false,
        @SerialName("enableCopyReaderPageChapterComment") var enableCopyReaderPageChapterComment: Boolean = false,
        @SerialName("enableReadTimeDouble") var enableReadTimeDouble: Boolean = false,
        @SerialName("enableVIPChapterTime") var enableVIPChapterTime: Boolean = false,
        @SerialName("doubleSpeed") var doubleSpeed: Int = 5
    )

    /**
     * 拦截配置
     * @param configurations 拦截配置列表
     */
    @Keep
    @Serializable
    data class InterceptOption(
        @SerialName("configurations") var configurations: MutableList<SelectedModel> = mutableListOf(
            SelectedModel("隐私政策更新弹框"),
            SelectedModel("同意隐私政策弹框"),
            SelectedModel("WebSocket"),
            SelectedModel("青少年模式请求"),
            SelectedModel("闪屏广告页面"),
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
        )
    )

    /**
     * 控件隐藏配置
     * @param enableHideRedDot 启用隐藏红点
     * @param enableSearchHideAllView 启用隐藏搜索全部控件
     * @param enableDisableQSNModeDialog 启用关闭青少年模式弹框
     * @param enableHideComicBannerAd 启用隐藏漫画banner广告
     * @param homeOption 首页配置
     * @param selectedOption 精选配置
     * @param findOption 发现配置
     * @param accountOption 用户页面配置
     * @param bookDetailOptions 书籍详情配置
     * @param bookLastPageOptions 阅读最后一页配置
     */
    @Keep
    @Serializable
    data class ViewHideOption(
        @SerialName("enableHideRedDot") var enableHideRedDot: Boolean = false,
        @SerialName("enableSearchHideAllView") var enableSearchHideAllView: Boolean = false,
        @SerialName("enableDisableQSNModeDialog") var enableDisableQSNModeDialog: Boolean = false,
        @SerialName("enableHideComicBannerAd") var enableHideComicBannerAd: Boolean = false,
        @SerialName("homeOption") var homeOption: HomeOption = HomeOption(),
        @SerialName("selectedOption") var selectedOption: SelectedOption = SelectedOption(),
        @SerialName("findOption") var findOption: FindOption = FindOption(),
        @SerialName("AccountOption") var accountOption: AccountOption = AccountOption(),
        @SerialName("BookDetailOptions") var bookDetailOptions: BookDetailOptions = BookDetailOptions(),
        @SerialName("BookLastPageOptions") var bookLastPageOptions: BookLastPageOptions = BookLastPageOptions()
    ) {
        /**
         * 主页配置
         * @param enableCaptureBottomNavigation 启用截取底部导航栏
         * @param configurations 主页配置列表
         * @param bottomNavigationConfigurations 底部导航栏配置
         */
        @Keep
        @Serializable
        data class HomeOption(
            @SerialName("enableCaptureBottomNavigation") var enableCaptureBottomNavigation: Boolean = false,
            @SerialName("configurations") var configurations: MutableList<SelectedModel> = mutableListOf(
                SelectedModel("主页顶部宝箱提示"),
                SelectedModel("主页顶部战力提示"),
                SelectedModel("书架每日导读"),
                SelectedModel("书架去找书"),
                SelectedModel("主页底部导航栏红点")
            ),
            @SerialName("bottomNavigationConfigurations") var bottomNavigationConfigurations: MutableList<SelectedModel> = mutableListOf()
        )

        /**
         * 精选配置
         * @param enableSelectedHide 启用隐藏精选
         * @param enableSelectedTitleHide 启用隐藏精选标题
         * @param configurations 精选配置列表
         * @param selectedTitleConfigurations 精选标题配置列表
         */
        @Keep
        @Serializable
        data class SelectedOption(
            @SerialName("enableSelectedHide") var enableSelectedHide: Boolean = false,
            @SerialName("enableSelectedTitleHide") var enableSelectedTitleHide: Boolean = false,
            @SerialName("selectedConfigurations") var configurations: MutableList<SelectedModel> = mutableListOf(
                SelectedModel("轮播图"),
                SelectedModel("轮播消息")
            ),
            @SerialName("selectedTitleConfigurations") var selectedTitleConfigurations: MutableList<SelectedModel> = mutableListOf()
        )


        /**
         * 发现配置
         * @param enableHideFindItem 启用隐藏发现选项
         * @param advItem 广告选项
         * @param broadCasts 广播选项
         * @param feedsItem 动态选项
         * @param filterConfItem 筛选选项
         * @param headItem 头部选项
         */
        @Keep
        @Serializable
        data class FindOption(
            @SerialName("enableHideFindItem") var enableHideFindItem: Boolean = false,
            @SerialName("broadCasts") var broadCasts: Boolean = false,
            @SerialName("feedsItem") var feedsItem: Boolean = false,
            @SerialName("advItem") var advItem: MutableList<SelectedModel> = mutableListOf(),
            @SerialName("filterConfItem") var filterConfItem: MutableList<SelectedModel> = mutableListOf(),
            @SerialName("headItem") var headItem: MutableList<SelectedModel> = mutableListOf(),
        )


        /**
         * 用户页面配置
         * @param enableNewAccountLayout 启用新版我的布局
         * @param enableHideAccount 启用隐藏用户页面
         * @param enableHideAccountRightTopRedDot 启用隐藏用户页面右上角红点
         * @param configurations 可用配置
         * @param newConfiguration 新可用配置
         */
        @Keep
        @Serializable
        data class AccountOption(
            @SerialName("enableNewAccountLayout") var enableNewAccountLayout: Boolean = false,
            @SerialName("enableHideAccount") var enableHideAccount: Boolean = false,
            @SerialName("enableHideAccountRightTopRedDot") var enableHideAccountRightTopRedDot: Boolean = false,
            @SerialName("configurationsOptionList") var configurations: MutableList<SelectedModel> = mutableListOf(),
            @SerialName("newConfiguration") var newConfiguration: MutableList<SelectedModel> = mutableListOf(),
        )

        /**
         * 书籍详情页面配置
         * @param enableHideBookDetail 启用隐藏书籍详情页面
         * @param configurations 已选配置集合
         */
        @Keep
        @Serializable
        @Immutable
        data class BookDetailOptions(
            @SerialName("enableHideBookDetail") var enableHideBookDetail: Boolean = false,
            @SerialName("configurations") var configurations: MutableList<SelectedModel> = mutableListOf(
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
        )

        /**
         * BookLastPageOptions
         * @param enableHideBookLastPage 启用隐藏书籍详情页面
         * @param configurations 已选配置集合
         */
        @Keep
        @Serializable
        data class BookLastPageOptions(
            @SerialName("enableHideBookLastPage") var enableHideBookLastPage: Boolean = false,
            @SerialName("selectedConfigurations") var configurations: MutableList<SelectedModel> = mutableListOf(
                SelectedModel("书友圈"),
                SelectedModel("看过此书的人还看过"),
                SelectedModel("推荐"),
                SelectedModel("同类作品推荐"),
                SelectedModel("收录此书的书单"),
                SelectedModel("试读")
            )
        )

    }

    /**
     * 替换配置
     * @param enableReplace 启用替换
     * @param replaceRuleList 替换规则列表
     * @param enableCustomDeviceInfo 启用自定义设备信息
     * @param enableSaveOriginalDeviceInfo 启用保存原始设备信息
     * @param customDeviceInfo 自定义设备信息
     * @param originalDeviceInfo 原始设备信息
     */
    @Keep
    @Serializable
    data class ReplaceRuleOption(
        @SerialName("enableReplace") var enableReplace: Boolean = false,
        @SerialName("replaceRuleList") var replaceRuleList: MutableList<ReplaceItem> = mutableListOf(),
        @SerialName("enableSaveOriginalDeviceInfo") var enableSaveOriginalDeviceInfo: Boolean = false,
        @SerialName("enableCustomDeviceInfo") var enableCustomDeviceInfo: Boolean = false,
        @SerialName("customDeviceInfo") var customDeviceInfo: CustomDeviceInfo = CustomDeviceInfo(),
        @SerialName("originalDeviceInfo") var originalDeviceInfo: CustomDeviceInfo = CustomDeviceInfo(),
    ) {

        /**
         * 替换项
         * @param replaceRuleName 替换规则名称
         * @param enableRegularExpressions 启用正则表达式
         * @param replaceRuleRegex 替换规则正则
         * @param replaceWith 替换为
         */
        @Keep
        @Serializable
        data class ReplaceItem(
            @SerialName("replaceRuleName") var replaceRuleName: String = "",
            @SerialName("enableRegularExpressions") var enableRegularExpressions: Boolean = false,
            @SerialName("replaceRuleRegex") var replaceRuleRegex: String = "",
            @SerialName("replaceWith") var replaceWith: String = "",
        )

        /**
         * customDeviceInfo
         * @param brand 设备厂商
         * @param model 设备型号
         * @param imei 设备IMEI
         * @param androidId 设备AndroidId
         * @param macAddress 设备Mac地址
         * @param serial 设备序列号
         * @param androidVersion 设备Android版本号
         * @param releaseVersion 设备系统版本号
         * @param screenHeight 设备屏幕高度
         * @param cpuInfo 设备CPU信息
         */
        @Keep
        @Serializable
        data class CustomDeviceInfo(
            @SerialName("brand") var brand: String = "",
            @SerialName("model") var model: String = "",
            @SerialName("imei") var imei: String = "",
            @SerialName("androidId") var androidId: String = "",
            @SerialName("macAddress") var macAddress: String = "02:00:00:00:00:00",
            @SerialName("serial") var serial: String = "",
            @SerialName("androidVersion") var androidVersion: String = "33",
            @SerialName("releaseVersion") var releaseVersion: String = "13",
            @SerialName("screenHeight") var screenHeight: Int = 2135,
            @SerialName("cpuInfo") var cpuInfo: String = "0000000000000000"
        ) {
            override fun toString(): String {
                return "brand:$brand\nmodel:$model\nimei:$imei\nandroidId:$androidId\nmacAddress:$macAddress\nserial:$serial\nandroidVersion:$androidVersion\nreleaseVersion:$releaseVersion\nscreenHeight:$screenHeight\ncpuInfo:$cpuInfo"
            }
        }

    }

    /**
     * 选中模型
     * @param title 标题
     * @param selected 是否选中
     */
    @Keep
    @Serializable
    data class SelectedModel(
        @SerialName("title") var title: String = "",
        @SerialName("selected") var selected: Boolean = false,
    )

    /**
     * 隐藏福利配置
     * @param enableHideWelfare 启用隐藏福利
     * @param configurations 配置
     * @param remoteCHideWelfareList 远程隐藏福利列表
     * @param hideWelfareList 隐藏福利列表
     */
    @Keep
    @Serializable
    data class HideWelfareOption(
        @SerialName("enableHideWelfare") var enableHideWelfare: Boolean = false,
        @SerialName("configurations") var configurations: MutableList<SelectedModel> = mutableListOf(
            SelectedModel("内部浏览器右上角菜单", true), SelectedModel("我的", false)
        ),
        @SerialName("remoteCHideWelfareList") var remoteCHideWelfareList: MutableSet<String> = mutableSetOf(
            "https://raw.githubusercontent.com/xihan123/AGE-API/master/details/HideWelfareList.json"
        ),
        @SerialName("hideWelfareList") var hideWelfareList: MutableSet<HideWelfare> = mutableSetOf()
    ) {

        /**
         * 隐藏福利模型
         * @param title 标题
         * @param imageUrl 图片地址
         * @param actionUrl 跳转地址
         */
        @Keep
        @Serializable
        data class HideWelfare(
            @SerialName("title") var title: String = "",
            @SerialName("imageUrl") var imageUrl: String = "",
            @SerialName("actionUrl") var actionUrl: String = ""
        )
    }


}

/**
 *读取配置文件模型
 */
fun readOptionEntity(): OptionEntity {
    val file = readOptionFile() ?: return defaultOptionEntity
    return try {
        if (file.readText().isNotEmpty()) {
            try {
                val kJson = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
                kJson.decodeFromString<OptionEntity>(file.readText()).apply {
                    val newOptionEntity = OptionEntity()
                    val newAdvOptionConfigurations = newOptionEntity.advOption.configurations
                    val newInterceptConfigurations = newOptionEntity.interceptOption.configurations
                    val newViewHideOptionConfigurations =
                        newOptionEntity.viewHideOption.homeOption.configurations
                    val newBookDetailOptionConfigurations =
                        newOptionEntity.viewHideOption.bookDetailOptions.configurations
                    val newHideWelfareOptionConfigurations =
                        newOptionEntity.hideBenefitsOption.configurations

                    advOption.configurations =
                        advOption.configurations.updateSelectedListOptionEntity(
                            newAdvOptionConfigurations
                        )
                    interceptOption.configurations =
                        interceptOption.configurations.updateSelectedListOptionEntity(
                            newInterceptConfigurations
                        )

                    viewHideOption.homeOption.configurations =
                        viewHideOption.homeOption.configurations.updateSelectedListOptionEntity(
                            newViewHideOptionConfigurations
                        )
                    viewHideOption.bookDetailOptions.configurations =
                        viewHideOption.bookDetailOptions.configurations.updateSelectedListOptionEntity(
                            newBookDetailOptionConfigurations
                        )
                    hideBenefitsOption.configurations =
                        hideBenefitsOption.configurations.updateSelectedListOptionEntity(
                            newHideWelfareOptionConfigurations
                        )
                }
            } catch (e: Exception) {
                loggerE(msg = "readOptionFile: ${e.message}")
                defaultOptionEntity
            }
        } else {
            defaultOptionEntity
        }
    } catch (e: Exception) {
        loggerE(msg = "readOptionEntity: ${e.message}")
        defaultOptionEntity
    }
}

/**
 * 读取配置文件
 */
fun readOptionFile(): File? = try {
    val file = File(
        "${Environment.getExternalStorageDirectory().absolutePath}/QDReader", "option.json"
    )
    val downloadFile = File(
        "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/QDReader",
        "option.json"
    )
    downloadFile.parentFile?.mkdirs()
    if (file.exists()) {
        // 移动文件 至 下载文件夹
        if (!downloadFile.exists()) {
            file.copyTo(downloadFile, true)
        }
        // 删除 file 父文件夹
        file.parentFile?.deleteRecursively()
    }
    if (!downloadFile.exists()) {
        downloadFile.createNewFile()
        downloadFile.writeText(Json.encodeToString(defaultOptionEntity))
    }
    downloadFile
} catch (e: Throwable) {
    loggerE(msg = "readOptionFile: ${e.message}")
    null
}

/**
 * 写入配置文件
 */
fun writeOptionFile(optionEntity: OptionEntity): Boolean = try {
    readOptionFile()?.writeText(Json.encodeToString(optionEntity))
    true
} catch (e: Exception) {
    loggerE(msg = "writeOptionFile: ${e.message}")
    false
}

/**
 * 返回一个默认的配置模型
 */
val defaultOptionEntity by lazy {
    OptionEntity(
        mainOption = OptionEntity.MainOption(
            packageName = "com.qidian.QDReader", enableAutoSign = true, enableLocalCard = true
        ), viewHideOption = OptionEntity.ViewHideOption(
            enableDisableQSNModeDialog = true,
            accountOption = OptionEntity.ViewHideOption.AccountOption(
                enableHideAccount = true, enableHideAccountRightTopRedDot = true
            )
        )
    )
}

val defaultSelectedList by lazy { mutableListOf<OptionEntity.SelectedModel>() }

/**
 * 更新配置
 */
fun updateOptionEntity(): Boolean = writeOptionFile(HookEntry.optionEntity)

/*
/**
 * 读取配置文件 并写入 用户ID 和 昵称
 * @param userId 用户ID
 * @param nickName 昵称
 */
fun readOptionFileAndWriteCustomText(
    map: Map<String, Any>,
//    userId: Long,
//    nickName: String,
): Boolean {
    val file = readOptionFile() ?: return false
    if (map.isEmpty()) return false
    return try {
        if (file.readText().isNotEmpty()) {
            try {
                val json = file.readText().parseObject()
                map.forEach { (t, u) ->
                    json[t] = u
                }
//                json["userId"] = userId
//                json["nickName"] = nickName
                file.writeText(json.toString())
                true
            } catch (e: Exception) {
                loggerE(msg = "readOptionFile: ${e.message}")
                false
            }
        } else {
            false
        }
    } catch (e: Exception) {
        loggerE(msg = "readOptionFile: ${e.message}")
        false
    }
}

 */
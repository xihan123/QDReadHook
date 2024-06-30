package cn.xihan.qdds.model


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CheckInDetailModel(
    @SerialName("AdvEnable")
    var advEnable: Int = 0,
    @SerialName("Balance")
    var balance: Int = 0,
    @SerialName("BasicStrategyId")
    var basicStrategyId: Int = 0,
    @SerialName("ChapterFragmentBalance")
    var chapterFragmentBalance: Int = 0,
    @SerialName("CheckInDescUrl")
    var checkInDescUrl: String = "",
    @SerialName("CheckInInfoOfCurrentWeek")
    var checkInInfoOfCurrentWeek: List<CheckInInfoOfCurrentWeekModel> = listOf(),
    @SerialName("CheckInInfoOfPreWeek")
    var checkInInfoOfPreWeek: List<CheckInInfoOfPreWeekModel> = listOf(),
    @SerialName("CheckInStatus")
    var checkInStatus: Int = 0,
    @SerialName("CurrentReadingTime")
    var currentReadingTime: Int = 0,
    @SerialName("CurrentWeekCheckInTipText")
    var currentWeekCheckInTipText: CurrentWeekCheckInTipTextModel = CurrentWeekCheckInTipTextModel(),
    @SerialName("DailyTask")
    var dailyTask: DailyTaskModel = DailyTaskModel(),
    @SerialName("DailyVideoStatus")
    var dailyVideoStatus: Int = 0,
    @SerialName("FreeRecheckChanceUsed")
    var freeRecheckChanceUsed: Int = 0,
    @SerialName("HasCheckIn")
    var hasCheckIn: Int = 0,
    @SerialName("HelpActionUrl")
    var helpActionUrl: String = "",
    @SerialName("IsMember")
    var isMember: Int = 0,
    @SerialName("IsStrategy202205")
    var isStrategy202205: Int = 0,
    @SerialName("LotteryInfo")
    var lotteryInfo: LotteryInfoModel = LotteryInfoModel(),
    @SerialName("MaxCount")
    var maxCount: Int = 0,
    @SerialName("MaxNoBrokenTimes")
    var maxNoBrokenTimes: Int = 0,
    @SerialName("MemberType")
    var memberType: Int = 0,
    @SerialName("MinGiftLen")
    var minGiftLen: Int = 0,
    @SerialName("NoBrokenTime")
    var noBrokenTime: Int = 0,
    @SerialName("PreWeekCheckInTipText")
    var preWeekCheckInTipText: PreWeekCheckInTipTextModel = PreWeekCheckInTipTextModel(),
    @SerialName("ReCheckPrice")
    var reCheckPrice: Int = 0,
    @SerialName("RedPCount")
    var redPCount: Int = 0,
    @SerialName("ShowAppStore")
    var showAppStore: Int = 0,
    @SerialName("SmallAccount")
    var smallAccount: Int = 0,
    @SerialName("TodayReadTime")
    var todayReadTime: TodayReadTimeModel = TodayReadTimeModel(),
    @SerialName("UserIcon")
    var userIcon: String = "",
    @SerialName("UserId")
    var userId: Int = 0,
    @SerialName("UserMode")
    var userMode: Int = 0,
    @SerialName("VideoFreeRecheckIn")
    var videoFreeRecheckIn: Int = 0,
    @SerialName("VideoRewardCount")
    var videoRewardCount: Int = 0,
    @SerialName("VideoRewardStatus")
    var videoRewardStatus: Int = 0,
    @SerialName("VideoTaskId")
    var videoTaskId: Int = 0,
    @SerialName("VideoTaskRewardCount")
    var videoTaskRewardCount: Int = 0,
    @SerialName("VideoTaskRewardCountV2")
    var videoTaskRewardCountV2: Int = 0,
    @SerialName("WeekNoBrokenTimes")
    var weekNoBrokenTimes: Int = 0,
    @SerialName("WeekReadTime")
    var weekReadTime: WeekReadTimeModel = WeekReadTimeModel(),
    @SerialName("WeekRewardCount")
    var weekRewardCount: Int = 0,
    @SerialName("WeekRewardStatus")
    var weekRewardStatus: Int = 0,
    @SerialName("WeekVideoRewardCount")
    var weekVideoRewardCount: Int = 0,
    @SerialName("WeekVideoRewardDesc")
    var weekVideoRewardDesc: String = "",
    @SerialName("WeekVideoRewardStatus")
    var weekVideoRewardStatus: Int = 0
) {
    @Keep
    @Serializable

    data class CheckInInfoOfCurrentWeekModel(
        @SerialName("CheckInDate")
        var checkInDate: Int = 0,
        @SerialName("CheckInStatus")
        var checkInStatus: Int = 0,
        @SerialName("CheckInTime")
        var checkInTime: Long = 0,
        @SerialName("IsChallengeFinished")
        var isChallengeFinished: Int = 0,
        @SerialName("IsConvertDay")
        var isConvertDay: Int = 0,
        @SerialName("NoBreakRewardBubble")
        var noBreakRewardBubble: NoBreakRewardBubbleModel? = null,
        @SerialName("Rewards")
        var rewards: List<RewardModel> = listOf(),
        @SerialName("ShowType")
        var showType: Int = 0
    ) {
        @Keep
        @Serializable

        data class NoBreakRewardBubbleModel(
            @SerialName("NoBreakType")
            var noBreakType: String = "",
            @SerialName("Reward")
            var reward: String = ""
        )

        @Keep
        @Serializable

        data class RewardModel(
            @SerialName("Count")
            var count: Int = 0,
            @SerialName("HasVideoUrge")
            var hasVideoUrge: Int = 0,
            @SerialName("LotteryCount")
            var lotteryCount: Int = 0,
            @SerialName("RewardImage")
            var rewardImage: String = "",
            @SerialName("RewardName")
            var rewardName: String = "",
            @SerialName("RewardShowText")
            var rewardShowText: String = "",
            @SerialName("RewardSimpleName")
            var rewardSimpleName: String = "",
            @SerialName("StrategyId")
            var strategyId: Int = 0,
            @SerialName("Type")
            var type: Int = 0
        )
    }

    @Keep
    @Serializable

    data class CheckInInfoOfPreWeekModel(
        @SerialName("CheckInDate")
        var checkInDate: Int = 0,
        @SerialName("CheckInStatus")
        var checkInStatus: Int = 0,
        @SerialName("CheckInTime")
        var checkInTime: Long = 0,
        @SerialName("IsChallengeFinished")
        var isChallengeFinished: Int = 0,
        @SerialName("IsConvertDay")
        var isConvertDay: Int = 0,
        @SerialName("Rewards")
        var rewards: List<RewardModel> = listOf(),
        @SerialName("ShowType")
        var showType: Int = 0
    ) {
        @Keep
        @Serializable

        data class RewardModel(
            @SerialName("Count")
            var count: Int = 0,
            @SerialName("HasVideoUrge")
            var hasVideoUrge: Int = 0,
            @SerialName("LotteryCount")
            var lotteryCount: Int = 0,
            @SerialName("RewardImage")
            var rewardImage: String = "",
            @SerialName("RewardName")
            var rewardName: String = "",
            @SerialName("RewardShowText")
            var rewardShowText: String = "",
            @SerialName("RewardSimpleName")
            var rewardSimpleName: String = "",
            @SerialName("StrategyId")
            var strategyId: Int = 0,
            @SerialName("Type")
            var type: Int = 0
        )
    }

    @Keep
    @Serializable

    data class CurrentWeekCheckInTipTextModel(
        @SerialName("HighlightText")
        var highlightText: String = "",
        @SerialName("PreText")
        var preText: String = ""
    )

    @Keep
    @Serializable

    data class DailyTaskModel(
        @SerialName("AllFinishedStatus")
        var allFinishedStatus: Int = 0,
        @SerialName("DailyTaskRewardRemain")
        var dailyTaskRewardRemain: Int = 0,
        @SerialName("RewardCount")
        var rewardCount: Int = 0,
        @SerialName("Target")
        var target: Int = 0,
        @SerialName("TaskId")
        var taskId: Long = 0,
        @SerialName("Type")
        var type: Int = 0
    )

    @Keep
    @Serializable

    data class LotteryInfoModel(
        @SerialName("Count")
        var count: Int = 0,
        @SerialName("HasVideoUrge")
        var hasVideoUrge: Int = 0,
        @SerialName("LotteryCount")
        var lotteryCount: Int = 0,
        @SerialName("Rewards")
        var rewards: List<RewardModel> = listOf(),
        @SerialName("VideoUrgeButtonText")
        var videoUrgeButtonText: String = "",
        @SerialName("VideoUrgeText")
        var videoUrgeText: String = ""
    ) {
        @Keep
        @Serializable

        data class RewardModel(
            @SerialName("Count")
            var count: Int = 0,
            @SerialName("HasVideoUrge")
            var hasVideoUrge: Int = 0,
            @SerialName("LotteryCount")
            var lotteryCount: Int = 0,
            @SerialName("RewardName")
            var rewardName: String = "",
            @SerialName("Type")
            var type: Int = 0
        )
    }

    @Keep
    @Serializable

    data class PreWeekCheckInTipTextModel(
        @SerialName("HighlightText")
        var highlightText: String = "",
        @SerialName("PreText")
        var preText: String = ""
    )

    @Keep
    @Serializable

    data class TodayReadTimeModel(
        @SerialName("NextLevelReadTimeGap")
        var nextLevelReadTimeGap: Int = 0,
        @SerialName("NextVipLevelReadTimeGap")
        var nextVipLevelReadTimeGap: Int = 0,
        @SerialName("ReadTime")
        var readTime: Int = 0,
        @SerialName("ReadTimeRewardRemain")
        var readTimeRewardRemain: Int = 0,
        @SerialName("VipReadTime")
        var vipReadTime: Int = 0
    )

    @Keep
    @Serializable

    data class WeekReadTimeModel(
        @SerialName("NextLevelReadTimeGap")
        var nextLevelReadTimeGap: Int = 0,
        @SerialName("NextVipLevelReadTimeGap")
        var nextVipLevelReadTimeGap: Int = 0,
        @SerialName("ReadTime")
        var readTime: Int = 0,
        @SerialName("ReadTimeRewardRemain")
        var readTimeRewardRemain: Int = 0,
        @SerialName("VipReadTime")
        var vipReadTime: Int = 0
    )
}
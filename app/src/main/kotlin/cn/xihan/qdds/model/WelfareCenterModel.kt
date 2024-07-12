package cn.xihan.qdds.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class WelfareCenterModel(
    @SerialName("Avatar") var avatar: String = "",
    @SerialName("BaizeModule") var baizeModule: BaizeModuleModel = BaizeModuleModel(),
    @SerialName("CountdownBenefitModule") var countdownBenefitModule: CountdownBenefitModuleModel = CountdownBenefitModuleModel(),
    @SerialName("EncryptedGuid") var encryptedGuid: String = "",
    @SerialName("EntranceTabItems") var entranceTabItems: List<EntranceTabItemModel> = listOf(),
    @SerialName("FragmentCnt") var fragmentCnt: Int = 0,
    @SerialName("Guid") var guid: String = "",
    @SerialName("InitialStatus") var initialStatus: Int = 0,
    @SerialName("MonthBenefitModule") var monthBenefitModule: MonthBenefitModuleModel = MonthBenefitModuleModel(),
    @SerialName("NickName") var nickName: String = "",
    @SerialName("RewardScore") var rewardScore: String = "",
    @SerialName("TreasureBox") var treasureBox: TreasureBoxModel = TreasureBoxModel(),
    @SerialName("VideoBenefitModule") var videoBenefitModule: VideoBenefitModuleModel = VideoBenefitModuleModel()
) {

    @Serializable
    data class BaizeModuleModel(
        @SerialName("BubbleText") var bubbleText: List<String> = listOf(),
        @SerialName("DecorateId") var decorateId: Int = 0,
        @SerialName("DecorateTimeout") var decorateTimeout: String = "",
        @SerialName("DecrateStauts") var decrateStauts: Int = 0,
        @SerialName("Energy") var energy: Int = 0
    )


    @Serializable
    data class CountdownBenefitModuleModel(
        @SerialName("CurrentTime") var currentTime: Long = 0,
        @SerialName("ExpireTime") var expireTime: Long = 0,
        @SerialName("TaskList") var taskList: List<TaskModel> = listOf(),
        @SerialName("Title") var title: String = ""
    )


    @Serializable
    data class EntranceTabItemModel(
        @SerialName("LinkUrl") var linkUrl: String = "",
        @SerialName("TabImgUrl") var tabImgUrl: String = "",
        @SerialName("TabName") var tabName: String = ""
    )


    @Serializable
    data class MonthBenefitModuleModel(
        @SerialName("CurrentTime") var currentTime: Long = 0,
        @SerialName("TaskList") var taskList: List<TaskModel> = listOf(),
        @SerialName("Title") var title: String = ""
    )


    @Serializable
    data class TreasureBoxModel(
        @SerialName("Desc") var desc: String = "",
        @SerialName("IntervalTime") var intervalTime: String = "",
        @SerialName("IsFinished") var isFinished: Int = 0,
        @SerialName("IsFreeBox") var isFreeBox: Int = 0,
        @SerialName("IsPopout") var isPopout: Int = 0,
        @SerialName("TaskId") var taskId: String = "",
        @SerialName("TaskRawId") var taskRawId: String = ""
    )


    @Serializable
    data class VideoBenefitModuleModel(
        @SerialName("Process") var process: Int = 0,
        @SerialName("RotateText") var rotateText: List<String> = listOf(),
        @SerialName("TaskList") var taskList: List<TaskModel> = listOf(),
        @SerialName("Title") var title: String = ""
    )
}


@Serializable
data class TaskModel(
    @SerialName("CompleteTime") var completeTime: Int = 0,
    @SerialName("Desc") var desc: String = "",
    @SerialName("DisplaySort") var displaySort: Int = 0,
    @SerialName("Icon") var icon: String = "",
    @SerialName("IsFinished") var isFinished: Int = 0,
    @SerialName("IsReceived") var isReceived: Int = 0,
    @SerialName("MileStoneType") var mileStoneType: Int = 0,
    @SerialName("Process") var process: Int = 0,
    @SerialName("RewardDesc") var rewardDesc: String? = "",
    @SerialName("TaskId") var taskId: String = "",
    @SerialName("TaskRawId") var taskRawId: String = "",
    @SerialName("TaskType") var taskType: Int = 0,
    @SerialName("Total") var total: Int = 0,
    @SerialName("Unit") var unit: Int = 0,
    @SerialName("Title") var title: String = ""
) 

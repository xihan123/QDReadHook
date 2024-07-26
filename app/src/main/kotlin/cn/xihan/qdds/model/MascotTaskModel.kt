package cn.xihan.qdds.model


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MascotTaskModel(
    @SerialName("CurrentAmount")
    var currentAmount: Int = 0,
    @SerialName("DiscoverData")
    var discoverData: DiscoverDataModel = DiscoverDataModel(),
    @SerialName("ExchangeData")
    var exchangeData: ExchangeDataModel = ExchangeDataModel(),
    @SerialName("TaskList")
    var taskList: List<TaskModel> = listOf()
) {
    @Keep
    @Serializable
    data class DiscoverDataModel(
        @SerialName("PositionList")
        var positionList: List<PositionModel> = listOf(),
        @SerialName("TotalAmount")
        var totalAmount: Int = 0
    ) {
        @Keep
        @Serializable
        data class PositionModel(
            @SerialName("Icon")
            var icon: String = "",
            @SerialName("Name")
            var name: String = "",
            @SerialName("PositionKey")
            var positionKey: Int = 0,
            @SerialName("Status")
            var status: Int = 0
        )
    }

    @Keep
    @Serializable
    data class ExchangeDataModel(
        @SerialName("AvailableList")
        var availableList: List<AvailableModel> = listOf(),
        @SerialName("OpenText")
        var openText: String = ""
    ) {
        @Keep
        @Serializable
        data class AvailableModel(
            @SerialName("ActionUrl")
            var actionUrl: String = "",
            @SerialName("Name")
            var name: String = ""
        )
    }

    @Keep
    @Serializable
    data class TaskModel(
        @SerialName("ActionUrl")
        var actionUrl: String = "",
        @SerialName("Amount")
        var amount: Int = 0,
        @SerialName("Icon")
        var icon: String = "",
        @SerialName("IsFinish")
        var isFinish: Int = 0,
        @SerialName("Name")
        var name: String = "",
        @SerialName("RewardType")
        var rewardType: Int = 0,
        @SerialName("Type")
        var type: Int = 0
    )
}
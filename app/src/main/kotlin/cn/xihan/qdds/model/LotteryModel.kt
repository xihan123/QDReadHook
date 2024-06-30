package cn.xihan.qdds.model


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class LotteryModel(
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
    @SerialName("Type")
    var type: Int = 0
)
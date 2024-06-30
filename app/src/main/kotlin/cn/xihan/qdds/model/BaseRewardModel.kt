package cn.xihan.qdds.model


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable

data class BaseRewardModel(
    @SerialName("RewardList")
    var rewardList: List<RewardModel> = listOf(),
    @SerialName("RiskConf")
    var riskConf: RiskConfModel? = null
) {
    @Keep
    @Serializable

    data class RewardModel(
        @SerialName("Desc")
        var desc: String = "",
        @SerialName("Icon")
        var icon: String = ""
    )
}

@Keep
@Serializable

data class RiskConfModel(
    @SerialName("BanId")
    var banId: String = "",
    @SerialName("BanMessage")
    var banMessage: String = "",
    @SerialName("CaptchaAId")
    var captchaAId: String = "",
    @SerialName("CaptchaType")
    var captchaType: String = "",
    @SerialName("CaptchaURL")
    var captchaURL: String = "",
    @SerialName("Challenge")
    var challenge: String = "",
    @SerialName("Gt")
    var gt: String = "",
    @SerialName("NewCaptcha")
    var newCaptcha: String = "",
    @SerialName("Offline")
    var offline: String = "",
    @SerialName("PhoneNumber")
    var phoneNumber: String = "",
    @SerialName("SessionKey")
    var sessionKey: String = ""
) 
package cn.xihan.qdds.model


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ExchangeChapterCardModel(
    @SerialName("Balance")
    var balance: String = "",
    @SerialName("Goods")
    var goods: List<GoodModel> = listOf(),
    @SerialName("HelpDesc")
    var helpDesc: String = "",
    @SerialName("LatestCoin")
    var latestCoin: String = "",
    @SerialName("Notice")
    var notice: String = ""
) {
    @Keep
    @Serializable
    data class GoodModel(
        @SerialName("ChapterCardCount")
        var chapterCardCount: String = "",
        @SerialName("GoodCount")
        var goodCount: String = "",
        @SerialName("GoodId")
        var goodId: String = "",
        @SerialName("GoodLeftCount")
        var goodLeftCount: String = "",
        @SerialName("GoodName")
        var goodName: String = "",
        @SerialName("GoodPic")
        var goodPic: String = "",
        @SerialName("GoodScore")
        var goodScore: String = "",
        @SerialName("GoodsDesc")
        var goodsDesc: String = "",
        @SerialName("GoodsType")
        var goodsType: String = "",
        @SerialName("IsOver")
        var isOver: String = "",
        @SerialName("LimitDesc")
        var limitDesc: String = "",
        @SerialName("Status")
        var status: String = "",
        @SerialName("Url")
        var url: String = ""
    )
}
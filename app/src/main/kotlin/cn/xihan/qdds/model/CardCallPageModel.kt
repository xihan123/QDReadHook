package cn.xihan.qdds.model


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CardCallPageModel(
    @SerialName("BackgroundImg")
    var backgroundImg: String = "",
    @SerialName("Balance")
    var balance: Int = 0,
    @SerialName("CardPoolInfoList")
    var cardPoolInfoList: List<CardPoolInfoModel> = listOf(),
    @SerialName("CardTicket")
    var cardTicket: String = "",
    @SerialName("CardTotalCount")
    var cardTotalCount: Int = 0,
    @SerialName("Cards")
    var cards: List<CardModel> = listOf(),
    @SerialName("CurrPoolId")
    var currPoolId: Int = 0,
    @SerialName("CurrPoolType")
    var currPoolType: Int = 0,
    @SerialName("FreeNum")
    var freeNum: Int = 0,
    @SerialName("HelpActionUrl")
    var helpActionUrl: String = "",
    @SerialName("HelpTitle")
    var helpTitle: String = "",
    @SerialName("IsFirstReBuy")
    var isFirstReBuy: Int = 0,
    @SerialName("IsMember")
    var isMember: Int = 0,
    @SerialName("IsNewUser")
    var isNewUser: Int = 0,
    @SerialName("LeftTicket")
    var leftTicket: String = "",
    @SerialName("MallActionUrl")
    var mallActionUrl: String = "",
    @SerialName("MaxFreeNum")
    var maxFreeNum: Int = 0,
    @SerialName("NextFreeTime")
    var nextFreeTime: Long = 0,
    @SerialName("OnceCost")
    var onceCost: Int = 0,
    @SerialName("OnceCostTicket")
    var onceCostTicket: Int = 0,
    @SerialName("OriginTenCost")
    var originTenCost: Int = 0,
    @SerialName("OriginTenCostTicket")
    var originTenCostTicket: Int = 0,
    @SerialName("OwnCardCount")
    var ownCardCount: Int = 0,
    @SerialName("RuleActionUrl")
    var ruleActionUrl: String = "",
    @SerialName("RuleDesc")
    var ruleDesc: String = "",
    @SerialName("SummonTenMark")
    var summonTenMark: String = "",
    @SerialName("SummonTenText")
    var summonTenText: String = "",
    @SerialName("TailDesc")
    var tailDesc: String = "",
    @SerialName("TailTitle")
    var tailTitle: String = "",
    @SerialName("TenCost")
    var tenCost: Int = 0,
    @SerialName("TenCostTicket")
    var tenCostTicket: Int = 0,
    @SerialName("TicketNum")
    var ticketNum: Int = 0,
    @SerialName("Title")
    var title: String = "",
    @SerialName("UserId")
    var userId: Int = 0,
    @SerialName("Users")
    var users: List<UserModel> = listOf()
) {
    @Keep
    @Serializable
    data class CardPoolInfoModel(
        @SerialName("PoolId")
        var poolId: Long = 0,
        @SerialName("PoolType")
        var poolType: Int = 0,
        @SerialName("Title")
        var title: String = ""
    )

    @Keep
    @Serializable
    data class CardModel(
        @SerialName("CardId")
        var cardId: Long = 0,
        @SerialName("CardName")
        var cardName: String = "",
        @SerialName("ImageType")
        var imageType: Int = 0,
        @SerialName("ImageUrl")
        var imageUrl: String = "",
        @SerialName("MultipleImages")
        var multipleImages: MultipleImagesModel = MultipleImagesModel(),
        @SerialName("RoleId")
        var roleId: Long = 0,
        @SerialName("Type")
        var type: Int = 0
    ) {
        @Keep
        @Serializable
        data class MultipleImagesModel(
            @SerialName("Status")
            var status: Int = 0
        )
    }

    @Keep
    @Serializable
    data class UserModel(
        @SerialName("CardId")
        var cardId: Long = 0,
        @SerialName("CardName")
        var cardName: String = "",
        @SerialName("ImageType")
        var imageType: Int = 0,
        @SerialName("ImageUrl")
        var imageUrl: String = "",
        @SerialName("Images")
        var images: ImagesModel = ImagesModel(),
        @SerialName("IsDynamic")
        var isDynamic: Int = 0,
        @SerialName("RoleId")
        var roleId: Long = 0,
        @SerialName("Type")
        var type: Int = 0,
        @SerialName("UserId")
        var userId: Int = 0,
        @SerialName("UserImage")
        var userImage: String = "",
        @SerialName("UserName")
        var userName: String = ""
    ) {
        @Keep
        @Serializable
        data class ImagesModel(
            @SerialName("Status")
            var status: Int = 0
        )
    }
}


@Keep
@Serializable
data class CardCallModel(
    @SerialName("CardTicket")
    var cardTicket: String = "",
    @SerialName("HasSSR")
    var hasSSR: Int = 0,
    @SerialName("ImageType")
    var imageType: Int = 0,
    @SerialName("IsFirstReBuy")
    var isFirstReBuy: Int = 0,
    @SerialName("IsNewUser")
    var isNewUser: Int = 0,
    @SerialName("Items")
    var items: List<ItemModel> = listOf(),
    @SerialName("LeftTicket")
    var leftTicket: String = ""
) {
    @Keep
    @Serializable
    data class ItemModel(
        @SerialName("AllCanUse")
        var allCanUse: Int = 0,
        @SerialName("BookId")
        var bookId: Int = 0,
        @SerialName("BookName")
        var bookName: String = "",
        @SerialName("CardId")
        var cardId: String = "",
        @SerialName("CardName")
        var cardName: String = "",
        @SerialName("CardType")
        var cardType: Int = 0,
        @SerialName("Cbid")
        var cbid: Int = 0,
        @SerialName("CurrentLevel")
        var currentLevel: Int = 0,
        @SerialName("DonateUserId")
        var donateUserId: Int = 0,
        @SerialName("ImageType")
        var imageType: Int = 0,
        @SerialName("ImageUrl")
        var imageUrl: String = "",
        @SerialName("MaxLevel")
        var maxLevel: Int = 0,
        @SerialName("MultipleImages")
        var multipleImages: MultipleImagesModel = MultipleImagesModel(),
        @SerialName("Rank")
        var rank: Int = 0,
        @SerialName("RoleId")
        var roleId: String = "",
        @SerialName("SubTitle")
        var subTitle: String = "",
        @SerialName("Title")
        var title: String = "",
        @SerialName("Type")
        var type: Int = 0
    ) {
        @Keep
        @Serializable
        data class MultipleImagesModel(
            @SerialName("Status")
            var status: Int = 0
        )
    }
}
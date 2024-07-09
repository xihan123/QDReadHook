package cn.xihan.qdds.service

import cn.xihan.qdds.model.BaseDataModel
import cn.xihan.qdds.model.BaseModel
import cn.xihan.qdds.model.BaseRewardModel
import cn.xihan.qdds.model.CardCallModel
import cn.xihan.qdds.model.CardCallPageModel
import cn.xihan.qdds.model.CheckInDetailModel
import cn.xihan.qdds.model.ExchangeChapterCardModel
import cn.xihan.qdds.model.LotteryModel
import cn.xihan.qdds.model.WelfareCenterModel
import cn.xihan.qdds.util.customModelRequest
import com.skydoves.sandwich.ApiResponse
import io.ktor.client.HttpClient

interface QdService {

    suspend fun checkRisk(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<BaseRewardModel>>

    suspend fun getWelfareCenter(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<WelfareCenterModel>>

    suspend fun getWelfareReward(
        model: BaseModel<BaseDataModel>
    ): ApiResponse<BaseModel<BaseRewardModel>>

    suspend fun receiveWelfareReward(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<BaseRewardModel>>

    suspend fun checkInDetail(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<CheckInDetailModel>>

    suspend fun autoCheckIn(model: BaseModel<BaseDataModel>): ApiResponse<String>

    suspend fun normalCheckIn(model: BaseModel<BaseDataModel>): ApiResponse<String>

    suspend fun lotteryChance(model: BaseModel<BaseDataModel>): ApiResponse<String>

    suspend fun lottery(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<LotteryModel>>

    suspend fun exchangeChapterCard(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<ExchangeChapterCardModel>>

    suspend fun buyChapterCard(model: BaseModel<BaseDataModel>): ApiResponse<String>

    suspend fun gameTime(model: BaseModel<BaseDataModel>): ApiResponse<String>

    suspend fun getCardCallPage(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<CardCallPageModel>>

    suspend fun getCardCall(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<CardCallModel>>

}

class QdServiceImpl(
    private val httpClient: HttpClient
) : QdService {

    override suspend fun checkRisk(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<BaseRewardModel>> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun getWelfareCenter(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<WelfareCenterModel>> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun getWelfareReward(
        model: BaseModel<BaseDataModel>
    ): ApiResponse<BaseModel<BaseRewardModel>> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun receiveWelfareReward(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<BaseRewardModel>> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun checkInDetail(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<CheckInDetailModel>> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun autoCheckIn(model: BaseModel<BaseDataModel>): ApiResponse<String> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun normalCheckIn(model: BaseModel<BaseDataModel>): ApiResponse<String> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun lotteryChance(model: BaseModel<BaseDataModel>): ApiResponse<String> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun lottery(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<LotteryModel>> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun exchangeChapterCard(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<ExchangeChapterCardModel>> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun buyChapterCard(model: BaseModel<BaseDataModel>): ApiResponse<String> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun gameTime(model: BaseModel<BaseDataModel>): ApiResponse<String> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun getCardCallPage(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<CardCallPageModel>> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

    override suspend fun getCardCall(model: BaseModel<BaseDataModel>): ApiResponse<BaseModel<CardCallModel>> =
        model.data?.customModelRequest(httpClient) ?: throw Exception(model.message)

}
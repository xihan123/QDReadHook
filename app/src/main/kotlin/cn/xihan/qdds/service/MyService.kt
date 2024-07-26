package cn.xihan.qdds.service


import cn.xihan.qdds.model.BaseDataModel
import cn.xihan.qdds.model.BaseModel
import cn.xihan.qdds.util.Path
import cn.xihan.qdds.util.Path.HEADERS
import cn.xihan.qdds.util.customRequest
import com.skydoves.sandwich.ApiResponse
import io.ktor.client.HttpClient

interface MyService {

    suspend fun checkRisk(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun getWelfareCenter(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun getWelfareReward(taskId: String): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun receiveWelfareReward(taskId: String): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun checkInDetail(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun autoCheckIn(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun normalCheckIn(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun lotteryChance(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun lottery(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun exchangeChapterCard(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun buyChapterCard(goodId: String): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun gameTime(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun getCardCallPage(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun getCardCall(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun getMascotTaskList(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun getMascotClockIn(): ApiResponse<BaseModel<BaseDataModel>>

    suspend fun getMascotReward(type: Int): ApiResponse<BaseModel<BaseDataModel>>

}

class MyServiceImpl(
    private val httpClient: HttpClient
) : MyService {

    override suspend fun checkRisk() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.CHECK_RISK, headers = HEADERS
    )

    override suspend fun getWelfareCenter() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.WELFARE_CENTER, headers = HEADERS
    )

    override suspend fun getWelfareReward(taskId: String) =
        httpClient.customRequest<BaseModel<BaseDataModel>>(
            url = Path.MY_BASE_URL + Path.WELFARE_REWARD + "?task_id=$taskId",
            headers = HEADERS
        )

    override suspend fun receiveWelfareReward(taskId: String) =
        httpClient.customRequest<BaseModel<BaseDataModel>>(
            url = Path.MY_BASE_URL + Path.RECEIVE_WELFARE_REWARD + "?task_id=$taskId",
            headers = HEADERS
        )


    override suspend fun checkInDetail() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.CHECK_IN_DETAIL, headers = HEADERS
    )


    override suspend fun autoCheckIn() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.AUTO_CHECK_IN, headers = HEADERS
    )


    override suspend fun normalCheckIn() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.NORMAL_CHECK_IN, headers = HEADERS
    )

    override suspend fun lotteryChance() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.LOTTERY_CHANCE, headers = HEADERS
    )


    override suspend fun lottery() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.LOTTERY, headers = HEADERS
    )


    override suspend fun exchangeChapterCard() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.EXCHANGE_CHAPTER_CARD, headers = HEADERS
    )

    override suspend fun buyChapterCard(goodId: String) =
        httpClient.customRequest<BaseModel<BaseDataModel>>(
            url = Path.MY_BASE_URL + Path.BUY_CHAPTER_CARD + "?good_id=$goodId", headers = HEADERS
        )


    override suspend fun gameTime() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.GAME_TIME, headers = HEADERS
    )

    override suspend fun getCardCallPage() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.CARD_CALL_PAGE, headers = HEADERS
    )

    override suspend fun getCardCall() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.CARD_CALL, headers = HEADERS
    )

    override suspend fun getMascotTaskList() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.MASCOT_TASK_LIST, headers = HEADERS
    )

    override suspend fun getMascotClockIn() = httpClient.customRequest<BaseModel<BaseDataModel>>(
        url = Path.MY_BASE_URL + Path.MASCOT_CLOCK_IN, headers = HEADERS
    )

    override suspend fun getMascotReward(type: Int) =
        httpClient.customRequest<BaseModel<BaseDataModel>>(
            url = Path.MY_BASE_URL + Path.MASCOT_TASK_REWARD + "?reward_type=$type",
            headers = HEADERS
        )

}


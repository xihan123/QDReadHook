package cn.xihan.qdds.repository

import android.content.Context
import androidx.annotation.WorkerThread
import cn.xihan.qdds.service.MyService
import cn.xihan.qdds.service.QdService
import cn.xihan.qdds.util.Option.optionEntity
import cn.xihan.qdds.util.Path
import cn.xihan.qdds.util.isSelectedByTitle
import cn.xihan.qdds.util.loge
import cn.xihan.qdds.util.toast
import cn.xihan.qdds.util.wait
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.sandwich.suspendThen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2024/6/24 下午8:06
 * @介绍 :
 */
@WorkerThread
class RemoteRepository(
    private val context: Context, private val myService: MyService, private val qdService: QdService
) {

    // 自动领取延迟
    private val delayTime by lazy { optionEntity.taskOption.delayTime }

    // 自动领取选项懒加载
    private val autoGetOption by lazy { optionEntity.taskOption.configurations }

    init {
        "Injection RemoteRepository".loge()
        if (optionEntity.taskOption.baseUrl.isNotBlank()) {
            Path.MY_BASE_URL = optionEntity.taskOption.baseUrl
        }
    }

    fun checkRisk() = flow {
        myService.checkRisk().suspendThen(qdService::checkRisk).suspendOnSuccess {
            data.data?.riskConf?.let { riskModel ->
                emit(riskModel.banMessage.isNotBlank())
            } ?: throw Throwable(
                data.data?.riskConf?.banMessage ?: data.message.ifBlank { "未知错误" })
        }
    }.flowOn(Dispatchers.Default)

    fun autoCheckRisk(block: () -> Unit = {}) = runBlocking(Dispatchers.Default) {
        myService.checkRisk().suspendThen(qdService::checkRisk).suspendOnSuccess {
            data.data?.riskConf?.let { riskModel ->
                toast(riskModel.banMessage.ifBlank { "自动检测高风险未知错误" })
            } ?: block()
        }
    }

    fun checkInDetail() = flow {
        myService.checkInDetail().suspendThen(qdService::checkInDetail).suspendOnSuccess {
            data.data?.let { checkInDetailModel ->
                emit(checkInDetailModel)
            }
        }
    }.flowOn(Dispatchers.Default)

    fun checkIn(isMember: Boolean) = flow {
        if (isMember) {
            myService.autoCheckIn().suspendThen(qdService::autoCheckIn).suspendOnSuccess {
                emit(data)
            }
        } else {
            myService.normalCheckIn().suspendThen(qdService::normalCheckIn).suspendOnSuccess {
                emit(data)
            }
        }
    }.flowOn(Dispatchers.Default)

    fun lotteryChance() = flow {
        myService.lotteryChance().suspendThen(qdService::lotteryChance).suspendOnSuccess {
            emit(data)
        }
    }.flowOn(Dispatchers.Default)

    fun lottery() = flow {
        myService.lottery().suspendThen(qdService::lottery).suspendOnSuccess {
            data.data?.takeIf { it.rewardName.isNotBlank() }?.let { lotteryModel ->
                emit(lotteryModel)
            } ?: throw Throwable(data.message.ifBlank { "未知错误" })
        }
    }.flowOn(Dispatchers.Default)

    fun exchangeChapterCard() = flow {
        myService.exchangeChapterCard().suspendThen(qdService::exchangeChapterCard)
            .suspendOnSuccess {
                data.data?.let { exchangeChapterCardModel ->
                    emit(exchangeChapterCardModel)
                } ?: throw Throwable(data.message.ifBlank { "未知错误" })
            }
    }.flowOn(Dispatchers.Default)

    fun buyChapterCard(goodId: String) = flow {
        myService.buyChapterCard(goodId).suspendThen(qdService::buyChapterCard).suspendOnSuccess {
            data.takeIf { it.isNotBlank() }?.let { response ->
                val json = JSONObject(response)
                json.optJSONObject("Data")?.optJSONObject("RiskConf")?.let {
                    throw Throwable(
                        json.optString("Message").ifBlank { "兑换章节卡需要验证,自行手动领取" })
                }
                val result = json.optInt("result")
                if (result == 0) {
                    emit(true)
                } else {
                    throw Throwable(json.optString("Message").ifBlank { "未知错误" })
                }
            }
        }
    }.flowOn(Dispatchers.Default)

    fun getWelfareCenter() = flow {
        myService.getWelfareCenter().suspendThen(qdService::getWelfareCenter).suspendOnSuccess {
            data.data?.let { welfareCenterModel ->
                emit(welfareCenterModel)
            }
        }
    }.flowOn(Dispatchers.Default)

    fun getWelfareReward(taskId: String) = flow {
        myService.getWelfareReward(taskId).suspendThen(qdService::getWelfareReward)
            .suspendOnSuccess {
                data.data?.takeIf { it.riskConf == null }?.let { rewardModel ->
                    emit(rewardModel)
                } ?: throw Throwable(data.data?.riskConf?.banMessage
                    ?: data.message.ifBlank { "未知错误" })
            }
    }.flowOn(Dispatchers.Default)

    fun receiveWelfareReward(taskId: String) = flow {
        myService.receiveWelfareReward(taskId).suspendThen(qdService::receiveWelfareReward)
            .suspendOnSuccess {
                data.data?.takeIf { it.riskConf == null }?.let { rewardModel ->
                    emit(rewardModel)
                } ?: throw Throwable(data.data?.riskConf?.banMessage
                    ?: data.message.ifBlank { "未知错误" })
            }
    }.flowOn(Dispatchers.Default)

    fun gameTime() = flow {
        myService.gameTime().suspendThen(qdService::gameTime).suspendOnSuccess {
            emit(data)
        }
    }.flowOn(Dispatchers.Default)

    fun getCardCallPage() = flow {
        myService.getCardCallPage().suspendThen(qdService::getCardCallPage).suspendOnSuccess {
            data.data?.let { cardCallPageModel ->
                emit(cardCallPageModel)
            } ?: throw Throwable(data.message.ifBlank { "未知错误" })
        }
    }.flowOn(Dispatchers.Default)

    fun getCardCall() = flow {
        myService.getCardCall().suspendThen(qdService::getCardCall).suspendOnSuccess {
            data.data?.let { cardCallModel ->
                emit(cardCallModel)
            } ?: throw Throwable(data.message.ifBlank { "未知错误" })
        }
    }.flowOn(Dispatchers.Default)

    suspend fun autoGameTime() = myService.gameTime().suspendThen(qdService::gameTime)

    /**
     * 待稳定
     */
    fun autoGetWelfareReward() = runBlocking(Dispatchers.IO) {
        val list = autoGetOption.filter { it.selected && "福利中心" in it.title }
        if (list.isEmpty()) return@runBlocking
        myService.getWelfareCenter().suspendThen(qdService::getWelfareCenter).suspendOnSuccess {
            data.data?.let { welfareCenterModel ->
                with(welfareCenterModel) {
                    val countdownBenefitModuleTaskList = countdownBenefitModule.taskList
                    if (autoGetOption.isSelectedByTitle("福利中心-宝箱")) {
                        autoGetWelfareReward(treasureBox.taskId)
                    }
                    if (autoGetOption.isSelectedByTitle("福利中心-激励视频")) {
                        val remaining =
                            videoBenefitModule.taskList.size - videoBenefitModule.process
                        if (remaining > 0) {
                            val taskId = videoBenefitModule.taskList.first().taskId
                            for (i in 1..remaining) {
                                wait(delayTime) {
                                    autoGetWelfareReward(taskId)
                                }
                            }
                        }
                    }
                    if (autoGetOption.isSelectedByTitle("福利中心-额外看3次小视频得奖励")) {
                        countdownBenefitModuleTaskList.firstOrNull { it.title == "额外看3次小视频得奖励" }
                            ?.let {
                                val remaining = it.total - it.process
                                if (remaining > 0) {
                                    val taskId = it.taskId
                                    for (i in 1..remaining) {
                                        wait(delayTime) {
                                            autoGetWelfareReward(taskId)
                                        }
                                    }
                                }
                            }
                    }
                    if (autoGetOption.isSelectedByTitle("福利中心-额外看1次小视频")) {
                        countdownBenefitModuleTaskList.firstOrNull { it.title == "额外看1次小视频得奖励" }
                            ?.let {
                                val remaining = it.total - it.process
                                if (remaining > 0) {
                                    autoGetWelfareReward(it.taskId)
                                }
                            }
                    }
                    if (autoGetOption.isSelectedByTitle("福利中心-游戏十分钟")) {
                        countdownBenefitModuleTaskList.firstOrNull { it.title == "当日玩游戏10分钟" }
                            ?.let {
                                val remaining = (it.total - it.process) * 2
                                if (remaining > 0) {
                                    for (i in 1..remaining) {
                                        wait(delayTime) {
                                            autoGameTime()
                                        }
                                    }
                                } else if (it.isReceived == 0) {
                                    autoReceiveWelfareReward(it.taskId)
                                }
                            }
                    }
                    toast("福利中心奖励领取完毕")
                }
            }
        }

    }

    suspend fun autoGetWelfareReward(taskId: String) = withContext(Dispatchers.Default) {
        myService.getWelfareReward(taskId).suspendThen(qdService::getWelfareReward).suspendOnError {
            toast("错误信息: ${message()}")
        }
    }

    suspend fun autoReceiveWelfareReward(taskId: String) = withContext(Dispatchers.Default) {
        myService.receiveWelfareReward(taskId).suspendThen(qdService::receiveWelfareReward)
            .suspendOnError {
                toast("错误信息: ${message()}")
            }
    }

    private suspend fun toast(message: String) =
        withContext(Dispatchers.Main) { context.toast(message) }
}
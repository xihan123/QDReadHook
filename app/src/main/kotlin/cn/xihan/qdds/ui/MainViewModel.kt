package cn.xihan.qdds.ui

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.xihan.qdds.model.CheckInDetailModel
import cn.xihan.qdds.model.ExchangeChapterCardModel
import cn.xihan.qdds.model.WelfareCenterModel
import cn.xihan.qdds.repository.RemoteRepository
import cn.xihan.qdds.util.Option.optionEntity
import cn.xihan.qdds.util.Path
import cn.xihan.qdds.util.loge
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2024/6/21 下午11:50
 * @介绍 :
 */
class MainViewModel(
    private val context: Application,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _initialState: MutableState<Boolean> =
        mutableStateOf(optionEntity.taskOption.enableDefaultRequest)
    val initialState: State<Boolean> get() = _initialState

    private val _errorState: MutableState<String> = mutableStateOf("")
    val errorState get() = _errorState

    private val _checkRiskState: MutableState<Boolean?> = mutableStateOf(null)
    val checkRiskState: State<Boolean?> get() = _checkRiskState

    private val _checkInDetailState: MutableState<CheckInDetailModel?> = mutableStateOf(null)
    val checkInDetailState: State<CheckInDetailModel?> get() = _checkInDetailState

    private val _exchangeChapterCardState: MutableState<ExchangeChapterCardModel?> =
        mutableStateOf(null)
    val exchangeChapterCardState: State<ExchangeChapterCardModel?> get() = _exchangeChapterCardState

    private val _welfareCenterReward: SnapshotStateMap<String, String> = mutableStateMapOf()
    val welfareCenterReward get() = _welfareCenterReward

    private val _welfareCenterModel: MutableState<WelfareCenterModel?> = mutableStateOf(null)
    val welfareCenterModel: State<WelfareCenterModel?> get() = _welfareCenterModel


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.message?.loge()
        showError(exception.message ?: "Unknown error")
    }

    fun showError(message: String) {
        _errorState.value = message
    }

    fun hideError() {
        _errorState.value = ""
    }

    fun checkRisk() = defaultLaunch {
        remoteRepository.checkRisk().catch().onEach {
            _checkRiskState.value = it
        }.collect()
    }

    fun checkInDetail() = defaultLaunch {
        remoteRepository.checkInDetail().catch().onEach {
            _checkInDetailState.value = it
        }.collect()
    }

    /**
     * 签到
     * @param isMember Boolean
     */
    fun checkIn(isMember: Boolean) = defaultLaunch {
        remoteRepository.checkIn(isMember).catch().onEach {
            checkInDetail()
        }.collect()
    }

    /**
     * 抽奖机会
     */
    fun lotteryChance() = defaultLaunch {
        remoteRepository.lotteryChance().catch().onEach {
            checkInDetail()
        }.collect()
    }

    /**
     * 抽奖
     */
    fun lottery() = defaultLaunch {
        remoteRepository.lottery().catch().onEach {
            addReward("签到-抽奖", it.rewardName)
            checkInDetail()
        }.collect()
    }

    /**
     * 查询章节卡
     */
    fun exchangeChapterCard() = defaultLaunch {
        remoteRepository.exchangeChapterCard().catch().onEach {
            "exchangeChapterCard: $it".loge()
            _exchangeChapterCardState.value = it
        }.collect()
    }

    /**
     * 购买章节卡
     * @param goodId String
     */
    fun buyChapterCard(goodId: String, goodName: String) = defaultLaunch {
        remoteRepository.buyChapterCard(goodId).catch().onEach {
            if (it) {
                addReward("周日兑换章节卡", goodName)
                exchangeChapterCard()
            } else {
                showError("兑换章节卡失败")
            }
        }.collect()
    }

    fun getWelfareCenter() = defaultLaunch {
        remoteRepository.getWelfareCenter().catch().onEach {
            _welfareCenterModel.value = it
        }.onCompletion {
            hideError()
        }.collect()
    }

    fun getWelfareReward(title: String, taskId: String) = defaultLaunch {
        remoteRepository.getWelfareReward(taskId).catch().onEach { rewardModel ->
            rewardModel.rewardList.joinToString(",") { it.desc }.let { reward ->
                addReward(title, reward)
                getWelfareCenter()
            }
        }.collect()
    }

    fun receiveWelfareReward(title: String, taskId: String) = defaultLaunch {
        remoteRepository.receiveWelfareReward(taskId)
            .catch().onEach { rewardModel ->
                rewardModel.rewardList.joinToString(",") { it.desc }.let { reward ->
                    addReward(title, reward)
                    getWelfareCenter()
                }
            }.collect()
    }

    fun gameTime() = defaultLaunch {
        remoteRepository.gameTime().catch().onEach {
            if ("success" in it) {
                getWelfareCenter()
            }
        }.collect()
    }

    fun setBaseUrl(baseUrl: String) {
        optionEntity.taskOption.baseUrl = baseUrl
        Path.MY_BASE_URL = baseUrl
    }

    fun setHeaders(headers: Map<String, String>) {
        Path.HEADERS = headers
    }

    fun ioLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            block.invoke(this)
        }
    }

    fun defaultLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
            block.invoke(this)
        }
    }

    /**
     * 捕获异常
     * @receiver Flow<T>
     * @return Flow<T>
     */
    private fun <T> Flow<T>.catch(): Flow<T> = catch {
        showError(it.message ?: "未知错误")
    }

    /**
     * 添加奖励
     * @param title String
     * @param reward String
     */
    fun addReward(title: String, reward: String) {
        if (reward.isNotBlank()) {
            if (_welfareCenterReward[title] == null) {
                _welfareCenterReward[title] = reward
            } else {
                _welfareCenterReward[title] += ",$reward"
            }
        }
    }

    fun defaultRequest() {
        with(optionEntity.taskOption) {
            if (enableDefaultRequest) {
                defaultConfiguration.filter { it.selected }.forEach { selectedModel ->
                    when (selectedModel.title) {
                        "检测风险" -> checkRisk()
                        "签到" -> checkInDetail()
                        "周日兑换章节卡" -> exchangeChapterCard()
                        "福利中心" -> getWelfareCenter()
                    }
                }
                _initialState.value = false
            }
        }
    }

}

@Serializable
data class MainState(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val welfareCenterModel: WelfareCenterModel? = null,
)
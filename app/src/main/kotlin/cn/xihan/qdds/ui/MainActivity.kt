package cn.xihan.qdds.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.xihan.qdds.BuildConfig
import cn.xihan.qdds.R
import cn.xihan.qdds.hook.HookEntry
import cn.xihan.qdds.util.CustomBookShelfTopImageModel
import cn.xihan.qdds.util.NiaNavigationBar
import cn.xihan.qdds.util.NiaNavigationBarItem
import cn.xihan.qdds.util.Option.cleanLog
import cn.xihan.qdds.util.Option.deleteAll
import cn.xihan.qdds.util.Option.optionEntity
import cn.xihan.qdds.util.Option.resetOptionEntity
import cn.xihan.qdds.util.Option.updateOptionEntity
import cn.xihan.qdds.util.OptionEntity
import cn.xihan.qdds.util.QTheme
import cn.xihan.qdds.util.StartImageModel
import cn.xihan.qdds.util.alertDialog
import cn.xihan.qdds.util.copyToClipboard
import cn.xihan.qdds.util.defaultEmptyList
import cn.xihan.qdds.util.defaultOptionEntity
import cn.xihan.qdds.util.getVersionCode
import cn.xihan.qdds.util.hideAppIcon
import cn.xihan.qdds.util.isSunday
import cn.xihan.qdds.util.isTablet
import cn.xihan.qdds.util.joinQQGroup
import cn.xihan.qdds.util.jumpToPermission
import cn.xihan.qdds.util.multiChoiceSelector
import cn.xihan.qdds.util.openUrl
import cn.xihan.qdds.util.parseKeyWordOption
import cn.xihan.qdds.util.rememberMutableStateListOf
import cn.xihan.qdds.util.rememberMutableStateOf
import cn.xihan.qdds.util.rememberSavableMutableStateOf
import cn.xihan.qdds.util.requestPermissionDialog
import cn.xihan.qdds.util.restartApplication
import cn.xihan.qdds.util.runAndCatch
import cn.xihan.qdds.util.showAppIcon
import cn.xihan.qdds.util.toTime
import cn.xihan.qdds.util.toast
import cn.xihan.qdds.util.wait
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.highcapable.yukihookapi.hook.xposed.parasitic.activity.base.ModuleAppCompatActivity
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale


/**
 * 主要活动
 * 创建[MainActivity]
 * @suppress Generate Documentation
 */
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ModuleAppCompatActivity() {

    val versionCode by lazy { getVersionCode(HookEntry.QD_PACKAGE_NAME) }

    override val moduleTheme =
        com.google.android.material.R.style.Theme_Material3_DayNight_NoActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE
        )
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            QTheme {
                ComposeContent()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ComposeContent() {
        val context = LocalContext.current
        val permission = rememberMutableStateOf(
            value = XXPermissions.isGranted(
                context, Permission.REQUEST_INSTALL_PACKAGES, Permission.MANAGE_EXTERNAL_STORAGE
            )
        )
        val navController = rememberNavController()
        var allowDisclaimers by rememberMutableStateOf(value = optionEntity.allowDisclaimers && optionEntity.currentDisclaimersVersionCode >= defaultOptionEntity.latestDisclaimersVersionCode)
        var currentDisclaimersVersionCode by rememberMutableStateOf(value = optionEntity.currentDisclaimersVersionCode)
        val latestDisclaimersVersionCode by rememberMutableStateOf(value = defaultOptionEntity.latestDisclaimersVersionCode)
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "QDReadHook") },
                    modifier = Modifier.fillMaxWidth(),
                    actions = {
                        Row {
                            IconButton(onClick = {
                                restartApplication()
                            }) {
                                Icon(Icons.Filled.Refresh, null)
                            }
                            IconButton(onClick = {
                                finish()
                            }) {
                                Icon(Icons.AutoMirrored.Filled.ExitToApp, null)
                            }
                        }
                    },
                    navigationIcon = {},
                    scrollBehavior = null
                )
            },
            contentWindowInsets = WindowInsets(0),
        ) { paddingValues ->
            NavHost(navController = navController,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(top = paddingValues.calculateTopPadding()),
                startDestination = when {
                    permission.value.not() && allowDisclaimers.not() -> "route_permission_request"
                    permission.value.not() -> "route_permission_request"
                    allowDisclaimers.not() -> "route_disclaimers"
                    else -> "top_level_route"
                },
                contentAlignment = Alignment.Center,
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { -it / 4 } },
                popEnterTransition = { slideInHorizontally { -it / 4 } },
                popExitTransition = { slideOutHorizontally { it } }) {

                composable("top_level_route") {
                    val topLevelNavController = rememberNavController()
                    val currentDestination =
                        topLevelNavController.currentBackStackEntryAsState().value?.destination

                    Scaffold(
                        bottomBar = {
                            NiaNavigationBar(
                                modifier = Modifier
                                    .alpha(0.95f)
                                    .fillMaxWidth()
                                    .selectableGroup()
                            ) {
                                TopLevelScreen.entries.forEach { destination ->
                                    NiaNavigationBarItem(
                                        icon = {
                                            Icon(
                                                destination.imageVector, contentDescription = null
                                            )
                                        },
                                        label = { Text(destination.label) },
                                        selected = destination.route == currentDestination?.route,
                                        onClick = {
                                            topLevelNavController.navigate(destination.route) {
                                                popUpTo(topLevelNavController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }

                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        //alwaysShowLabel = false
                                    )
                                }
                            }
                        }, contentWindowInsets = WindowInsets.navigationBars
                    ) { padding ->

                        NavHost(
                            modifier = Modifier.zIndex(1f),
                            navController = topLevelNavController,
                            startDestination = TopLevelScreen.entries.first().route,
                        ) {
                            composable("route_main") {
                                MainScreen(padding = padding)
                            }

                            composable("route_purify") {
                                PurifyScreen(padding = padding)
                            }

                            composable("route_about") {
                                AboutScreen(versionCode, padding = padding)
                            }
                        }


                    }
                }

                composable("route_disclaimers") {
                    Disclaimers(onAgreeClick = {
                        allowDisclaimers = true
                        currentDisclaimersVersionCode = latestDisclaimersVersionCode
                        optionEntity.allowDisclaimers = true
                        optionEntity.currentDisclaimersVersionCode = latestDisclaimersVersionCode
                        updateOptionEntity()
                    }, onDisagreeClick = {
                        finish()
                    })
                }

                composable("route_permission_request") {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "起点和模块都需要存储权限\nps:用来管理位于外部存储的配置文件",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        Button(onClick = {
                            requestPermissionDialog(onGranted = {
                                permission.value = true
                                restartApplication()
                            }, onDenied = {
                                permission.value = false
                                jumpToPermission()
                            })
                        }) {
                            Text("点我请求权限")
                        }
                    }

                }

            }
        }
    }

    /**
     * 主屏幕
     * @since 7.9.354-1296
     * @param [versionCode] 版本代码
     * @param [context] 上下文
     * @suppress Generate Documentation
     */
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun MainScreen(
//    versionCode: Int,
        padding: PaddingValues,
        context: Context = LocalContext.current,
        viewModel: MainViewModel = koinViewModel()
    ) {
        val isTablet = isTablet()
        val itemModifier = remember {
            Modifier
                .fillMaxWidth()
                .height(if (isTablet) 48.dp else 38.dp)
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            PrimaryCard("主设置") {

                ItemWithSwitch(text = "启用启动时检查权限",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.mainOption.enableStartCheckingPermissions),
                    onCheckedChange = {
                        optionEntity.mainOption.enableStartCheckingPermissions = it
                    })

                ItemWithSwitch(text = "发帖上传图片显示直链",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.mainOption.enablePostToShowImageUrl),
                    onCheckedChange = {
                        optionEntity.mainOption.enablePostToShowImageUrl = it
                    })

                ItemWithSwitch(text = "免广告领取奖励",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.mainOption.enableFreeAdReward),
                    onCheckedChange = {
                        optionEntity.mainOption.enableFreeAdReward = it
                    })

                ItemWithSwitch(text = "忽略限时免费批量订阅限制",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.mainOption.enableIgnoreFreeSubscribeLimit),
                    onCheckedChange = {
                        optionEntity.mainOption.enableIgnoreFreeSubscribeLimit = it
                    })

                ItemWithSwitch(text = "解锁会员卡专属背景",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.mainOption.enableUnlockMemberBackground),
                    onCheckedChange = {
                        optionEntity.mainOption.enableUnlockMemberBackground = it
                    })

                ItemWithSwitch(text = "一键导出表情包",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.mainOption.enableExportEmoji),
                    onCheckedChange = {
                        optionEntity.mainOption.enableExportEmoji = it
                    })

                ItemWithSwitch(text = "启用旧版每日导读",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.mainOption.enableOldDailyRead),
                    onCheckedChange = {
                        optionEntity.mainOption.enableOldDailyRead = it
                    })

                ItemWithSwitch(text = "启用修复抖音分享",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.mainOption.enableFixDouYinShare),
                    onCheckedChange = {
                        optionEntity.mainOption.enableFixDouYinShare = it
                    })

                val enableCustomIMEI =
                    rememberMutableStateOf(value = optionEntity.mainOption.enableCustomIMEI)

                ItemWithSwitch(text = "启用自定义IMEI",
                    modifier = itemModifier,
                    checked = enableCustomIMEI,
                    onCheckedChange = {
                        optionEntity.mainOption.enableCustomIMEI = it
                    })

                if (enableCustomIMEI.value) {
                    val defaultQImei = rememberMutableStateOf(value = optionEntity.mainOption.qimei)

                    ItemWithEditText(title = "QIMEI", text = defaultQImei, onTextChange = {
                        optionEntity.mainOption.qimei = it
                    })
                }

                ItemWithSwitch(text = "启用抓取cookie",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.cookieOption.enableCookie),
                    onCheckedChange = {
                        optionEntity.cookieOption.enableCookie = it
                    })

                ItemWithSwitch(text = "启用调试日志",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.cookieOption.enableDebug),
                    onCheckedChange = {
                        optionEntity.cookieOption.enableDebug = it
                    },
                    onLongClick = {
                        cleanLog(context)
                    })


            }

            PrimaryCard("自动化设置") {

                val coroutineScope = rememberCoroutineScope()
                val baseUrl = rememberMutableStateOf(value = optionEntity.taskOption.baseUrl)
                val delayTime =
                    rememberMutableStateOf(value = optionEntity.taskOption.delayTime.toString())
                val enableDefaultRequest =
                    rememberMutableStateOf(optionEntity.taskOption.enableDefaultRequest)
                val baseExpandState = rememberMutableStateOf(false)
                val accounts = rememberMutableStateListOf(optionEntity.taskOption.accounts)
                val accountExpandState = rememberMutableStateOf(false)
                var selectedAccount by rememberMutableStateOf(accounts.firstOrNull())

                LaunchedEffect(selectedAccount) {
                    if (selectedAccount != null) {
                        val map = mutableMapOf<String, String>()
                        with(selectedAccount!!) {
                            if (uid.isNotBlank()) {
                                map["uid"] = uid
                            }
                            if (imei.isNotBlank()) {
                                map["imei"] = imei
                            }
                            if (ua.isNotBlank()) {
                                map["User-Agent"] = ua
                            }
                            if (cookie.isNotBlank()) {
                                map["cookie"] = cookie
                            }
                        }
                        viewModel.setHeaders(map)
                        if (viewModel.initialState.value) {
                            viewModel.defaultRequest()
                        }
                    }
                }

                ItemWithNewPage("基础设置",
                    itemModifier,
                    onClick = { baseExpandState.value = true })

                val baseExpandDismissRequest = {
                    baseExpandState.value = false
                }

                if (baseExpandState.value) {
                    MyAlertDialog(
                        onDismissRequest = baseExpandDismissRequest
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ItemWithEditText(
                                title = "基础地址",
                                text = baseUrl,
                                onTextChange = viewModel::setBaseUrl
                            )

                            ItemWithEditText(title = "延迟时间", text = delayTime, onTextChange = {
                                if (it.isNotBlank()) {
                                    runAndCatch {
                                        optionEntity.taskOption.delayTime = it.toInt()
                                    }
                                }
                            })

                            ItemWithSwitch("启用默认请求",
                                modifier = itemModifier,
                                checked = enableDefaultRequest,
                                onCheckedChange = {
                                    optionEntity.taskOption.enableDefaultRequest = it
                                })

                            if (enableDefaultRequest.value) {
                                ItemWithNewPage("默认请求列表", itemModifier, onClick = {
                                    context.multiChoiceSelector(optionEntity.taskOption.defaultConfiguration)
                                })
                            }
                        }
                    }
                }

                ItemWithNewPage("账号设置",
                    itemModifier,
                    onClick = { accountExpandState.value = true })

                if (accountExpandState.value) {
                    MyAlertDialog(onDismissRequest = {
                        accountExpandState.value = false
                    }) {
                        LazyColumn {
                            item("settings") {
                                Row(
                                    modifier = itemModifier,
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    TextButton(onClick = {
                                        accounts += OptionEntity.TaskOption.AccountModel()
                                    }) {
                                        Text(
                                            text = "添加一个",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                    TextButton(onClick = {
                                        optionEntity.taskOption.accounts = accounts.toList()
                                        updateOptionEntity()
                                    }) {
                                        Text(
                                            text = "保存全部",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                    TextButton(onClick = {
                                        accounts.clear()
                                        optionEntity.taskOption.accounts = emptyList()
                                        updateOptionEntity()
                                    }) {
                                        Text(
                                            text = "删除全部",
                                            color = Color.Red,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                }
                            }
                            items(accounts) { account ->
                                AccountItem(account = account, onDelete = {
                                    accounts.remove(account)
                                })
                            }
                        }
                    }
                }

                var accountDropdownExpanded by rememberMutableStateOf(false)

                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.TopCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedAccount?.uid ?: "",
                        onValueChange = {},
                        label = { Text("选中账号UID") },
                        trailingIcon = {
                            IconButton(onClick = {
                                accountDropdownExpanded = true
                            }) {
                                Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropdownMenu(expanded = accountDropdownExpanded, onDismissRequest = {
                        accountDropdownExpanded = false
                    }) {
                        accounts.forEach { account ->
                            DropdownMenuItem(
                                // 最近领取时间: ${account.lastReceivedRewardTime.toTime()}
                                text = { Text("UID: ${account.uid}") },
                                onClick = {
                                    selectedAccount = account
                                    accountDropdownExpanded = false
                                },
                            )
                        }
                    }
                }

                if (baseUrl.value.isNotBlank() && accounts.isNotEmpty() && selectedAccount != null) {

                    ItemWithNewPage(
                        text = "是否高风险: ${viewModel.checkRiskState.value ?: "[点击检查]"}",
                        modifier = itemModifier,
                        onClick = viewModel::checkRisk
                    )

                    ItemWithNewPage(
                        text = "获取签到信息",
                        modifier = itemModifier,
                        onClick = viewModel::checkInDetail
                    )

                    if (viewModel.checkInDetailState.value != null) {
                        TasksCard("签到信息") {
                            with(viewModel.checkInDetailState.value!!) {
                                val checkInState = checkInStatus == 1 || hasCheckIn == 1
                                ItemWithNewPage(text = "是否签到: ${if (checkInState) "已签到" else "未签到 [点击签到]"}",
                                    modifier = itemModifier,
                                    onClick = {
                                        if (!checkInState) {
                                            viewModel.checkIn(isMember == 1)
                                        }
                                    })

                                if (lotteryInfo.hasVideoUrge != 0) {
                                    TasksItem(
                                        total = lotteryInfo.hasVideoUrge,
                                        done = if (lotteryInfo.videoUrgeText.isNotBlank()) 1 else 0,
                                        title = "抽奖机会",
                                        singleExecution = viewModel::lotteryChance
                                    )
                                }

                                if (lotteryInfo.lotteryCount > 0) {
                                    TasksItem(total = lotteryInfo.lotteryCount,
                                        done = 0,
                                        title = "抽奖次数",
                                        singleExecution = viewModel::lottery,
                                        allExecution = {
                                            coroutineScope.launch(Dispatchers.Default) {
                                                val remaining = lotteryInfo.lotteryCount
                                                for (i in 1..remaining) {
                                                    wait(delayTime.value.toInt()) {
                                                        viewModel.lottery()
                                                    }
                                                }
                                            }
                                        })
                                }
                            }
                        }
                    }

                    if (isSunday()) {
                        TasksCard("兑换章节卡") {
                            ItemWithNewPage(
                                text = "[点我查询章节卡碎片]",
                                modifier = itemModifier,
                                onClick = viewModel::exchangeChapterCard
                            )

                            if (viewModel.exchangeChapterCardState.value != null) {
                                with(viewModel.exchangeChapterCardState.value!!) {
                                    val coin = balance.toInt()
                                    if (coin == 0 || coin < 15) {
                                        ItemWithNewPage(
                                            text = "没有足够的章节卡碎片", modifier = itemModifier
                                        )
                                    } else {
                                        goods.minByOrNull { model -> kotlin.math.abs(coin - model.goodScore.toInt()) }
                                            ?.let { goodModel ->
                                                ItemWithNewPage(text = "目前碎片: $coin, 兑换章节卡: ${goodModel.chapterCardCount} ${goodModel.goodName}",
                                                    modifier = itemModifier,
                                                    onClick = {
                                                        viewModel.buyChapterCard(
                                                            goodModel.goodId,
                                                            "${goodModel.chapterCardCount} ${goodModel.goodName}"
                                                        )
                                                    })
                                            }
                                    }
                                }
                            }

                        }
                    }

                    ItemWithNewPage(
                        text = "获取卡牌召唤信息",
                        modifier = itemModifier,
                        onClick = viewModel::getCardCallPage
                    )

                    if (viewModel.cardCallPageModel.value != null) {
                        TasksCard("卡牌召唤") {
                            with(viewModel.cardCallPageModel.value!!) {
                                if (freeNum != 0 && nextFreeTime < System.currentTimeMillis()) {
                                    TasksItem(
                                        total = freeNum,
                                        done = 0,
                                        title = "免费召唤",
                                        singleExecution = viewModel::getCardCall
                                    )
                                } else {
                                    ItemWithNewPage(
                                        text = "还未到免费领卡时间,下一次免费召唤时间: ${nextFreeTime.toTime()}",
                                        modifier = itemModifier,
                                        onClick = {}
                                    )
                                }
                            }

                        }
                    }

                    ItemWithNewPage(
                        text = "获取福利中心",
                        modifier = itemModifier,
                        onClick = viewModel::getWelfareCenter
                    )

                    if (viewModel.welfareCenterModel.value != null) {
                        TasksCard("福利中心") {
                            with(viewModel.welfareCenterModel.value!!) {
                                // 宝箱奖励
                                TasksItem(total = 1,
                                    done = if (treasureBox.intervalTime.toLong() > 0L) 1 else 0,
                                    title = treasureBox.desc,
                                    singleExecution = {
                                        viewModel.getWelfareReward(
                                            treasureBox.desc, treasureBox.taskId
                                        )
                                    })

                                val videoRemaining =
                                    videoBenefitModule.taskList.size - videoBenefitModule.process
                                // 激励视频任务
                                val videoBenefitModuleExecution: () -> Unit = {
                                    val taskId = videoBenefitModule.taskList.first().taskId
                                    coroutineScope.launch(Dispatchers.Default) {
                                        for (i in 1..videoRemaining) {
                                            wait(delayTime.value.toInt()) {
                                                viewModel.getWelfareReward(
                                                    videoBenefitModule.title, taskId
                                                )
                                            }
                                        }
                                    }
                                }
                                TasksItem(
                                    total = videoBenefitModule.taskList.size,
                                    done = videoBenefitModule.process,
                                    title = videoBenefitModule.title,
                                    singleExecution = {
                                        viewModel.getWelfareReward(
                                            videoBenefitModule.title,
                                            videoBenefitModule.taskList.first().taskId
                                        )
                                    },
                                    allExecution = if (videoRemaining > 0) videoBenefitModuleExecution else null
                                )

                                val countdownBenefitModuleTaskList = countdownBenefitModule.taskList
                                // 额外看3次小视频得奖励
                                countdownBenefitModuleTaskList.firstOrNull { it.title == "额外看3次小视频得奖励" }
                                    ?.let {
                                        val remaining = it.total - it.process
                                        val allExecution: () -> Unit = {
                                            val taskId = it.taskId
                                            coroutineScope.launch(Dispatchers.Default) {
                                                for (i in 1..remaining) {

                                                    wait(delayTime.value.toInt()) {
                                                        viewModel.getWelfareReward(
                                                            it.title, taskId
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        TasksItem(
                                            total = it.total,
                                            done = it.process,
                                            title = it.title,
                                            singleExecution = {
                                                viewModel.getWelfareReward(
                                                    it.title, it.taskId
                                                )
                                            },
                                            allExecution = if (remaining > 0) allExecution else null
                                        )
                                    }
                                // 额外看1次小视频得激励碎片
                                countdownBenefitModuleTaskList.firstOrNull { it.title == "额外看1次小视频得奖励" }
                                    ?.let {
                                        TasksItem(total = it.total,
                                            done = it.process,
                                            title = it.title,
                                            singleExecution = {
                                                viewModel.getWelfareReward(
                                                    it.title, it.taskId
                                                )
                                            })

                                    }
                                // 当日听书1分钟
                                countdownBenefitModuleTaskList.firstOrNull { it.title == "当日听书1分钟" }
                                    ?.let {
                                        val receive: () -> Unit = {
                                            viewModel.receiveWelfareReward(
                                                it.title, it.taskId
                                            )
                                        }
                                        TasksItem(
                                            total = it.total,
                                            done = it.process,
                                            title = it.title,
                                            receive = if (it.isReceived == 0) receive else null
                                        )
                                    }
                                // 当日玩游戏10分钟
                                countdownBenefitModuleTaskList.firstOrNull { it.title == "当日玩游戏10分钟" }
                                    ?.let {
                                        val gameRemaining = (it.total - it.process) * 2 + 1
                                        val allExecution: () -> Unit = {
                                            coroutineScope.launch(Dispatchers.IO) {
                                                withContext(Dispatchers.Main) {
                                                    context.toast("开始游戏时间,预计${it.total - it.process + 1}分钟")
                                                }
                                                for (i in 1..gameRemaining) {
                                                    wait(30) {
                                                        viewModel.gameTime()
                                                    }
                                                }
                                            }
                                        }
                                        val receive: () -> Unit = {
                                            viewModel.receiveWelfareReward(
                                                it.title, it.taskId
                                            )
                                        }
                                        TasksItem(
                                            total = it.total,
                                            done = it.process,
                                            title = it.title,
                                            singleExecution = viewModel::gameTime,
                                            allExecution = if (gameRemaining > 0) allExecution else null,
                                            receive = if (it.isReceived == 0) receive else null
                                        )
                                    }

                            }
                        }
                    }

                    if (viewModel.welfareCenterReward.isNotEmpty()) {
                        Text(
                            text = viewModel.welfareCenterReward.entries.joinToString { "${it.key}: ${it.value}\n" },
                            modifier = Modifier.padding(horizontal = 15.dp),
                            // 始终显示全部
                            overflow = TextOverflow.Visible
                        )
                    }

                    if (viewModel.errorState.value.isNotBlank()) {
                        ItemWithEditText(
                            title = "错误信息",
                            text = viewModel.errorState,
                            onTextChange = {})
                    }
                }

                // 待实现
//                ItemWithNewPage(text = "自动化选项列表", onClick = {
//                    context.multiChoiceSelector(optionEntity.taskOption.configurations)
//                })

                ItemWithNewPage(text = "自动化设置列表", modifier = itemModifier, onClick = {
                    context.multiChoiceSelector(optionEntity.automatizationOption)
                })
            }

            PrimaryCard("书架设置") {

                val enableCustomBookShelfTopImage =
                    rememberMutableStateOf(value = optionEntity.bookshelfOption.enableCustomBookShelfTopImage)

                ItemWithSwitch(text = "启用自定义书架顶部图片",
                    modifier = itemModifier,
                    checked = enableCustomBookShelfTopImage,
                    onCheckedChange = {
                        optionEntity.bookshelfOption.enableCustomBookShelfTopImage = it
                    })

                if (enableCustomBookShelfTopImage.value) {
                    val enableSameNightAndDay =
                        rememberMutableStateOf(value = optionEntity.bookshelfOption.enableSameNightAndDay)

                    ItemWithSwitch(text = "启用夜间和日间相同",
                        modifier = itemModifier,
                        checked = enableSameNightAndDay,
                        onCheckedChange = {
                            optionEntity.bookshelfOption.enableSameNightAndDay = it
                        })

                    CustomBookShelfTopImageOption(title = "白天模式",
//                    modifier = itemModifier,
                        customBookShelfTopImageModel = optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel,
                        onValueChange = {
                            optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel = it
                            updateOptionEntity()
                        })

                    if (!enableSameNightAndDay.value) {
                        CustomBookShelfTopImageOption(title = "夜间模式",
//                        modifier = itemModifier,
                            customBookShelfTopImageModel = optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel,
                            onValueChange = {
                                optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel =
                                    it
                                updateOptionEntity()
                            })
                    }

                }


            }

            PrimaryCard("阅读页设置") {

                ItemWithSwitch(text = "阅读页字体自动替换",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.readPageOption.enableCustomFont),
                    onCheckedChange = {
                        optionEntity.readPageOption.enableCustomFont = it
                    })

                ItemWithSwitch(text = "阅读页章评图片长按保存原图",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.readPageOption.enableShowReaderPageChapterSaveRawPicture),
                    onCheckedChange = {
                        optionEntity.readPageOption.enableShowReaderPageChapterSaveRawPicture = it
                    })

                ItemWithSwitch(text = "阅读页章评评论长按复制",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.readPageOption.enableCopyReaderPageChapterComment),
                    onCheckedChange = {
                        optionEntity.readPageOption.enableCopyReaderPageChapterComment = it
                    })

                ItemWithSwitch(text = "阅读页章评图片保存原图对话框",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.readPageOption.enableShowReaderPageChapterSavePictureDialog),
                    onCheckedChange = {
                        optionEntity.readPageOption.enableShowReaderPageChapterSavePictureDialog =
                            it
                    })

                ItemWithSwitch(text = "阅读页章评音频导出对话框",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.readPageOption.enableShowReaderPageChapterSaveAudioDialog),
                    onCheckedChange = {
                        optionEntity.readPageOption.enableShowReaderPageChapterSaveAudioDialog = it
                    })

                val enableReadTimeFactor =
                    rememberMutableStateOf(value = optionEntity.readPageOption.enableReadTimeFactor)

                ItemWithSwitch(text = "阅读时间加倍",
                    modifier = itemModifier,
                    checked = enableReadTimeFactor,
                    onCheckedChange = {
                        optionEntity.readPageOption.enableReadTimeFactor = it
                    })

                if (enableReadTimeFactor.value) {
                    val speedFactor =
                        rememberMutableStateOf(value = optionEntity.readPageOption.speedFactor.toString())

                    ItemWithEditText(title = "时间加倍系数", text = speedFactor, onTextChange = {
                        if (it.isNotBlank()) {
                            runAndCatch {
                                optionEntity.readPageOption.speedFactor = it.toInt()
                            }
                        }
                    })
                }

                val enableRedirectReadingPageBackgroundPath =
                    rememberMutableStateOf(value = optionEntity.readPageOption.enableRedirectReadingPageBackgroundPath)

                ItemWithSwitch(text = "重定向阅读页主题路径",
                    checked = enableRedirectReadingPageBackgroundPath,
                    onCheckedChange = {
                        optionEntity.readPageOption.enableRedirectReadingPageBackgroundPath = it
                    })


            }

            PrimaryCard("启动图设置") {

                val enableCustomStartImage =
                    rememberMutableStateOf(value = optionEntity.startImageOption.enableCustomStartImage)

                ItemWithSwitch(
                    text = "启用自定义启动图",
                    checked = enableCustomStartImage,
                    onCheckedChange = {
                        optionEntity.startImageOption.enableCustomStartImage = it
                    })
                if (enableCustomStartImage.value) {
                    val enableCaptureTheOfficialLaunchMapList =
                        rememberMutableStateOf(value = optionEntity.startImageOption.enableCaptureTheOfficialLaunchMapList)

                    ItemWithSwitch(text = "启用抓取官方启动图",
                        checked = enableCaptureTheOfficialLaunchMapList,
                        onCheckedChange = {
                            optionEntity.startImageOption.enableCaptureTheOfficialLaunchMapList = it
                        })

                    ItemWithSwitch(text = "启用重定向本地启动图",
                        checked = rememberMutableStateOf(value = optionEntity.startImageOption.enableRedirectLocalStartImage),
                        onCheckedChange = {
                            optionEntity.startImageOption.enableRedirectLocalStartImage = it
                        })

                    if (optionEntity.startImageOption.officialLaunchMapList.isNotEmpty()) {
                        val enableCaptureTheOfficialLaunchMapListExpand =
                            rememberMutableStateOf(value = false)
                        val expandIcon =
                            if (enableCaptureTheOfficialLaunchMapListExpand.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                enableCaptureTheOfficialLaunchMapListExpand.value =
                                    !enableCaptureTheOfficialLaunchMapListExpand.value
                            }
                            .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "官方启动图列表: ${optionEntity.startImageOption.officialLaunchMapList.size}张"
                            )

                            Text(
                                text = if (enableCaptureTheOfficialLaunchMapListExpand.value) "收起" else "展开",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End,
                            )

                            Icon(
                                imageVector = expandIcon,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }


                        if (enableCaptureTheOfficialLaunchMapListExpand.value) {
                            Surface(
                                modifier = Modifier
                                    .animateContentSize()
                                    .padding(1.dp),
                                color = Color.Transparent
                            ) {
                                val list =
                                    remember { optionEntity.startImageOption.officialLaunchMapList }
                                val listSize = list.size
                                val height = (listSize / 3 + if (listSize % 3 == 0) 0 else 1) * 250

                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(height.dp)
                                ) {
                                    items(list.windowed(3, 3, true)) { sublist ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            sublist.forEach { item ->
                                                StartImageItem(
                                                    startImageModel = item,
                                                    modifier = Modifier
                                                        .combinedClickable(onClick = {},
                                                            onLongClick = {
                                                                context.apply {
                                                                    toast("已复制图片链接")
                                                                    copyToClipboard(item.imageUrl)
                                                                }
                                                            })
                                                        .height(250.dp)
                                                        .fillParentMaxWidth(.3f)
                                                        .padding(
                                                            2.dp
                                                        )
                                                )
                                            }

                                        }

                                    }

                                }


                            }
                        }
                    }

                    val customStartImageUrlList = rememberMutableStateOf(
                        value = optionEntity.startImageOption.customStartImageUrlList.joinToString(
                            ";"
                        )
                    )

                    ItemWithEditText(title = "填入网络图片直链",
                        right = { Insert(list = customStartImageUrlList) },
                        text = customStartImageUrlList,
                        onTextChange = {
                            optionEntity.startImageOption.customStartImageUrlList =
                                parseKeyWordOption(it)
                        })
                }

            }
        }

    }

    @Composable
    private fun PurifyScreen(
//    versionCode: Int,
        padding: PaddingValues,
        context: Context = LocalContext.current,
    ) {
        val isTablet = isTablet()
        val itemModifier = remember {
            Modifier
                .fillMaxWidth()
                .height(if (isTablet) 48.dp else 38.dp)
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            PrimaryCard("广告设置") {
                ItemWithNewPage(text = "广告设置列表", modifier = itemModifier, onClick = {
                    context.multiChoiceSelector(optionEntity.advOption)
                })
            }

            PrimaryCard("拦截设置") {

                ItemWithNewPage(text = "拦截设置列表", modifier = itemModifier, onClick = {
                    context.multiChoiceSelector(optionEntity.interceptOption)
                })
            }

            PrimaryCard("屏蔽设置") {

                ItemWithNewPage(text = "屏蔽选项列表", modifier = itemModifier, onClick = {
                    context.multiChoiceSelector(optionEntity.shieldOption.configurations)
                })

                ItemWithSwitch(text = "启用快速屏蔽弹窗",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.shieldOption.enableQuickShieldDialog),
                    onCheckedChange = {
                        optionEntity.shieldOption.enableQuickShieldDialog = it
                    })

                val authorList = rememberMutableStateOf(
                    value = optionEntity.shieldOption.authorList.joinToString(
                        ";"
                    ),
                )

                ItemWithEditText(title = "填入需要屏蔽的完整作者名称", text = authorList, right = {
                    Insert(authorList)
                }, onTextChange = {
                    optionEntity.shieldOption.authorList = parseKeyWordOption(it)
                })

                val bookNameList = rememberMutableStateOf(
                    value = optionEntity.shieldOption.bookNameList.joinToString(
                        ";"
                    )
                )

                ItemWithEditText(title = "填入需要屏蔽的书名关键词", text = bookNameList, right = {
                    Insert(bookNameList)
                }, onTextChange = {
                    optionEntity.shieldOption.bookNameList = parseKeyWordOption(it)
                })

                ItemWithSwitch(text = "启用书类型增强屏蔽",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.shieldOption.enableBookTypeEnhancedBlocking),
                    onCheckedChange = {
                        optionEntity.shieldOption.enableBookTypeEnhancedBlocking = it
                    })

                val bookTypeList = rememberMutableStateOf(
                    value = optionEntity.shieldOption.bookTypeList.joinToString(";")
                )

                ItemWithEditText(title = "填入需要屏蔽的书类型", text = bookTypeList, right = {
                    Insert(bookTypeList)
                }, onTextChange = {
                    optionEntity.shieldOption.bookTypeList = parseKeyWordOption(it)
                })

                val bookWordsCount = rememberMutableStateOf(
                    value = optionEntity.shieldOption.bookWordsCount.toString()
                )

                ItemWithEditText(
                    title = "填入需要屏蔽的书字数,-1为不限制",
                    text = bookWordsCount,
                    onTextChange = {
                        runAndCatch {
                            optionEntity.shieldOption.bookWordsCount = it.toLong()
                        }
                    })
            }

            PrimaryCard("隐藏控件设置") {

                ItemWithSwitch(text = "隐藏部分小红点",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.viewHideOption.enableHideRedDot),
                    onCheckedChange = {
                        optionEntity.viewHideOption.enableHideRedDot = it
                    })

                ItemWithSwitch(text = "隐藏我-右上角消息红点",
                    modifier = itemModifier,
                    checked = rememberMutableStateOf(value = optionEntity.viewHideOption.accountOption.enableHideAccountRightTopRedDot),
                    onCheckedChange = {
                        optionEntity.viewHideOption.accountOption.enableHideAccountRightTopRedDot =
                            it
                    })

                ItemWithNewPage(text = "搜索配置列表", modifier = itemModifier, onClick = {
                    context.multiChoiceSelector(optionEntity.viewHideOption.searchOption)
                })

                ItemWithNewPage(text = "主页隐藏选项列表", modifier = itemModifier, onClick = {
                    context.multiChoiceSelector(optionEntity.viewHideOption.homeOption.configurations)
                })
                val enableCaptureBottomNavigation =
                    rememberMutableStateOf(value = optionEntity.viewHideOption.homeOption.enableCaptureBottomNavigation)
                ItemWithSwitch(text = "启用抓取底部导航栏",
                    modifier = itemModifier,
                    checked = enableCaptureBottomNavigation,
                    onCheckedChange = {
                        optionEntity.viewHideOption.homeOption.enableCaptureBottomNavigation = it
                    })

                if (enableCaptureBottomNavigation.value) {
                    ItemWithNewPage(text = "主页底部导航栏选项列表",
                        modifier = itemModifier,
                        onClick = {
                            context.multiChoiceSelector(optionEntity.viewHideOption.homeOption.bottomNavigationConfigurations)
                        },
                        onLongClick = {
                            optionEntity.viewHideOption.homeOption.bottomNavigationConfigurations =
                                defaultEmptyList
                            context.toast("已恢复默认")
                        })
                }

                val enableSelectedHide =
                    rememberMutableStateOf(value = optionEntity.viewHideOption.selectedOption.enableSelectedHide)

                ItemWithSwitch(text = "精选-启用选项屏蔽",
                    modifier = itemModifier,
                    checked = enableSelectedHide,
                    onCheckedChange = {
                        optionEntity.viewHideOption.selectedOption.enableSelectedHide = it
                    })

                if (enableSelectedHide.value) {
                    ItemWithNewPage(text = "精选-隐藏控件列表", modifier = itemModifier, onClick = {
                        context.multiChoiceSelector(optionEntity.viewHideOption.selectedOption.configurations)
                    }, onLongClick = {
                        optionEntity.viewHideOption.selectedOption.configurations = defaultEmptyList
                        context.toast("已恢复默认")
                    })
                }

                val enableSelectedTitleHide =
                    rememberMutableStateOf(value = optionEntity.viewHideOption.selectedOption.enableSelectedTitleHide)

                ItemWithSwitch(text = "精选-标题启用选项屏蔽",
                    modifier = itemModifier,
                    checked = enableSelectedTitleHide,
                    onCheckedChange = {
                        optionEntity.viewHideOption.selectedOption.enableSelectedTitleHide = it
                    })

                if (enableSelectedTitleHide.value) {
                    ItemWithNewPage(text = "精选-标题隐藏控件列表",
                        modifier = itemModifier,
                        onClick = {
                            context.multiChoiceSelector(optionEntity.viewHideOption.selectedOption.selectedTitleConfigurations)
                        },
                        onLongClick = {
                            optionEntity.viewHideOption.selectedOption.selectedTitleConfigurations =
                                defaultEmptyList
                            context.toast("已恢复默认")
                        })
                }

                val hideAccount =
                    rememberMutableStateOf(value = optionEntity.viewHideOption.accountOption.enableHideAccount)


                ItemWithSwitch(text = "启用我-隐藏控件",
                    modifier = itemModifier,
                    checked = hideAccount,
                    onCheckedChange = {
                        optionEntity.viewHideOption.accountOption.enableHideAccount = it
                    })

                if (hideAccount.value) {
                    ItemWithNewPage(text = "我-隐藏控件列表", modifier = itemModifier, onClick = {
                        context.multiChoiceSelector(optionEntity.viewHideOption.accountOption.configurations)
                    }, onLongClick = {
                        optionEntity.viewHideOption.accountOption.configurations = defaultEmptyList
                        context.toast("已恢复默认")
                    })
                }

                val enableCaptureBookReadPageView =
                    rememberMutableStateOf(value = optionEntity.viewHideOption.readPageOptions.enableCaptureBookReadPageView)

                ItemWithSwitch(text = "启用阅读页抓取控件",
                    modifier = itemModifier,
                    checked = enableCaptureBookReadPageView,
                    onCheckedChange = {
                        optionEntity.viewHideOption.readPageOptions.enableCaptureBookReadPageView =
                            it
                    })

                if (enableCaptureBookReadPageView.value) {
                    ItemWithNewPage(text = "阅读页-隐藏控件列表",
                        modifier = itemModifier,
                        onClick = {
                            context.multiChoiceSelector(optionEntity.viewHideOption.readPageOptions.configurations)
                        },
                        onLongClick = {
                            optionEntity.viewHideOption.readPageOptions.configurations =
                                defaultEmptyList
                            context.toast("已恢复默认")
                        })
                }

                val hideDetail =
                    rememberMutableStateOf(value = optionEntity.viewHideOption.enableHideBookDetail)

                ItemWithSwitch(text = "启用书籍详情-隐藏控件",
                    modifier = itemModifier,
                    checked = hideDetail,
                    onCheckedChange = {
                        optionEntity.viewHideOption.enableHideBookDetail = it
                    })

                if (hideDetail.value) {
                    ItemWithNewPage(text = "书籍详情-隐藏控件列表",
                        modifier = itemModifier,
                        onClick = {
                            context.multiChoiceSelector(optionEntity.viewHideOption.bookDetailOptions)
                        })
                }

                val enableHideLastPage =
                    rememberMutableStateOf(value = optionEntity.viewHideOption.enableHideLastPage)

                ItemWithSwitch(text = "启用阅读页-最后一页-隐藏控件",
                    modifier = itemModifier,
                    checked = enableHideLastPage,
                    onCheckedChange = {
                        optionEntity.viewHideOption.enableHideLastPage = it
                    })

                if (enableHideLastPage.value) {
                    ItemWithNewPage(
                        text = "阅读页-最后一页-隐藏控件列表",
                        modifier = itemModifier,
                        onClick = {
                            context.multiChoiceSelector(optionEntity.viewHideOption.bookLastPageOptions)
                        })
                }


            }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AboutScreen(
        versionCode: Int,
        padding: PaddingValues,
//    modifier: Modifier = Modifier,
        context: Context = LocalContext.current,
    ) {
        val isTablet = isTablet()
        val itemModifier = remember {
            Modifier
                .fillMaxWidth()
                .height(if (isTablet) 58.dp else 38.dp)
        }
        PrimaryCard(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            ItemWithSwitch(text = "隐藏桌面图标",
                modifier = itemModifier,
                checked = rememberMutableStateOf(value = optionEntity.mainOption.enableHideAppIcon),
                onCheckedChange = {
                    optionEntity.mainOption.enableHideAppIcon = it
                    runAndCatch {
                        if (it) {
                            context.hideAppIcon()
                        } else {
                            context.showAppIcon()
                        }
                    }
                })

            var deleteAllDialog by rememberMutableStateOf(value = false)
            if (deleteAllDialog) {
                ConfirmationDialog("清除起点所有缓存", onConfirm = {
                    deleteAll()
                    toast("清除成功")
                    restartApplication()
                }, onDismiss = { deleteAllDialog = false })
            }

            ItemWithNewPage("清除起点所有缓存", modifier = itemModifier, onClick = {
                deleteAllDialog = true
            })

            var resetOptionEntityDialog by rememberMutableStateOf(value = false)
            if (resetOptionEntityDialog) {
                ConfirmationDialog("重置模块配置文件", onConfirm = {
                    resetOptionEntity()
                    toast("重置成功,即将重启应用")
                    restartApplication()
                }, onDismiss = { resetOptionEntityDialog = false })
            }

            ItemWithNewPage(text = "重置模块配置文件", modifier = itemModifier, onClick = {
                resetOptionEntityDialog = true
            })

            ItemWithNewPage(text = "打赏", modifier = itemModifier, onClick = {
                context.openUrl("https://github.com/xihan123/QDReadHook#%E6%89%93%E8%B5%8F")
            })

            ItemWithNewPage(text = "新QD模块交流群", modifier = itemModifier, onClick = {
                context.joinQQGroup("ekDZd0CDaBKVg5HxQJE9-avi0uWPVK8a")
            })

            ItemWithNewPage(text = "新QD模块(赞助/内测)群", modifier = itemModifier, onClick = {
                context.alertDialog {
                    title = "作者的话"
                    message =
                        "因为开发QD模块占用日常时间过长，无法保证能够及时适配，希望大家能够理解。\n" + "QD模块的新版本更新将会在赞助群抢先体验。\n" + "开发不易，有能力的人可以进行赞助，交流群的相册有付款码。\n" + "赞助多少无要求，各凭心意。赞助群门槛暂定四十元，后续群费会随时向上调整，就不再另行通知。\n" + "大家量力而行，非常感谢各位的支持。愿大家都有一个良好的追书体验~"

                    positiveButton("确定") {
                        context.joinQQGroup("srF3lQITSezYciRF43-Ih9cyJp82mmaS")
                    }
                    negativeButton("取消") {
                        it.dismiss()
                    }
                    build()
                    show()
                }
            })

            ItemWithNewPage(text = "TG交流群", modifier = itemModifier, onClick = {
                context.openUrl("https://t.me/+tHAFB7FQKHdiYjU9")
            })

            ItemWithNewPage(text = "开发者: 希涵", modifier = itemModifier, onClick = {
                context.openUrl("https://github.com/xihan123")
            })

            ItemWithNewPage(text = "常见问题", modifier = itemModifier, onClick = {
                context.openUrl("https://github.com/xihan123/QDReadHook#%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98")
            })

            ItemWithNewPage(text = "功能列表", modifier = itemModifier, onClick = {
                context.openUrl("https://xihan123.github.io/QDReadHook/app/cn.xihan.qdds/index.html")
            })

            ItemWithNewPage(
                text = "编译时间: ${
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                        BuildConfig.BUILD_TIMESTAMP
                    )
                }", modifier = itemModifier
            )

            var openDialog by rememberMutableStateOf(value = false)

            ItemWithNewPage(text = "免责声明",
                modifier = itemModifier,
                onClick = { openDialog = true })

            if (openDialog) {
                BasicAlertDialog(onDismissRequest = { openDialog = false }) {
                    Card {
                        Disclaimers(
                            displayButton = false
                        )
                    }
                }
            }

            var cookieDialog by rememberMutableStateOf(value = false)
            val cookieDialogDismissRequest = {
                cookieDialog = false
            }

            ItemWithNewPage(text = "查看Cookie",
                modifier = itemModifier,
                onClick = { cookieDialog = true })

            if (cookieDialog) {

                MyAlertDialog(onDismissRequest = cookieDialogDismissRequest, text = {
                    Column {
                        TextButton(onClick = {
                            context.copyToClipboard("${optionEntity.cookieOption.uid}")
                            context.toast("已复制UID")
                        }) {
                            Text(text = "UID: ${optionEntity.cookieOption.uid}")
                        }

                        TextButton(onClick = {
                            context.copyToClipboard("${optionEntity.cookieOption.ua}")
                            context.toast("已复制UA")
                        }) {
                            Text(
                                text = "ua: ${optionEntity.cookieOption.ua}", maxLines = 2
                            )
                        }

                        TextButton(onClick = {
                            context.copyToClipboard("${optionEntity.cookieOption.cookie}")
                            context.toast("已复制Cookie")
                        }) {
                            Text(
                                text = "cookie: ${optionEntity.cookieOption.cookie}", maxLines = 2
                            )
                        }
                    }
                }, confirmButton = {
                    TextButton(onClick = {
                        runAndCatch {
                            optionEntity.taskOption.accounts.filter { it.uid.isNotEmpty() }
                                .firstOrNull { it.uid.toInt() == optionEntity.cookieOption.uid }
                                ?.let {
                                    it.cookie = optionEntity.cookieOption.cookie
                                    it.ua = optionEntity.cookieOption.ua
                                } ?: run {
                                optionEntity.taskOption.accounts =
                                    optionEntity.taskOption.accounts.plus(
                                        OptionEntity.TaskOption.AccountModel(
                                            uid = optionEntity.cookieOption.uid.toString(),
                                            cookie = optionEntity.cookieOption.cookie,
                                            ua = optionEntity.cookieOption.ua
                                        )
                                    )
                            }
                            updateOptionEntity()
                            cookieDialogDismissRequest()
                            context.toast("已添加/更新Cookie")
                        }
                    }) {
                        Text("一键添加/更新")
                    }
                }, dismissButton = {
                    TextButton(onClick = cookieDialogDismissRequest) {
                        Text("关闭")
                    }
                })
            }

            ItemWithAction(
                text = "起点内部版本号: $versionCode\n模块版本: ${BuildConfig.VERSION_NAME}-${BuildConfig.VERSION_CODE}\n目前支持版本号范围: 7.9.354-1296 至 7.9.xxx-1499",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )


        }

    }

}

/**
 * 免责声明
 * @since 7.9.354-1296
 * @param [modifier] 修饰符
 * @param [onAgreeClick] 点击同意
 * @param [onDisagreeClick] 点击不同意
 * @param [displayButton] 显示按钮
 * @suppress Generate Documentation
 */
@Composable
fun Disclaimers(
    modifier: Modifier = Modifier,
    onAgreeClick: () -> Unit = {},
    onDisagreeClick: () -> Unit = {},
    displayButton: Boolean = true,
) {
    var remainingTime by rememberSavableMutableStateOf(value = 30L)
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    if (displayButton) {
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    coroutineScope.launch {
                        while (remainingTime > 0) {
                            delay(1000)
                            remainingTime.takeUnless { it == 0L }?.let { remainingTime-- }
                        }
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "免责声明",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clickable { remainingTime = 0L })

        Spacer(modifier = Modifier.height(16.dp))

        val text = buildAnnotatedString {
            appendLine("        1.本模块是基于Xposed框架开发的，使用本模块需要您的设备已经安装了Xposed框架。Xposed框架是一种修改Android系统行为的工具，它可能会导致系统不稳定、无法开机或损坏设备。您在使用本模块之前，应该了解Xposed框架的原理和风险，并自行承担后果。\n")
            appendLine("        2.本模块的源代码是公开的，任何人都可以查看或修改它，开发者不对本模块的功能和效果做任何保证，也不对本模块可能造成的任何损失或损害负责。请在使用前确认你下载的 QDReadHook 是来自可信的渠道，并且没有被恶意篡改或添加木马等病毒。\n")
            appendLine("        3.本模块不是为了破坏起点中文网的正常运营和作者的合法权益。请在使用本模块时，尊重作者的劳动成果，支持正版阅读，不要利用本模块进行非法活动或其他损害起点合法权益的行为。\n")
            appendLine("        4.开发者保留对该Xposed模块的更新、修改、暂停、终止等权利，使用者应该自行确认其使用版本的安全性和稳定性。\n\n")
            append("        本模块仅供学习交流，请在下载24小时内删除。在使用该Xposed模块之前认真审慎阅读、充分理解 ")

            withLink(LinkAnnotation.Url("https://acts.qidian.com/pact/user_pact.html")) {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF0E9FF2), fontWeight = FontWeight.W900
                    )
                ) {
                    append("起点读书用户服务协议")
                }
            }

            append("以及上述 免责声明，如有异议请勿使用。如果您使用了该Xposed模块，即代表您已经完全接受本免责声明。\n\n")

            append("详细信息请到 ")

            withLink(LinkAnnotation.Url("https://github.com/xihan123/QDReadHook#%E5%85%8D%E8%B4%A3%E5%A3%B0%E6%98%8E")) {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF0E9FF2), fontWeight = FontWeight.W900
                    )
                ) {
                    append("Github")
                }
            }

            append(" 或者 ")
            withLink(LinkAnnotation.Url("https://gitee.com/xihan123/QDReadHook#%E5%85%8D%E8%B4%A3%E5%A3%B0%E6%98%8E")) {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF0E9FF2), fontWeight = FontWeight.W900
                    )
                ) {
                    append("Gitee")
                }
            }

            append(" 查看")

            withStyle(
                style = SpanStyle(
                    color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 20.sp
                )
            ) {
                appendLine("\n\n免费软件,如果是付费购买请立即联系卖家退款并举报!!!")
            }
        }

        Text(
            text = text, style = TextStyle(
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        if (displayButton) {
            Spacer(modifier = Modifier.height(16.dp))
            if (remainingTime != 0L) {
                Text(
                    text = String.format(
                        "剩余时间: %s 秒", remainingTime
                    ), fontWeight = FontWeight.Bold, fontSize = 24.sp
                )
            } else {
                Button(onClick = onAgreeClick) { Text(text = "我已阅读并同意") }
            }

            Button(onClick = onDisagreeClick) { Text(text = "拒绝并退出") }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemWithNewPage(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .combinedClickable(
                onClick = onClick, onLongClick = onLongClick
            )
            .padding(horizontal = 15.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "more",
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemWithAction(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .combinedClickable(
                onClick = onClick, onLongClick = onLongClick
            )
            .padding(horizontal = 15.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = text, style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ItemWithEditText(
    title: String,
    text: MutableState<String>,
    right: @Composable () -> Unit = {},
    onTextChange: ((String) -> Unit) = {},
) {
    TextField(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp),
        value = text.value,
        label = { Text(text = title) },
        onValueChange = {
            text.value = it
            onTextChange(it)
            updateOptionEntity()
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                right()
                if (text.value.isNotBlank()) {
                    IconButton(onClick = {
                        text.value = ""
                        onTextChange("")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close, contentDescription = null
                        )
                    }
                }
            }
        })
}

/**
 * 插入
 * @since 7.9.354-1296
 * @param [list] 列表
 * @suppress Generate Documentation
 */
@Composable
fun Insert(list: MutableState<String>) {
    if (list.value.isNotBlank()) {
        IconButton(onClick = {
            list.value = list.value.plus(";")
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}

@Composable
private fun PrimaryCard(
    title: String = "", modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        content = {
            if (title.isNotBlank()) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth()
                )
            }
            content()
        })
}

@Composable
private fun TasksCard(
    title: String = "",
    modifier: Modifier = Modifier,
    allExecution: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth()
                )
                if (allExecution != null) {
                    Button(onClick = allExecution) {
                        Text(text = "一键执行")
                    }
                }
            }
            content()
        })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemWithSwitch(
    text: String,
    checked: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    onLongClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
//            .background(MaterialTheme.colorScheme.surfaceVariant)
            .combinedClickable(
                onClick = {
                    if (enabled) {
                        checked.value = !checked.value
                        onCheckedChange(checked.value)
                        updateOptionEntity()
                    }
                }, onLongClick = onLongClick
            )
            .padding(horizontal = 15.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall,
                overflow = TextOverflow.Visible,
                textAlign = TextAlign.Start
            )
            AnimatedSwitchButton(
                modifier = Modifier.size(48.dp), checked = checked.value
            )
        }
    }
}

@Composable
private fun AnimatedSwitchButton(
    modifier: Modifier = Modifier,
    checked: Boolean?,
) {
    val switchButton by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.ic_switch)
    )

    if (checked == null) {
        LottieAnimation(modifier = modifier, composition = switchButton, progress = { 1f })
    } else {
        val animationProgress by animateFloatAsState(
            targetValue = if (checked) 1f else 0f,
            animationSpec = tween(400, easing = LinearEasing),
            label = ""
        )

        LottieAnimation(
            modifier = modifier,
            composition = switchButton,
            progress = { animationProgress })
    }
}


@Composable
private fun CustomBookShelfTopImageOption(
    title: String,
    customBookShelfTopImageModel: CustomBookShelfTopImageModel,
    modifier: Modifier = Modifier,
    onValueChange: (CustomBookShelfTopImageModel) -> Unit = {}
) {
    val border01 = rememberMutableStateOf(value = customBookShelfTopImageModel.border01)
    val font = rememberMutableStateOf(value = customBookShelfTopImageModel.font)
    val fontHLight = rememberMutableStateOf(value = customBookShelfTopImageModel.fontHLight)
    val fontLight = rememberMutableStateOf(value = customBookShelfTopImageModel.fontLight)
    val fontOnSurface = rememberMutableStateOf(value = customBookShelfTopImageModel.fontOnSurface)
    val surface01 = rememberMutableStateOf(value = customBookShelfTopImageModel.surface01)
    val surfaceIcon = rememberMutableStateOf(value = customBookShelfTopImageModel.surfaceIcon)
    val headImage = rememberMutableStateOf(value = customBookShelfTopImageModel.headImage)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth()
        )

        TextField(modifier = Modifier.fillMaxWidth(), value = border01.value, label = {
            Text(text = "边框颜色", style = MaterialTheme.typography.bodySmall)
        }, onValueChange = {
            border01.value = it
            onValueChange(
                customBookShelfTopImageModel.copy(
                    border01 = it
                )
            )
        }, trailingIcon = {
            if (border01.value.isNotBlank()) {
                IconButton(onClick = {
                    border01.value = ""
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }
        })

        TextField(modifier = Modifier.fillMaxWidth(), value = font.value, label = {
            Text(text = "字体颜色", style = MaterialTheme.typography.bodySmall)
        }, onValueChange = {
            font.value = it
            onValueChange(
                customBookShelfTopImageModel.copy(
                    font = it
                )
            )
        }, trailingIcon = {
            if (font.value.isNotBlank()) {
                IconButton(onClick = {
                    font.value = ""
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }
        })

        TextField(modifier = Modifier.fillMaxWidth(), value = fontHLight.value, label = {
            Text(text = "字体高亮颜色", style = MaterialTheme.typography.bodySmall)
        }, onValueChange = {
            fontHLight.value = it
            onValueChange(
                customBookShelfTopImageModel.copy(
                    fontHLight = it
                )
            )
        }, trailingIcon = {
            if (fontHLight.value.isNotBlank()) {
                IconButton(onClick = {
                    fontHLight.value = ""
                }) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = null
                    )
                }
            }
        })

        TextField(modifier = Modifier.fillMaxWidth(), value = fontLight.value, label = {
            Text(text = "字体浅色颜色", style = MaterialTheme.typography.bodySmall)
        }, onValueChange = {
            fontLight.value = it
            onValueChange(
                customBookShelfTopImageModel.copy(
                    fontLight = it
                )
            )
        }, trailingIcon = {
            if (fontLight.value.isNotBlank()) {
                IconButton(onClick = {
                    fontLight.value = ""
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }
        })

        TextField(modifier = Modifier.fillMaxWidth(), value = fontOnSurface.value, label = {
            Text(
                text = "字体在表面上的颜色", style = MaterialTheme.typography.bodySmall
            )
        }, onValueChange = {
            fontOnSurface.value = it
            onValueChange(
                customBookShelfTopImageModel.copy(
                    fontOnSurface = it
                )
            )
        }, trailingIcon = {
            if (fontOnSurface.value.isNotBlank()) {
                IconButton(onClick = {
                    fontOnSurface.value = ""
                }) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = null
                    )
                }
            }
        })

        TextField(modifier = Modifier.fillMaxWidth(), value = surface01.value, label = {
            Text(text = "表面颜色", style = MaterialTheme.typography.bodySmall)
        }, onValueChange = {
            surface01.value = it
            onValueChange(
                customBookShelfTopImageModel.copy(
                    surface01 = it
                )
            )
        }, trailingIcon = {
            if (surface01.value.isNotBlank()) {
                IconButton(onClick = {
                    surface01.value = ""
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }
        })

        TextField(modifier = Modifier.fillMaxWidth(), value = surfaceIcon.value, label = {
            Text(text = "曲面图标颜色", style = MaterialTheme.typography.bodySmall)
        }, onValueChange = {
            surfaceIcon.value = it
            onValueChange(
                customBookShelfTopImageModel.copy(
                    surfaceIcon = it
                )
            )
        }, trailingIcon = {
            if (surfaceIcon.value.isNotBlank()) {
                IconButton(onClick = {
                    surfaceIcon.value = ""
                }) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = null
                    )
                }
            }
        })

        TextField(modifier = Modifier.fillMaxWidth(), value = headImage.value, label = {
            Text(
                text = "顶部图片网络直链 ps:分辨率为 1125*504 最佳",
                style = MaterialTheme.typography.bodySmall
            )
        }, onValueChange = {
            headImage.value = it
            onValueChange(
                customBookShelfTopImageModel.copy(
                    headImage = it
                )
            )
        }, trailingIcon = {
            if (headImage.value.isNotBlank()) {
                IconButton(onClick = {
                    headImage.value = ""
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }
        })

    }


}

/**
 * 启动图像项目
 * @since 7.9.354-1296
 * @param [startImageModel] 启动图像模型
 * @param [modifier] 修饰符
 * @suppress Generate Documentation
 */
@Composable
private fun StartImageItem(
    startImageModel: StartImageModel,
    modifier: Modifier = Modifier,
) {
    val isUsed = rememberMutableStateOf(value = startImageModel.isUsed)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
    ) {
        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(model = startImageModel.preImageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = startImageModel.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(shape = RoundedCornerShape(50), colors = ButtonDefaults.textButtonColors(
                    containerColor = if (isUsed.value) Color.DarkGray else Color(
                        255, 230, 231
                    ),
                    contentColor = Color.Red,
                ), onClick = {
                    isUsed.value = !isUsed.value
                    startImageModel.isUsed = isUsed.value
                    updateOptionEntity()
                }) {
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = if (isUsed.value) "停用" else "启用",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1
                    )
                }

            }
        }
    }
}


@Composable
fun ConfirmationDialog(
    text: String,
    onShowCloseButton: Boolean = false,
    onConfirm: (() -> Unit)? = null,
    onDismiss: () -> Unit = {},
) = AlertDialog(title = {
    Text(text = "提示", style = MaterialTheme.typography.bodyLarge)
}, text = {
    Text(text = text, style = MaterialTheme.typography.bodyMedium)
}, confirmButton = {
    onConfirm?.let {
        TextButton(onClick = {
            it.invoke()
            onDismiss.invoke()
        }) {
            Text(
                text = stringResource(android.R.string.ok),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red
            )
        }
    }
}, dismissButton = {
    if (onShowCloseButton) {
        TextButton(onDismiss) {
            Text(
                text = stringResource(android.R.string.cancel),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}, onDismissRequest = onDismiss
)


@Composable
fun MyAlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    dismissButton: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit = {},
    title: String = "",
    text: @Composable () -> Unit
) = AlertDialog(
    onDismissRequest = onDismissRequest,
    modifier = modifier,
    confirmButton = confirmButton,
    dismissButton = dismissButton,
    title = {
        if (title.isNotBlank()) {
            Text(
                text = title, style = MaterialTheme.typography.bodyLarge
            )
        }
    },
    text = text
)

/**
 * 任务项目
 * @suppress Generate Documentation
 */
@Composable
fun TasksItem(
    total: Int,
    done: Int,
    title: String,
    modifier: Modifier = Modifier,
    singleExecution: (() -> Unit)? = null,
    allExecution: (() -> Unit)? = null,
    receive: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "$done/$total",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
        )
        singleExecution?.let {
            TextButton(it, enabled = done <= total) {
                Text(
                    text = "+1", style = MaterialTheme.typography.bodySmall, maxLines = 1
                )
            }
        }

        allExecution?.let {
            TextButton(it, enabled = done <= total) {
                Text(
                    text = "+${total - done}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
        }

        receive?.let {
            TextButton(it, enabled = done >= total) {
                Text(
                    text = "领取", style = MaterialTheme.typography.bodySmall, maxLines = 1
                )
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountItem(
    modifier: Modifier = Modifier,
    account: OptionEntity.TaskOption.AccountModel,
    onDelete: () -> Unit = {}
) {
    val state = rememberMutableStateOf(value = false)
    val imei = rememberMutableStateOf(value = account.imei)
    val uid = rememberMutableStateOf(value = account.uid)
    val ua = rememberMutableStateOf(value = account.ua)
    val cookie = rememberMutableStateOf(value = account.cookie)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
            .combinedClickable(
                onClick = {

                }, onLongClick = onDelete
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("UID: ${account.uid}", modifier = Modifier.weight(1f))

        IconButton(onClick = {
            state.value = !state.value
        }) {
            if (state.value) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null
                )
            } else {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null
                )
            }
        }
    }

    AnimatedVisibility(visible = state.value) {
        Column {
            ItemWithEditText(title = "QIMEI", text = imei, onTextChange = {
                account.imei = it
            })
            ItemWithEditText(title = "UID", text = uid, onTextChange = {
                account.uid = it
            })
            ItemWithEditText(title = "User-Agent", text = ua, onTextChange = {
                account.ua = it
            })
            ItemWithEditText(title = "Cookie", text = cookie, onTextChange = {
                account.cookie = it
            })
        }
    }

}

/**
 * 顶级屏幕
 * 创建[TopLevelScreen]
 * @param [route] 路线
 * @param [imageVector] 图像矢量
 * @param [label] 标签
 * @suppress Generate Documentation
 */
enum class TopLevelScreen(
    val route: String,
    val imageVector: ImageVector,
    val label: String,
) {
    Main(
        "route_main", Icons.Filled.Home, "主设置"
    ),
    Purify(
        "route_purify", Icons.Filled.Delete, "净化"
    ),
    About(
        "route_about", Icons.Filled.Info, "关于"
    )
}
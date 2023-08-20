package cn.xihan.qdds

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build.*
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.xihan.qdds.HookEntry.Companion.NOT_SUPPORT_OLD_LAYOUT_VERSION_CODE
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import com.highcapable.yukihookapi.hook.xposed.parasitic.activity.base.ModuleAppCompatActivity
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2023/1/27 15:48
 * @介绍 :
 */
class MainActivity : ModuleAppCompatActivity() {

    val versionCode by lazy { getVersionCode(HookEntry.QD_PACKAGE_NAME) }

    override val moduleTheme =
        com.google.android.material.R.style.Theme_Material3_DayNight_NoActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mdc3Theme {
                val systemUiController = rememberSystemUiController()
                val darkIcons = isSystemInDarkTheme()
                SideEffect {
                    systemUiController.apply {
                        setSystemBarsColor(
                            color = Color.Transparent, darkIcons = !darkIcons
                        )
                        setNavigationBarColor(
                            color = Color.Transparent, darkIcons = !darkIcons
                        )
                    }

                }
                ComposeContent()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ComposeContent() {
        val permission = rememberMutableStateOf(
            value = XXPermissions.isGranted(
                this, if (this.applicationInfo.targetSdkVersion > 30) arrayOf(
                    Permission.MANAGE_EXTERNAL_STORAGE, Permission.REQUEST_INSTALL_PACKAGES
                ) else Permission.Group.STORAGE.plus(Permission.REQUEST_INSTALL_PACKAGES)
            )
        )
        var allowDisclaimers by rememberMutableStateOf(value = HookEntry.optionEntity.allowDisclaimers)
        val items = listOf(
            MainScreen.MainSetting, MainScreen.PurifySetting, MainScreen.About
        )
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "QDReadHook")
            }, modifier = Modifier.fillMaxWidth(), actions = {
                Row {
                    IconButton(onClick = {
                        restartApplication()
                    }) {
                        Icon(Icons.Filled.Refresh, null)
                    }
                    IconButton(onClick = {
                        finish()
                    }) {
                        Icon(Icons.Filled.ExitToApp, null)
                    }
                }
            }, navigationIcon = {}, scrollBehavior = null
            )
        }, bottomBar = {
            if (permission.value && allowDisclaimers) {
                BottomNavigationBar(
                    navController = navController,
                    items = items,
                    modifier = Modifier.fillMaxWidth()
                        .navigationBarsPadding()
                )
            }
        }) { paddingValues ->

            if (permission.value) {
                if (allowDisclaimers) {
                    NavHost(
                        navController = navController,
                        startDestination = MainScreen.MainSetting.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        /**
                         * 主设置
                         */
                        composable(MainScreen.MainSetting.route) {
                            MainScreen(versionCode)
                        }

                        /**
                         * 净化
                         */
                        composable(MainScreen.PurifySetting.route) {
                            PurifyScreen(versionCode)
                        }

                        /**
                         * 关于
                         */
                        composable(MainScreen.About.route) {
                            AboutScreen(versionCode)
                        }

                    }
                } else {
                    Disclaimers(modifier = Modifier.padding(paddingValues), onAgreeClick = {
                        allowDisclaimers = true
                        HookEntry.optionEntity.allowDisclaimers = true
                        updateOptionEntity()
                    }, onDisagreeClick = {
                        finish()
                    })
                }
            } else {
                Column(
                    modifier = Modifier.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        "需要存储以及安装未知应用权限",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    Button(onClick = {
                        requestPermission(onGranted = {
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
 * Switch设置模型
 * @param title 标题
 * @param subTitle 副标题
 * @param checked 是否选中
 * @param onCheckedChange 选中状态改变事件
 * @param enabled 是否可用
 */
@Composable
fun SwitchSetting(
    title: String,
    checked: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    subTitle: String = "",
    onCheckedChange: (Boolean) -> Unit = {},
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier.clickable(onClick = {
            if (enabled) {
                checked.value = !checked.value
                onCheckedChange(checked.value)
                updateOptionEntity()
            }
        }), verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .padding(8.dp),
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Visible,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start
            )
            if (subTitle.isNotEmpty()) {
                Text(
                    text = subTitle,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Visible,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
            }
        }


        Switch(
            modifier = Modifier.scale(0.7f), checked = checked.value, onCheckedChange = {
                checked.value = it
                onCheckedChange(it)
                updateOptionEntity()
            }, enabled = enabled
        )

    }
}

@Composable
fun EditTextSetting(
    title: String,
    text: MutableState<String>,
    modifier: Modifier = Modifier,
    subTitle: String = "",
    right: @Composable () -> Unit = {},
    onTextChange: ((String) -> Unit) = {},
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .padding(8.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Visible,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start
            )
            if (subTitle.isNotEmpty()) {

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subTitle,
                    overflow = TextOverflow.Visible,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
            }

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text.value,
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
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        }
                    }


                },
                // shape = RoundedCornerShape(30.dp),

            )
        }
    }
}

/**
 * 文本设置模型
 * @param title 标题
 * @param subTitle 副标题
 * @param onClick 点击事件
 * @param modifier Modifier
 * @param showRightIcon 是否显示右侧图标
 * @param bigTitle 是否大标题
 */
@Composable
fun TextSetting(
    title: String,
    modifier: Modifier = Modifier,
    subTitle: String = "",
    showRightIcon: Boolean = true,
    bigTitle: Boolean = false,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .padding(8.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Visible,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                style = if (bigTitle) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start
            )
            if (subTitle.isNotEmpty()) {

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subTitle,
                    overflow = TextOverflow.Visible,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
            }
        }

        if (showRightIcon) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp)
            )
        }
    }
}

@Composable
fun CustomBookShelfTopImageOption(
    title: String,
    customBookShelfTopImageModel: OptionEntity.BookshelfOption.CustomBookShelfTopImageModel,
    onValueChange: (OptionEntity.BookshelfOption.CustomBookShelfTopImageModel) -> Unit = {},
    modifier: Modifier = Modifier,
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
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextSetting(
            title = title,
            subTitle = "颜色使用十六进制代码,如 #FFFFFF",
            showRightIcon = false,
            bigTitle = false
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
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
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
            Text(text = "字体在表面上的颜色", style = MaterialTheme.typography.bodySmall)
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
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
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
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
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
 * 启动图模型
 */
@Composable
fun StartImageItem(
    startImageModel: OptionEntity.StartImageOption.StartImageModel,
    modifier: Modifier = Modifier,
) {
    val isUsed = rememberMutableStateOf(value = startImageModel.isUsed)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
    ) {
        Box {

            CoilImage(
                modifier = Modifier.fillMaxSize(),
                imageModel = { startImageModel.preImageUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop, alignment = Alignment.Center
                )
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
                    containerColor = if (isUsed.value) Color.DarkGray else Color(255, 230, 231),
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

/*
/**
 * 搜索标题栏
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchByTextAppBar(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
    onClickSearch: () -> Unit = {},
) {
    TextField(modifier = modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            Row {
                if (text.isNotBlank()) {
                    IconButton(onClick = {
                        onTextChange("")
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }

                IconButton(onClick = onClickSearch) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            }
        })


}

 */

/**
 * 主设置
 */
@Composable
fun MainScreen(
    versionCode: Int,
    context: Context = LocalContext.current,
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            TextSetting(title = "主设置", showRightIcon = false, bigTitle = true)

            Column(modifier = Modifier.padding(4.dp)) {
                SwitchSetting(
                    title = "自动签到",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableAutoSign),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enableAutoSign = it
                    }
                )

                SwitchSetting(
                    title = "自动领取阅读积分",
                    subTitle = "非后台领取，需要进到阅读积分页面",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableReceiveReadingCreditsAutomatically),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enableReceiveReadingCreditsAutomatically =
                            it
                    }
                )

                SwitchSetting(
                    title = "发帖上传图片显示直链",
                    subTitle = "图片上传完后会弹框",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enablePostToShowImageUrl),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enablePostToShowImageUrl =
                            it
                    }
                )


                SwitchSetting(title = "本地至尊卡",
                    subTitle = "仅美观，无任何实际作用",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableLocalCard),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enableLocalCard = it
                    })

                val freeAdReward =
                    rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableFreeAdReward)
                SwitchSetting(title = "免广告领取奖励", checked = freeAdReward, onCheckedChange = {
                    HookEntry.optionEntity.mainOption.enableFreeAdReward = it
                })

                if (freeAdReward.value && versionCode < 896) {
                    EditTextSetting(title = "免广告领取奖励自动退出时间",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.freeAdRewardAutoExitTime.toString()),
                        subTitle = "单位为秒,默认为3秒,如不需要把此数值设定大一些",
                        onTextChange = {
                            if (it.isNotBlank() && it.isNumber()) {
                                HookEntry.optionEntity.mainOption.freeAdRewardAutoExitTime =
                                    it.toInt()
                            }
                        })
                }

                SwitchSetting(title = "忽略限时免费批量订阅限制",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableIgnoreFreeSubscribeLimit),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enableIgnoreFreeSubscribeLimit = it
                    })

                if (versionCode > 827) {
                    SwitchSetting(title = "解锁会员卡专属背景",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableUnlockMemberBackground),
                        onCheckedChange = {
                            HookEntry.optionEntity.mainOption.enableUnlockMemberBackground = it
                        })
                }

                if (versionCode >= 868) {
                    SwitchSetting(title = "新我的布局",
                        subTitle = "896+版本已无旧版布局建议开启防止日志刷屏",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.accountOption.enableNewAccountLayout),
                        onCheckedChange = {
                            HookEntry.optionEntity.viewHideOption.accountOption.enableNewAccountLayout =
                                it
                        })

                    SwitchSetting(title = "新精选布局",
                        subTitle = "906+版本已无旧版布局建议开启防止日志刷屏",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableNewStore),
                        onCheckedChange = {
                            HookEntry.optionEntity.mainOption.enableNewStore = it
                        })
                }

                if (versionCode >= 884) {
                    SwitchSetting(title = "一键导出表情包",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableExportEmoji),
                        onCheckedChange = {
                            HookEntry.optionEntity.mainOption.enableExportEmoji = it
                        })

                }

                if (versionCode >= 896) {
                    SwitchSetting(
                        title = "试用模式弹框",
                        subTitle = "开启后起点会弹出隐私策略的弹框，不同意后点开始试用,完了关闭该选项即可",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableForceTrialMode),
                        onCheckedChange = {
                            HookEntry.optionEntity.mainOption.enableForceTrialMode = it
                        }
                    )

                    /*
                    SwitchSetting(
                        title = "测试功能",
                        subTitle = "群问",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableTestFunction),
                        onCheckedChange = {
                            HookEntry.optionEntity.mainOption.enableTestFunction = it
                        }
                    )

                     */
                }

                if (versionCode >= 906) {
                    val hideWelfare =
                        rememberMutableStateOf(value = HookEntry.optionEntity.hideBenefitsOption.enableHideWelfare)
                    SwitchSetting(
                        title = "显示全部隐藏福利",
                        subTitle = "开启后去搜索页面随便搜索一个内容，然后下面这个配置显示位置即可\n如果下面没有选项先去搜索一下",
                        checked = hideWelfare,
                        onCheckedChange = {
                            HookEntry.optionEntity.hideBenefitsOption.enableHideWelfare = it
                        }
                    )

                    if (hideWelfare.value) {

                        val remoteHideWelfareList = rememberMutableStateOf(
                            value = HookEntry.optionEntity.hideBenefitsOption.remoteCHideWelfareList.joinToString(
                                ";"
                            )
                        )

                        EditTextSetting(title = "填入远程隐藏福利配置直链",
                            subTitle = "以\";\"分隔",
                            right = {
                                Insert(list = remoteHideWelfareList)
                            },
                            text = remoteHideWelfareList,
                            onTextChange = {
                                HookEntry.optionEntity.hideBenefitsOption.remoteCHideWelfareList =
                                    HookEntry.parseKeyWordOption(it)
                            })

                        if (remoteHideWelfareList.value.isNotBlank()) {
                            TextSetting(
                                title = "获取远程隐藏福利信息",
                                modifier = Modifier.padding(4.dp),
                                onClick = {
                                    context.checkHideWelfareUpdate()
                                }
                            )
                        }

                        TextSetting(
                            title = "隐藏福利显示位置列表",
                            modifier = Modifier.padding(4.dp),
                            onClick = {
                                context.multiChoiceSelector(HookEntry.optionEntity.hideBenefitsOption.configurations)
                            }
                        )

                        TextSetting(
                            title = "清空隐藏福利列表",
                            modifier = Modifier.padding(4.dp),
                            onClick = {
                                HookEntry.optionEntity.hideBenefitsOption.hideWelfareList.clear()
                                updateOptionEntity()
                                hideWelfare.value = false
                            }
                        )
                    }

                }


            }

        }

        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "书架设置", showRightIcon = false, bigTitle = true)

            Column(modifier = Modifier.padding(4.dp)) {

                if (versionCode > 827) {

                    SwitchSetting(title = "新书架布局",
                        subTitle = "906+版本已无旧版布局建议开启防止日志刷屏",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.bookshelfOption.enableNewBookShelfLayout),
                        onCheckedChange = {
                            HookEntry.optionEntity.bookshelfOption.enableNewBookShelfLayout = it
                        })

                }

                if (versionCode < NOT_SUPPORT_OLD_LAYOUT_VERSION_CODE) {
                    SwitchSetting(title = "旧版布局",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.bookshelfOption.enableOldLayout),
                        onCheckedChange = {
                            HookEntry.optionEntity.bookshelfOption.enableOldLayout = it
                        })
                }

                val enableCustomBookShelfTopImage =
                    rememberMutableStateOf(value = HookEntry.optionEntity.bookshelfOption.enableCustomBookShelfTopImage)

                SwitchSetting(title = "启用自定义书架顶部图片",
                    subTitle = "内容可空,不填则为官方默认",
                    checked = enableCustomBookShelfTopImage,
                    onCheckedChange = {
                        HookEntry.optionEntity.bookshelfOption.enableCustomBookShelfTopImage = it
                    })

                if (enableCustomBookShelfTopImage.value) {
                    val enableSameNightAndDay =
                        rememberMutableStateOf(value = HookEntry.optionEntity.bookshelfOption.enableSameNightAndDay)

                    SwitchSetting(title = "启用夜间和日间相同",
                        checked = enableSameNightAndDay,
                        onCheckedChange = {
                            HookEntry.optionEntity.bookshelfOption.enableSameNightAndDay = it
                        })

                    CustomBookShelfTopImageOption(title = "白天模式",
                        customBookShelfTopImageModel = HookEntry.optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel,
                        onValueChange = {
                            HookEntry.optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel =
                                it
                            updateOptionEntity()
                        })

                    if (!enableSameNightAndDay.value) {
                        CustomBookShelfTopImageOption(title = "夜间模式",
                            customBookShelfTopImageModel = HookEntry.optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel,
                            onValueChange = {
                                HookEntry.optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel =
                                    it
                                updateOptionEntity()
                            })
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "阅读页设置", showRightIcon = false, bigTitle = true)

            Column(modifier = Modifier.padding(4.dp)) {
                if (versionCode >= 868) {
                    SwitchSetting(title = "阅读页章评图片长按保存原图",
                        subTitle = "进入图片详情后长按图片",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.readPageOption.enableShowReaderPageChapterSaveRawPicture),
                        onCheckedChange = {
                            HookEntry.optionEntity.readPageOption.enableShowReaderPageChapterSaveRawPicture =
                                it
                        })

                    SwitchSetting(title = "阅读页章评评论长按复制",
                        subTitle = "会覆盖官方默认弹框",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.readPageOption.enableCopyReaderPageChapterComment),
                        onCheckedChange = {
                            HookEntry.optionEntity.readPageOption.enableCopyReaderPageChapterComment =
                                it
                        })

                    SwitchSetting(title = "阅读页章评图片保存原图对话框",
                        subTitle = "直接长按图片显示直链地址",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.readPageOption.enableShowReaderPageChapterSavePictureDialog),
                        onCheckedChange = {
                            HookEntry.optionEntity.readPageOption.enableShowReaderPageChapterSavePictureDialog =
                                it
                        })

                    if (versionCode >= 884) {
                        SwitchSetting(title = "阅读页章评音频导出对话框",
                            subTitle = "点击一下播放成功后即可长按弹出对话框",
                            checked = rememberMutableStateOf(value = HookEntry.optionEntity.readPageOption.enableShowReaderPageChapterSaveAudioDialog),
                            onCheckedChange = {
                                HookEntry.optionEntity.readPageOption.enableShowReaderPageChapterSaveAudioDialog =
                                    it
                            })
                    }

                    val enableReadTimeDouble =
                        rememberMutableStateOf(value = HookEntry.optionEntity.readPageOption.enableReadTimeDouble)
                    SwitchSetting(title = "阅读时间加倍",
                        subTitle = "ps:时灵时不灵",
                        checked = enableReadTimeDouble,
                        onCheckedChange = {
                            HookEntry.optionEntity.readPageOption.enableReadTimeDouble = it
                        })
                    if (enableReadTimeDouble.value) {
                        SwitchSetting(title = "并加VIP章节时间",
                            checked = rememberMutableStateOf(value = HookEntry.optionEntity.readPageOption.enableVIPChapterTime),
                            onCheckedChange = {
                                HookEntry.optionEntity.readPageOption.enableVIPChapterTime = it
                            })

                        val doubleSpeed =
                            rememberMutableStateOf(value = HookEntry.optionEntity.readPageOption.doubleSpeed.toString())

                        EditTextSetting(title = "倍速设定",
                            subTitle = "默认为5倍,建议倍速不要太大，开大了到时候号没了与作者无关",
                            text = doubleSpeed,
                            onTextChange = {
                                if (it.isNotBlank() || it.isNumber()) {
                                    HookEntry.optionEntity.readPageOption.doubleSpeed = it.toInt()
                                }
                            })

                    }
                }

                if (versionCode > 827) {
                    val enableCustomReaderThemePath =
                        rememberMutableStateOf(value = HookEntry.optionEntity.readPageOption.enableCustomReaderThemePath)
                    SwitchSetting(title = "自定义阅读页主题路径",
                        checked = enableCustomReaderThemePath,
                        onCheckedChange = {
                            HookEntry.optionEntity.readPageOption.enableCustomReaderThemePath = it
                        })

                    if (enableCustomReaderThemePath.value) {
                        TextSetting(title = "可视化阅读页背景色调调整", onClick = {
                            context.showVisualizeReadingPageBackgroundColorAdjustmentDialog()
                        })
                    }
                }

            }
        }


        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "启动图设置", showRightIcon = false, bigTitle = true)

            Column(modifier = Modifier.padding(4.dp)) {

                val enableCustomStartImage =
                    rememberMutableStateOf(value = HookEntry.optionEntity.startImageOption.enableCustomStartImage)

                SwitchSetting(title = "启用自定义启动图",
                    checked = enableCustomStartImage,
                    onCheckedChange = {
                        HookEntry.optionEntity.startImageOption.enableCustomStartImage = it
                    })

                if (enableCustomStartImage.value) {
                    val enableCaptureTheOfficialLaunchMapList =
                        rememberMutableStateOf(value = HookEntry.optionEntity.startImageOption.enableCaptureTheOfficialLaunchMapList)

                    SwitchSetting(title = "启用抓取官方启动图",
                        checked = enableCaptureTheOfficialLaunchMapList,
                        subTitle = "启用后去启动图页面滑到底，然后重启起点/模块就可以看到数据了,之后可关闭",
                        onCheckedChange = {
                            HookEntry.optionEntity.startImageOption.enableCaptureTheOfficialLaunchMapList =
                                it
                        })

                    SwitchSetting(title = "启用自定义本地启动图",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.startImageOption.enableCustomLocalStartImage),
                        subTitle = "启用后会重定向下载路径为:\"$splashPath\"\n随机所存在的图片参与启动图",
                        onCheckedChange = {
                            HookEntry.optionEntity.startImageOption.enableCustomLocalStartImage =
                                it
                        })

                    if (HookEntry.optionEntity.startImageOption.officialLaunchMapList.isNotEmpty()) {
                        // 展开状态
                        val enableCaptureTheOfficialLaunchMapListExpand =
                            rememberMutableStateOf(value = false)
                        // 展开图标
                        val expandIcon =
                            if (enableCaptureTheOfficialLaunchMapListExpand.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

                        // 展开按钮
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                enableCaptureTheOfficialLaunchMapListExpand.value =
                                    !enableCaptureTheOfficialLaunchMapListExpand.value
                            }
                            .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "官方启动图列表: ${HookEntry.optionEntity.startImageOption.officialLaunchMapList.size}张"
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
                                    HookEntry.optionEntity.startImageOption.officialLaunchMapList
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
                                                        .height(250.dp)
                                                        .fillParentMaxWidth(.3f)
                                                        .padding(
                                                            2.dp
                                                        ),
                                                )
                                            }

                                        }

                                    }

                                }


                            }
                        }


                    }

                    // 网络图片地址
                    val customStartImageUrlList = rememberMutableStateOf(
                        value = HookEntry.optionEntity.startImageOption.customStartImageUrlList.joinToString(
                            ";"
                        )
                    )

                    EditTextSetting(title = "填入网络图片直链",
                        subTitle = "以\";\"分隔",
                        right = {
                            Insert(list = customStartImageUrlList)
                        },
                        text = customStartImageUrlList,
                        onTextChange = {
                            HookEntry.optionEntity.startImageOption.customStartImageUrlList =
                                HookEntry.parseKeyWordOption(it)
                        })


                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            TextSetting(title = "闪屏设置", showRightIcon = false, bigTitle = true)

            Column(modifier = Modifier.padding(4.dp)) {

                val enableSplash =
                    rememberMutableStateOf(value = HookEntry.optionEntity.splashOption.enableSplash)

                SwitchSetting(title = "启用闪屏页", checked = enableSplash, onCheckedChange = {
                    HookEntry.optionEntity.splashOption.enableSplash = it
                })

                if (enableSplash.value) {

                    SwitchSetting(title = "闪屏页显示全部按钮",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.splashOption.enableCustomSplash),
                        onCheckedChange = {
                            HookEntry.optionEntity.splashOption.enableCustomSplash = it
                        })

                    val customSplash =
                        rememberMutableStateOf(value = HookEntry.optionEntity.splashOption.enableCustomSplash)

                    SwitchSetting(title = "启用自定义闪屏页",
                        checked = customSplash,
                        onCheckedChange = {
                            HookEntry.optionEntity.splashOption.enableCustomSplash = it
                        })

                    if (customSplash.value) {

                        EditTextSetting(title = "填入自定义闪屏页跳转到书籍页面的关键词",
                            subTitle = "填入书籍的BookId,详情页分享链接里面\"bookid=\"后面到\"&\"前那串数字就是了",
                            text = rememberMutableStateOf(value = HookEntry.optionEntity.splashOption.customBookId),
                            onTextChange = {
                                HookEntry.optionEntity.splashOption.customBookId = it
                            })

                        EditTextSetting(title = "自定义闪屏页类型",
                            subTitle = "填入 0 或者 1 其他数字可能无效喔~",
                            text = rememberMutableStateOf(value = HookEntry.optionEntity.splashOption.customSplashType.toString()),
                            onTextChange = {
                                if (it.isNotBlank() && it.isNumber()) {
                                    HookEntry.optionEntity.splashOption.customSplashType =
                                        it.toInt()
                                }
                            })

                        EditTextSetting(title = "自定义闪屏页图片",
                            subTitle = "填入图片所在绝对路径,如失败请给起点存储权限,或检查是否填写正确,留空默认",
                            text = rememberMutableStateOf(value = HookEntry.optionEntity.splashOption.customSplashImageFilePath),
                            onTextChange = {
                                HookEntry.optionEntity.splashOption.customSplashImageFilePath = it
                            })


                    }


                }


            }
        }

    }
}

/**
 * 净化设置
 */
@Composable
fun PurifyScreen(
    versionCode: Int,
    context: Context = LocalContext.current,
) {

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            TextSetting(title = "广告设置", showRightIcon = false, bigTitle = true)

            TextSetting(title = "广告设置列表", modifier = Modifier.padding(4.dp), onClick = {
                val shieldOptionList = HookEntry.optionEntity.advOption.advOptionList
                val checkedItems = BooleanArray(shieldOptionList.size)
                if (HookEntry.optionEntity.advOption.advOptionSelectedList.isNotEmpty()) {
                    safeRun {
                        shieldOptionList.forEachIndexed { index, _ ->
                            // 对比 shieldOptionList 和 optionEntity.viewHideOption.accountOption.configurationsSelectedOptionList 有相同的元素就设置为true
                            if (HookEntry.optionEntity.advOption.advOptionSelectedList.any { it == index }) {
                                checkedItems[index] = true
                            }
                        }
                    }
                }
                context.multiChoiceSelector(
                    shieldOptionList, checkedItems, "禁用选项列表"
                ) { _, i, isChecked ->
                    checkedItems[i] = isChecked
                }.doOnDismiss {
                    checkedItems.forEachIndexed { index, b ->
                        if (b) {
                            HookEntry.optionEntity.advOption.advOptionSelectedList += index
                        } else {
                            HookEntry.optionEntity.advOption.advOptionSelectedList -= index
                        }
                    }
                    updateOptionEntity()
                }

            })

        }

        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            TextSetting(title = "拦截设置", showRightIcon = false, bigTitle = true)

            TextSetting(title = "拦截设置列表", modifier = Modifier.padding(4.dp), onClick = {
                context.multiChoiceSelector(HookEntry.optionEntity.interceptOption.configurations)
            })

        }

        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "屏蔽设置", showRightIcon = false, bigTitle = true)

            Column(modifier = Modifier.padding(4.dp)) {

                TextSetting(title = "屏蔽选项列表", onClick = {
                    val shieldOptionList = listOf(
                        "搜索-发现(热词)",
                        "搜索-热门作品榜",
                        "搜索-人气标签榜",
                        "搜索-为你推荐",
                        "精选-主页面",
                        "精选-分类",
                        "精选-分类-全部作品",
                        "精选-免费-免费推荐",
                        "精选-免费-新书入库",
                        "精选-畅销精选、主编力荐等更多",
                        "精选-新书强推、三江推荐",
                        "精选-排行榜",
                        "精选-新书",
                        "每日导读",
                        "精选-漫画",
                        "精选-漫画-其他",
                        "阅读-最后一页-看过此书的人还看过",
                        "阅读-最后一页-同类作品推荐",
                        "阅读-最后一页-推荐",
                        "分类-小编力荐、本周强推等更多"
                    )
                    val checkedItems = BooleanArray(shieldOptionList.size)
                    if (HookEntry.optionEntity.shieldOption.shieldOptionValueSet.isNotEmpty()) {
                        safeRun {
                            shieldOptionList.forEachIndexed { index, _ ->
                                if (index in HookEntry.optionEntity.shieldOption.shieldOptionValueSet) {
                                    checkedItems[index] = true
                                }
                            }
                        }
                    }
                    context.multiChoiceSelector(
                        shieldOptionList, checkedItems, "屏蔽选项列表"
                    ) { _, i, isChecked ->
                        checkedItems[i] = isChecked
                    }.doOnDismiss {
                        checkedItems.forEachIndexed { index, b ->
                            if (b) {
                                HookEntry.optionEntity.shieldOption.shieldOptionValueSet += index
                            } else {
                                HookEntry.optionEntity.shieldOption.shieldOptionValueSet -= index
                            }
                        }
                        updateOptionEntity()
                    }
                })

                SwitchSetting(title = "启用快速屏蔽弹窗",
                    subTitle = "书籍详情页长按书名或作者名弹出对话框\nps:书名和作者挨在一起的那两个",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.shieldOption.enableQuickShieldDialog),
                    onCheckedChange = {
                        HookEntry.optionEntity.shieldOption.enableQuickShieldDialog = it
                    })

                val authorList = rememberMutableStateOf(
                    value = HookEntry.optionEntity.shieldOption.authorList.joinToString(
                        ";"
                    ),
                )
                EditTextSetting(
                    title = "填入需要屏蔽的完整作者名称",
                    text = authorList,
                    subTitle = "使用 \";\" 分隔",
                    right = {
                        Insert(authorList)
                    },
                    onTextChange = {
                        HookEntry.optionEntity.shieldOption.authorList =
                            HookEntry.parseKeyWordOption(it)
                    })

                val bookNameList = rememberMutableStateOf(
                    value = HookEntry.optionEntity.shieldOption.bookNameList.joinToString(
                        ";"
                    )
                )
                EditTextSetting(
                    title = "填入需要屏蔽的书名关键词",
                    text = bookNameList,
                    subTitle = "注意:单字威力巨大!!!\n使用 \";\" 分隔",
                    right = {
                        Insert(bookNameList)
                    },
                    onTextChange = {
                        HookEntry.optionEntity.shieldOption.bookNameList =
                            HookEntry.parseKeyWordOption(it)
                    }
                )

                SwitchSetting(title = "启用书类型增强屏蔽",
                    subTitle = "解除下面的限制改为包含关键词执行屏蔽\n例如:关键词为\"仙侠\"时,类型为\"古典仙侠\"的书也会被屏蔽",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.shieldOption.enableBookTypeEnhancedBlocking),
                    onCheckedChange = {
                        HookEntry.optionEntity.shieldOption.enableBookTypeEnhancedBlocking = it
                    })

                val bookTypeList = rememberMutableStateOf(
                    value = HookEntry.optionEntity.shieldOption.bookTypeList.joinToString(";")
                )
                EditTextSetting(
                    title = "填入需要屏蔽的书类型",
                    text = bookTypeList,
                    subTitle = "必须匹配关键词才执行屏蔽\n使用 \";\" 分隔",
                    right = {
                        Insert(bookTypeList)
                    },
                    onTextChange = {
                        HookEntry.optionEntity.shieldOption.bookTypeList =
                            HookEntry.parseKeyWordOption(it)
                    }
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "隐藏控件设置", showRightIcon = false, bigTitle = true)

            Column(modifier = Modifier.padding(4.dp)) {
                SwitchSetting(title = "启用抓取底部导航栏",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.homeOption.enableCaptureBottomNavigation),
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.homeOption.enableCaptureBottomNavigation =
                            it
                    })

                TextSetting(title = "主页-隐藏控件列表", onClick = {
                    context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.homeOption.configurations)
                })

                val enableSelectedHide =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.selectedOption.enableSelectedHide)

                SwitchSetting(title = "精选-启用选项屏蔽",
                    checked = enableSelectedHide,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.selectedOption.enableSelectedHide = it
                    })

                if (enableSelectedHide.value) {
                    TextSetting(title = "精选-隐藏控件列表",
                        subTitle = "如若提示没有可用选项,请先打开精选页面滑一滑重启即可",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.selectedOption.configurations)
                        })
                }

                val enableSelectedTitleHide =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.selectedOption.enableSelectedTitleHide)

                SwitchSetting(title = "精选-标题启用选项屏蔽",
                    checked = enableSelectedTitleHide,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.selectedOption.enableSelectedTitleHide =
                            it
                    })

                if (enableSelectedTitleHide.value) {
                    TextSetting(title = "精选-标题隐藏控件列表",
                        subTitle = "如若提示没有可用选项,请先打开精选页面后重启即可",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.selectedOption.selectedTitleConfigurations)
                        })
                }

                SwitchSetting(title = "隐藏部分小红点",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.enableHideRedDot),
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.enableHideRedDot = it
                    })


                SwitchSetting(title = "搜索页面一刀切",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.enableSearchHideAllView),
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.enableSearchHideAllView = it
                    })

                SwitchSetting(title = "关闭青少年模式弹框",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.enableDisableQSNModeDialog),
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.enableDisableQSNModeDialog = it
                    })

                SwitchSetting(title = "隐藏我-右上角消息红点",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccountRightTopRedDot),
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccountRightTopRedDot =
                            it
                    })

                SwitchSetting(title = "隐藏精选-漫画轮播图广告",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.enableHideComicBannerAd),
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.enableHideComicBannerAd = it
                    })

                val hideFind =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.findOption.enableHideFindItem)

                SwitchSetting(title = "启用发现-隐藏控件",
                    subTitle = "如提示没有可用选项，请先打开发现页面,并重启起点",
                    checked = hideFind,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.findOption.enableHideFindItem = it
                    })

                if (hideFind.value) {

                    SwitchSetting(title = "隐藏发现-动态", checked = rememberMutableStateOf(
                        value = HookEntry.optionEntity.viewHideOption.findOption.feedsItem
                    ), onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.findOption.feedsItem = it
                    })

                    SwitchSetting(title = "隐藏发现-广播",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.findOption.broadCasts),
                        onCheckedChange = {
                            HookEntry.optionEntity.viewHideOption.findOption.broadCasts = it
                        })

                    TextSetting(title = "发现-头部列表", onClick = {
                        context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.findOption.headItem)
                    })

                    TextSetting(title = "发现-广告列表", onClick = {
                        context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.findOption.advItem)
                    })

                    TextSetting(title = "发现-筛选列表", onClick = {
                        context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.findOption.filterConfItem)
                    })

                }

                val hideAccount =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccount)

                SwitchSetting(title = "启用我-隐藏控件", checked = hideAccount, onCheckedChange = {
                    HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccount = it
                })

                if (hideAccount.value) {
                    TextSetting(title = "我-隐藏控件列表",
                        subTitle = "如提示没有可用选项，请先打开\"我\"页面,并重启起点",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.accountOption.configurations)
                        })

                    TextSetting(title = "我-隐藏控件列表(新)",
                        subTitle = "如提示没有可用选项，请先打开\"我\"页面,并重启起点",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.accountOption.newConfiguration)
                        })
                }

                val hideDetail =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.bookDetailOptions.enableHideBookDetail)

                SwitchSetting(title = "启用书籍详情-隐藏控件",
                    checked = hideDetail,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.bookDetailOptions.enableHideBookDetail =
                            it
                    })

                if (hideDetail.value) {
                    TextSetting(title = "书籍详情-隐藏控件列表", onClick = {
                        context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations)
                    })
                }

                val hideBookReadLastPage =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.bookLastPageOptions.enableHideBookLastPage)
                SwitchSetting(title = "启用阅读页-最后一页-隐藏控件",
                    checked = hideBookReadLastPage,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.bookLastPageOptions.enableHideBookLastPage =
                            it
                    })

                if (hideBookReadLastPage.value) {
                    TextSetting(title = "阅读页-最后一页-隐藏控件列表", onClick = {
                        context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.bookLastPageOptions.configurations)
                    })
                }

            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "替换规则设置", showRightIcon = false, bigTitle = true)

            Column(modifier = Modifier.padding(4.dp)) {

                val enableReplace =
                    rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.enableReplace)
                SwitchSetting(title = "启用替换", checked = enableReplace, onCheckedChange = {
                    HookEntry.optionEntity.replaceRuleOption.enableReplace = it
                })

                if (enableReplace.value) {
                    TextSetting(title = "替换规则列表", onClick = {
                        context.showReplaceOptionDialog()
                    })
                }

                /*
                val enableCustomBookFansValue =
                    rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.enableCustomBookFansValue)

                SwitchSetting(title = "启用自定义书粉丝值",
                    subTitle = "不会用可进群问\n使用此功能默认你阅读并同意免责声明\n造成的一切后果均个人行为,与模块作者无关",
                    checked = enableCustomBookFansValue,
                    onCheckedChange = {
                        HookEntry.optionEntity.bookFansValueOption.enableCustomBookFansValue = it
                    })

                if (enableCustomBookFansValue.value) {
                    EditTextSetting(title = "昵称",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.nickName),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.nickName = it
                        })

                    EditTextSetting(title = "相差的粉丝值",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.dValue.toString()),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.dValue = it.toLong()
                        })

                    EditTextSetting(title = "打赏描述",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.daShangDesc),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.daShangDesc = it
                        })

                    EditTextSetting(title = "头像地址",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.headImageUrl),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.headImageUrl = it
                        })

                    EditTextSetting(title = "联盟排名",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.leagueRank.toString()),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.leagueRank = it.toInt()
                        })

                    EditTextSetting(title = "联盟类型",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.leagueType.toString()),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.leagueType = it.toInt()
                        })

                    EditTextSetting(title = "排名",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.rank.toString()),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.rank = it.toInt()
                        })

                    EditTextSetting(title = "排名名称",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.rankName),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.rankName = it
                        })

                    EditTextSetting(title = "排名升级描述",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.rankUpgradeDesc),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.rankUpgradeDesc = it
                        })

                    EditTextSetting(title = "排序id",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.orderId.toString()),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.orderId = it.toInt()
                        })

                    EditTextSetting(title = "粉丝值/书友值",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.amount.toString()),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.amount = it.toInt()
                        })

                    EditTextSetting(title = "粉丝排名",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.fansRank.toString()),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.fansRank = it.toInt()
                        })

                    EditTextSetting(title = "标题图片地址",
                        text = rememberMutableStateOf(value = HookEntry.optionEntity.bookFansValueOption.mTitleImage),
                        onTextChange = {
                            HookEntry.optionEntity.bookFansValueOption.mTitleImage = it
                        })
                }

                 */

                /*
                if (versionCode >= 896) {

                    val enableCustomDeviceInfo =
                        rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.enableCustomDeviceInfo)

                    SwitchSetting(
                        title = "启用保存原始设备信息",
                        subTitle = "会将原始设备信息保存\n开启后获取成功一次之后最好关闭\n否则可能会卡IO线程",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.enableSaveOriginalDeviceInfo),
                        onCheckedChange = {
                            HookEntry.optionEntity.replaceRuleOption.enableSaveOriginalDeviceInfo =
                                it
                        }
                    )

                    TextSetting(
                        title = "原始设备信息"
                    ) {
                        context.alertDialog {
                            title = "原始设备信息"
                            message =
                                "${HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo}"
                            positiveButton("复制") {
                                context.copyToClipboard(HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo.toString())
                            }
                            build()
                            show()
                        }
                    }

                    SwitchSetting(
                        title = "启用自定义设备信息",
                        subTitle = "带*为必填项\n不会用可进群问\n使用此功能造成的一切后果自负",
                        checked = enableCustomDeviceInfo,
                        onCheckedChange = {
                            HookEntry.optionEntity.replaceRuleOption.enableCustomDeviceInfo = it
                        }
                    )

                    if (enableCustomDeviceInfo.value) {
                        EditTextSetting(
                            title = "设备厂商*",
                            subTitle = "如: OnePlus",
                            text = rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.brand),
                            onTextChange = {
                                HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.brand = it
                            }
                        )

                        EditTextSetting(
                            title = "设备型号*",
                            subTitle = "如: GM1900",
                            text = rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.model),
                            onTextChange = {
                                HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.model = it
                            }
                        )


                        val imei =
                            rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.imei)
                        EditTextSetting(
                            title = "设备IMEI*",
                            subTitle = "如: cc51cce4aafd4d0e0fd61031100014816b02",
                            text = imei,
                            right = {
                                TextButton(onClick = {
                                    imei.value = randomIMEI().md5()
                                    HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.imei =
                                        imei.value
                                    updateOptionEntity()
                                }) {
                                    Text(text = "随机生成")
                                }
                            },
                            onTextChange = {
                                HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.imei = it
                            }
                        )

                        val androidId =
                            rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.androidId)
                        EditTextSetting(
                            title = "设备AndroidId*",
                            text = androidId,
                            right = {
                                TextButton(onClick = {
                                    androidId.value = randomIMEI().md5().substring(0, 15)
                                    HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.androidId =
                                        androidId.value
                                    updateOptionEntity()
                                }) {
                                    Text(text = "随机生成")
                                }
                            },
                            onTextChange = {
                                HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.androidId =
                                    it
                            }
                        )

                        EditTextSetting(
                            title = "设备Mac地址*",
                            subTitle = "如: 02:00:00:00:00:00",
                            text = rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.macAddress),
                            onTextChange = {
                                HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.macAddress =
                                    it
                            }
                        )

                        EditTextSetting(
                            title = "设备序列号",
                            text = rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.serial),
                            onTextChange = {
                                HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.serial =
                                    it
                            }
                        )

                        EditTextSetting(
                            title = "设备Android版本*",
                            subTitle = "如: 33",
                            text = rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.androidVersion),
                            onTextChange = {
                                HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.androidVersion =
                                    it
                            }
                        )

                        EditTextSetting(
                            title = "设备屏幕高度*",
                            subTitle = "如: 2135",
                            text = rememberMutableStateOf(value = "${HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.screenHeight}"),
                            onTextChange = {
                                if (it.isNotBlank() || it.isNumber()) {
                                    HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.screenHeight =
                                        it.toInt()
                                }

                            }
                        )

                        EditTextSetting(
                            title = "设备CPU信息*",
                            subTitle = "如: 0000000000000000",
                            text = rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.cpuInfo),
                            onTextChange = {
                                HookEntry.optionEntity.replaceRuleOption.customDeviceInfo.cpuInfo =
                                    it
                            }
                        )

                    }
                }

                 */


            }
        }


    }
}

/**
 * 关于
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    versionCode: Int,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        SwitchSetting(title = "隐藏桌面图标",
            checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableHideAppIcon),
            onCheckedChange = {
                HookEntry.optionEntity.mainOption.enableHideAppIcon = it
                safeRun {
                    if (it) {
                        context.hideAppIcon()
                    } else {
                        context.showAppIcon()
                    }
                }
            })

        TextSetting(title = "重置模块配置文件", subTitle = "", onClick = {
            writeOptionFile(OptionEntity())
            context.toast("重置成功,即将重启应用")
            (context as? Activity)?.restartApplication()
        })

        TextSetting(title = "打赏", subTitle = "", onClick = {
            context.openUrl("https://github.com/xihan123/QDReadHook#%E6%89%93%E8%B5%8F")
        })

        TextSetting(title = "QD模块交流群", subTitle = "727983520", onClick = {
            context.joinQQGroup("JdqL9prgQ3epIUed3weaEkJwtNgNQaWa")
        })

        TextSetting(title = "QD模块赞助群",
            subTitle = "575801108\n赞助后加群主好友发记录",
            onClick = {
                context.alertDialog {
                    title = "作者的话"
                    message =
                        "因为开发QD模块占用日常时间过长，无法保证能够及时适配，希望大家能够理解。\n" + "QD模块的新版本更新将会在赞助群抢先体验。\n" + "开发不易，有能力的人可以进行赞助，交流群的相册有付款码。\n" + "赞助多少无要求，各凭心意。赞助群群费暂定二十元，后续群费会随时向上调整，就不再另行通知。\n" + "大家量力而行，非常感谢各位的支持。愿大家都有一个良好的追书体验~"

                    positiveButton("确定") {
                        context.joinQQGroup("3BZmGVQtRFFyTqz_Lhw7QpB_aV-WZ-Lj")
                    }
                    negativeButton("取消") {
                        it.dismiss()
                    }
                    build()
                    show()
                }
            })

        TextSetting(title = "TG交流群", onClick = {
            context.openUrl("https://t.me/+tHAFB7FQKHdiYjU9")
        })

        TextSetting(title = "作者", subTitle = "希涵", onClick = {
            context.openUrl("https://github.com/xihan123")
        })

        TextSetting(title = "常见问题", subTitle = "", onClick = {
            context.openUrl("https://jihulab.com/xihan123/QDReadHook/-/wikis/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98")
        })

        TextSetting(
            title = "编译时间", subTitle = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
            ).format(BuildConfig.BUILD_TIMESTAMP), showRightIcon = false
        )

        var openDialog by rememberMutableStateOf(value = false)
        TextSetting(title = stringResource(id = R.string.disclaimers_title),
            showRightIcon = false,
            onClick = {
                openDialog = true
            })

        if (openDialog) {
            AlertDialog(onDismissRequest = { openDialog = false }) {
                Card {
                    Disclaimers(
                        displayButton = false
                    )
                }
            }
        }

        TextSetting(
            title = "起点内部版本号",
            subTitle = "$versionCode",
            showRightIcon = false,
            onClick = {

            }
        )

        TextSetting(title = "模块版本号",
            subTitle = BuildConfig.VERSION_NAME,
            showRightIcon = false,
            onClick = {
                context.checkModuleUpdate()
            })
    }

}

/**
 * 免责声明
 * @param modifier Modifier
 * @param onAgreeClick 点击同意
 * @param onDisagreeClick 点击不同意
 * @param displayButton 是否显示倒计时
 */
@Composable
fun Disclaimers(
    modifier: Modifier = Modifier,
    onAgreeClick: () -> Unit = {},
    onDisagreeClick: () -> Unit = {},
    displayButton: Boolean = true,
) {
    var remainingTime by rememberMutableStateOf(value = 3L)
    if (displayButton) {
        val isActive =
            LocalLifecycleOwner.current.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        LaunchedEffect(isActive) {
            while (isActive && remainingTime > 0) {
                delay(1000)
                remainingTime -= 1
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
        Text(
            text = stringResource(id = R.string.disclaimers_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.disclaimers_message))

        if (displayButton) {
            Spacer(modifier = Modifier.height(16.dp))

            if (remainingTime != 0L) {
                Text(
                    text = String.format(
                        stringResource(id = R.string.remaining_time), remainingTime
                    ), fontWeight = FontWeight.Bold, fontSize = 24.sp
                )
            } else {
                Button(onClick = onAgreeClick) { Text(text = stringResource(id = R.string.disclaimers_agree)) }
            }

            Button(onClick = onDisagreeClick) { Text(text = stringResource(id = R.string.disclaimers_disagree)) }

        }


    }
}

@Composable
private fun BottomNavigationBar(
    navController: NavController, items: List<MainScreen>, modifier: Modifier = Modifier
) {
    NiaNavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { item ->
            NiaNavigationBarItem(
                icon = { Icon(item.imageVector, contentDescription = null) },
                label = { Text(item.title) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
//                    alwaysShowLabel = false
            )
        }
    }

}

private sealed class MainScreen(
    val route: String, val imageVector: ImageVector, val title: String
) {
    /**
     * 主设置
     */
    object MainSetting : MainScreen("main_setting", Icons.Filled.Home, "主设置")

    /**
     * 净化设置
     */
    object PurifySetting : MainScreen("purify_setting", Icons.Filled.Delete, "净化")

    /**
     * 关于
     */
    object About : MainScreen("about", Icons.Filled.Info, "关于")


}
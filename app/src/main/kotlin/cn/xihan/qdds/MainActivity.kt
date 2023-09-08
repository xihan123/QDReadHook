package cn.xihan.qdds

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
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
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            QTheme {
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
        var currentDisclaimersVersionCode by rememberMutableStateOf(value = HookEntry.optionEntity.currentDisclaimersVersionCode)
        val latestDisclaimersVersionCode by rememberMutableStateOf(value = HookEntry.optionEntity.latestDisclaimersVersionCode)
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
            if (permission.value && allowDisclaimers && currentDisclaimersVersionCode == latestDisclaimersVersionCode) {
                BottomNavigationBar(
                    navController = navController,
                    items = items,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                )
            }
        }) { paddingValues ->
            if (permission.value) {
                if (allowDisclaimers && currentDisclaimersVersionCode == latestDisclaimersVersionCode) {
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
                        currentDisclaimersVersionCode = latestDisclaimersVersionCode
                        HookEntry.optionEntity.allowDisclaimers = true
                        HookEntry.optionEntity.currentDisclaimersVersionCode =
                            latestDisclaimersVersionCode
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
                        "需要存储以及安装未知应用权限\n存储权限:用来管理位于外部存储的配置文件\n安装未知应用权限:Android 11及以上读取其他应用版本号",
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
            modifier = Modifier
                .weight(1f)
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
            modifier = Modifier
                .weight(1f)
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
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextSetting(
    title: String,
    modifier: Modifier = Modifier,
    subTitle: String = "",
    showRightIcon: Boolean = true,
    bigTitle: Boolean = false,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
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
    modifier: Modifier = Modifier,
    onValueChange: (OptionEntity.BookshelfOption.CustomBookShelfTopImageModel) -> Unit = {}
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            TextSetting(title = "主设置", showRightIcon = false, bigTitle = true)

            Column(modifier = Modifier.padding(4.dp)) {
                SwitchSetting(title = "自动签到",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableAutoSign),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enableAutoSign = it
                    })

                SwitchSetting(title = "自动跳过闪屏页",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableAutoSkipSplash),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enableAutoSkipSplash = it
                    })

                SwitchSetting(title = "自动领取阅读积分",
                    subTitle = "非后台领取，需要进到阅读积分页面",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableReceiveReadingCreditsAutomatically),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enableReceiveReadingCreditsAutomatically =
                            it
                    })

                SwitchSetting(title = "发帖上传图片显示直链",
                    subTitle = "图片上传完后会弹框",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enablePostToShowImageUrl),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enablePostToShowImageUrl = it
                    })


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

                SwitchSetting(title = "测试页面",
                    subTitle = "在起点关于页面，点按或者长按起点图标",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableDebugActivity),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enableDebugActivity = it
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
                    SwitchSetting(title = "试用模式弹框",
                        subTitle = "开启后起点会弹出隐私策略的弹框，不同意后点开始试用,完了关闭该选项即可",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableForceTrialMode),
                        onCheckedChange = {
                            HookEntry.optionEntity.mainOption.enableForceTrialMode = it
                        })

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
                    SwitchSetting(title = "显示全部隐藏福利",
                        subTitle = "开启后去搜索页面随便搜索一个内容，然后下面这个配置显示位置即可\n如果下面没有选项先去搜索一下",
                        checked = hideWelfare,
                        onCheckedChange = {
                            HookEntry.optionEntity.hideBenefitsOption.enableHideWelfare = it
                        })

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
                                    parseKeyWordOption(it)
                            })

                        if (remoteHideWelfareList.value.isNotBlank()) {
                            TextSetting(title = "获取远程隐藏福利信息",
                                modifier = Modifier.padding(4.dp),
                                onClick = {
                                    context.checkHideWelfareUpdate()
                                })
                        }

                        TextSetting(title = "隐藏福利显示位置列表",
                            modifier = Modifier.padding(4.dp),
                            onClick = {
                                context.multiChoiceSelector(HookEntry.optionEntity.hideBenefitsOption.configurations)
                            })

                        TextSetting(title = "清空隐藏福利列表",
                            modifier = Modifier.padding(4.dp),
                            onClick = {
                                HookEntry.optionEntity.hideBenefitsOption.hideWelfareList.clear()
                                updateOptionEntity()
                                hideWelfare.value = false
                            })
                    }

                }

            }

        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
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
            modifier = Modifier
                .fillMaxWidth()
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
            modifier = Modifier
                .fillMaxWidth()
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
                            HookEntry.optionEntity.startImageOption.enableCustomLocalStartImage = it
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

                    EditTextSetting(title = "填入网络图片直链", subTitle = "以\";\"分隔", right = {
                        Insert(list = customStartImageUrlList)
                    }, text = customStartImageUrlList, onTextChange = {
                        HookEntry.optionEntity.startImageOption.customStartImageUrlList =
                            parseKeyWordOption(it)
                    })


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
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            TextSetting(title = "广告设置", showRightIcon = false, bigTitle = true)

            TextSetting(title = "广告设置列表", modifier = Modifier.padding(4.dp), onClick = {
                context.multiChoiceSelector(HookEntry.optionEntity.advOption.configurations)
            })


        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "屏蔽设置", showRightIcon = false, bigTitle = true)

            Column(modifier = Modifier.padding(4.dp)) {

                TextSetting(title = "屏蔽选项列表", onClick = {
                    context.multiChoiceSelector(HookEntry.optionEntity.shieldOption.configurations)
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
                EditTextSetting(title = "填入需要屏蔽的完整作者名称",
                    text = authorList,
                    subTitle = "使用 \";\" 分隔",
                    right = {
                        Insert(authorList)
                    },
                    onTextChange = {
                        HookEntry.optionEntity.shieldOption.authorList = parseKeyWordOption(it)
                    })

                val bookNameList = rememberMutableStateOf(
                    value = HookEntry.optionEntity.shieldOption.bookNameList.joinToString(
                        ";"
                    )
                )
                EditTextSetting(title = "填入需要屏蔽的书名关键词",
                    text = bookNameList,
                    subTitle = "注意:单字威力巨大!!!\n使用 \";\" 分隔",
                    right = {
                        Insert(bookNameList)
                    },
                    onTextChange = {
                        HookEntry.optionEntity.shieldOption.bookNameList = parseKeyWordOption(it)
                    })

                SwitchSetting(title = "启用书类型增强屏蔽",
                    subTitle = "解除下面的限制改为包含关键词执行屏蔽\n例如:关键词为\"仙侠\"时,类型为\"古典仙侠\"的书也会被屏蔽",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.shieldOption.enableBookTypeEnhancedBlocking),
                    onCheckedChange = {
                        HookEntry.optionEntity.shieldOption.enableBookTypeEnhancedBlocking = it
                    })

                val bookTypeList = rememberMutableStateOf(
                    value = HookEntry.optionEntity.shieldOption.bookTypeList.joinToString(";")
                )
                EditTextSetting(title = "填入需要屏蔽的书类型",
                    text = bookTypeList,
                    subTitle = "必须匹配关键词才执行屏蔽\n使用 \";\" 分隔",
                    right = {
                        Insert(bookTypeList)
                    },
                    onTextChange = {
                        HookEntry.optionEntity.shieldOption.bookTypeList = parseKeyWordOption(it)
                    })
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
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

                TextSetting(title = "底部导航栏隐藏控件列表", onClick = {
                    context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.homeOption.bottomNavigationConfigurations)
                }, onLongClick = {
                    HookEntry.optionEntity.viewHideOption.homeOption.bottomNavigationConfigurations =
                        defaultSelectedList
                    context.toast("已恢复默认")
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
                        subTitle = "如若提示没有可用选项,请先打开精选页面滑一滑重启即可\n长按恢复默认,其他操作后生效\nps:不小心长按到了不做任何操作马上右上角重启或者划卡清理即可恢复",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.selectedOption.configurations)
                        },
                        onLongClick = {
                            HookEntry.optionEntity.viewHideOption.selectedOption.configurations =
                                defaultOptionEntity.viewHideOption.selectedOption.configurations
                            context.toast("已恢复默认")
                        }
                    )
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
                        subTitle = "如若提示没有可用选项,请先打开精选页面后重启即可\n长按恢复默认,其他操作后生效\n ps:不小心长按到了不做任何操作马上右上角重启或者划卡清理即可恢复",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.selectedOption.selectedTitleConfigurations)
                        },
                        onLongClick = {
                            HookEntry.optionEntity.viewHideOption.selectedOption.selectedTitleConfigurations =
                                defaultSelectedList
                            context.toast("已恢复默认")
                        })
                }

                SwitchSetting(title = "隐藏部分小红点",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.enableHideRedDot),
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.enableHideRedDot = it
                    })

                val enableHideSearch =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.searchOption.enableHideSearch)

                SwitchSetting(title = "搜索-启用选项屏蔽",
                    checked = enableHideSearch,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.searchOption.enableHideSearch =
                            it
                    })

                if (enableHideSearch.value) {
                    TextSetting(title = "搜索配置列表", onClick = {
                        context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.searchOption.configurations)
                    }, onLongClick = {
                        HookEntry.optionEntity.viewHideOption.searchOption.configurations =
                            defaultSelectedList
                        context.toast("已恢复默认")
                    })
                }

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
                    }, onLongClick = {
                        HookEntry.optionEntity.viewHideOption.findOption.headItem =
                            defaultSelectedList
                        context.toast("已恢复默认")
                    })

                    TextSetting(title = "发现-广告列表", onClick = {
                        context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.findOption.advItem)
                    }, onLongClick = {
                        HookEntry.optionEntity.viewHideOption.findOption.advItem =
                            defaultSelectedList
                        context.toast("已恢复默认")
                    })

                    TextSetting(title = "发现-筛选列表", onClick = {
                        context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.findOption.filterConfItem)
                    },
                        onLongClick = {
                            HookEntry.optionEntity.viewHideOption.findOption.filterConfItem =
                                defaultSelectedList
                            context.toast("已恢复默认")
                        })

                }

                val hideAccount =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccount)

                SwitchSetting(title = "启用我-隐藏控件", checked = hideAccount, onCheckedChange = {
                    HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccount = it
                })

                if (hideAccount.value) {
                    TextSetting(title = "我-隐藏控件列表",
                        subTitle = "如提示没有可用选项，请先打开\"我\"页面,并重启起点\n长按恢复默认,其他操作后生效\n ps:不小心长按到了不做任何操作马上右上角重启或者划卡清理即可恢复",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.accountOption.configurations)
                        },
                        onLongClick = {
                            HookEntry.optionEntity.viewHideOption.accountOption.configurations =
                                defaultSelectedList
                            context.toast("已恢复默认")
                        })

                    TextSetting(title = "我-隐藏控件列表(新)",
                        subTitle = "如提示没有可用选项，请先打开\"我\"页面,并重启起点\n长按恢复默认,其他操作后生效\n ps:不小心长按到了不做任何操作马上右上角重启或者划卡清理即可恢复",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.accountOption.newConfiguration)
                        },
                        onLongClick = {
                            HookEntry.optionEntity.viewHideOption.accountOption.newConfiguration =
                                defaultSelectedList
                            context.toast("已恢复默认")
                        })
                }

                val enableCaptureBookReadPageView =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.readPageOptions.enableCaptureBookReadPageView)

                SwitchSetting(title = "启用阅读页抓取控件",
                    checked = enableCaptureBookReadPageView,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.readPageOptions.enableCaptureBookReadPageView =
                            it
                    })

                if (enableCaptureBookReadPageView.value) {
                    TextSetting(title = "阅读页-隐藏控件列表", onClick = {
                        context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.readPageOptions.configurations)
                    },onLongClick = {
                        HookEntry.optionEntity.viewHideOption.readPageOptions.configurations =
                            defaultSelectedList
                        context.toast("已恢复默认")
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
            modifier = Modifier
                .fillMaxWidth()
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
            context.safeCast<Activity>()?.restartApplication()
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
        TextSetting(title = "免责声明",
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

            })

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
@OptIn(ExperimentalTextApi::class)
@Composable
fun Disclaimers(
    modifier: Modifier = Modifier,
    onAgreeClick: () -> Unit = {},
    onDisagreeClick: () -> Unit = {},
    displayButton: Boolean = true,
) {
    var remainingTime by rememberSaveable { mutableLongStateOf(value = 30L) }
    val context = LocalContext.current
    if (displayButton) {
        val isActive =
            LocalLifecycleOwner.current.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        LaunchedEffect(isActive) {
            while (isActive && remainingTime > 0) {
                delay(1000)
                remainingTime.takeUnless { it == 0L }?.let { remainingTime-- }
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

            pushUrlAnnotation(UrlAnnotation("https://acts.qidian.com/pact/user_pact.html"))
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF0E9FF2),
                    fontWeight = FontWeight.W900
                )
            ) {
                append("起点读书用户服务协议")
            }

            append("以及上述 免责声明，如有异议请勿使用。如果您使用了该Xposed模块，即代表您已经完全接受本免责声明。\n\n")

            append("详细信息请到 ")
            pushUrlAnnotation(UrlAnnotation("https://github.com/xihan123/QDReadHook#%E5%85%8D%E8%B4%A3%E5%A3%B0%E6%98%8E"))
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF0E9FF2),
                    fontWeight = FontWeight.W900
                )
            ) {
                append("Github")
            }
            append(" 或者 ")
            pushUrlAnnotation(UrlAnnotation("https://jihulab.com/xihan123/QDReadHook#%E5%85%8D%E8%B4%A3%E5%A3%B0%E6%98%8E"))
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF0E9FF2),
                    fontWeight = FontWeight.W900
                )
            ) {
                append("极狐GitLab")
            }

            append(" 查看")
        }

        ClickableText(
            text = text,
            onClick = { offset ->
                text.getUrlAnnotations(start = offset, end = offset).map { it.item.url }
                    .forEach { url ->
                        url.takeUnless { url.isBlank() }?.let { context.openUrl(url) }
                    }
            }
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

@Composable
fun ClickableText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (Int) -> Unit
) {
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
    val pressIndicator = Modifier.pointerInput(onClick) {
        detectTapGestures { pos ->
            layoutResult.value?.let { layoutResult ->
                onClick(layoutResult.getOffsetForPosition(pos))
            }
        }
    }

    Text(
        text = text,
        modifier = modifier.then(pressIndicator),
        style = style,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = {
            layoutResult.value = it
            onTextLayout(it)
        }
    )
}
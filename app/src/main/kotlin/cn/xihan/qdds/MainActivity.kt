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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
        val items = listOf(
            MainScreen.MainSetting, MainScreen.PurifySetting, MainScreen.About
        )
        val navController = rememberNavController()
        Scaffold(modifier = M.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "QDReadHook")
            }, modifier = M.fillMaxWidth(), actions = {
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
            if (permission.value) {
                NiaNavigationBar(
                    modifier = M
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .height(56.dp),
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEachIndexed { index, screen ->
                        NiaNavigationRailItem(icon = {
                            when (index) {
                                0 -> Icon(Icons.Filled.Home, null)
                                1 -> Icon(Icons.Filled.Delete, null)
                                2 -> Icon(Icons.Filled.Info, null)
                            }
                        },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                if (navBackStackEntry != null) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            modifier = M.weight(1f)
                        )

                    }
                }
            }
        }) { paddingValues ->

            if (permission.value) {
                NavHost(
                    navController = navController,
                    startDestination = MainScreen.MainSetting.route,
                    modifier = M.padding(paddingValues)
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

                Column(
                    modifier = M.padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        "需要存储以及安装未知应用权限",
                        modifier = M.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = {
                        requestPermission(
                            onGranted = {
                                permission.value = true
                            }, onDenied = {
                                permission.value = false
                                jumpToPermission()
                            }
                        )

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
            modifier = M
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
            modifier = M.scale(0.7f), checked = checked.value, onCheckedChange = {
                checked.value = it
                onCheckedChange(it)
                updateOptionEntity()
            }, enabled = enabled
        )

    }
}

/**
 * 编辑框设置模型
 * @param title 标题
 * @param subTitle 副标题
 * @param text 文本
 * @param showInsert 插入文本
 * @param onTextChange 文本改变事件
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextSetting(
    title: String,
    text: MutableState<String>,
    modifier: Modifier = Modifier,
    subTitle: String = "",
    showInsert: Boolean = false,
    onTextChange: ((String) -> Unit) = {},
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = M
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Visible,
                modifier = M.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start
            )
            if (subTitle.isNotEmpty()) {

                Spacer(modifier = M.height(4.dp))

                Text(
                    text = subTitle,
                    overflow = TextOverflow.Visible,
                    modifier = M.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
            }

            TextField(
                modifier = M.fillMaxWidth(),
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
                        if (text.value.isNotBlank() && showInsert) {
                            IconButton(onClick = {
                                text.value = text.value.plus(";")
                                onTextChange(text.value)
                            }) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                            }
                        }
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
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                )
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
            modifier = M
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Visible,
                modifier = M.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                style = if (bigTitle) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start
            )
            if (subTitle.isNotEmpty()) {

                Spacer(modifier = M.height(4.dp))

                Text(
                    text = subTitle,
                    overflow = TextOverflow.Visible,
                    modifier = M.fillMaxWidth(),
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
                modifier = M
                    .padding(8.dp)
                    .size(20.dp)
            )
        }
    }
}

/**
 * 自定义书架顶部图片配置
 */
@OptIn(ExperimentalMaterial3Api::class)
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

        TextField(modifier = M.fillMaxWidth(), value = border01.value, label = {
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
        }, colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        )
        )

        TextField(modifier = M.fillMaxWidth(), value = font.value, label = {
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
        }, colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        )
        )

        TextField(modifier = M.fillMaxWidth(), value = fontHLight.value, label = {
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
        }, colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        )
        )

        TextField(modifier = M.fillMaxWidth(), value = fontLight.value, label = {
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
        }, colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        )
        )

        TextField(modifier = M.fillMaxWidth(), value = fontOnSurface.value, label = {
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
        }, colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        )
        )

        TextField(modifier = M.fillMaxWidth(), value = surface01.value, label = {
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
        }, colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        )
        )

        TextField(modifier = M.fillMaxWidth(), value = surfaceIcon.value, label = {
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
        }, colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        )
        )

        TextField(modifier = M.fillMaxWidth(), value = headImage.value, label = {
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
        }, colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        )
        )

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
                modifier = M
                    .fillMaxSize(),
                imageModel = { startImageModel.preImageUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )

            Column(
                modifier = M
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

                Spacer(modifier = M.height(10.dp))

                TextButton(
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (isUsed.value) Color.DarkGray else Color(255, 230, 231),
                        contentColor = Color.Red,
                    ),
                    onClick = {
                        isUsed.value = !isUsed.value
                        startImageModel.isUsed = isUsed.value
                        updateOptionEntity()
                    }) {
                    Text(
                        modifier = M.wrapContentSize(),
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
        modifier = M.verticalScroll(rememberScrollState())
    ) {

        Card(
            modifier = M
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            TextSetting(title = "主设置", showRightIcon = false, bigTitle = true)

            Column(modifier = M.padding(4.dp)) {

                SwitchSetting(title = "自动签到",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableAutoSign),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enableAutoSign = it
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

                if (freeAdReward.value) {
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

                SwitchSetting(title = "忽略粉丝值跳转加群限制",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableIgnoreFansValueJumpLimit),
                    onCheckedChange = {
                        HookEntry.optionEntity.mainOption.enableIgnoreFansValueJumpLimit = it
                    })

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
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.accountOption.enableNewAccountLayout),
                        onCheckedChange = {
                            HookEntry.optionEntity.viewHideOption.accountOption.enableNewAccountLayout =
                                it
                        })

                    SwitchSetting(title = "新精选布局",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.mainOption.enableNewStore),
                        onCheckedChange = {
                            HookEntry.optionEntity.mainOption.enableNewStore = it
                        })
                }


            }

        }

        Card(
            modifier = M
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "书架设置", showRightIcon = false, bigTitle = true)

            Column(modifier = M.padding(4.dp)) {

                if (versionCode > 827) {

                    SwitchSetting(title = "新书架布局",
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

                SwitchSetting(
                    title = "启用自定义书架顶部图片",
                    subTitle = "内容可空,不填则为官方默认",
                    checked = enableCustomBookShelfTopImage,
                    onCheckedChange = {
                        HookEntry.optionEntity.bookshelfOption.enableCustomBookShelfTopImage =
                            it
                    })

                if (enableCustomBookShelfTopImage.value) {
                    val enableSameNightAndDay =
                        rememberMutableStateOf(value = HookEntry.optionEntity.bookshelfOption.enableSameNightAndDay)

                    SwitchSetting(title = "启用夜间和日间相同",
                        checked = enableSameNightAndDay,
                        onCheckedChange = {
                            HookEntry.optionEntity.bookshelfOption.enableSameNightAndDay =
                                it
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
            modifier = M
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "阅读页设置", showRightIcon = false, bigTitle = true)

            Column(modifier = M.padding(4.dp)) {
                if (versionCode >= 868) {
                    SwitchSetting(
                        title = "阅读页章评图片长按保存原图",
                        subTitle = "进入图片详情后长按图片",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.readPageOption.enableShowReaderPageChapterSaveRawPicture),
                        onCheckedChange = {
                            HookEntry.optionEntity.readPageOption.enableShowReaderPageChapterSaveRawPicture =
                                it
                        })

                    SwitchSetting(
                        title = "阅读页章评图片保存原图对话框",
                        subTitle = "直接长按图片显示直链地址",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.readPageOption.enableShowReaderPageChapterSavePictureDialog),
                        onCheckedChange = {
                            HookEntry.optionEntity.readPageOption.enableShowReaderPageChapterSavePictureDialog =
                                it
                        })

                    val enableReadTimeDouble =
                        rememberMutableStateOf(value = HookEntry.optionEntity.readPageOption.enableReadTimeDouble)
                    SwitchSetting(
                        title = "阅读时间加倍",
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

                        EditTextSetting(
                            title = "倍速设定",
                            subTitle = "默认为5倍,建议倍速不要太大，开大了到时候号没了与作者无关",
                            text = doubleSpeed,
                            onTextChange = {
                                if (it.isNotBlank() || it.isNumber()) {
                                    doubleSpeed.value = it
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
            modifier = M
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "启动图设置", showRightIcon = false, bigTitle = true)

            Column(modifier = M.padding(4.dp)) {

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

                    if (HookEntry.optionEntity.startImageOption.officialLaunchMapList.isNotEmpty()) {
                        // 展开状态
                        val enableCaptureTheOfficialLaunchMapListExpand =
                            rememberMutableStateOf(value = false)
                        // 展开图标
                        val expandIcon =
                            if (enableCaptureTheOfficialLaunchMapListExpand.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

                        // 展开按钮
                        Row(
                            modifier = M
                                .fillMaxWidth()
                                .clickable {
                                    enableCaptureTheOfficialLaunchMapListExpand.value =
                                        !enableCaptureTheOfficialLaunchMapListExpand.value
                                }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "官方启动图列表: ${HookEntry.optionEntity.startImageOption.officialLaunchMapList.size}张"
                            )

                            Text(
                                text = if (enableCaptureTheOfficialLaunchMapListExpand.value) "收起" else "展开",
                                modifier = M.weight(1f),
                                textAlign = TextAlign.End,
                            )

                            Icon(
                                imageVector = expandIcon,
                                contentDescription = null,
                                modifier = M.size(24.dp)
                            )
                        }

                        if (enableCaptureTheOfficialLaunchMapListExpand.value) {
                            Surface(
                                modifier = M
                                    .animateContentSize()
                                    .padding(1.dp),
                                color = Color.Transparent
                            ) {
                                val list =
                                    HookEntry.optionEntity.startImageOption.officialLaunchMapList
                                val listSize = list.size
                                val height = (listSize / 3 + if (listSize % 3 == 0) 0 else 1) * 250

                                LazyColumn(
                                    modifier = M
                                        .fillMaxWidth()
                                        .height(height.dp)
                                ) {
                                    items(list.windowed(3, 3, true)) { sublist ->
                                        Row(
                                            modifier = M
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            sublist.forEach { item ->
                                                StartImageItem(
                                                    startImageModel = item,
                                                    modifier = M
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


                    EditTextSetting(title = "填入网络图片直链",
                        subTitle = "以\";\"分隔",
                        showInsert = true,
                        text = rememberMutableStateOf(
                            value = HookEntry.optionEntity.startImageOption.customStartImageUrlList.joinToString(
                                ";"
                            )
                        ),
                        onTextChange = {
                            HookEntry.optionEntity.startImageOption.customStartImageUrlList =
                                HookEntry.parseKeyWordOption(it)
                        })


                }
            }
        }

        Card(
            modifier = M
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            TextSetting(title = "闪屏设置", showRightIcon = false, bigTitle = true)

            Column(modifier = M.padding(4.dp)) {

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

                    SwitchSetting(
                        title = "启用自定义闪屏页",
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
        modifier = M.verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = M
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            TextSetting(title = "广告设置", showRightIcon = false, bigTitle = true)

            TextSetting(title = "广告设置列表", modifier = M.padding(4.dp), onClick = {
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
            modifier = M
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            TextSetting(title = "拦截设置", showRightIcon = false, bigTitle = true)

            TextSetting(title = "拦截设置列表", modifier = M.padding(4.dp), onClick = {
                context.multiChoiceSelector(HookEntry.optionEntity.interceptOption.configurations)
            })

        }

        Card(
            modifier = M
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "屏蔽设置", showRightIcon = false, bigTitle = true)

            Column(modifier = M.padding(4.dp)) {

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
                        "精选-漫画-其他"
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


                EditTextSetting(
                    title = "填入需要屏蔽的完整作者名称",
                    text = rememberMutableStateOf(
                        value = HookEntry.optionEntity.shieldOption.authorList.joinToString(
                            ";"
                        ),
                    ),
                    subTitle = "使用 \";\" 分隔",
                    showInsert = true,
                    onTextChange = {
                        HookEntry.optionEntity.shieldOption.authorList =
                            HookEntry.parseKeyWordOption(it)
                    }
                )

                EditTextSetting(
                    title = "填入需要屏蔽的书名关键词",
                    text = rememberMutableStateOf(
                        value = HookEntry.optionEntity.shieldOption.bookNameList.joinToString(
                            ";"
                        )
                    ),
                    subTitle = "注意:单字威力巨大!!!\n使用 \";\" 分隔", onTextChange = {
                        HookEntry.optionEntity.shieldOption.bookNameList =
                            HookEntry.parseKeyWordOption(it)
                    },
                    showInsert = true
                )

                SwitchSetting(title = "启用书类型增强屏蔽",
                    subTitle = "解除下面的限制改为包含关键词执行屏蔽\n例如:关键词为\"仙侠\"时,类型为\"古典仙侠\"的书也会被屏蔽",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.shieldOption.enableBookTypeEnhancedBlocking),
                    onCheckedChange = {
                        HookEntry.optionEntity.shieldOption.enableBookTypeEnhancedBlocking = it
                    })

                EditTextSetting(
                    title = "填入需要屏蔽的书类型",
                    text = rememberMutableStateOf(
                        value = HookEntry.optionEntity.shieldOption.bookTypeList.joinToString(";")
                    ),
                    subTitle = "必须匹配关键词才执行屏蔽\n使用 \";\" 分隔",
                    onTextChange = {
                        HookEntry.optionEntity.shieldOption.bookTypeList =
                            HookEntry.parseKeyWordOption(it)
                    },
                    showInsert = true
                )
            }
        }

        Card(
            modifier = M
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "隐藏控件设置", showRightIcon = false, bigTitle = true)

            Column(modifier = M.padding(4.dp)) {
                SwitchSetting(
                    title = "启用抓取底部导航栏",
                    checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.homeOption.enableCaptureBottomNavigation),
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.homeOption.enableCaptureBottomNavigation =
                            it
                    }
                )

                TextSetting(title = "主页-隐藏控件列表", onClick = {
                    context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.homeOption.configurations)
                })

                val enableSelectedHide =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.selectedOption.enableSelectedHide)

                SwitchSetting(
                    title = "精选-启用选项屏蔽",
                    checked = enableSelectedHide,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.selectedOption.enableSelectedHide = it
                    }
                )

                if (enableSelectedHide.value) {
                    TextSetting(
                        title = "精选-隐藏控件列表",
                        subTitle = "如若提示没有可用选项,请先打开精选页面滑一滑重启即可",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.selectedOption.configurations)
                        }
                    )
                }

                val enableSelectedTitleHide =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.selectedOption.enableSelectedTitleHide)

                SwitchSetting(
                    title = "精选-标题启用选项屏蔽",
                    checked = enableSelectedTitleHide,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.selectedOption.enableSelectedTitleHide =
                            it
                    }
                )

                if (enableSelectedTitleHide.value) {
                    TextSetting(
                        title = "精选-标题隐藏控件列表",
                        subTitle = "如若提示没有可用选项,请先打开精选页面后重启即可",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.selectedOption.selectedTitleConfigurations)
                        }
                    )
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

                SwitchSetting(
                    title = "关闭青少年模式弹框",
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

                SwitchSetting(
                    title = "启用发现-隐藏控件",
                    subTitle = "如提示没有可用选项，请先打开发现页面,并重启起点",
                    checked = hideFind,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.findOption.enableHideFindItem = it
                    })

                if (hideFind.value) {

                    SwitchSetting(
                        title = "隐藏发现-动态",
                        checked = rememberMutableStateOf(
                            value = HookEntry.optionEntity.viewHideOption.findOption.feedsItem
                        ),
                        onCheckedChange = {
                            HookEntry.optionEntity.viewHideOption.findOption.feedsItem = it
                        })

                    SwitchSetting(
                        title = "隐藏发现-广播",
                        checked = rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.findOption.broadCasts),
                        onCheckedChange = {
                            HookEntry.optionEntity.viewHideOption.findOption.broadCasts = it
                        })

                    TextSetting(
                        title = "发现-头部列表",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.findOption.headItem)
                        })

                    TextSetting(
                        title = "发现-广告列表",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.findOption.advItem)
                        })

                    TextSetting(
                        title = "发现-筛选列表",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.findOption.filterConfItem)
                        })

                }

                val hideAccount =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccount)

                SwitchSetting(
                    title = "启用我-隐藏控件",
                    checked = hideAccount,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccount = it
                    })

                if (hideAccount.value) {
                    TextSetting(
                        title = "我-隐藏控件列表",
                        subTitle = "如提示没有可用选项，请先打开\"我\"页面,并重启起点",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.accountOption.configurations)
                        })

                    TextSetting(
                        title = "我-隐藏控件列表(新)",
                        subTitle = "如提示没有可用选项，请先打开\"我\"页面,并重启起点",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.accountOption.newConfiguration)
                        })
                }

                val hideDetail =
                    rememberMutableStateOf(value = HookEntry.optionEntity.viewHideOption.bookDetailOptions.enableHideBookDetail)

                SwitchSetting(
                    title = "启用书籍详情-隐藏控件",
                    checked = hideDetail,
                    onCheckedChange = {
                        HookEntry.optionEntity.viewHideOption.bookDetailOptions.enableHideBookDetail =
                            it
                    })

                if (hideDetail.value) {
                    TextSetting(
                        title = "书籍详情-隐藏控件列表",
                        onClick = {
                            context.multiChoiceSelector(HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations)
                        })
                }


            }
        }

        Card(
            modifier = M
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            TextSetting(title = "替换规则设置", showRightIcon = false, bigTitle = true)

            Column(modifier = M.padding(4.dp)) {

                val enableReplace =
                    rememberMutableStateOf(value = HookEntry.optionEntity.replaceRuleOption.enableReplace)
                SwitchSetting(
                    title = "启用替换",
                    checked = enableReplace,
                    onCheckedChange = {
                        HookEntry.optionEntity.replaceRuleOption.enableReplace = it
                    })

                if (enableReplace.value) {
                    TextSetting(
                        title = "替换规则列表",
                        onClick = {
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
@Composable
fun AboutScreen(
    versionCode: Int,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
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
            val activity = context as? Activity
            activity?.restartApplication()
        })

        TextSetting(title = "打赏", subTitle = "", onClick = {
            context.openUrl("https://github.com/xihan123/QDReadHook#%E6%89%93%E8%B5%8F")
        })

        TextSetting(title = "QD模块交流群", subTitle = "727983520", onClick = {
            context.joinQQGroup("JdqL9prgQ3epIUed3weaEkJwtNgNQaWa")
        })

        TextSetting(
            title = "QD模块赞助群",
            subTitle = "575801108\n赞助后加群主好友发记录",
            onClick = {
                context.alertDialog {
                    title = "作者的话"
                    message =
                        "因为开发QD模块占用日常时间过长，无法保证能够及时适配，希望大家能够理解。\n" +
                                "QD模块的新版本更新将会在赞助群抢先体验。\n" +
                                "开发不易，有能力的人可以进行赞助，交流群的相册有付款码。\n" +
                                "赞助多少无要求，各凭心意。赞助群群费暂定十元，后续群费会随时向上调整，就不再另行通知。\n" +
                                "大家量力而行，非常感谢各位的支持。愿大家都有一个良好的追书体验~"

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

        TextSetting(
            title = "版本号",
            subTitle = BuildConfig.VERSION_NAME,
            showRightIcon = false,
            onClick = {
                context.checkModuleUpdate()
            })

    }

}

private sealed class MainScreen(val route: String, val title: String) {
    /**
     * 主设置
     */
    object MainSetting : MainScreen("main_setting", "主设置")

    /**
     * 净化设置
     */
    object PurifySetting : MainScreen("purify_setting", "净化")

    /**
     * 关于
     */
    object About : MainScreen("about", "关于")


}
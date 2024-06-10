package cn.xihan.qdds

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.forEach
import cn.xihan.qdds.Option.parseNeedShieldList
import cn.xihan.qdds.Option.splashPath
import cn.xihan.qdds.Option.updateOptionEntity
import com.alibaba.fastjson2.toJSONString
import com.highcapable.yukihookapi.hook.factory.MembersType
import com.highcapable.yukihookapi.hook.factory.constructor
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.param.HookParam
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.ListClass
import com.highcapable.yukihookapi.hook.type.java.UnitType
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import de.robv.android.xposed.XposedHelpers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FilenameFilter
import java.io.Serializable
import java.util.Locale
import java.util.Random
import kotlin.system.exitProcess

/**
 * 记住可变状态
 * @param [value] 价值
 * @return [MutableState<T>]
 * @suppress Generate Documentation
 */
@Composable
fun <T> rememberMutableStateOf(value: T): MutableState<T> = remember { mutableStateOf(value) }

/**
 * 记住可保存可变状态
 * @param [value] 价值
 * @return [MutableState<T>]
 * @suppress Generate Documentation
 */
@Composable
fun <T> rememberSavableMutableStateOf(value: T): MutableState<T> =
    rememberSaveable { mutableStateOf(value) }

/**
 * 记住可变交互源
 * @since 7.9.334-1196
 * @suppress Generate Documentation
 */
@Composable
fun rememberMutableInteractionSource() = remember { MutableInteractionSource() }

/**
 * 获取方位无线电
 * @since 7.9.334-1196
 * @return [Float]
 * @suppress Generate Documentation
 */
@Composable
fun getAspectRadio(): Float {
    val configuration = LocalConfiguration.current
    return remember(configuration) {
        configuration.screenHeightDp.toFloat() / configuration.screenWidthDp.toFloat()
    }
}

/**
 * 是平板电脑
 * @since 7.9.334-1196
 * @return [Boolean]
 * @suppress Generate Documentation
 */
@Composable
fun isTablet(): Boolean {
    val configuration = LocalConfiguration.current
    return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        configuration.screenHeightDp > 600
    } else {
        configuration.screenWidthDp > 600
    }
}

/**
 * 打印错误日志
 * @suppress Generate Documentation
 */
fun String.loge() {
    if (BuildConfig.DEBUG) {
        YLog.error(msg = this, tag = YLog.Configs.tag)
    }
}

/**
 * 打印错误日志
 * @suppress Generate Documentation
 */
fun Throwable.loge() {
    if (BuildConfig.DEBUG) {
        YLog.error(msg = this.message ?: "未知错误", tag = YLog.Configs.tag)
    }
}

/**
 * 安全转换
 * @see [T?]
 * @return [T?]
 * @suppress Generate Documentation
 */
inline fun <reified T> Any?.safeCast(): T? = this as? T

/**
 * 按 ID 查找视图
 * @param [id] 编号
 * @see [T?]
 * @return [T?]
 * @suppress Generate Documentation
 */
@Throws(NoSuchFieldException::class, IllegalAccessException::class)
inline fun <reified T : View> Any.findViewById(id: Int): T? {
    return XposedHelpers.callMethod(this, "findViewById", id).safeCast<T>()
}

/**
 * 获取视图
 * @param [name] 名称
 * @param [isSuperClass] 是超一流
 * @return [T?]
 * @suppress Generate Documentation
 */
@Throws(NoSuchFieldException::class, IllegalAccessException::class)
inline fun <reified T : View> Any.getView(name: String, isSuperClass: Boolean = false): T? =
    getParam<T>(name, isSuperClass)

/**
 * 获取视图
 * @param [pairs] 对
 * @return [List<View>]
 * @suppress Generate Documentation
 */
fun Any.getViews(vararg pairs: Pair<String, Boolean> = arrayOf("name" to false)): List<View> =
    if (pairs.isEmpty()) emptyList()
    else pairs.mapNotNull { (name, isSuperClass) -> getParam<View>(name, isSuperClass) }

/**
 * 获取视图
 * @param [isSuperClass] 是超一流
 * @suppress Generate Documentation
 */
@Throws(NoSuchFieldException::class, IllegalAccessException::class)
inline fun <reified T : View> Any.getViews(isSuperClass: Boolean = false) =
    getParamList<T>(isSuperClass)

/**
 * 获取视图
 * @param [type] 类型
 * @param [isSuperClass] 是超一流
 * @return [ArrayList<Any>]
 * @suppress Generate Documentation
 */
@Throws(NoSuchFieldException::class, IllegalAccessException::class)
fun Any.getViews(type: Class<*>, isSuperClass: Boolean = false): ArrayList<Any> {
    val results = arrayListOf<Any>()
    val classes =
        if (isSuperClass) generateSequence(javaClass) { it.superclass }.toList() else listOf(
            javaClass
        )
    for (clazz in classes) {
        clazz.declaredFields.filter { type.isAssignableFrom(it.type) }.forEach { field ->
            field.isAccessible = true
            val value = field.get(this)
            if (type.isInstance(value)) {
                results += value as Any
            }
        }
    }
    return results
}

/**
 * 获取参数
 * @param [name] 名称
 * @param [isSuperClass] 是超一流
 * @return [T?]
 * @suppress Generate Documentation
 */
@Throws(NoSuchFieldException::class, IllegalAccessException::class)
inline fun <reified T> Any.getParam(name: String, isSuperClass: Boolean = false): T? {
    val queue = ArrayDeque<Class<*>>()
    var clazz: Class<*>? = if (isSuperClass) javaClass.superclass else javaClass
    while (clazz != null) {
        queue.add(clazz)
        clazz = clazz.superclass
    }
    while (queue.isNotEmpty()) {
        val currentClass = queue.removeFirst()
        try {
            val field = currentClass.getDeclaredField(name).apply { isAccessible = true }
            return field[this].safeCast<T>()
        } catch (_: NoSuchFieldException) {
            // Ignore and continue searching
        }
    }
    return null
}

/**
 * 获取参数列表
 * @param [isSuperClass] 是超一流
 * @return [ArrayList<T>]
 * @suppress Generate Documentation
 */
@Throws(NoSuchFieldException::class, IllegalAccessException::class)
inline fun <reified T> Any.getParamList(isSuperClass: Boolean = false): ArrayList<T> {
    val results = ArrayList<T>()
    val classes =
        if (isSuperClass) generateSequence(javaClass) { it.superclass }.toList() else listOf(
            javaClass
        )
    val type = T::class.java
    for (clazz in classes) {
        clazz.declaredFields.filter { type.isAssignableFrom(it.type) }.forEach { field ->
            field.isAccessible = true
            val value = field.get(this)
            if (type.isInstance(value)) {
                results += value as T
            }
        }
    }
    return results
}

/**
 * 设置可见性（如果不相等）
 * @param [status] 地位
 * @suppress Generate Documentation
 */
fun View.setVisibilityIfNotEqual(status: Int = View.GONE) {
    this.takeIf { it.visibility != status }?.also { visibility = status }
}

/**
 * 为子控件设置可见性
 * @param [visibility] 能见度
 * @suppress Generate Documentation
 */
fun View.setVisibilityWithChildren(visibility: Int = View.GONE) {
    setVisibilityIfNotEqual(visibility)
    if (this is ViewGroup) {
        this.forEach { child ->
            child.setVisibilityWithChildren(visibility)
        }
    }
}

/**
 * 设置参数
 * @param [name] 名字
 * @param [value] 值
 * @suppress Generate Documentation
 */
fun Any.setParam(name: String, value: Any?) {
    when (value) {
        is Int -> XposedHelpers.setIntField(this, name, value)
        is Boolean -> XposedHelpers.setBooleanField(this, name, value)
        is String -> XposedHelpers.setObjectField(this, name, value)
        is Long -> XposedHelpers.setLongField(this, name, value)
        is Float -> XposedHelpers.setFloatField(this, name, value)
        is Double -> XposedHelpers.setDoubleField(this, name, value)
        is Short -> XposedHelpers.setShortField(this, name, value)
        is Byte -> XposedHelpers.setByteField(this, name, value)
        is Char -> XposedHelpers.setCharField(this, name, value)
        else -> XposedHelpers.setObjectField(this, name, value)
    }
}

/**
 * 设置参数
 * @param [params] 参数
 * @suppress Generate Documentation
 */
fun Any.setParams(vararg params: Pair<String, Any?>) {
    params.forEach {
        setParam(it.first, it.second)
    }
}

/**
 * 获取系统上下文
 * @see [Context]
 * @return [Context]
 * @suppress Generate Documentation
 */
fun getSystemContext(): Context {
    val activityThreadClass = XposedHelpers.findClass("android.app.ActivityThread", null)
    val activityThread =
        XposedHelpers.callStaticMethod(activityThreadClass, "currentActivityThread")
    val context = XposedHelpers.callMethod(activityThread, "getSystemContext").safeCast<Context>()
    return context ?: throw Error("Failed to get system context.")
}

/**
 * 重新启动应用程序
 * @suppress Generate Documentation
 */
fun Activity.restartApplication() = packageManager.getLaunchIntentForPackage(packageName)?.let {
    finishAffinity()
    startActivity(intent)
    exitProcess(0)
}

/**
 * 获取版本代码
 * @param [packageName] 包名
 * @see [Int]
 * @return [Int]
 * @suppress Generate Documentation
 */
fun Context.getVersionCode(packageName: String): Int = try {
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            packageManager.getPackageInfo(
                packageName, PackageManager.GET_SIGNING_CERTIFICATES
            ).longVersionCode.toInt()
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
            packageManager.getPackageInfo(packageName, 0).longVersionCode.toInt()
        }

        else -> {
            packageManager.getPackageInfo(packageName, 0).versionCode
        }
    }
} catch (e: Throwable) {
    e.loge()
    0
}

/**
 * 打印调用堆栈
 * @suppress Generate Documentation
 */
fun String.printCallStack() {
    val stringBuilder = StringBuilder()
    stringBuilder.appendLine("----className: $this ----")
    stringBuilder.appendLine("Dump Stack: ---------------start----------------")
    val ex = Throwable()
    val stackElements = ex.stackTrace
    stackElements.forEachIndexed { index, stackTraceElement ->
        stringBuilder.appendLine("Dump Stack: $index: $stackTraceElement")
    }
    stringBuilder.appendLine("Dump Stack: ---------------end----------------")
    stringBuilder.toString().loge()
}

/**
 *  执行并捕获异常
 * @param [block] 块
 * @suppress Generate Documentation
 */
inline fun runAndCatch(block: () -> Unit) = runCatching {
    block()
}.onFailure {
    it.loge()
}

/**
 * 打印调用堆栈
 * @suppress Generate Documentation
 */
fun Any.printCallStack() {
    this.javaClass.name.printCallStack()
}

/**
 * 复制到剪贴板
 * @param [text] 发短信
 * @suppress Generate Documentation
 */
fun Context.copyToClipboard(text: String) {
    val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text))
}

/**
 * 加入QQ群
 * @param [key] 钥匙
 * @see [Boolean]
 * @return [Boolean]
 * @suppress Generate Documentation
 */
fun Context.joinQQGroup(key: String): Boolean {
    val uri = Uri.Builder().scheme("mqqopensdkapi").authority("bizAgent").appendPath("qm")
        .appendPath("qr").appendQueryParameter(
            "url", "http://qm.qq.com/cgi-bin/qm/qr?from=app&p=android&jump_from=webapi&k=$key"
        ).build()
    val intent = Intent().apply {
        data = uri
    }
    return try {
        startActivity(intent)
        true
    } catch (e: Exception) {
        // 未安装手Q或安装的版本不支持
        Toast.makeText(this, "未安装手Q或安装的版本不支持", Toast.LENGTH_SHORT).show()
        false
    }
}

/**
 * 隐藏应用图标
 * @suppress Generate Documentation
 */
fun Context.hideAppIcon() {
    val componentName = ComponentName(this, MainActivity::class.java.name)
    if (packageManager.getComponentEnabledSetting(componentName) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}

/**
 * “显示应用”图标
 * @suppress Generate Documentation
 */
fun Context.showAppIcon() {
    val componentName = ComponentName(this, MainActivity::class.java.name)
    if (packageManager.getComponentEnabledSetting(componentName) != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}

/**
 * 请求权限
 * @suppress Generate Documentation
 */
fun Activity.requestPermissionDialog(
    onGranted: () -> Unit = { restartApplication() },
    onDenied: () -> Unit = { jumpToPermission() },
) {
    val permission = XXPermissions.isGranted(
        this, Permission.REQUEST_INSTALL_PACKAGES, Permission.MANAGE_EXTERNAL_STORAGE
    )
    if (permission) {
        return
    }
    alertDialog {
        title = "温馨提示"
        message =
            "请授予以下权限,否则无法正常使用\n安装未知应用权限: Android 14 强制需要\n存储权限:用来管理位于外部存储的配置文件"
        positiveButton("授予") {
            XXPermissions.with(this@requestPermissionDialog).permission(
                Permission.MANAGE_EXTERNAL_STORAGE, Permission.REQUEST_INSTALL_PACKAGES
            ).request { _, allGranted ->
                if (allGranted) {
                    onGranted()
                } else {
                    onDenied()
                }
            }
        }
        negativeButton("拒绝并退出") {
            onDenied()
        }
        build()
        show()
    }
}

/**
 * 跳转到权限
 * @suppress Generate Documentation
 */
fun Context.jumpToPermission() {
    if (this.applicationInfo.targetSdkVersion > 30) {
        XXPermissions.startPermissionActivity(
            this,
            Permission.REQUEST_INSTALL_PACKAGES,
            Permission.MANAGE_EXTERNAL_STORAGE
        )
    } else {
        XXPermissions.startPermissionActivity(this, Permission.Group.STORAGE)
    }
}

/**
 * 打开url
 * @param [url] url
 * @suppress Generate Documentation
 */
fun Context.openUrl(url: String) = runCatching {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}

/**
 * 吐司
 * @param [msg] 消息
 * @suppress Generate Documentation
 */
fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

/**
 * 打印不支持版本
 * @param [versionCode] 版本代码
 * @suppress Generate Documentation
 */
fun String.printlnNotSupportVersion(versionCode: Int = 0) =
    YLog.error(msg = "${this}不支持的版本号为: $versionCode", tag = YLog.Configs.tag)

/**
 * 合并
 * @param [newConfigurations] 新配置
 * @see [List<SelectedModel>]
 * @return [List<SelectedModel>]
 * @suppress Generate Documentation
 */
infix fun List<SelectedModel>.merge(newConfigurations: List<SelectedModel>): List<SelectedModel> {
    val result = this.toMutableList()
    result -= this.filter { it.title !in newConfigurations.map { it1 -> it1.title } }.toSet()
    result += newConfigurations.filter { it.title !in this.map { it1 -> it1.title } }
    return result.distinct().sortedBy { it.title }
}

/**
 * 按标题选择
 * @param [title] 标题
 * @see [Boolean]
 * @return [Boolean]
 * @suppress Generate Documentation
 */
fun List<SelectedModel>.isSelectedByTitle(title: String): Boolean =
    firstOrNull { it.title == title }?.selected ?: false

/**
 * 解析关键字选项
 * @param [it] 它
 * @see [MutableSet<String>]
 * @return [MutableSet<String>]
 * @suppress Generate Documentation
 */
fun parseKeyWordOption(it: String = ""): MutableSet<String> =
    it.split(";").filter(String::isNotBlank).map(String::trim).toMutableSet()

/**
 * 寻找或添加
 * @param [title] 标题
 * @param [iterator] 迭代器
 * @suppress Generate Documentation
 */
fun MutableList<SelectedModel>.findOrPlus(
    title: String,
    iterator: MutableIterator<Any?>,
) = runAndCatch {
    firstOrNull { it.title == title }?.let { config ->
        if (config.selected) {
            iterator.remove()
        }
    } ?: SelectedModel(title = title).also { plusAssign(it) }
    updateOptionEntity()
}

/**
 * 寻找或添加
 * @param [title] 标题
 * @param [actionUnit] 行动单位
 * @suppress Generate Documentation
 */
fun MutableList<SelectedModel>.findOrPlus(
    title: String,
    actionUnit: () -> Unit = {},
) = runAndCatch {
    firstOrNull { it.title == title }?.let { config ->
        if (config.selected) {
            actionUnit()
        }
    } ?: SelectedModel(title = title).also { plusAssign(it) }
    updateOptionEntity()
}

/**
 * 多选选择器
 * @param [list] 列表
 * @suppress Generate Documentation
 */
fun Context.multiChoiceSelector(
    list: List<SelectedModel>,
) = runAndCatch {
    if (list.isEmpty()) {
        toast("没有可用的选项")
        return@runAndCatch
    }

    val checkedItems = list.map { it.selected }.toBooleanArray()
    multiChoiceSelector(
        list.map { it.title }, checkedItems, "选项列表"
    ) { _, i, isChecked ->
        checkedItems[i] = isChecked
    }.doOnDismiss {
        checkedItems.forEachIndexed { index, b ->
            list[index].selected = b
        }
        updateOptionEntity()
    }
}

/**
 * 获取应用程序apk路径
 * @param [packageName] 程序包名称
 * @return [String]
 * @suppress Generate Documentation
 */
fun Context.getApplicationApkPath(packageName: String): String {
    val pm = this.packageManager
    val apkPath = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        pm.getApplicationInfo(
            packageName, PackageManager.GET_SIGNING_CERTIFICATES
        ).publicSourceDir
    } else {
        pm.getApplicationInfo(packageName, 0).publicSourceDir
    }
    return apkPath ?: throw Error("Failed to get the APK path of $packageName")
}

/**
 * 查找方法和打印
 * @param [className] 类名
 * @param [printCallStack] 打印调用堆栈
 * @param [printType] 打印类型
 * @suppress Generate Documentation
 */
fun PackageParam.findMethodAndPrint(
    className: String, printCallStack: Boolean = false, printType: MembersType = MembersType.METHOD
) {
    when (printType) {
        MembersType.METHOD -> {
            className.toClass().method().hookAll().after {
                print(printCallStack)
            }
        }

        MembersType.CONSTRUCTOR -> {
            className.toClass().constructor().hookAll().after {
                print(printCallStack)
            }
        }

        else -> {
            with(className.toClass()) {
                (method().giveAll() + constructor().giveAll()).hookAll {
                    after {
                        print(printCallStack)
                    }
                }
            }
        }
    }
}

private fun HookParam.print(
    printCallStack: Boolean = false
) {
    val stringBuilder = StringBuilder().apply {
        append("---类名: ${instanceClass?.name} 方法名: ${method.name}\n")
        if (args.isEmpty()) {
            append("无参数\n")
        } else {
            args.forEachIndexed { index, any ->
                append("参数${any?.javaClass?.simpleName} ${index}: ${any.mToString()}\n")
            }
        }
        result?.let { append("---返回值: ${it.mToString()}") }
    }
    stringBuilder.toString().loge()
    if (printCallStack) {
        instance.printCallStack()
    }
}

/**
 * 打印参数
 * @return [String]
 * @suppress Generate Documentation
 */
fun Array<Any?>.printArgs(): String {
    val stringBuilder = StringBuilder()
    this.forEachIndexed { index, any ->
        stringBuilder.append("args[$index]: ${any.mToString()}\n")
    }
    return stringBuilder.toString()
}

/**
 * m到字符串
 * @return [String]
 * @suppress Generate Documentation
 */
fun Any?.mToString(): String = when (this) {
    is String, is Int, is Long, is Float, is Double, is Boolean -> "$this"
    is Array<*> -> this.joinToString(",")
    is ByteArray -> this.toHexString()//this.toString(Charsets.UTF_8)
    is Serializable, is Parcelable -> this.toJSONString()
    else -> {
        val list = listOf(
            "Entity",
            "Model",
            "Bean",
            "Result",
        )
        if (list.any { this?.javaClass?.name?.contains(it) == true }) this.toJSONString()
        else if (this?.javaClass?.name?.contains("QDHttpResp") == true) this.getParamList<String>()
            .toString()
        else this?.toString() ?: "null"
    }
}

/**
 * 字节数组使用hex编码
 */
fun ByteArray.toHexString(): String {
    val sb = StringBuilder()
    forEach {
        sb.append(String.format("%02x", it))
    }
    return sb.toString()
}

/**
 * 按类型查找视图
 * @param [viewClass] 视图类
 * @return [ArrayList<View>]
 * @suppress Generate Documentation
 */
fun ViewGroup.findViewsByType(viewClass: Class<*>): ArrayList<View> {
    val result = arrayListOf<View>()
    val queue = ArrayDeque<View>()
    queue.add(this)

    while (queue.isNotEmpty()) {
        val view = queue.removeFirst()
        if (viewClass.isInstance(view)) {
            result.add(view)
        }

        if (view is ViewGroup) {
            for (i in 0..<view.childCount) {
                queue.add(view.getChildAt(i))
            }
        }
    }

    return result
}

/**
 * 随机时间
 * @return [Long]
 * @suppress Generate Documentation
 */
fun randomTime(): Long = Random().nextInt(500) + 50L

/**
 * 后随机延迟
 * @param [delayMillis] 延迟毫秒
 * @param [action] 行动
 * @suppress Generate Documentation
 */
fun View.postRandomDelay(delayMillis: Long = randomTime(), action: View.() -> Unit) =
    postDelayed({ this.action() }, delayMillis)

/**
 * 随机延迟执行点击
 * @suppress Generate Documentation
 */
fun View.randomDelayPerformClick() = postRandomDelay { performClick() }

/**
 * 将一个字符串数组转换为一个由字符串和布尔值组成的对数组
 * @param [default] 违约
 * @return [Array<Pair<String, Boolean>>]
 * @suppress Generate Documentation
 */
fun Array<String>.toPairs(default: Boolean = false): Array<Pair<String, Boolean>> =
    this.map { it to default }.toTypedArray()

/**
 * 隐藏视图
 * @suppress Generate Documentation
 */
fun List<View>.hideViews() = forEach { it.setVisibilityIfNotEqual() }

/**
 * 执行点击
 *  @suppress Generate Documentation
 */
fun List<View>.randomDelayPerformClick() = forEach { it.randomDelayPerformClick() }

/**
 * 获取带回退字符串
 * @param [key] 钥匙
 * @return [String?]
 * @suppress Generate Documentation
 */
fun com.alibaba.fastjson2.JSONObject.getStringWithFallback(key: String): String? =
    this.getString(key) ?: this.getString(key.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    })

/**
 * 使用回退获取jsonarray
 * @param [key] 钥匙
 * @return [com.alibaba.fastjson2.JSONArray?]
 * @suppress Generate Documentation
 */
fun com.alibaba.fastjson2.JSONObject.getJSONArrayWithFallback(key: String): com.alibaba.fastjson2.JSONArray? =
    this.getJSONArray(key)
        ?: this.getJSONArray(key.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })

/**
 * 获取名称
 * @suppress Generate Documentation
 */
fun View.getName() = toString().substringAfter("/").replace("}", "")

/**
 * 随机位图
 * @since 7.9.334-1196
 * @return [Bitmap?]
 * @suppress Generate Documentation
 */
fun randomBitmap(): Bitmap? {
    val filter = FilenameFilter { _, name ->
        name.endsWith(".jpg", true) || name.endsWith(".png", true)
    }
    val files = File(splashPath).apply { if (!exists()) mkdirs() }
    val list = files.listFiles(filter) ?: return null
    val index = (list.indices).random()
    return try {
        BitmapFactory.decodeFile(list[index].absolutePath)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * # 移动到私有存储
 * * 将位于 "/sdcard/Download/QDReader/Font" 的字体文件替换到 "/data/user/0/com.qidian.QDReader/files/fulltype_fonts/"和"/data/user/0/com.qidian.QDReader/files/truetype_fonts/"
 * * 启用后阅读页设置字体选择 汉仪楷体
 * * ps: 无需改名并且仅一个生效
 * @since 7.9.334-1196
 * @param [context] 上下文
 */
fun moveToPrivateStorage(context: Context) = runBlocking(Dispatchers.IO) {
    runAndCatch {
        val fontFile = File(Option.fontPath).apply {
            parentFile?.mkdirs()
            if (!exists()) {
                mkdir()
            }
        }
        val internalFontFile =
            File("${context.filesDir}/fulltype_fonts", "HYKaiT18030F.ttf").apply {
                parentFile?.mkdirs()
                if (!exists()) {
                    createNewFile()
                }
            }
        val internalFontFile2 =
            File("${context.filesDir}/truetype_fonts", "HYKaiT18030F.ttf_new.ttf").apply {
                parentFile?.mkdirs()
                if (!exists()) {
                    createNewFile()
                }
            }
        fontFile.listFiles()?.takeIf { it.isNotEmpty() }?.let {
            it.firstOrNull()?.apply {
                copyTo(internalFontFile, overwrite = true)
                copyTo(internalFontFile2, overwrite = true)
            }
        }
    }
}

/**
 * 数据处理
 * @since 7.9.334-1196
 * @param [value] 价值
 * @return [Int]
 * @suppress Generate Documentation
 */
fun Context.dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

/**
 * x
 * @since 7.9.334-1196
 * @param [other] 另外
 * @return [ViewGroup.LayoutParams]
 * @suppress Generate Documentation
 */
infix fun Int.x(other: Int): ViewGroup.LayoutParams = ViewGroup.LayoutParams(this, other)

/**
 * return false
 * @since 7.9.334-1196
 * @param [methodData] 方法数据
 * @suppress Generate Documentation
 */
fun PackageParam.returnFalse(className: String, methodName: String, paramCount: Int = 0) {
    className.toClass().method {
        name = methodName
        if (paramCount == 0) {
            emptyParam()
        } else {
            paramCount(paramCount)
        }
        returnType = BooleanType
    }.hook().replaceToFalse()
}

/**
 * 拦截
 * @since 7.9.334-1196
 * @param [className] 类名
 * @param [methodName] 方法名称
 * @param [paramCount] 参数计数
 * @suppress Generate Documentation
 */
fun PackageParam.intercept(
    className: String, methodName: String, paramCount: Int = 0
) {
    className.toClass().method {
        name = methodName
        if (paramCount == 0) {
            emptyParam()
        } else {
            paramCount(paramCount)
        }
        returnType = UnitType
    }.hook().intercept()
}

fun PackageParam.shieldResult(
    className: String, methodName: String, paramCount: Int = 0, returnType: Any = ListClass
) {
    className.toClass().method {
        name = methodName
        if (paramCount == 0) {
            emptyParam()
        } else {
            paramCount(paramCount)
        }
        this.returnType = returnType
    }.hook().after {
        result.safeCast<ArrayList<*>>()?.let {
            result = parseNeedShieldList(it)
        }
    }
}

fun PackageParam.shieldUnit(
    className: String, methodName: String, paramCount: Int = 0, index: Int = 0
) {
    className.toClass().method {
        name = methodName
        if (paramCount == 0) {
            emptyParam()
        } else {
            paramCount(paramCount)
        }
        returnType = UnitType
    }.hook().before {
        args[index].safeCast<MutableList<*>>()?.let {
            args(index).set(parseNeedShieldList(it))
        }
    }
}
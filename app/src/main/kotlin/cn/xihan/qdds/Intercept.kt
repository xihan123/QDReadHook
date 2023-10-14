package cn.xihan.qdds

import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType
import org.luckypray.dexkit.DexKitBridge
import java.io.File
import java.lang.reflect.Modifier

/**
 * 拦截选项
 * @since 7.9.306-1030
 * @param [versionCode] 版本代码
 * @param [configurations] 配置
 */
fun PackageParam.interceptOption(
    versionCode: Int,
    configurations: List<SelectedModel>,
) {
    val interceptList = mutableListOf<String>()
    configurations.filter { it.selected }.takeIf { it.isNotEmpty() }?.forEach { selected ->
        when (selected.title) {
            "检测更新" -> interceptCheckUpdate(versionCode)
            "隐私政策更新弹框" -> interceptPrivacyPolicy(versionCode)
            "同意隐私政策弹框" -> interceptAgreePrivacyPolicy(versionCode)
            "WebSocket" -> interceptWebSocket(versionCode)
            "青少年模式请求" -> interceptQSNModeRequest(versionCode)
            "青少年模式弹框" -> interceptQSNYDialog(versionCode)
            "阅读页水印" -> interceptReaderBookPageWaterMark(versionCode)
            "发帖图片水印" -> interceptPostImageWatermark(versionCode)
            "自动跳转精选" -> interceptAutoJumpSelected(versionCode)
            "首次安装分析" -> interceptFirstInstallAnalytics(versionCode)
            else -> interceptList += selected.title
        }
    }
    if (interceptList.isNotEmpty()){
        interceptAsyncInitTask(versionCode, interceptList)
    }
}

/**
 * 拦截检测更新
 * @since 7.9.306-1030 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.interceptCheckUpdate(versionCode: Int) {
    when (versionCode) {
        in 1030..1099 -> {
            intercept("com.qidian.QDReader.ui.activity.MainGroupActivity", "checkUpdate")

            DexKitBridge.create(appInfo.sourceDir)?.use { bridge ->
                bridge.findClass {
                    excludePackages = listOf("com")
                    matcher {
                        methods {
                            add {
                                modifiers = Modifier.PUBLIC
                                returnType = "void"
                                paramCount = 5
                            }
                        }
                        usingStrings = listOf("SettingUpdateVersionNotifyTime", "0")
                    }
                }.firstNotNullOfOrNull { classData ->
                    classData.getMethods().findMethod {
                        matcher {
                            returnType = "void"
                            paramTypes = listOf(
                                "android.app.Activity",
                                null,
                                "android.os.Handler",
                                "boolean",
                                "boolean"
                            )
                        }
                    }.firstNotNullOfOrNull { methodData ->
                        intercept(
                            methodData.className,
                            methodData.methodName,
                            methodData.paramTypeNames.size
                        )
                    }
                }
            }
        }

        else -> "拦截检测更新".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 拦截隐私策略
 * @since 7.9.306-1030 ~ 1099
 * @param [version] 版本
 */
fun PackageParam.interceptPrivacyPolicy(version: Int) {
    when (version) {
        in 1030..1099 -> {
            intercept("com.qidian.QDReader.ui.activity.MainGroupActivity", "checkPrivacyVersion")
        }

        else -> "拦截隐私政策更新弹框".printlnNotSupportVersion(version)
    }
}

/**
 * 拦截同意隐私政策弹框
 * @since 7.9.306-1030 ~ 1099
 * @param [version] 版本
 */
fun PackageParam.interceptAgreePrivacyPolicy(version: Int) {
    when (version) {
        in 1030..1099 -> {
            DexKitBridge.create(appInfo.sourceDir)?.use { bridge ->
                bridge.findClass {
                    searchPackages = listOf("com.qidian.QDReader.util")
                    matcher {
                        usingStrings = listOf(
                            "first_install_app_for_privacy",
                            "https://acts.qidian.com/pact/qd_pact.html"
                        )
                    }
                }.firstNotNullOfOrNull { classData ->
                    classData.getMethods().findMethod {
                        matcher {
                            modifiers = Modifier.PUBLIC
                            returnType = "void"
                            paramTypes = listOf(
                                "android.app.Activity", "boolean", "java.lang.String", null
                            )
                        }
                    }.firstNotNullOfOrNull { methodData ->
                        intercept(
                            methodData.className,
                            methodData.methodName,
                            methodData.paramTypeNames.size
                        )
                    }
                }
            }
        }

        else -> "拦截同意隐私政策弹框".printlnNotSupportVersion(version)
    }
}

/**
 * 拦截WebSocket
 * @since 7.9.306-1030
 * @param [version] 版本
 */
fun PackageParam.interceptWebSocket(version: Int) {
    when (version) {
        in 1030..1099 -> {
            DexKitBridge.create(appInfo.sourceDir)?.use { bridge ->
                bridge.findClass {
                    searchPackages = listOf("com.qidian.QDReader.component.msg")
                    matcher {
                        usingStrings = listOf("handleOpen WebSocket isOpen")
                    }
                }.firstNotNullOfOrNull { classData ->
                    classData.getMethods().findMethod {
                        matcher {
                            modifiers = Modifier.PRIVATE
                            returnType = "void"
                            usingStrings = listOf("handleOpen WebSocket isOpen")
                        }
                    }.firstNotNullOfOrNull { methodData ->
                        intercept(methodData.className, methodData.methodName)
                    }
                }
            }
        }
    }
}

/**
 * 拦截青少年模式请求
 * @since 7.9.306-1030 ~ 1099
 * @param [version] 版本
 */
fun PackageParam.interceptQSNModeRequest(version: Int) {
    when (version) {
        in 1030..1099 -> {
            "com.qidian.QDReader.bll.manager.QDTeenagerManager".toClass().method {
                name = "init"
                paramCount(1)
                returnType = UnitType
            }.hook().intercept()
        }

        else -> "拦截青少年模式初始化".printlnNotSupportVersion(version)
    }
}

/**
 * 拦截阅读页水印
 * @since 7.9.306-1030 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.interceptReaderBookPageWaterMark(versionCode: Int) {
    when (versionCode) {
        in 1030..1099 -> {
            intercept("com.qidian.QDReader.ui.activity.QDReaderActivity", "setWaterMark")
        }

        else -> "拦截阅读页水印".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 拦截发帖图片水印
 * @since 7.9.306-1030 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.interceptPostImageWatermark(versionCode: Int) {
    when (versionCode) {
        in 1030..1099 -> {
            "com.qidian.QDReader.ui.activity.CirclePostEditActivity".toClass().method {
                name = "addInk2BitmapFile"
                paramCount(2)
                returnType = StringClass
            }.hook().replaceAny {
                val s = args[0] as String
                val file = args[1] as File
                file.apply {
                    File(s).takeIf { it.exists() }?.copyTo(this, true)
                }
                file.absolutePath
            }
        }

        else -> "拦截发帖图片水印".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 拦截自动跳转精选
 * @since 7.9.306-1030 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.interceptAutoJumpSelected(versionCode: Int) {
    when (versionCode) {
        in 1030..1099 -> {
            intercept(
                className = "com.qidian.QDReader.ui.activity.MainGroupActivity",
                methodName = "checkOpenView",
                paramCount = 1
            )
        }

        else -> "拦截自动跳转精选".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 拦截首次安装分析
 * @since 7.9.306-1030 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.interceptFirstInstallAnalytics(versionCode: Int) {
    when (versionCode) {
        in 1030..1099 -> {
            intercept(
                className = "com.qidian.QDReader.ui.activity.MainGroupActivity",
                methodName = "firstInstallAnalytics"
            )
        }

        else -> "拦截首次安装分析".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 拦截异步初始化任务
 * @since 7.9.306-1030 ~ 1099
 */
fun PackageParam.interceptAsyncInitTask(versionCode: Int,clsNameList: List<String>) {
    when(versionCode){
        in 1030..1099 -> {
            "com.rousetime.android_startup.StartupManager".toClass(). method {
                name = "start"
                emptyParam()
            }.hook().before {
                val startupList =
                    instance.getParam<MutableList<*>>("startupList") ?: return@before
                val iterator = startupList.iterator()
                while (iterator.hasNext()) {
                    val clsName = iterator.next()?.javaClass?.name
                    if (clsNameList.any { it == clsName }) {
                        iterator.remove()
                    }
                }
            }
        }
        else -> "拦截异步初始化任务".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 拦截青少年模式弹框
 * @since 7.9.306-1030
 * @param [versionCode] 版本代码
 */
fun PackageParam.interceptQSNYDialog(versionCode: Int) {
    when (versionCode) {
        in 1030..1099 -> {
            DexKitBridge.create(appInfo.sourceDir)?.use { bridge ->
                bridge.findClass {
                    searchPackages = listOf("com.qidian.QDReader.bll.helper")
                    matcher {
                        usingStrings = listOf(
                            "SettingTeenagerModeOpen",
                            "TeenagerMode",
                            "teenagerUnopenedDesc"
                        )
                    }
                }.firstNotNullOfOrNull { classData ->
                    classData.getMethods().findMethod {
                        matcher {
                            modifiers = Modifier.PRIVATE
                            paramTypes = listOf("com.qidian.QDReader.ui.activity.BaseActivity")
                            returnType = "void"
                        }
                    }.firstNotNullOfOrNull { methodData ->
                        intercept(
                            methodData.className,
                            methodData.methodName,
                            methodData.paramTypeNames.size
                        )
                    }

                }
            }
        }

        else -> "拦截青少年模式弹框".printlnNotSupportVersion(versionCode)
    }
}
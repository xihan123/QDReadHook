package cn.xihan.qdds


import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.UnitType

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2023/2/6 16:45
 * @介绍 :
 */
/**
 * 拦截配置
 */
fun PackageParam.interceptOption(
    version: Int,
    configurations: List<OptionEntity.SelectedModel>,
) {
    if (configurations.isEmpty()) return
    val interceptList = mutableListOf<String>()
    configurations.filter { it.selected }.forEach { selected ->
        when (selected.title) {
            "隐私政策更新弹框" -> interceptPrivacyPolicy(version)
            "同意隐私政策弹框" -> interceptAgreePrivacyPolicy(version)
            "WebSocket" -> interceptWebSocket(version)
            "青少年模式请求" -> interceptQSNModeRequest(version)
            "闪屏广告页面" -> interceptSplashAdActivity(version)
            else -> interceptList.add(selected.title)
        }
    }

    if (interceptList.isNotEmpty()) {
        interceptAsyncInitTask(version, interceptList)
    }
}

/**
 * 拦截 Geetest 初始化
 */
fun PackageParam.interceptGeetest(version: Int) {
    when (version) {
        868 -> {

        }
    }

}

/**
 * 拦截隐私政策更新弹框
 */
fun PackageParam.interceptPrivacyPolicy(version: Int) {
    when (version) {
        in 868..950 -> {
            findClass("com.qidian.QDReader.ui.activity.MainGroupActivity").hook {
                injectMember {
                    method {
                        name = "checkPrivacyVersion"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "拦截隐私政策更新弹框".printlnNotSupportVersion(version)
    }
}

/**
 * 拦截同意隐私政策弹框
 * btnAgree
 */
fun PackageParam.interceptAgreePrivacyPolicy(version: Int) {
    val needHookClass = when (version) {
        in 868..878 -> "com.qidian.QDReader.util.w4"
        884 -> "com.qidian.QDReader.util.u4"
        in 890..900 -> "com.qidian.QDReader.util.v4"
        in 906..944 -> "com.qidian.QDReader.util.w4"
        else -> null
    }
    val needHookMethod = when (version) {
        in 868..872 -> "k0"
        878 -> "l0"
        in 884..944 -> "i0"
        else -> null
    }
    if (needHookClass == null || needHookMethod == null) {
        "拦截同意隐私政策弹框".printlnNotSupportVersion(version)
        return
    }
    needHookClass.hook {
        injectMember {
            method {
                name = needHookMethod
                paramCount(4)
                returnType = UnitType
            }
            intercept()
        }
    }
}

/**
 * 拦截WebSocket
 * "handleOpen WebSocket isOpen"
 */
fun PackageParam.interceptWebSocket(version: Int) {
    val needHookClass = when (version) {
        in 868..878 -> "com.qidian.QDReader.component.msg.c"
        in 884..944 -> "com.qidian.QDReader.component.msg.cihai"
        else -> null
    }
    val needHookMethod = when (version) {
        in 868..878 -> "r"
        in 884..944 -> "o"
        else -> null
    }
    if (needHookClass == null || needHookMethod == null) {
        "拦截WebSocket".printlnNotSupportVersion(version)
        return
    }
    needHookClass.hook {
        injectMember {
            method {
                name = needHookMethod
                emptyParam()
                returnType = UnitType
            }
            intercept()
        }
    }
}

/**
 * 拦截青少年模式请求
 */
fun PackageParam.interceptQSNModeRequest(version: Int) {
    when (version) {
        in 868..950 -> {
            findClass("com.qidian.QDReader.bll.manager.QDTeenagerManager").hook {
                injectMember {
                    method {
                        name = "init"
                        paramCount(1)
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "拦截青少年模式初始化".printlnNotSupportVersion(version)
    }
}

/**
 * 拦截闪屏广告页面
 * SplashManager
 * SettingSplashEnableGDT
 */
fun PackageParam.interceptSplashAdActivity(version: Int) {
    val needHookClass = when (version) {
        in 884..900 -> "g6.search"
        in 906..916 -> "j6.search"
        924 -> "k6.search"
        in 932..938 -> "n6.search"
        944 -> "m6.search"
        else -> null
    }
    val needHookMethod = when (version) {
        in 884..944 -> "b"
        else -> null
    }

    needHookClass?.hook {
        injectMember {
            method {
                name = needHookMethod!!
                returnType = BooleanType
            }
            replaceToFalse()
        }
    } ?: "拦截闪屏广告页面".printlnNotSupportVersion(version)
}

/**
 * 拦截异步初始化任务
 * @param version 版本号
 * @param substring 0:标题 1:拦截的类名
 */
fun PackageParam.interceptAsyncInitTask(
    version: Int,
    clsNameList: List<String>
) {
    when (version) {
        in 872..950 -> {
            findClass("com.rousetime.android_startup.StartupManager").hook {
                injectMember {
                    method {
                        name = "start"
                        emptyParam()
                    }
                    beforeHook {
                        val startupList =
                            instance.getParam<MutableList<*>>("startupList") ?: return@beforeHook
                        val iterator = startupList.iterator()
                        while (iterator.hasNext()) {
                            val clsName = iterator.next()?.javaClass?.name
                            if (clsNameList.any { it == clsName }) {
                                iterator.remove()
                            }
                        }
                    }
                }
            }
        }

        else -> "拦截初始化任务".printlnNotSupportVersion(version)
    }
}
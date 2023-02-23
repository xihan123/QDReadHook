package cn.xihan.qdds

import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.StringClass
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
    configurations.filter { it.selected }.forEach { selected ->
        when (selected.title) {
            "隐私政策更新弹框" -> interceptPrivacyPolicy(version)
            "同意隐私政策弹框" -> interceptAgreePrivacyPolicy(version)
            "WebSocket" -> interceptWebSocket(version)
            "青少年模式请求" -> interceptQSNModeRequest(version)
            "闪屏广告页面" -> interceptSplashAdActivity(version)
            else -> interceptAsyncInitTask(version, selected.title.split("|"))
        }


    }
}

/**
 * 干掉 Geetest 初始化
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
        in 868..880 -> {
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
    when (version) {
        in 868..872 -> {
            findClass("com.qidian.QDReader.util.w4").hook {
                injectMember {
                    method {
                        name = "k0"
                        paramCount(4)
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "拦截同意隐私政策弹框".printlnNotSupportVersion(version)
    }
}

/**
 * 拦截WebSocket
 * "handleOpen WebSocket isOpen"
 */
fun PackageParam.interceptWebSocket(version: Int) {
    when (version) {
        in 868..872 -> {
            findClass("com.qidian.QDReader.component.msg.c").hook {
                injectMember {
                    method {
                        name = "r"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "拦截WebSocket".printlnNotSupportVersion(version)
    }
}

/**
 * 拦截青少年模式请求
 */
fun PackageParam.interceptQSNModeRequest(version: Int) {
    when (version) {
        in 868..880 -> {
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
 */
fun PackageParam.interceptSplashAdActivity(version: Int) {
    when (version) {
        in 868..880 -> {
            findClass("com.qidian.QDReader.ui.activity.SplashADActivity").hook {
                injectMember {
                    method {
                        name = "onCreate"
                        paramCount(1)
                        returnType = UnitType
                    }
                    afterHook {
                        instance.printCallStack()
                        val b = instance.getParam<Any>("b")
                        b?.apply {
                            setParam("a", false)
                            method {
                                name = "a"
                                emptyParam()
                            }.get(b).call()
                        }

                    }
                }
            }
        }

        else -> "拦截闪屏广告页面".printlnNotSupportVersion(version)
    }
}

/**
 * 拦截异步初始化任务
 * @param version 版本号
 * @param title 标题
 * @param className 类名
 */
fun PackageParam.interceptAsyncInitTask(
    version: Int,
    substring: List<String>
) {
    when (version) {
        872 -> {
            findClass(substring[1]).hook {
                injectMember {
                    method {
                        name = "create"
                        returnType = StringClass
                    }
                    intercept()
                }
            }
        }

        else -> "拦截$${substring[0]}".printlnNotSupportVersion(version)
    }
}
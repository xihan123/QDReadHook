package cn.xihan.qdds


import android.content.pm.PackageInfo
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.ListClass
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
    val interceptList = mutableListOf<String>()
    configurations.filter { it.selected }.forEach { selected ->
        when (selected.title) {
            "隐私政策更新弹框" -> interceptPrivacyPolicy(version)
            "同意隐私政策弹框" -> interceptAgreePrivacyPolicy(version)
            "WebSocket" -> interceptWebSocket(version)
            "青少年模式请求" -> interceptQSNModeRequest(version)
            "闪屏广告页面" -> interceptSplashAdActivity(version)
            "部分检测设备环境" -> interceptEnvironmentCheck(version)
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
        in 906..916 -> "com.qidian.QDReader.util.w4"
        else -> null
    }
    val needHookMethod = when (version) {
        in 868..872 -> "k0"
        878 -> "l0"
        in 884..916 -> "i0"
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
        in 884..916 -> "com.qidian.QDReader.component.msg.cihai"
        else -> null
    }
    val needHookMethod = when (version) {
        in 868..878 -> "r"
        in 884..916 -> "o"
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
        else -> null
    }
    val needHookMethod = when (version) {
        in 884..916 -> "b"
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
 * 拦截部分检测设备环境
 *
 */
fun PackageParam.interceptEnvironmentCheck(versionCode: Int) {
    val needHookClass = when(versionCode){
        in 896..900 -> "c6.b"
        in 906..916 -> "f6.b"
        else -> null
    }

    /**
     * lsposed
     */
    needHookClass?.hook {
        injectMember {
            method {
                name = "a"
                emptyParam()
                returnType = IntType
            }
            replaceTo(0)
        }

        injectMember {
            method {
                name = "b"
                emptyParam()
                returnType = BooleanType
            }
            replaceToFalse()
        }

        injectMember {
            method {
                name = "cihai"
                emptyParam()
                returnType = BooleanType
            }
            replaceToFalse()
        }
    }


    /**
     * de.robv.android.xposed.XposedHelpers
     */
    val needHookClass2 = when(versionCode){
        in 896..900 -> "fe.b"
        in 906..916 -> "ke.b"
        else -> null
    }
    needHookClass2?.hook {
        injectMember {
            method {
                name = "judian"
                emptyParam()
                returnType = UnitType
            }
            intercept()
        }

        injectMember {
            method {
                name = "cihai"
                emptyParam()
                returnType = UnitType
            }
            intercept()
        }
    }
    when (versionCode) {
        in 896..916 -> {

            /**
             * /system/bin/qemu_props
             */
            findClass("com.yw.baseutil.judian").hook {
                injectMember {
                    method {
                        name = "t"
                        paramCount(1)
                        returnType = BooleanType
                    }
                    replaceToFalse()
                }
            }

            /**
             * 包名列表
             */
            val needHookMethod = when (versionCode) {
                in 896..900 -> "j"
                in 906..916 -> "k"
                else -> null
            }

            /**
             * 代理检测
             */
            val needHookMethod2 = when (versionCode) {
                in 896..900 -> "N"
                in 906..916 -> "O"
                else -> null
            }
            /**
             * x86 检测
             */
            val needHookMethod3 = when (versionCode) {
                in 896..900 -> "O"
                in 906..916 -> "P"
                else -> null
            }

            findClass("com.qidian.QDReader.core.util.m").hook {
                injectMember {
                    method {
                        name = needHookMethod!!
                        paramCount(1)
                        returnType = ListClass
                    }
                    replaceTo(emptyList<PackageInfo>())
                }

                injectMember {
                    method {
                        name =needHookMethod2!!
                        emptyParam()
                        returnType = BooleanType
                    }
                    replaceToFalse()
                }

                injectMember {
                    method {
                        name =needHookMethod3!!
                        emptyParam()
                        returnType = BooleanType
                    }
                    replaceToFalse()
                }

            }

            /*
            /**
             * abcEncDyettonFeyedxadcDyettonqwy
             */
            findClass("ie.a").hook {
                injectMember {
                    method {
                        name = "search"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }

            }

             */

        }

        else -> "部分检测设备环境".printlnNotSupportVersion(HookEntry.versionCode)
    }
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
        in 872..916 -> {
            /*
            findClass(substring[1]).hook {
                injectMember {
                    method {
                        name = "create"
                        returnType = StringClass
                    }
                    intercept()
                }
            }

             */

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
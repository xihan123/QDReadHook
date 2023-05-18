package cn.xihan.qdds

import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import com.highcapable.yukihookapi.hook.factory.current
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2022/8/28 16:16
 * @介绍 :
 */

/**
 * Hook 闪屏页相关
 */
fun PackageParam.splashPage(
    versionCode: Int,
    isEnableSplash: Boolean = false,
    isEnableCustomSplash: Boolean = false,
) {
    if (isEnableSplash) {
        if (isEnableCustomSplash) {
            enableCustomSplash(versionCode)
        }
    } else {
        disableSplash(versionCode)
    }
}

/**
 * 关闭闪屏页
 */
fun PackageParam.disableSplash(versionCode: Int) {
    when (versionCode) {
        in 758..924 -> {
            /**
             * com.qidian.QDReader.ui.activity.MainGroupActivity.onCreate
             * SplashManager.c().h(this.getApplicationContext());
             */
            val splashManagerNeedHookMethod = when (versionCode) {
                in 758..878 -> "k"
                in 884..924 -> "h"
                else -> null
            }
            if (splashManagerNeedHookMethod != null) {
                findClass("com.qidian.QDReader.bll.splash.SplashManager").hook {
                    injectMember {
                        method {
                            name = splashManagerNeedHookMethod
                            paramCount(1)
                            returnType = UnitType
                        }
                        intercept()
                    }
                }
            }


            findClass("com.qidian.QDReader.ui.activity.SplashImageActivity").hook {
                injectMember {
                    method {
                        name = "showSplashImage"
                        param(StringClass)
                        returnType = UnitType
                    }
                    afterHook {
                        val mSplashHelper = instance.getParam<Any>("mSplashHelper")
                        mSplashHelper?.current {
                            method {
                                name = "e"
                            }.call()

                        }
                    }
                }
            }

        }

        else -> "闪屏页".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 启用自定义闪屏页
 */
fun PackageParam.enableCustomSplash(
    versionCode: Int,
    isEnableCustomSplashImageShowAllButton: Boolean = false,
    customSplashImageFilePath: String = "",
    customBookId: String = "",
    customSplashImageType: Int = 0,
) {
    when (versionCode) {
        in 758..950 -> {
            findClass("com.qidian.QDReader.ui.activity.SplashImageActivity").hook {
                if (!isEnableCustomSplashImageShowAllButton) {
                    injectMember {
                        method {
                            name = "onCreate"
                            param(BundleClass)
                            returnType = UnitType
                        }
                        afterHook {
                            val btnSkip = instance.getView<Button>("btnSkip")
                            btnSkip?.visibility = View.GONE
                            val ivTop = instance.getView<ImageView>("ivTop")
                            ivTop?.visibility = View.GONE
                            val layoutShadow = instance.getParam<RelativeLayout>("layoutShadow")
                            layoutShadow?.visibility = View.GONE
                            val mGotoActivityShimmer =
                                instance.getView<FrameLayout>("mGotoActivityShimmer")
                            mGotoActivityShimmer?.visibility = View.GONE
                        }
                    }
                }
                injectMember {
                    method {
                        name = "start"
                        param(
                            "com.qidian.QDReader.ui.activity.SplashActivity".toClass(),
                            StringClass,
                            StringClass,
                            LongType,
                            IntType
                        )
                    }

                    beforeHook {
                        if (customSplashImageFilePath.isNotBlank()) {
                            args(index = 1).set(customSplashImageFilePath)
                        }
                        if (customBookId.isNotBlank()) {
                            args(index = 2).set("QDReader://ShowBook/$customBookId")
                        }
                        args(index = 4).set(customSplashImageType)
                    }

                    afterHook {
                        // 打印传入的参数
                        //loggerE(msg = " \nargs[1]: ${args[1] as String} \nargs[2]: ${args[2] as String} \nargs[3]: ${args[3] as Long} \nargs[4]: ${args[4] as Int}")
                    }


                }
            }
        }

        else -> "闪屏页".printlnNotSupportVersion(versionCode)
    }
}
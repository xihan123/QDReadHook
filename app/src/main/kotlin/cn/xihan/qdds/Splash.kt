package cn.xihan.qdds

import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import com.highcapable.yukihookapi.hook.factory.current
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType
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
        in 758..880 -> {
            findClass("com.qidian.QDReader.bll.splash.SplashManager").hook {
                injectMember {
                    method {
                        name = "k"
                        paramCount(1)
                        returnType = UnitType
                    }
                    intercept()
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

            /**
             * SettingSplashEnableGDT
             */
            val needHookClass = when (versionCode) {
                868 -> "n6.a"
                872 -> "l6.a"
                else -> null
            }
            needHookClass?.hook {
                injectMember {
                    method {
                        name = "e"
                        emptyParam()
                        returnType = BooleanType
                    }
                    replaceToFalse()
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
        in 758..880 -> {
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

/*
/**
 * 闪屏页相关配置弹框
 */
fun Context.showSplashOptionDialog() {
    val linearLayout = CustomLinearLayout(this, isAutoWidth = false)
    val enableSplashOptionSwitch = CustomSwitch(
        context = this,
        title = "启用闪屏页",
        isEnable = HookEntry.optionEntity.splashOption.enableSplash
    ) {
        HookEntry.optionEntity.splashOption.enableSplash = it
    }
    val enableCustomSplashOptionSwitch = CustomSwitch(
        context = this,
        title = "启用自定义闪屏页",
        isAvailable = HookEntry.optionEntity.splashOption.enableSplash,
        isEnable = HookEntry.optionEntity.splashOption.enableCustomSplash
    ) {
        HookEntry.optionEntity.splashOption.enableCustomSplash = it
    }
    val customSplashEnableShowAllButtonOptionSwitch = CustomSwitch(
        context = this,
        title = "启用闪屏页显示全部按钮",
        isAvailable = HookEntry.optionEntity.splashOption.enableCustomSplash,
        isEnable = HookEntry.optionEntity.splashOption.enableCustomSplashAllButton
    ) {
        HookEntry.optionEntity.splashOption.enableCustomSplashAllButton = it
    }
    val splashCustomToBookEditText = CustomEditText(
        context = this,
        title = "填入自定义闪屏页跳转到书籍页面的关键词",
        message = "填入书籍的BookId,详情页分享链接里面\"bookid=\"后面到\"&\"前那串数字就是了",
        value = HookEntry.optionEntity.splashOption.customBookId,
        isAvailable = HookEntry.optionEntity.splashOption.enableCustomSplash
    ) {
        HookEntry.optionEntity.splashOption.customBookId = it
    }
    val splashCustomTypeOptionEdit = CustomEditText(
        context = this,
        title = "自定义闪屏页类型",
        message = "填入 0 或者 1 其他数字可能无效喔~",
        value = HookEntry.optionEntity.splashOption.customSplashType.toString(),
        isAvailable = HookEntry.optionEntity.splashOption.enableCustomSplash
    ) {
        HookEntry.optionEntity.splashOption.customSplashType = it.toInt()
    }
    val splashCustomImageEditText = CustomEditText(
        context = this,
        title = "自定义闪屏页图片",
        message = "填入图片所在绝对路径,如失败请给起点存储权限,或检查是否填写正确,留空默认",
        value = HookEntry.optionEntity.splashOption.customSplashImageFilePath,
        isAvailable = HookEntry.optionEntity.splashOption.enableCustomSplash
    ) {
        HookEntry.optionEntity.splashOption.customSplashImageFilePath = it
    }
    linearLayout.apply {
        addView(enableSplashOptionSwitch)
        addView(enableCustomSplashOptionSwitch)
        addView(customSplashEnableShowAllButtonOptionSwitch)
        addView(splashCustomToBookEditText)
        addView(splashCustomTypeOptionEdit)
        addView(splashCustomImageEditText)
    }
    alertDialog {
        title = "闪屏页相关配置"
        customView = linearLayout
        okButton {
            updateOptionEntity()
        }
        negativeButton("返回") {
            it.dismiss()
        }
        build()
        show()
    }
}
 */

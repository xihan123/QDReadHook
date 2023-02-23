package cn.xihan.qdds

import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.BooleanType

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2023/2/11 13:56
 * @介绍 :
 */
/**
 * 自定义书架顶部图片
 */
fun PackageParam.customBookShelfTopImage(versionCode: Int) {
    when (versionCode) {
        in 860..880 -> {
            findClass("com.qidian.QDReader.repository.entity.config.BookshelfConfig").hook {
                injectMember {
                    method {
                        name = "getLightMode"
                        emptyParam()
                        returnType =
                            "com.qidian.QDReader.repository.entity.config.ConfigColors".toClass()
                    }
                    afterHook {
                        result?.setDayMode(HookEntry.optionEntity)
                    }
                }

                injectMember {
                    method {
                        name = "getDarkMode"
                        emptyParam()
                        returnType =
                            "com.qidian.QDReader.repository.entity.config.ConfigColors".toClass()
                    }
                    afterHook {
                        if (HookEntry.optionEntity.bookshelfOption.enableSameNightAndDay) {
                            result?.setDayMode(HookEntry.optionEntity)
                        } else {
                            result?.setDarkMode(HookEntry.optionEntity)
                        }
                    }
                }

            }
        }

        else -> "自定义书架顶部图片".printlnNotSupportVersion(versionCode)
    }
}

/**
 * Hook 启用旧版布局
 */
fun PackageParam.enableOldLayout(versionCode: Int) {
    when (versionCode) {
        in 758..799 -> {
            findClass("com.qidian.QDReader.component.config.QDAppConfigHelper\$Companion").hook {
                injectMember {
                    method {
                        name = "getBookShelfNewStyle"
                    }
                    replaceToFalse()
                }
            }
        }

        else -> "启用旧版布局".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 新版书架布局
 * 上级调用: com.qidian.QDReader.ui.activity.MainGroupActivity.onCreate
 * mFragmentPagerAdapter
 */
fun PackageParam.newBookShelfLayout(
    versionCode: Int,
    enableNewBookShelfLayout: Boolean = false,
) {
    val needHookClass = when (versionCode) {
        827 -> "s4.a\$a"
        in 834..843 -> "q4.a\$a"
        in 850..868 -> "r4.a\$a"
        872 -> "p4.a\$a"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 827..850 -> "b"
        in 868..872 -> "c"
        else -> null
    }
    needHookClass?.hook {
        injectMember {
            method {
                name = needHookMethod ?: "b"
                emptyParam()
                returnType = BooleanType
            }
            if (enableNewBookShelfLayout) {
                replaceToTrue()
            } else {
                replaceToFalse()
            }

        }
    } ?: "新版书架布局".printlnNotSupportVersion(versionCode)
}

/**
 * 设置白天模式参数
 */
private fun Any.setDayMode(optionEntity: OptionEntity) {
    setParams(
        "border01" to optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel.border01.ifBlank {
            getParam<String>(
                "border01"
            )!!
        },
        "font" to optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel.font.ifBlank {
            getParam<String>(
                "font"
            )!!
        },
        "fontHLight" to optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel.fontHLight.ifBlank {
            getParam<String>(
                "fontHLight"
            )!!
        },
        "fontLight" to optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel.fontLight.ifBlank {
            getParam<String>(
                "fontLight"
            )!!
        },
        "fontOnSurface" to optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel.fontOnSurface.ifBlank {
            getParam<String>(
                "fontOnSurface"
            )!!
        },
        "surface01" to optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel.surface01.ifBlank {
            getParam<String>(
                "surface01"
            )!!
        },
        "surfaceIcon" to optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel.surfaceIcon.ifBlank {
            getParam<String>(
                "surfaceIcon"
            )!!
        },
        "headImage" to optionEntity.bookshelfOption.lightModeCustomBookShelfTopImageModel.headImage.ifBlank {
            getParam<String>(
                "headImage"
            )!!
        },
    )
}

/**
 * 设置夜间模式参数
 */
private fun Any.setDarkMode(optionEntity: OptionEntity) {
    setParams(
        "border01" to optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel.border01.ifBlank {
            getParam<String>(
                "border01"
            )!!
        },
        "font" to optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel.font.ifBlank {
            getParam<String>(
                "font"
            )!!
        },
        "fontHLight" to optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel.fontHLight.ifBlank {
            getParam<String>(
                "fontHLight"
            )!!
        },
        "fontLight" to optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel.fontLight.ifBlank {
            getParam<String>(
                "fontLight"
            )!!
        },
        "fontOnSurface" to optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel.fontOnSurface.ifBlank {
            getParam<String>(
                "fontOnSurface"
            )!!
        },
        "surface01" to optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel.surface01.ifBlank {
            getParam<String>(
                "surface01"
            )!!
        },
        "surfaceIcon" to optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel.surfaceIcon.ifBlank {
            getParam<String>(
                "surfaceIcon"
            )!!
        },
        "headImage" to optionEntity.bookshelfOption.darkModeCustomBookShelfTopImageModel.headImage.ifBlank {
            getParam<String>(
                "headImage"
            )!!
        },
    )
}
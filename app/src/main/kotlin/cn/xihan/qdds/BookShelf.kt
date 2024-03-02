package cn.xihan.qdds

import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.param.PackageParam

/**
 * 自定义书架顶部图片
 * # 官方默认图片地址
 *
 * [地址1](https://qidian.qpic.cn/qidian_common/349573/35838f8b9f9182b379490a3e0e5f70b9/0)
 *
 * [地址2](https://imgservices-1252317822.image.myqcloud.com/image/20210507/q2bvc3z5vd.jpg)
 * ## 建议分辨率为 1125*504
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.customBookShelfTopImage(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.repository.entity.config.BookshelfConfig".toClass().apply {

                method {
                    name = "getLightMode"
                    emptyParam()
                    returnType =
                        "com.qidian.QDReader.repository.entity.config.ConfigColors".toClass()
                }.hook().after {
                    result?.setDayMode(Option.optionEntity)
                }

                method {
                    name = "getDarkMode"
                    emptyParam()
                    returnType =
                        "com.qidian.QDReader.repository.entity.config.ConfigColors".toClass()
                }.hook().after {
                    if (Option.optionEntity.bookshelfOption.enableSameNightAndDay) {
                        result?.setDayMode(Option.optionEntity)
                    } else {
                        result?.setDarkMode(Option.optionEntity)
                    }
                }
            }
        }

        else -> "自定义书架顶部图片".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 设置白天模式参数
 * @suppress Generate Documentation
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
 * @suppress Generate Documentation
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
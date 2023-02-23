package cn.xihan.qdds

import android.content.Context
import android.os.Environment
import androidx.annotation.Keep
import com.alibaba.fastjson2.toJSONString
import com.highcapable.yukihookapi.hook.log.loggerE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2022/11/17 12:27
 * @介绍 :
 */
/**
 * 读取自定义主题文件夹
 */
fun readCustomThemeFile(): File? = try {
    val file =
        File(
            "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/QDReader/ReaderTheme",
            "customTheme.json"
        )
    file.parentFile?.mkdirs()
    if (!file.exists()) {
        file.createNewFile()
        file.writeText(Json.encodeToString(defaultThemeModel()))
    }
    file
} catch (e: Exception) {
    loggerE(msg = "readCustomThemeFile: ${e.message}")
    null
}

/**
 * 读取自定义主题文件
 */
fun readCustomThemeOptionModel(): ThemeModel {
    val file = readCustomThemeFile() ?: return defaultThemeModel()
    return if (file.readText().isNotEmpty()) {
        try {
            val kJson = Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
            kJson.decodeFromString(file.readText())
        } catch (e: Exception) {
            loggerE(msg = "readCustomThemeOptionModel: ${e.message}")
            defaultThemeModel()
        }
    } else {
        defaultThemeModel()
    }
}

/**
 * 写入自定义主题文件
 */
fun writeCustomThemeOptionFile(themeModel: ThemeModel): Boolean =
    try {
        readCustomThemeFile()?.writeText(Json.encodeToString(themeModel))
        true
    } catch (e: Exception) {
        loggerE(msg = "writeCustomThemeOptionFile: ${e.message}")
        false
    }

@Keep
@Serializable
data class ThemeModel(
    @SerialName("bg")
    var bg: BgModel = BgModel(),
    @SerialName("colors")
    var colors: ColorsModel = ColorsModel(),
    @SerialName("previewImage")
    var previewImage: String = "",
    @SerialName("version")
    var version: Int = 0,
) {
    @Keep
    @Serializable
    data class BgModel(
        @SerialName("image")
        var image: String = "",
        @SerialName("type")
        var type: Int = 0,
    )

    @Keep
    @Serializable
    data class ColorsModel(
        @SerialName("background")
        var background: String = "",
        @SerialName("backgroundLight")
        var backgroundLight: String = "",
        @SerialName("font")
        var font: String = "",
        @SerialName("highLight")
        var highLight: String = "",
        @SerialName("tint")
        var tint: String = "",
    )
}

/**
 * 默认主题
 */
fun defaultThemeModel(): ThemeModel = ThemeModel(
    bg = ThemeModel.BgModel(
        image = "readBg.jpg",
        type = 2
    ),
    colors = ThemeModel.ColorsModel(
        background = "#E6F1E3",
        backgroundLight = "#F8FFF8",
        font = "#B2B2B2",
        highLight = "#336C47",
        tint = "#336C47"
    ),
    previewImage = "slice_bg.png",
    version = 1
)

/**
 * 可视化阅读页背景颜色调整
 */
fun Context.showVisualizeReadingPageBackgroundColorAdjustmentDialog() {
    val themeModel = readCustomThemeOptionModel()
    val linearLayout = CustomLinearLayout(this, isAutoWidth = false)
    val fontColor = CustomEditTextColor(
        context = this,
        title = "字体颜色",
        message = "如:#B2B2B2",
        value = themeModel.colors.font
    ) {
        themeModel.colors.font = it
    }

    val backgroundColor = CustomEditTextColor(
        context = this,
        title = "背景颜色",
        message = "如:#E6F1E3",
        value = themeModel.colors.background
    ) {
        themeModel.colors.background = it
    }

    val backgroundColorLight = CustomEditTextColor(
        context = this,
        title = "背景颜色(亮)",
        message = "如:#F8FFF8",
        value = themeModel.colors.backgroundLight
    ) {
        themeModel.colors.backgroundLight = it
    }

    val highLightColor = CustomEditTextColor(
        context = this,
        title = "高亮颜色",
        message = "如:#336C47",
        value = themeModel.colors.highLight
    ) {
        themeModel.colors.highLight = it
    }

    val tint = CustomEditTextColor(
        context = this,
        title = "tint颜色",
        message = "如:#336C47",
        value = themeModel.colors.tint
    ) {
        themeModel.colors.tint = it
    }

    val bgType = CustomEditText(
        context = this,
        title = "背景类型",
        message = "如:2",
        value = themeModel.bg.type.toString()
    ) {
        themeModel.bg.type = it.toInt()
    }

    val bgImage = CustomEditText(
        context = this,
        title = "背景图片",
        message = "如:readBg.jpg",
        value = themeModel.bg.image
    ) {
        themeModel.bg.image = it
    }

    val previewImage = CustomEditText(
        context = this,
        title = "预览图片",
        message = "如:slice_bg.png",
        value = themeModel.previewImage
    ) {
        themeModel.previewImage = it
    }

    linearLayout.apply {
        addView(fontColor)
        addView(backgroundColor)
        addView(backgroundColorLight)
        addView(highLightColor)
        addView(tint)
        addView(bgType)
        addView(bgImage)
        addView(previewImage)
    }

    alertDialog {
        title = "可视化阅读页背景颜色调整"
        customView = linearLayout
        positiveButton("保存并复制到剪切板") {
            writeCustomThemeOptionFile(themeModel)
            safeRun {
                copyToClipboard(themeModel.toJSONString())
            }
        }
        negativeButton("返回") {
            it.dismiss()
        }

        build()
        show()
    }

}

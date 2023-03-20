package cn.xihan.qdds

import android.content.Context
import android.os.Environment
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType
import java.io.File

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2023/2/11 13:55
 * @介绍 :
 */

/**
 * 自定义阅读页背景路径
 * 上级调用: ReaderTheme
 */
fun PackageParam.customReadBackgroundPath(versionCode: Int) {
    val needHookClass = when (versionCode) {
        827 -> "d6.f"
        in 834..868 -> "b6.f"
        in 872..878 -> "z5.f"
        884 -> "u5.c"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 827..878 -> "G"
        884 -> "C"
        else -> null
    }
    if (needHookClass == null || needHookMethod == null) {
        "自定义阅读页背景路径".printlnNotSupportVersion(versionCode)
        return
    }

    needHookClass.hook {
        injectMember {
            method {
                name = needHookMethod
                paramCount(1)
                returnType = StringClass
            }
            afterHook {
                result =
                    "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/QDReader/ReaderTheme/"
            }
        }
    }
}

/**
 * 阅读页-章评相关
 */
fun PackageParam.readerPageChapterReviewPictures(
    versionCode: Int,
    enableShowReaderPageChapterSaveRawPictures: Boolean = false,
    enableShowReaderPageChapterSavePictureDialog: Boolean = false,
    enableShowReaderPageChapterSaveAudioDialog: Boolean = false,
    enableCopyReaderPageChapterComment: Boolean = false,
) {
    if (enableShowReaderPageChapterSaveRawPictures && versionCode in 868..884) {
        findClass("com.qd.ui.component.modules.imagepreivew.QDUIGalleryActivity").hook {
            injectMember {
                method {
                    name = "initView"
                    emptyParam()
                    returnType = UnitType
                }
                afterHook {
                    instance.setParam("mMoreIconStyle", 1)
                }
            }
        }
    }

    if (enableShowReaderPageChapterSavePictureDialog || enableCopyReaderPageChapterComment) {
        val needHookClass = when (versionCode) {
            in 868..878 -> "com.qidian.QDReader.ui.viewholder.chaptercomment.list.b0"
            884 -> "com.qidian.QDReader.ui.viewholder.chaptercomment.list.y"
            else -> null
        }
        val needHookMethod = when (versionCode) {
            in 868..878 -> "A"
            884 -> "x"
            else -> null
        }
        if (needHookClass == null || needHookMethod == null) {
            "阅读页-章评图片".printlnNotSupportVersion(versionCode)
            return
        }
        /**
         * com.qidian.QDReader.ui.adapter.reader.ChapterParagraphCommentAdapter.onBindContentItemViewHolder
         * b00.A(newParagraphCommentListBean$DataListBean0, this.getMBookInfo());
         */
        needHookClass.hook {
            injectMember {
                method {
                    name = needHookMethod
                    paramCount(2)
                    returnType = UnitType
                }
                afterHook {
                    if (enableShowReaderPageChapterSavePictureDialog) {
                        val rawImgUrl =
                            args[0]?.toJSONString().parseObject().getString("imageDetail")
                        val p = instance.getParam<ImageView>("p")
                        if (rawImgUrl == null || p == null) return@afterHook
                        p.setOnLongClickListener {
                            p.context.alertDialog {
                                title = "图片地址"
                                message = rawImgUrl
                                positiveButton("复制") {
                                    p.context.copyToClipboard(rawImgUrl)
                                }
                                negativeButton("取消") {
                                    it.dismiss()
                                }
                                build()
                                show()
                            }
                            true
                        }
                    }

                    if (enableCopyReaderPageChapterComment) {
                        val r = instance.getView<TextView>("r") ?: return@afterHook
                        val v = instance.getParam<Context>("v") ?: return@afterHook
                        r.setOnLongClickListener {
                            v.apply {
                                copyToClipboard(r.text.toString())
                                toast("已复制到剪贴板")
                            }
                            false
                        }
                    }

                }
            }
        }
    }

    if (enableShowReaderPageChapterSaveAudioDialog && versionCode == 884) {
        findClass("com.qidian.QDReader.ui.view.chapter_review.VoicePlayerView").hook {
            injectMember {
                method {
                    name = "o"
                    emptyParam()
                    returnType = UnitType
                }
                afterHook {
                    val g = instance.getView<RelativeLayout>("g")
                    val l = instance.getParam<String>("l")
                    val b = instance.getParam<Context>("b")
                    if (l.isNullOrBlank() || b == null) {
                        "音频文件不存在".loge()
                        return@afterHook
                    }
                    g?.setOnLongClickListener {
                        g.context.audioExportDialog(l)
                        true
                    }
                }
            }
        }
    }
}

/**
 * 阅读时间加倍
 * user_book_read_time
 */
fun PackageParam.readTimeDouble(
    versionCode: Int,
    enableVIPChapterTime: Boolean = false,
    doubleSpeed: Int = 5,
) {
    val needHookClass = when (versionCode) {
        868 -> "sf.a"
        in 872..872 -> "qf.a"
        878 -> "pf.a"
        884 -> "jf.search"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 868..878 -> "d"
        884 -> "a"
        else -> null
    }
    if (needHookClass == null || needHookMethod == null) {
        "阅读时间加倍".printlnNotSupportVersion(versionCode)
        return
    }
    needHookClass.hook {
        injectMember {
            method {
                name = needHookMethod
                paramCount(2)
            }
            afterHook {
                val list = result as? MutableList<*>
                if (list.isNullOrEmpty()) return@afterHook
                list.forEach { item ->
                    item?.let {
                        val totalTime = it.getParam<Long>("totalTime")
                        val currentTime = System.currentTimeMillis()
                        val startTime2 = currentTime - ((totalTime ?: 1000) * doubleSpeed)
                        it.setParams(
                            "startTime" to startTime2,
                            "endTime" to currentTime,
                            "totalTime" to (currentTime - startTime2),
                        )
                        if (enableVIPChapterTime) {
                            it.setParam("chapterVIP", 1)
                        }
                    }
                }

            }
        }
    }

}

/**
 * 音频文件导出对话框
 */
fun Context.audioExportDialog(filePath: String) {
    val file = File(filePath)
    if (!file.exists()) {
        toast("音频文件不存在")
        return
    }
    val editText = CustomEditText(
        context = this,
        mHint = "输入要保存的文件名",
        value = file.name,
    )
    alertDialog {
        title = "导出文件\n输入要保存的文件名"
        customView = editText
        okButton {
            val fileName = "${editText.editText.text}.m4a"
            if (fileName.isBlank()) {
                toast("文件名不能为空")
                return@okButton
            }
            val saveFile = File(
                "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/QDReader/Audio",
                fileName
            ).apply {
                if (!parentFile.exists()) parentFile.mkdirs()
            }
            if (saveFile.exists()) {
                toast("文件已存在")
                return@okButton
            }
            file.copyTo(saveFile)
            toast("导出成功")
            it.dismiss()
        }
        cancelButton {
            it.dismiss()
        }
        build()
        show()
    }

}
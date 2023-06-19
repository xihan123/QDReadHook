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
        in 884..900 -> "u5.c"
        in 906..916 -> "x5.c"
        924 -> "y5.c"
        in 932..938 -> "b6.c"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 827..878 -> "G"
        in 884..938 -> "C"
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
            replaceTo("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/QDReader/ReaderTheme/")
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
    if (enableShowReaderPageChapterSaveRawPictures && versionCode in 868..950) {
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
            in 890..938 -> "com.qidian.QDReader.ui.viewholder.chaptercomment.list.e0"
            else -> null
        }
        val needHookMethod = when (versionCode) {
            in 868..878 -> "A"
            884 -> "x"
            in 890..938 -> "z"
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

    if (enableShowReaderPageChapterSaveAudioDialog && versionCode in 884..938) {
        when (versionCode) {
            884 -> {
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

            in 890..938 -> {
                findClass("com.qidian.QDReader.ui.view.chapter_review.VoicePlayerView").hook {
                    injectMember {
                        method {
                            name = "p"
                            emptyParam()
                            returnType = UnitType
                        }
                        afterHook {
                            val g = instance.getParentView<RelativeLayout>("g")
                            val p = instance.getParam<String>("p")
                            val b = instance.getParentParam<Context>("b")
                            if (p.isNullOrBlank() || b == null) {
                                "音频文件不存在".loge()
                                return@afterHook
                            }
                            g?.setOnLongClickListener {
                                g.context.audioExportDialog(p)
                                true
                            }
                        }
                    }
                }
            }

            else -> "音频导入".printlnNotSupportVersion(versionCode)
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
        in 884..890 -> "jf.search"
        in 896..900 -> "kf.search"
        in 906..924 -> "pf.search"
        in 932..938 -> "sf.search"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 868..878 -> "d"
        in 884..938 -> "a"
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

/**
 * 阅读页最后一页
 * @param versionCode 版本号
 * @param shieldAlsoRead 是否屏蔽推荐
 * @param shieldRecommendation 是否屏蔽推荐
 * @param shieldSimilarRecommend 是否屏蔽相似推荐
 * @param hideAlsoRead 是否隐藏读过的书
 * @param hideRecommendation 是否隐藏推荐
 * @param hideBookList 是否隐藏书单
 * @param hideSimilarRecommend 是否隐藏相似推荐
 * @param hideTryRead 是否隐藏试读
 * @param hideCircle 是否隐藏圈子
 * @param hideAdView 是否隐藏广告
 */
fun PackageParam.readBookLastPage(
    versionCode: Int,
    shieldAlsoRead: Boolean = false,
    shieldRecommendation: Boolean = false,
    shieldSimilarRecommend: Boolean = false,
    hideAlsoRead: Boolean = false,
    hideRecommendation: Boolean = false,
    hideBookList: Boolean = false,
    hideSimilarRecommend: Boolean = false,
    hideTryRead: Boolean = false,
    hideCircle: Boolean = false,
    hideAdView: Boolean = false
) {
    val needHookClass = when (versionCode) {
        in 896..938 -> "com.qidian.QDReader.ui.view.lastpage.LastPageRoleView"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 896..938 -> "l"
        else -> null
    }
    needHookClass?.hook {
        injectMember {
            method {
                name = needHookMethod!!
                param("com.qidian.QDReader.repository.entity.BookLastPage".toClass())
                returnType = UnitType
            }
            afterHook {
                setBookLastPage(
                    obj = args[0],
                    shieldAlsoRead = shieldAlsoRead,
                    shieldRecommendation = shieldRecommendation,
                    shieldSimilarRecommend = shieldSimilarRecommend,
                    hideAlsoRead = hideAlsoRead,
                    hideRecommendation = hideRecommendation,
                    hideBookList = hideBookList,
                    hideSimilarRecommend = hideSimilarRecommend,
                    hideTryRead = hideTryRead,
                    hideCircle = hideCircle
                )
            }
        }
    }

    val needHookClass2 = when (versionCode) {
        in 896..938 -> "com.qidian.QDReader.ui.view.lastpage.LastPageCircleView"
        else -> null
    }
    val needHookMethod2 = when (versionCode) {
        in 896..900 -> "f"
        in 906..938 -> "g"
        else -> null
    }

    needHookClass2?.hook {
        injectMember {
            method {
                name = needHookMethod2!!
                param("com.qidian.QDReader.repository.entity.BookLastPage".toClass())
                returnType = UnitType
            }
            afterHook {
                setBookLastPage(
                    obj = args[0],
                    shieldAlsoRead = shieldAlsoRead,
                    shieldRecommendation = shieldRecommendation,
                    shieldSimilarRecommend = shieldSimilarRecommend,
                    hideAlsoRead = hideAlsoRead,
                    hideRecommendation = hideRecommendation,
                    hideBookList = hideBookList,
                    hideSimilarRecommend = hideSimilarRecommend,
                    hideTryRead = hideTryRead,
                    hideCircle = hideCircle
                )
            }
        }
    }

    val needHookClass3 = when (versionCode) {
        in 896..938 -> "com.qidian.QDReader.ui.view.lastpage.LastPageTryReadViewWrap"
        else -> null
    }
    val needHookMethod3 = when (versionCode) {
        in 896..938 -> "bind"
        else -> null
    }
    needHookClass3?.hook {
        injectMember {
            method {
                name = needHookMethod3!!
                param("com.qidian.QDReader.repository.entity.BookLastPage".toClass())
                returnType = UnitType
            }
            afterHook {
                setBookLastPage(
                    obj = args[0],
                    shieldAlsoRead = shieldAlsoRead,
                    shieldRecommendation = shieldRecommendation,
                    shieldSimilarRecommend = shieldSimilarRecommend,
                    hideAlsoRead = hideAlsoRead,
                    hideRecommendation = hideRecommendation,
                    hideBookList = hideBookList,
                    hideSimilarRecommend = hideSimilarRecommend,
                    hideTryRead = hideTryRead,
                    hideCircle = hideCircle
                )
            }
        }
    }
    when (versionCode) {
        in 896..950 -> {
            if (hideAdView) {
                findClass("com.qidian.QDReader.ui.activity.BookLastPageNewActivity").hook {
                    injectMember {
                        method {
                            name = "updateADView"
                            paramCount(1)
                            returnType = UnitType
                        }
                        intercept()
                    }
                }
            }
        }

        else -> "阅读页最后一页".printlnNotSupportVersion(versionCode)
    }

}

/**
 * 处理传入的对象
 * @param obj 传入的对象
 * @param shieldAlsoRead 是否屏蔽读过的书
 * @param shieldRecommendation 是否屏蔽推荐
 * @param hideAlsoRead 是否隐藏读过的书
 * @param hideRecommendation 是否隐藏推荐
 * @param hideBookList 是否隐藏书单
 * @param shieldSimilarRecommend 是否屏蔽相似推荐
 * @param hideSimilarRecommend 是否隐藏相似推荐
 * @param hideTryRead 是否隐藏试读
 * @param hideCircle 是否隐藏圈子
 */
private fun setBookLastPage(
    obj: Any?,
    shieldAlsoRead: Boolean = false,
    shieldRecommendation: Boolean = false,
    shieldSimilarRecommend: Boolean = false,
    hideAlsoRead: Boolean = true,
    hideRecommendation: Boolean = true,
    hideBookList: Boolean = true,
    hideSimilarRecommend: Boolean = true,
    hideTryRead: Boolean = true,
    hideCircle: Boolean = true,
) {

    if (shieldAlsoRead) {
        val alsoReadList = obj?.getParam<MutableList<*>>("alsoReadList")
        alsoReadList?.let {
            obj.setParam("alsoReadList", HookEntry.parseNeedShieldList(alsoReadList))
        }
    }
    if (hideAlsoRead) {
        obj?.setParam("alsoReadList", null)
    }
    if (shieldRecommendation) {
        val recommendList = obj?.getParam<MutableList<*>>("recommendList")
        recommendList?.let {
            obj.setParam("recommendList", HookEntry.parseNeedShieldList(recommendList))
        }
    }
    if (hideRecommendation) {
        obj?.setParam("recommendList", null)
    }
    if (shieldSimilarRecommend) {
        val similarRecommend = obj?.getParam<Any>("similarRecommend")
        val bookList = similarRecommend?.getParam<MutableList<*>>("bookList")
        bookList?.let {
            similarRecommend.setParam("bookList", HookEntry.parseNeedShieldList(bookList))
        }
    }
    if (hideSimilarRecommend) {
        obj?.setParam("similarRecommend", null)
    }
    if (hideBookList) {
        obj?.setParam("relatedBookList", null)
    }
    if (hideTryRead) {
        obj?.setParam("tryReadInfo", null)
    }
    if (hideCircle) {
        val bookCircleInfo = obj?.getParam<Any>("bookCircleInfo")
        bookCircleInfo?.setParam("enableCircle", 0)
    }


}


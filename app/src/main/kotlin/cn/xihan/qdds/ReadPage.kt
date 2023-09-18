package cn.xihan.qdds

import android.content.Context
import android.os.Environment
import android.view.View
import android.widget.ImageView
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
        in 944..950 -> "a6.c"
        958 -> "y5.c"
        970 -> "w5.c"
        in 980..1020 -> "kc.a"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 827..878 -> "G"
        in 884..1020 -> "C"
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
            replaceTo(themePath)
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
    if (enableShowReaderPageChapterSaveRawPictures && versionCode in 868..1099) {
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
        /**
         * com.qidian.QDReader.ui.adapter.reader.ChapterParagraphCommentAdapter.onBindContentItemViewHolder
         * b00.A(newParagraphCommentListBean$DataListBean0, this.getMBookInfo());
         */
        val needHookClass = when (versionCode) {
            in 868..878 -> "com.qidian.QDReader.ui.viewholder.chaptercomment.list.b0"
            884 -> "com.qidian.QDReader.ui.viewholder.chaptercomment.list.y"
            in 890..944 -> "com.qidian.QDReader.ui.viewholder.chaptercomment.list.e0"
            950 -> "com.qidian.QDReader.ui.viewholder.chaptercomment.list.m0"
            in 958..980 -> "com.qidian.QDReader.ui.viewholder.chaptercomment.list.e0"
            in 994..1020 -> "com.qidian.QDReader.ui.viewholder.chaptercomment.list.l0"
            else -> null
        }
        val needHookMethod = when (versionCode) {
            in 868..878 -> "A"
            884 -> "x"
            in 890..980 -> "z"
            in 994..1020 -> "K"
            else -> null
        }
        val needHookClass2 = when (versionCode) {
            in 970..980 -> "com.qidian.QDReader.ui.viewholder.chaptercomment.list.m0"
            in 994..1020 -> "com.qidian.QDReader.ui.viewholder.chaptercomment.list.t0"
            else -> null
        }
        val needHookMethod2 = when (versionCode) {
            in 970..980 -> "z"
            in 994..1020 -> "I"
            else -> null
        }
        if (needHookClass == null || needHookMethod == null) {
            "阅读页-章评相关复制".printlnNotSupportVersion(versionCode)
        } else {
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
                            val imageViews = instance.getViews<ImageView>()
                            if (!rawImgUrl.isNullOrBlank() || imageViews.isNotEmpty()) {
                                imageViews.filter { "app:id/image" in it.toString() }.takeIf { it.isNotEmpty() }?.first()?.setOnLongClickListener { imageView ->
                                    imageView.context.alertDialog {
                                        title = "图片地址"
                                        message = rawImgUrl
                                        positiveButton("复制") {
                                            imageView.context.copyToClipboard(rawImgUrl)
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
                        }

                        if (enableCopyReaderPageChapterComment) {
                            val messageTextView =
                                "com.qd.ui.component.widget.textview.MessageTextView".toClass()
                            val textViews = instance.getViews(messageTextView)
                            if (textViews.isNotEmpty()) {
                                textViews.forEach { any ->
                                    val textView = any.safeCast<TextView>()
                                    textView?.setOnLongClickListener {
                                        textView.context.alertDialog {
                                            title = "评论内容"
                                            message = textView.text.toString()
                                            positiveButton("复制") {
                                                textView.context.copyToClipboard(textView.text.toString())
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
                            }
                        }

                    }
                }
            }

            needHookClass2?.hook {
                injectMember {
                    method {
                        name = needHookMethod2!!
                        paramCount(2)
                        returnType = UnitType
                    }
                    afterHook {
                        if (enableShowReaderPageChapterSavePictureDialog) {
                            val rawImgUrl =
                                args[0]?.toJSONString().parseObject().getString("imageDetail")
                            val imageViews = instance.getViews<ImageView>()
                            if (!rawImgUrl.isNullOrBlank() || imageViews.isNotEmpty()) {
                                imageViews.filter { "app:id/image" in it.toString() }.takeIf { it.isNotEmpty() }?.first()?.setOnLongClickListener { imageView ->
                                    imageView.context.alertDialog {
                                        title = "图片地址"
                                        message = rawImgUrl
                                        positiveButton("复制") {
                                            imageView.context.copyToClipboard(rawImgUrl)
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
                        }

                        if (enableCopyReaderPageChapterComment) {
                            val messageTextView =
                                "com.qd.ui.component.widget.textview.MessageTextView".toClass()
                            val textViews = instance.getViews(messageTextView)
                            if (textViews.isNotEmpty()) {
                                textViews.forEach { any ->
                                    val textView = any.safeCast<TextView>()
                                    textView?.setOnLongClickListener {
                                        textView.context.alertDialog {
                                            title = "评论内容"
                                            message = textView.text.toString()
                                            positiveButton("复制") {
                                                textView.context.copyToClipboard(textView.text.toString())
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
                            }
                        }
                    }
                }
            }
        }
    }

    if (enableShowReaderPageChapterSaveAudioDialog && versionCode in 890..1099) {
        when (versionCode) {
            in 890..1099 -> {
                findClass("com.qidian.QDReader.ui.view.chapter_review.VoicePlayerView").hook {
                    injectMember {
                        method {
                            name = "p"
                            emptyParam()
                            returnType = UnitType
                        }
                        afterHook {
                            val relativeLayouts =
                                instance.getViews(
                                    "com.qd.ui.component.widget.roundwidget.QDUIRoundRelativeLayout".toClass(),
                                    isSuperClass = true
                                )
                            val strings = instance.getParamList<String>().filter { it.isNotBlank() }

                            if ((strings.isNotEmpty() && strings.size == 2) && relativeLayouts.isNotEmpty()) {
                                relativeLayouts.forEach {
                                    (it as View).setOnLongClickListener { view ->
                                        view.context.audioExportDialog(strings[0], strings[1])
                                        true
                                    }
                                }
                            }

                        }
                    }
                }
            }

            else -> "音频导出".printlnNotSupportVersion(versionCode)
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
        in 932..944 -> "sf.search"
        950 -> "uf.search"
        958 -> "rf.search"
        970 -> "tf.search"
        980 -> "xg.search"
        in 994..1020 -> "yg.search"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 868..878 -> "d"
        in 884..1020 -> "a"
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
                val list = result.safeCast<MutableList<*>>()
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
fun Context.audioExportDialog(networkUrl: String, filePath: String) {
    val file = File(filePath)
    if (!file.exists()) {
        toast("音频文件不存在")
        return
    }
    val linearLayout = CustomLinearLayout(context = this)
    val textView = CustomTextView(
        context = this,
        mText = "音频文件网络地址: $networkUrl\n音频文件本地地址: $filePath",
    )
    val editText = CustomEditText(
        context = this,
        mHint = "输入要保存的文件名",
        value = file.name,
    )
    linearLayout.apply {
        addView(editText)
        addView(textView)
    }
    alertDialog {
        title = "导出文件\n输入要保存的文件名"
        customView = linearLayout
        positiveButton("本地导出") {
            val fileName = "${editText.editText.text}.m4a"
            if (fileName.isBlank()) {
                toast("文件名不能为空")
                return@positiveButton
            }
            val saveFile = File(
                "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/QDReader/Audio",
                fileName
            ).apply {
                parentFile?.mkdirs()
            }
            if (saveFile.exists()) {
                toast("文件已存在")
                return@positiveButton
            }
            file.copyTo(saveFile)
            toast("导出成功")
            it.dismiss()
        }
        negativeButton("复制网络地址") {
            copyToClipboard(networkUrl)
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
        in 896..1020 -> "com.qidian.QDReader.ui.view.lastpage.LastPageRoleView"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 896..1020 -> "l"
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
        in 896..1020 -> "com.qidian.QDReader.ui.view.lastpage.LastPageCircleView"
        else -> null
    }
    val needHookMethod2 = when (versionCode) {
        in 896..900 -> "f"
        in 906..1020 -> "g"
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
        in 896..1020 -> "com.qidian.QDReader.ui.view.lastpage.LastPageTryReadViewWrap"
        else -> null
    }
    val needHookMethod3 = when (versionCode) {
        in 896..1020 -> "bind"
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
        in 896..1099 -> {
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


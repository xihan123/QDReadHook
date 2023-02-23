package cn.xihan.qdds

import android.os.Environment
import android.widget.ImageView
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType

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
        872 -> "z5.f"
        else -> null
    }
    needHookClass?.hook {
        injectMember {
            method {
                name = "G"
                paramCount(1)
                returnType = StringClass
            }
            afterHook {
                result =
                    "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/QDReader/ReaderTheme/"
            }
        }
    } ?: "自定义阅读页背景路径".printlnNotSupportVersion(versionCode)
}

/**
 * 阅读页-章评图片
 */
fun PackageParam.readerPageChapterReviewPictures(
    versionCode: Int,
    enableShowReaderPageChapterSaveRawPictures: Boolean = false,
    enableShowReaderPageChapterSavePictureDialog: Boolean = false,
) {
    when (versionCode) {
        in 868..872 -> {
            if (enableShowReaderPageChapterSaveRawPictures) {
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

            if (enableShowReaderPageChapterSavePictureDialog) {
                findClass("com.qidian.QDReader.ui.viewholder.chaptercomment.list.b0").hook {
                    injectMember {
                        method {
                            name = "A"
                            paramCount(2)
                            returnType = UnitType
                        }
                        afterHook {
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
        872 -> "qf.a"
        else -> null
    }
    needHookClass?.hook {
        injectMember {
            method {
                name = "d"
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
    } ?: "阅读时间加倍".printlnNotSupportVersion(versionCode)

}
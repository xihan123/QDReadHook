package cn.xihan.qdds

import cn.xihan.qdds.Option.optionEntity
import cn.xihan.qdds.Option.splashPath
import cn.xihan.qdds.Option.updateOptionEntity
import com.highcapable.yukihookapi.hook.factory.constructor
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.BitmapClass
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType


/**
 * 自定义启动图
 * @since 7.9.306-1030 ~ 1199
 * @param versionCode 版本号
 */
fun PackageParam.customStartImage(versionCode: Int) {
    when (versionCode) {
        in 1030..1199 -> {
            "com.qidian.QDReader.repository.entity.config.AppConfigBean".toClass().method {
                name = "getBootWallPapers"
                emptyParam()
                returnType =
                    "com.qidian.QDReader.repository.entity.config.BootWallPapers".toClass()
            }.hook().before {
                val bootWallPapers = instance.getParam<Any>("bootWallPapers")
                bootWallPapers?.let {
                    var papers = it.getParam<MutableList<Any?>>("papers")
                    if (!papers.isNullOrEmpty()) {
                        val copyPapers = papers
                        papers.first()?.let { page ->
                            if (page::class.java.name == "com.qidian.QDReader.repository.entity.config.Paper") {
                                val officialLaunchMapList =
                                    optionEntity.startImageOption.officialLaunchMapList.filter { it1 -> it1.isUsed }
                                if (officialLaunchMapList.isNotEmpty()) {
                                    officialLaunchMapList.forEach { startImageModel ->
                                        val copyPage = page::class.java.constructor {
                                            param(LongType, StringClass, LongType, IntType)
                                        }.get().call(
                                            startImageModel.paperId,
                                            startImageModel.imageUrl,
                                            1674986638000,
                                            1
                                        )
                                        copyPapers += copyPage
                                    }
                                }

                                val customStartImageUrlList =
                                    optionEntity.startImageOption.customStartImageUrlList

                                if (customStartImageUrlList.isNotEmpty()) {
                                    customStartImageUrlList.forEachIndexed { index, s ->
                                        val copyPage = page::class.java.constructor {
                                            param(LongType, StringClass, LongType, IntType)
                                        }.get().call(
                                            (12345 + index).toLong(),
                                            s,
                                            1674986638000,
                                            1
                                        )
                                        copyPapers += copyPage
                                    }
                                }

                                if (copyPapers.isNotEmpty()) {
                                    papers = copyPapers
                                }
                            }
                        }
                    }
                }
            }


        }

        else -> "自定义启动图".printlnNotSupportVersion(versionCode)
    }

}

/**
 * 获取官方启动图列表
 * 开启后去启动图页面滑倒底部，后续关闭该功能
 * @since 7.9.306-1030 ~ 1199
 * @param [versionCode] 版本代码
 */
fun PackageParam.captureTheOfficialLaunchMapList(versionCode: Int) {
    when (versionCode) {
        in 1030..1199 -> {
            "com.qidian.QDReader.ui.activity.splash_config.QDSplashConfigFragment".toClass()
                .method {
                    name = "loadData"
                    paramCount(2)
                    returnType = UnitType
                }.hook().after {
                    val dataList = instance.getParam<ArrayList<*>>("dataList")
                    val startImageList =
                        optionEntity.startImageOption.officialLaunchMapList.toMutableList()
                    dataList?.forEach { item ->
                        item?.let {
                            val imageUrl = item.getParam<String>("imageUrl")
                            val paperId = item.getParam<Long>("paperId")
                            val name = item.getParam<String>("name")
                            val preImageUrl = item.getParam<String>("preImageUrl")
                            if (imageUrl != null && paperId != null && name != null && preImageUrl != null) {
                                startImageList.find { it1 -> it1.paperId == paperId }
                                    ?.let { startImage ->
                                        startImage.name = name
                                        startImage.imageUrl = imageUrl
                                        startImage.preImageUrl = preImageUrl
                                    } ?: startImageList.plusAssign(
                                    StartImageModel(
                                        name = name,
                                        imageUrl = imageUrl,
                                        preImageUrl = preImageUrl,
                                        paperId = paperId
                                    )
                                )
                            }
                        }
                    }

                    optionEntity.startImageOption.officialLaunchMapList =
                        startImageList
                    updateOptionEntity()
                }
        }

        else -> "抓取官方启动图列表".printlnNotSupportVersion(versionCode)
    }

}

/**
 * 自定义本地启动图
 * @since 7.9.306-1030 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.customLocalStartImage(versionCode: Int) {
    when (versionCode) {
        in 1030..1199 -> {
            val list = listOf(
                "com.qidian.QDReader.ui.activity.SplashActivity\$a",
                "com.qidian.QDReader.ui.activity.SplashActivity\$judian",
                "com.qidian.QDReader.ui.activity.SplashActivity\$cihai"
            )
            list.forEach {
                it.toClass().method {
                    name = "onSuccess"
                    param(BitmapClass)
                    returnType = UnitType
                }.hook().before {
                    randomBitmap()?.let { bitmap ->
                        args(0).set(bitmap)
                    }
                }
            }

            "com.qidian.QDReader.util.SplashDownloadUtil".toClass().method {
                name = "cihai"
                emptyParam()
                returnType = StringClass
            }.hook().replaceTo(splashPath)

        }
    }
}
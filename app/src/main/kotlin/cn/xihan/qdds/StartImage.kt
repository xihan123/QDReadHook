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
 * # 自定义启动图
 * * 填入网络图片直链
 *
 *      ps:可填写自定义的图片直链地址
 *
 *      ps2:使用英文的分号(引号内的符号)";"分隔
 *
 * @since 7.9.334-1196 ~ 1299
 * @param versionCode 版本号
 */
fun PackageParam.customStartImage(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
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
 * # 抓取官方启动图
 * * 启用后到 我的装扮->启动屏幕 滑动到最底部 然后回到管理页面查看
 *
 *      ps:如果没有记得检查存储权限并重启起点/模块后再看
 *
 *      ps2:如需查看所有官方图片地址可在配置文件中找到
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.captureTheOfficialLaunchMapList(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
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
 * # 自定义本地启动图
 * * 启用后放置于/storage/emulated/0/Download/QDReader/Splash
 * @since 7.9.334-1196 ~ 1099
 * @param [versionCode] 版本代码
 */
fun PackageParam.customLocalStartImage(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            val list = listOf(
                "com.qidian.QDReader.ui.activity.SplashActivity\$a",
                "com.qidian.QDReader.ui.activity.SplashActivity\$cihai"
            )
            list.forEach {
                it.toClassOrNull()?.method {
                    name = "onSuccess"
                    param(BitmapClass)
                    returnType = UnitType
                }?.hook()?.before {
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
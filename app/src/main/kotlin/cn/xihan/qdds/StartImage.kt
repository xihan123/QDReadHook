package cn.xihan.qdds


import cn.xihan.qdds.HookEntry.Companion.optionEntity
import com.alibaba.fastjson2.toJSONString
import com.highcapable.yukihookapi.hook.factory.constructor
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2023/1/29 18:50
 * @介绍 :
 */
/**
 * 自定义启动图
 * @param versionCode 版本号
 */
fun PackageParam.customStartImage(versionCode: Int) {
    when (versionCode) {
        in 860..872 -> {
            findClass("com.qidian.QDReader.repository.entity.config.AppConfigBean").hook {
                injectMember {
                    method {
                        name = "getBootWallPapers"
                        emptyParam()
                        returnType =
                            "com.qidian.QDReader.repository.entity.config.BootWallPapers".toClass()
                    }
                    beforeHook {
                        val bootWallPapers = instance.getParam<Any>("bootWallPapers")
                        bootWallPapers?.let {
                            var papers = it.getParam<MutableList<Any?>>("papers")
                            if (!papers.isNullOrEmpty()) {
                                /*
                                val iterator = papers.iterator()
                                while (iterator.hasNext()) {
                                    val item = iterator.next().toJSONString()
                                    loggerE(msg = "paper: $item")
                                }

                                 */
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
            }

        }

        else -> "自定义启动图".printlnNotSupportVersion(versionCode)
    }

}

/**
 * 抓取官方启动图列表
 */
fun PackageParam.captureTheOfficialLaunchMapList(versionCode: Int) {
    when (versionCode) {
        in 860..872 -> {
            findClass("com.qidian.QDReader.ui.activity.splash_config.QDSplashConfigFragment").hook {
                injectMember {
                    method {
                        name = "loadData"
                        paramCount(2)
                        returnType = UnitType
                    }
                    afterHook {
                        safeRun {
                            val dataList = instance.getParam<ArrayList<*>>("dataList")
                            val startImageList =
                                optionEntity.startImageOption.officialLaunchMapList
                            dataList?.forEach { item ->
                                item?.let {
                                    val text = item.toJSONString()
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
                                            OptionEntity.StartImageOption.StartImageModel(
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
                }
            }
        }

        else -> "抓取官方启动图列表".printlnNotSupportVersion(versionCode)
    }

}

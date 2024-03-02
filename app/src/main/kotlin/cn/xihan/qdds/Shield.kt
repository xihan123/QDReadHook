package cn.xihan.qdds

import android.content.Context
import android.view.View
import android.widget.TextView
import cn.xihan.qdds.Option.isNeedShield
import cn.xihan.qdds.Option.parseNeedShieldComicList
import cn.xihan.qdds.Option.parseNeedShieldList
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.ArrayListClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.JSONArrayClass
import com.highcapable.yukihookapi.hook.type.java.ListClass
import com.highcapable.yukihookapi.hook.type.java.UnitType
import org.luckypray.dexkit.DexKitBridge
import java.lang.reflect.Modifier

/**
 * 屏蔽选项
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 * @param [configurations] 配置
 * @suppress Generate Documentation
 */
fun PackageParam.shieldOption(
    versionCode: Int,
    configurations: List<SelectedModel>,
    bridge: DexKitBridge
) {
    configurations.filter { it.selected }.takeIf { it.isNotEmpty() }?.forEach { selected ->
        when (selected.title) {
            "精选-主页面" -> shieldMainPage(versionCode)
            "精选-分类" -> shieldCategory(versionCode, bridge)
            "精选-分类-全部作品" -> shieldCategoryAllBook(versionCode, bridge)
            "精选-免费-免费推荐" -> shieldFreeRecommend(versionCode, bridge)
            "精选-免费-新书入库" -> shieldFreeNewBook(versionCode)
            "精选-畅销精选、主编力荐等更多" -> shieldHotAndRecommend(versionCode, bridge)
            "精选-新书强推、三江推荐" -> shieldNewBookAndRecommend(versionCode, bridge)
            "精选-排行榜" -> shieldBookRank(versionCode)
            "精选-新书" -> shieldNewBook(versionCode)
            "每日导读" -> shieldDailyReading(versionCode, bridge)
            "分类-小编力荐、本周强推等更多" -> shieldCategoryBookListReborn(versionCode)
            "精选-漫画" -> shieldComic(versionCode)
            "精选-漫画-其他" -> shieldComicOther(versionCode, bridge)
        }
    }
}

/**
 * 屏蔽每日导读
 * @since 7.9.334-1196
 * @param [versionCode] 版本代码
 */
fun PackageParam.shieldDailyReading(
    versionCode: Int, bridge: DexKitBridge
) {
    when (versionCode) {
        in 1196..1299 -> {

            bridge.apply {
                findClass {
                    searchPackages = listOf("com.qidian.QDReader.ui.adapter")
                    matcher {
                        usingStrings = listOf(
                            "DailyReadingClosedAd", "PositionMark"
                        )
                    }
                }.firstNotNullOfOrNull { classData ->
                    classData.findMethod {
                        matcher {
                            modifiers = Modifier.PUBLIC
                            paramTypes = listOf("java.util.ArrayList")
                            returnType = "void"
                        }
                    }.firstNotNullOfOrNull { methodData ->
                        shieldUnit(
                            className = methodData.className,
                            methodName = methodData.methodName,
                            paramCount = methodData.paramTypeNames.size
                        )
                    }
                }

                /*
                findClass {
                    searchPackages = listOf("com.qidian.QDReader.flutter")
                    matcher {

                        addMethod {
                            name = "onSuccess"
                            paramTypes = listOf(
                                "java.util.ArrayList"
                            )
                            returnType = "void"
                            usingStrings = listOf(
                                "dailyReadingDataCacheChange"
                            )
                        }

                        usingStrings = listOf(
                            "dailyReadingDataCacheChange"
                        )
                    }
                }.firstNotNullOfOrNull { classData ->
                    "classData: $classData".loge()
                    classData.findMethod {
                        matcher {
                            name = "onSuccess"
                            paramTypes = listOf(
                                "java.util.ArrayList"
                            )
                            returnType = "void"
                            usingStrings = listOf(
                                "dailyReadingDataCacheChange"
                            )
                        }
                    }.firstNotNullOfOrNull { methodData ->
                        shieldUnit(
                            className = methodData.className,
                            methodName = methodData.methodName,
                            paramCount = methodData.paramTypeNames.size
                        )
                    }
                }

                 */
            }
        }

        else -> "屏蔽每日导读".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽精选主页面
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.shieldMainPage(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            shieldResult(
                className = "com.qidian.QDReader.repository.entity.BookListData",
                methodName = "getItems"
            )
        }

        else -> "屏蔽精选主页面".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽精选-分类
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.shieldCategory(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1196..1299 -> {

            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.ui.adapter")
                matcher {
                    usingStrings = listOf(
                        "QDBookCategoryActivity", "fenleicategory"
                    )
                }
            }.firstNotNullOfOrNull { classData ->
                classData.findMethod {
                    matcher {
                        modifiers = Modifier.PUBLIC
                        paramTypes = listOf("int", "java.util.List")
                        returnType = "void"
                    }
                }.firstNotNullOfOrNull { methodData ->
                    methodData.className.toClass().method {
                        name = methodData.methodName
                        paramCount(methodData.paramTypeNames.size)
                        returnType = UnitType
                    }.hook().before {
                        val list = args[0].safeCast<List<*>>() ?: return@before
                        val iterator = list.iterator()
                        while (iterator.hasNext()) {
                            runAndCatch {
                                iterator.next()?.getParam<MutableList<*>>("categoryItems")
                                    ?.let {
                                        parseNeedShieldList(it)
                                    }
                            }
                        }
                        args(0).set(list)
                    }
                }
            }

            shieldResult(
                className = "com.qidian.morphing.MorphingWidgetData", methodName = "getList"
            )
        }

        else -> "屏蔽精选-分类".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽精选-分类-全部作品
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.shieldCategoryAllBook(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1196..1299 -> {

            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.ui.fragment")
                matcher {
                    methods {
                        add {
                            modifiers = Modifier.PUBLIC
                            paramTypes = listOf("int", "java.util.ArrayList")
                            returnType = "void"
                        }
                    }
                    usingStrings = listOf(
                        "errorMessage", "serverBookList"
                    )
                }
            }.firstNotNullOfOrNull { classData ->
                classData.findMethod {
                    matcher {
                        modifiers = Modifier.PUBLIC
                        paramTypes = listOf("int", "java.util.ArrayList")
                        returnType = "void"
                        usingStrings = listOf("serverBookList")
                    }
                }.firstNotNullOfOrNull { methodData ->
                    shieldUnit(
                        className = methodData.className,
                        methodName = methodData.methodName,
                        paramCount = methodData.paramTypeNames.size,
                        index = 1
                    )
                }
            }
        }

        else -> "屏蔽精选-分类-全部作品".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽精选-免费-免费推荐
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.shieldFreeRecommend(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1196..1299 -> {

            bridge.findClass {
                excludePackages = listOf("com")
                matcher {
                    methods {
                        add {
                            modifiers = Modifier.PUBLIC
                            paramTypes = listOf(
                                "com.qidian.QDReader.repository.entity.BookStoreDynamicItem",
                                "int",
                                "int"
                            )
                            returnType = "void"
                        }
                    }
                }
            }.firstNotNullOfOrNull { classData ->
                classData.findMethod {
                    matcher {
                        modifiers = Modifier.PUBLIC
                        paramTypes = listOf(
                            "com.qidian.QDReader.repository.entity.BookStoreDynamicItem",
                            "int",
                            "int"
                        )
                        returnType = "void"
                    }
                }.firstNotNullOfOrNull { methodData ->
                    methodData.className.toClass().method {
                        name = methodData.methodName
                        paramCount(methodData.paramTypeNames.size)
                        returnType = UnitType
                    }.hook().before {
                        args[0]?.getParam<ArrayList<*>>("BookList")?.let {
                            parseNeedShieldList(it)
                        }
                    }
                }
            }
        }

        else -> "屏蔽精选-免费-免费推荐".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽精选-新书
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.shieldNewBook(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.adapter.newbook.BookTagViewHolder".toClass().method {
                name = "updateUI"
                param("com.qidian.QDReader.repository.entity.newbook.NewBookCard".toClass())
                returnType = UnitType
            }.hook().before {
                args[0]?.let {
                    runAndCatch {
                        val goldRecBean = it.getParam<Any>("goldRecBean")
                        goldRecBean?.let {
                            val items = goldRecBean.getParam<MutableList<*>>("items")
                            items?.let {
                                parseNeedShieldList(items)
                            }
                        }
                        val bookShortageBean = it.getParam<Any>("bookShortageBean")
                        bookShortageBean?.let {
                            val items = bookShortageBean.getParam<MutableList<*>>("items")
                            items?.let {
                                parseNeedShieldList(items)
                            }
                        }

                        val monthBannerListBean = it.getParam<Any>("monthBannerListBean")
                        monthBannerListBean?.let {
                            val items = monthBannerListBean.getParam<MutableList<*>>("items")
                            items?.let {
                                parseNeedShieldList(items)
                            }
                        }
                        val newBookAIRecommendBean = it.getParam<Any>("newBookAIRecommendBean")
                        newBookAIRecommendBean?.let {
                            val items = newBookAIRecommendBean.getParam<MutableList<*>>("items")
                            items?.let {
                                parseNeedShieldList(items)
                            }
                        }
                        val newBookRankBean = it.getParam<Any>("newBookRankBean")
                        newBookRankBean?.let {
                            val items = newBookRankBean.getParam<MutableList<*>>("items")
                            items?.let {
                                parseNeedShieldList(items)
                            }
                        }

                        val newBookTagBean = it.getParam<Any>("newBookTagBean")
                        newBookTagBean?.let {
                            val items = newBookTagBean.getParam<MutableList<*>>("items")
                            items?.let {
                                parseNeedShieldList(items)
                            }
                        }

                    }
                }
            }

            shieldResult(
                className = "com.qidian.QDReader.repository.entity.newbook.RecPageData",
                methodName = "getItems"
            )

        }

        else -> "屏蔽精选-新书".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽免费-新书入库
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.shieldFreeNewBook(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            val list = listOf(
                "com.qidian.QDReader.ui.fragment.QDNewBookInStoreFragment",
                "com.qidian.QDReader.ui.activity.QDNewBookInStoreActivity"
            )
            list.forEach { className ->
                className.toClass().method {
                    param(
                        className.toClass(),
                        "com.qidian.QDReader.repository.entity.NewBookInStore".toClass()
                    )
                    returnType = UnitType
                }.hook().before {
                    args[1]?.let {
                        val categoryIdList = it.getParam<MutableList<*>>("CategoryIdList")
                        val itemList = it.getParam<MutableList<*>>("ItemList")
                        categoryIdList?.let { list ->
                            parseNeedShieldList(list)
                        }
                        itemList?.let { list ->
                            parseNeedShieldList(list)
                        }
                    }
                }
            }

        }

        else -> "屏蔽免费-新书入库".printlnNotSupportVersion(versionCode)
    }

}

/**
 * 屏蔽畅销精选、主编力荐等更多
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.shieldHotAndRecommend(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1196..1299 -> {

            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.ui.adapter")
                matcher {
                    usingStrings = listOf(
                        "BookLastPageBookShortageActivity", "morebooklist", "booklist"
                    )
                }
            }.firstNotNullOfOrNull { classData ->
                classData.findMethod {
                    matcher {
                        modifiers = Modifier.PUBLIC
                        paramTypes = listOf(
                            "java.util.List"
                        )
                        returnType = "void"
                    }
                }.firstNotNullOfOrNull { methodData ->
                    shieldUnit(
                        className = methodData.className,
                        methodName = methodData.methodName,
                        paramCount = methodData.paramTypeNames.size
                    )
                }
            }
        }

        else -> "屏蔽畅销精选、主编力荐等更多".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽新书强推、三江推荐
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.shieldNewBookAndRecommend(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1196..1299 -> {

            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.ui.adapter")
                matcher {
                    methods {
                        add {
                            modifiers = Modifier.PUBLIC
                            name = "getItem"
                            paramTypes = listOf("int")
                            returnType = "com.qidian.QDReader.repository.entity.BookStoreItem"
                        }
                    }
                    usingStrings = listOf(" (", "-", ")")
                }
            }.filter { "QDTeenagerBookStoreAdapter" !in it.name }
                .firstNotNullOfOrNull { classData ->
                    classData.findMethod {
                        matcher {
                            modifiers = Modifier.PUBLIC
                            paramTypes = listOf(
                                "java.util.List"
                            )
                            returnType = "void"
                        }
                    }.filter { "setHeaderData" != it.className }
                        .firstNotNullOfOrNull { methodData ->
                            methodData.className.toClass().method {
                                name = methodData.methodName
                                paramCount = methodData.paramTypeNames.size
                                returnType = UnitType
                            }.hook().before {
                                args[0].safeCast<MutableList<*>>()?.let {
                                    runAndCatch {
                                        val iterator = it.iterator()
                                        while (iterator.hasNext()) {
                                            val item = iterator.next().toJSONString()
                                            val json = item.parseObject()
                                            val jb = json.getJSONObject("BookStoreItem")
                                            if (jb != null) {
                                                val authorName =
                                                    jb.getStringWithFallback("authorName")
                                                val bookName =
                                                    jb.getStringWithFallback("bookName")
                                                val categoryName =
                                                    jb.getStringWithFallback("categoryName")
                                                val array =
                                                    jb.getJSONArrayWithFallback("tagList")
                                                val bookTypeArray = mutableSetOf("")
                                                if (!categoryName.isNullOrBlank()) {
                                                    bookTypeArray += categoryName
                                                }
                                                if (!array.isNullOrEmpty()) {
                                                    for (i in array.indices) {
                                                        array += array.getString(i)
                                                    }
                                                }
                                                val isNeedShield = isNeedShield(
                                                    bookName = bookName,
                                                    authorName = authorName,
                                                    bookType = bookTypeArray
                                                )
                                                if (isNeedShield) {
                                                    iterator.remove()
                                                }
                                            }
                                        }
                                        args(0).set(it)
                                    }
                                }
                            }
                        }
                }

        }

        else -> "屏蔽新书强推、三江推荐".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽精选-排行榜
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.shieldBookRank(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            shieldResult(
                className = "com.qidian.QDReader.repository.entity.RankListData",
                methodName = "getRankBookList"
            )
        }

        else -> "屏蔽精选-排行榜".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽分类-小编力荐、本周强推等
 * @since 7.9.334-1196 ~ 1299
 *  @param [versionCode] 版本代码
 */
fun PackageParam.shieldCategoryBookListReborn(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.view.BookItemView".toClass().method {
                param(JSONArrayClass)
                returnType = ListClass
            }.hook().after {
                result.safeCast<ArrayList<*>>()?.let {
                    result = parseNeedShieldList(it)
                }
            }
        }

        else -> "屏蔽分类-小编力荐、本周强推等".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽-漫画
 *  @since 7.9.334-1196 ~ 1299
 *  @param [versionCode] 版本代码
 */
fun PackageParam.shieldComic(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.repository.entity.ComicSquareItem".toClass().method {
                name = "getComicSqureRecmdItems"
                emptyParam()
                returnType = ArrayListClass
            }.hook().after {
                result.safeCast<ArrayList<*>>()?.let {
                    result = parseNeedShieldComicList(it)
                }
            }
        }

        else -> "屏蔽-漫画".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽-漫画-其他
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.shieldComicOther(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1196..1299 -> {

            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.ui.adapter")
                matcher {
                    methods {
                        add {
                            modifiers = Modifier.PUBLIC
                            paramTypes = listOf("int")
                            returnType = "com.qidian.QDReader.repository.entity.ComicBookItem"
                        }
                    }
                }
            }.firstNotNullOfOrNull { classData ->
                classData.findMethod {
                    matcher {
                        modifiers = Modifier.PUBLIC
                        paramTypes = listOf(
                            "java.util.ArrayList"
                        )
                        returnType = "void"
                    }
                }.firstNotNullOfOrNull { methodData ->
                    methodData.className.toClass().method {
                        name = methodData.methodName
                        paramCount = methodData.paramTypeNames.size
                        returnType = UnitType
                    }.hook().before {
                        args[0].safeCast<MutableList<*>>()?.let {
                            args(0).set(parseNeedShieldComicList(it))
                        }
                    }
                }
            }
        }

        else -> "屏蔽-漫画-其他".printlnNotSupportVersion(versionCode)
    }

}

/**
 *  书籍详情-快速屏蔽
 *  @since 7.9.334-1196 ~ 1299
 *  @param [versionCode] 版本代码
 */
fun PackageParam.quickShield(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.activity.QDBookDetailActivity".toClass().method {
                param(
                    "com.qidian.QDReader.ui.activity.QDBookDetailActivity".toClass(),
                    BooleanType,
                    "com.qidian.QDReader.repository.entity.BookDetail".toClass()
                )
                returnType = UnitType
            }.hook().after {
                val viewMap =
                    args[0]?.getParam<Map<*, View>>("_\$_findViewCache") ?: return@after
                val ids = listOf("tvBookName", "tvAuthorName")
                val textViews =
                    viewMap.values.filterIsInstance<TextView>().filter { it.getName() in ids }
                val tvBookName = textViews.first { it.getName() == "tvBookName" }
                val tvAuthorName = textViews.first { it.getName() == "tvAuthorName" }
                tvBookName.setOnLongClickListener {
                    it.context.showQuickShieldDialog(
                        name = tvBookName.text.toString()
                    )
                    true
                }
                tvAuthorName.setOnLongClickListener {
                    it.context.showQuickShieldDialog(
                        author = tvAuthorName.text.toString()
                    )
                    true
                }
            }
        }

        else -> "书籍详情-快速屏蔽".printlnNotSupportVersion(versionCode)
    }
}

/**
 * # 显示快速屏蔽对话框
 * * 书籍详情页长按作者名或者书名即可弹出屏蔽框
 *
 *          ps: 书名和作者名挨在一起的位置
 * @since 7.9.334-1196
 * @param [name] 名称
 * @param [author] 著者
 */
fun Context.showQuickShieldDialog(
    name: String = "", author: String = ""
) {
    if (name.isNotBlank()) {
        val editText = CustomEditText(
            context = this, hint = "输入书名关键词", value = name
        )
        alertDialog {
            title = "编辑需要屏蔽的书名关键词?"
            customView = editText
            okButton {
                Option.addShieldBook(bookName = editText.editText.text.toString())
            }
            cancelButton {
                it.dismiss()
            }
            build()
            show()
        }

    }

    if (author.isNotBlank()) {
        alertDialog {
            title = "确认屏蔽该作者?"
            message = "作者: $author"
            okButton {
                Option.addShieldBook(authorName = author)
            }
            cancelButton {
                it.dismiss()
            }
            build()
            show()
        }
    }

}

package cn.xihan.qdds

import android.content.Context
import android.view.View
import android.widget.TextView
import cn.xihan.qdds.HookEntry.Companion.isNeedShield
import cn.xihan.qdds.HookEntry.Companion.parseNeedShieldList
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.ContextClass
import com.highcapable.yukihookapi.hook.type.java.ArrayListClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.JSONArrayClass
import com.highcapable.yukihookapi.hook.type.java.ListClass
import com.highcapable.yukihookapi.hook.type.java.UnitType

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2022/8/28 16:11
 * @介绍 :
 */

/**
 * 屏蔽选项
 * @param versionCode 版本号
 * @param configurations 屏蔽选项列表
 */
fun PackageParam.shieldOption(
    versionCode: Int,
    configurations: List<OptionEntity.SelectedModel>
) {
    configurations.filter { it.selected }.takeIf { it.isNotEmpty() }?.forEach { selected ->
        when (selected.title) {
            "精选-主页面" -> shieldChoice(versionCode)
            "精选-分类" -> shieldCategory(versionCode)
            "精选-分类-全部作品" -> shieldCategoryAllBook(versionCode)
            "精选-免费-免费推荐" -> shieldFreeRecommend(versionCode)
            "精选-免费-新书入库" -> shieldFreeNewBook(versionCode)
            "精选-畅销精选、主编力荐等更多" -> shieldHotAndRecommend(versionCode)
            "精选-新书强推、三江推荐" -> shieldNewBookAndRecommend(versionCode)
            "精选-排行榜" -> shieldBookRank(versionCode)
            "精选-新书" -> shieldNewBook(versionCode)
            "每日导读" -> shieldDailyReading(versionCode)
            "精选-漫画" -> shieldComic(versionCode)
            "精选-漫画-其他" -> shieldComicOther(versionCode)
            "分类-小编力荐、本周强推等更多" -> shieldCategoryBookListReborn(versionCode)
        }
    }
}

/**
 * 屏蔽每日导读指定的书籍
 */
fun PackageParam.shieldDailyReading(
    versionCode: Int,
) {
    /**
     * 上级调用:com.qidian.QDReader.ui.activity.DailyReadingActivity.onCreate bindView()
     */
    val needHookClass = when (versionCode) {
        in 788..800 -> "com.qidian.QDReader.component.api.b1"
        in 804..812 -> "com.qidian.QDReader.component.api.f1"
        in 827..860 -> "com.qidian.QDReader.component.api.h1"
        in 860..878 -> "com.qidian.QDReader.component.api.k1"
        in 884..890 -> "com.qidian.QDReader.component.api.h1"
        in 896..970 -> "com.qidian.QDReader.component.api.i1"
        in 980..1020 -> "com.qidian.QDReader.component.api.j1"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 788..812 -> "j"
        in 827..878 -> "k"
        in 884..1020 -> "h"
        else -> null
    }
    if (needHookClass == null || needHookMethod == null) {
        "屏蔽每日导读".printlnNotSupportVersion(versionCode)
        return
    }
    needHookClass.hook {
        injectMember {
            method {
                name = needHookMethod
                emptyParam()
                returnType = ArrayListClass
            }

            afterHook {
                result.safeCast<ArrayList<*>>()?.let {
                    result = parseNeedShieldList(it)
                }
            }
        }
    }
}

/**
 * 屏蔽精选主页面
 */
fun PackageParam.shieldChoice(versionCode: Int) {
    when (versionCode) {
        in 788..1099 -> {
            /**
             * 精选主页面
             */
            findClass("com.qidian.QDReader.repository.entity.BookListData").hook {
                injectMember {
                    method {
                        name = "getItems"
                        returnType = ListClass
                    }
                    beforeHook {
                        val list = instance.getParam<MutableList<*>>("items")
                        list?.let {
                            safeRun {
                                result = parseNeedShieldList(it)
                            }
                        }
                    }
                }
            }
        }

        else -> "屏蔽精选主页面".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽精选-分类
 * 上级调用:com.qidian.QDReader.ui.activity.QDBookCategoryActivity  mRightAdapter
 */
fun PackageParam.shieldCategory(versionCode: Int) {
    when (versionCode) {
        in 788..808 -> {
            /**
             * 分类
             * 上级调用:com.qidian.QDReader.ui.adapter.x6.onBindContentItemViewHolder if(v1 == 2)
             */
            findClass("com.qidian.QDReader.ui.adapter.x6\$a").hook {
                injectMember {
                    constructor {
                        param(
                            "com.qidian.QDReader.ui.adapter.x6".toClass(),
                            ContextClass,
                            IntType,
                            ListClass
                        )
                    }
                    beforeHook {
                        args[3].safeCast<MutableList<*>>()?.let {
                            safeRun {
                                args(3).set(parseNeedShieldList(it))
                            }
                        }
                    }
                }
            }
        }

        812 -> {
            findClass("com.qidian.QDReader.ui.adapter.x6\$a").hook {
                injectMember {
                    constructor {
                        param(
                            "com.qidian.QDReader.ui.adapter.x6".toClass(),
                            ContextClass,
                            IntType,
                            ListClass
                        )
                    }
                    beforeHook {
                        args[3].safeCast<MutableList<*>>()?.let {
                            safeRun {
                                args(3).set(parseNeedShieldList(it))
                            }
                        }
                    }
                }
            }

            /**
             * 上级调用:com.qidian.QDReader.ui.fragment.LibraryFragment.loadData(boolean) : void
             */
            findClass("com.qidian.QDReader.ui.fragment.LibraryFragment\$a").hook {
                injectMember {
                    method {
                        name = "a"
                        param(IntType, ArrayListClass)
                        returnType = UnitType
                    }
                    beforeHook {
                        args[1].safeCast<MutableList<*>>()?.let {
                            safeRun {
                                args(1).set(parseNeedShieldList(it))
                            }
                        }
                    }
                }
            }
        }

        in 827..1020 -> {
            val needHookClass = when (versionCode) {
                827 -> "com.qidian.QDReader.ui.adapter.x6"
                834 -> "com.qidian.QDReader.ui.adapter.y6"
                in 842..860 -> "com.qidian.QDReader.ui.adapter.z6"
                in 868..878 -> "com.qidian.QDReader.ui.adapter.a7"
                in 884..890 -> "com.qidian.QDReader.ui.adapter.z6"
                in 896..924 -> "com.qidian.QDReader.ui.adapter.v6"
                in 932..958 -> "com.qidian.QDReader.ui.adapter.x6"
                970 -> "com.qidian.QDReader.ui.adapter.a7"
                in 980..1020 -> "com.qidian.QDReader.ui.adapter.z6"
                else -> null
            }
            val needHookMethod = when (versionCode) {
                in 827..878 -> "r"
                in 884..1020 -> "o"
                else -> null
            }
            if (needHookClass == null || needHookMethod == null) {
                "屏蔽精选-分类".printlnNotSupportVersion(versionCode)
            } else {
                needHookClass.hook {
                    injectMember {
                        method {
                            name = needHookMethod
                            param(IntType, ListClass)
                            returnType = UnitType
                        }
                        afterHook {
                            val b = instance.getParam<List<*>>("b")
                            b?.let {
                                safeRun {
                                    val bIterator = b.iterator()
                                    while (bIterator.hasNext()) {
                                        val next = bIterator.next()
                                        next?.let {
                                            val categoryItems =
                                                it.getParam<MutableList<*>>("categoryItems")
                                            categoryItems?.let {
                                                args(1).set(parseNeedShieldList(categoryItems))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            /**
             * 上级调用: com.qidian.morphing.MorphingAdapter.getItem -> MorphingCard -> MorphingWidgetData -> list
             */
            findClass("com.qidian.morphing.MorphingWidgetData").hook {
                injectMember {
                    method {
                        name = "getList"
                        emptyParam()
                        returnType = ListClass
                    }
                    afterHook {
                        result.safeCast<MutableList<*>>()?.let {
                            safeRun {
                                result = parseNeedShieldList(it)
                            }
                        }
                    }
                }
            }

        }

        else -> "屏蔽分类".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽精选-免费-免费推荐
 * 上级调用:com.qidian.QDReader.ui.fragment.QDBookStoreFragment.onViewInject
 * mAdapter
 * onBindContentItemViewHolder
 * if(this.getContentItemViewType(arg8) != 8)
 */
fun PackageParam.shieldFreeRecommend(versionCode: Int) {
    val freeRecommendHookClass = when (versionCode) {
        788 -> "la.a"
        in 792..808 -> "ka.a"
        812 -> "ia.a"
        827 -> "la.a"
        in 834..860 -> "ka.a"
        868 -> "ma.a"
        872 -> "ka.a"
        878 -> "ja.a"
        in 884..890 -> "ca.search"
        in 896..900 -> "da.search"
        in 906..916 -> "ha.search"
        924 -> "ia.search"
        in 932..938 -> "la.search"
        944 -> "ka.search"
        950 -> "ma.search"
        958 -> "ja.search"
        970 -> "ia.search"
        in 980..1020 -> "cb.search"
        else -> null
    }
    val freeRecommendHookMethod = when (versionCode) {
        in 788..878 -> "n"
        in 884..1020 -> "k"
        else -> null
    }
    if (freeRecommendHookClass == null || freeRecommendHookMethod == null) {
        "屏蔽免费-免费推荐".printlnNotSupportVersion(versionCode)
        return
    }
    freeRecommendHookClass.hook {
        injectMember {
            method {
                name = freeRecommendHookMethod
                param(
                    "com.qidian.QDReader.repository.entity.BookStoreDynamicItem".toClass(),
                    IntType,
                    IntType
                )
            }
            beforeHook {
                val item = args[0]?.getParam<ArrayList<*>>("BookList")
                item?.let {
                    safeRun {
                        parseNeedShieldList(it)
                    }
                }

            }
        }
    }
}

/**
 * 屏蔽精选-新书
 */
fun PackageParam.shieldNewBook(versionCode: Int) {
    when (versionCode) {
        in 792..843 -> {
            /**
             * 精选-新书
             */
            findClass("com.qidian.QDReader.repository.entity.newbook.NewBookCard").hook {
                injectMember {
                    method {
                        name = "buildData"
                        returnType = UnitType
                    }
                    afterHook {
                        safeRun {
                            val goldRecBean = instance.getParam<Any>("goldRecBean")
                            goldRecBean?.let {
                                val items = goldRecBean.getParam<MutableList<*>>("items")
                                items?.let {
                                    parseNeedShieldList(items)
                                }
                            }
                            val bookShortageBean =
                                instance.getParam<Any>("bookShortageBean")
                            bookShortageBean?.let {
                                val items = bookShortageBean.getParam<MutableList<*>>("items")
                                items?.let {
                                    parseNeedShieldList(items)
                                }
                            }

                            val monthBannerListBean =
                                instance.getParam<Any>("monthBannerListBean")
                            monthBannerListBean?.let {
                                val items =
                                    monthBannerListBean.getParam<MutableList<*>>("items")
                                items?.let {
                                    parseNeedShieldList(items)
                                }
                            }
                            val newBookAIRecommendBean =
                                instance.getParam<Any>("newBookAIRecommendBean")
                            newBookAIRecommendBean?.let {
                                val items =
                                    newBookAIRecommendBean.getParam<MutableList<*>>("items")
                                items?.let {
                                    parseNeedShieldList(items)
                                }
                            }
                            val newBookRankBean =
                                instance.getParam<Any>("newBookRankBean")
                            newBookRankBean?.let {
                                val items = newBookRankBean.getParam<MutableList<*>>("items")
                                items?.let {
                                    parseNeedShieldList(items)
                                }
                            }
                            /*
                            val newBookRecommendBean =
                                instance.getParam<Any>("newBookRecommendBean")

                            newBookRecommendBean?.let {
                                val items =
                                    getParam<MutableList<*>>(newBookRecommendBean, "items")
                                items?.let {
                                    val iterator = it.iterator()
                                    while (iterator.hasNext()) {
                                        val item = iterator.next().toJSONString()
                                        val jb = item.parseObject()
                                        val recData = jb.getJSONObject("recData")
                                        if (recData != null){
                                            val items = recData.getJSONArray("items") as? MutableList<*>
                                            items?.let {
                                                parseNeedShieldList(items)
                                            }
                                        }
                                    }
                                }
                            }

                             */
                            val newBookTagBean =
                                instance.getParam<Any>("newBookTagBean")
                            newBookTagBean?.let {
                                val items = newBookTagBean.getParam<MutableList<*>>("items")
                                items?.let {
                                    parseNeedShieldList(items)
                                }
                            }

                        }
                    }
                }
            }

            /**
             * 精选-新书-新书入库/新书强推
             */
            findClass("com.qidian.QDReader.repository.entity.newbook.RecPageData").hook {
                injectMember {
                    method {
                        name = "getItems"
                        returnType = ListClass
                    }
                    afterHook {
                        result.safeCast<MutableList<*>>()?.let {
                            parseNeedShieldList(it)
                        }
                    }
                }
            }
        }

        in 850..1099 -> {
            findClass("com.qidian.QDReader.ui.adapter.newbook.BookTagViewHolder").hook {
                injectMember {
                    method {
                        name = "updateUI"
                        param("com.qidian.QDReader.repository.entity.newbook.NewBookCard".toClass())
                        returnType = UnitType
                    }
                    beforeHook {
                        args[0]?.let {
                            safeRun {
                                val goldRecBean = it.getParam<Any>("goldRecBean")
                                goldRecBean?.let {
                                    val items = goldRecBean.getParam<MutableList<*>>("items")
                                    items?.let {
                                        parseNeedShieldList(items)
                                    }
                                }
                                val bookShortageBean =
                                    it.getParam<Any>("bookShortageBean")
                                bookShortageBean?.let {
                                    val items = bookShortageBean.getParam<MutableList<*>>("items")
                                    items?.let {
                                        parseNeedShieldList(items)
                                    }
                                }

                                val monthBannerListBean =
                                    it.getParam<Any>("monthBannerListBean")
                                monthBannerListBean?.let {
                                    val items =
                                        monthBannerListBean.getParam<MutableList<*>>("items")
                                    items?.let {
                                        parseNeedShieldList(items)
                                    }
                                }
                                val newBookAIRecommendBean =
                                    it.getParam<Any>("newBookAIRecommendBean")
                                newBookAIRecommendBean?.let {
                                    val items =
                                        newBookAIRecommendBean.getParam<MutableList<*>>("items")
                                    items?.let {
                                        parseNeedShieldList(items)
                                    }
                                }
                                val newBookRankBean =
                                    it.getParam<Any>("newBookRankBean")
                                newBookRankBean?.let {
                                    val items = newBookRankBean.getParam<MutableList<*>>("items")
                                    items?.let {
                                        parseNeedShieldList(items)
                                    }
                                }
                                /*
                                val newBookRecommendBean =
                                    instance.getParam<Any>("newBookRecommendBean")

                                newBookRecommendBean?.let {
                                    val items =
                                        getParam<MutableList<*>>(newBookRecommendBean, "items")
                                    items?.let {
                                        val iterator = it.iterator()
                                        while (iterator.hasNext()) {
                                            val item = iterator.next().toJSONString()
                                            val jb = item.parseObject()
                                            val recData = jb.getJSONObject("recData")
                                            if (recData != null){
                                                val items = recData.getJSONArray("items") as? MutableList<*>
                                                items?.let {
                                                    parseNeedShieldList(items)
                                                }
                                            }
                                        }
                                    }
                                }

                                 */
                                val newBookTagBean =
                                    it.getParam<Any>("newBookTagBean")
                                newBookTagBean?.let {
                                    val items = newBookTagBean.getParam<MutableList<*>>("items")
                                    items?.let {
                                        parseNeedShieldList(items)
                                    }
                                }

                            }
                        }
                    }
                }
            }

            /**
             * 精选-新书-新书入库/新书强推
             */
            findClass("com.qidian.QDReader.repository.entity.newbook.RecPageData").hook {
                injectMember {
                    method {
                        name = "getItems"
                        returnType = ListClass
                    }
                    afterHook {
                        result.safeCast<MutableList<*>>()?.let {
                            parseNeedShieldList(it)
                        }
                    }
                }
            }
        }

        else -> "屏蔽精选-新书".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽免费-新书入库
 */
fun PackageParam.shieldFreeNewBook(versionCode: Int) {
    when (versionCode) {
        in 788..1099 -> {
            findClass("com.qidian.QDReader.ui.fragment.QDNewBookInStoreFragment").hook {
                injectMember {
                    method {
                        name = "loadData\$lambda-6"
                        param(
                            "com.qidian.QDReader.ui.fragment.QDNewBookInStoreFragment".toClass(),
                            "com.qidian.QDReader.repository.entity.NewBookInStore".toClass()
                        )
                    }
                    beforeHook {
                        args[1]?.let {
                            val categoryIdList = it.getParam<MutableList<*>>("CategoryIdList")
                            val itemList = it.getParam<MutableList<*>>("ItemList")
                            categoryIdList?.let { list ->
                                safeRun {
                                    parseNeedShieldList(list)
                                }
                            }
                            itemList?.let { list ->
                                safeRun {
                                    parseNeedShieldList(list)
                                }
                            }
                        }
                    }
                }
            }

            findClass("com.qidian.QDReader.ui.activity.QDNewBookInStoreActivity").hook {
                injectMember {
                    method {
                        name = "loadData\$lambda-6"
                        param(
                            "com.qidian.QDReader.ui.activity.QDNewBookInStoreActivity".toClass(),
                            "com.qidian.QDReader.repository.entity.NewBookInStore".toClass()
                        )
                    }
                    beforeHook {
                        args[1]?.let {
                            val categoryIdList = it.getParam<MutableList<*>>("CategoryIdList")
                            val itemList = it.getParam<MutableList<*>>("ItemList")
                            categoryIdList?.let { list ->
                                safeRun {
                                    parseNeedShieldList(list)
                                }
                            }
                            itemList?.let { list ->
                                safeRun {
                                    parseNeedShieldList(list)
                                }
                            }
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
 * 上级调用:com.qidian.QDReader.ui.activity.QDNewBookInStoreActivity.initView() 在刷新前修改List数据
 * getMAdapter
 */
fun PackageParam.shieldHotAndRecommend(versionCode: Int) {
    val needHookClass = when (versionCode) {
        in 788..834 -> "com.qidian.QDReader.ui.adapter.s"
        in 842..860 -> "com.qidian.QDReader.ui.adapter.t"
        in 868..878 -> "com.qidian.QDReader.ui.adapter.u"
        in 884..970 -> "com.qidian.QDReader.ui.adapter.r"
        in 980..1020 -> "com.qidian.QDReader.ui.adapter.q"
        else -> null
    }
    needHookClass?.hook {
        injectMember {
            method {
                name = "setList"
                param(ListClass)
                returnType = UnitType
            }
            afterHook {
                val list = instance.getParam<MutableList<*>>("b")
                list?.let {
                    safeRun {
                        parseNeedShieldList(it)
                    }
                }

            }
        }
    } ?: "屏蔽畅销精选、主编力荐等更多".printlnNotSupportVersion(versionCode)
}

/**
 * 屏蔽新书强推、三江推荐
 */
fun PackageParam.shieldNewBookAndRecommend(versionCode: Int) {
    when (versionCode) {
        788 -> {
            findClass("com.qidian.QDReader.ui.fragment.SanJiangPagerFragment").hook {
                injectMember {
                    method {
                        name = "r"
                        param("com.qidian.QDReader.ui.fragment.SanJiangPagerFragment".toClass())
                        returnType = ListClass
                    }
                    afterHook {
                        result.safeCast<MutableList<*>>()?.let {
                            safeRun {
                                val iterator = it.iterator()
                                while (iterator.hasNext()) {
                                    val json = iterator.next().toJSONString().parseObject()
                                    val jb = json.getJSONObject("BookStoreItem")
                                    if (jb != null) {
                                        val authorName = jb.getString("AuthorName")
                                        val bookName = jb.getString("BookName")
                                        val categoryName = jb.getString("CategoryName")
                                        val array = jb.getJSONArray("tagList")
                                        val bookTypeArray = mutableSetOf(categoryName)
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
                                result = it
                            }
                        }
                    }
                }
            }
        }

        in 792..800 -> {
            findClass("com.qidian.QDReader.ui.fragment.SanJiangPagerFragment").hook {
                injectMember {
                    method {
                        name = "s"
                        param("com.qidian.QDReader.ui.fragment.SanJiangPagerFragment".toClass())
                        returnType = ListClass
                    }
                    afterHook {
                        result.safeCast<MutableList<*>>()?.let {
                            safeRun {
                                val iterator = it.iterator()
                                while (iterator.hasNext()) {
                                    val item = iterator.next().toJSONString()
                                    val json = item.parseObject()
                                    val jb = json.getJSONObject("BookStoreItem")
                                    if (jb != null) {
                                        val authorName = jb.getString("AuthorName")
                                        val bookName = jb.getString("BookName")
                                        val categoryName = jb.getString("CategoryName")
                                        val array = jb.getJSONArray("tagList")
                                        val bookTypeArray = mutableSetOf(categoryName)
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
                                result = it
                            }
                        }
                    }
                }
            }
        }

        in 804..1020 -> {
            /**
             *上级调用:com.qidian.QDReader.ui.fragment.SanJiangPagerFragment mAdapter
             */
            val needHookClass = when (versionCode) {
                in 804..812 -> "com.qidian.QDReader.ui.adapter.lb"
                827 -> "com.qidian.QDReader.ui.adapter.nb"
                834 -> "com.qidian.QDReader.ui.adapter.ob"
                in 842..860 -> "com.qidian.QDReader.ui.adapter.pb"
                in 868..878 -> "com.qidian.QDReader.ui.adapter.rb"
                in 884..890 -> "com.qidian.QDReader.ui.adapter.qb"
                in 896..924 -> "com.qidian.QDReader.ui.adapter.mb"
                in 932..958 -> "com.qidian.QDReader.ui.adapter.rb"
                970 -> "com.qidian.QDReader.ui.adapter.ub"
                in 980..1020 -> "com.qidian.QDReader.ui.adapter.tb"
                else -> null
            }
            val needHookMethod = when (versionCode) {
                in 804..878 -> "q"
                in 884..1020 -> "n"
                else -> null
            }
            if (needHookClass == null || needHookMethod == null) {
                "屏蔽新书强推、三江推荐".printlnNotSupportVersion(versionCode)
            } else {
                needHookClass.hook {
                    injectMember {
                        method {
                            name = needHookMethod
                            param(ListClass)
                            returnType = UnitType
                        }
                        beforeHook {
                            args[0].safeCast<MutableList<*>>()?.let {
                                safeRun {
                                    val iterator = it.iterator()
                                    while (iterator.hasNext()) {
                                        val item = iterator.next().toJSONString()
                                        val json = item.parseObject()
                                        val jb = json.getJSONObject("BookStoreItem")
                                        if (jb != null) {
                                            val authorName = jb.getString("AuthorName")
                                            val bookName = jb.getString("BookName")
                                            val categoryName = jb.getString("CategoryName")
                                            val array = jb.getJSONArray("tagList")
                                            val bookTypeArray = mutableSetOf(categoryName)
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
        }

        else -> "屏蔽新书强推、三江推荐".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽精选-排行榜
 */
fun PackageParam.shieldBookRank(versionCode: Int) {
    val needHookMethod = when (versionCode) {
        in 808..994 -> "lambda\$loadBookList\$4"
        in 1005..1020 -> "lambda\$loadBookList\$5"
        else -> null
    }
    needHookMethod?.let {
        findClass("com.qidian.QDReader.ui.fragment.RankingFragment").hook {
            injectMember {
                method {
                    name = it
                    param(
                        BooleanType,
                        BooleanType,
                        IntType,
                        "com.qidian.QDReader.repository.entity.RankListData".toClass()
                    )
                    returnType = UnitType
                }
                beforeHook {
                    args[3]?.let {
                        val rankBookList = it.getParam<MutableList<*>>("rankBookList")
                        rankBookList?.let {
                            parseNeedShieldList(rankBookList)
                        }
                    }
                }
            }
        }
    } ?: "屏蔽排行榜".printlnNotSupportVersion(versionCode)
}

/**
 * 屏蔽分类-全部作品
 */
fun PackageParam.shieldCategoryAllBook(versionCode: Int) {
    when (versionCode) {
        in 788..812 -> {
            /**
             * 分类-全部作品
             */
            findClass("com.qidian.QDReader.ui.activity.BookLibraryActivity").hook {
                injectMember {
                    method {
                        name = "M"
                        param("com.qidian.QDReader.ui.activity.BookLibraryActivity".toClass())
                        returnType = ArrayListClass
                    }
                    beforeHook {
                        args[0]?.let {
                            val list = it.getParam<ArrayList<*>>("mBookList")
                            list?.let {
                                safeRun {
                                    parseNeedShieldList(it)
                                }
                            }
                        }
                    }
                }
            }
        }

        in 827..878 -> {
            /**
             * 上级调用:com.qidian.QDReader.ui.fragment.LibraryFragment.loadData(boolean) : void
             */
            findClass("com.qidian.QDReader.ui.fragment.LibraryFragment\$a").hook {
                injectMember {
                    method {
                        name = "a"
                        param(IntType, ArrayListClass)
                        returnType = UnitType
                    }
                    beforeHook {
                        args[1].safeCast<MutableList<*>>()?.let {
                            safeRun {
                                args(1).set(parseNeedShieldList(it))
                            }
                        }
                    }
                }
            }
        }

        in 884..1099 -> {
            findClass("com.qidian.QDReader.ui.fragment.LibraryFragment\$search").hook {
                injectMember {
                    method {
                        name = "search"
                        param(IntType, ArrayListClass)
                        returnType = UnitType
                    }
                    beforeHook {
                        args[1].safeCast<MutableList<*>>()?.let {
                            safeRun {
                                args(1).set(parseNeedShieldList(it))
                            }
                        }
                    }
                }
            }
        }

        else -> "屏蔽分类-全部作品".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽分类-小编力荐、本周强推等
 */
fun PackageParam.shieldCategoryBookListReborn(versionCode: Int) {
    val needHookMethodName = when (versionCode) {
        in 950..958 -> "o0"
        in 970..1020 -> "n0"
        else -> null
    }
    needHookMethodName?.let {
        findClass("com.qidian.QDReader.ui.view.BookItemView").hook {
            injectMember {
                method {
                    name = needHookMethodName
                    param(JSONArrayClass)
                    returnType = ListClass
                }
                afterHook {
                    result.safeCast<ArrayList<*>>()?.let {
                        result = parseNeedShieldList(it)
                    }
                }
            }
        }
    } ?: "屏蔽分类-小编力荐、本周强推等".printlnNotSupportVersion(versionCode)
}

/**
 * 屏蔽-漫画
 */
fun PackageParam.shieldComic(versionCode: Int) {
    when (versionCode) {
        in 812..1099 -> {
            findClass("com.qidian.QDReader.repository.entity.ComicSquareItem").hook {
                injectMember {
                    method {
                        name = "getComicSqureRecmdItems"
                        emptyParam()
                        returnType = ArrayListClass
                    }
                    afterHook {
                        result.safeCast<ArrayList<*>>()?.let {
                            result = HookEntry.parseNeedShieldComicList(it)
                        }
                    }
                }
            }
        }

        else -> "屏蔽-漫画".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽-漫画-其他
 * 上级调用: com.qidian.QDReader.ui.activity.QDComicSquareItemDetailActivity.loadData
 * mAdapter
 */
fun PackageParam.shieldComicOther(versionCode: Int) {
    val needHookClass = when (versionCode) {
        in 812..834 -> "com.qidian.QDReader.ui.adapter.c2"
        in 842..860 -> "com.qidian.QDReader.ui.adapter.d2"
        in 868..878 -> "com.qidian.QDReader.ui.adapter.e2"
        in 884..970 -> "com.qidian.QDReader.ui.adapter.b2"
        in 980..1020 -> "com.qidian.QDReader.ui.adapter.a2"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 812..878 -> "q"
        in 884..1020 -> "n"
        else -> null
    }
    if (needHookClass == null || needHookMethod == null) {
        "屏蔽-漫画-其他".printlnNotSupportVersion(versionCode)
        return
    }
    needHookClass.hook {
        injectMember {
            method {
                name = needHookMethod
                param(ArrayListClass)
                returnType = UnitType
            }
            afterHook {
                val list = instance.getParamList<ArrayList<*>?>()
                if (list.isNotEmpty()) {
                    HookEntry.parseNeedShieldComicList(list)
                }
            }
        }
    }
}

/**
 * 书籍详情-快速屏蔽
 */
fun PackageParam.quickShield(versionCode: Int) {
    when (versionCode) {
        in 1005..1020 -> {
            findClass("com.qidian.QDReader.ui.activity.QDBookDetailActivity").hook {
                injectMember {
                    method {
                        name = "mergeBookDetail\$lambda-6"
                        paramCount(3)
                        returnType = UnitType
                    }
                    afterHook {
                        val viewMap =
                            args[0]?.getParam<Map<*, View>>("_\$_findViewCache") ?: return@afterHook
                        val ids = listOf("tvBookName", "tvAuthorName")
                        val textViews =
                            viewMap.values.filterIsInstance<TextView>()
                                .filter { it.getName() in ids }
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
            }
        }

        else -> "书籍详情-快速屏蔽".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 书籍详情-快速屏蔽弹框
 * @param name 书名
 * @param author 作者
 */
fun Context.showQuickShieldDialog(
    name: String = "",
    author: String = ""
) {
    if (name.isNotBlank()) {
        val editText = CustomEditText(
            context = this,
            mHint = "输入书名关键词",
            value = name
        )
        alertDialog {
            title = "编辑需要屏蔽的书名关键词?"
            customView = editText
            okButton {
                HookEntry.addShieldBook(bookName = editText.editText.text.toString())
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
                HookEntry.addShieldBook(authorName = author)
            }
            cancelButton {
                it.dismiss()
            }
            build()
            show()
        }
    }

}
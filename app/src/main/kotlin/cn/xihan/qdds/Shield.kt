package cn.xihan.qdds

import cn.xihan.qdds.HookEntry.Companion.isNeedShield
import cn.xihan.qdds.HookEntry.Companion.parseNeedShieldList
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.ContextClass
import com.highcapable.yukihookapi.hook.type.java.ArrayListClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.IntType
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
 * @param optionValueSet 屏蔽选项值
 */
fun PackageParam.shieldOption(versionCode: Int, optionValueSet: Set<Int>) {
    // 遍历 optionValueSet 包含的值 执行指定方法
    optionValueSet.forEach {
        when (it) {
            0 -> shieldSearchFind(versionCode)
            3 -> shieldSearchRecommend(versionCode)
            4 -> shieldChoice(versionCode)
            5 -> shieldCategory(versionCode)
            6 -> shieldCategoryAllBook(versionCode)
            7 -> shieldFreeRecommend(versionCode)
            8 -> shieldFreeNewBook(versionCode)
            9 -> shieldHotAndRecommend(versionCode)
            10 -> shieldNewBookAndRecommend(versionCode)
            11 -> shieldBookRank(versionCode)
            12 -> shieldNewBook(versionCode)
            13 -> shieldDailyReading(versionCode)
            14 -> shieldComic(versionCode)
            15 -> shieldComicOther(versionCode)
        }
    }
    shieldSearch(versionCode, HookEntry.isEnableShieldOption(1), HookEntry.isEnableShieldOption(2))
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
        in 860..872 -> "com.qidian.QDReader.component.api.k1"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 788..812 -> "j"
        in 827..872 -> "k"
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
                val list = result as? ArrayList<*>
                list?.let {
                    result = parseNeedShieldList(list)
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
        in 788..880 -> {
            /**
             * 精选主页面
             */
            findClass("com.qidian.QDReader.repository.entity.BookListData").hook {
                injectMember {
                    method {
                        name = "getItems"
                        returnType = ListClass
                    }
                    afterHook {
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
                        val list = args[3] as? MutableList<*>
                        list?.let {
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
                        val list = args[3] as? MutableList<*>
                        list?.let {
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
                        val b = args[1] as? MutableList<*>
                        b?.let {
                            safeRun {
                                args(1).set(parseNeedShieldList(it))
                            }
                        }
                    }
                }
            }
        }

        in 827..872 -> {
            val needHookClass = when (versionCode) {
                827 -> "com.qidian.QDReader.ui.adapter.x6"
                834 -> "com.qidian.QDReader.ui.adapter.y6"
                in 842..860 -> "com.qidian.QDReader.ui.adapter.z6"
                in 868..872 -> "com.qidian.QDReader.ui.adapter.a7"
                else -> null
            }
            needHookClass?.hook {
                injectMember {
                    method {
                        name = "r"
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
                        val list = result as? MutableList<*>
                        list?.let {
                            safeRun {
                                result = parseNeedShieldList(list)
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
    val freeRecommendHookClass: String? = when (versionCode) {
        788 -> "la.a"
        in 792..808 -> "ka.a"
        812 -> "ia.a"
        827 -> "la.a"
        in 834..860 -> "ka.a"
        868 -> "ma.a"
        872 -> "ka.a"
        else -> null
    }
    freeRecommendHookClass?.hook {
        injectMember {
            method {
                name = "n"
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
    } ?: "屏蔽免费-免费推荐".printlnNotSupportVersion(versionCode)
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
                        val list = result as? MutableList<*>
                        list?.let {
                            parseNeedShieldList(it)
                        }
                    }
                }
            }
        }

        in 850..880 -> {
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
                        val list = result as? MutableList<*>
                        list?.let {
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
        in 788..880 -> {
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
        in 868..872 -> "com.qidian.QDReader.ui.adapter.u"
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
                        val list = result as? MutableList<*>
                        list?.let {
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

        in 792..800 -> {
            findClass("com.qidian.QDReader.ui.fragment.SanJiangPagerFragment").hook {
                injectMember {
                    method {
                        name = "s"
                        param("com.qidian.QDReader.ui.fragment.SanJiangPagerFragment".toClass())
                        returnType = ListClass
                    }
                    afterHook {
                        val list = result as? MutableList<*>
                        list?.let {
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

        in 804..872 -> {
            val needHookClass = when (versionCode) {
                in 804..812 -> "com.qidian.QDReader.ui.adapter.lb"
                827 -> "com.qidian.QDReader.ui.adapter.nb"
                834 -> "com.qidian.QDReader.ui.adapter.ob"
                in 842..860 -> "com.qidian.QDReader.ui.adapter.pb"
                in 868..872 -> "com.qidian.QDReader.ui.adapter.rb"
                else -> null
            }
            /**
             *上级调用:com.qidian.QDReader.ui.fragment.SanJiangPagerFragment mAdapter
             */
            needHookClass?.hook {
                injectMember {
                    method {
                        name = "q"
                        param(ListClass)
                        returnType = UnitType
                    }
                    beforeHook {
                        val list = args[0] as? MutableList<*>
                        list?.let {
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

        else -> "屏蔽新书强推、三江推荐".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽精选-排行榜
 */
fun PackageParam.shieldBookRank(versionCode: Int) {
    when (versionCode) {
        in 808..872 -> {
            findClass("com.qidian.QDReader.ui.fragment.RankingFragment").hook {
                injectMember {
                    method {
                        name = "lambda\$loadBookList\$4"

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
        }

        else -> "屏蔽排行榜".printlnNotSupportVersion(versionCode)
    }
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

        in 827..880 -> {
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
                        val b = args[1] as? MutableList<*>
                        b?.let {
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
 * 屏蔽搜索发现(热词)
 */
fun PackageParam.shieldSearchFind(versionCode: Int) {
    when (versionCode) {
        in 788..880 -> {
            /**
             * 搜索发现(热词)
             */
            findClass("com.qidian.QDReader.repository.entity.search.SearchHotWordBean").hook {
                injectMember {
                    method {
                        name = "getWordList"
                        emptyParam()
                        returnType = ListClass
                    }
                    afterHook {
                        val list = result as? MutableList<*>
                        list?.clear()
                        result = list
                    }
                }
            }
        }

        else -> "屏蔽搜索发现(热词)".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽搜索-热门作品榜、人气标签榜
 * @param versionCode 版本号
 * @param isNeedShieldBookRank 屏蔽热门作品榜
 * @param isNeedShieldTagRank 屏蔽人气标签榜
 */
fun PackageParam.shieldSearch(
    versionCode: Int,
    isNeedShieldBookRank: Boolean,
    isNeedShieldTagRank: Boolean,
) {
    if (isNeedShieldBookRank) {
        /**
         * 上级调用: com.qidian.QDReader.ui.activity.QDSearchListActivity.bindView() mAdapter
         */
        val needHookClass: String? = when (versionCode) {
            788 -> "o9.d"
            in 792..808 -> "n9.d"
            812 -> "l9.d"
            827 -> "m9.d"
            in 834..854 -> "l9.d"
            in 858..860 -> "m9.d"
            868 -> "n9.d"
            872 -> "l9.d"
            else -> null
        }
        /**
         * 屏蔽热门作品榜更多
         */
        needHookClass?.hook {
            injectMember {
                method {
                    name = "o"
                }
                beforeHook {
                    val list = args[0] as? MutableList<*>
                    list?.let {
                        safeRun {
                            args(0).set(parseNeedShieldList(it))
                        }
                    }
                }
            }
        } ?: "屏蔽热门作品榜更多".printlnNotSupportVersion(versionCode)
    }
    when (versionCode) {
        in 788..880 -> {
            findClass("com.qidian.QDReader.ui.view.search.SearchHomePageRankView").hook {
                if (isNeedShieldBookRank) {
                    /**
                     * 热门作品榜
                     */
                    injectMember {
                        method {
                            name = "setBookRank"
                            param("com.qidian.QDReader.repository.entity.search.SearchBookRankBean".toClass())
                        }
                        beforeHook {
                            args[0]?.let {
                                val bookList = it.getParam<MutableList<*>>("BookList")
                                bookList?.let {
                                    safeRun {
                                        parseNeedShieldList(it)
                                    }
                                }
                            }
                        }
                    }
                }

                if (isNeedShieldTagRank) {
                    /**
                     * 人气标签榜
                     */
                    injectMember {
                        method {
                            name = "setTagRank"
                            param("com.qidian.QDReader.repository.entity.search.SearchTagRankBean".toClass())
                        }
                        beforeHook {
                            args[0]?.let {
                                val list = it.getParam<MutableList<*>>("TagList")
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
        }

        else -> "屏蔽搜索-热门作品榜、人气标签榜".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽搜索-为你推荐
 */
fun PackageParam.shieldSearchRecommend(versionCode: Int) {
    when (versionCode) {
        in 788..880 -> {
            /**
             * 搜索-为你推荐
             */
            findClass("com.qidian.QDReader.repository.entity.search.SearchHomeCombineBean").hook {
                injectMember {
                    constructor {
                        param(ListClass)
                    }
                    beforeHook {
                        val list = args[0] as? MutableList<*>
                        list?.clear()
                    }
                }
            }
        }

        else -> "屏蔽搜索-为你推荐".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 屏蔽-漫画
 */
fun PackageParam.shieldComic(versionCode: Int) {
    when (versionCode) {
        in 812..880 -> {

            findClass("com.qidian.QDReader.repository.entity.ComicSquareItem").hook {
                injectMember {
                    method {
                        name = "getComicSqureRecmdItems"
                        emptyParam()
                        returnType = ArrayListClass
                    }
                    afterHook {
                        val list = result as? ArrayList<*>
                        list?.let {
                            result = HookEntry.parseNeedShieldComicList(list)
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
        in 868..872 -> "com.qidian.QDReader.ui.adapter.e2"
        else -> null
    }
    needHookClass?.hook {
        injectMember {
            method {
                name = "q"
                param(ArrayListClass)
                returnType = UnitType
            }
            afterHook {
                val b = instance.getParam<ArrayList<*>>("b")
                b?.let {
                    HookEntry.parseNeedShieldComicList(b)
                }
            }
        }
    } ?: "屏蔽-漫画-其他".printlnNotSupportVersion(versionCode)
}

/**
/**
 * 屏蔽相关配置弹框
*/
fun Context.showShieldOptionDialog() {
val linearLayout = CustomLinearLayout(this, isAutoWidth = false)
val customTextView = CustomTextView(
context = this, mText = "屏蔽选项列表", isBold = true
) {
val shieldOptionList = listOf(
"搜索-发现(热词)",
"搜索-热门作品榜",
"搜索-人气标签榜",
"搜索-为你推荐",
"精选-主页面",
"精选-分类",
"精选-分类-全部作品",
"精选-免费-免费推荐",
"精选-免费-新书入库",
"精选-畅销精选、主编力荐等更多",
"精选-新书强推、三江推荐",
"精选-排行榜",
"精选-新书",
"每日导读",
"精选-漫画",
"精选-漫画-其他"
)
val checkedItems = BooleanArray(shieldOptionList.size)
if (HookEntry.optionEntity.shieldOption.shieldOptionValueSet.isNotEmpty()) {
safeRun {
shieldOptionList.forEachIndexed { index, _ ->
if (index in HookEntry.optionEntity.shieldOption.shieldOptionValueSet) {
checkedItems[index] = true
}
}
}
}
multiChoiceSelector(shieldOptionList, checkedItems, "屏蔽选项列表") { _, i, isChecked ->
checkedItems[i] = isChecked
}.doOnDismiss {
checkedItems.forEachIndexed { index, b ->
if (b) {
HookEntry.optionEntity.shieldOption.shieldOptionValueSet += index
} else {
HookEntry.optionEntity.shieldOption.shieldOptionValueSet -= index
}
}
}
}
val authorNameOptionCustomEdit = CustomEditText(
context = this,
title = "填入需要屏蔽的完整作者名称",
message = "使用 \";\" 分隔",
value = HookEntry.optionEntity.shieldOption.authorList.joinToString(";")
) {
HookEntry.optionEntity.shieldOption.authorList = HookEntry.parseKeyWordOption(it)
}
val bookNameOptionCustomEdit = CustomEditText(
context = this,
title = "填入需要屏蔽的书名关键词",
message = "注意:单字威力巨大!!!\n使用 \";\" 分隔",
value = HookEntry.optionEntity.shieldOption.bookNameList.joinToString(";")
) {
HookEntry.optionEntity.shieldOption.bookNameList = HookEntry.parseKeyWordOption(it)
}
val bookTypeSwitch = CustomSwitch(
context = this,
title = "启用书类型增强屏蔽",
isEnable = HookEntry.optionEntity.shieldOption.enableBookTypeEnhancedBlocking
) {
HookEntry.optionEntity.shieldOption.enableBookTypeEnhancedBlocking = it
}
val bookTypeOptionCustomEdit = CustomEditText(
context = this,
title = "填入需要屏蔽的书类型",
message = "使用 \";\" 分隔",
value = HookEntry.optionEntity.shieldOption.bookTypeList.joinToString(";")
) {
HookEntry.optionEntity.shieldOption.bookTypeList = HookEntry.parseKeyWordOption(it)
}
linearLayout.apply {
addView(customTextView)
addView(authorNameOptionCustomEdit)
addView(bookNameOptionCustomEdit)
addView(bookTypeSwitch)
addView(bookTypeOptionCustomEdit)
}
alertDialog {
title = "屏蔽相关配置"
customView = linearLayout
okButton {
updateOptionEntity()
}
negativeButton("返回") {
it.dismiss()
}
build()
show()
}

}
 */
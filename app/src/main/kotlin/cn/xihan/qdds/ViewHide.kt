package cn.xihan.qdds

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import com.highcapable.yukihookapi.hook.factory.constructor
import com.highcapable.yukihookapi.hook.factory.current
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.ViewClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.ListClass
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType
import de.robv.android.xposed.XposedHelpers

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2022/8/28 16:12
 * @介绍 :
 */
/**
 * 主页配置列表
 */
fun PackageParam.homeOption(versionCode: Int, optionValueSet: List<OptionEntity.SelectedModel>) {
    optionValueSet.filter { it.selected }.forEach {
        when (it.title) {
            "主页顶部宝箱提示" -> hideMainTopBox(versionCode)
            "主页顶部战力提示" -> hideMainTopPower(versionCode)
            "书架每日导读" -> hideBookshelfDailyReading(versionCode)
            "书架去找书" -> hideBookshelfFindBook(versionCode)
//            "主页底部导航栏发现" -> hideBottomNavigationFind(versionCode)
            "主页底部导航栏红点" -> hideBottomRedDot(versionCode)
        }
    }
}

/**
 * 精选-隐藏配置
 */
fun PackageParam.selectedOption(versionCode: Int) {
    when (versionCode) {
        in 868..980 -> {
            /*
            findClass("com.qidian.morphing.card.BaseMorphingCard").hook {
                injectMember {
                    method {
                        name = "item"
                        paramCount(1)
                        returnType = UnitType
                    }
                    afterHook {
                        args[0]?.let {
                            val name = it.getParam<Any>("data")?.getParam<Any>("cardTitle")
                                ?.getParam<String>("name")
//                            "name: $name".loge()
                            if (name.isNullOrBlank()) return@let
                            HookEntry.optionEntity.viewHideOption.selectedOption.configurations.findOrPlus(
                                title = name
                            ) {
                                instance<FrameLayout>().visibility = View.GONE
                            }
                        }
                    }
                }
            }

             */

            /**
             * 新方法
             */
            findClass("com.qidian.QDReader.ui.modules.bookstore.BookStoreRebornFragment").hook {
                injectMember {
                    method {
                        name = "updateUI"
                        paramCount(4)
                        returnType = UnitType
                    }
                    beforeHook {
                        val bookStoreWrap = args[0] ?: return@beforeHook
                        val cardItems =
                            bookStoreWrap.getParam<MutableList<*>>("cardItems") ?: return@beforeHook
                        val iterator = cardItems.iterator()
                        while (iterator.hasNext()) {
                            val bookStoreCardItem = iterator.next() ?: continue
                            val colName = bookStoreCardItem.getParam<String>("colName")
                            var title = bookStoreCardItem.getParam<String>("title")
                            if (!colName.isNullOrBlank() && title.isNullOrBlank()) {
                                when (colName) {
                                    "banner" -> {
                                        title = "轮播图"
                                    }

                                    "broadcast" -> {
                                        title = "轮播消息"
                                    }

                                    "icon" -> {
                                        val jsonObject =
                                            bookStoreCardItem.getParam<Any>("jsonObject")
                                                ?: continue
                                        jsonObject.current {
                                            val jsonArray = method {
                                                name = "getAsJsonArray"
                                                paramCount(1)
                                            }.call("Items")

                                            jsonArray?.current {
                                                val iterator = method {
                                                    name = "iterator"
                                                }.call() as? MutableIterator<*>

                                                while (iterator?.hasNext() == true) {
                                                    val item = iterator.next()
                                                    item?.current {
                                                        val title = method {
                                                            name = "get"
                                                        }.call("Text")
                                                        HookEntry.optionEntity.viewHideOption.selectedOption.configurations.findOrPlus(
                                                            title = "$title".replace("\"", ""),
                                                            iterator = iterator
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!title.isNullOrBlank()) {
                                HookEntry.optionEntity.viewHideOption.selectedOption.configurations.findOrPlus(
                                    title = title,
                                    iterator = iterator
                                )
                            }


                        }
                    }
                }
            }

            findClass("com.qidian.QDReader.ui.modules.bookstore.MorphingFragment").hook {
                injectMember {
                    method {
                        name = "updateUI"
                        paramCount(4)
                        returnType = UnitType
                    }
                    beforeHook {
                        val bookStoreWrap = args[0] ?: return@beforeHook
                        val cardItems =
                            bookStoreWrap.getParam<MutableList<*>>("cardItems") ?: return@beforeHook
                        val iterator = cardItems.iterator()
                        while (iterator.hasNext()) {
                            val bookStoreCardItem = iterator.next() ?: continue
                            val data = bookStoreCardItem.getParam<Any>("data") ?: continue
                            val columnName =
                                data.getParam<Any>("extension")?.getParam<String>("columnName")
                            var title = data.getParam<Any>("cardTitle")?.getParam<String>("name")
//                            "columnName: $columnName, title: $title".loge()
                            if (!columnName.isNullOrBlank() && title.isNullOrBlank() && "banner" == columnName) {
                                title = "轮播图"
                            }
                            if (!title.isNullOrBlank()) {
                                HookEntry.optionEntity.viewHideOption.selectedOption.configurations.findOrPlus(
                                    title = title!!,
                                    iterator = iterator
                                )
                            }
                            val multiData = data.getParam<MutableList<*>>("multiData")
                            if (multiData != null) {
                                val multiIterator = multiData.iterator()
                                while (multiIterator.hasNext()) {
                                    val item = multiIterator.next() ?: continue
                                    val columnName =
                                        item.getParam<Any>("extension")
                                            ?.getParam<String>("columnName")
                                    var title =
                                        item.getParam<Any>("cardTitle")?.getParam<String>("name")
                                    if (!columnName.isNullOrBlank() && title.isNullOrBlank()) {
                                        when (columnName) {
                                            "broadcast" -> {
                                                title = "轮播消息"
                                            }

                                            "icon" -> {
                                                val list = item.getParam<MutableList<*>>("list")
                                                if (list != null) {
                                                    val listIterator = list.iterator()
                                                    while (listIterator.hasNext()) {
                                                        val iconItem = listIterator.next()
                                                        val itemName =
                                                            iconItem?.getParam<String>("itemName")
//                                                        "itemName: $itemName".loge()
                                                        if (!itemName.isNullOrBlank()) {
                                                            HookEntry.optionEntity.viewHideOption.selectedOption.configurations.findOrPlus(
                                                                title = itemName,
                                                                iterator = listIterator
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (!title.isNullOrBlank()) {
                                        HookEntry.optionEntity.viewHideOption.selectedOption.configurations.findOrPlus(
                                            title = title!!,
                                            iterator = multiIterator
                                        )
                                    }
                                }
                            }


                        }
                    }
                }
            }
        }

        else -> "精选-隐藏配置".printlnNotSupportVersion(versionCode)
    }

}

/**
 * 精选-标题隐藏配置
 */
fun PackageParam.selectedTitleOption(versionCode: Int) {
    when (versionCode) {
        in 872..980 -> {
            findClass("com.qidian.QDReader.ui.fragment.QDStorePagerFragment").hook {
                injectMember {
                    method {
                        name = "onViewInject"
                        param(ViewClass)
                        returnType = UnitType
                    }
                    afterHook {

                        val mAdapter = instance.getParam<Any>("mAdapter") ?: return@afterHook
                        val mTabLayout =
                            instance.getParam<FrameLayout>("mTabLayout") ?: return@afterHook

                        mAdapter.current {
                            val countSize = superClass().method {
                                name = "getCount"
                                emptyParam()
                                returnType = IntType
                            }.int()
                            val pageList = mutableListOf<Any?>()
                            val needShieldTitleList = HookEntry.getNeedShieldTitleList()
                            if (countSize > 0 && needShieldTitleList.isNotEmpty()) {
                                /*
                                for (i in 0 until countSize) {
                                    val type = superClass().method {
                                        name = "getType"
                                        paramCount(1)
                                        returnType = IntType
                                    }.int(i)
                                    typeList.add(type)
                                }

                                 */
                                needShieldTitleList.forEach {
                                    val page = superClass().method {
                                        name = "getItemByType"
                                        paramCount(1)
                                        returnType =
                                            "com.qidian.QDReader.ui.fragment.BasePagerFragment".toClass()
                                    }.call(it)
                                    pageList.add(page)
//                                    "type: $it".loge()
                                }
                                if (pageList.isNotEmpty()) {
                                    pageList.forEach {
                                        it?.let {
                                            val v = superClass().method {
                                                name = "removePage"
                                                paramCount(1)
                                                returnType = IntType
                                            }.int(it)
//                                            "${it::class.java.name} removePage: $v".loge()
                                        }
                                    }

                                    superClass().method {
                                        name = "notifyDataSetChanged"
                                        emptyParam()
                                        returnType = UnitType
                                    }.call()
                                }
                            }
                        }

                        instance.current {
                            method {
                                name = "calculateSize"
                                emptyParam()
                                returnType = BooleanType
                            }.call()
                        }

                        val child = mTabLayout.getChildAt(0) as FrameLayout
                        val child2 = child.getChildAt(0) as FrameLayout
                        val child3 = child2.getChildAt(1) as LinearLayout
                        val needRemoveView = mutableListOf<View>()
                        for (i in 0 until child3.childCount) {
                            val childAt = child3.getChildAt(i) as FrameLayout
                            val textView = childAt.getView<TextView>("j") ?: continue
                            if (textView.text.isNotBlank()) {
                                HookEntry.optionEntity.viewHideOption.selectedOption.selectedTitleConfigurations.findOrPlus(
                                    title = textView.text.toString()
                                ) {
                                    needRemoveView.add(childAt)
//                                    childAt.visibility = View.INVISIBLE
                                }
                            }
                        }
                        if (needRemoveView.isNotEmpty()) {
                            needRemoveView.forEach {
                                child3.removeView(it)
                            }
                        }

                    }
                }

            }
        }

        else -> "精选-标题隐藏配置".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏主页面-顶部宝箱提示
 */
fun PackageParam.hideMainTopBox(versionCode: Int) {
    when (versionCode) {
        in 812..980 -> {
            findClass("com.qidian.QDReader.ui.activity.MainGroupActivity").hook {
                injectMember {
                    method {
                        name = "getGlobalMsg"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "主页面-顶部宝箱提示".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏主页面-顶部战力提示
 */
fun PackageParam.hideMainTopPower(versionCode: Int) {
    when (versionCode) {
        in 878..980 -> {
            findClass("com.qidian.QDReader.ui.activity.MainGroupActivity").hook {
                injectMember {
                    method {
                        name = "getFightRankMsg"
                        emptyParam()
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }
    }
}

/**
 * 隐藏书架-每日导读
 * 上级调用:com.qidian.QDReader.ui.fragment.QDBookShelfPagerFragment.bindView()
 * bindGridAdapter()
 * bindListAdapter()
 */
fun PackageParam.hideBookshelfDailyReading(versionCode: Int) {
    val gridAdapterClass = when (versionCode) {
        in 804..812 -> "com.qidian.QDReader.ui.adapter.j0"
        in 827..860 -> "com.qidian.QDReader.ui.adapter.i0"
        in 868..878 -> "com.qidian.QDReader.ui.adapter.j0"
        in 884..970 -> "com.qidian.QDReader.ui.adapter.g0"
        else -> null
    }
    val listAdapterClass = when (versionCode) {
        in 804..812 -> "com.qidian.QDReader.ui.adapter.h0"
        in 827..860 -> "com.qidian.QDReader.ui.adapter.k0"
        in 868..878 -> "com.qidian.QDReader.ui.adapter.l0"
        in 884..970 -> "com.qidian.QDReader.ui.adapter.i0"
        else -> null
    }
    if (gridAdapterClass == null || listAdapterClass == null) {
        "隐藏书架-每日导读".printlnNotSupportVersion(versionCode)
    } else {
        gridAdapterClass.hook {
            injectMember {
                method {
                    name = "getHeaderItemCount"
                    emptyParam()
                    returnType = IntType
                }
                replaceTo(0)
            }
        }

        listAdapterClass.hook {
            injectMember {
                method {
                    name = "getHeaderItemCount"
                    emptyParam()
                    returnType = IntType
                }
                replaceTo(0)
            }
        }
    }

    if (versionCode > 827) {
        findClass("com.qidian.QDReader.ui.modules.bookshelf.view.BookShelfCheckInView").hook {
            injectMember {
                method {
                    name = "setupDailyReading"
                    returnType = UnitType
                }
                intercept()
            }
        }
    }

}

/**
 * 隐藏书架-去找书
 */
fun PackageParam.hideBookshelfFindBook(versionCode: Int) {
    when (versionCode) {
        in 868..970 -> {
            /**
             * QDBookShelfBrowserRecordHolder
             */
            val needHookClass = when (versionCode) {
                in 868..878 -> "com.qidian.QDReader.ui.viewholder.bookshelf.r"
                in 884..970 -> "com.qidian.QDReader.ui.viewholder.bookshelf.o"
                else -> null
            }
            needHookClass?.hook {
                injectMember {
                    constructor {
                        paramCount(2)
                    }
                    afterHook {
                        args[0]?.let {
                            val view = it as? View
                            view?.visibility = View.GONE
                        }
                    }
                }
            } ?: "隐藏书架-去找书".printlnNotSupportVersion(versionCode)
            findClass("com.qidian.QDReader.ui.modules.bookshelf.adapter.BaseBooksAdapter").hook {
                injectMember {
                    method {
                        name = "getFooterItemCount"
                        emptyParam()
                        returnType = IntType
                    }
                    replaceTo(0)
                }
            }
        }

        else -> "隐藏书架-去找书".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 搜索页面一刀切
 */
fun PackageParam.hideSearchAllView(versionCode: Int) {
    when (versionCode) {
        in 788..980 -> {
            /**
             * 搜索页面一刀切
             */
            findClass("com.qidian.QDReader.ui.fragment.serach.NewSearchHomePageFragment").hook {
                injectMember {
                    method {
                        name = "loadData"
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "屏蔽搜索页面一刀切".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏底部导航栏红点
 * 上级调用位置:com.qidian.QDReader.ui.widget.maintab.PagerSlidingTabStrip.s() smallDotsView
 */
fun PackageParam.hideBottomRedDot(versionCode: Int) {
    val needHookClass = when (versionCode) {
        in 758..768 -> "com.qidian.QDReader.ui.widget.maintab.a"
        in 772..878 -> "com.qidian.QDReader.ui.widget.maintab.e"
        in 884..970 -> "com.qidian.QDReader.ui.widget.maintab.b"
        else -> null
    }
    val needHookMethod = when (versionCode) {
        in 758..878 -> "h"
        884 -> "e"
        in 890..900 -> "h"
        906 -> "e"
        in 916..924 -> "h"
        in 932..958 -> "e"
        970 -> "g"
        else -> null
    }
    if (needHookClass == null || needHookMethod == null) {
        "隐藏底部导航栏红点".printlnNotSupportVersion(versionCode)
        return
    }
    needHookClass.hook {
        injectMember {
            method {
                name = needHookMethod
                returnType = IntType
            }
            replaceTo(1)
        }
    }
}

/**
 * 隐藏底部导航栏
 */
fun PackageParam.hideBottomNavigation(versionCode: Int) {

    val needHookMethod = when (versionCode) {
        in 872..878 -> "s"
        in 884..958 -> "p"
        970 -> "s"
        else -> null
    }

    when (versionCode) {
        in 827..958 -> {
            findClass("com.qidian.QDReader.ui.widget.maintab.PagerSlidingTabStrip").hook {
                injectMember {
                    method {
                        name = needHookMethod!!
                        emptyParam()
                        returnType = UnitType
                    }
                    afterHook {
                        val i = instance.getView<LinearLayout>("i")
                        i?.let {
                            val childCount = it.childCount
                            for (index in 0..<childCount) {
                                val childView = it.getChildAt(index) as? RelativeLayout
                                childView?.let {
                                    val childViewChildCount = childView.childCount
                                    for (childViewIndex in 0..<childViewChildCount) {
                                        if (childView.getChildAt(childViewIndex) is TextView) {
                                            val textView =
                                                childView.getChildAt(childViewIndex) as TextView
                                            val text = textView.text
                                            HookEntry.optionEntity.viewHideOption.homeOption.configurations.findOrPlus(
                                                title = "主页底部导航栏$text"
                                            ) {
                                                val view = textView.parent as? View
                                                view?.visibility = View.GONE
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        970 -> {
            findClass("com.qidian.QDReader.ui.widget.maintab.PagerSlidingTabStrip").hook {
                injectMember {
                    method {
                        name = needHookMethod!!
                        emptyParam()
                        returnType = UnitType
                    }
                    afterHook {
                        val i = instance.getView<LinearLayout>("j")
                        i?.let {
                            val childCount = it.childCount
                            for (index in 0..<childCount) {
                                val childView = it.getChildAt(index) as? FrameLayout
                                childView?.let {
                                    val childViewChildCount = childView.childCount
                                    for (childViewIndex in 0..<childViewChildCount) {
                                        val name =
                                            childView.getChildAt(childViewIndex).javaClass.name
                                        if (name == "androidx.constraintlayout.widget.ConstraintLayout") {
                                            val constraintLayout =
                                                childView.getChildAt(childViewIndex) as ViewGroup
                                            val constraintLayoutCount = constraintLayout.childCount
                                            for (constraintLayoutIndex in 0..<constraintLayoutCount) {
                                                val childViewChild3 = constraintLayout.getChildAt(
                                                    constraintLayoutIndex
                                                )
                                                if (childViewChild3 is TextView) {
                                                    HookEntry.optionEntity.viewHideOption.homeOption.configurations.findOrPlus(
                                                        title = "主页底部导航栏${childViewChild3.text}"
                                                    ) {
                                                        val view = childViewChild3.parent.parent as? View
                                                        view?.visibility = View.GONE
                                                    }
                                                }
                                            }
                                        }
                                    }
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
 * 隐藏底部导航栏-发现
 */
fun PackageParam.hideBottomNavigationFind(versionCode: Int) {
    when (versionCode) {
        in 792..880 -> {
            findClass("com.qidian.QDReader.ui.widget.maintab.PagerSlidingTabStrip").hook {
                injectMember {
                    method {
                        name = "s"
                        emptyParam()
                        returnType = UnitType
                    }
                    afterHook {
                        val i = instance.getView<LinearLayout>("i")
                        i?.let {
                            val view = it.getChildAt(2)
                            view?.visibility = View.GONE
                        }
                    }
                }
            }
        }

        else -> "隐藏底部导航栏-发现".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 发现-隐藏控件
 */
fun PackageParam.findViewHide(
    versionCode: Int,
) {
    when (versionCode) {
        in 860..980 -> {
            findClass("com.qidian.QDReader.ui.fragment.FindFragmentReborn").hook {
                injectMember {
                    method {
                        name = "parserFindBean"
                        param(
                            "com.qidian.QDReader.repository.entity.FindBean".toClass(),
                            BooleanType
                        )
                        returnType = ListClass
                    }
                    beforeHook {
                        args[0]?.let { findBean ->
                            val adItems = findBean.getParam<MutableList<*>>("adItems")
                            adItems?.let {
                                val iterator = it.iterator()
                                while (iterator.hasNext()) {
                                    val next = iterator.next().toJSONString().parseObject()
                                    val showName =
                                        next?.getString("showName") ?: next?.getString("ShowName")
                                    if (!showName.isNullOrBlank()) {
                                        HookEntry.optionEntity.viewHideOption.findOption.advItem.findOrPlus(
                                            showName,
                                            iterator
                                        )
                                        updateOptionEntity()
                                    }
                                }
                            }

                            if (HookEntry.optionEntity.viewHideOption.findOption.broadCasts) {
                                findBean.getParam<MutableList<*>>("broadcasts")?.clear()
                            }

                            if (HookEntry.optionEntity.viewHideOption.findOption.feedsItem) {
                                findBean.getParam<MutableList<*>>("feedsItems")?.clear()
                            }

                            val filterConf = findBean.getParam<MutableList<*>>("filterConf")
                            filterConf?.let {
                                val iterator = it.iterator()
                                while (iterator.hasNext()) {
                                    val next = iterator.next().toJSONString().parseObject()
                                    val name = next?.getString("desc") ?: next?.getString("Desc")
                                    if (!name.isNullOrBlank()) {
                                        HookEntry.optionEntity.viewHideOption.findOption.filterConfItem.findOrPlus(
                                            name,
                                            iterator
                                        )
                                        updateOptionEntity()
                                    }
                                }
                            }

                            val headItems = findBean.getParam<MutableList<*>>("headItems")
                            headItems?.let {
                                val iterator = it.iterator()
                                while (iterator.hasNext()) {
                                    val next = iterator.next().toJSONString().parseObject()
                                    val name =
                                        next?.getString("showName") ?: next?.getString("ShowName")
                                    if (!name.isNullOrBlank()) {
                                        HookEntry.optionEntity.viewHideOption.findOption.headItem.findOrPlus(
                                            name,
                                            iterator
                                        )
                                        updateOptionEntity()
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        else -> "发现-隐藏控件".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 我-隐藏控件
 */
fun PackageParam.accountViewHide(
    versionCode: Int,
) {
    when (versionCode) {
        in 792..808 -> {
            /**
             * 我-隐藏控件
             */
            findClass("com.qidian.QDReader.ui.fragment.QDUserAccountFragment").hook {
                injectMember {
                    method {
                        name = "lambda\$loadData\$3"
                        param("com.qidian.QDReader.repository.entity.UserAccountDataBean".toClass())
                        returnType = UnitType
                    }
                    beforeHook {
                        args[0]?.let {
                            val items = it.getParam<MutableList<*>>("Items")
                            items?.let { list ->
                                safeRun {
                                    val iterator = list.iterator()
                                    while (iterator.hasNext()) {
                                        val item = iterator.next() as? MutableList<*>
                                        item?.let { list2 ->
                                            val iterator2 = list2.iterator()
                                            while (iterator2.hasNext()) {
                                                val item2 =
                                                    iterator2.next().toJSONString().parseObject()
                                                val showName = item2?.getString("showName")
                                                if (!showName.isNullOrBlank()) {
                                                    HookEntry.optionEntity.viewHideOption.accountOption.configurations.findOrPlus(
                                                        title = showName,
                                                        iterator = iterator2
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        in 812..900 -> {
            findClass("com.qidian.QDReader.ui.fragment.QDUserAccountFragment").hook {
                injectMember {
                    method {
                        name = "renderUIByData"
                        param("com.qidian.QDReader.repository.entity.UserAccountDataBean".toClass())
                        returnType = UnitType
                    }
                    beforeHook {
                        args[0]?.let {
                            val items = it.getParam<MutableList<*>>("Items")
                            items?.let { list ->
                                safeRun {
                                    val iterator = list.iterator()
                                    while (iterator.hasNext()) {
                                        val item = iterator.next() as? MutableList<*>
                                        item?.let { list2 ->
                                            val iterator2 = list2.iterator()
                                            while (iterator2.hasNext()) {
                                                val item2 =
                                                    iterator2.next().toJSONString().parseObject()
                                                val showName = item2?.getString("showName")
                                                if (!showName.isNullOrBlank()) {
                                                    HookEntry.optionEntity.viewHideOption.accountOption.configurations.findOrPlus(
                                                        title = showName,
                                                        iterator = iterator2
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (versionCode in 868..900) {
                findClass("com.qidian.QDReader.ui.fragment.main_group.QDUserAccountRebornFragment").hook {
                    injectMember {
                        method {
                            name = "processAccountItem"
                            param("com.qidian.QDReader.repository.entity.user_account.UserAccountItemBean".toClass())
                            returnType = ListClass
                        }
                        afterHook {
                            val userAccountItemBean = args[0] ?: return@afterHook
//                            val member = userAccountItemBean.getParam<Any>("member")
//                            member?.setParams(
//                                "isMember" to 1,
//                                "title" to "至尊卡",
//                                "subTitle" to "会员到期时间：2099-12-31"
//                            )
                            val benefitButtonList =
                                userAccountItemBean.getParam<MutableList<*>>("benefitButtonList")
                            if (!benefitButtonList.isNullOrEmpty()) {
                                val iterator = benefitButtonList.iterator()
                                while (iterator.hasNext()) {
                                    val next = iterator.next().toJSONString().parseObject()
                                    val name = next?.getString("name") ?: next?.getString("Name")
                                    if (!name.isNullOrBlank()) {
                                        HookEntry.optionEntity.viewHideOption.accountOption.newConfiguration.findOrPlus(
                                            title = name,
                                            iterator = iterator
                                        )
                                    }
                                }


                            }

                            val functionButtonList =
                                userAccountItemBean.getParam<MutableList<*>>("functionButtonList")
                            if (!functionButtonList.isNullOrEmpty()) {
                                val iterator = functionButtonList.iterator()
                                while (iterator.hasNext()) {
                                    val next = iterator.next().toJSONString().parseObject()
                                    val name = next?.getString("name") ?: next?.getString("Name")
                                    if (!name.isNullOrBlank()) {
                                        HookEntry.optionEntity.viewHideOption.accountOption.newConfiguration.findOrPlus(
                                            title = name,
                                            iterator = iterator
                                        )
                                    }
                                }


                            }

                            val bottomButtonList =
                                userAccountItemBean.getParam<MutableList<*>>("bottomButtonList")
                            if (!bottomButtonList.isNullOrEmpty()) {
                                val iterator = bottomButtonList.iterator()
                                while (iterator.hasNext()) {
                                    val next = iterator.next().toJSONString().parseObject()
                                    val name = next?.getString("name") ?: next?.getString("Name")
                                    if (!name.isNullOrBlank()) {
                                        HookEntry.optionEntity.viewHideOption.accountOption.newConfiguration.findOrPlus(
                                            title = name,
                                            iterator = iterator
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        in 906..980 -> {
            findClass("com.qidian.QDReader.ui.fragment.main_group.QDUserAccountRebornFragment").hook {
                injectMember {
                    method {
                        name = "processAccountItem"
                        param("com.qidian.QDReader.repository.entity.user_account.UserAccountItemBean".toClass())
                        returnType = ListClass
                    }
                    afterHook {
                        val userAccountItemBean = args[0] ?: return@afterHook
//                            val member = userAccountItemBean.getParam<Any>("member")
//                            member?.setParams(
//                                "isMember" to 1,
//                                "title" to "至尊卡",
//                                "subTitle" to "会员到期时间：2099-12-31"
//                            )
                        val benefitButtonList =
                            userAccountItemBean.getParam<MutableList<*>>("benefitButtonList")
                        if (!benefitButtonList.isNullOrEmpty()) {
                            val iterator = benefitButtonList.iterator()
                            while (iterator.hasNext()) {
                                val next = iterator.next().toJSONString().parseObject()
                                val name = next?.getString("name") ?: next?.getString("Name")
                                if (!name.isNullOrBlank()) {
                                    HookEntry.optionEntity.viewHideOption.accountOption.newConfiguration.findOrPlus(
                                        title = name,
                                        iterator = iterator
                                    )
                                }
                            }


                        }

                        val functionButtonList =
                            userAccountItemBean.getParam<MutableList<Any?>>("functionButtonList")
                        if (!functionButtonList.isNullOrEmpty()) {
                            val iterator = functionButtonList.iterator()
                            while (iterator.hasNext()) {
                                val next = iterator.next().toJSONString().parseObject()
                                val name = next?.getString("name") ?: next?.getString("Name")
                                if (!name.isNullOrBlank()) {
                                    HookEntry.optionEntity.viewHideOption.accountOption.newConfiguration.findOrPlus(
                                        title = name,
                                        iterator = iterator
                                    )
                                }
                            }

                            // 添加列表
                            if (HookEntry.optionEntity.hideBenefitsOption.configurations[1].selected) {
                                val hideWelfareList =
                                    HookEntry.optionEntity.hideBenefitsOption.hideWelfareList
                                if (hideWelfareList.isNotEmpty()) {
                                    val copyFunctionButtonList = functionButtonList
                                    copyFunctionButtonList.first()?.let { item ->
                                        val className = item::class.java.name
                                        if ("com.qidian.QDReader.repository.entity.user_account.FunctionButton" == className) {
                                            hideWelfareList.forEach { model ->
                                                val copyFunctionButton =
                                                    item::class.java.constructor {
                                                        param(
                                                            StringClass,
                                                            StringClass,
                                                            StringClass,
                                                            StringClass,
                                                            StringClass,
                                                            "com.qidian.QDReader.repository.entity.RedDot".toClass(),
                                                            LongType,
                                                            LongType,
                                                            IntType,
                                                            IntType
                                                        )
                                                    }.get().call(
                                                        "",
                                                        model.imageUrl,
                                                        model.title,
                                                        "",
                                                        model.actionUrl,
                                                        null,
                                                        0L,
                                                        0L,
                                                        0,
                                                        0
                                                    )

                                                copyFunctionButtonList += copyFunctionButton
                                            }
                                            if (copyFunctionButtonList.isNotEmpty()) {
                                                userAccountItemBean.setParam(
                                                    "functionButtonList",
                                                    copyFunctionButtonList
                                                )
                                            }

                                        }
                                        "className:$className".loge()
                                    }
                                }
                            }
                        }

                        val bottomButtonList =
                            userAccountItemBean.getParam<MutableList<*>>("bottomButtonList")
                        if (!bottomButtonList.isNullOrEmpty()) {
                            val iterator = bottomButtonList.iterator()
                            while (iterator.hasNext()) {
                                val next = iterator.next().toJSONString().parseObject()
                                val name = next?.getString("name") ?: next?.getString("Name")
                                if (!name.isNullOrBlank()) {
                                    HookEntry.optionEntity.viewHideOption.accountOption.newConfiguration.findOrPlus(
                                        title = name,
                                        iterator = iterator
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }

        else -> "我-隐藏控件".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏我-右上角消息红点
 */
fun PackageParam.accountRightTopRedDot(versionCode: Int) {
    when (versionCode) {
        in 812..980 -> {
            findClass("com.qidian.QDReader.component.config.QDAppConfigHelper\$Companion").hook {
                injectMember {
                    method {
                        name = "isEnableUniteMessage"
                        emptyParam()
                        returnType = BooleanType
                    }
                    replaceToFalse()
                }
            }

            if (versionCode >= 868) {
                findClass("com.qidian.QDReader.ui.fragment.main_group.QDUserAccountRebornFragment").hook {
                    injectMember {
                        method {
                            name = "setNewMsgText"
                            paramCount(2)
                            returnType = UnitType
                        }
                        afterHook {
                            val msgDotView = instance.getParam<View>("msgDotView")
                            msgDotView?.visibility = View.VISIBLE
                        }
                    }

                    injectMember {
                        method {
                            name = "initView"
                            paramCount(1)
                            returnType = UnitType
                        }
                        afterHook {
                            val msgDotView = instance.getView<View>("msgDotView")
                            msgDotView?.visibility = View.VISIBLE
                        }
                    }

                    /*
                    injectMember {
                        method {
                            name = "updateNewMsgUnReadCount"
                            emptyParam()
                            returnType = UnitType
                        }
                       intercept()
                    }

                    injectMember {
                        method {
                            name = "updateRedPoint"
                            emptyParam()
                            returnType = UnitType
                        }
                        intercept()
                    }

                    injectMember {
                        method {
                            name = "updateRedPointReturnShow"
                            emptyParam()
                            returnType = BooleanType
                        }
                        replaceToFalse()
                    }

                     */
                }
            }

        }

        else -> "我-右上角消息红点".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 我-移除青少年模式弹框
 * 上级调用:com.qidian.QDReader.bll.helper.QDTeenagerHelper$Companion.h() new-instance v2, g1
 */
fun PackageParam.removeQSNYDialog(versionCode: Int) {
    val needHookClass = when (versionCode) {
        in 758..854 -> "com.qidian.QDReader.bll.helper.g1"
        in 858..868 -> "com.qidian.QDReader.bll.helper.m1"
        in 872..878 -> "com.qidian.QDReader.bll.helper.k1"
        in 884..900 -> "com.qidian.QDReader.bll.helper.h1"
        in 906..924 -> "com.qidian.QDReader.bll.helper.n1"
        in 932..970 -> "com.qidian.QDReader.bll.helper.m0"
        else -> null
    }
    needHookClass?.hook {
        injectMember {
            method {
                name = "run"
                emptyParam()
                returnType = UnitType
            }
            intercept()
        }
    } ?: "移除青少年模式弹框".printlnNotSupportVersion(versionCode)


    /*
        /**
         * 上级调用位置:com.qidian.QDReader.bll.manager.QDTeenagerManager.teenWorkDialog
         */
        val dialogClassName: String? = when (versionCode) {
            in 758..768 -> "com.qidian.QDReader.bll.helper.v1"
            772 -> "com.qidian.QDReader.bll.helper.w1"
            in 776..800 -> "com.qidian.QDReader.bll.helper.t1"
            else -> null
        }
        dialogClassName?.hook {
            injectMember {
                method {
                    name = "show"
                    superClass()
                }
                beforeHook {
                    printCallStack(instance.javaClass.name)
                }
                //intercept()
            }
        } ?: "移除青少年模式弹框".printlnNotSupportVersion(versionCode)

     */

}

/**
 * 书籍详情-隐藏控件
 * @param versionCode 版本号
 * @param isNeedHideCqzs 是否需要隐藏出圈指数
 * @param isNeedHideRybq 是否需要隐藏荣誉标签
 * @param isNeedHideQqGroups 是否需要隐藏QQ群
 * @param isNeedHideSyq 是否需要隐藏书友圈
 * @param isNeedHideSyb 是否需要隐藏书友榜
 * @param isNeedHideYpjz 是否需要隐藏月票金主
 * @param isNeedHideCenterAd 是否需要隐藏本书看点|中心广告
 * @param isNeedHideFloatAd 是否需要隐藏浮窗广告
 * @param isNeedHideBookRecommend 是否需要隐藏同类作品推荐
 * @param isNeedHideBookRecommend2 是否需要隐藏看过此书的人还看过
 */
fun PackageParam.bookDetailHide(
    versionCode: Int,
    isNeedHideCqzs: Boolean = HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations[0].selected,
    isNeedHideRybq: Boolean = HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations[1].selected,
    isNeedHideQqGroups: Boolean = HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations[2].selected,
    isNeedHideSyq: Boolean = HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations[3].selected,
    isNeedHideSyb: Boolean = HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations[4].selected,
    isNeedHideYpjz: Boolean = HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations[5].selected,
    isNeedHideCenterAd: Boolean = HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations[6].selected,
    isNeedHideFloatAd: Boolean = HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations[7].selected,
    isNeedHideBookRecommend: Boolean = HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations[8].selected,
    isNeedHideBookRecommend2: Boolean = HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurations[9].selected,
) {
    when (versionCode) {
        in 808..812 -> {

            findClass("com.qidian.QDReader.ui.activity.QDBookDetailActivity").hook {
                injectMember {
                    method {
                        name = "notifyData"
                        param(BooleanType)
                        returnType = UnitType
                    }
                    beforeHook {
                        val mBookDetail = instance.getParam<Any>("mBookDetail")
                        mBookDetail?.let {

                            val baseBookInfo = it.getParam<Any>("baseBookInfo")
                            baseBookInfo?.let {
                                /**
                                 * 荣誉标签
                                 */
                                if (isNeedHideRybq) {
                                    val honorTagList =
                                        baseBookInfo.getParam<MutableList<*>>("honorTagList")
                                    honorTagList?.clear()
                                }

                                /**
                                 * 月票金主
                                 */
                                if (isNeedHideYpjz) {
                                    val monthTopUser =
                                        baseBookInfo.getParam<MutableList<*>>("monthTopUser")
                                    monthTopUser?.clear()
                                }

                            }

                            /**
                             * QQ群
                             */
                            if (isNeedHideQqGroups) {
                                val qqGroup = it.getParam<MutableList<*>>("qqGroup")
                                qqGroup?.clear()
                            }

                            /**
                             * 同类作品推荐
                             */
                            if (isNeedHideBookRecommend) {
                                val sameRecommend = it.getParam<MutableList<*>>("sameRecommend")
                                sameRecommend?.clear()
                            }

                            /**
                             * 看过此书的人还看过
                             */
                            if (isNeedHideBookRecommend2) {
                                val bookFriendsRecommend =
                                    it.getParam<MutableList<*>>("bookFriendsRecommend")
                                bookFriendsRecommend?.clear()
                            }

                        }
                    }
                }

                if (isNeedHideCenterAd) {
                    injectMember {
                        method {
                            name = "getAD\$lambda-74\$lambda-73\$lambda-72"
                            returnType = UnitType
                        }
                        intercept()
                    }
                }

                if (isNeedHideFloatAd) {
                    injectMember {
                        method {
                            name = "getFloatingAd"
                            emptyParam()
                            returnType = UnitType
                        }
                        intercept()
                    }
                }

                if (isNeedHideCqzs) {
                    /**
                     * 出圈指数
                     */
                    injectMember {
                        method {
                            name = "addCircleMarkInfo"
                            param("com.qidian.QDReader.repository.entity.OutCircleIndexInfo".toClass())
                            returnType = UnitType
                        }
                        afterHook {
                            val view = XposedHelpers.callMethod(
                                instance,
                                "findViewById",
                                0x7F090442
                            ) as? View
                            view?.visibility = View.GONE
                        }
                    }
                }

            }

            if (isNeedHideSyb) {
                /**
                 * 隐藏书友榜
                 */
                findClass("com.qidian.QDReader.ui.view.BookFansModuleView").hook {
                    injectMember {
                        method {
                            name = "d"
                            param(
                                LongType,
                                StringClass,
                                "com.qidian.QDReader.repository.entity.FansInfo".toClass(),
                                ListClass
                            )
                            returnType = UnitType
                        }
                        afterHook {
                            val bookFansModuleView = instance as? LinearLayout
                            bookFansModuleView?.visibility = View.GONE
                        }
                    }
                }
            }

            if (isNeedHideSyq) {
                /**
                 * 隐藏书友圈
                 */
                findClass("com.qidian.QDReader.ui.view.BookCircleModuleView").hook {
                    injectMember {
                        method {
                            name = "bind"
                            returnType = UnitType
                        }
                        afterHook {
                            val bookCircleModuleView = instance as? LinearLayout
                            bookCircleModuleView?.visibility = View.GONE
                        }
                    }
                }
            }
        }

        in 827..970 -> {
            findClass("com.qidian.QDReader.ui.activity.QDBookDetailActivity").hook {
                injectMember {
                    method {
                        name = "notifyData"
                        param(BooleanType)
                        returnType = UnitType
                    }
                    beforeHook {
                        val mBookDetail = instance.getParam<Any>("mBookDetail")
                        mBookDetail?.let {

                            val baseBookInfo = it.getParam<Any>("baseBookInfo")
                            baseBookInfo?.let {
                                /**
                                 * 荣誉标签
                                 */
                                if (isNeedHideRybq) {
                                    val honorTagList =
                                        baseBookInfo.getParam<MutableList<*>>("honorTagList")
                                    honorTagList?.clear()
                                }

                                /**
                                 * 月票金主
                                 */
                                if (isNeedHideYpjz) {
                                    val monthTopUser =
                                        baseBookInfo.getParam<MutableList<*>>("monthTopUser")
                                    monthTopUser?.clear()
                                }

                            }

                            /**
                             * QQ群
                             */
                            if (isNeedHideQqGroups) {
                                val qqGroup = it.getParam<MutableList<*>>("qqGroup")
                                qqGroup?.clear()
                            }

                            /**
                             * 同类作品推荐
                             */
                            if (isNeedHideBookRecommend) {
                                val sameRecommend = it.getParam<MutableList<*>>("sameRecommend")
                                sameRecommend?.clear()
                            }

                            /**
                             * 看过此书的人还看过
                             */
                            if (isNeedHideBookRecommend2) {
                                val bookFriendsRecommend =
                                    it.getParam<MutableList<*>>("bookFriendsRecommend")
                                bookFriendsRecommend?.clear()
                            }

                        }
                    }
                }

                if (isNeedHideCenterAd) {
                    injectMember {
                        method {
                            name {
                                it.contains("getAD\$lambda")
                            }
                            paramCount(2)
                            returnType = UnitType
                        }
                        intercept()
                    }
                }

                if (isNeedHideFloatAd) {
                    injectMember {
                        method {
                            name = "getFloatingAd"
                            emptyParam()
                            returnType = UnitType
                        }
                        intercept()
                    }
                }

                if (isNeedHideCqzs) {
                    /**
                     * 出圈指数
                     */
                    injectMember {
                        method {
                            name = "addCircleMarkInfo"
                            param("com.qidian.QDReader.repository.entity.OutCircleIndexInfo".toClass())
                            returnType = UnitType
                        }
                        afterHook {
                            val tvCircleMarkLevelId = when (versionCode) {
                                in 827..924 -> 0x7F090442
                                932 -> 0x7F0904A8
                                938 -> 0x7F0919CA
                                944 -> 0x7F0919DF
                                950 -> 0x7F091A0B
                                958 -> 0x7F091A12
                                970 -> 0x7F091AA5
                                else -> null
                            }
                            if (tvCircleMarkLevelId != null) {
                                val view = XposedHelpers.callMethod(
                                    instance,
                                    "findViewById",
                                    tvCircleMarkLevelId
                                ) as? View
                                view?.visibility = View.GONE
                            } else {
                                "隐藏出圈指数".printlnNotSupportVersion(versionCode)
                            }
                        }
                    }
                }

            }

            if (isNeedHideSyb) {
                /**
                 * 隐藏书友榜
                 */
                val bookFansModuleNeedHookMethod = when (versionCode) {
                    in 827..878 -> "d"
                    in 884..970 -> "a"
                    else -> null
                }
                if (bookFansModuleNeedHookMethod == null) {
                    "隐藏书友榜".printlnNotSupportVersion(versionCode)
                } else {
                    findClass("com.qidian.QDReader.ui.view.BookFansModuleView").hook {
                        injectMember {
                            method {
                                name = bookFansModuleNeedHookMethod
                                param(
                                    LongType,
                                    StringClass,
                                    "com.qidian.QDReader.repository.entity.FansInfo".toClass(),
                                    ListClass
                                )
                                returnType = UnitType
                            }
                            afterHook {
                                val bookFansModuleView = instance as? LinearLayout
                                bookFansModuleView?.visibility = View.GONE
                            }
                        }
                    }
                }

            }

            if (isNeedHideSyq) {
                /**
                 * 隐藏书友圈
                 */
                findClass("com.qidian.QDReader.ui.view.BookCircleModuleView").hook {
                    injectMember {
                        method {
                            name = "bind"
                            returnType = UnitType
                        }
                        afterHook {
                            val bookCircleModuleView = instance as? LinearLayout
                            bookCircleModuleView?.visibility = View.GONE
                        }
                    }
                }
            }
        }

        else -> "书籍详情-隐藏控件".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏阅读页-章末底部月票打赏红包
 */
fun PackageParam.hideReadPageBottom(versionCode: Int) {
    when (versionCode) {
        in 827..980 -> {
            findClass("com.qidian.QDReader.readerengine.view.QDSuperEngineView").hook {
                injectMember {
                    method {
                        name = "initInteractionBarView"
                        returnType = UnitType
                    }
                    afterHook {
                        val mInteractionBarView =
                            instance.getView<LinearLayout>("mInteractionBarView")
                        mInteractionBarView?.visibility = View.GONE
                    }
                }
            }
        }

        else -> "隐藏阅读页-章末底部月票打赏红包".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 漫画-隐藏轮播图广告
 * 上级调用: com.qidian.QDReader.repository.entity.ComicSquareItem.getComicSquareAdItems()
 */
fun PackageParam.comicHideBannerAd(versionCode: Int) {
    val needHookClass = when (versionCode) {
        812 -> "la.h"
        827 -> "oa.h"
        in 834..843 -> "na.h"
        in 850..860 -> "na.g"
        868 -> "pa.g"
        872 -> "na.g"
        878 -> "ma.g"
        in 884..890 -> "fa.d"
        in 896..900 -> "ga.d"
        in 906..916 -> "ka.d"
        924 -> "la.d"
        in 932..938 -> "oa.d"
        944 -> "na.d"
        950 -> "pa.d"
        958 -> "ma.d"
        970 -> "la.d"
        else -> null
    }
    needHookClass?.hook {
        injectMember {
            method {
                name = "bindView"
                emptyParam()
                returnType = UnitType
            }
            afterHook {
                val g = instance.getView<FrameLayout>("g")
                g?.let {
                    // 获取 g 的父控件并隐藏
                    val parent = g.parent as? ViewGroup
                    parent?.visibility = View.GONE
                }

            }
        }
    } ?: "漫画-隐藏轮播图广告".printlnNotSupportVersion(versionCode)
}

/**
 * 隐藏小红点
 */
fun PackageParam.hideRedDot(versionCode: Int) {
    when (versionCode) {
        in 868..980 -> {
            findClass("com.qidian.QDReader.framework.widget.customerview.SmallDotsView").hook {
                injectMember {
                    method {
                        name = "onDraw"
                        paramCount(1)
                        returnType = UnitType
                    }
                    intercept()
                }
            }
        }

        else -> "隐藏小红点".printlnNotSupportVersion(versionCode)
    }
}
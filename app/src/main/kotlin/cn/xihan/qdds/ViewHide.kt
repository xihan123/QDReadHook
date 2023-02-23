package cn.xihan.qdds

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
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
        in 868..872 -> {
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
        872 -> {
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
        in 812..880 -> {
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
 * 隐藏书架-每日导读
 * 上级调用:com.qidian.QDReader.ui.fragment.QDBookShelfPagerFragment.bindView()
 * bindGridAdapter()
 * bindListAdapter()
 */
fun PackageParam.hideBookshelfDailyReading(versionCode: Int) {
    when (versionCode) {
        in 804..812 -> {
            findClass("com.qidian.QDReader.ui.adapter.j0").hook {
                injectMember {
                    method {
                        name = "getHeaderItemCount"
                        emptyParam()
                        returnType = IntType
                    }
                    replaceTo(0)
                }
            }

            findClass("com.qidian.QDReader.ui.adapter.h0").hook {
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

        in 827..872 -> {
            val gridAdapterClass = when (versionCode) {
                in 827..860 -> "com.qidian.QDReader.ui.adapter.i0"
                in 868..872 -> "com.qidian.QDReader.ui.adapter.j0"
                else -> null
            }
            val listAdapterClass = when (versionCode) {
                in 827..860 -> "com.qidian.QDReader.ui.adapter.k0"
                in 868..872 -> "com.qidian.QDReader.ui.adapter.l0"
                else -> null
            }
            gridAdapterClass?.hook {
                injectMember {
                    method {
                        name = "getHeaderItemCount"
                        emptyParam()
                        returnType = IntType
                    }
                    replaceTo(0)
                }
            }

            listAdapterClass?.hook {
                injectMember {
                    method {
                        name = "getHeaderItemCount"
                        emptyParam()
                        returnType = IntType
                    }
                    replaceTo(0)
                }
            }

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

        else -> "隐藏书架-每日导读".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏书架-去找书
 */
fun PackageParam.hideBookshelfFindBook(versionCode: Int) {
    when (versionCode) {
        in 868..872 -> {

            /**
             * QDBookShelfBrowserRecordHolder
             */
            findClass("com.qidian.QDReader.ui.viewholder.bookshelf.r").hook {
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
            }

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
        in 788..880 -> {
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
        in 772..872 -> "com.qidian.QDReader.ui.widget.maintab.e"
        else -> null
    }
    needHookClass?.hook {
        injectMember {
            method {
                name = "h"
                returnType = IntType
            }
            replaceTo(1)
        }
    } ?: "隐藏底部导航栏红点".printlnNotSupportVersion(versionCode)
}

/**
 * 隐藏底部导航栏
 */
fun PackageParam.hideBottomNavigation(versionCode: Int) {
    when (versionCode) {
        872 -> {
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
                            val childCount = it.childCount
                            for (index in 0 until childCount) {
                                val childView = it.getChildAt(index) as? RelativeLayout
                                childView?.let {
                                    val childViewChildCount = childView.childCount
                                    for (childViewIndex in 0 until childViewChildCount) {
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

        else -> "隐藏底部导航栏".printlnNotSupportVersion(versionCode)
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
        in 860..872 -> {
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
                                    val showName = next?.getString("showName")
                                    if (!showName.isNullOrBlank()) {
                                        HookEntry.optionEntity.viewHideOption.findOption.advItem.apply {
                                            find { it1 -> it1.title == showName }?.let { advItem ->
                                                if (advItem.selected) {
                                                    iterator.remove()
                                                }
                                            } ?: plusAssign(
                                                OptionEntity.SelectedModel(
                                                    showName
                                                )
                                            )

                                        }
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
                                    val name = next?.getString("desc")
                                    if (!name.isNullOrBlank()) {
                                        HookEntry.optionEntity.viewHideOption.findOption.filterConfItem.apply {
                                            find { it1 -> it1.title == name }?.let { filterConf ->
                                                if (filterConf.selected) {
                                                    iterator.remove()
                                                }
                                            } ?: plusAssign(
                                                OptionEntity.SelectedModel(
                                                    name
                                                )
                                            )

                                        }
                                        updateOptionEntity()
                                    }
                                }
                            }

                            val headItems = findBean.getParam<MutableList<*>>("headItems")
                            headItems?.let {
                                val iterator = it.iterator()
                                while (iterator.hasNext()) {
                                    val next = iterator.next().toJSONString().parseObject()
                                    val name = next?.getString("showName")
                                    if (!name.isNullOrBlank()) {
                                        HookEntry.optionEntity.viewHideOption.findOption.headItem.apply {
                                            find { it1 -> it1.title == name }?.let { headItem ->
                                                if (headItem.selected) {
                                                    iterator.remove()
                                                }
                                            } ?: plusAssign(
                                                OptionEntity.SelectedModel(
                                                    name
                                                )
                                            )
                                        }
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

        in 812..880 -> {
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

            if (versionCode >= 868) {
                findClass("com.qidian.QDReader.ui.fragment.main_group.QDUserAccountRebornFragment").hook {
                    injectMember {
                        method {
                            name = "processAccountItem"
                            param("com.qidian.QDReader.repository.entity.user_account.UserAccountItemBean".toClass())
                            returnType = ListClass
                        }
                        afterHook {
                            val userAccountItemBean = args[0] ?: return@afterHook
                            val benefitButtonList =
                                userAccountItemBean.getParam<MutableList<*>>("benefitButtonList")
                            if (!benefitButtonList.isNullOrEmpty()) {
                                val iterator = benefitButtonList.iterator()
                                while (iterator.hasNext()) {
                                    val next = iterator.next().toJSONString().parseObject()
                                    val name = next?.getString("name")
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
                                    val name = next?.getString("name")
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
                                    val name = next?.getString("name")
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

        else -> "我-隐藏控件".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏我-右上角消息红点
 */
fun PackageParam.accountRightTopRedDot(versionCode: Int) {
    when (versionCode) {
        in 812..880 -> {
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
                            val msgDotView = getParam<View>("msgDotView")
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
                            val msgDotView = getParam<View>("msgDotView")
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
        872 -> "com.qidian.QDReader.bll.helper.k1"
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

        in 827..880 -> {
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

        else -> "书籍详情-隐藏控件".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏阅读页-章末底部月票打赏红包
 */
fun PackageParam.hideReadPageBottom(versionCode: Int) {
    when (versionCode) {
        in 827..880 -> {
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
        in 868..880 -> {
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

        else -> "隐藏全部小红点".printlnNotSupportVersion(versionCode)
    }
}

/*
/**
 * 我-隐藏控件配置
 */
fun Context.showHideOptionDialog() {
    val linearLayout = CustomLinearLayout(this, isAutoWidth = false)
    val homeTextView = CustomTextView(
        context = this,
        mText = "主页-隐藏控件列表",
        isBold = true
    ) {
        val shieldOptionList =
            HookEntry.optionEntity.viewHideOption.homeOption.homeOptionList
        val checkedItems = BooleanArray(shieldOptionList.size)
        if (HookEntry.optionEntity.viewHideOption.homeOption.homeOptionSelectedList.isNotEmpty()) {
            safeRun {
                shieldOptionList.forEachIndexed { index, _ ->
                    // 对比 shieldOptionList 和 optionEntity.viewHideOption.accountOption.configurationsSelectedOptionList 有相同的元素就设置为true
                    if (HookEntry.optionEntity.viewHideOption.homeOption.homeOptionSelectedList.any { it == index }) {
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
                    HookEntry.optionEntity.viewHideOption.homeOption.homeOptionSelectedList += index
                } else {
                    HookEntry.optionEntity.viewHideOption.homeOption.homeOptionSelectedList -= index
                }
            }
        }
    }
    val searchHideAllViewOption = CustomSwitch(
        context = this,
        title = "搜索页面一刀切",
        isEnable = HookEntry.optionEntity.viewHideOption.enableSearchHideAllView
    ) {
        HookEntry.optionEntity.viewHideOption.enableSearchHideAllView = it
    }
    val enableDisableQSNModeDialogOption = CustomSwitch(
        context = this,
        title = "关闭青少年模式弹框",
        isEnable = HookEntry.optionEntity.viewHideOption.enableDisableQSNModeDialog
    ) {
        HookEntry.optionEntity.viewHideOption.enableDisableQSNModeDialog = it
    }
    val accountViewHideRightTopRedDotSwitchOption = CustomSwitch(
        context = this,
        title = "隐藏我-右上角消息红点",
        isEnable = HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccountRightTopRedDot
    ) {
        HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccountRightTopRedDot = it
    }
    val hideComicBannerAdOption = CustomSwitch(
        context = this,
        title = "隐藏精选-漫画轮播图广告",
        isEnable = HookEntry.optionEntity.viewHideOption.enableHideComicBannerAd
    ) {
        HookEntry.optionEntity.viewHideOption.enableHideComicBannerAd = it
    }
    val accountViewHideOptionSwitchOption = CustomSwitch(
        context = this,
        title = "启用我-隐藏控件",
        isEnable = HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccount
    ) {
        HookEntry.optionEntity.viewHideOption.accountOption.enableHideAccount = it
    }
    val customTextView = CustomTextView(
        context = this,
        mText = "我-屏蔽控件列表",
        isBold = true
    ) {
        val shieldOptionList =
            HookEntry.optionEntity.viewHideOption.accountOption.configurationsOptionList.toList()
        val checkedItems = BooleanArray(shieldOptionList.size)
        if (HookEntry.optionEntity.viewHideOption.accountOption.configurationsSelectedOptionList.isNotEmpty()) {
            safeRun {
                shieldOptionList.forEachIndexed { index, _ ->
                    // 对比 shieldOptionList 和 optionEntity.viewHideOption.accountOption.configurationsSelectedOptionList 有相同的元素就设置为true
                    if (HookEntry.optionEntity.viewHideOption.accountOption.configurationsSelectedOptionList.any { it == shieldOptionList[index] }) {
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
                    HookEntry.optionEntity.viewHideOption.accountOption.configurationsSelectedOptionList += shieldOptionList[index]
                } else {
                    HookEntry.optionEntity.viewHideOption.accountOption.configurationsSelectedOptionList -= shieldOptionList[index]
                }
            }
        }
    }
    val bookDetailHideOptionSwitch = CustomSwitch(
        context = this,
        title = "启用书籍详情-隐藏控件",
        isEnable = HookEntry.optionEntity.viewHideOption.bookDetailOptions.enableHideBookDetail
    ) {
        HookEntry.optionEntity.viewHideOption.bookDetailOptions.enableHideBookDetail = it
    }
    val bookDetailHideOptionList = CustomTextView(
        context = this,
        mText = "书籍详情-屏蔽控件列表",
        isBold = true
    ) {
        val shieldOptionList =
            HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurationsOptionList.toList()
        val checkedItems = BooleanArray(shieldOptionList.size)
        if (HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurationsSelectedOptionList.isNotEmpty()) {
            safeRun {
                shieldOptionList.forEachIndexed { index, _ ->
                    // 对比 shieldOptionList 和 optionEntity.viewHideOption.accountOption.configurationsSelectedOptionList 有相同的元素就设置为true
                    if (HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurationsSelectedOptionList.any { it == shieldOptionList[index] }) {
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
                    HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurationsSelectedOptionList += shieldOptionList[index]
                } else {
                    HookEntry.optionEntity.viewHideOption.bookDetailOptions.configurationsSelectedOptionList -= shieldOptionList[index]
                }
            }
        }
    }
    linearLayout.apply {
        addView(homeTextView)
        addView(searchHideAllViewOption)
        addView(enableDisableQSNModeDialogOption)
        addView(accountViewHideRightTopRedDotSwitchOption)
        addView(hideComicBannerAdOption)
        addView(accountViewHideOptionSwitchOption)
        addView(customTextView)
        addView(bookDetailHideOptionSwitch)
        addView(bookDetailHideOptionList)
    }
    alertDialog {
        title = "隐藏控件配置"
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
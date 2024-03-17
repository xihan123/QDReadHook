package cn.xihan.qdds

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson2.parseObject
import com.alibaba.fastjson2.toJSONString
import com.highcapable.yukihookapi.hook.factory.constructor
import com.highcapable.yukihookapi.hook.factory.current
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.param.HookParam
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.TextViewClass
import com.highcapable.yukihookapi.hook.type.android.ViewClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.ListClass
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType
import org.luckypray.dexkit.DexKitBridge
import java.lang.reflect.Modifier

/**
 * 主页配置列表
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 * @param [configurations] 配置
 * @suppress Generate Documentation
 */
fun PackageParam.homeOption(
    versionCode: Int, configurations: List<SelectedModel>, bridge: DexKitBridge
) {
    configurations.filter { it.selected }.takeIf { it.isNotEmpty() }?.forEach {
        when (it.title) {
            "主页顶部宝箱提示" -> hideMainTopBox(versionCode)
            "主页顶部战力提示" -> hideMainTopPower(versionCode)
            "书架每日导读" -> hideBookshelfDailyReading(versionCode, bridge)
            "书架顶部标题" -> hideBookshelfTopTitle(versionCode)
        }
    }
}

/**
 * 搜索选项
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 * @param [configurations] 配置
 * @suppress Generate Documentation
 */
fun PackageParam.searchOption(
    versionCode: Int, configurations: List<SelectedModel>, bridge: DexKitBridge
) {
    when (versionCode) {
        in 1196..1299 -> {
            val map = mapOf(
                "搜索历史" to 1, "搜索发现" to 2, "搜索排行榜" to 3, "为你推荐" to 4
            ).filterKeys { key -> configurations.any { it.selected && it.title == key } }

            if (map.isNotEmpty()) {

                bridge.findClass {
                    excludePackages = listOf("com")
                    matcher {
                        usingStrings = listOf("combineBean", "dataList")
                    }
                }.firstNotNullOfOrNull { classData ->
                    classData.findMethod {
                        matcher {
                            paramTypes = listOf("java.util.List")
                            returnType = "void"
                            usingStrings = listOf("dataList")
                        }
                    }.firstNotNullOfOrNull { methodData ->
                        methodData.className.toClass().method {
                            name = methodData.methodName
                            paramCount(methodData.paramTypeNames.size)
                            returnType = UnitType
                        }.hook().before {
                            val list = args[0].safeCast<MutableList<*>>() ?: return@before
                            val iterator = list.iterator()
                            while (iterator.hasNext()) {
                                val type = iterator.next()?.getParam<Int>("type")
                                if (type in map.values) {
                                    iterator.remove()
                                }
                            }
                        }
                    }
                }
            }
        }

        else -> "搜索配置列表".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 精选-隐藏配置
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.selectedOption(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {

            "com.qidian.QDReader.ui.modules.bookstore.BookStoreRebornFragment".toClass().method {
                name = "updateUI"
                paramCount(4)
                returnType = UnitType
            }.hook().before {
                val bookStoreWrap = args[0] ?: return@before
                val cardItems = bookStoreWrap.getParam<MutableList<*>>("cardItems") ?: return@before
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
                                    bookStoreCardItem.getParam<Any>("jsonObject") ?: continue
                                jsonObject.current {
                                    val jsonArray = method {
                                        name = "getAsJsonArray"
                                        paramCount(1)
                                    }.call("Items")

                                    jsonArray?.current {
                                        val iterator2 = method {
                                            name = "iterator"
                                        }.call().safeCast<MutableIterator<*>>()

                                        while (iterator2?.hasNext() == true) {
                                            val item = iterator2.next()
                                            item?.current {
                                                val title2 = method {
                                                    name = "get"
                                                }.call("Text")
                                                Option.optionEntity.viewHideOption.selectedOption.configurations.findOrPlus(
                                                    title = "$title2".replace("\"", ""),
                                                    iterator = iterator2
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!title.isNullOrBlank()) {
                        Option.optionEntity.viewHideOption.selectedOption.configurations.findOrPlus(
                            title = title, iterator = iterator
                        )
                    }
                }
            }

            "com.qidian.QDReader.ui.modules.bookstore.MorphingFragment".toClass().method {
                name = "updateUI"
                paramCount(4)
                returnType = UnitType
            }.hook().before {
                val bookStoreWrap = args[0] ?: return@before
                val cardItems = bookStoreWrap.getParam<MutableList<*>>("cardItems") ?: return@before
                val iterator = cardItems.iterator()
                while (iterator.hasNext()) {
                    val bookStoreCardItem = iterator.next() ?: continue
                    val data = bookStoreCardItem.getParam<Any>("data") ?: continue
                    val columnName = data.getParam<Any>("extension")?.getParam<String>("columnName")
                    var title = data.getParam<Any>("cardTitle")?.getParam<String>("name")
                    if (!columnName.isNullOrBlank() && title.isNullOrBlank() && "banner" == columnName) {
                        title = "轮播图"
                    }
                    if (!title.isNullOrBlank()) {
                        Option.optionEntity.viewHideOption.selectedOption.configurations.findOrPlus(
                            title = title!!, iterator = iterator
                        )
                    }
                    val multiData = data.getParam<MutableList<*>>("multiData")
                    if (multiData != null) {
                        val multiIterator = multiData.iterator()
                        while (multiIterator.hasNext()) {
                            val item = multiIterator.next() ?: continue
                            val columnName2 =
                                item.getParam<Any>("extension")?.getParam<String>("columnName")
                            var title2 = item.getParam<Any>("cardTitle")?.getParam<String>("name")
                            if (!columnName2.isNullOrBlank() && title2.isNullOrBlank()) {
                                when (columnName2) {
                                    "broadcast" -> {
                                        title2 = "轮播消息"
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
                                                    Option.optionEntity.viewHideOption.selectedOption.configurations.findOrPlus(
                                                        title = itemName, iterator = listIterator
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!title2.isNullOrBlank()) {
                                Option.optionEntity.viewHideOption.selectedOption.configurations.findOrPlus(
                                    title = title2, iterator = multiIterator
                                )
                            }
                        }
                    }
                }
            }

        }

        else -> "选项配置列表".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 精选-标题隐藏配置
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.selectedTitleOption(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.fragment.QDStorePagerFragment".toClass().method {
                name = "onViewInject"
                param(ViewClass)
                returnType = UnitType
            }.hook().after {
                val mAdapter = instance.getParam<Any>("mAdapter") ?: return@after
                val mTabLayout = instance.getParam<FrameLayout>("mTabLayout") ?: return@after

                mAdapter.current {
                    val countSize = superClass().method {
                        name = "getCount"
                        emptyParam()
                        returnType = IntType
                    }.int()
                    val pageList = mutableListOf<Any?>()
                    val needShieldTitleList = Option.getNeedShieldTitleList()
                    if (countSize > 0 && needShieldTitleList.isNotEmpty()) {
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
//                                    val v =
                                    superClass().method {
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

                val textViews = mTabLayout.findViewsByType(TextViewClass)

                textViews.forEach { view ->
                    val text = (view as TextView).text.toString()
                    if (text.isNotBlank()) {
                        Option.optionEntity.viewHideOption.selectedOption.selectedTitleConfigurations.findOrPlus(
                            title = text
                        ) {
                            val parent = view.parent.parent.parent as LinearLayout
                            parent.removeView(view.parent.parent as View)
                        }
                    }
                }
            }
        }

        else -> "精选-标题隐藏".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏主页面-顶部宝箱提示
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.hideMainTopBox(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            intercept(
                className = "com.qidian.QDReader.ui.activity.MainGroupActivity",
                methodName = "getGlobalMsg"
            )
        }

        else -> "主页面-顶部宝箱提示".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏主页面-顶部战力提示
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.hideMainTopPower(versionCode: Int) {
    when (versionCode) {
        in 878..1299 -> {
            intercept(
                className = "com.qidian.QDReader.ui.activity.MainGroupActivity",
                methodName = "getFightRankMsg"
            )
        }

        else -> "主页面-顶部战力提示".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏书架-每日导读
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.hideBookshelfDailyReading(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1196..1299 -> {
            bridge.apply {
                findClass {
                    matcher {
                        methods {
                            add {
                                paramTypes = listOf("int")
                                returnType = "com.qidian.QDReader.repository.entity.BookItem"
                            }
                            add {
                                paramTypes =
                                    listOf("com.qidian.QDReader.repository.entity.BookShelfItem")
                                returnType = "void"
                            }
                        }
                    }
                }.forEach { classData ->
                    classData.findMethod {
                        matcher {
                            name = "getHeaderItemCount"
                            returnType = "int"
                        }
                    }.firstNotNullOfOrNull { methodData ->
                        methodData.className.toClass().method {
                            name = methodData.methodName
                            returnType = IntType
                        }.hook().replaceTo(0)
                    }
                }

                intercept(
                    className = "com.qidian.QDReader.ui.modules.bookshelf.BookShelfViewModel",
                    methodName = "fetchDailyReading",
                    paramCount = 2
                )
            }
        }

        else -> "书架-每日导读".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏书架顶部标题
 * @since 7.9.334-1196
 * @param [versionCode] 版本代码
 * @suppress Generate Documentation
 */
fun PackageParam.hideBookshelfTopTitle(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.modules.bookshelf.adapter.BaseBooksAdapter".toClass().method {
                name = "getHeaderItemCount"
                emptyParam()
                returnType = IntType
            }.hook().replaceTo(0)
        }
    }
}

/**
 * 隐藏底部导航栏红点
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 * @suppress Generate Documentation
 */
fun PackageParam.hideBottom(
    versionCode: Int,
    hideRedDot: Boolean = true,
    hideNavigation: Boolean = true,
    bridge: DexKitBridge
) {
    when (versionCode) {
        in 1196..1299 -> {

            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.ui.widget.maintab")
                matcher {
                    usingStrings = listOf(
                        "Icon tab provider return null when index in [0, tab count).",
                        "tabLayout",
                        "BOTTOM_TAB_OPERATION_RED_DOT_"
                    )
                }
            }.firstNotNullOfOrNull { classData ->
                classData.findMethod {
                    matcher {
                        usingStrings = listOf(
                            "Icon tab provider return null when index in [0, tab count).",
                            "tabLayout",
                            "BOTTOM_TAB_OPERATION_RED_DOT_"
                        )
                    }
                }.firstNotNullOfOrNull { methodData ->
                    methodData.className.toClass().method {
                        name = methodData.methodName
                        returnType = UnitType
                    }.hook().after {
                        val linearLayout =
                            instance.getViews("android.widget.LinearLayout".toClass())
                                .filterIsInstance<LinearLayout>().firstOrNull() ?: return@after

                        if (hideRedDot) {
                            linearLayout.findViewsByType("com.qidian.QDReader.framework.widget.customerview.SmallDotsView".toClass())
                                .takeIf {
                                    it.isNotEmpty()
                                }?.hideViews()
                        }

                        if (hideNavigation) {
                            val textViews = linearLayout.findViewsByType(TextViewClass)
                                .filter { textView -> (textView as TextView).text.isNotBlank() }
                            if (textViews.isNotEmpty()) {
                                textViews.forEach { textView ->
                                    val text = (textView as TextView).text
                                    if ("回到顶部" !in text) {
                                        Option.optionEntity.viewHideOption.homeOption.bottomNavigationConfigurations.findOrPlus(
                                            title = "主页底部导航栏${text}"
                                        ) {
                                            textView.parent.parent.safeCast<View>()
                                                ?.setVisibilityIfNotEqual()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        else -> "主页面-底部导航栏".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 我-隐藏控件
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.accountViewHide(
    versionCode: Int,
) {
    when (versionCode) {
        in 1196..1299 -> {

            "com.qidian.QDReader.ui.fragment.main_group.QDUserAccountRebornFragment".toClass()
                .method {
                    name = "processAccountItem"
                    param("com.qidian.QDReader.repository.entity.user_account.UserAccountItemBean".toClass())
                    returnType = ListClass
                }.hook().after {
                    val userAccountItemBean = args[0] ?: return@after
                    val benefitButtonList =
                        userAccountItemBean.getParam<MutableList<*>>("benefitButtonList")
                    if (!benefitButtonList.isNullOrEmpty()) {
                        val iterator = benefitButtonList.iterator()
                        while (iterator.hasNext()) {
                            val next = iterator.next().toJSONString().parseObject()
                            val name = next?.getStringWithFallback("name")
                            if (!name.isNullOrBlank()) {
                                Option.optionEntity.viewHideOption.accountOption.configurations.findOrPlus(
                                    title = name, iterator = iterator
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
                            val name = next?.getStringWithFallback("name")
                            if (!name.isNullOrBlank()) {
                                Option.optionEntity.viewHideOption.accountOption.configurations.findOrPlus(
                                    title = name, iterator = iterator
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
                            val name = next?.getStringWithFallback("name")
                            if (!name.isNullOrBlank()) {
                                Option.optionEntity.viewHideOption.accountOption.configurations.findOrPlus(
                                    title = name, iterator = iterator
                                )
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
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.accountRightTopRedDot(versionCode: Int) {
    when (versionCode) {
        in 1196..1299 -> {
            returnFalse(
                className = "com.qidian.QDReader.component.config.QDAppConfigHelper\$Companion",
                methodName = "isEnableUniteMessage"
            )

            fun HookParam.hideSmallDotsView() {
                instance.getViews("com.qidian.QDReader.framework.widget.customerview.SmallDotsView".toClass())
                    .takeIf { it.isNotEmpty() }?.filterIsInstance<View>()?.hideViews()
            }

            "com.qidian.QDReader.ui.fragment.main_group.QDUserAccountRebornFragment".toClass()
                .apply {
                    method {
                        name = "setNewMsgText"
                        paramCount(2)
                        returnType = UnitType
                    }.hook().after {
                        hideSmallDotsView()
                    }

                    method {
                        name = "initView"
                        paramCount(1)
                        returnType = UnitType
                    }.hook().after {
                        hideSmallDotsView()
                    }
                }

        }

        else -> "我-右上角消息红点".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 书籍详情-隐藏控件
 * @since 7.9.334-1196 ~ 1299
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
    isNeedHideCqzs: Boolean = false,
    isNeedHideRybq: Boolean = false,
    isNeedHideQqGroups: Boolean = false,
    isNeedHideSyq: Boolean = false,
    isNeedHideSyb: Boolean = false,
    isNeedHideYpjz: Boolean = false,
    isNeedHideCenterAd: Boolean = false,
    isNeedHideFloatAd: Boolean = false,
    isNeedHideBookRecommend: Boolean = false,
    isNeedHideBookRecommend2: Boolean = false,
) {
    when (versionCode) {
        in 1196..1299 -> {
            "com.qidian.QDReader.ui.activity.QDBookDetailActivity".toClass().apply {
                method {
                    name = "notifyData"
                    param(BooleanType)
                    returnType = UnitType
                }.hook().before {
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

                if (isNeedHideCenterAd) {
                    method {
                        name {
                            it.contains("getAD\$lambda")
                        }
                        paramCount(2)
                        returnType = UnitType
                    }.hook().intercept()
                }

                if (isNeedHideFloatAd) {
                    method {
                        name = "getFloatingAd"
                        emptyParam()
                        returnType = UnitType
                    }.hook().intercept()
                }

                if (isNeedHideCqzs) {
                    method {
                        name = "addCircleMarkInfo"
                        param("com.qidian.QDReader.repository.entity.OutCircleIndexInfo".toClass())
                        returnType = UnitType
                    }.hook().after {
                        val viewMap =
                            instance.getParam<Map<*, View>>("_\$_findViewCache") ?: return@after
                        viewMap.values.filterIsInstance<TextView>()
                            .firstOrNull { "tvCircleMarkLevel" == it.getName() }
                            ?.setVisibilityIfNotEqual() ?: "隐藏出圈指数".printlnNotSupportVersion(
                            versionCode
                        )
                    }
                }


            }

            if (isNeedHideSyb) {
                "com.qidian.QDReader.ui.view.BookFansModuleView".toClass().method {
                    param(
                        LongType,
                        StringClass,
                        "com.qidian.QDReader.repository.entity.FansInfo".toClass(),
                        ListClass
                    )
                    returnType = UnitType
                }.hook().after {
                    instance.safeCast<LinearLayout>()?.setVisibilityIfNotEqual()
                }
            }

            if (isNeedHideSyq) {
                "com.qidian.QDReader.ui.view.BookCircleModuleView".toClass().method {
                    name = "bind"
                    returnType = UnitType
                }.hook().after {
                    instance.safeCast<LinearLayout>()?.setVisibilityIfNotEqual()
                }
            }

        }

        else -> "书籍详情".printlnNotSupportVersion(versionCode)
    }

}

/**
 * 阅读页面-隐藏控件
 * @since 7.9.334-1196 ~ 1299
 * @param [versionCode] 版本代码
 */
fun PackageParam.hideReadPage(versionCode: Int, bridge: DexKitBridge) {
    when (versionCode) {
        in 1196..1299 -> {
            bridge.findClass {
                searchPackages = listOf("com.qidian.QDReader.readerengine.view.menu")
                matcher {
                    usingStrings = listOf("QDReaderActivity_MoreMenu")
                }
            }.firstNotNullOfOrNull { classData ->
                classData.findMethod {
                    matcher {
                        modifiers = Modifier.PRIVATE
                        paramTypes = listOf("android.content.Context")
                        returnType = "void"
                    }
                }.firstNotNullOfOrNull { methodData ->
                    methodData.className.toClass().method {
                        name = methodData.methodName
                        paramCount(methodData.paramTypeNames.size)
                        returnType = UnitType
                    }.hook().after {
                        instance.getParamList<View>().takeIf { it.isNotEmpty() }?.let { views ->
                            val iterator = views.iterator()
                            while (iterator.hasNext()) {
                                val view = iterator.next()
                                val name = view.getName()
                                Option.optionEntity.viewHideOption.readPageOptions.configurations.findOrPlus(
                                    name
                                ) {
                                    view.setVisibilityWithChildren()
                                }
                            }
                        }
                    }
                }
            }
        }

        else -> "阅读页面-隐藏控件".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 隐藏红点
 * @since 7.9.334-1196
 * @param [versionCode] 版本代码
 */
fun PackageParam.hideRedDot(versionCode: Int) {
    when (versionCode) {
        in 868..1299 -> {
            intercept(
                className = "com.qidian.QDReader.framework.widget.customerview.SmallDotsView",
                methodName = "onDraw",
                paramCount = 1
            )
        }

        else -> "隐藏小红点".printlnNotSupportVersion(versionCode)
    }
}



package cn.xihan.qdds

import android.content.Context
import android.widget.TextView
import com.highcapable.yukihookapi.hook.factory.current
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.LongType
import com.highcapable.yukihookapi.hook.type.java.StringClass
import com.highcapable.yukihookapi.hook.type.java.UnitType

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2022/10/5 19:16
 * @介绍 :
 */
/**
 * 启用净化替换
 */
fun PackageParam.enableReplace(versionCode: Int) {
    when (versionCode) {
        in 812..906 -> {
            findClass("com.qidian.QDReader.component.util.FockUtil").hook {
                injectMember {
                    method {
                        name = "restoreShufflingText"
                        paramCount(3)
                        returnType = StringClass
                    }
                    afterHook {
                        val mResult = result as? String
                        mResult?.let {
                            if (HookEntry.optionEntity.replaceRuleOption.replaceRuleList.isNotEmpty()) {
                                result =
                                    mResult.replaceByReplaceRuleList(HookEntry.optionEntity.replaceRuleOption.replaceRuleList)
                            }
                        }
                        //loggerE(msg = "restoreShufflingText: ${result as? String}")
                    }
                }
            }
        }

        else -> "正则替换".printlnNotSupportVersion(versionCode)
    }
}

/**
 * 替换配置
 */
fun Context.showReplaceOptionDialog() {
    val linearLayout = CustomLinearLayout(this, isAutoWidth = false)

    val replaceListView = CustomListView(
        context = this,
        onItemClickListener = { adapter, position ->
            safeRun {
                val replaceRule = HookEntry.optionEntity.replaceRuleOption.replaceRuleList[position]
                showAddOrEditReplaceRuleDialog(replaceRule) {
                    adapter.updateListData(HookEntry.optionEntity.replaceRuleOption.replaceRuleList.map { it.replaceRuleName })
                }
            }
        },
        onItemLongClickListener = { adapter, position ->
            safeRun {
                val replaceRule = HookEntry.optionEntity.replaceRuleOption.replaceRuleList[position]
                alertDialog {
                    title = "删除替换规则: ${replaceRule.replaceRuleName}"
                    positiveButton("确定") {
                        safeRun {
                            adapter.removeItem(position)
                            HookEntry.optionEntity.replaceRuleOption.replaceRuleList.removeAt(
                                position
                            )
                        }
                    }
                    negativeButton("返回") {
                        it.dismiss()
                    }
                    build()
                    show()
                }
            }
        },
        listData = HookEntry.optionEntity.replaceRuleOption.replaceRuleList.map { it.replaceRuleName },
    )
    val addReplaceOption = CustomButton(context = this, mText = "添加替换规则", onClickAction = {
        showAddOrEditReplaceRuleDialog {
            replaceListView.updateListData(HookEntry.optionEntity.replaceRuleOption.replaceRuleList.map { it.replaceRuleName })
        }

    })
    linearLayout.apply {
        addView(addReplaceOption)
        addView(replaceListView)
    }
    alertDialog {
        title = "替换配置"
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

/**
 * 添加或编辑替换规则
 * @param replaceEntity 替换规则模型
 * @param successAction 成功回调
 */
fun Context.showAddOrEditReplaceRuleDialog(
    replaceEntity: OptionEntity.ReplaceRuleOption.ReplaceItem = OptionEntity.ReplaceRuleOption.ReplaceItem(),
    successAction: () -> Unit,
) {
    val linearLayout = CustomLinearLayout(this, isAutoWidth = false)
    val replaceRuleNameOption = CustomEditText(
        context = this,
        title = "替换规则名称",
        value = replaceEntity.replaceRuleName,
        mHint = "请输入替换规则名称"
    ) {
        replaceEntity.replaceRuleName = it
    }
    val replaceRuleOption = CustomEditText(
        context = this,
        title = "替换规则",
        value = replaceEntity.replaceRuleRegex,
        mHint = "请输入替换规则"
    ) {
        replaceEntity.replaceRuleRegex = it
    }
    val replaceRuleResultOption = CustomEditText(
        context = this,
        title = "替换规则结果",
        value = replaceEntity.replaceWith,
        mHint = "请输入替换规则结果"
    ) {
        replaceEntity.replaceWith = it
    }
    val enableReplaceOption = CustomSwitch(
        context = this, title = "启用正则表达式", isEnable = replaceEntity.enableRegularExpressions
    ) {
        replaceEntity.enableRegularExpressions = it
    }
    linearLayout.apply {
        addView(replaceRuleNameOption)
        addView(replaceRuleOption)
        addView(replaceRuleResultOption)
        addView(enableReplaceOption)
    }
    alertDialog {
        title = "添加替换规则"
        customView = linearLayout
        okButton {
            HookEntry.optionEntity.replaceRuleOption.replaceRuleList.find { it1 -> it1.replaceRuleName == replaceEntity.replaceRuleName }
                ?.let { it1 ->
                    HookEntry.optionEntity.replaceRuleOption.replaceRuleList.remove(it1)
                }
            HookEntry.optionEntity.replaceRuleOption.replaceRuleList += replaceEntity
            successAction()
            it.dismiss()
        }
        negativeButton("返回") {
            it.dismiss()
        }
        build()
        show()
    }
}

///**
// * 自定义书友值
// */
//fun PackageParam.customBookFansValue(versionCode: Int) {
//    when (versionCode) {
//        in 872..900 -> {
//            findClass("com.qidian.QDReader.ui.modules.bookshelf.dialog.BookShelfMiniCardDialog").hook {
//                injectMember {
//                    method {
//                        name = "setupIntegrityInfo"
//                        param("com.qidian.QDReader.repository.entity.bookshelf.BookShelfInfo".toClass())
//                    }
//                    beforeHook {
//                        args[0]?.let {
//                            val fansInfo = it.getParam<Any>("fansInfo")
////                            "fansInfo: $fansInfo".loge()
//                            val userTags = it.getParam<MutableList<*>>("userTags")
//                            HookEntry.optionEntity.bookFansValueOption.apply {
//                                fansInfo?.setParams(
//                                    "amount" to amount,
////                                "fansLevel" to 9,
//                                    "fansRank" to fansRank,
////                                "rankName" to "黄金总盟"
//                                )
//                                userTags?.first()?.setParams(
////                                "mTitleName" to "黄金总盟",
//                                    "mTitleImage" to mTitleImage,
////                                "mTitleSubType" to 301,
////                                "mTitleType" to 3,
////                                "mTitleId" to 5
//
//                                )
//                            }
//
//
//                        }
//                    }
//                }
//            }
//
//            findClass("com.qidian.QDReader.ui.fragment.TotalListFragment").hook {
//                injectMember {
//                    method {
//                        name = "buildFragment"
//                        paramCount(1)
//                        returnType = UnitType
//                    }
//                    beforeHook {
//                        val userFansInfo = args[0]?.getParam<Any>("userFansInfo")
//                        HookEntry.optionEntity.bookFansValueOption.apply {
//                            userFansInfo?.setParams(
//                                "Amount" to amount,
//                                "DValue" to dValue,
//                                "DaShangDesc" to daShangDesc,
//                                "FansRank" to fansRank,
//                                "HeadImageUrl" to headImageUrl,
//                                "LeagueRank" to leagueRank,
//                                "LeagueType" to leagueType,
//                                "MonthUpgradeDesc" to rankUpgradeDesc,
//                                "NickName" to nickName,
//                                "RankName" to rankName,
//                                "RankUpgradeDesc" to rankUpgradeDesc
//                            )
//                        }
//
////                        "userFansInfo: ${userFansInfo.toJSONString()}".loge()
//                    }
//                }
//            }
//
//            val needHookMethod = when (versionCode) {
//                872 -> "h"
//                in 884..900 -> "e"
//                else -> null
//            }
//            needHookMethod?.let {
//                findClass("com.qidian.QDReader.ui.view.BookFansBottomView").hook {
//                    injectMember {
//                        method {
//                            name = needHookMethod
//                            param(
//                                "com.qidian.QDReader.repository.entity.QDFansUserValue".toClass(),
//                                LongType,
//                                StringClass
//                            )
//                            returnType = UnitType
//                        }
//                        beforeHook {
////                        val userFansInfo = .getParam<Any>("mUserInfo")
//                            HookEntry.optionEntity.bookFansValueOption.apply {
//                                args[0]?.setParams(
//                                    "Amount" to amount,
//                                    "DValue" to dValue,
//                                    "DaShangDesc" to daShangDesc,
//                                    "FansRank" to fansRank,
//                                    "HeadImageUrl" to headImageUrl,
//                                    "LeagueRank" to leagueRank,
//                                    "LeagueType" to leagueType,
//                                    "MonthUpgradeDesc" to rankUpgradeDesc,
//                                    "NickName" to nickName,
//                                    "RankName" to rankName,
//                                    "RankUpgradeDesc" to rankUpgradeDesc
//                                )
//                            }
//                        }
//                    }
//                }
//            } ?: "书友值".printlnNotSupportVersion(versionCode)
//
//
//            /**
//             * tvName
//             */
//            val textViewId = when (versionCode) {
//                872 -> 0x7F0919AB
//                878 -> 0x7F091A0B
//                884 -> 0x7F091A33
//                890 -> 0x7F091A4B
//                in 896..900 -> 0x7F091A51
//                else -> null
//            }
//            if (textViewId == null) {
//                "自定义书友值-长按保存".printlnNotSupportVersion(versionCode)
//            } else {
//                findClass("com.qidian.QDReader.ui.adapter.FansRankingAdapter").hook {
//                    injectMember {
//                        method {
//                            name = "convert"
//                            paramCount(3)
//                            returnType = UnitType
//                        }
//                        afterHook {
//                            val item = args[2] ?: return@afterHook
//                            args[0]?.current {
//                                val textView = method {
//                                    name = "getView"
//                                }.call(textViewId) as? TextView
//                                textView?.setOnLongClickListener {
//                                    val amount = item.getParam<Int>("amount")
//                                    val leagueRank = item.getParam<Int>("leagueRank")
//                                    val leagueType = item.getParam<Int>("leagueType")
//                                    val nickName = item.getParam<String>("nickName")
//                                    val orderId = item.getParam<Int>("orderId")
//                                    val rank = item.getParam<Int>("rank")
//                                    val rankName = item.getParam<String>("rankName")
//
//                                    val realImageUrl = item.getParam<String>("realImageUrl")
//                                    val mTitleImage =
//                                        item.getParam<MutableList<*>>("userTagList")?.first()
//                                            ?.getParam<String>("mTitleImage")
//
//                                    val nextRankName = parseLeagueTypeMap(rankName!!)
//                                    val dValue =
//                                        parseLeagueTypeMapValue(rankName, amount!!).toLong()
//
//                                    HookEntry.optionEntity.bookFansValueOption.apply {
//                                        this.amount = amount
//                                        this.leagueRank = leagueRank!!
//                                        this.leagueType = leagueType!!
//                                        this.nickName = nickName!!
//                                        this.orderId = orderId!!
//                                        this.rank = rank!!
//                                        this.rankName = rankName
//                                        this.mTitleImage = mTitleImage!!
//                                        this.headImageUrl = realImageUrl!!
//                                        this.dValue = dValue
//                                        daShangDesc = "再打赏%1\$s点升级“$nextRankName”，1点=1书友值"
//                                        rankUpgradeDesc = "还需%1\$s书友值成为“$nextRankName”"
//                                    }
//                                    updateOptionEntity()
//                                    true
//                                }
//                            }
//
//                        }
//                    }
//                }
//            }
//
//
//        }
//
//        else -> "自定义书友值".printlnNotSupportVersion(versionCode)
//    }
//}

/**
 * 自定义设备信息
 * @param versionCode
 * @param enableCustomDeviceInfo 是否启用自定义设备信息
 * @param enableSaveOriginalDeviceInfo 是否保存原始设备信息
 * @param deviceInfoModel 自定义设备信息模型
 */
fun PackageParam.customDeviceInfo(
    versionCode: Int,
    deviceInfoModel: OptionEntity.ReplaceRuleOption.CustomDeviceInfo,
    enableCustomDeviceInfo: Boolean = false,
    enableSaveOriginalDeviceInfo: Boolean = false
) {
    /**
     * android_release
     */
    val needHookClass = when (versionCode) {
        in 896..906 -> "com.qidian.QDReader.core.util.m"
        else -> null
    }

    /**
     * 设备厂商
     */
    val brand = when (versionCode) {
        in 896..900 -> "k"
        906 -> "l"
        else -> null
    }

    /**
     * 设备型号
     */
    val model = when (versionCode) {
        in 896..900 -> "l"
        906 -> "m"
        else -> null
    }

    /**
     * Android 版本
     */
    val androidVersion = when (versionCode) {
        in 896..900 -> "m"
        906 -> "n"
        else -> null
    }

    /**
     * 发布版本
     */
    val release = when (versionCode) {
        in 896..900 -> "r"
        906 -> "s"
        else -> null
    }

    /**
     * 屏幕高度
     */
    val heightPixels = when (versionCode) {
        in 896..900 -> "t"
        906 -> "u"
        else -> null
    }

    /**
     * IMEI
     */
    val imei = when (versionCode) {
        in 896..900 -> listOf("d", "e", "f", "h")
        906 -> listOf("e", "f", "g", "i")
        else -> null
    }

    /**
     * 设备返回信息
     */
    val deviceInfo = when (versionCode) {
        in 896..900 -> "B"
        906 -> "C"
        else -> null
    }
    /**
     * 设备Mac地址
     */
    val macAddress = when (versionCode) {
        in 896..900 -> "E"
        906 -> "F"
        else -> null
    }
    /**
     * 设备序列号
     */
    val serial = when (versionCode) {
        in 896..900 -> "y"
        906 -> "z"
        else -> null
    }
    /**
     * cpu 信息
     */
    val cpuInfo = when (versionCode) {
        in 896..906 -> "a"
        else -> null
    }
    /**
     * AndroidId
     */
    val androidId = when (versionCode) {
        in 896..906 -> "cihai"
        else -> null
    }

    needHookClass?.hook {

        injectMember {
            method {
                name = brand!!
                emptyParam()
                returnType = StringClass
            }
            afterHook {
                if (enableSaveOriginalDeviceInfo) {
                    HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo.brand =
                        result as String
                    updateOptionEntity()
                }

                if (enableCustomDeviceInfo) {
                    result = deviceInfoModel.brand
                }

            }
//                    replaceTo(deviceInfo.brand)
        }

        injectMember {
            method {
                name = model!!
                emptyParam()
                returnType = StringClass
            }
            afterHook {
                if (enableSaveOriginalDeviceInfo) {
                    HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo.model =
                        result as String
                    updateOptionEntity()
                }
                if (enableCustomDeviceInfo) {
                    result = deviceInfoModel.model
                }
            }
//                    replaceTo(deviceInfo.model)
        }

        injectMember {
            method {
                name = androidVersion!!
                emptyParam()
                returnType = StringClass
            }
            afterHook {
                if (enableSaveOriginalDeviceInfo) {
                    HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo.androidVersion =
                        result as String
                    updateOptionEntity()
                }
                if (enableCustomDeviceInfo) {
                    result = deviceInfoModel.androidVersion
                }
            }
//                    replaceTo(deviceInfo.androidVersion)
        }

        injectMember {
            method {
                name = release!!
                emptyParam()
                returnType = StringClass
            }
            afterHook {
                if (enableSaveOriginalDeviceInfo) {
                    HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo.releaseVersion =
                        result as String
                    updateOptionEntity()
                }
                if (enableCustomDeviceInfo) {
                    result = deviceInfoModel.releaseVersion
                }
            }
//                    replaceTo(deviceInfo.releaseVersion)
        }

        injectMember {
            method {
                name = heightPixels!!
                emptyParam()
                returnType = IntType
            }
            afterHook {
                if (enableSaveOriginalDeviceInfo) {
                    HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo.screenHeight =
                        result as Int
                    updateOptionEntity()
                }
                if (enableCustomDeviceInfo) {
                    result = deviceInfoModel.screenHeight
                }
            }
//                    replaceTo(deviceInfo.screenHeight)
        }

        imei?.forEachIndexed { index, s ->
            injectMember {
                method {
                    name = s
                    emptyParam()
                    returnType = StringClass
                }
                afterHook {
                    if (enableSaveOriginalDeviceInfo && index == 0) {
                        HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo.imei =
                            result as String
                        updateOptionEntity()
                    }
                    if (enableCustomDeviceInfo) {
                        result = deviceInfoModel.imei
                    }
                }
//                    replaceTo(deviceInfo.imei)
            }
        }

        injectMember {
            method {
                name =deviceInfo!!
                emptyParam()
                returnType = StringClass
            }
            afterHook {
                if (enableCustomDeviceInfo) {
                    result =
                        "null${deviceInfoModel.releaseVersion}${deviceInfoModel.model}null"
                }
            }
//                    replaceTo("null${deviceInfo.releaseVersion}${deviceInfo.model}null")
        }

        injectMember {
            method {
                name =macAddress!!
                emptyParam()
                returnType = StringClass
            }
            afterHook {
                if (enableSaveOriginalDeviceInfo) {
                    HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo.macAddress =
                        result as String
                    updateOptionEntity()
                }
                if (enableCustomDeviceInfo) {
                    result = deviceInfoModel.macAddress
                }
            }
//                    replaceTo(deviceInfo.macAddress)
        }

        injectMember {
            method {
                name = serial!!
                emptyParam()
                returnType = StringClass
            }
            afterHook {
                if (enableSaveOriginalDeviceInfo) {
                    HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo.serial =
                        result as String
                    updateOptionEntity()
                }

                if (enableCustomDeviceInfo) {
                    result = deviceInfoModel.serial
                }
            }
//                    replaceTo(deviceInfo.serial)
        }

        injectMember {
            method {
                name = cpuInfo!!
                emptyParam()
                returnType = StringClass
            }
            afterHook {
                if (enableSaveOriginalDeviceInfo) {
                    HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo.cpuInfo =
                        result as String
                    updateOptionEntity()
                }
                if (enableCustomDeviceInfo) {
                    result = deviceInfoModel.cpuInfo
                }
            }
//                    replaceTo(deviceInfo.cpuInfo)
        }


        injectMember {
            method {
                name = androidId!!
                emptyParam()
                returnType = StringClass
            }
            afterHook {
                if (enableSaveOriginalDeviceInfo) {
                    HookEntry.optionEntity.replaceRuleOption.originalDeviceInfo.androidId =
                        result as String
                    updateOptionEntity()
                }
                if (enableCustomDeviceInfo) {
                    result = deviceInfoModel.androidId
                }
            }
//                    replaceTo(deviceInfo.androidId)
        }

    } ?: "自定义设备信息".printlnNotSupportVersion(versionCode)

}
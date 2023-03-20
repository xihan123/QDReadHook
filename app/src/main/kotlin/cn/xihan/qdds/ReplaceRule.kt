package cn.xihan.qdds

import android.content.Context
import android.widget.TextView
import com.highcapable.yukihookapi.hook.factory.current
import com.highcapable.yukihookapi.hook.param.PackageParam
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
        in 812..900 -> {
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

/**
 * 自定义书友值
 */
fun PackageParam.customBookFansValue(versionCode: Int) {
    when (versionCode) {
        in 872..884 -> {
            findClass("com.qidian.QDReader.ui.modules.bookshelf.dialog.BookShelfMiniCardDialog").hook {
                injectMember {
                    method {
                        name = "setupIntegrityInfo"
                        param("com.qidian.QDReader.repository.entity.bookshelf.BookShelfInfo".toClass())
                    }
                    beforeHook {
                        args[0]?.let {
                            val fansInfo = it.getParam<Any>("fansInfo")
//                            "fansInfo: $fansInfo".loge()
                            val userTags = it.getParam<MutableList<*>>("userTags")
                            HookEntry.optionEntity.bookFansValueOption.apply {
                                fansInfo?.setParams(
                                    "amount" to amount,
//                                "fansLevel" to 9,
                                    "fansRank" to fansRank,
//                                "rankName" to "黄金总盟"
                                )
                                userTags?.first()?.setParams(
//                                "mTitleName" to "黄金总盟",
                                    "mTitleImage" to mTitleImage,
//                                "mTitleSubType" to 301,
//                                "mTitleType" to 3,
//                                "mTitleId" to 5

                                )
                            }


                        }
                    }
                }
            }

            findClass("com.qidian.QDReader.ui.fragment.TotalListFragment").hook {
                injectMember {
                    method {
                        name = "buildFragment"
                        paramCount(1)
                        returnType = UnitType
                    }
                    beforeHook {
                        val userFansInfo = args[0]?.getParam<Any>("userFansInfo")
                        HookEntry.optionEntity.bookFansValueOption.apply {
                            userFansInfo?.setParams(
                                "Amount" to amount,
                                "DValue" to dValue,
                                "DaShangDesc" to daShangDesc,
                                "FansRank" to fansRank,
                                "HeadImageUrl" to headImageUrl,
                                "LeagueRank" to leagueRank,
                                "LeagueType" to leagueType,
                                "MonthUpgradeDesc" to rankUpgradeDesc,
                                "NickName" to nickName,
                                "RankName" to rankName,
                                "RankUpgradeDesc" to rankUpgradeDesc
                            )
                        }

//                        "userFansInfo: ${userFansInfo.toJSONString()}".loge()
                    }
                }
            }

            findClass("com.qidian.QDReader.ui.view.BookFansBottomView").hook {
                injectMember {
                    method {
                        name = if (versionCode == 884) "e" else "h"
                        param(
                            "com.qidian.QDReader.repository.entity.QDFansUserValue".toClass(),
                            LongType,
                            StringClass
                        )
                        returnType = UnitType
                    }
                    beforeHook {
//                        val userFansInfo = .getParam<Any>("mUserInfo")
                        HookEntry.optionEntity.bookFansValueOption.apply {
                            args[0]?.setParams(
                                "Amount" to amount,
                                "DValue" to dValue,
                                "DaShangDesc" to daShangDesc,
                                "FansRank" to fansRank,
                                "HeadImageUrl" to headImageUrl,
                                "LeagueRank" to leagueRank,
                                "LeagueType" to leagueType,
                                "MonthUpgradeDesc" to rankUpgradeDesc,
                                "NickName" to nickName,
                                "RankName" to rankName,
                                "RankUpgradeDesc" to rankUpgradeDesc
                            )
                        }
                    }
                }
            }

            /**
             * tvName
             */
            val textViewId = when (versionCode) {
                872 -> 0x7F0919AB
                878 -> 0x7F091A0B
                884 -> 0x7F091A33
                else -> null
            }
            if (textViewId == null) {
                "自定义书友值-长按保存".printlnNotSupportVersion(versionCode)
            } else {
                findClass("com.qidian.QDReader.ui.adapter.FansRankingAdapter").hook {
                    injectMember {
                        method {
                            name = "convert"
                            paramCount(3)
                            returnType = UnitType
                        }
                        afterHook {
                            val item = args[2] ?: return@afterHook
                            args[0]?.current {
                                val textView = method {
                                    name = "getView"
                                }.call(textViewId) as? TextView
                                textView?.setOnLongClickListener {
                                    val amount = item.getParam<Int>("amount")
                                    val leagueRank = item.getParam<Int>("leagueRank")
                                    val leagueType = item.getParam<Int>("leagueType")
                                    val nickName = item.getParam<String>("nickName")
                                    val orderId = item.getParam<Int>("orderId")
                                    val rank = item.getParam<Int>("rank")
                                    val rankName = item.getParam<String>("rankName")

                                    val realImageUrl = item.getParam<String>("realImageUrl")
                                    val mTitleImage =
                                        item.getParam<MutableList<*>>("userTagList")?.first()
                                            ?.getParam<String>("mTitleImage")

                                    val nextRankName = parseLeagueTypeMap(rankName!!)
                                    val dValue =
                                        parseLeagueTypeMapValue(rankName, amount!!).toLong()

                                    HookEntry.optionEntity.bookFansValueOption.apply {
                                        this.amount = amount
                                        this.leagueRank = leagueRank!!
                                        this.leagueType = leagueType!!
                                        this.nickName = nickName!!
                                        this.orderId = orderId!!
                                        this.rank = rank!!
                                        this.rankName = rankName
                                        this.mTitleImage = mTitleImage!!
                                        this.headImageUrl = realImageUrl!!
                                        this.dValue = dValue
                                        daShangDesc = "再打赏%1\$s点升级“$nextRankName”，1点=1书友值"
                                        rankUpgradeDesc = "还需%1\$s书友值成为“$nextRankName”"
                                    }
                                    updateOptionEntity()
                                    true
                                }
                            }

                        }
                    }
                }
            }


        }

        else -> "自定义书友值".printlnNotSupportVersion(versionCode)
    }
}
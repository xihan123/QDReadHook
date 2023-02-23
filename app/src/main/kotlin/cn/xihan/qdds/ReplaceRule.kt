package cn.xihan.qdds

import android.content.Context
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.java.StringClass

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
        in 812..880 -> {
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




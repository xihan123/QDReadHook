![QDReadHook](https://socialify.git.ci/xihan123/QDReadHook/image?description=1&font=Inter&forks=1&issues=1&language=1&logo=https%3A%2F%2Ft3.picb.cc%2F2023%2F05%2F30%2FIj8tn6.png&name=1&owner=1&pattern=Plus&pulls=1&stargazers=1&theme=Light)

![above](https://img.shields.io/badge/Android-8.0%20or%20above-brightgreen.svg)
[![Android CI](https://github.com/xihan123/QDReadHook/actions/workflows/build.yml/badge.svg)](https://github.com/xihan123/QDReadHook/actions/workflows/build.yml)
[![Latest Release](https://img.shields.io/github/release/xihan123/QDReadHook.svg)](https://github.com/xihan123/QDReadHook/releases)
![downloads](https://img.shields.io/github/downloads/xihan123/QDReadHook/total)
[![Blank](https://img.shields.io/github/downloads/Xposed-Modules-Repo/cn.xihan.qdds/total?label=LSPosed%20Repo&logo=Android&style=flat&labelColor=F48FB1&logoColor=ffffff)](https://github.com/Xposed-Modules-Repo/cn.xihan.qdds/releases)

---

## 起点阅读 Xp模块

使用 [YukiHookAPI](https://github.com/fankes/YukiHookAPI)

* 使用前注意给予起点存储权限

* 设置页面在:起点->我的->左上角设置->阅读设置/模块设置(长按)(1.1.2+)

* 起点目前支持 758~768、772、776、780、784、788、792、796、800、804、808、812、827、834、842、843、850、854、858、860、868、872、878、884、890、896、900、906、916、924、932、938、944、950、958、970版本

* 暂时提高版本号范围以支持一些不容易改变的类

* 1.2.7+ 配置文件路径为 "/sdcard/Download/QDReader",会自行移动原配置文件并删除原配置文件目录,注意原配置文件目录不要有重要文件

* 下载地址

  * [支持的版本合集蓝奏云](https://xihan.lanzouv.com/b0413c6he) 密码:xihan

  * [酷安](https://www.coolapk.com/apk/5066/)

  * [官方网站](https://www.yuewen.com/app.html#appqd)

* QD模块交流群: [727983520](https://qm.qq.com/cgi-bin/qm/qr?k=JT0K0sZEJHm4CnsRjRTKxY3uL-xoO6CG&jump_from=webapi&authKey=yGg3h07NWBGGF4TmxtRNykIQ4HLM4t/uxrAtqHx15zgRmIR4sC14HxKYOq376ekt) <a target="_blank" href="https://qm.qq.com/cgi-bin/qm/qr?k=JT0K0sZEJHm4CnsRjRTKxY3uL-xoO6CG&jump_from=webapi&authKey=yGg3h07NWBGGF4TmxtRNykIQ4HLM4t/uxrAtqHx15zgRmIR4sC14HxKYOq376ekt"><img border="0" src="https://pub.idqqimg.com/wpa/images/group.png" alt="QD模块交流群" title="QD模块交流群"></a>

## 常见问题

* 如提示 "**Manifest文件中Activity/Service/Permission的声明有问题或者Permission权限未授予**"
把 "**广告配置**" 中 "**GDT(TX)广告**" 去掉勾选

* 如提示 "**激励广告拉取失败,详细错误码:50000**" 则检查设备网络，**DNS**或者**Hosts** 是否拦截了"**e.qq.com**"、"**gdt.qq.com**"、"**gtimg.cn**"、"**gdtimg.com**"域名

* 开关功能不生效

        注意看上述所提到支持的版本号

        1.1.2版本以前如还不生效则查看/data/misc/某个文件夹/prefs/cn.xihan.qdds 这个文件夹权限是否可读,如果不可读手动设置一下，每次修改了配置都需要修改此权限,并应用子文件 权限 都设定为755最佳。还不行就把模块卸载了重新安装，激活后先去把配置调整好，再去上述路径改权限，完事最好清下起点数据，打开就完美了!!!

        1.1.2及以后则查看起点是否有存储权限，查看是否存在"/sdcard/QDReader/option.json"这个文件(没有就创建一个)

        1.2.7+ 配置文件路径为 "/sdcard/Download/QDReader",其他如上

        ps:根据版本不同，显示的路径可能也不同，可能是"/storage/emulated/0/QDReader/option.json".
        如果使用系统自带文件管理器，直接在"根目录(内部存储)"创建文件夹"QDReader"即可
        
        如果上述都不行，那就试着清除起点数据或者重装模块(同时删除上述路径所在的配置文件)，也可能需要重启一下手机。

        还不行就附上日志提issues或酷安留言私信我即可(语气不要太冲，说的好像我欠你啥的，上来就质疑我的也不一定会回复。以为自己是谁啊，你用不了我就一定要让你也能用上，我还能远程施法单单让你用不了不成?)

* 没开启闪屏页却一直显示闪屏页

        这种情况一般是因为本地已经有缓存了,最简单的方法是清除起点的数据,把要开的功能提前开好

* 开启自定义启动图无效?

        多进几次起点等启动图全部下载完成就会开始随机

* 开启去广告无效

        和上述一致，清数据重启即可

* ~~目前去青少年模式弹框仅仅只是防止频繁弹，不是完全去掉,我之前测试用隔一会弹一下，开启后仅弹一次~~ 1.0.2+版本是通过 Hook 自定义Dialog 的 **show()** 方法，会导致投月票或者特殊订阅弹框不显示(1.1.6+已修复此问题) 1.1.6+是通过拦截上级调用

* ~~模块初次使用建议操作流程~~1.1.2及以上无需此操作，授予起点存储权限即可

        1.安装好模块后把模块和起点都强行停止运行
        2.激活(勾选)模块
        3.打开模块配置好相关选项
        4.强行停止模块运行
        5.修改上述所提到的文件夹权限
        6.清除起点数据
        7.打开起点

* 部分功能之前好好的，突然失效，**1.2.9+ 配置文件模型改变，部分设定需要重新设置!!!** 开关以及配置都正常却失效日志也没有。可以理解为被热修复了，一般来说更新最新版即可或者提Issues

* 激活模块或使用Lspatch版起点闪退,可按如下流程排查
            
      1.关闭免广告领取奖励
      2.关闭快速屏蔽弹框
      3.是否使用了lspatch修补的起点并且同时启用了模块
      4.关闭模块
  
  如果上述流程走完还是无法解决闪退，可以把配置文件删除并重启起点，再不济重启手机

* 如提示"检测到您当前设备环境有异常，请确保规范使用后再进行尝试"

        起点加强了风控
        自行隐藏设备Root环境
* Android 9 从起点内进入设置界面白屏,这是一个奇怪的bug，暂时找不到解决方法,内置版可以另外安装一个模块本体来管理配置文件

---

## Lspatch 使用说明

* 安装后启动前需要授予起点存储权限!!!要不然无法读取配置文件则不会生效,或者你设定错了可能会使用默认配置

* 已支持动态配置(1.1.2+)

* 因为修改了签名,所以快速登录无法使用,只能用手机号登录!!!所以如果可以还是使用 Xp 模式

* 用 [LSPatch](https://github.com/LSPosed/LSPatch) 把 [SignHook](https://github.com/xihan123/SignHook) 打包进目标平台即可调用第三方(登录/分享)

  * QQ
  * 微信
  * 抖音

* 请勿激活模块的同时使用此版本!!!

---

## 免责声明

* 该Xposed模块仅供学习交流使用，使用者必须自行承担使用该模块所带来的风险和责任。

* 该Xposed模块可以修改系统行为，使用者应该仔细审查模块的操作并自行决定是否使用。
* 使用该Xposed模块可能导致设备不稳定、崩溃和数据丢失等问题。作者不对任何因使用该模块而导致的问题承担责任。
* 开发者保留对该Xposed模块的更新、修改、暂停、终止等权利，使用者应该自行确认其使用版本的安全性和稳定性。
* 任何人因使用该Xposed模块而导致的任何问题，作者不承担任何责任，一切后果由使用者自行承担。
* 对于使用该Xposed模块所产生的任何问题，作者不提供任何形式的技术支持和解决方案。

请在使用该Xposed模块之前认真阅读以上免责声明并自行权衡风险和利益，如有异议请勿使用。如果您使用了该Xposed模块，即代表您已经完全接受本免责声明。

---

## 截图

![image1](https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/1.png)
![image1-1](https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/1-1.png)
![image2](https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/2.png)
![image3](https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/3.png)
![image4](https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/4.png)
![image5](https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/5.png)
![image6](https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/6.png)
![image7](https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/7.png)
![image8](https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/8.png)
![image9](https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/9.png)
![image10](https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/10.png)

---

## 打赏

## 如果觉得这个模块对您有用，可扫描下方二维码随意打赏,要是能打赏个 10.24 🐵就太👍了。您的支持就是我更新的动力

<table>
<tr>
<td align=center>支付宝</td>
<td align=center>微信</td>
</tr>

<tr>
<td>
<img src="https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/zfb.jpg" width=270 >
</td>
<td>
<img src="https://jihulab.com/xihan123/QDReadHook/-/raw/master/Screenshots/wx.png" width=270 >
</td>
</tr>

</table>

---
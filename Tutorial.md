# 某点阅读 7.9.228-758 自定义增强功能

## 用到的工具

* MT管理器2(反编译分析用)、Jeb
* 开发者助手(类似查找 控件 **ID** 功能的都可)
* Android Studio(可有可无)
* 某点阅读(主角)

---

## 修改前截图

![图21](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/修改前.jpg?raw=true)

## 修改后截图

![图18](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/18.jpg?raw=true)
![图19](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/19.jpg?raw=true)
![图20](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/20.jpg?raw=true)

## 目录

* [自动签到](#自动签到)
* [新旧版布局](#新旧版布局)
* [本地至尊卡](#本地至尊卡)
* [去除书架右下角浮窗](#去除书架右下角浮窗)
* [去除底部导航栏中心广告](#去除底部导航栏中心广告)
* [去除TX广告系](#去除TX广告系)
* [去除底部小红点](#去除底部小红点)
* [闪屏页相关](#闪屏页相关)
* [反射方法](#反射方法)
* [开源地址](#Xposed模块开源)

### 自动签到

* 1.使用开发者助手获取 **ID** 或 **ID-HEX**

![图1](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/1.jpg?raw=true)
![图2](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/2.jpg?raw=true)

* 2.以整数类型,十六进制查找相应的 **ID-HEX**

![图3](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/3.jpg?raw=true)

* 3.查找结果一目了然,点进方法转为 Java 分析一波

![图4](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/4.jpg?raw=true)

* 4.这是它自定义控件初始化的位置,看看哪里调用
**setText** 、 **setOnClickListener** 方法(新旧布局相同思路)

![图5](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/5.jpg?raw=true)
![图6](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/6.jpg?raw=true)
![图7](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/7.jpg?raw=true)

* 5.如果是使用 **Xposed** Hook的差不多到这就行了 反射获取这个控件调用 **performClick()** 即可(甚至可以反射获取这个控件的 **getText** 方法 来判断是不是 **签到** 这两个字)

* 5.如果是反编译则添加如下代码

```smail
// p1 为 控件 注意跟在 setOnClickListener 后 参数为啥就设定啥
// 至于为何是 调用 LinearLayout 类的 performClick()
// 因为它这个 QDUIButton 继承的是 LinearLayout
// 示例 invoke-virtual {p1, v2}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

invoke-virtual {p1}, Landroid/widget/LinearLayout;->performClick()Z
```

#### ps:注意 如果控件没有定义 **setOnClickListener** 调用 **performClick()** 会返回 **flase** 即无效

#### ps:查找ID只演示一次 后续都是通用方法

---

### 新旧版布局

* 1.用上面的类名直接搜调用 定位到 **d()** 判断 **QDAppConfigHelper.p()** 返回的布尔值 从这两个方法内调用的类名可以确定为  **f()**  新布局 **e()** 为旧布局,很好理解了吧(不过如果直接修改这个方法有个坑,在某些版本可能会导致闪退,可以深入查看两个调用后再修改,具体可看我写的 **Xposed** 模块源码,放在最后)

![图8](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/8.jpg?raw=true)

---

### 本地至尊卡

* 无任何作用,仅仅是为了好看?

* 1.字符常量池搜索一下 **已开通** 直接有结果,就不去整那些花里胡哨的了

![图9](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/9.jpg?raw=true)

* 2.分析搜索到的相关代码块 理解为 判断 **QDAppConfigHelper.U** 返回的整数类型如果 等于2 和 不等于3 就 提示 **已开通** , 也就是说只要让它返回 **2** 即可(如上面方法一样有坑依然深入一两次调用可解决)

![图10](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/10.jpg?raw=true)

* 3.到 **QDAppConfigHelper.U** 查看代码发现是 调用了 **a.getMemberType()**, 不用管这个 **a** 是啥，继续找调用

![图11](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/11.jpg?raw=true)

* 4.到 **getMemberType()** 方法后发现里面实例化了一个 **MemberBean** 这调用来调用去的太麻烦了，干脆就全局搜了一下 **getMemberType()** 方法,结果还真有惊喜

![图12](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/12.jpg?raw=true)

*5.直接有和用户相关的类 打开发现果然有 2个疑似的方法 修改**getMemberType()** 返回值为 2 **getIsMember()** 为 1 后成功点亮本地至尊卡

![图13](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/13.jpg?raw=true)
![图14](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/14.jpg?raw=true)

---

### 去除书架右下角浮窗

* 1.炮如法制，获取 **ID-HEX** 后定位到 **onViewInject()** 方法,仔细分析流程后发现 **refresh** 调用了 重写方法 **notifyDataSetChanged** 此方法内又调用了 **loadBookShelfAd** 这个就是关键方法,如果是 **Xposed** 就Hook置空此方法,反编译修改就删除上级调用。再通过 获取**ID=HEX** 定位 **取消** 浮窗,使用下述方法移除

```Java

public void refresh(int arg9) {
     // 忽略其他代码
     // 这里为重写的方法 notifyDataSetChanged
     this.notifyDataSetChanged();
}
```

```Java
public void notifyDataSetChanged() {
    // 忽略其他代码
    this.loadBookShelfAd();
}
```

* 怕有小笨蛋不知道咋改, **Xposed** Hook的话就通过反射调用 这个控件 设置 **visibility** 属性为 **View.GONE**. 反编译的则直接修改 **xml**对应 **ID** 的控件给加上 **android:visibility="gone"**

![图15](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/15.jpg?raw=true)

---

### 去除底部导航栏中心广告

* 1.分析当前 **Activity** 所属的 **Fragment** 打开方法一看就找到 **checkAdTab()** 到 参数内 **new t()** 的这个方法里面看看

![图16](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/16.jpg?raw=true)

* 2.构造函数传递了一个 **activity** 和 **String** 直接能看到关键方法 **c()**

![图17](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/17.jpg?raw=true)

* 3.**Xposed** Hook 这个方法直接置空即可.反编译则可删除方法内容或删除上级调用

### 去除TX系广告

* 1.众所周知,TX广告一般都是通过动态加载 **Assets** 目录下 **gdt_plugin/gdtadv2.jar** 正常思路来讲把此jar删掉即可,但是在 **Xposed** 模块里没有这个功能,我的想法是 Hook 它 **getAssetPluginName** 或者 **getAssetPluginDir** 修改返回值为 **null**。 这个是通用方法,不过有些应用可能没处理容错，未找到此Jar可能会闪退

```Java
// 忽略部分代码
public class CustomPkgConstants {
    
     public static Class getADActivityClass() throws Exception {
        return CustomPkgConstants.a("jebdyn.dexdec.irsb.Activity_eb35bb3");
    }

    public static String getADActivityName() {
        String v0 = GlobalSetting.getCustomADActivityClassName();
        return StringUtil.isEmpty(v0) ? CustomPkgConstants.b : v0;
    }

    public static String getAssetPluginDir() {
        return "gdt_plugin";
    }

    public static String getAssetPluginName() {
        return "gdtadv2.jar";
    }

}

```

* 2.通过上面的 **getADActivityClass** 找到上级调用，这 **a(Context)** 为最终调用判断SDK是否可用 **Xposed** 返回**false**即可

```Java
package com.qq.e.comm;

// 此为检查各种权限的类 最上方
public final class b {
     public static boolean a(Context arg4) {
        try {
            //这为最终调用方法 返回false 即可判断sdk无法使用
            
        }
        catch(Throwable v4) {
            GDTLogger.e("Exception While check SDK Env", v4);
            return 0;
        }

        return 0;
    }

    private static boolean a(Context arg6, Class[] arg7) {
        int v1;
        for(v1 = 0; v1 <= 0; ++v1) {
            try {
                Intent v3 = new Intent();
                v3.setClass(arg6, arg7[0]);
                if(arg6.getPackageManager().resolveActivity(v3, 0x10000) == null) {
                    GDTLogger.e(String.format("Activity[%s] is required in AndroidManifest.xml", arg7[0].getName()));
                    return 0;
                }
            }
            catch(Throwable v6) {
                GDTLogger.e("Exception while checking required activities", v6);
                return 0;
            }
        }

        return 1;
    }

    private static boolean b(Context arg7) {
        String[] v0 = new String[]{"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCESS_WIFI_STATE"};
        int v2;
        for(v2 = 0; v2 < 3; ++v2) {
            try {
                String v3 = v0[v2];
                if(arg7.checkCallingOrSelfPermission(v3) == -1) {
                    GDTLogger.e(String.format("Permission %s is required in AndroidManifest.xml", v3));
                    return 0;
                }
            }
            catch(Throwable v7) {
                GDTLogger.e("Check required Permissions error", v7);
                return 0;
            }
        }

        return 1;
    }

     private static boolean b(Context arg7, Class[] arg8) {
        int v1;
        for(v1 = 0; v1 <= 0; ++v1) {
            try {
                Class v3 = arg8[0];
                Intent v4 = new Intent();
                v4.setClass(arg7, v3);
                if(arg7.getPackageManager().resolveService(v4, 0x10000) == null) {
                    GDTLogger.e(String.format("Service[%s] is required in AndroidManifest.xml", v3.getName()));
                    return 0;
                }
            }
            catch(Throwable v7) {
                GDTLogger.e("Exception while checking required services", v7);
                return 0;
            }
        }

        return 1;
    }


}

```

* 3.通过分析启动任务，看名字发现了相关类,把 **create** 置空即可，实测相关日志减少。此方法不通用

![图23](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/23.png?raw=true)

```Java
package com.qidian.QDReader.start;

public final class AsyncMainGDTTask extends QDDefaultSyncTask {
    @Override  // com.qidian.QDReader.start.QDDefaultSyncTask
    public Object create(Context arg2) {
        AppMethodBeat.i(0x33C4);
        String v2 = this.create(arg2);
        AppMethodBeat.o(0x33C4);
        return v2;
    }

    @Nullable
    public String create(@NotNull Context arg4) {
        AppMethodBeat.i(0x33BF);
        p.e(arg4, "context");
        Trace.beginSection("AsyncMainGDTTask----");
        // key值删了
        // 或许有人问了 为什么不Hook initWith 因为此方法初始化失败会卡住无法进入
        GDTADManager.getInstance().initWith(arg4, "key");
        Trace.endSection();
        AppMethodBeat.o(0x33BF);
        return "GDTADManager";
    }
}

```

```java
package com.qidian.QDReader.start;

public final class AsyncMainGameADSDKTask extends QDDefaultAsyncMainTask {
    @Override  // com.qidian.QDReader.start.QDDefaultAsyncMainTask
    public Object create(Context arg2) {
        AppMethodBeat.i(12970);
        String v2 = this.create(arg2);
        AppMethodBeat.o(12970);
        return v2;
    }

    @Nullable
    public String create(@NotNull Context arg3) {
        AppMethodBeat.i(12950);
        p.e(arg3, "context");
        this.initGameAdSDK(arg3);
        AppMethodBeat.o(12950);
        return "GameADSDK";
    }

    private final void initGameAdSDK(Context arg4) {
        AppMethodBeat.i(0x32A0);
        try {
            KlevinManager.init(arg4, new Builder().appId("30030").debugMode(false).directDownloadNetworkType(16).customController(new AsyncMainGameADSDKTask.b()).build(), new AsyncMainGameADSDKTask.a());
        }
        catch(Exception v4) {
            Logger.e(v4.getMessage());
        }

        AppMethodBeat.o(0x32A0);
    }
}
```

![图21](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/21.jpg?raw=true)
![图22](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/22.png?raw=true)

---

### 去除底部小红点

* 通过开发者助手或者Android studio的布局分析找到 **ID** 为 **dotImg** dex里面发现没有相关实例化的地方,分析发现这个已经是自定义控件了(以前版本是一个ImageView),找到哪里引用了 **view_tab_image_large.xml**,
最终定位到 **PagerSlidingTabStrip.s()**方法, 修改 **v5.h()** 返回值为 **1** 即可

```xml
    <com.qidian.QDReader.framework.widget.customerview.SmallDotsView
        android:id="@id/dotImg"
        android:layout_height="-2"
        android:layout_marginLeft="@dimen/nr"
        android:layout_marginTop="@dimen/jl"
        android:layout_toRightOf="@id/centerV"
        android:layout_width="-2"
        android:visibility="0" />
```

```java
package com.qidian.QDReader.ui.widget.maintab;

public class PagerSlidingTabStrip extends HorizontalScrollView {

    // 忽略部分代码
    public void s() {
        //layout:view_tab_title_text
        v6 = LayoutInflater.from(this.getContext()).inflate(0x7F0C08A4, null); 
        // id:tagView 
        QDUITagView v10 = (QDUITagView)v6.findViewById(0x7F09147E); 
        // id:dotImg
        SmallDotsView v9_1 = (SmallDotsView)v6.findViewById(0x7F09057B);  
        v9_1.setBorderWidth(n.a(1f));
        int v11 = v5.i();
        // 让其返回 1
        int v13 = v5.h();
        // 判断 int类型 v13 如果等于 0 则 显示 1则隐藏
        if(v13 == 0) {
            // 判断 v11 不等于 1 或者 v10 是显示状态 则隐藏 反之显示
            v9_1.setVisibility(v11 != 1 || v10.getVisibility() == 0 ? 8 : 0);
          } else if(v13 == 1) {
             // 等同于View.GONE
             v9_1.setVisibility(8);
        }
     }


}

```

---

### 闪屏页相关

#### 禁用闪屏页

* 通过日志和抓包分析发现获取闪屏页网络请求是在 **MainGroupActivity** 启动后开始, 分析 **onCreate** 发现一个疑似调用了 **SplashManager** **f()** 返回一个单例 调用了 **k()** 直接置空即可

```fenced
    不过如果本地已经有缓存了可以清空一下App存储空间，通过分析也能发现这个保存的数据存在应用私有目录下 /databases/QDConfig 数据库内,光删除这个数据库也行。
```

```java


    @Override  // com.qidian.QDReader.ui.activity.BaseActivity
    protected void onCreate(Bundle arg7) {
        //忽略其他代码
        //
        SplashManager.f().k(this.getApplicationContext());

    }

```

```java

public void k(Context arg6) {
        // 忽略部分代码
        try {
            // 使用 okhttp 网络请求 
            QDHttpClient v1_1 = new com.qidian.QDReader.framework.network.qd.QDHttpClient.b().c(false).e(false).b();
            StringBuilder v2 = new StringBuilder();
            // 拼接参数
            v2.append(Urls.q6());
            v2.append("?");
            String v3 = this.g();
            // 拼接参数
            if(!t0.k(v3)) {
                v2.append("localLabels=");
                v2.append(v3);
            }
            // 进行请求
            v1_1.l(this.toString(), v2.toString(), new SplashManager.a(this, arg6));
        } catch(Exception v6) {
        }
    }

```

#### 自定义闪屏页

* 由于 **SplashActivity** 各种关键函数都被抽取到native层了，就略过直接到 **SplashImageActivity** 来分析, **onCreate** 直接就发现了相关代码,通过调用 **start(activity,string,string,long,int)** 调用跳转到此Activity, 只要修改 **start(activity,string,string,long,int)** 的参数即可做到定义自己想要图片

```java

@Override  // com.qidian.QDReader.ui.activity.BaseActivity
    protected void onCreate(Bundle arg8) {
        AppMethodBeat.i(0x7190);
        super.onCreate(arg8);
        // 设置不可侧滑返回
        this.setSwipeBackEnable(false);
        // 接收 intent 传的参数
        this.actionUrl = this.getIntent().getStringExtra("actionUrl");
        String v1 = this.getIntent().getStringExtra("imageFilePath");
        this.splashId = this.getIntent().getLongExtra("splashId", 0L);
        this.style = this.getIntent().getIntExtra("splashStyle", 0);
        // 设置布局
        this.setContentView(0x7F0C077E);  // layout:splash_image_layout
        // 初始化控件
        ImageView v8 = (ImageView)this.findViewById(0x7F090854);  // id:imgSplashScreen
        this.imgSplash = v8;
        v8.setVisibility(8);
        Button v8_1 = (Button)this.findViewById(0x7F0913CC);  // id:splash_skip_button
        this.btnSkip = v8_1;
        v8_1.setVisibility(8);
        this.mGotoActivity = (QDUIRoundRelativeLayout)this.findViewById(0x7F0906F7);  // id:gotoActivity
        this.mGotoActivityShimmer = (ShimmerFrameLayout)this.findViewById(0x7F0906F8);  // id:gotoActivity_shimmer
        this.pagClick = (PAGWrapperView)this.findViewById(0x7F0910A6);  // id:pagClick
        this.ivTop = (ImageView)this.findViewById(0x7F090AA8);  // id:ivTop
        this.layoutSwipeUp = (ConstraintLayout)this.findViewById(0x7F090CF7);  // id:layoutSwipeUp
        this.layoutShadow = (RelativeLayout)this.findViewById(0x7F090CE1);  // id:layoutShadow
        // 状态栏沉浸
        if(n0.t()) {
            ViewCompat.setOnApplyWindowInsetsListener(this.getWindow().getDecorView(), (View arg1, WindowInsetsCompat arg2) -> this.lambda$onCreate$0(arg1, arg2));
        }
        else {
            this.fitWindowInsets();
        }
        this.mSplashHelper = new c(this, this.btnSkip);
        // 展示闪屏图片
        this.showSplashImage(v1);
        HashMap v8_2 = new HashMap();
        v8_2.put("actionUrl", String.valueOf(this.actionUrl));
        v8_2.put("splashId", String.valueOf(this.splashId));
        this.configActivityData(this, v8_2);
        AppMethodBeat.o(0x7190);
    }


    public static void start(SplashActivity arg3, String arg4, String arg5, long arg6, int arg8) {
        AppMethodBeat.i(29048);
        Intent v1 = new Intent();
        v1.setClass(arg3, SplashImageActivity.class);
        // 传入本地图片地址
        v1.putExtra("imageFilePath", arg4);
        // 动作Url
        v1.putExtra("actionUrl", arg5);
        // 闪屏ID
        v1.putExtra("splashId", arg6);
        // 闪屏类型
        v1.putExtra("splashStyle", arg8);
        arg3.startActivity(v1);
        AppMethodBeat.o(29048);
    }

    private void showSplashImage(String arg10) {
        int v3;
        int v1_1;
        AppMethodBeat.i(0x71BA);
        // 判断传入的本地图片路径是否为空
        if(t0.k(arg10)) {
            this.layoutSwipeUp.setVisibility(8);
            this.mGotoActivityShimmer.setVisibility(8);
            this.pagClick.setVisibility(8);
            this.imgSplash.setVisibility(8);
            this.btnSkip.setVisibility(8);
            this.mSplashHelper.e();
        }
        else {
            // 如果创建文件路径失败则调用 mSplashHelper的 e() 方法  也就是直接结束此页面到主页
            if(!com.qidian.QDReader.audiobook.utils.c.h(arg10)) {
                this.mSplashHelper.e();
                AppMethodBeat.o(0x71BA);
                return;
            }
            // 不用管这些方法
            Point v1 = c1.e(this);
            if(v1 == null) {
                v1_1 = e.E().k();
                v3 = e.E().j();
            }
            else {
                int v3_1 = v1.x;
                v3 = v1.y;
                v1_1 = v3_1;
            }

            try {
                this.mBitmap = n6.a.g(arg10, v1_1, v3, false);
            }
            catch(OutOfMemoryError v10) {
                Logger.exception(v10);
            }

            c1.f(this.imgSplash, new BitmapDrawable(this.getResources(), this.mBitmap), v1_1, v3);
            this.mGotoActivity.setOnClickListener((View arg1) -> this.lambda$showSplashImage$1(arg1));
            this.imgSplash.setVisibility(0);
            this.btnSkip.setVisibility(0);
            this.mSplashHelper.l();
            // 下面是之前传入的一些参数,动作Url、闪屏类型
            if(TextUtils.isEmpty(this.actionUrl)) {
                this.layoutSwipeUp.setVisibility(8);
                this.mGotoActivityShimmer.setVisibility(8);
                this.pagClick.setVisibility(8);
            }
            else if(this.style == 1) {
                this.layoutShadow.setVisibility(0);
                this.layoutSwipeUp.setVisibility(0);
                this.mGotoActivityShimmer.setVisibility(8);
                this.pagClick.setVisibility(8);
                this.mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
                this.layoutSwipeUp.setOnTouchListener((View arg1, MotionEvent arg2) -> this.lambda$showSplashImage$2(arg1, arg2));
            }
            else {
                this.layoutShadow.setVisibility(8);
                this.layoutSwipeUp.setVisibility(8);
                this.mGotoActivityShimmer.setVisibility(0);
                this.pagClick.setVisibility(0);
            }
        }

        AppMethodBeat.o(0x71BA);
    }


```

* 最终效果图

![图24](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/24.jpg?raw=true)
![图25](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/25.jpg?raw=true)
![gif0](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/0.gif?raw=true)
![gif1](https://github.com/xihan123/QDReadHook/blob/master/Screenshots/Tutorial/1.gif?raw=true)

---

## 反射方法

```kotlin

/**
 * 通过反射获取控件
 * @param param 参数 为 Class 实例
 * @param name 字段名
 */
@Throws(NoSuchFieldException::class, IllegalAccessException::class)
inline fun <reified T : View> getView(param: Any, name: String): T? {
    return getParam<T>(param, name)
}

/**
 * 反射获取任何类型
 */
@Throws(NoSuchFieldException::class, IllegalAccessException::class)
inline fun <reified T> getParam(param: Any, name: String): T? {
    val clazz: Class<*> = param.javaClass
    val field = clazz.getDeclaredField(name)
    field.isAccessible = true
    return field[param] as? T
}

```

## Xposed模块开源

* [Lsp仓库地址](https://modules.lsposed.org/module/cn.xihan.qdds)
* [Github开源地址](https://github.com/xihan123/QDReadHook)
* [论坛地址](https://www.52pojie.cn/thread-1658012-1-1.html)

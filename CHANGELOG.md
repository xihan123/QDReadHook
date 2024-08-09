# Changelog
## [3.2.2](https://github.com/xihan123/QDReadHook/compare/v3.2.1...v3.2.2) (2024-08-09)


### Features

* **自动化设置:** 新增了`奇妙世界`任务 ([acf59eb](https://github.com/xihan123/QDReadHook/commit/acf59eb72bc88f3de994299236c8b13c0cfeae74))
* **隐藏控件设置:** `1336`+ 新增隐藏详情页3处轮播广告 ([a604128](https://github.com/xihan123/QDReadHook/commit/a60412806e8269256feb901d7ec58cdb28f648f5))


### Miscellaneous

* 更新依赖 ([6fefe28](https://github.com/xihan123/QDReadHook/commit/6fefe2874704fabc64d8680d729ccc5905b329af))

## [3.2.1](https://github.com/xihan123/QDReadHook/compare/v3.2.0...v3.2.1) (2024-07-25)


### Features

* **util:** 在Utils中添加classLoader参数 ([c69aff5](https://github.com/xihan123/QDReadHook/commit/c69aff5023c20823de5a8fff2d9ba0e985617a69))
* **主设置:** 新增`启用新版发现页` ([39e4655](https://github.com/xihan123/QDReadHook/commit/39e4655ac9e732ea5ac03c720b89983d0abaa3ae))


### Miscellaneous

* 更新依赖库 ([530f899](https://github.com/xihan123/QDReadHook/commit/530f899728a85fb56e2718e581ba6dd8317dc43d))

## [3.2.0](https://github.com/xihan123/QDReadHook/compare/v3.1.9...v3.2.0) (2024-07-12)


### Features

* **屏蔽设置:** 新增'屏蔽的书字数'功能,目前只测了旧每日导读可用 ([04520f7](https://github.com/xihan123/QDReadHook/commit/04520f72d1d3e6d11e68522ea1c0985c88855c5f))
* **自动化设置:** 新增`卡牌召唤` ([3883505](https://github.com/xihan123/QDReadHook/commit/388350598132be57bf89af20f358e710679325f4))


### Bug Fixes

* WelfareCenterModel类型错误 ([d725db9](https://github.com/xihan123/QDReadHook/commit/d725db987bda3d6ff1eabce32f1b8d1c099ba094))
* 修复配置文件更新的条件判断 ([1eb3358](https://github.com/xihan123/QDReadHook/commit/1eb33581fabb053173eb7ca70435f47d16415246))


### Miscellaneous

* 更新依赖库 ([effc494](https://github.com/xihan123/QDReadHook/commit/effc494a3c6b52329560c421078dcebc44f338b6))


### Performance Improvements

* **主设置:** 默认关闭自定义IMEI ([28933a7](https://github.com/xihan123/QDReadHook/commit/28933a7c6fff5f96205f60a9dc7aed703b13a8a9))

## [3.1.9](https://github.com/xihan123/QDReadHook/compare/v3.1.8...v3.1.9) (2024-07-05)


### Features

* 添加HTTP非200响应验证器,如果有错误会显示错误信息 ([3a9077f](https://github.com/xihan123/QDReadHook/commit/3a9077f94d8670bbc3004882539d1a8f992fa160))


### Bug Fixes

* 修复`领取`按钮灰色逻辑 ([abe4ac6](https://github.com/xihan123/QDReadHook/commit/abe4ac6aecb76ce274c0a2b9ad276e683ecf5521))
* 修复多开环境权限异常[258]([#258](https://github.com/xihan123/QDReadHook/issues/258)) ([4f6e5d3](https://github.com/xihan123/QDReadHook/commit/4f6e5d39af489c7599058a49c7bd2ab681f610f7))


### CI

* 更新构建流程 ([78532ce](https://github.com/xihan123/QDReadHook/commit/78532cebded3fd12897ce057df271a55cf434ad1))


### Miscellaneous

* 更新依赖库 ([2dbb70d](https://github.com/xihan123/QDReadHook/commit/2dbb70d503e8a6b0a71523a8062832b85a9444c2))


### Performance Improvements

* **About:** 关于页面元素显示不全可滑动 ([3980992](https://github.com/xihan123/QDReadHook/commit/398099289c5b55d551ade122006816c5f4c18367))

## [3.1.8](https://github.com/xihan123/QDReadHook/compare/v3.1.7...v3.1.8) (2024-06-30)


### ⚠ BREAKING CHANGES

* `1296`+ 已无`章末广告`选项所以被移除
* 提高最低支持版本为 7.9.354-1296,低于此版本的勿更新！！！

### Features

* `1296`+ 已无`章末广告`选项所以被移除 ([b0dce7a](https://github.com/xihan123/QDReadHook/commit/b0dce7ac67cfa04ac30741a47893e772b420a8ce))
* 提高最低支持版本为 7.9.354-1296,低于此版本的勿更新！！！ ([98db62c](https://github.com/xihan123/QDReadHook/commit/98db62c560e05b0b5af7142a6401bce83da2654c))
* 新增`自动化配置` ([070e780](https://github.com/xihan123/QDReadHook/commit/070e780194427bc16c7c98367767e676d64a8e9b))


### Miscellaneous

* 更新依赖库 ([cea8109](https://github.com/xihan123/QDReadHook/commit/cea8109b87a78545a395e7b668f2cb30fd1e5290))


### Refactoring

* 重命名和移动文件以优化结构 ([72a30b0](https://github.com/xihan123/QDReadHook/commit/72a30b04035d1cf313f5f7fa2c4259f405aa8d07))

## [3.1.7](https://github.com/xihan123/QDReadHook/compare/v3.1.6...v3.1.7) (2024-06-12)


### Features

* **拦截设置:** `1286+版本`新增 `部分环境检测` 选项 ([c66867d](https://github.com/xihan123/QDReadHook/commit/c66867d5a830caa0c369e88b184129c9909dd384))
* 更新权限请求 ([3054359](https://github.com/xihan123/QDReadHook/commit/3054359db7eae8ebe763d0a64c17837535fd04d3))


### Bug Fixes

* **屏蔽设置:** 修复获取书籍作者名称逻辑，优化字段兼容性 ([0d93bde](https://github.com/xihan123/QDReadHook/commit/0d93bde85dd31c00f72944921c1a604286e6d114))


### Miscellaneous

* 更新项目版本并修改工作流 ([7d4a37a](https://github.com/xihan123/QDReadHook/commit/7d4a37ad168f3da5041b96f48e2f63bd4d55f986))


### Performance Improvements

* **关于:** 添加`重置配置文件`、`清除缓存`功能的确认对话框 ([503143a](https://github.com/xihan123/QDReadHook/commit/503143a8c06e2c784375f7efaa0bbb02ed72faf5))
* 更新广告配置默认选项 ([3e110ec](https://github.com/xihan123/QDReadHook/commit/3e110ec859979aaa7ff5ef829305a48e8f02b185))

## [3.1.6](https://github.com/xihan123/QDReadHook/compare/v3.1.5...v3.1.6) (2024-05-15)


### Reverts

* **阅读页设置:** 恢复旧版阅读时间加倍 ([657bcaf](https://github.com/xihan123/QDReadHook/commit/657bcaf42c4adb8596db2958fe92fe480b44df6a))


### Miscellaneous

* 更新依赖库 ([945d55e](https://github.com/xihan123/QDReadHook/commit/945d55ed1d51d5bdf0ecba3667da166180c1eace))

## [3.1.5](https://github.com/xihan123/QDReadHook/compare/v3.1.4...v3.1.5) (2024-04-29)


### Bug Fixes

* **主设置:** 忽略限免限制可点击下载\nps:并不能过期后继续阅读 ([46926e8](https://github.com/xihan123/QDReadHook/commit/46926e869b7b6f80caac941a8d6fd894f8ea235f))
* 优化阅读时间计算 ([979fc61](https://github.com/xihan123/QDReadHook/commit/979fc618202106cf65971433713fc73fd7d9d18e))
* 贡献者列表 ([08f7b1d](https://github.com/xihan123/QDReadHook/commit/08f7b1d640947b4bf01ba7e333ae1caf50e301a9))


### Docs

* update .all-contributorsrc [skip ci] ([14eab33](https://github.com/xihan123/QDReadHook/commit/14eab33362e9df1e70593fdd3c5567e58f259a91))
* update README.md [skip ci] ([fbfa6c3](https://github.com/xihan123/QDReadHook/commit/fbfa6c3e1d26c426019b92232e93f84a6dbf22dc))


### Miscellaneous

* **master:** release 3.1.4 ([0de3081](https://github.com/xihan123/QDReadHook/commit/0de308129c102cabd7503616212b5ec7356fb32a))
* **master:** release 3.1.4 ([1d2d047](https://github.com/xihan123/QDReadHook/commit/1d2d0475b868bb0d5f3d30073212efb305efcabe))

## [3.1.4](https://github.com/xihan123/QDReadHook/compare/v3.1.4...v3.1.4) (2024-04-24)


### Bug Fixes

* 贡献者列表 ([08f7b1d](https://github.com/xihan123/QDReadHook/commit/08f7b1d640947b4bf01ba7e333ae1caf50e301a9))


### Docs

* update .all-contributorsrc [skip ci] ([14eab33](https://github.com/xihan123/QDReadHook/commit/14eab33362e9df1e70593fdd3c5567e58f259a91))
* update README.md [skip ci] ([fbfa6c3](https://github.com/xihan123/QDReadHook/commit/fbfa6c3e1d26c426019b92232e93f84a6dbf22dc))


### Miscellaneous

* **master:** release 3.1.4 ([1d2d047](https://github.com/xihan123/QDReadHook/commit/1d2d0475b868bb0d5f3d30073212efb305efcabe))

## [3.1.4](https://github.com/xihan123/QDReadHook/compare/v3.1.4...v3.1.4) (2024-04-24)


### Bug Fixes

* 贡献者列表 ([08f7b1d](https://github.com/xihan123/QDReadHook/commit/08f7b1d640947b4bf01ba7e333ae1caf50e301a9))

## [3.1.4](https://github.com/xihan123/QDReadHook/compare/v3.1.3...v3.1.4) (2024-04-24)


### Features

* 添加贡献者列表 ([25eaae0](https://github.com/xihan123/QDReadHook/commit/25eaae0cf953dc52c43117d4a9b11d08fe008061))


### Feature Improvements

* **阅读页设置:** 重写阅读时间加倍 [@vvb2060](https://github.com/vvb2060) ([bf0bf7b](https://github.com/xihan123/QDReadHook/commit/bf0bf7b76eb65b56a2f946356daa8fe9a5e47f8a))


### Miscellaneous

* 更新依赖 ([7e3c593](https://github.com/xihan123/QDReadHook/commit/7e3c593f7bdfadd691c232d5fe5a176c33671bc8))

## [3.1.3](https://github.com/xihan123/QDReadHook/compare/v3.1.2...v3.1.3) (2024-04-18)

### Features

* **主设置:** `1196` + 新增 修复抖音分享,启用后可以修复抖音分享视频显示`文件不存在` ([0a6b671](https://github.com/xihan123/QDReadHook/commit/0a6b671f6a432fdb780c75994f8a44723f5f5f88))

### Miscellaneous

* 更新依赖 ([576e51b](https://github.com/xihan123/QDReadHook/commit/576e51b27115ef0fa8593a28253fe4f45991d750))

## [3.1.2](https://github.com/xihan123/QDReadHook/compare/v3.1.1...v3.1.2) (2024-03-29)


### Features

* **主设置:** 新增测试功能 `自定义IMEI` ([5212b38](https://github.com/xihan123/QDReadHook/commit/5212b389b395b93538b0898f3f6a90ae4dd7284c))


### Miscellaneous

* 更新依赖 ([caeec12](https://github.com/xihan123/QDReadHook/commit/caeec129659002531b2c6249f4237ca62c56c352))

## [3.1.1](https://github.com/xihan123/QDReadHook/compare/v3.1.0...v3.1.1) (2024-03-17)

### ⚠ BREAKING CHANGES

* 移除 `隐藏主页配置-去找书` 功能

### Bug Fixes

* `1196` 版本 `隐藏每日导读` 失效 ([237ed4f](https://github.com/xihan123/QDReadHook/commit/237ed4f66a87e0836a7331e2e2e0e08aa0f00406))

### Feature Improvements

* `GDT广告` 默认关闭 ([3da93a9](https://github.com/xihan123/QDReadHook/commit/3da93a92e75d84ae3c72303175072ca407be5648))

## [3.1.0](https://github.com/xihan123/QDReadHook/compare/v3.0.9...v3.1.0) (2024-03-02)


### ⚠ BREAKING CHANGES

* 提高最低支持版本为 `7.9.334-1196`,低于此版本的勿更新！！！

### Features

* **主设置:** 新增 `抓取Cookie`、`调试日志` ([4ceb2b8](https://github.com/xihan123/QDReadHook/commit/4ceb2b8cfc52319862f984d3bdaecbd0067993d6))
* 共存版支持 ([6b9bd89](https://github.com/xihan123/QDReadHook/commit/6b9bd894f313081d84e841ac2f89ad9ea991f244))
* 提高最低支持版本为 `7.9.334-1196`,低于此版本的勿更新！！！ ([881571c](https://github.com/xihan123/QDReadHook/commit/881571c82faf7ea3e0ce7711f6dc4fca2b32679f))

### Bug Fixes

* `1196` 版本 `旧版每日导读` 参数类型变更 ([f75e9bb](https://github.com/xihan123/QDReadHook/commit/f75e9bb5f9b0171f57547a247063e7842bb7382e))
* 倒计时有概率不动[[#206](https://github.com/xihan123/QDReadHook/issues/206)]([#206](https://github.com/xihan123/QDReadHook/issues/206)) ([975ce87](https://github.com/xihan123/QDReadHook/commit/975ce87f3580448608c98282793209a3be6905f0))


### Feature Improvements

* 改善默认配置 ([2936059](https://github.com/xihan123/QDReadHook/commit/29360595b80515ed19daa50d94bf85063ec8b999))
* 新增温馨提示 ([3cd7e53](https://github.com/xihan123/QDReadHook/commit/3cd7e53296f5ca6e61a8ccc377f0506a420ed46a))


### Miscellaneous

* 更新依赖 ([573b396](https://github.com/xihan123/QDReadHook/commit/573b396a90b01d8f66514a4ace44fe2ddb2151d6))

## [3.1.0](https://github.com/xihan123/QDReadHook/compare/v3.1.0...v3.1.0) (2024-03-02)


### Miscellaneous

* 更新依赖 ([5dc94c5](https://github.com/xihan123/QDReadHook/commit/5dc94c5ad56b5d9590eddab634055bea8626aed7))

## [3.0.9](https://github.com/xihan123/QDReadHook/compare/v3.0.9...v3.0.9) (2024-03-02)


### ⚠ BREAKING CHANGES

* 提高最低支持版本为 `7.9.334-1196`,低于此版本的勿更新！！！

### Features

* **主设置:** 新增 `抓取Cookie`、`调试日志` ([4ceb2b8](https://github.com/xihan123/QDReadHook/commit/4ceb2b8cfc52319862f984d3bdaecbd0067993d6))
* 共存版支持 ([6b9bd89](https://github.com/xihan123/QDReadHook/commit/6b9bd894f313081d84e841ac2f89ad9ea991f244))
* 提高最低支持版本为 `7.9.334-1196`,低于此版本的勿更新！！！ ([881571c](https://github.com/xihan123/QDReadHook/commit/881571c82faf7ea3e0ce7711f6dc4fca2b32679f))


### Bug Fixes

* `1196` 版本 `旧版每日导读` 参数类型变更 ([f75e9bb](https://github.com/xihan123/QDReadHook/commit/f75e9bb5f9b0171f57547a247063e7842bb7382e))
* 倒计时有概率不动[[#206](https://github.com/xihan123/QDReadHook/issues/206)]([#206](https://github.com/xihan123/QDReadHook/issues/206)) ([975ce87](https://github.com/xihan123/QDReadHook/commit/975ce87f3580448608c98282793209a3be6905f0))


### Feature Improvements

* 改善默认配置 ([2936059](https://github.com/xihan123/QDReadHook/commit/29360595b80515ed19daa50d94bf85063ec8b999))
* 新增温馨提示 ([3cd7e53](https://github.com/xihan123/QDReadHook/commit/3cd7e53296f5ca6e61a8ccc377f0506a420ed46a))


### Miscellaneous

* 更新依赖 ([b6e7592](https://github.com/xihan123/QDReadHook/commit/b6e759271f422e1c4dc53bce089ed28bd7a32ec4))

## [3.0.9](https://github.com/xihan123/QDReadHook/compare/v3.0.8...v3.0.9) (2024-01-28)


### Features

* **主设置:** 新增 `启用默认IMEI` ([aae212e](https://github.com/xihan123/QDReadHook/commit/aae212e9a5871bd70181729b8a7a1bfe7fc6788c))


### Miscellaneous

* 更新依赖 ([93c942f](https://github.com/xihan123/QDReadHook/commit/93c942f7399db9b2273b50278924b4dd576fb409))

## [3.0.8](https://github.com/xihan123/QDReadHook/compare/v3.0.7...v3.0.8) (2024-01-18)


### Bug Fixes

* `忽略限免批量订阅限制` 失效 ([14e2447](https://github.com/xihan123/QDReadHook/commit/14e2447b9492bf6ea3974175de9350bdd4cf5b35))


### Miscellaneous

* 更新依赖 ([8252293](https://github.com/xihan123/QDReadHook/commit/8252293ee23245697deb9d23fb704be93efbccdb))

## [3.0.7](https://github.com/xihan123/QDReadHook/compare/v3.0.6...v3.0.7) (2024-01-04)


### ⚠ BREAKING CHANGES

* 提高最低支持版本为 `7.9.326-1146`,低于此版本的勿更新！！！

### Features

* 提高最低支持版本为 `7.9.326-1146`,低于此版本的勿更新！！！ ([d1c0fa3](https://github.com/xihan123/QDReadHook/commit/d1c0fa3771d5a3d808db98cb2bb62811df05e8f0))


### Miscellaneous

* **master:** release 3.0.6 ([1b90247](https://github.com/xihan123/QDReadHook/commit/1b90247ffcdaea9c6191d70fb631e74c640b4c3e))
* 更新依赖 ([8a9b6ae](https://github.com/xihan123/QDReadHook/commit/8a9b6aecd1947be78f75cd561add4cc16c3ffd4e))

## [3.0.6](https://github.com/xihan123/QDReadHook/compare/v3.0.6...v3.0.6) (2023-12-04)


### Bug Fixes

* build.yml error ([d14984e](https://github.com/xihan123/QDReadHook/commit/d14984ef5cda2c6cd9ca7f44c696ca55f1ecd218))

## [3.0.6](https://github.com/xihan123/QDReadHook/compare/v3.0.5...v3.0.6) (2023-12-04)


### ⚠ BREAKING CHANGES

* 提高最低支持版本为 `7.9.318-1106`,低于此版本的勿更新！！！

### Features

* 提高最低支持版本为 `7.9.318-1106`,低于此版本的勿更新！！！ ([baaa8c3](https://github.com/xihan123/QDReadHook/commit/baaa8c3ce9897752309e16dac40928900b7b1e2f))
* **阅读页设置:** 新增 `阅读页字体自动替换` ([3083163](https://github.com/xihan123/QDReadHook/commit/3083163e23866364c61f360e216d7af7e2c5e903))


### Bug Fixes

* `自动领取章末红包` 弹2次 ([47c8080](https://github.com/xihan123/QDReadHook/commit/47c808027ebdbde28014ba7c38ec6f7a60914ab3))


### Miscellaneous

* 更新依赖 ([aa338a5](https://github.com/xihan123/QDReadHook/commit/aa338a50aa4b9f3899f19684fb9b0b057048f734))

## [3.0.4](https://github.com/xihan123/QDReadHook/compare/v3.0.3...v3.0.4) (2023-12-01)


### ⚠ BREAKING CHANGES

* 移除桌面图标相关

### Features

* **关于:** 新增`导入配置`、`清除起点所有缓存(保留登录信息和模块配置)` ([ecd7499](https://github.com/xihan123/QDReadHook/commit/ecd74996fb89f0d49cbbdb903ee2e9a69a2e4b2a))
* 提高版本号范围 ([2ef5aef](https://github.com/xihan123/QDReadHook/commit/2ef5aef7436881a6e5e50bba7d847d2ceab6265a))
* 移除桌面图标相关 ([fdbfaf8](https://github.com/xihan123/QDReadHook/commit/fdbfaf8ae1845eadd78f66119a5caf93a1d40743))


### Bug Fixes

* **隐藏控件:** 修复 `阅读页隐藏控件列表` ([1d63a55](https://github.com/xihan123/QDReadHook/commit/1d63a550827560f4f05f43ff8bac030f72249d75))


### Miscellaneous

* 更新依赖 ([3a32180](https://github.com/xihan123/QDReadHook/commit/3a32180c601ddcb3412eed6084a5f313826e4c19))
* 更新依赖 ([16cf96e](https://github.com/xihan123/QDReadHook/commit/16cf96e4d6fecd0dbc1f8a5881f56f67ee011bad))

## [3.0.3](https://github.com/xihan123/QDReadHook/compare/v3.0.2...v3.0.3) (2023-11-14)


### Features

* **广告设置:** `1086` + 版本新增主页-书架顶部广告 ([2f0fcfe](https://github.com/xihan123/QDReadHook/commit/2f0fcfe80e93caf1670f4243d39a6a66b0a4be16))


### Feature Improvements

* 改善权限申请提示 ([94bf781](https://github.com/xihan123/QDReadHook/commit/94bf781704cf75c939defb24bb4d8806623e3c04))


### Miscellaneous

* 更新依赖 ([222b9a6](https://github.com/xihan123/QDReadHook/commit/222b9a6b75cb3c7c979fe22a8c3c877459b852ae))

## [3.0.2](https://github.com/xihan123/QDReadHook/compare/v3.0.1...v3.0.2) (2023-10-26)


### Feature Improvements

* [[#159](https://github.com/xihan123/QDReadHook/issues/159)](https://github.com/xihan123/QDReadHook/issues/159) ([54a4f6f](https://github.com/xihan123/QDReadHook/commit/54a4f6fb3121aef9edfe5e97b4147a155d8880a9))
* 优化检查权限逻辑 ([434f6e5](https://github.com/xihan123/QDReadHook/commit/434f6e54ae26a26f8f85b8118faae41a86818598))


### Miscellaneous

* **master:** release 3.0.1 ([041919b](https://github.com/xihan123/QDReadHook/commit/041919b8fe9bcaf943074169771a31d266d299a1))
* 更新依赖 ([cb7814d](https://github.com/xihan123/QDReadHook/commit/cb7814d7e988779a1d81fca4cf145349047dd659))

## [3.0.1](https://github.com/xihan123/QDReadHook/compare/v3.0.1...v3.0.1) (2023-10-16)


### ⚠ BREAKING CHANGES

* 移除替换规则相关
* 迁移新API,后续版本理论上自动适配,仅支持起点 `7.9.306-1030` 及以上版本
* 提高最低支持Xposed版本为 90
* 移除 `屏蔽设置列表` -> `搜索相关`, `1005` + 版本 新增 `隐藏控件设置` -> `搜索配置列表`
* 移除 `自定义闪屏页`,`关闭闪屏` 合并至`拦截设置列表` -> `闪屏广告页面`
* 移除`QQ阅读免广告领取奖励`
* 移除`QQ阅读免广告领取奖励`
* 移除`配音导入`
* 仅支持 `884`+ 版本拦截启动图页面的广告

### Reverts

* **隐藏控件:** 恢复 `隐藏书架顶部标题` 功能 ([8d878c5](https://github.com/xihan123/QDReadHook/commit/8d878c5a1b092ec5a56da23f277b1c01976e4c04))


### Features

* `872`+ 版本书籍详情-快速屏蔽弹框 ([9ada69e](https://github.com/xihan123/QDReadHook/commit/9ada69e7a3a19f9a66a20f45832d32946ef4a60a))
* `872`+ 版本自定义粉丝值 ([958ca79](https://github.com/xihan123/QDReadHook/commit/958ca793bb69ea95b91326883f7d803b641038d2))
* `878` 版本隐藏主页面-顶部战力提示 ([aba0c76](https://github.com/xihan123/QDReadHook/commit/aba0c76850657f8792e3332712b437148a409cc8))
* `884` 版本新增章评相关 ([a64057f](https://github.com/xihan123/QDReadHook/commit/a64057f55c76379435724ccb403eaf776a912008))
* `884` 版本章评导入导出配音以及一键导出表情包 ([ab70a69](https://github.com/xihan123/QDReadHook/commit/ab70a69410790291c1874a3d75bcf422c2b5df06))
* `896`、`900` 版本新增阅读页最后一页相关 ([e56e33f](https://github.com/xihan123/QDReadHook/commit/e56e33f9fc305f4370c21b3e02527be4bea0b2dd))
* `944` 版本新增`自定义本地启动图`,以及QQ阅读-8.0.0.888_310 `免广告领取奖励` ([90b6cb1](https://github.com/xihan123/QDReadHook/commit/90b6cb1f27e7da6c933b40a53a040bf107e3b37e))
* `970` 版本 `自动领取阅读积分`100~500ms随机延迟领取 ([5bcecb6](https://github.com/xihan123/QDReadHook/commit/5bcecb60ac7328ff1ee9c722376d461b7d6e771e))
* `970` 版本`阅读积分`页面自动领取奖励 ([c89412a](https://github.com/xihan123/QDReadHook/commit/c89412a7ac87f18c88d409ab1e25a4f573526d1f))
* `970` 版本阅读页章评以及楼中楼图片、评论复制 ([5099804](https://github.com/xihan123/QDReadHook/commit/50998047a728597fac5358f1378cf65ff9b1662a))
* `阅读页-浮窗广告` 尝试性增强 ([ccf2927](https://github.com/xihan123/QDReadHook/commit/ccf29277b095a46ff2cc96cfc9fde9c0bf1786e6))
* `阅读页-浮窗广告` 尝试性增强 ([38c6e80](https://github.com/xihan123/QDReadHook/commit/38c6e802f4edcfa1d3bc1da80c6d1520768c696a))
* **Intercept:** `872` 版本自选拦截初始化 ([fc74523](https://github.com/xihan123/QDReadHook/commit/fc74523ed05cfabe123c18efb24e4b50c1550298))
* **main:** `872` 版本新旧精选布局 ([922da5e](https://github.com/xihan123/QDReadHook/commit/922da5e69179f455beafe9370510cb224ba7afd7))
* **ViewHide:** `872` 版本自选精选-隐藏控件 ([d6554e9](https://github.com/xihan123/QDReadHook/commit/d6554e985c0673c56fd5379f185a1bffa1cf02e1))
* 主设置:`启动时检查权限` 为可选，并调整隐藏控件的选项顺序 ([7bc4f41](https://github.com/xihan123/QDReadHook/commit/7bc4f413551fb993f20e3d20b57bedf7614b4ef4))
* **主设置:** `896`+ 试用模式弹框、真·免广告领取奖励 ([782d270](https://github.com/xihan123/QDReadHook/commit/782d270831f63d97f60c9e02fa5e724efbc4135c))
* **主设置:** `906` +版本新增隐藏福利列表 ([83ab08a](https://github.com/xihan123/QDReadHook/commit/83ab08a6cbcc47bd5340e7168d43ad11e3ac5ad3))
* **主设置:** `970` 版本 `发帖显示图片直链` ([e80c133](https://github.com/xihan123/QDReadHook/commit/e80c13394bd73aead61cf3c6c1d90f35d4ae6f7d))
* **主设置:** `994` + 新增 `测试页面` 以及更新免责声明 ([3d049e6](https://github.com/xihan123/QDReadHook/commit/3d049e6c1bd2a675e00af2cb649dfa81052364a0))
* **主设置:** `994` + 新增 `自动跳过闪屏页`,启用后无闪屏页直接进入主页面 ([c7a2069](https://github.com/xihan123/QDReadHook/commit/c7a2069e574a1c11ce27c3f49de74311398fa60d))
* 仅支持 `884`+ 版本拦截启动图页面的广告 ([41de6e2](https://github.com/xihan123/QDReadHook/commit/41de6e21d123fa9b6e2a266450f4feaf18a5fecf))
* 优化自动领取功能 ([d4be397](https://github.com/xihan123/QDReadHook/commit/d4be397ff2c7d1aececff392464b1315b63c8860))
* 最低支持版本改为Android 8 ([9a02bd8](https://github.com/xihan123/QDReadHook/commit/9a02bd80720120440b7077927f7c76582365a2c2))
* 尝试性禁用检查更新弹框 ([a5b8f68](https://github.com/xihan123/QDReadHook/commit/a5b8f68a38020b6a7c4b4ce9306120c24b24679a))
* **广告:** 禁用初始化GDT SDK ([4335d0d](https://github.com/xihan123/QDReadHook/commit/4335d0d20f39bc96dca4a604cd0334cb9bebc72b))
* **广告设置:** `896`+ 阅读页-最新页面弹框广告 ([327f2de](https://github.com/xihan123/QDReadHook/commit/327f2de57c8bbaf175cbe8dea76828dfbd789be3))
* **广告配置:** 合并闪屏广告、增强GDT广告 ([eb82d4a](https://github.com/xihan123/QDReadHook/commit/eb82d4a5b94604ec8063aadcca44544bed20665f))
* **拦截设置:** `896`+ 异步主GDT广告任务、异步主游戏广告SDK任务、异步主游戏下载任务、异步子屏幕截图任务、部分环境检测 ([3983fc4](https://github.com/xihan123/QDReadHook/commit/3983fc401ea91303aa9eb1a287ebeb49e9500b22))
* **拦截设置:** `970` 版本 `发帖图片水印` ([689a350](https://github.com/xihan123/QDReadHook/commit/689a350ba1e365cd024aeaa9e6a8bf1af0ca6ff3))
* **拦截设置:** `970` 版本实验性功能`阅读页水印` ([72b316a](https://github.com/xihan123/QDReadHook/commit/72b316a65f668f336465f8c988f6b0abddbb3d01))
* **拦截设置:** `980` + 新增拦截 `自动跳转精选` ([3429f08](https://github.com/xihan123/QDReadHook/commit/3429f08178441f11d8e35c3ee63afb285ab4625a))
* **拦截设置:** `994` + 新增 `拦截首次安装分析` ([9dc5148](https://github.com/xihan123/QDReadHook/commit/9dc5148e84ff19df2c4c20fcf57b1a8a58c1d0a9))
* **拦截设置:** 合并青少年模式弹框、检查更新 ([23970d9](https://github.com/xihan123/QDReadHook/commit/23970d91733db920e7f7869bf0c562846f5824a8))
* **拦截设置:** 新增一处拦截选项(非`980`+版本慎开) ([ed663b6](https://github.com/xihan123/QDReadHook/commit/ed663b6601ede7d0fc664218f7020a9a286b7190))
* 提高最低支持Xposed版本为 90 ([2b93f7c](https://github.com/xihan123/QDReadHook/commit/2b93f7c2c419becda8dd954a244e6c2693a7e4cc))
* 新增启动强制检查存储权限、`1050` + 启用旧版每日导读、移除新版不支持的功能 ([48e2a6d](https://github.com/xihan123/QDReadHook/commit/48e2a6ddee76c9dda7fccc0480e64722ac3a8e0c))
* **替换规则设置:** `896`+ 自定义设备信息 ([2853ead](https://github.com/xihan123/QDReadHook/commit/2853eadb1945b4d085e536240572f728c24db96b))
* 模块管理页面支持 `Android12` + 动态配色 ([408f92a](https://github.com/xihan123/QDReadHook/commit/408f92a0237a2d718771a7c1f16fd48979a0c8cc))
* 移除 `屏蔽设置列表` -&gt; `搜索相关`, `1005` + 版本 新增 `隐藏控件设置` -> `搜索配置列表` ([38309d8](https://github.com/xihan123/QDReadHook/commit/38309d89b1b0da4cec5c721f6dd485ccd6d6e60a))
* 移除 `自定义闪屏页`,`关闭闪屏` 合并至`拦截设置列表` -&gt; `闪屏广告页面` ([520ae38](https://github.com/xihan123/QDReadHook/commit/520ae38ec1d3af80e958a670aaff32d139efc276))
* 移除`QQ阅读免广告领取奖励` ([26c9851](https://github.com/xihan123/QDReadHook/commit/26c9851dfee1c43950ae0b89f1447b800e951165))
* 移除`QQ阅读免广告领取奖励` ([c1b8962](https://github.com/xihan123/QDReadHook/commit/c1b8962d60fbf283651c6fca9d520ebde77aefbb))
* 移除`配音导入` ([5052b4d](https://github.com/xihan123/QDReadHook/commit/5052b4d0c87f7151b1f61580dd6754b113bf1ca1))
* 移除替换规则相关 ([806f8aa](https://github.com/xihan123/QDReadHook/commit/806f8aa46295b268c1c9a1705c7e5b03f358b6de))
* **自动化设置:** `1030` + 合并自动签到、自动领积分、自动点击章末红包 ([ecc7217](https://github.com/xihan123/QDReadHook/commit/ecc7217fb96384b4afaa1318cc5faadcae6fa10c))
* 迁移新API,后续版本理论上自动适配,仅支持起点 `7.9.306-1030` 及以上版本 ([4b6049d](https://github.com/xihan123/QDReadHook/commit/4b6049df6a9a31a6159df744f860a27a2be5422e))
* 适配 `1005` 版本 ([bc0136c](https://github.com/xihan123/QDReadHook/commit/bc0136c244856c5b2e811ba4888c976bc65f16df))
* 适配 `1020` 版本 ([8c42f6d](https://github.com/xihan123/QDReadHook/commit/8c42f6d4f7f436f0ee6c47e66bed6e6221c51142))
* 适配 `1030` 版本 ([42bbd91](https://github.com/xihan123/QDReadHook/commit/42bbd91b4b31b5e7629ae0920410c7e3e90fbe8a))
* 适配 `878` 版本 ([40efb28](https://github.com/xihan123/QDReadHook/commit/40efb282b4dfbf5d80cbaa08524f5bea1b7d3dc5))
* 适配 `884` 版本 ([4742bd6](https://github.com/xihan123/QDReadHook/commit/4742bd66bf6c249fe5848ce44fe4479d87e049b7))
* 适配 `890` 版本 ([d5620a8](https://github.com/xihan123/QDReadHook/commit/d5620a818e4696323cfdb7051ed681a3dfe32032))
* 适配 `896`、`900` 版本 ([4666116](https://github.com/xihan123/QDReadHook/commit/4666116fb78120209a5485e9771147e631c951c5))
* 适配 `906` 版本 ([64d51e9](https://github.com/xihan123/QDReadHook/commit/64d51e937c29a4b9a72dc216705500ba5387d0ea))
* 适配 `916` 版本 ([162c6b6](https://github.com/xihan123/QDReadHook/commit/162c6b624d7577e6b61d7cc0001937c8fd227897))
* 适配 `924` 版本 ([f0a5165](https://github.com/xihan123/QDReadHook/commit/f0a51650e7aa27f2e698842fee2cb692411f5749))
* 适配 `932` 版本 ([3e709bf](https://github.com/xihan123/QDReadHook/commit/3e709bf936bebf4c2d9da2d6d57fd298cd623076))
* 适配 `938` 版本 ([5a4af1f](https://github.com/xihan123/QDReadHook/commit/5a4af1feca687a22c269aa36bc84339e7f3d578b))
* 适配 `944` 版本 ([7c5ccd6](https://github.com/xihan123/QDReadHook/commit/7c5ccd6fd11be16f68622e41f022744b2e1db2c5))
* 适配 `950` 版本、新增一处屏蔽选项 ([839320e](https://github.com/xihan123/QDReadHook/commit/839320ef402d5a816ed6880202795237efa40bd7))
* 适配 `958` 版本、修复配音导出 ([fbfe684](https://github.com/xihan123/QDReadHook/commit/fbfe684ddd85f2c738a00265ff5fec401e8cb0b1))
* 适配 `958` 版本、修复配音导出 ([33dd884](https://github.com/xihan123/QDReadHook/commit/33dd884562066f60f030153068df3e10a7005526))
* 适配 `970` 版本 ([40c07ff](https://github.com/xihan123/QDReadHook/commit/40c07ff1db57948473b604a997141e109a51b14a))
* 适配 `970` 版本 ([f589c4a](https://github.com/xihan123/QDReadHook/commit/f589c4a41c238fe50f8fe437116fea5536c022f5))
* 适配 `980` 版本 ([b68a2fc](https://github.com/xihan123/QDReadHook/commit/b68a2fc277b4c1986a49c2294bd472e8702210ca))
* 适配 `994` 版本 ([3f0a604](https://github.com/xihan123/QDReadHook/commit/3f0a604610488c3006b44b818531711a190f39f7))
* **阅读页设置:** `890`~`970` 版本 `配音导出`支持网络及本地地址，`884`版本移除此功能 ([600d366](https://github.com/xihan123/QDReadHook/commit/600d36630884375d6816f0e1ca49f4fda31aec24))
* **隐藏控件设置:** `1005` + 版本新增 `主页-隐藏控件列表` -&gt; `书架顶部标题` ([97c67f2](https://github.com/xihan123/QDReadHook/commit/97c67f2d12ae9cbd5bed5cb39f591091781165b8))
* **隐藏控件设置:** `1005` + 版本新增 `阅读页-隐藏控件` ([607dd59](https://github.com/xihan123/QDReadHook/commit/607dd59d14272c5374ac80894758683b7f2b88ab))
* **隐藏控件设置:** `827`~`970` 版本 `隐藏底部导航栏`优化 ([9f2cfa5](https://github.com/xihan123/QDReadHook/commit/9f2cfa522ad31268845e4c704c7946a63db44353))


### Bug Fixes

* [Bug] 点赞按钮异常 [[#123](https://github.com/xihan123/QDReadHook/issues/123)](https://github.com/xihan123/QDReadHook/issues/123) ([df41fc0](https://github.com/xihan123/QDReadHook/commit/df41fc094e36095f7768119571d6e090e7e5a8ff))
* `890` 版本新"我"布局隐藏控件失效 ([3a31d74](https://github.com/xihan123/QDReadHook/commit/3a31d74aebd1ee03869803dd06f9ba8bc71d768f))
* `970` 版本 `精选-标题`隐藏失效 ([44b12c3](https://github.com/xihan123/QDReadHook/commit/44b12c3c94c86e11d7677cf88705879673e70183))
* `970` 版本底部导航栏隐藏失效 ([50f8ccb](https://github.com/xihan123/QDReadHook/commit/50f8ccb76b18431a0ccb0779289ea0d8acf69c26))
* `屏蔽-精选主页面`失效 ([1a77a0d](https://github.com/xihan123/QDReadHook/commit/1a77a0d45a37cbfbf55d40b98fd1113efcc6b001))
* `屏蔽-精选主页面`失效 ([4d6cc21](https://github.com/xihan123/QDReadHook/commit/4d6cc212a4155b40a08bb81344f17c4b2e614e55))
* `自动跳过闪屏页` 按钮未对齐 ([1840c82](https://github.com/xihan123/QDReadHook/commit/1840c82aa61bfe382c047ec8f9b5189df681310c))
* build.gradle.kts ([f5a9e8d](https://github.com/xihan123/QDReadHook/commit/f5a9e8d6ee2d7a84dbd154032d85e79e42dbc91d))
* **deps:** update activity to v1.8.0-alpha05 ([968924f](https://github.com/xihan123/QDReadHook/commit/968924f74dc054e24adb6525dbfb918c96e59155))
* **deps:** update dependency androidx.activity:activity-compose to v1.8.0-alpha06 ([bfaa442](https://github.com/xihan123/QDReadHook/commit/bfaa442fed4c884c617f0a287a830cf854b4d7ce))
* **deps:** update dependency com.alibaba.fastjson2:fastjson2-kotlin to v2.0.34 ([116cac9](https://github.com/xihan123/QDReadHook/commit/116cac9163d08c49c66f41b61a476e831ea9258f))
* **deps:** update dependency com.google.android.material:material to v1.10.0-alpha04 ([1a58cea](https://github.com/xihan123/QDReadHook/commit/1a58cea1ab87f8a665eeafe2976028b152a9b788))
* **deps:** update dependency org.htmlunit:htmlunit3-android to v3.3.0 ([fc741e3](https://github.com/xihan123/QDReadHook/commit/fc741e301a9aacf07cdd1ef18e4d93211b9929d5))
* UpsideDownCake ([af89061](https://github.com/xihan123/QDReadHook/commit/af890610baa321503e80675895452de01907147d))
* 依赖不存在 ([780a5c1](https://github.com/xihan123/QDReadHook/commit/780a5c1ce1f046c4c60f1caf1261c01b140b5962))
* 修复 `搜索配置` 选项可能为空白 ([ca1e851](https://github.com/xihan123/QDReadHook/commit/ca1e851239e7ce96748669a0daeea811cad5708c))
* 修复 `隐藏每日导读` 选项失效 ([714c2b9](https://github.com/xihan123/QDReadHook/commit/714c2b92017717daa23230469330e6e59bdb9108))
* 修复深色模式 `免责声明` 字体颜色看不清 ([a05dadb](https://github.com/xihan123/QDReadHook/commit/a05dadb37e7297c2a3d086ee3ed4ec7f27a7aa3d))
* 免责声明逻辑 ([7165dff](https://github.com/xihan123/QDReadHook/commit/7165dff4814f7f9abe00cb6d53475ec57cf9f884))
* 内置后不显示模块设置入口 ([df19d76](https://github.com/xihan123/QDReadHook/commit/df19d761c3295cf4638f65f7dcdb5c045403940f))
* 可能出现的屏蔽失效 ([53fecd9](https://github.com/xihan123/QDReadHook/commit/53fecd9427b7882dbbdafb897efaf077a376aad2))
* 配音导入对话框 ([e5a1140](https://github.com/xihan123/QDReadHook/commit/e5a11401bd32ba9886338f373518bf85f75b8889))


### Feature Improvements

* 整理、格式化、排序代码 ([9a559e2](https://github.com/xihan123/QDReadHook/commit/9a559e29f75eac5851be3c20df158c9cd334eafe))
* 整理、格式化、排序代码 ([e0ff176](https://github.com/xihan123/QDReadHook/commit/e0ff17693a584cde5a1229c9c60dca5ba2a38063))
* 整理、格式化、排序代码 ([20768d7](https://github.com/xihan123/QDReadHook/commit/20768d76df69c026861c64ca9dcfa2e3d75926a8))
* 重构 `屏蔽选项列表` ([9e3ec99](https://github.com/xihan123/QDReadHook/commit/9e3ec996b48d1bdc3700ee3df743c7dadd77a9fd))
* 重构 `广告设置列表` ([c391b93](https://github.com/xihan123/QDReadHook/commit/c391b93ceb8fb6ec471bb6c44803652c73a13144))
* 重构 `底部导航栏选项列表` ([0d2727f](https://github.com/xihan123/QDReadHook/commit/0d2727ff21806074f9aa9d226b699e518a26e481))


### CI

* automerge-action.yml ([3b87691](https://github.com/xihan123/QDReadHook/commit/3b876913da521b336258ee533c57ff7eb51b357b))
* automerge-action.yml ([6778db0](https://github.com/xihan123/QDReadHook/commit/6778db0e20b83e4bf40ed329b470bb66f217975d))
* build.yml ([0ab6f6d](https://github.com/xihan123/QDReadHook/commit/0ab6f6d4c2d9f0656748d9a5bbad2d5e5b0bcd95))
* build.yml ([e147043](https://github.com/xihan123/QDReadHook/commit/e1470431dbd66260329dfbf8c22d79132ed72dd2))
* build.yml ([6b420eb](https://github.com/xihan123/QDReadHook/commit/6b420ebe8849d6ed1ac305b08f00ecc4f78c5364))
* build.yml ([65b18de](https://github.com/xihan123/QDReadHook/commit/65b18debdf373d95efcd60a8eb6d34e73fa85607))
* build.yml ([29a3f27](https://github.com/xihan123/QDReadHook/commit/29a3f27fd6fe27231326773313ae41e76562e43d))
* build.yml ([07152b6](https://github.com/xihan123/QDReadHook/commit/07152b61179cbd1835e638ec195e0a56bb622928))
* build.yml ([350d1ba](https://github.com/xihan123/QDReadHook/commit/350d1ba3b57df5376740932da43573bf50deafa1))
* build.yml ([25a639c](https://github.com/xihan123/QDReadHook/commit/25a639c59a35f3d8677ad3263365fe43c9194849))
* build.yml ([9b7ca28](https://github.com/xihan123/QDReadHook/commit/9b7ca283466645f82c444caed73ae55c8593c29a))
* build.yml ([a0e846c](https://github.com/xihan123/QDReadHook/commit/a0e846c5cdd5a09ab1d9ff0d64764ca5623058f9))
* gitlab-ci.yml ([a5d06d0](https://github.com/xihan123/QDReadHook/commit/a5d06d02204071d9ae5e75e73dd8c604adb363cd))
* gitlab-ci.yml ([543f318](https://github.com/xihan123/QDReadHook/commit/543f3186755492fb80aa6cf10b6141c156c3bbe5))
* release.yml ([19b4e37](https://github.com/xihan123/QDReadHook/commit/19b4e372185d5dc71e812f216efeb3a083c036f2))
* release.yml ([e71140d](https://github.com/xihan123/QDReadHook/commit/e71140d27c16673a8cb69c68567d8a20d6a5302b))
* release.yml ([9850930](https://github.com/xihan123/QDReadHook/commit/9850930874910990a0555359493aa4beb39fb92a))
* release.yml ([cdaa591](https://github.com/xihan123/QDReadHook/commit/cdaa5910c10f0621329e0061f00d53180df2aa50))
* release.yml ([bbe3722](https://github.com/xihan123/QDReadHook/commit/bbe37222b3449bf793c60dbac216e0e3b711491d))
* release.yml ([963b361](https://github.com/xihan123/QDReadHook/commit/963b361cc9e00b5a93de0e0853a40b9a390c579d))
* release.yml ([c1f6366](https://github.com/xihan123/QDReadHook/commit/c1f636642c338380aee3dcd0da2d49c36c8f525a))
* release.yml ([da43342](https://github.com/xihan123/QDReadHook/commit/da43342ae9beb03e9b769e2889eff853fb2081b7))
* release.yml ([a687038](https://github.com/xihan123/QDReadHook/commit/a6870384b6a7d99d74688003d5d775834236fabb))
* release.yml ([afd689b](https://github.com/xihan123/QDReadHook/commit/afd689b74f90f483f3db0ada5f5685a3cf4cd379))
* release.yml ([8a1dca0](https://github.com/xihan123/QDReadHook/commit/8a1dca0b826c954ce873d45ea91fa5ee687cc18d))
* release.yml ([c141444](https://github.com/xihan123/QDReadHook/commit/c14144486bff474d9e347360be32f8ed69a03c89))
* release.yml ([6dffa62](https://github.com/xihan123/QDReadHook/commit/6dffa6241f48dbaaa7f761c2630098be29fa067a))
* release.yml ([8f4a426](https://github.com/xihan123/QDReadHook/commit/8f4a4261d004e192ce60e71e30c091b59e9a24c0))
* release.yml ([4aa87bf](https://github.com/xihan123/QDReadHook/commit/4aa87bfbeb9896e28e26c08e416afdf4d30d2b1a))
* release.yml ([a8462df](https://github.com/xihan123/QDReadHook/commit/a8462df14cdca9efe0b9329d99d211874e9466ec))
* release.yml ([a55af53](https://github.com/xihan123/QDReadHook/commit/a55af531758385255ba8c55e7584d675e3bc63c3))
* release.yml ([ceb2f67](https://github.com/xihan123/QDReadHook/commit/ceb2f67ad25d885e0851864c0d14d08047923e61))
* release.yml ([258a623](https://github.com/xihan123/QDReadHook/commit/258a623aa247b457d7eb2756df93be653ffcd5a0))
* release.yml ([bccd8ef](https://github.com/xihan123/QDReadHook/commit/bccd8ef95aed602d33170b056074af87e85ebb42))
* release.yml ([c2b07d6](https://github.com/xihan123/QDReadHook/commit/c2b07d6690cba39abff8649af6242a55d506a270))
* release.yml ([bced994](https://github.com/xihan123/QDReadHook/commit/bced9941c10b94e6235c1c9a0009a6160ce8de9f))
* release.yml ([012ad09](https://github.com/xihan123/QDReadHook/commit/012ad09d9fb1e797e276ad597491caa785593690))
* release.yml ([fc50d23](https://github.com/xihan123/QDReadHook/commit/fc50d23882d494f1b97ef7e26e62d1fc509c705e))
* release.yml ([14833f2](https://github.com/xihan123/QDReadHook/commit/14833f29e1171eb67ce67467e2ae67aeb3173d4c))
* release.yml ([6abc522](https://github.com/xihan123/QDReadHook/commit/6abc5221e4d5d626a5f12bdb21fcc59186b9f8ce))
* stale.yml ([6a4808c](https://github.com/xihan123/QDReadHook/commit/6a4808cacac71b8ad5e65406b05f6ce3d7df8964))


### Miscellaneous

* **deps:** update dependency com.google.devtools.ksp to v1.9.0-rc-1.0.11 ([461ece5](https://github.com/xihan123/QDReadHook/commit/461ece5db162d903a17c36408e0ce788ac95fa0d))
* **deps:** update kotlin monorepo to v1.9.0-rc ([07b80e7](https://github.com/xihan123/QDReadHook/commit/07b80e759ec2095b4247e9372961046e9693a84d))
* Initial commit ([43517f5](https://github.com/xihan123/QDReadHook/commit/43517f5bffcb3a53c6970d514ee39da48d7a4d8f))
* **master:** release 2.0.4 ([b5d5c41](https://github.com/xihan123/QDReadHook/commit/b5d5c41a74e6c9f2f181220f06d9ee3668e7fafa))
* **master:** release 2.0.4 ([81327f9](https://github.com/xihan123/QDReadHook/commit/81327f91ae079a75a63ecfa4f5f5baafce1aea05))
* **master:** release 2.0.5 ([c7ae607](https://github.com/xihan123/QDReadHook/commit/c7ae6070df5619204387f087b8741b5dded8214d))
* **master:** release 2.0.6 ([a0cb22d](https://github.com/xihan123/QDReadHook/commit/a0cb22d3c7a6e10292dac21bd667e800fcc57523))
* **master:** release 2.0.6 ([cfa49e2](https://github.com/xihan123/QDReadHook/commit/cfa49e263651a4610d954a016cd643adbaeb6d11))
* **master:** release 2.0.7 ([6952b49](https://github.com/xihan123/QDReadHook/commit/6952b493813436868d70f0997129a62cc87d9f00))
* **master:** release 2.0.7 ([4c83ba0](https://github.com/xihan123/QDReadHook/commit/4c83ba0d1d2205a90ef05dd458373130df4e2c25))
* **master:** release 2.0.7 ([3f08b07](https://github.com/xihan123/QDReadHook/commit/3f08b079d2440825abeb4bfee97b8fdd3213964c))
* **master:** release 2.0.7 ([b35c104](https://github.com/xihan123/QDReadHook/commit/b35c10478159d77ee95332d80c14389cb5ae83eb))
* **master:** release 2.0.8 ([7d3311b](https://github.com/xihan123/QDReadHook/commit/7d3311b8e1021e200f364435f0f947aa429ffe03))
* **master:** release 2.0.8 ([b3baa6a](https://github.com/xihan123/QDReadHook/commit/b3baa6a90a7868c5d18469a11c7c80154ca06b66))
* **master:** release 2.0.8 ([0bc1030](https://github.com/xihan123/QDReadHook/commit/0bc103040fbeeb29cedd831ce77e5cda315164ce))
* **master:** release 2.0.9 ([360b229](https://github.com/xihan123/QDReadHook/commit/360b22919ac401c7d7b57724210c547078753fb2))
* **master:** release 2.0.9 ([98e245c](https://github.com/xihan123/QDReadHook/commit/98e245c5af1772535a5305c05edfaafc505f5c49))
* **master:** release 2.1.0 ([cdea359](https://github.com/xihan123/QDReadHook/commit/cdea3592d59aecd58585fc3a24233189daac6ca3))
* **master:** release 2.1.0 ([afd2f06](https://github.com/xihan123/QDReadHook/commit/afd2f064c115d32de186cd10262d3f27eeb27926))
* **master:** release 2.1.0 ([3c5035a](https://github.com/xihan123/QDReadHook/commit/3c5035ab81de59c029be633841135e68ebfbee4e))
* **master:** release 2.1.0 ([b58da5a](https://github.com/xihan123/QDReadHook/commit/b58da5aa1d783e2a9cf06a7b3864e345d1c486e5))
* **master:** release 2.1.0 ([e0ce41d](https://github.com/xihan123/QDReadHook/commit/e0ce41d0f3c8ab31281ee14672e687db42f4424c))
* **master:** release 2.1.1 ([5d427d6](https://github.com/xihan123/QDReadHook/commit/5d427d6dcd02f7e36fad715e9e6ffb9f388b91fd))
* **master:** release 2.1.1 ([333ae33](https://github.com/xihan123/QDReadHook/commit/333ae332657f07038cbb9067b5be56c527668533))
* **master:** release 2.1.2 ([2433660](https://github.com/xihan123/QDReadHook/commit/243366077d580c1eeefe4dd4a44f606e3e33aaa8))
* **master:** release 2.1.2 ([62adb53](https://github.com/xihan123/QDReadHook/commit/62adb53b26ec9a03e14027f4b5597b9b6775616c))
* **master:** release 2.1.2 ([bdd54bd](https://github.com/xihan123/QDReadHook/commit/bdd54bd29a72cd289c0fbc74d326b56a95405cbd))
* **master:** release 2.1.3 ([3487c69](https://github.com/xihan123/QDReadHook/commit/3487c69ecb7d97d030641656b5e8128c9e0774de))
* **master:** release 2.1.3 ([25e0055](https://github.com/xihan123/QDReadHook/commit/25e00559f09a1d16871d0311eac01d8f8467e10e))
* **master:** release 2.1.4 ([92832a9](https://github.com/xihan123/QDReadHook/commit/92832a9b4157a967b34b8e8dbb29e45a857d5f9f))
* **master:** release 2.1.4 ([ba2f338](https://github.com/xihan123/QDReadHook/commit/ba2f338876e8316383d6e87978a216c73edff48a))
* **master:** release 2.1.5 ([5b07e2c](https://github.com/xihan123/QDReadHook/commit/5b07e2cf5f6beeb81528524fb781909767c8bc2e))
* **master:** release 2.1.5 ([f02f36c](https://github.com/xihan123/QDReadHook/commit/f02f36c24462fb13c2a925e31b7809184736d155))
* **master:** release 2.1.6 ([4951cf5](https://github.com/xihan123/QDReadHook/commit/4951cf5ea0e1a85740c9ccaa9a2ce65ba14c9e33))
* **master:** release 2.1.6 ([5138196](https://github.com/xihan123/QDReadHook/commit/513819670481ddfc2ba9a9babd081f2a2b4fe30f))
* **master:** release 2.1.6 ([3f0b29f](https://github.com/xihan123/QDReadHook/commit/3f0b29fd8037767b88097052caa4858d36e60453))
* **master:** release 2.1.6 ([602f187](https://github.com/xihan123/QDReadHook/commit/602f1876af64cda345a28ddf0ed1f10b60b4b703))
* **master:** release 2.1.6 ([84047a6](https://github.com/xihan123/QDReadHook/commit/84047a6974931e6255a6c653fc51ce2fe6755326))
* **master:** release 2.1.6 ([8842c8f](https://github.com/xihan123/QDReadHook/commit/8842c8f95bb06101dbaed33b185598b483a8263f))
* **master:** release 2.1.6 ([dc502ff](https://github.com/xihan123/QDReadHook/commit/dc502ff9f0ceda5e307dd1d269e1ea182cee53cb))
* **master:** release 2.1.6 ([1559607](https://github.com/xihan123/QDReadHook/commit/1559607f925e8d0b6347d571d3434055d30cdae9))
* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))
* **master:** release 2.1.7 ([d339980](https://github.com/xihan123/QDReadHook/commit/d3399809de98884d00291ec58a347ec88e8c0904))
* **master:** release 2.1.7 ([71e65cb](https://github.com/xihan123/QDReadHook/commit/71e65cb1656dff259ed9e76981419c1229afb9b7))
* **master:** release 2.1.8 ([0450cb6](https://github.com/xihan123/QDReadHook/commit/0450cb64d65539ca7af7343c0624192d8e1ee422))
* **master:** release 2.1.8 ([2deef1c](https://github.com/xihan123/QDReadHook/commit/2deef1cab6d30eeb734b2b09fc2b860f69d7a322))
* **master:** release 2.1.8 ([1fccf07](https://github.com/xihan123/QDReadHook/commit/1fccf072ce675a1b5be2911fabbd048be10419b8))
* **master:** release 2.1.8 ([7d4dcaa](https://github.com/xihan123/QDReadHook/commit/7d4dcaaa375c07cc4a99a3e3ab1ea97ad5e774bc))
* **master:** release 2.1.9 ([143ec94](https://github.com/xihan123/QDReadHook/commit/143ec944f35b2278d2106dcee448f609e2c3a26c))
* **master:** release 2.1.9 ([31dd984](https://github.com/xihan123/QDReadHook/commit/31dd984f56f71c8a4118b9186221433df69d2c19))
* **master:** release 2.1.9 ([32e2e43](https://github.com/xihan123/QDReadHook/commit/32e2e438a53f86e29db7792a159862c10a0fdefb))
* **master:** release 2.1.9 ([8cd40da](https://github.com/xihan123/QDReadHook/commit/8cd40da3afd48eb0d8bb8f0c63bcccec9deaa3f0))
* **master:** release 2.2.0 ([645d861](https://github.com/xihan123/QDReadHook/commit/645d8611412bfdc126a211e13f6bc3059eadea33))
* **master:** release 2.2.2 ([e788c50](https://github.com/xihan123/QDReadHook/commit/e788c500915d9108666214af2aa5c30bd3e7ce52))
* **master:** release 2.2.3 ([752771d](https://github.com/xihan123/QDReadHook/commit/752771d215aedbcc52403bf8ed6179a65ab2d0a4))
* **master:** release 2.2.4 ([3767495](https://github.com/xihan123/QDReadHook/commit/3767495b497a96123ba50ce07bb4baf26cef5e6b))
* **master:** release 2.2.6 ([fcf8f33](https://github.com/xihan123/QDReadHook/commit/fcf8f336dfe10026f70e8d4c0e7d2f7569629962))
* **master:** release 2.2.7 ([4a8d865](https://github.com/xihan123/QDReadHook/commit/4a8d865eb468a81b757fb39d92d0886ad3bf51f2))
* **master:** release 2.2.8 ([5bcaed0](https://github.com/xihan123/QDReadHook/commit/5bcaed0bbaed675d0340e2154a0b7fba2ddd4622))
* **master:** release 2.2.9 ([9be1485](https://github.com/xihan123/QDReadHook/commit/9be1485a654345cdc72bd71e1311f4ca8886b30e))
* **master:** release 2.3.0 ([0a9d2bf](https://github.com/xihan123/QDReadHook/commit/0a9d2bfadb33c3cac7df28c01119ffa3acdf1956))
* **master:** release 2.3.1 ([9d33939](https://github.com/xihan123/QDReadHook/commit/9d3393987d83246d299b769d693f919fd3f8567e))
* **master:** release 2.3.2 ([3525683](https://github.com/xihan123/QDReadHook/commit/35256832a74550dceb6be3129a238555bd9be008))
* **master:** release 2.3.3 ([8facf5f](https://github.com/xihan123/QDReadHook/commit/8facf5fda1814dfa32e6121f34256cf549ffd5e0))
* **master:** release 2.3.4 ([274a584](https://github.com/xihan123/QDReadHook/commit/274a5844cfa2a34ab9e8d56ded7aaecf72729faa))
* **master:** release 2.3.5 ([0709c2b](https://github.com/xihan123/QDReadHook/commit/0709c2bf9c552eeb35e98f836c9d3096d6bd93f8))
* **master:** release 2.3.6 ([d37d642](https://github.com/xihan123/QDReadHook/commit/d37d64244bb974340186dd09ea2e71fb621f8b57))
* **master:** release 2.3.7 ([8fb8c5b](https://github.com/xihan123/QDReadHook/commit/8fb8c5bf84df46d68c602a8cf78c7a080a1d3a63))
* **master:** release 2.3.8 ([08c4de9](https://github.com/xihan123/QDReadHook/commit/08c4de95f03d38726d653f7461710d8b2d27cce4))
* **master:** release 3.0.0 ([ef9cb71](https://github.com/xihan123/QDReadHook/commit/ef9cb71b10c30c1ba8a55c2e3e0e785ace860c5a))
* **master:** release 3.0.0 ([d08cc37](https://github.com/xihan123/QDReadHook/commit/d08cc37dfbf5ed5f0a619743eea4f98b154f4f28))
* **master:** release 3.0.1 ([c3195d2](https://github.com/xihan123/QDReadHook/commit/c3195d21bc9b07cefe9937f02f10798d7a50442a))
* 其他 ([1898b9d](https://github.com/xihan123/QDReadHook/commit/1898b9d5983863c3ca8111b902a9def74ce99c9c))
* 其他 ([69042cf](https://github.com/xihan123/QDReadHook/commit/69042cfe9e76f87e9a658046f9a74803acdaa079))
* 反射获取父类参数使用广度优先搜索算法 ([f0dca07](https://github.com/xihan123/QDReadHook/commit/f0dca0700bbec0a5bce78b28ee522100598658c9))
* 整理代码 ([81852fd](https://github.com/xihan123/QDReadHook/commit/81852fdda8208a6eec61778f623eee6f12b61464))
* 整理代码 ([17255f4](https://github.com/xihan123/QDReadHook/commit/17255f420a814033581a38a4d3cc0ccb94904f97))
* 整理代码 ([512920e](https://github.com/xihan123/QDReadHook/commit/512920ef4780a49d316ec03540d8a5ea41127698))
* 整理代码 ([30b7d8c](https://github.com/xihan123/QDReadHook/commit/30b7d8cf599e417b49367c012144bee15e5a0012))
* 整理代码 ([2ed7da0](https://github.com/xihan123/QDReadHook/commit/2ed7da04fe80c0f61993c50c4479ceb6aff253ec))
* 新增免责声明 ([b5b19d1](https://github.com/xihan123/QDReadHook/commit/b5b19d1e7fa1a46058ecddb75fd1e8d3345bd4a8))
* 新增或移除选项时根据标题重新排序 ([f795dc1](https://github.com/xihan123/QDReadHook/commit/f795dc145fc346feaea39e4747ea03b07bbac806))
* 更新依赖 ([a185651](https://github.com/xihan123/QDReadHook/commit/a185651f5e32facc7e939182dc350efcc2e4ed0a))
* 更新依赖 ([17d6b69](https://github.com/xihan123/QDReadHook/commit/17d6b69e859d46d6c0d504ad05fce26da0d01715))
* 更新依赖 ([99575e0](https://github.com/xihan123/QDReadHook/commit/99575e0d2a4452c0457c3608291c098fc6ad9471))
* 更新依赖 ([4f41897](https://github.com/xihan123/QDReadHook/commit/4f4189705e98c193e2190968b4d9fdc156f697b8))
* 更新依赖 ([afd689b](https://github.com/xihan123/QDReadHook/commit/afd689b74f90f483f3db0ada5f5685a3cf4cd379))
* 更新依赖 ([52958c9](https://github.com/xihan123/QDReadHook/commit/52958c9cc065567d5171d6388a4fe91204032f42))
* 更新依赖 ([5185e83](https://github.com/xihan123/QDReadHook/commit/5185e83c54be94265ddedd01c8e0957e3c465c86))
* 更新依赖 ([891350f](https://github.com/xihan123/QDReadHook/commit/891350faaec942b2248538d65556409d24bf1105))
* 更新依赖 ([8f4205f](https://github.com/xihan123/QDReadHook/commit/8f4205f12fbb6761b19a3644c4489f77492d74f3))
* 更新依赖 ([0756c65](https://github.com/xihan123/QDReadHook/commit/0756c65081f60799dd19cd7f0c25bfeccd596073))
* 更新依赖 ([1b760fd](https://github.com/xihan123/QDReadHook/commit/1b760fde081bc213d01a088b18704131de9ed66e))
* 更新依赖 ([12a78fe](https://github.com/xihan123/QDReadHook/commit/12a78fe79f571d0b4c8bec788f1e4f280b1bcb88))
* 更新依赖 ([d5a2920](https://github.com/xihan123/QDReadHook/commit/d5a2920f1ec9e1f3355ab773d8181652fc5ae785))
* 更新依赖 ([f9cf0d1](https://github.com/xihan123/QDReadHook/commit/f9cf0d199e9edb93797af17a0920e95bafd0c313))
* 更新依赖 ([aa0fae1](https://github.com/xihan123/QDReadHook/commit/aa0fae1b77798c31e59eab0cbf2058d889488336))
* 更新依赖 ([ad90e71](https://github.com/xihan123/QDReadHook/commit/ad90e71a91383a9f812c321d6d55020cafe8dac0))
* 更新依赖 ([fea1055](https://github.com/xihan123/QDReadHook/commit/fea1055f800b1845bd1d75e29384adee9252a58c))
* 更新依赖 ([fa21690](https://github.com/xihan123/QDReadHook/commit/fa21690e8354fd38c976f0929f6f22a7085ce772))
* 更新依赖 ([42b80e8](https://github.com/xihan123/QDReadHook/commit/42b80e8f9b32e2dd9891ae098ff01e4fe1268080))
* 更新依赖库 ([c05dfa7](https://github.com/xihan123/QDReadHook/commit/c05dfa7446b0d2752218e5421c0fd49eabcb74ed))
* 更新依赖库 ([587ef02](https://github.com/xihan123/QDReadHook/commit/587ef02f9722b3d07aeba0f8d648e04bf46226fc))
* 更新依赖库 ([a8dc817](https://github.com/xihan123/QDReadHook/commit/a8dc817c207a00b3c70631d3e028c5592b6c98da))
* 更新依赖库 ([dfa133c](https://github.com/xihan123/QDReadHook/commit/dfa133c267b1d6161e1bfd8a2210dd8b76531981))
* 更新依赖库 ([5ab86c5](https://github.com/xihan123/QDReadHook/commit/5ab86c584b01ad2fb1001761d5ecb651a34d2f8e))
* 更新依赖库 ([21d64cd](https://github.com/xihan123/QDReadHook/commit/21d64cdb95f2ec2233b2d60e8cfefea58b8203ee))
* 更新依赖库 ([0bc7b1e](https://github.com/xihan123/QDReadHook/commit/0bc7b1e36ce835206cbd6036781256317d6d02a5))
* 更新依赖库 ([8e395bc](https://github.com/xihan123/QDReadHook/commit/8e395bc0a6bf830e85245779028d6d5979a97259))
* 更新依赖库 ([a51d4ab](https://github.com/xihan123/QDReadHook/commit/a51d4ab136d1142ff9eecc224b80ac887b4f80b1))
* 更新依赖库 ([55ab25f](https://github.com/xihan123/QDReadHook/commit/55ab25f78da20f493f7ff26ac22c5aa544d8f73e))
* 更新依赖库 ([0d86cd2](https://github.com/xihan123/QDReadHook/commit/0d86cd2fdf2c4b1f59ef32c3313d004fe95292cd))
* 更新免责声明和预览图以及其他 ([ceaf241](https://github.com/xihan123/QDReadHook/commit/ceaf2415ff9e3fced7008178e5cb7a14b45e6d56))
* 更新图片 ([c45e0f6](https://github.com/xihan123/QDReadHook/commit/c45e0f6291745ed32286d709eb5c51674dbcfe56))
* 更新图片 ([1259d28](https://github.com/xihan123/QDReadHook/commit/1259d2807ecfe7516f6ace20116550a6a8bab324))
* 更新资源 ([06a8e77](https://github.com/xihan123/QDReadHook/commit/06a8e77a988f227c3c24b1ba566bf8cb4957280a))
* 更新预览图 ([e9e4b8f](https://github.com/xihan123/QDReadHook/commit/e9e4b8fac213e289c39b2c0327f3168f9668fec2))
* 更新预览图 ([7733c5c](https://github.com/xihan123/QDReadHook/commit/7733c5cffc684e2beae591a20613845e4c6dfa96))
* 清理代码 ([c8afa81](https://github.com/xihan123/QDReadHook/commit/c8afa81ad75a49a4e1317c80f4da3aa16f044af8))
* 移除无用图片 ([2b57494](https://github.com/xihan123/QDReadHook/commit/2b5749411dcbc14f3a26fdcbdf30721a92ef85eb))

## [3.0.1](https://github.com/xihan123/QDReadHook/compare/v3.0.0...v3.0.1) (2023-10-16)


### Reverts

* **隐藏控件:** 恢复 `隐藏书架顶部标题` 功能 ([8d878c5](https://github.com/xihan123/QDReadHook/commit/8d878c5a1b092ec5a56da23f277b1c01976e4c04))


### Features

* 主设置:`启动时检查权限` 为可选，并调整隐藏控件的选项顺序 ([7bc4f41](https://github.com/xihan123/QDReadHook/commit/7bc4f413551fb993f20e3d20b57bedf7614b4ef4))


### Bug Fixes

* 修复 `搜索配置` 选项可能为空白 ([ca1e851](https://github.com/xihan123/QDReadHook/commit/ca1e851239e7ce96748669a0daeea811cad5708c))
* 修复 `隐藏每日导读` 选项失效 ([714c2b9](https://github.com/xihan123/QDReadHook/commit/714c2b92017717daa23230469330e6e59bdb9108))
* 修复深色模式 `免责声明` 字体颜色看不清 ([a05dadb](https://github.com/xihan123/QDReadHook/commit/a05dadb37e7297c2a3d086ee3ed4ec7f27a7aa3d))


### Miscellaneous

* **master:** release 3.0.0 ([ef9cb71](https://github.com/xihan123/QDReadHook/commit/ef9cb71b10c30c1ba8a55c2e3e0e785ace860c5a))
* 更新依赖 ([a185651](https://github.com/xihan123/QDReadHook/commit/a185651f5e32facc7e939182dc350efcc2e4ed0a))
* 更新依赖 ([17d6b69](https://github.com/xihan123/QDReadHook/commit/17d6b69e859d46d6c0d504ad05fce26da0d01715))

## [3.0.0](https://github.com/xihan123/QDReadHook/compare/v3.0.0...v3.0.0) (2023-10-14)


### Miscellaneous

* 更新依赖 ([17d6b69](https://github.com/xihan123/QDReadHook/commit/17d6b69e859d46d6c0d504ad05fce26da0d01715))

## [3.0.0](https://github.com/xihan123/QDReadHook/compare/v2.3.8...v3.0.0) (2023-10-14)


### ⚠ BREAKING CHANGES

* 移除替换规则相关
* 迁移新API,后续版本理论上自动适配,仅支持起点 `7.9.306-1030` 及以上版本
* 提高最低支持Xposed版本为 90

### Features

* **广告配置:** 合并闪屏广告、增强GDT广告 ([eb82d4a](https://github.com/xihan123/QDReadHook/commit/eb82d4a5b94604ec8063aadcca44544bed20665f))
* **拦截设置:** 合并青少年模式弹框、检查更新 ([23970d9](https://github.com/xihan123/QDReadHook/commit/23970d91733db920e7f7869bf0c562846f5824a8))
* 提高最低支持Xposed版本为 90 ([2b93f7c](https://github.com/xihan123/QDReadHook/commit/2b93f7c2c419becda8dd954a244e6c2693a7e4cc))
* 新增启动强制检查存储权限、`1050` + 启用旧版每日导读、移除新版不支持的功能 ([48e2a6d](https://github.com/xihan123/QDReadHook/commit/48e2a6ddee76c9dda7fccc0480e64722ac3a8e0c))
* 移除替换规则相关 ([806f8aa](https://github.com/xihan123/QDReadHook/commit/806f8aa46295b268c1c9a1705c7e5b03f358b6de))
* **自动化设置:** `1030` + 合并自动签到、自动领积分、自动点击章末红包 ([ecc7217](https://github.com/xihan123/QDReadHook/commit/ecc7217fb96384b4afaa1318cc5faadcae6fa10c))
* 迁移新API,后续版本理论上自动适配,仅支持起点 `7.9.306-1030` 及以上版本 ([4b6049d](https://github.com/xihan123/QDReadHook/commit/4b6049df6a9a31a6159df744f860a27a2be5422e))


### CI

* build.yml ([0ab6f6d](https://github.com/xihan123/QDReadHook/commit/0ab6f6d4c2d9f0656748d9a5bbad2d5e5b0bcd95))
* release.yml ([19b4e37](https://github.com/xihan123/QDReadHook/commit/19b4e372185d5dc71e812f216efeb3a083c036f2))


### Miscellaneous

* 更新依赖 ([99575e0](https://github.com/xihan123/QDReadHook/commit/99575e0d2a4452c0457c3608291c098fc6ad9471))
* 更新资源 ([06a8e77](https://github.com/xihan123/QDReadHook/commit/06a8e77a988f227c3c24b1ba566bf8cb4957280a))

## [2.3.8](https://github.com/xihan123/QDReadHook/compare/v2.3.7...v2.3.8) (2023-09-22)


### Features

* 适配 `1030` 版本 ([42bbd91](https://github.com/xihan123/QDReadHook/commit/42bbd91b4b31b5e7629ae0920410c7e3e90fbe8a))


### CI

* release.yml ([e71140d](https://github.com/xihan123/QDReadHook/commit/e71140d27c16673a8cb69c68567d8a20d6a5302b))


### Miscellaneous

* 更新依赖库 ([c05dfa7](https://github.com/xihan123/QDReadHook/commit/c05dfa7446b0d2752218e5421c0fd49eabcb74ed))

## [2.3.7](https://github.com/xihan123/QDReadHook/compare/v2.3.6...v2.3.7) (2023-09-19)


### Features

* 优化自动领取功能 ([d4be397](https://github.com/xihan123/QDReadHook/commit/d4be397ff2c7d1aececff392464b1315b63c8860))
* 适配 `1020` 版本 ([8c42f6d](https://github.com/xihan123/QDReadHook/commit/8c42f6d4f7f436f0ee6c47e66bed6e6221c51142))


### Feature Improvements

* 整理、格式化、排序代码 ([9a559e2](https://github.com/xihan123/QDReadHook/commit/9a559e29f75eac5851be3c20df158c9cd334eafe))


### CI

* release.yml ([9850930](https://github.com/xihan123/QDReadHook/commit/9850930874910990a0555359493aa4beb39fb92a))


### Miscellaneous

* 更新依赖库 ([587ef02](https://github.com/xihan123/QDReadHook/commit/587ef02f9722b3d07aeba0f8d648e04bf46226fc))

## [2.3.6](https://github.com/xihan123/QDReadHook/compare/v2.3.5...v2.3.6) (2023-09-09)


### Features

* **隐藏控件设置:** `1005` + 版本新增 `主页-隐藏控件列表` -&gt; `书架顶部标题` ([97c67f2](https://github.com/xihan123/QDReadHook/commit/97c67f2d12ae9cbd5bed5cb39f591091781165b8))
* **隐藏控件设置:** `1005` + 版本新增 `阅读页-隐藏控件` ([607dd59](https://github.com/xihan123/QDReadHook/commit/607dd59d14272c5374ac80894758683b7f2b88ab))


### CI

* release.yml ([cdaa591](https://github.com/xihan123/QDReadHook/commit/cdaa5910c10f0621329e0061f00d53180df2aa50))


### Miscellaneous

* 更新依赖库 ([a8dc817](https://github.com/xihan123/QDReadHook/commit/a8dc817c207a00b3c70631d3e028c5592b6c98da))

## [2.3.5](https://github.com/xihan123/QDReadHook/compare/v2.3.4...v2.3.5) (2023-09-07)


### ⚠ BREAKING CHANGES

* 移除 `屏蔽设置列表` -> `搜索相关`, `1005` + 版本 新增 `隐藏控件设置` -> `搜索配置列表`

### Features

* 移除 `屏蔽设置列表` -&gt; `搜索相关`, `1005` + 版本 新增 `隐藏控件设置` -> `搜索配置列表` ([38309d8](https://github.com/xihan123/QDReadHook/commit/38309d89b1b0da4cec5c721f6dd485ccd6d6e60a))
* 适配 `1005` 版本 ([bc0136c](https://github.com/xihan123/QDReadHook/commit/bc0136c244856c5b2e811ba4888c976bc65f16df))


### CI

* release.yml ([bbe3722](https://github.com/xihan123/QDReadHook/commit/bbe37222b3449bf793c60dbac216e0e3b711491d))


### Miscellaneous

* 更新依赖库 ([dfa133c](https://github.com/xihan123/QDReadHook/commit/dfa133c267b1d6161e1bfd8a2210dd8b76531981))

## [2.3.4](https://github.com/xihan123/QDReadHook/compare/v2.3.3...v2.3.4) (2023-09-06)


### Features

* **主设置:** `994` + 新增 `测试页面` 以及更新免责声明 ([3d049e6](https://github.com/xihan123/QDReadHook/commit/3d049e6c1bd2a675e00af2cb649dfa81052364a0))


### Bug Fixes

* [Bug] 点赞按钮异常 [[#123](https://github.com/xihan123/QDReadHook/issues/123)](https://github.com/xihan123/QDReadHook/issues/123) ([df41fc0](https://github.com/xihan123/QDReadHook/commit/df41fc094e36095f7768119571d6e090e7e5a8ff))
* `自动跳过闪屏页` 按钮未对齐 ([1840c82](https://github.com/xihan123/QDReadHook/commit/1840c82aa61bfe382c047ec8f9b5189df681310c))


### CI

* release.yml ([963b361](https://github.com/xihan123/QDReadHook/commit/963b361cc9e00b5a93de0e0853a40b9a390c579d))


### Miscellaneous

* 反射获取父类参数使用广度优先搜索算法 ([f0dca07](https://github.com/xihan123/QDReadHook/commit/f0dca0700bbec0a5bce78b28ee522100598658c9))

## [2.3.3](https://github.com/xihan123/QDReadHook/compare/v2.3.2...v2.3.3) (2023-09-04)


### ⚠ BREAKING CHANGES

* 移除 `自定义闪屏页`,`关闭闪屏` 合并至`拦截设置列表` -> `闪屏广告页面`

### Features

* **主设置:** `994` + 新增 `自动跳过闪屏页`,启用后无闪屏页直接进入主页面 ([c7a2069](https://github.com/xihan123/QDReadHook/commit/c7a2069e574a1c11ce27c3f49de74311398fa60d))
* **拦截设置:** `994` + 新增 `拦截首次安装分析` ([9dc5148](https://github.com/xihan123/QDReadHook/commit/9dc5148e84ff19df2c4c20fcf57b1a8a58c1d0a9))
* 移除 `自定义闪屏页`,`关闭闪屏` 合并至`拦截设置列表` -&gt; `闪屏广告页面` ([520ae38](https://github.com/xihan123/QDReadHook/commit/520ae38ec1d3af80e958a670aaff32d139efc276))


### Bug Fixes

* 内置后不显示模块设置入口 ([df19d76](https://github.com/xihan123/QDReadHook/commit/df19d761c3295cf4638f65f7dcdb5c045403940f))


### Feature Improvements

* 整理、格式化、排序代码 ([e0ff176](https://github.com/xihan123/QDReadHook/commit/e0ff17693a584cde5a1229c9c60dca5ba2a38063))


### CI

* release.yml ([c1f6366](https://github.com/xihan123/QDReadHook/commit/c1f636642c338380aee3dcd0da2d49c36c8f525a))


### Miscellaneous

* 更新依赖库 ([5ab86c5](https://github.com/xihan123/QDReadHook/commit/5ab86c584b01ad2fb1001761d5ecb651a34d2f8e))

## [2.3.2](https://github.com/xihan123/QDReadHook/compare/v2.3.1...v2.3.2) (2023-09-02)


### Features

* **拦截设置:** `980` + 新增拦截 `自动跳转精选` ([3429f08](https://github.com/xihan123/QDReadHook/commit/3429f08178441f11d8e35c3ee63afb285ab4625a))
* 适配 `994` 版本 ([3f0a604](https://github.com/xihan123/QDReadHook/commit/3f0a604610488c3006b44b818531711a190f39f7))


### Feature Improvements

* 整理、格式化、排序代码 ([20768d7](https://github.com/xihan123/QDReadHook/commit/20768d76df69c026861c64ca9dcfa2e3d75926a8))
* 重构 `屏蔽选项列表` ([9e3ec99](https://github.com/xihan123/QDReadHook/commit/9e3ec996b48d1bdc3700ee3df743c7dadd77a9fd))
* 重构 `广告设置列表` ([c391b93](https://github.com/xihan123/QDReadHook/commit/c391b93ceb8fb6ec471bb6c44803652c73a13144))
* 重构 `底部导航栏选项列表` ([0d2727f](https://github.com/xihan123/QDReadHook/commit/0d2727ff21806074f9aa9d226b699e518a26e481))


### CI

* release.yml ([da43342](https://github.com/xihan123/QDReadHook/commit/da43342ae9beb03e9b769e2889eff853fb2081b7))

## [2.3.1](https://github.com/xihan123/QDReadHook/compare/v2.3.0...v2.3.1) (2023-08-29)


### Features

* **拦截设置:** 新增一处拦截选项(非`980`+版本慎开) ([ed663b6](https://github.com/xihan123/QDReadHook/commit/ed663b6601ede7d0fc664218f7020a9a286b7190))
* 模块管理页面支持 `Android12` + 动态配色 ([408f92a](https://github.com/xihan123/QDReadHook/commit/408f92a0237a2d718771a7c1f16fd48979a0c8cc))


### Miscellaneous

* 新增或移除选项时根据标题重新排序 ([f795dc1](https://github.com/xihan123/QDReadHook/commit/f795dc145fc346feaea39e4747ea03b07bbac806))
* 更新免责声明和预览图以及其他 ([ceaf241](https://github.com/xihan123/QDReadHook/commit/ceaf2415ff9e3fced7008178e5cb7a14b45e6d56))

## [2.3.0](https://github.com/xihan123/QDReadHook/compare/v2.2.9...v2.3.0) (2023-08-24)


### Features

* 适配 `980` 版本 ([b68a2fc](https://github.com/xihan123/QDReadHook/commit/b68a2fc277b4c1986a49c2294bd472e8702210ca))


### Miscellaneous

* 整理代码 ([81852fd](https://github.com/xihan123/QDReadHook/commit/81852fdda8208a6eec61778f623eee6f12b61464))

## [2.2.9](https://github.com/xihan123/QDReadHook/compare/v2.2.8...v2.2.9) (2023-08-20)


### Features

* **主设置:** `970` 版本 `发帖显示图片直链` ([e80c133](https://github.com/xihan123/QDReadHook/commit/e80c13394bd73aead61cf3c6c1d90f35d4ae6f7d))
* **拦截设置:** `970` 版本 `发帖图片水印` ([689a350](https://github.com/xihan123/QDReadHook/commit/689a350ba1e365cd024aeaa9e6a8bf1af0ca6ff3))


### Miscellaneous

* 整理代码 ([17255f4](https://github.com/xihan123/QDReadHook/commit/17255f420a814033581a38a4d3cc0ccb94904f97))

## [2.2.8](https://github.com/xihan123/QDReadHook/compare/v2.2.7...v2.2.8) (2023-08-18)


### Features

* **拦截设置:** `970` 版本实验性功能`阅读页水印` ([72b316a](https://github.com/xihan123/QDReadHook/commit/72b316a65f668f336465f8c988f6b0abddbb3d01))
* **阅读页设置:** `890`~`970` 版本 `配音导出`支持网络及本地地址，`884`版本移除此功能 ([600d366](https://github.com/xihan123/QDReadHook/commit/600d36630884375d6816f0e1ca49f4fda31aec24))
* **隐藏控件设置:** `827`~`970` 版本 `隐藏底部导航栏`优化 ([9f2cfa5](https://github.com/xihan123/QDReadHook/commit/9f2cfa522ad31268845e4c704c7946a63db44353))


### Miscellaneous

* 整理代码 ([512920e](https://github.com/xihan123/QDReadHook/commit/512920ef4780a49d316ec03540d8a5ea41127698))
* 更新依赖 ([4f41897](https://github.com/xihan123/QDReadHook/commit/4f4189705e98c193e2190968b4d9fdc156f697b8))

## [2.2.7](https://github.com/xihan123/QDReadHook/compare/v2.2.6...v2.2.7) (2023-08-16)


### Features

* `970` 版本 `自动领取阅读积分`100~500ms随机延迟领取 ([5bcecb6](https://github.com/xihan123/QDReadHook/commit/5bcecb60ac7328ff1ee9c722376d461b7d6e771e))

## [2.2.6](https://github.com/xihan123/QDReadHook/compare/v2.2.4...v2.2.6) (2023-08-15)


### Features

* `970` 版本`阅读积分`页面自动领取奖励 ([c89412a](https://github.com/xihan123/QDReadHook/commit/c89412a7ac87f18c88d409ab1e25a4f573526d1f))


### Bug Fixes

* `970` 版本 `精选-标题`隐藏失效 ([44b12c3](https://github.com/xihan123/QDReadHook/commit/44b12c3c94c86e11d7677cf88705879673e70183))

## [2.2.5](https://github.com/xihan123/QDReadHook/compare/v2.2.4...v2.2.5) (2023-08-15)


### Features

* `970` 版本`阅读积分`页面自动领取奖励 ([c89412a](https://github.com/xihan123/QDReadHook/commit/c89412a7ac87f18c88d409ab1e25a4f573526d1f))

## [2.2.4](https://github.com/xihan123/QDReadHook/compare/v2.2.3...v2.2.4) (2023-08-12)


### Features

* `970` 版本阅读页章评以及楼中楼图片、评论复制 ([5099804](https://github.com/xihan123/QDReadHook/commit/50998047a728597fac5358f1378cf65ff9b1662a))

## [2.2.3](https://github.com/xihan123/QDReadHook/compare/v2.2.2...v2.2.3) (2023-08-12)


### Bug Fixes

* `970` 版本底部导航栏隐藏失效 ([50f8ccb](https://github.com/xihan123/QDReadHook/commit/50f8ccb76b18431a0ccb0779289ea0d8acf69c26))

## [2.2.2](https://github.com/xihan123/QDReadHook/compare/v2.2.0...v2.2.2) (2023-08-11)


### ⚠ BREAKING CHANGES

* 移除`QQ阅读免广告领取奖励`

### Features

* `阅读页-浮窗广告` 尝试性增强 ([ccf2927](https://github.com/xihan123/QDReadHook/commit/ccf29277b095a46ff2cc96cfc9fde9c0bf1786e6))
* 尝试性禁用检查更新弹框 ([a5b8f68](https://github.com/xihan123/QDReadHook/commit/a5b8f68a38020b6a7c4b4ce9306120c24b24679a))
* 移除`QQ阅读免广告领取奖励` ([26c9851](https://github.com/xihan123/QDReadHook/commit/26c9851dfee1c43950ae0b89f1447b800e951165))
* 适配 `958` 版本、修复配音导出 ([fbfe684](https://github.com/xihan123/QDReadHook/commit/fbfe684ddd85f2c738a00265ff5fec401e8cb0b1))
* 适配 `970` 版本 ([40c07ff](https://github.com/xihan123/QDReadHook/commit/40c07ff1db57948473b604a997141e109a51b14a))
* 适配 `970` 版本 ([f589c4a](https://github.com/xihan123/QDReadHook/commit/f589c4a41c238fe50f8fe437116fea5536c022f5))


### Bug Fixes

* `屏蔽-精选主页面`失效 ([1a77a0d](https://github.com/xihan123/QDReadHook/commit/1a77a0d45a37cbfbf55d40b98fd1113efcc6b001))


### CI

* build.yml ([e147043](https://github.com/xihan123/QDReadHook/commit/e1470431dbd66260329dfbf8c22d79132ed72dd2))
* build.yml ([6b420eb](https://github.com/xihan123/QDReadHook/commit/6b420ebe8849d6ed1ac305b08f00ecc4f78c5364))
* release.yml ([a687038](https://github.com/xihan123/QDReadHook/commit/a6870384b6a7d99d74688003d5d775834236fabb))
* release.yml ([afd689b](https://github.com/xihan123/QDReadHook/commit/afd689b74f90f483f3db0ada5f5685a3cf4cd379))
* release.yml ([8a1dca0](https://github.com/xihan123/QDReadHook/commit/8a1dca0b826c954ce873d45ea91fa5ee687cc18d))


### Miscellaneous

* **master:** release 2.1.8 ([0450cb6](https://github.com/xihan123/QDReadHook/commit/0450cb64d65539ca7af7343c0624192d8e1ee422))
* **master:** release 2.1.9 ([143ec94](https://github.com/xihan123/QDReadHook/commit/143ec944f35b2278d2106dcee448f609e2c3a26c))
* **master:** release 2.1.9 ([31dd984](https://github.com/xihan123/QDReadHook/commit/31dd984f56f71c8a4118b9186221433df69d2c19))
* 更新依赖 ([afd689b](https://github.com/xihan123/QDReadHook/commit/afd689b74f90f483f3db0ada5f5685a3cf4cd379))
* 更新依赖 ([52958c9](https://github.com/xihan123/QDReadHook/commit/52958c9cc065567d5171d6388a4fe91204032f42))
* 更新依赖 ([5185e83](https://github.com/xihan123/QDReadHook/commit/5185e83c54be94265ddedd01c8e0957e3c465c86))

## [2.2.1](https://github.com/xihan123/QDReadHook/compare/v2.2.0...v2.2.1) (2023-08-10)


### Features

* 适配 `970` 版本 ([f589c4a](https://github.com/xihan123/QDReadHook/commit/f589c4a41c238fe50f8fe437116fea5536c022f5))


### CI

* build.yml ([6b420eb](https://github.com/xihan123/QDReadHook/commit/6b420ebe8849d6ed1ac305b08f00ecc4f78c5364))
* release.yml ([8a1dca0](https://github.com/xihan123/QDReadHook/commit/8a1dca0b826c954ce873d45ea91fa5ee687cc18d))

## [2.2.0](https://github.com/xihan123/QDReadHook/compare/v2.1.9...v2.2.0) (2023-08-10)


### Features

* `阅读页-浮窗广告` 尝试性增强 ([38c6e80](https://github.com/xihan123/QDReadHook/commit/38c6e802f4edcfa1d3bc1da80c6d1520768c696a))


### Miscellaneous

* 更新依赖 ([891350f](https://github.com/xihan123/QDReadHook/commit/891350faaec942b2248538d65556409d24bf1105))

## [2.1.9](https://github.com/xihan123/QDReadHook/compare/v2.1.9...v2.1.9) (2023-07-31)


### Miscellaneous

* **master:** release 2.1.9 ([8cd40da](https://github.com/xihan123/QDReadHook/commit/8cd40da3afd48eb0d8bb8f0c63bcccec9deaa3f0))

## [2.1.9](https://github.com/xihan123/QDReadHook/compare/v2.1.8...v2.1.9) (2023-07-31)


### ⚠ BREAKING CHANGES

* 移除`QQ阅读免广告领取奖励`

### Features

* 移除`QQ阅读免广告领取奖励` ([c1b8962](https://github.com/xihan123/QDReadHook/commit/c1b8962d60fbf283651c6fca9d520ebde77aefbb))
* 适配 `958` 版本、修复配音导出 ([33dd884](https://github.com/xihan123/QDReadHook/commit/33dd884562066f60f030153068df3e10a7005526))


### Bug Fixes

* `屏蔽-精选主页面`失效 ([4d6cc21](https://github.com/xihan123/QDReadHook/commit/4d6cc212a4155b40a08bb81344f17c4b2e614e55))


### Miscellaneous

* **master:** release 2.1.8 ([2deef1c](https://github.com/xihan123/QDReadHook/commit/2deef1cab6d30eeb734b2b09fc2b860f69d7a322))
* **master:** release 2.1.8 ([1fccf07](https://github.com/xihan123/QDReadHook/commit/1fccf072ce675a1b5be2911fabbd048be10419b8))
* **master:** release 2.1.8 ([7d4dcaa](https://github.com/xihan123/QDReadHook/commit/7d4dcaaa375c07cc4a99a3e3ab1ea97ad5e774bc))
* 整理代码 ([30b7d8c](https://github.com/xihan123/QDReadHook/commit/30b7d8cf599e417b49367c012144bee15e5a0012))
* 更新依赖 ([0756c65](https://github.com/xihan123/QDReadHook/commit/0756c65081f60799dd19cd7f0c25bfeccd596073))

## [2.1.8](https://github.com/xihan123/QDReadHook/compare/v2.1.8...v2.1.8) (2023-07-16)


### Miscellaneous

* **master:** release 2.1.8 ([1fccf07](https://github.com/xihan123/QDReadHook/commit/1fccf072ce675a1b5be2911fabbd048be10419b8))
* **master:** release 2.1.8 ([7d4dcaa](https://github.com/xihan123/QDReadHook/commit/7d4dcaaa375c07cc4a99a3e3ab1ea97ad5e774bc))
* 整理代码 ([30b7d8c](https://github.com/xihan123/QDReadHook/commit/30b7d8cf599e417b49367c012144bee15e5a0012))

## [2.1.8](https://github.com/xihan123/QDReadHook/compare/v2.1.8...v2.1.8) (2023-07-16)


### Miscellaneous

* **master:** release 2.1.8 ([7d4dcaa](https://github.com/xihan123/QDReadHook/commit/7d4dcaaa375c07cc4a99a3e3ab1ea97ad5e774bc))

## [2.1.8](https://github.com/xihan123/QDReadHook/compare/v2.1.7...v2.1.8) (2023-07-16)


### ⚠ BREAKING CHANGES

* 移除`配音导入`
* 仅支持 `884`+ 版本拦截启动图页面的广告

### Features

* `872`+ 版本书籍详情-快速屏蔽弹框 ([9ada69e](https://github.com/xihan123/QDReadHook/commit/9ada69e7a3a19f9a66a20f45832d32946ef4a60a))
* `872`+ 版本自定义粉丝值 ([958ca79](https://github.com/xihan123/QDReadHook/commit/958ca793bb69ea95b91326883f7d803b641038d2))
* `878` 版本隐藏主页面-顶部战力提示 ([aba0c76](https://github.com/xihan123/QDReadHook/commit/aba0c76850657f8792e3332712b437148a409cc8))
* `884` 版本新增章评相关 ([a64057f](https://github.com/xihan123/QDReadHook/commit/a64057f55c76379435724ccb403eaf776a912008))
* `884` 版本章评导入导出配音以及一键导出表情包 ([ab70a69](https://github.com/xihan123/QDReadHook/commit/ab70a69410790291c1874a3d75bcf422c2b5df06))
* `896`、`900` 版本新增阅读页最后一页相关 ([e56e33f](https://github.com/xihan123/QDReadHook/commit/e56e33f9fc305f4370c21b3e02527be4bea0b2dd))
* `944` 版本新增`自定义本地启动图`,以及QQ阅读-8.0.0.888_310 `免广告领取奖励` ([90b6cb1](https://github.com/xihan123/QDReadHook/commit/90b6cb1f27e7da6c933b40a53a040bf107e3b37e))
* **Intercept:** `872` 版本自选拦截初始化 ([fc74523](https://github.com/xihan123/QDReadHook/commit/fc74523ed05cfabe123c18efb24e4b50c1550298))
* **main:** `872` 版本新旧精选布局 ([922da5e](https://github.com/xihan123/QDReadHook/commit/922da5e69179f455beafe9370510cb224ba7afd7))
* **ViewHide:** `872` 版本自选精选-隐藏控件 ([d6554e9](https://github.com/xihan123/QDReadHook/commit/d6554e985c0673c56fd5379f185a1bffa1cf02e1))
* **主设置:** `896`+ 试用模式弹框、真·免广告领取奖励 ([782d270](https://github.com/xihan123/QDReadHook/commit/782d270831f63d97f60c9e02fa5e724efbc4135c))
* **主设置:** `906` +版本新增隐藏福利列表 ([83ab08a](https://github.com/xihan123/QDReadHook/commit/83ab08a6cbcc47bd5340e7168d43ad11e3ac5ad3))
* 仅支持 `884`+ 版本拦截启动图页面的广告 ([41de6e2](https://github.com/xihan123/QDReadHook/commit/41de6e21d123fa9b6e2a266450f4feaf18a5fecf))
* 最低支持版本改为Android 8 ([9a02bd8](https://github.com/xihan123/QDReadHook/commit/9a02bd80720120440b7077927f7c76582365a2c2))
* **广告:** 禁用初始化GDT SDK ([4335d0d](https://github.com/xihan123/QDReadHook/commit/4335d0d20f39bc96dca4a604cd0334cb9bebc72b))
* **广告设置:** `896`+ 阅读页-最新页面弹框广告 ([327f2de](https://github.com/xihan123/QDReadHook/commit/327f2de57c8bbaf175cbe8dea76828dfbd789be3))
* **拦截设置:** `896`+ 异步主GDT广告任务、异步主游戏广告SDK任务、异步主游戏下载任务、异步子屏幕截图任务、部分环境检测 ([3983fc4](https://github.com/xihan123/QDReadHook/commit/3983fc401ea91303aa9eb1a287ebeb49e9500b22))
* **替换规则设置:** `896`+ 自定义设备信息 ([2853ead](https://github.com/xihan123/QDReadHook/commit/2853eadb1945b4d085e536240572f728c24db96b))
* 移除`配音导入` ([5052b4d](https://github.com/xihan123/QDReadHook/commit/5052b4d0c87f7151b1f61580dd6754b113bf1ca1))
* 适配 `878` 版本 ([40efb28](https://github.com/xihan123/QDReadHook/commit/40efb282b4dfbf5d80cbaa08524f5bea1b7d3dc5))
* 适配 `884` 版本 ([4742bd6](https://github.com/xihan123/QDReadHook/commit/4742bd66bf6c249fe5848ce44fe4479d87e049b7))
* 适配 `890` 版本 ([d5620a8](https://github.com/xihan123/QDReadHook/commit/d5620a818e4696323cfdb7051ed681a3dfe32032))
* 适配 `896`、`900` 版本 ([4666116](https://github.com/xihan123/QDReadHook/commit/4666116fb78120209a5485e9771147e631c951c5))
* 适配 `906` 版本 ([64d51e9](https://github.com/xihan123/QDReadHook/commit/64d51e937c29a4b9a72dc216705500ba5387d0ea))
* 适配 `916` 版本 ([162c6b6](https://github.com/xihan123/QDReadHook/commit/162c6b624d7577e6b61d7cc0001937c8fd227897))
* 适配 `924` 版本 ([f0a5165](https://github.com/xihan123/QDReadHook/commit/f0a51650e7aa27f2e698842fee2cb692411f5749))
* 适配 `932` 版本 ([3e709bf](https://github.com/xihan123/QDReadHook/commit/3e709bf936bebf4c2d9da2d6d57fd298cd623076))
* 适配 `938` 版本 ([5a4af1f](https://github.com/xihan123/QDReadHook/commit/5a4af1feca687a22c269aa36bc84339e7f3d578b))
* 适配 `944` 版本 ([7c5ccd6](https://github.com/xihan123/QDReadHook/commit/7c5ccd6fd11be16f68622e41f022744b2e1db2c5))
* 适配 `950` 版本、新增一处屏蔽选项 ([839320e](https://github.com/xihan123/QDReadHook/commit/839320ef402d5a816ed6880202795237efa40bd7))


### Bug Fixes

* `890` 版本新"我"布局隐藏控件失效 ([3a31d74](https://github.com/xihan123/QDReadHook/commit/3a31d74aebd1ee03869803dd06f9ba8bc71d768f))
* build.gradle.kts ([f5a9e8d](https://github.com/xihan123/QDReadHook/commit/f5a9e8d6ee2d7a84dbd154032d85e79e42dbc91d))
* **deps:** update activity to v1.8.0-alpha05 ([968924f](https://github.com/xihan123/QDReadHook/commit/968924f74dc054e24adb6525dbfb918c96e59155))
* **deps:** update dependency androidx.activity:activity-compose to v1.8.0-alpha06 ([bfaa442](https://github.com/xihan123/QDReadHook/commit/bfaa442fed4c884c617f0a287a830cf854b4d7ce))
* **deps:** update dependency com.alibaba.fastjson2:fastjson2-kotlin to v2.0.34 ([116cac9](https://github.com/xihan123/QDReadHook/commit/116cac9163d08c49c66f41b61a476e831ea9258f))
* **deps:** update dependency com.google.android.material:material to v1.10.0-alpha04 ([1a58cea](https://github.com/xihan123/QDReadHook/commit/1a58cea1ab87f8a665eeafe2976028b152a9b788))
* **deps:** update dependency org.htmlunit:htmlunit3-android to v3.3.0 ([fc741e3](https://github.com/xihan123/QDReadHook/commit/fc741e301a9aacf07cdd1ef18e4d93211b9929d5))
* UpsideDownCake ([af89061](https://github.com/xihan123/QDReadHook/commit/af890610baa321503e80675895452de01907147d))
* 免责声明逻辑 ([7165dff](https://github.com/xihan123/QDReadHook/commit/7165dff4814f7f9abe00cb6d53475ec57cf9f884))
* 可能出现的屏蔽失效 ([53fecd9](https://github.com/xihan123/QDReadHook/commit/53fecd9427b7882dbbdafb897efaf077a376aad2))
* 配音导入对话框 ([e5a1140](https://github.com/xihan123/QDReadHook/commit/e5a11401bd32ba9886338f373518bf85f75b8889))


### CI

* automerge-action.yml ([3b87691](https://github.com/xihan123/QDReadHook/commit/3b876913da521b336258ee533c57ff7eb51b357b))
* automerge-action.yml ([6778db0](https://github.com/xihan123/QDReadHook/commit/6778db0e20b83e4bf40ed329b470bb66f217975d))
* build.yml ([65b18de](https://github.com/xihan123/QDReadHook/commit/65b18debdf373d95efcd60a8eb6d34e73fa85607))
* build.yml ([29a3f27](https://github.com/xihan123/QDReadHook/commit/29a3f27fd6fe27231326773313ae41e76562e43d))
* build.yml ([07152b6](https://github.com/xihan123/QDReadHook/commit/07152b61179cbd1835e638ec195e0a56bb622928))
* build.yml ([350d1ba](https://github.com/xihan123/QDReadHook/commit/350d1ba3b57df5376740932da43573bf50deafa1))
* build.yml ([25a639c](https://github.com/xihan123/QDReadHook/commit/25a639c59a35f3d8677ad3263365fe43c9194849))
* build.yml ([9b7ca28](https://github.com/xihan123/QDReadHook/commit/9b7ca283466645f82c444caed73ae55c8593c29a))
* build.yml ([a0e846c](https://github.com/xihan123/QDReadHook/commit/a0e846c5cdd5a09ab1d9ff0d64764ca5623058f9))
* gitlab-ci.yml ([a5d06d0](https://github.com/xihan123/QDReadHook/commit/a5d06d02204071d9ae5e75e73dd8c604adb363cd))
* gitlab-ci.yml ([543f318](https://github.com/xihan123/QDReadHook/commit/543f3186755492fb80aa6cf10b6141c156c3bbe5))
* release.yml ([c141444](https://github.com/xihan123/QDReadHook/commit/c14144486bff474d9e347360be32f8ed69a03c89))
* release.yml ([6dffa62](https://github.com/xihan123/QDReadHook/commit/6dffa6241f48dbaaa7f761c2630098be29fa067a))
* release.yml ([8f4a426](https://github.com/xihan123/QDReadHook/commit/8f4a4261d004e192ce60e71e30c091b59e9a24c0))
* release.yml ([4aa87bf](https://github.com/xihan123/QDReadHook/commit/4aa87bfbeb9896e28e26c08e416afdf4d30d2b1a))
* release.yml ([a8462df](https://github.com/xihan123/QDReadHook/commit/a8462df14cdca9efe0b9329d99d211874e9466ec))
* release.yml ([a55af53](https://github.com/xihan123/QDReadHook/commit/a55af531758385255ba8c55e7584d675e3bc63c3))
* release.yml ([ceb2f67](https://github.com/xihan123/QDReadHook/commit/ceb2f67ad25d885e0851864c0d14d08047923e61))
* release.yml ([258a623](https://github.com/xihan123/QDReadHook/commit/258a623aa247b457d7eb2756df93be653ffcd5a0))
* release.yml ([bccd8ef](https://github.com/xihan123/QDReadHook/commit/bccd8ef95aed602d33170b056074af87e85ebb42))
* release.yml ([c2b07d6](https://github.com/xihan123/QDReadHook/commit/c2b07d6690cba39abff8649af6242a55d506a270))
* release.yml ([bced994](https://github.com/xihan123/QDReadHook/commit/bced9941c10b94e6235c1c9a0009a6160ce8de9f))
* release.yml ([012ad09](https://github.com/xihan123/QDReadHook/commit/012ad09d9fb1e797e276ad597491caa785593690))
* release.yml ([fc50d23](https://github.com/xihan123/QDReadHook/commit/fc50d23882d494f1b97ef7e26e62d1fc509c705e))
* release.yml ([14833f2](https://github.com/xihan123/QDReadHook/commit/14833f29e1171eb67ce67467e2ae67aeb3173d4c))
* release.yml ([6abc522](https://github.com/xihan123/QDReadHook/commit/6abc5221e4d5d626a5f12bdb21fcc59186b9f8ce))
* stale.yml ([6a4808c](https://github.com/xihan123/QDReadHook/commit/6a4808cacac71b8ad5e65406b05f6ce3d7df8964))


### Miscellaneous

* **deps:** update dependency com.google.devtools.ksp to v1.9.0-rc-1.0.11 ([461ece5](https://github.com/xihan123/QDReadHook/commit/461ece5db162d903a17c36408e0ce788ac95fa0d))
* **deps:** update kotlin monorepo to v1.9.0-rc ([07b80e7](https://github.com/xihan123/QDReadHook/commit/07b80e759ec2095b4247e9372961046e9693a84d))
* Initial commit ([43517f5](https://github.com/xihan123/QDReadHook/commit/43517f5bffcb3a53c6970d514ee39da48d7a4d8f))
* **master:** release 2.0.4 ([b5d5c41](https://github.com/xihan123/QDReadHook/commit/b5d5c41a74e6c9f2f181220f06d9ee3668e7fafa))
* **master:** release 2.0.4 ([81327f9](https://github.com/xihan123/QDReadHook/commit/81327f91ae079a75a63ecfa4f5f5baafce1aea05))
* **master:** release 2.0.5 ([c7ae607](https://github.com/xihan123/QDReadHook/commit/c7ae6070df5619204387f087b8741b5dded8214d))
* **master:** release 2.0.6 ([a0cb22d](https://github.com/xihan123/QDReadHook/commit/a0cb22d3c7a6e10292dac21bd667e800fcc57523))
* **master:** release 2.0.6 ([cfa49e2](https://github.com/xihan123/QDReadHook/commit/cfa49e263651a4610d954a016cd643adbaeb6d11))
* **master:** release 2.0.7 ([6952b49](https://github.com/xihan123/QDReadHook/commit/6952b493813436868d70f0997129a62cc87d9f00))
* **master:** release 2.0.7 ([4c83ba0](https://github.com/xihan123/QDReadHook/commit/4c83ba0d1d2205a90ef05dd458373130df4e2c25))
* **master:** release 2.0.7 ([3f08b07](https://github.com/xihan123/QDReadHook/commit/3f08b079d2440825abeb4bfee97b8fdd3213964c))
* **master:** release 2.0.7 ([b35c104](https://github.com/xihan123/QDReadHook/commit/b35c10478159d77ee95332d80c14389cb5ae83eb))
* **master:** release 2.0.8 ([7d3311b](https://github.com/xihan123/QDReadHook/commit/7d3311b8e1021e200f364435f0f947aa429ffe03))
* **master:** release 2.0.8 ([b3baa6a](https://github.com/xihan123/QDReadHook/commit/b3baa6a90a7868c5d18469a11c7c80154ca06b66))
* **master:** release 2.0.8 ([0bc1030](https://github.com/xihan123/QDReadHook/commit/0bc103040fbeeb29cedd831ce77e5cda315164ce))
* **master:** release 2.0.9 ([360b229](https://github.com/xihan123/QDReadHook/commit/360b22919ac401c7d7b57724210c547078753fb2))
* **master:** release 2.0.9 ([98e245c](https://github.com/xihan123/QDReadHook/commit/98e245c5af1772535a5305c05edfaafc505f5c49))
* **master:** release 2.1.0 ([cdea359](https://github.com/xihan123/QDReadHook/commit/cdea3592d59aecd58585fc3a24233189daac6ca3))
* **master:** release 2.1.0 ([afd2f06](https://github.com/xihan123/QDReadHook/commit/afd2f064c115d32de186cd10262d3f27eeb27926))
* **master:** release 2.1.0 ([3c5035a](https://github.com/xihan123/QDReadHook/commit/3c5035ab81de59c029be633841135e68ebfbee4e))
* **master:** release 2.1.0 ([b58da5a](https://github.com/xihan123/QDReadHook/commit/b58da5aa1d783e2a9cf06a7b3864e345d1c486e5))
* **master:** release 2.1.0 ([e0ce41d](https://github.com/xihan123/QDReadHook/commit/e0ce41d0f3c8ab31281ee14672e687db42f4424c))
* **master:** release 2.1.1 ([5d427d6](https://github.com/xihan123/QDReadHook/commit/5d427d6dcd02f7e36fad715e9e6ffb9f388b91fd))
* **master:** release 2.1.1 ([333ae33](https://github.com/xihan123/QDReadHook/commit/333ae332657f07038cbb9067b5be56c527668533))
* **master:** release 2.1.2 ([2433660](https://github.com/xihan123/QDReadHook/commit/243366077d580c1eeefe4dd4a44f606e3e33aaa8))
* **master:** release 2.1.2 ([62adb53](https://github.com/xihan123/QDReadHook/commit/62adb53b26ec9a03e14027f4b5597b9b6775616c))
* **master:** release 2.1.2 ([bdd54bd](https://github.com/xihan123/QDReadHook/commit/bdd54bd29a72cd289c0fbc74d326b56a95405cbd))
* **master:** release 2.1.3 ([3487c69](https://github.com/xihan123/QDReadHook/commit/3487c69ecb7d97d030641656b5e8128c9e0774de))
* **master:** release 2.1.3 ([25e0055](https://github.com/xihan123/QDReadHook/commit/25e00559f09a1d16871d0311eac01d8f8467e10e))
* **master:** release 2.1.4 ([92832a9](https://github.com/xihan123/QDReadHook/commit/92832a9b4157a967b34b8e8dbb29e45a857d5f9f))
* **master:** release 2.1.4 ([ba2f338](https://github.com/xihan123/QDReadHook/commit/ba2f338876e8316383d6e87978a216c73edff48a))
* **master:** release 2.1.5 ([5b07e2c](https://github.com/xihan123/QDReadHook/commit/5b07e2cf5f6beeb81528524fb781909767c8bc2e))
* **master:** release 2.1.5 ([f02f36c](https://github.com/xihan123/QDReadHook/commit/f02f36c24462fb13c2a925e31b7809184736d155))
* **master:** release 2.1.6 ([4951cf5](https://github.com/xihan123/QDReadHook/commit/4951cf5ea0e1a85740c9ccaa9a2ce65ba14c9e33))
* **master:** release 2.1.6 ([5138196](https://github.com/xihan123/QDReadHook/commit/513819670481ddfc2ba9a9babd081f2a2b4fe30f))
* **master:** release 2.1.6 ([3f0b29f](https://github.com/xihan123/QDReadHook/commit/3f0b29fd8037767b88097052caa4858d36e60453))
* **master:** release 2.1.6 ([602f187](https://github.com/xihan123/QDReadHook/commit/602f1876af64cda345a28ddf0ed1f10b60b4b703))
* **master:** release 2.1.6 ([84047a6](https://github.com/xihan123/QDReadHook/commit/84047a6974931e6255a6c653fc51ce2fe6755326))
* **master:** release 2.1.6 ([8842c8f](https://github.com/xihan123/QDReadHook/commit/8842c8f95bb06101dbaed33b185598b483a8263f))
* **master:** release 2.1.6 ([dc502ff](https://github.com/xihan123/QDReadHook/commit/dc502ff9f0ceda5e307dd1d269e1ea182cee53cb))
* **master:** release 2.1.6 ([1559607](https://github.com/xihan123/QDReadHook/commit/1559607f925e8d0b6347d571d3434055d30cdae9))
* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))
* **master:** release 2.1.7 ([d339980](https://github.com/xihan123/QDReadHook/commit/d3399809de98884d00291ec58a347ec88e8c0904))
* **master:** release 2.1.7 ([71e65cb](https://github.com/xihan123/QDReadHook/commit/71e65cb1656dff259ed9e76981419c1229afb9b7))
* 其他 ([1898b9d](https://github.com/xihan123/QDReadHook/commit/1898b9d5983863c3ca8111b902a9def74ce99c9c))
* 其他 ([69042cf](https://github.com/xihan123/QDReadHook/commit/69042cfe9e76f87e9a658046f9a74803acdaa079))
* 整理代码 ([2ed7da0](https://github.com/xihan123/QDReadHook/commit/2ed7da04fe80c0f61993c50c4479ceb6aff253ec))
* 新增免责声明 ([b5b19d1](https://github.com/xihan123/QDReadHook/commit/b5b19d1e7fa1a46058ecddb75fd1e8d3345bd4a8))
* 更新依赖 ([1b760fd](https://github.com/xihan123/QDReadHook/commit/1b760fde081bc213d01a088b18704131de9ed66e))
* 更新依赖 ([12a78fe](https://github.com/xihan123/QDReadHook/commit/12a78fe79f571d0b4c8bec788f1e4f280b1bcb88))
* 更新依赖 ([d5a2920](https://github.com/xihan123/QDReadHook/commit/d5a2920f1ec9e1f3355ab773d8181652fc5ae785))
* 更新依赖 ([f9cf0d1](https://github.com/xihan123/QDReadHook/commit/f9cf0d199e9edb93797af17a0920e95bafd0c313))
* 更新依赖 ([aa0fae1](https://github.com/xihan123/QDReadHook/commit/aa0fae1b77798c31e59eab0cbf2058d889488336))
* 更新依赖 ([ad90e71](https://github.com/xihan123/QDReadHook/commit/ad90e71a91383a9f812c321d6d55020cafe8dac0))
* 更新依赖 ([fea1055](https://github.com/xihan123/QDReadHook/commit/fea1055f800b1845bd1d75e29384adee9252a58c))
* 更新依赖 ([fa21690](https://github.com/xihan123/QDReadHook/commit/fa21690e8354fd38c976f0929f6f22a7085ce772))
* 更新依赖 ([42b80e8](https://github.com/xihan123/QDReadHook/commit/42b80e8f9b32e2dd9891ae098ff01e4fe1268080))
* 更新依赖库 ([21d64cd](https://github.com/xihan123/QDReadHook/commit/21d64cdb95f2ec2233b2d60e8cfefea58b8203ee))
* 更新依赖库 ([0bc7b1e](https://github.com/xihan123/QDReadHook/commit/0bc7b1e36ce835206cbd6036781256317d6d02a5))
* 更新依赖库 ([8e395bc](https://github.com/xihan123/QDReadHook/commit/8e395bc0a6bf830e85245779028d6d5979a97259))
* 更新依赖库 ([a51d4ab](https://github.com/xihan123/QDReadHook/commit/a51d4ab136d1142ff9eecc224b80ac887b4f80b1))
* 更新依赖库 ([55ab25f](https://github.com/xihan123/QDReadHook/commit/55ab25f78da20f493f7ff26ac22c5aa544d8f73e))
* 更新依赖库 ([0d86cd2](https://github.com/xihan123/QDReadHook/commit/0d86cd2fdf2c4b1f59ef32c3313d004fe95292cd))
* 更新图片 ([c45e0f6](https://github.com/xihan123/QDReadHook/commit/c45e0f6291745ed32286d709eb5c51674dbcfe56))
* 更新图片 ([1259d28](https://github.com/xihan123/QDReadHook/commit/1259d2807ecfe7516f6ace20116550a6a8bab324))
* 更新预览图 ([e9e4b8f](https://github.com/xihan123/QDReadHook/commit/e9e4b8fac213e289c39b2c0327f3168f9668fec2))
* 更新预览图 ([7733c5c](https://github.com/xihan123/QDReadHook/commit/7733c5cffc684e2beae591a20613845e4c6dfa96))
* 清理代码 ([c8afa81](https://github.com/xihan123/QDReadHook/commit/c8afa81ad75a49a4e1317c80f4da3aa16f044af8))
* 移除无用图片 ([2b57494](https://github.com/xihan123/QDReadHook/commit/2b5749411dcbc14f3a26fdcbdf30721a92ef85eb))

## [2.1.7](https://github.com/xihan123/QDReadHook/compare/v2.1.7...v2.1.7) (2023-07-04)


### ⚠ BREAKING CHANGES

* 移除`配音导入`
* 仅支持 `884`+ 版本拦截启动图页面的广告

### Features

* `872`+ 版本书籍详情-快速屏蔽弹框 ([9ada69e](https://github.com/xihan123/QDReadHook/commit/9ada69e7a3a19f9a66a20f45832d32946ef4a60a))
* `872`+ 版本自定义粉丝值 ([958ca79](https://github.com/xihan123/QDReadHook/commit/958ca793bb69ea95b91326883f7d803b641038d2))
* `878` 版本隐藏主页面-顶部战力提示 ([aba0c76](https://github.com/xihan123/QDReadHook/commit/aba0c76850657f8792e3332712b437148a409cc8))
* `884` 版本新增章评相关 ([a64057f](https://github.com/xihan123/QDReadHook/commit/a64057f55c76379435724ccb403eaf776a912008))
* `884` 版本章评导入导出配音以及一键导出表情包 ([ab70a69](https://github.com/xihan123/QDReadHook/commit/ab70a69410790291c1874a3d75bcf422c2b5df06))
* `896`、`900` 版本新增阅读页最后一页相关 ([e56e33f](https://github.com/xihan123/QDReadHook/commit/e56e33f9fc305f4370c21b3e02527be4bea0b2dd))
* **Intercept:** `872` 版本自选拦截初始化 ([fc74523](https://github.com/xihan123/QDReadHook/commit/fc74523ed05cfabe123c18efb24e4b50c1550298))
* **main:** `872` 版本新旧精选布局 ([922da5e](https://github.com/xihan123/QDReadHook/commit/922da5e69179f455beafe9370510cb224ba7afd7))
* **ViewHide:** `872` 版本自选精选-隐藏控件 ([d6554e9](https://github.com/xihan123/QDReadHook/commit/d6554e985c0673c56fd5379f185a1bffa1cf02e1))
* **主设置:** `896`+ 试用模式弹框、真·免广告领取奖励 ([782d270](https://github.com/xihan123/QDReadHook/commit/782d270831f63d97f60c9e02fa5e724efbc4135c))
* **主设置:** `906` +版本新增隐藏福利列表 ([83ab08a](https://github.com/xihan123/QDReadHook/commit/83ab08a6cbcc47bd5340e7168d43ad11e3ac5ad3))
* 仅支持 `884`+ 版本拦截启动图页面的广告 ([41de6e2](https://github.com/xihan123/QDReadHook/commit/41de6e21d123fa9b6e2a266450f4feaf18a5fecf))
* 最低支持版本改为Android 8 ([9a02bd8](https://github.com/xihan123/QDReadHook/commit/9a02bd80720120440b7077927f7c76582365a2c2))
* **广告:** 禁用初始化GDT SDK ([4335d0d](https://github.com/xihan123/QDReadHook/commit/4335d0d20f39bc96dca4a604cd0334cb9bebc72b))
* **广告设置:** `896`+ 阅读页-最新页面弹框广告 ([327f2de](https://github.com/xihan123/QDReadHook/commit/327f2de57c8bbaf175cbe8dea76828dfbd789be3))
* **拦截设置:** `896`+ 异步主GDT广告任务、异步主游戏广告SDK任务、异步主游戏下载任务、异步子屏幕截图任务、部分环境检测 ([3983fc4](https://github.com/xihan123/QDReadHook/commit/3983fc401ea91303aa9eb1a287ebeb49e9500b22))
* **替换规则设置:** `896`+ 自定义设备信息 ([2853ead](https://github.com/xihan123/QDReadHook/commit/2853eadb1945b4d085e536240572f728c24db96b))
* 移除`配音导入` ([5052b4d](https://github.com/xihan123/QDReadHook/commit/5052b4d0c87f7151b1f61580dd6754b113bf1ca1))
* 适配 `878` 版本 ([40efb28](https://github.com/xihan123/QDReadHook/commit/40efb282b4dfbf5d80cbaa08524f5bea1b7d3dc5))
* 适配 `884` 版本 ([4742bd6](https://github.com/xihan123/QDReadHook/commit/4742bd66bf6c249fe5848ce44fe4479d87e049b7))
* 适配 `890` 版本 ([d5620a8](https://github.com/xihan123/QDReadHook/commit/d5620a818e4696323cfdb7051ed681a3dfe32032))
* 适配 `896`、`900` 版本 ([4666116](https://github.com/xihan123/QDReadHook/commit/4666116fb78120209a5485e9771147e631c951c5))
* 适配 `906` 版本 ([64d51e9](https://github.com/xihan123/QDReadHook/commit/64d51e937c29a4b9a72dc216705500ba5387d0ea))
* 适配 `916` 版本 ([162c6b6](https://github.com/xihan123/QDReadHook/commit/162c6b624d7577e6b61d7cc0001937c8fd227897))
* 适配 `924` 版本 ([f0a5165](https://github.com/xihan123/QDReadHook/commit/f0a51650e7aa27f2e698842fee2cb692411f5749))
* 适配 `932` 版本 ([3e709bf](https://github.com/xihan123/QDReadHook/commit/3e709bf936bebf4c2d9da2d6d57fd298cd623076))
* 适配 `938` 版本 ([5a4af1f](https://github.com/xihan123/QDReadHook/commit/5a4af1feca687a22c269aa36bc84339e7f3d578b))
* 适配 `944` 版本 ([7c5ccd6](https://github.com/xihan123/QDReadHook/commit/7c5ccd6fd11be16f68622e41f022744b2e1db2c5))


### Bug Fixes

* `890` 版本新"我"布局隐藏控件失效 ([3a31d74](https://github.com/xihan123/QDReadHook/commit/3a31d74aebd1ee03869803dd06f9ba8bc71d768f))
* build.gradle.kts ([f5a9e8d](https://github.com/xihan123/QDReadHook/commit/f5a9e8d6ee2d7a84dbd154032d85e79e42dbc91d))
* **deps:** update activity to v1.8.0-alpha05 ([968924f](https://github.com/xihan123/QDReadHook/commit/968924f74dc054e24adb6525dbfb918c96e59155))
* **deps:** update dependency androidx.activity:activity-compose to v1.8.0-alpha06 ([bfaa442](https://github.com/xihan123/QDReadHook/commit/bfaa442fed4c884c617f0a287a830cf854b4d7ce))
* **deps:** update dependency com.alibaba.fastjson2:fastjson2-kotlin to v2.0.34 ([116cac9](https://github.com/xihan123/QDReadHook/commit/116cac9163d08c49c66f41b61a476e831ea9258f))
* **deps:** update dependency com.google.android.material:material to v1.10.0-alpha04 ([1a58cea](https://github.com/xihan123/QDReadHook/commit/1a58cea1ab87f8a665eeafe2976028b152a9b788))
* **deps:** update dependency org.htmlunit:htmlunit3-android to v3.3.0 ([fc741e3](https://github.com/xihan123/QDReadHook/commit/fc741e301a9aacf07cdd1ef18e4d93211b9929d5))
* UpsideDownCake ([af89061](https://github.com/xihan123/QDReadHook/commit/af890610baa321503e80675895452de01907147d))
* 免责声明逻辑 ([7165dff](https://github.com/xihan123/QDReadHook/commit/7165dff4814f7f9abe00cb6d53475ec57cf9f884))
* 可能出现的屏蔽失效 ([53fecd9](https://github.com/xihan123/QDReadHook/commit/53fecd9427b7882dbbdafb897efaf077a376aad2))
* 配音导入对话框 ([e5a1140](https://github.com/xihan123/QDReadHook/commit/e5a11401bd32ba9886338f373518bf85f75b8889))


### CI

* automerge-action.yml ([3b87691](https://github.com/xihan123/QDReadHook/commit/3b876913da521b336258ee533c57ff7eb51b357b))
* automerge-action.yml ([6778db0](https://github.com/xihan123/QDReadHook/commit/6778db0e20b83e4bf40ed329b470bb66f217975d))
* build.yml ([65b18de](https://github.com/xihan123/QDReadHook/commit/65b18debdf373d95efcd60a8eb6d34e73fa85607))
* build.yml ([29a3f27](https://github.com/xihan123/QDReadHook/commit/29a3f27fd6fe27231326773313ae41e76562e43d))
* build.yml ([07152b6](https://github.com/xihan123/QDReadHook/commit/07152b61179cbd1835e638ec195e0a56bb622928))
* build.yml ([350d1ba](https://github.com/xihan123/QDReadHook/commit/350d1ba3b57df5376740932da43573bf50deafa1))
* build.yml ([25a639c](https://github.com/xihan123/QDReadHook/commit/25a639c59a35f3d8677ad3263365fe43c9194849))
* build.yml ([9b7ca28](https://github.com/xihan123/QDReadHook/commit/9b7ca283466645f82c444caed73ae55c8593c29a))
* build.yml ([a0e846c](https://github.com/xihan123/QDReadHook/commit/a0e846c5cdd5a09ab1d9ff0d64764ca5623058f9))
* gitlab-ci.yml ([a5d06d0](https://github.com/xihan123/QDReadHook/commit/a5d06d02204071d9ae5e75e73dd8c604adb363cd))
* gitlab-ci.yml ([543f318](https://github.com/xihan123/QDReadHook/commit/543f3186755492fb80aa6cf10b6141c156c3bbe5))
* release.yml ([c141444](https://github.com/xihan123/QDReadHook/commit/c14144486bff474d9e347360be32f8ed69a03c89))
* release.yml ([6dffa62](https://github.com/xihan123/QDReadHook/commit/6dffa6241f48dbaaa7f761c2630098be29fa067a))
* release.yml ([8f4a426](https://github.com/xihan123/QDReadHook/commit/8f4a4261d004e192ce60e71e30c091b59e9a24c0))
* release.yml ([4aa87bf](https://github.com/xihan123/QDReadHook/commit/4aa87bfbeb9896e28e26c08e416afdf4d30d2b1a))
* release.yml ([a8462df](https://github.com/xihan123/QDReadHook/commit/a8462df14cdca9efe0b9329d99d211874e9466ec))
* release.yml ([a55af53](https://github.com/xihan123/QDReadHook/commit/a55af531758385255ba8c55e7584d675e3bc63c3))
* release.yml ([ceb2f67](https://github.com/xihan123/QDReadHook/commit/ceb2f67ad25d885e0851864c0d14d08047923e61))
* release.yml ([258a623](https://github.com/xihan123/QDReadHook/commit/258a623aa247b457d7eb2756df93be653ffcd5a0))
* release.yml ([bccd8ef](https://github.com/xihan123/QDReadHook/commit/bccd8ef95aed602d33170b056074af87e85ebb42))
* release.yml ([c2b07d6](https://github.com/xihan123/QDReadHook/commit/c2b07d6690cba39abff8649af6242a55d506a270))
* release.yml ([bced994](https://github.com/xihan123/QDReadHook/commit/bced9941c10b94e6235c1c9a0009a6160ce8de9f))
* release.yml ([012ad09](https://github.com/xihan123/QDReadHook/commit/012ad09d9fb1e797e276ad597491caa785593690))
* release.yml ([fc50d23](https://github.com/xihan123/QDReadHook/commit/fc50d23882d494f1b97ef7e26e62d1fc509c705e))
* release.yml ([14833f2](https://github.com/xihan123/QDReadHook/commit/14833f29e1171eb67ce67467e2ae67aeb3173d4c))
* release.yml ([6abc522](https://github.com/xihan123/QDReadHook/commit/6abc5221e4d5d626a5f12bdb21fcc59186b9f8ce))
* stale.yml ([6a4808c](https://github.com/xihan123/QDReadHook/commit/6a4808cacac71b8ad5e65406b05f6ce3d7df8964))


### Miscellaneous

* **deps:** update dependency com.google.devtools.ksp to v1.9.0-rc-1.0.11 ([461ece5](https://github.com/xihan123/QDReadHook/commit/461ece5db162d903a17c36408e0ce788ac95fa0d))
* **deps:** update kotlin monorepo to v1.9.0-rc ([07b80e7](https://github.com/xihan123/QDReadHook/commit/07b80e759ec2095b4247e9372961046e9693a84d))
* Initial commit ([43517f5](https://github.com/xihan123/QDReadHook/commit/43517f5bffcb3a53c6970d514ee39da48d7a4d8f))
* **master:** release 2.0.4 ([b5d5c41](https://github.com/xihan123/QDReadHook/commit/b5d5c41a74e6c9f2f181220f06d9ee3668e7fafa))
* **master:** release 2.0.4 ([81327f9](https://github.com/xihan123/QDReadHook/commit/81327f91ae079a75a63ecfa4f5f5baafce1aea05))
* **master:** release 2.0.5 ([c7ae607](https://github.com/xihan123/QDReadHook/commit/c7ae6070df5619204387f087b8741b5dded8214d))
* **master:** release 2.0.6 ([a0cb22d](https://github.com/xihan123/QDReadHook/commit/a0cb22d3c7a6e10292dac21bd667e800fcc57523))
* **master:** release 2.0.6 ([cfa49e2](https://github.com/xihan123/QDReadHook/commit/cfa49e263651a4610d954a016cd643adbaeb6d11))
* **master:** release 2.0.7 ([6952b49](https://github.com/xihan123/QDReadHook/commit/6952b493813436868d70f0997129a62cc87d9f00))
* **master:** release 2.0.7 ([4c83ba0](https://github.com/xihan123/QDReadHook/commit/4c83ba0d1d2205a90ef05dd458373130df4e2c25))
* **master:** release 2.0.7 ([3f08b07](https://github.com/xihan123/QDReadHook/commit/3f08b079d2440825abeb4bfee97b8fdd3213964c))
* **master:** release 2.0.7 ([b35c104](https://github.com/xihan123/QDReadHook/commit/b35c10478159d77ee95332d80c14389cb5ae83eb))
* **master:** release 2.0.8 ([7d3311b](https://github.com/xihan123/QDReadHook/commit/7d3311b8e1021e200f364435f0f947aa429ffe03))
* **master:** release 2.0.8 ([b3baa6a](https://github.com/xihan123/QDReadHook/commit/b3baa6a90a7868c5d18469a11c7c80154ca06b66))
* **master:** release 2.0.8 ([0bc1030](https://github.com/xihan123/QDReadHook/commit/0bc103040fbeeb29cedd831ce77e5cda315164ce))
* **master:** release 2.0.9 ([360b229](https://github.com/xihan123/QDReadHook/commit/360b22919ac401c7d7b57724210c547078753fb2))
* **master:** release 2.0.9 ([98e245c](https://github.com/xihan123/QDReadHook/commit/98e245c5af1772535a5305c05edfaafc505f5c49))
* **master:** release 2.1.0 ([cdea359](https://github.com/xihan123/QDReadHook/commit/cdea3592d59aecd58585fc3a24233189daac6ca3))
* **master:** release 2.1.0 ([afd2f06](https://github.com/xihan123/QDReadHook/commit/afd2f064c115d32de186cd10262d3f27eeb27926))
* **master:** release 2.1.0 ([3c5035a](https://github.com/xihan123/QDReadHook/commit/3c5035ab81de59c029be633841135e68ebfbee4e))
* **master:** release 2.1.0 ([b58da5a](https://github.com/xihan123/QDReadHook/commit/b58da5aa1d783e2a9cf06a7b3864e345d1c486e5))
* **master:** release 2.1.0 ([e0ce41d](https://github.com/xihan123/QDReadHook/commit/e0ce41d0f3c8ab31281ee14672e687db42f4424c))
* **master:** release 2.1.1 ([5d427d6](https://github.com/xihan123/QDReadHook/commit/5d427d6dcd02f7e36fad715e9e6ffb9f388b91fd))
* **master:** release 2.1.1 ([333ae33](https://github.com/xihan123/QDReadHook/commit/333ae332657f07038cbb9067b5be56c527668533))
* **master:** release 2.1.2 ([2433660](https://github.com/xihan123/QDReadHook/commit/243366077d580c1eeefe4dd4a44f606e3e33aaa8))
* **master:** release 2.1.2 ([62adb53](https://github.com/xihan123/QDReadHook/commit/62adb53b26ec9a03e14027f4b5597b9b6775616c))
* **master:** release 2.1.2 ([bdd54bd](https://github.com/xihan123/QDReadHook/commit/bdd54bd29a72cd289c0fbc74d326b56a95405cbd))
* **master:** release 2.1.3 ([3487c69](https://github.com/xihan123/QDReadHook/commit/3487c69ecb7d97d030641656b5e8128c9e0774de))
* **master:** release 2.1.3 ([25e0055](https://github.com/xihan123/QDReadHook/commit/25e00559f09a1d16871d0311eac01d8f8467e10e))
* **master:** release 2.1.4 ([92832a9](https://github.com/xihan123/QDReadHook/commit/92832a9b4157a967b34b8e8dbb29e45a857d5f9f))
* **master:** release 2.1.4 ([ba2f338](https://github.com/xihan123/QDReadHook/commit/ba2f338876e8316383d6e87978a216c73edff48a))
* **master:** release 2.1.5 ([5b07e2c](https://github.com/xihan123/QDReadHook/commit/5b07e2cf5f6beeb81528524fb781909767c8bc2e))
* **master:** release 2.1.5 ([f02f36c](https://github.com/xihan123/QDReadHook/commit/f02f36c24462fb13c2a925e31b7809184736d155))
* **master:** release 2.1.6 ([4951cf5](https://github.com/xihan123/QDReadHook/commit/4951cf5ea0e1a85740c9ccaa9a2ce65ba14c9e33))
* **master:** release 2.1.6 ([5138196](https://github.com/xihan123/QDReadHook/commit/513819670481ddfc2ba9a9babd081f2a2b4fe30f))
* **master:** release 2.1.6 ([3f0b29f](https://github.com/xihan123/QDReadHook/commit/3f0b29fd8037767b88097052caa4858d36e60453))
* **master:** release 2.1.6 ([602f187](https://github.com/xihan123/QDReadHook/commit/602f1876af64cda345a28ddf0ed1f10b60b4b703))
* **master:** release 2.1.6 ([84047a6](https://github.com/xihan123/QDReadHook/commit/84047a6974931e6255a6c653fc51ce2fe6755326))
* **master:** release 2.1.6 ([8842c8f](https://github.com/xihan123/QDReadHook/commit/8842c8f95bb06101dbaed33b185598b483a8263f))
* **master:** release 2.1.6 ([dc502ff](https://github.com/xihan123/QDReadHook/commit/dc502ff9f0ceda5e307dd1d269e1ea182cee53cb))
* **master:** release 2.1.6 ([1559607](https://github.com/xihan123/QDReadHook/commit/1559607f925e8d0b6347d571d3434055d30cdae9))
* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))
* **master:** release 2.1.7 ([71e65cb](https://github.com/xihan123/QDReadHook/commit/71e65cb1656dff259ed9e76981419c1229afb9b7))
* 其他 ([1898b9d](https://github.com/xihan123/QDReadHook/commit/1898b9d5983863c3ca8111b902a9def74ce99c9c))
* 其他 ([69042cf](https://github.com/xihan123/QDReadHook/commit/69042cfe9e76f87e9a658046f9a74803acdaa079))
* 整理代码 ([2ed7da0](https://github.com/xihan123/QDReadHook/commit/2ed7da04fe80c0f61993c50c4479ceb6aff253ec))
* 新增免责声明 ([b5b19d1](https://github.com/xihan123/QDReadHook/commit/b5b19d1e7fa1a46058ecddb75fd1e8d3345bd4a8))
* 更新依赖 ([12a78fe](https://github.com/xihan123/QDReadHook/commit/12a78fe79f571d0b4c8bec788f1e4f280b1bcb88))
* 更新依赖 ([d5a2920](https://github.com/xihan123/QDReadHook/commit/d5a2920f1ec9e1f3355ab773d8181652fc5ae785))
* 更新依赖 ([f9cf0d1](https://github.com/xihan123/QDReadHook/commit/f9cf0d199e9edb93797af17a0920e95bafd0c313))
* 更新依赖 ([aa0fae1](https://github.com/xihan123/QDReadHook/commit/aa0fae1b77798c31e59eab0cbf2058d889488336))
* 更新依赖 ([ad90e71](https://github.com/xihan123/QDReadHook/commit/ad90e71a91383a9f812c321d6d55020cafe8dac0))
* 更新依赖 ([fea1055](https://github.com/xihan123/QDReadHook/commit/fea1055f800b1845bd1d75e29384adee9252a58c))
* 更新依赖 ([fa21690](https://github.com/xihan123/QDReadHook/commit/fa21690e8354fd38c976f0929f6f22a7085ce772))
* 更新依赖 ([42b80e8](https://github.com/xihan123/QDReadHook/commit/42b80e8f9b32e2dd9891ae098ff01e4fe1268080))
* 更新依赖库 ([21d64cd](https://github.com/xihan123/QDReadHook/commit/21d64cdb95f2ec2233b2d60e8cfefea58b8203ee))
* 更新依赖库 ([0bc7b1e](https://github.com/xihan123/QDReadHook/commit/0bc7b1e36ce835206cbd6036781256317d6d02a5))
* 更新依赖库 ([8e395bc](https://github.com/xihan123/QDReadHook/commit/8e395bc0a6bf830e85245779028d6d5979a97259))
* 更新依赖库 ([a51d4ab](https://github.com/xihan123/QDReadHook/commit/a51d4ab136d1142ff9eecc224b80ac887b4f80b1))
* 更新依赖库 ([55ab25f](https://github.com/xihan123/QDReadHook/commit/55ab25f78da20f493f7ff26ac22c5aa544d8f73e))
* 更新依赖库 ([0d86cd2](https://github.com/xihan123/QDReadHook/commit/0d86cd2fdf2c4b1f59ef32c3313d004fe95292cd))
* 更新图片 ([c45e0f6](https://github.com/xihan123/QDReadHook/commit/c45e0f6291745ed32286d709eb5c51674dbcfe56))
* 更新图片 ([1259d28](https://github.com/xihan123/QDReadHook/commit/1259d2807ecfe7516f6ace20116550a6a8bab324))
* 更新预览图 ([e9e4b8f](https://github.com/xihan123/QDReadHook/commit/e9e4b8fac213e289c39b2c0327f3168f9668fec2))
* 更新预览图 ([7733c5c](https://github.com/xihan123/QDReadHook/commit/7733c5cffc684e2beae591a20613845e4c6dfa96))
* 清理代码 ([c8afa81](https://github.com/xihan123/QDReadHook/commit/c8afa81ad75a49a4e1317c80f4da3aa16f044af8))
* 移除无用图片 ([2b57494](https://github.com/xihan123/QDReadHook/commit/2b5749411dcbc14f3a26fdcbdf30721a92ef85eb))

## [2.1.7](https://github.com/xihan123/QDReadHook/compare/v2.1.6...v2.1.7) (2023-07-04)


### ⚠ BREAKING CHANGES

* 移除`配音导入`

### Features

* 移除`配音导入` ([5052b4d](https://github.com/xihan123/QDReadHook/commit/5052b4d0c87f7151b1f61580dd6754b113bf1ca1))
* 适配 `944` 版本 ([7c5ccd6](https://github.com/xihan123/QDReadHook/commit/7c5ccd6fd11be16f68622e41f022744b2e1db2c5))


### Bug Fixes

* **deps:** update activity to v1.8.0-alpha05 ([968924f](https://github.com/xihan123/QDReadHook/commit/968924f74dc054e24adb6525dbfb918c96e59155))
* **deps:** update dependency androidx.activity:activity-compose to v1.8.0-alpha06 ([bfaa442](https://github.com/xihan123/QDReadHook/commit/bfaa442fed4c884c617f0a287a830cf854b4d7ce))
* **deps:** update dependency com.alibaba.fastjson2:fastjson2-kotlin to v2.0.34 ([116cac9](https://github.com/xihan123/QDReadHook/commit/116cac9163d08c49c66f41b61a476e831ea9258f))
* **deps:** update dependency com.google.android.material:material to v1.10.0-alpha04 ([1a58cea](https://github.com/xihan123/QDReadHook/commit/1a58cea1ab87f8a665eeafe2976028b152a9b788))
* **deps:** update dependency org.htmlunit:htmlunit3-android to v3.3.0 ([fc741e3](https://github.com/xihan123/QDReadHook/commit/fc741e301a9aacf07cdd1ef18e4d93211b9929d5))


### Miscellaneous

* **deps:** update dependency com.google.devtools.ksp to v1.9.0-rc-1.0.11 ([461ece5](https://github.com/xihan123/QDReadHook/commit/461ece5db162d903a17c36408e0ce788ac95fa0d))
* **deps:** update kotlin monorepo to v1.9.0-rc ([07b80e7](https://github.com/xihan123/QDReadHook/commit/07b80e759ec2095b4247e9372961046e9693a84d))
* **master:** release 2.1.6 ([4951cf5](https://github.com/xihan123/QDReadHook/commit/4951cf5ea0e1a85740c9ccaa9a2ce65ba14c9e33))
* **master:** release 2.1.6 ([5138196](https://github.com/xihan123/QDReadHook/commit/513819670481ddfc2ba9a9babd081f2a2b4fe30f))
* **master:** release 2.1.6 ([3f0b29f](https://github.com/xihan123/QDReadHook/commit/3f0b29fd8037767b88097052caa4858d36e60453))
* **master:** release 2.1.6 ([602f187](https://github.com/xihan123/QDReadHook/commit/602f1876af64cda345a28ddf0ed1f10b60b4b703))
* **master:** release 2.1.6 ([84047a6](https://github.com/xihan123/QDReadHook/commit/84047a6974931e6255a6c653fc51ce2fe6755326))
* **master:** release 2.1.6 ([8842c8f](https://github.com/xihan123/QDReadHook/commit/8842c8f95bb06101dbaed33b185598b483a8263f))
* **master:** release 2.1.6 ([dc502ff](https://github.com/xihan123/QDReadHook/commit/dc502ff9f0ceda5e307dd1d269e1ea182cee53cb))
* **master:** release 2.1.6 ([1559607](https://github.com/xihan123/QDReadHook/commit/1559607f925e8d0b6347d571d3434055d30cdae9))
* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))
* 整理代码 ([2ed7da0](https://github.com/xihan123/QDReadHook/commit/2ed7da04fe80c0f61993c50c4479ceb6aff253ec))
* 更新依赖 ([12a78fe](https://github.com/xihan123/QDReadHook/commit/12a78fe79f571d0b4c8bec788f1e4f280b1bcb88))


### CI

* release.yml ([c141444](https://github.com/xihan123/QDReadHook/commit/c14144486bff474d9e347360be32f8ed69a03c89))

## [2.1.6](https://github.com/xihan123/QDReadHook/compare/v2.1.6...v2.1.6) (2023-06-22)


### Bug Fixes

* **deps:** update activity to v1.8.0-alpha05 ([968924f](https://github.com/xihan123/QDReadHook/commit/968924f74dc054e24adb6525dbfb918c96e59155))
* **deps:** update dependency androidx.activity:activity-compose to v1.8.0-alpha06 ([bfaa442](https://github.com/xihan123/QDReadHook/commit/bfaa442fed4c884c617f0a287a830cf854b4d7ce))
* **deps:** update dependency com.alibaba.fastjson2:fastjson2-kotlin to v2.0.34 ([116cac9](https://github.com/xihan123/QDReadHook/commit/116cac9163d08c49c66f41b61a476e831ea9258f))
* **deps:** update dependency com.google.android.material:material to v1.10.0-alpha04 ([1a58cea](https://github.com/xihan123/QDReadHook/commit/1a58cea1ab87f8a665eeafe2976028b152a9b788))
* **deps:** update dependency org.htmlunit:htmlunit3-android to v3.3.0 ([fc741e3](https://github.com/xihan123/QDReadHook/commit/fc741e301a9aacf07cdd1ef18e4d93211b9929d5))


### Miscellaneous

* **deps:** update dependency com.google.devtools.ksp to v1.9.0-rc-1.0.11 ([461ece5](https://github.com/xihan123/QDReadHook/commit/461ece5db162d903a17c36408e0ce788ac95fa0d))
* **deps:** update kotlin monorepo to v1.9.0-rc ([07b80e7](https://github.com/xihan123/QDReadHook/commit/07b80e759ec2095b4247e9372961046e9693a84d))
* **master:** release 2.1.6 ([5138196](https://github.com/xihan123/QDReadHook/commit/513819670481ddfc2ba9a9babd081f2a2b4fe30f))
* **master:** release 2.1.6 ([3f0b29f](https://github.com/xihan123/QDReadHook/commit/3f0b29fd8037767b88097052caa4858d36e60453))
* **master:** release 2.1.6 ([602f187](https://github.com/xihan123/QDReadHook/commit/602f1876af64cda345a28ddf0ed1f10b60b4b703))
* **master:** release 2.1.6 ([84047a6](https://github.com/xihan123/QDReadHook/commit/84047a6974931e6255a6c653fc51ce2fe6755326))
* **master:** release 2.1.6 ([8842c8f](https://github.com/xihan123/QDReadHook/commit/8842c8f95bb06101dbaed33b185598b483a8263f))
* **master:** release 2.1.6 ([dc502ff](https://github.com/xihan123/QDReadHook/commit/dc502ff9f0ceda5e307dd1d269e1ea182cee53cb))
* **master:** release 2.1.6 ([1559607](https://github.com/xihan123/QDReadHook/commit/1559607f925e8d0b6347d571d3434055d30cdae9))
* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))

## [2.1.6](https://github.com/xihan123/QDReadHook/compare/v2.1.6...v2.1.6) (2023-06-22)


### Bug Fixes

* **deps:** update activity to v1.8.0-alpha05 ([968924f](https://github.com/xihan123/QDReadHook/commit/968924f74dc054e24adb6525dbfb918c96e59155))
* **deps:** update dependency androidx.activity:activity-compose to v1.8.0-alpha06 ([bfaa442](https://github.com/xihan123/QDReadHook/commit/bfaa442fed4c884c617f0a287a830cf854b4d7ce))
* **deps:** update dependency com.alibaba.fastjson2:fastjson2-kotlin to v2.0.34 ([116cac9](https://github.com/xihan123/QDReadHook/commit/116cac9163d08c49c66f41b61a476e831ea9258f))
* **deps:** update dependency com.google.android.material:material to v1.10.0-alpha04 ([1a58cea](https://github.com/xihan123/QDReadHook/commit/1a58cea1ab87f8a665eeafe2976028b152a9b788))
* **deps:** update dependency org.htmlunit:htmlunit3-android to v3.3.0 ([fc741e3](https://github.com/xihan123/QDReadHook/commit/fc741e301a9aacf07cdd1ef18e4d93211b9929d5))


### Miscellaneous

* **deps:** update dependency com.google.devtools.ksp to v1.9.0-rc-1.0.11 ([461ece5](https://github.com/xihan123/QDReadHook/commit/461ece5db162d903a17c36408e0ce788ac95fa0d))
* **master:** release 2.1.6 ([3f0b29f](https://github.com/xihan123/QDReadHook/commit/3f0b29fd8037767b88097052caa4858d36e60453))
* **master:** release 2.1.6 ([602f187](https://github.com/xihan123/QDReadHook/commit/602f1876af64cda345a28ddf0ed1f10b60b4b703))
* **master:** release 2.1.6 ([84047a6](https://github.com/xihan123/QDReadHook/commit/84047a6974931e6255a6c653fc51ce2fe6755326))
* **master:** release 2.1.6 ([8842c8f](https://github.com/xihan123/QDReadHook/commit/8842c8f95bb06101dbaed33b185598b483a8263f))
* **master:** release 2.1.6 ([dc502ff](https://github.com/xihan123/QDReadHook/commit/dc502ff9f0ceda5e307dd1d269e1ea182cee53cb))
* **master:** release 2.1.6 ([1559607](https://github.com/xihan123/QDReadHook/commit/1559607f925e8d0b6347d571d3434055d30cdae9))
* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))

## [2.1.6](https://github.com/xihan123/QDReadHook/compare/v2.1.6...v2.1.6) (2023-06-22)


### Bug Fixes

* **deps:** update activity to v1.8.0-alpha05 ([968924f](https://github.com/xihan123/QDReadHook/commit/968924f74dc054e24adb6525dbfb918c96e59155))
* **deps:** update dependency androidx.activity:activity-compose to v1.8.0-alpha06 ([bfaa442](https://github.com/xihan123/QDReadHook/commit/bfaa442fed4c884c617f0a287a830cf854b4d7ce))
* **deps:** update dependency com.alibaba.fastjson2:fastjson2-kotlin to v2.0.34 ([116cac9](https://github.com/xihan123/QDReadHook/commit/116cac9163d08c49c66f41b61a476e831ea9258f))
* **deps:** update dependency com.google.android.material:material to v1.10.0-alpha04 ([1a58cea](https://github.com/xihan123/QDReadHook/commit/1a58cea1ab87f8a665eeafe2976028b152a9b788))
* **deps:** update dependency org.htmlunit:htmlunit3-android to v3.3.0 ([fc741e3](https://github.com/xihan123/QDReadHook/commit/fc741e301a9aacf07cdd1ef18e4d93211b9929d5))


### Miscellaneous

* **master:** release 2.1.6 ([602f187](https://github.com/xihan123/QDReadHook/commit/602f1876af64cda345a28ddf0ed1f10b60b4b703))
* **master:** release 2.1.6 ([84047a6](https://github.com/xihan123/QDReadHook/commit/84047a6974931e6255a6c653fc51ce2fe6755326))
* **master:** release 2.1.6 ([8842c8f](https://github.com/xihan123/QDReadHook/commit/8842c8f95bb06101dbaed33b185598b483a8263f))
* **master:** release 2.1.6 ([dc502ff](https://github.com/xihan123/QDReadHook/commit/dc502ff9f0ceda5e307dd1d269e1ea182cee53cb))
* **master:** release 2.1.6 ([1559607](https://github.com/xihan123/QDReadHook/commit/1559607f925e8d0b6347d571d3434055d30cdae9))
* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))

## [2.1.6](https://github.com/xihan123/QDReadHook/compare/v2.1.6...v2.1.6) (2023-06-19)


### Bug Fixes

* **deps:** update activity to v1.8.0-alpha05 ([968924f](https://github.com/xihan123/QDReadHook/commit/968924f74dc054e24adb6525dbfb918c96e59155))
* **deps:** update dependency com.alibaba.fastjson2:fastjson2-kotlin to v2.0.34 ([116cac9](https://github.com/xihan123/QDReadHook/commit/116cac9163d08c49c66f41b61a476e831ea9258f))
* **deps:** update dependency com.google.android.material:material to v1.10.0-alpha04 ([1a58cea](https://github.com/xihan123/QDReadHook/commit/1a58cea1ab87f8a665eeafe2976028b152a9b788))
* **deps:** update dependency org.htmlunit:htmlunit3-android to v3.3.0 ([fc741e3](https://github.com/xihan123/QDReadHook/commit/fc741e301a9aacf07cdd1ef18e4d93211b9929d5))


### Miscellaneous

* **master:** release 2.1.6 ([84047a6](https://github.com/xihan123/QDReadHook/commit/84047a6974931e6255a6c653fc51ce2fe6755326))
* **master:** release 2.1.6 ([8842c8f](https://github.com/xihan123/QDReadHook/commit/8842c8f95bb06101dbaed33b185598b483a8263f))
* **master:** release 2.1.6 ([dc502ff](https://github.com/xihan123/QDReadHook/commit/dc502ff9f0ceda5e307dd1d269e1ea182cee53cb))
* **master:** release 2.1.6 ([1559607](https://github.com/xihan123/QDReadHook/commit/1559607f925e8d0b6347d571d3434055d30cdae9))
* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))

## [2.1.6](https://github.com/xihan123/QDReadHook/compare/v2.1.6...v2.1.6) (2023-06-19)


### Bug Fixes

* **deps:** update activity to v1.8.0-alpha05 ([968924f](https://github.com/xihan123/QDReadHook/commit/968924f74dc054e24adb6525dbfb918c96e59155))
* **deps:** update dependency com.alibaba.fastjson2:fastjson2-kotlin to v2.0.34 ([116cac9](https://github.com/xihan123/QDReadHook/commit/116cac9163d08c49c66f41b61a476e831ea9258f))
* **deps:** update dependency com.google.android.material:material to v1.10.0-alpha04 ([1a58cea](https://github.com/xihan123/QDReadHook/commit/1a58cea1ab87f8a665eeafe2976028b152a9b788))


### Miscellaneous

* **master:** release 2.1.6 ([8842c8f](https://github.com/xihan123/QDReadHook/commit/8842c8f95bb06101dbaed33b185598b483a8263f))
* **master:** release 2.1.6 ([dc502ff](https://github.com/xihan123/QDReadHook/commit/dc502ff9f0ceda5e307dd1d269e1ea182cee53cb))
* **master:** release 2.1.6 ([1559607](https://github.com/xihan123/QDReadHook/commit/1559607f925e8d0b6347d571d3434055d30cdae9))
* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))

## [2.1.6](https://github.com/xihan123/QDReadHook/compare/v2.1.6...v2.1.6) (2023-06-19)


### Bug Fixes

* **deps:** update activity to v1.8.0-alpha05 ([968924f](https://github.com/xihan123/QDReadHook/commit/968924f74dc054e24adb6525dbfb918c96e59155))
* **deps:** update dependency com.alibaba.fastjson2:fastjson2-kotlin to v2.0.34 ([116cac9](https://github.com/xihan123/QDReadHook/commit/116cac9163d08c49c66f41b61a476e831ea9258f))


### Miscellaneous

* **master:** release 2.1.6 ([dc502ff](https://github.com/xihan123/QDReadHook/commit/dc502ff9f0ceda5e307dd1d269e1ea182cee53cb))
* **master:** release 2.1.6 ([1559607](https://github.com/xihan123/QDReadHook/commit/1559607f925e8d0b6347d571d3434055d30cdae9))
* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))

## [2.1.6](https://github.com/xihan123/QDReadHook/compare/v2.1.6...v2.1.6) (2023-06-19)


### Bug Fixes

* **deps:** update dependency com.alibaba.fastjson2:fastjson2-kotlin to v2.0.34 ([116cac9](https://github.com/xihan123/QDReadHook/commit/116cac9163d08c49c66f41b61a476e831ea9258f))


### Miscellaneous

* **master:** release 2.1.6 ([1559607](https://github.com/xihan123/QDReadHook/commit/1559607f925e8d0b6347d571d3434055d30cdae9))
* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))

## [2.1.6](https://github.com/xihan123/QDReadHook/compare/v2.1.6...v2.1.6) (2023-06-19)


### Miscellaneous

* **master:** release 2.1.6 ([b5b8a56](https://github.com/xihan123/QDReadHook/commit/b5b8a56f0b2c2beda0292ef6fcdccee12522499f))

## [2.1.6](https://github.com/xihan123/QDReadHook/compare/v2.1.5...v2.1.6) (2023-06-19)


### Features

* 适配 `938` 版本 ([5a4af1f](https://github.com/xihan123/QDReadHook/commit/5a4af1feca687a22c269aa36bc84339e7f3d578b))


### CI

* release.yml ([6dffa62](https://github.com/xihan123/QDReadHook/commit/6dffa6241f48dbaaa7f761c2630098be29fa067a))


### Miscellaneous

* **master:** release 2.1.5 ([5b07e2c](https://github.com/xihan123/QDReadHook/commit/5b07e2cf5f6beeb81528524fb781909767c8bc2e))
* **master:** release 2.1.5 ([f02f36c](https://github.com/xihan123/QDReadHook/commit/f02f36c24462fb13c2a925e31b7809184736d155))
* 更新依赖 ([d5a2920](https://github.com/xihan123/QDReadHook/commit/d5a2920f1ec9e1f3355ab773d8181652fc5ae785))

## [2.1.5](https://github.com/xihan123/QDReadHook/compare/v2.1.5...v2.1.5) (2023-06-11)


### Miscellaneous

* **master:** release 2.1.5 ([f02f36c](https://github.com/xihan123/QDReadHook/commit/f02f36c24462fb13c2a925e31b7809184736d155))

## [2.1.5](https://github.com/xihan123/QDReadHook/compare/v2.1.4...v2.1.5) (2023-06-11)


### Features

* **广告:** 禁用初始化GDT SDK ([4335d0d](https://github.com/xihan123/QDReadHook/commit/4335d0d20f39bc96dca4a604cd0334cb9bebc72b))
* 适配 `932` 版本 ([3e709bf](https://github.com/xihan123/QDReadHook/commit/3e709bf936bebf4c2d9da2d6d57fd298cd623076))


### Miscellaneous

* 更新依赖 ([f9cf0d1](https://github.com/xihan123/QDReadHook/commit/f9cf0d199e9edb93797af17a0920e95bafd0c313))


### CI

* release.yml ([8f4a426](https://github.com/xihan123/QDReadHook/commit/8f4a4261d004e192ce60e71e30c091b59e9a24c0))

## [2.1.4](https://github.com/xihan123/QDReadHook/compare/v2.1.4...v2.1.4) (2023-05-20)


### Miscellaneous

* **master:** release 2.1.4 ([ba2f338](https://github.com/xihan123/QDReadHook/commit/ba2f338876e8316383d6e87978a216c73edff48a))

## [2.1.4](https://github.com/xihan123/QDReadHook/compare/v2.1.3...v2.1.4) (2023-05-20)


### Features

* 适配 `924` 版本 ([f0a5165](https://github.com/xihan123/QDReadHook/commit/f0a51650e7aa27f2e698842fee2cb692411f5749))


### Miscellaneous

* **master:** release 2.1.2 ([2433660](https://github.com/xihan123/QDReadHook/commit/243366077d580c1eeefe4dd4a44f606e3e33aaa8))
* **master:** release 2.1.3 ([3487c69](https://github.com/xihan123/QDReadHook/commit/3487c69ecb7d97d030641656b5e8128c9e0774de))
* **master:** release 2.1.3 ([25e0055](https://github.com/xihan123/QDReadHook/commit/25e00559f09a1d16871d0311eac01d8f8467e10e))
* 更新依赖 ([ad90e71](https://github.com/xihan123/QDReadHook/commit/ad90e71a91383a9f812c321d6d55020cafe8dac0))


### CI

* build.yml ([65b18de](https://github.com/xihan123/QDReadHook/commit/65b18debdf373d95efcd60a8eb6d34e73fa85607))
* release.yml ([4aa87bf](https://github.com/xihan123/QDReadHook/commit/4aa87bfbeb9896e28e26c08e416afdf4d30d2b1a))
* release.yml ([a8462df](https://github.com/xihan123/QDReadHook/commit/a8462df14cdca9efe0b9329d99d211874e9466ec))

## [2.1.3](https://github.com/xihan123/QDReadHook/compare/v2.1.3...v2.1.3) (2023-05-13)


### CI

* release.yml ([a8462df](https://github.com/xihan123/QDReadHook/commit/a8462df14cdca9efe0b9329d99d211874e9466ec))


### Miscellaneous

* **master:** release 2.1.2 ([2433660](https://github.com/xihan123/QDReadHook/commit/243366077d580c1eeefe4dd4a44f606e3e33aaa8))
* **master:** release 2.1.3 ([25e0055](https://github.com/xihan123/QDReadHook/commit/25e00559f09a1d16871d0311eac01d8f8467e10e))

## [2.1.3](https://github.com/xihan123/QDReadHook/compare/v2.1.2...v2.1.3) (2023-05-13)


### Features

* 适配 `916` 版本 ([162c6b6](https://github.com/xihan123/QDReadHook/commit/162c6b624d7577e6b61d7cc0001937c8fd227897))


### Miscellaneous

* **master:** release 2.1.2 ([2433660](https://github.com/xihan123/QDReadHook/commit/243366077d580c1eeefe4dd4a44f606e3e33aaa8))
* **master:** release 2.1.2 ([62adb53](https://github.com/xihan123/QDReadHook/commit/62adb53b26ec9a03e14027f4b5597b9b6775616c))
* **master:** release 2.1.2 ([bdd54bd](https://github.com/xihan123/QDReadHook/commit/bdd54bd29a72cd289c0fbc74d326b56a95405cbd))
* 更新依赖 ([fea1055](https://github.com/xihan123/QDReadHook/commit/fea1055f800b1845bd1d75e29384adee9252a58c))
* 更新图片 ([c45e0f6](https://github.com/xihan123/QDReadHook/commit/c45e0f6291745ed32286d709eb5c51674dbcfe56))
* 清理代码 ([c8afa81](https://github.com/xihan123/QDReadHook/commit/c8afa81ad75a49a4e1317c80f4da3aa16f044af8))


### CI

* release.yml ([a8462df](https://github.com/xihan123/QDReadHook/commit/a8462df14cdca9efe0b9329d99d211874e9466ec))

## [2.1.2](https://github.com/xihan123/QDReadHook/compare/v2.1.2...v2.1.2) (2023-05-13)


### Features

* 适配 `916` 版本 ([162c6b6](https://github.com/xihan123/QDReadHook/commit/162c6b624d7577e6b61d7cc0001937c8fd227897))


### Miscellaneous

* **master:** release 2.1.2 ([62adb53](https://github.com/xihan123/QDReadHook/commit/62adb53b26ec9a03e14027f4b5597b9b6775616c))
* **master:** release 2.1.2 ([bdd54bd](https://github.com/xihan123/QDReadHook/commit/bdd54bd29a72cd289c0fbc74d326b56a95405cbd))
* 更新依赖 ([fea1055](https://github.com/xihan123/QDReadHook/commit/fea1055f800b1845bd1d75e29384adee9252a58c))
* 更新图片 ([c45e0f6](https://github.com/xihan123/QDReadHook/commit/c45e0f6291745ed32286d709eb5c51674dbcfe56))
* 清理代码 ([c8afa81](https://github.com/xihan123/QDReadHook/commit/c8afa81ad75a49a4e1317c80f4da3aa16f044af8))

## [2.1.2](https://github.com/xihan123/QDReadHook/compare/v2.1.2...v2.1.2) (2023-05-09)


### Miscellaneous

* **master:** release 2.1.2 ([bdd54bd](https://github.com/xihan123/QDReadHook/commit/bdd54bd29a72cd289c0fbc74d326b56a95405cbd))
* 更新图片 ([c45e0f6](https://github.com/xihan123/QDReadHook/commit/c45e0f6291745ed32286d709eb5c51674dbcfe56))

## [2.1.2](https://github.com/xihan123/QDReadHook/compare/v2.1.1...v2.1.2) (2023-05-09)


### Features

* **主设置:** `906` +版本新增隐藏福利列表 ([83ab08a](https://github.com/xihan123/QDReadHook/commit/83ab08a6cbcc47bd5340e7168d43ad11e3ac5ad3))


### CI

* release.yml ([a55af53](https://github.com/xihan123/QDReadHook/commit/a55af531758385255ba8c55e7584d675e3bc63c3))


### Miscellaneous

* **master:** release 2.1.1 ([5d427d6](https://github.com/xihan123/QDReadHook/commit/5d427d6dcd02f7e36fad715e9e6ffb9f388b91fd))
* **master:** release 2.1.1 ([333ae33](https://github.com/xihan123/QDReadHook/commit/333ae332657f07038cbb9067b5be56c527668533))
* 更新依赖 ([fa21690](https://github.com/xihan123/QDReadHook/commit/fa21690e8354fd38c976f0929f6f22a7085ce772))
* 更新图片 ([c45e0f6](https://github.com/xihan123/QDReadHook/commit/c45e0f6291745ed32286d709eb5c51674dbcfe56))

## [2.1.1](https://github.com/xihan123/QDReadHook/compare/v2.1.1...v2.1.1) (2023-05-01)


### Miscellaneous

* **master:** release 2.1.1 ([333ae33](https://github.com/xihan123/QDReadHook/commit/333ae332657f07038cbb9067b5be56c527668533))

## [2.1.1](https://github.com/xihan123/QDReadHook/compare/v2.1.0...v2.1.1) (2023-05-01)


### Features

* 适配 `906` 版本 ([64d51e9](https://github.com/xihan123/QDReadHook/commit/64d51e937c29a4b9a72dc216705500ba5387d0ea))


### CI

* build.yml ([29a3f27](https://github.com/xihan123/QDReadHook/commit/29a3f27fd6fe27231326773313ae41e76562e43d))
* release.yml ([ceb2f67](https://github.com/xihan123/QDReadHook/commit/ceb2f67ad25d885e0851864c0d14d08047923e61))

## [2.1.0](https://github.com/xihan123/QDReadHook/compare/v2.1.0...v2.1.0) (2023-04-27)


### Bug Fixes

* build.gradle.kts ([f5a9e8d](https://github.com/xihan123/QDReadHook/commit/f5a9e8d6ee2d7a84dbd154032d85e79e42dbc91d))
* UpsideDownCake ([af89061](https://github.com/xihan123/QDReadHook/commit/af890610baa321503e80675895452de01907147d))


### Miscellaneous

* **master:** release 2.1.0 ([afd2f06](https://github.com/xihan123/QDReadHook/commit/afd2f064c115d32de186cd10262d3f27eeb27926))
* **master:** release 2.1.0 ([3c5035a](https://github.com/xihan123/QDReadHook/commit/3c5035ab81de59c029be633841135e68ebfbee4e))
* **master:** release 2.1.0 ([b58da5a](https://github.com/xihan123/QDReadHook/commit/b58da5aa1d783e2a9cf06a7b3864e345d1c486e5))
* **master:** release 2.1.0 ([e0ce41d](https://github.com/xihan123/QDReadHook/commit/e0ce41d0f3c8ab31281ee14672e687db42f4424c))
* 其他 ([1898b9d](https://github.com/xihan123/QDReadHook/commit/1898b9d5983863c3ca8111b902a9def74ce99c9c))

## [2.1.0](https://github.com/xihan123/QDReadHook/compare/v2.1.0...v2.1.0) (2023-04-27)


### Bug Fixes

* build.gradle.kts ([f5a9e8d](https://github.com/xihan123/QDReadHook/commit/f5a9e8d6ee2d7a84dbd154032d85e79e42dbc91d))


### Miscellaneous

* **master:** release 2.1.0 ([3c5035a](https://github.com/xihan123/QDReadHook/commit/3c5035ab81de59c029be633841135e68ebfbee4e))
* **master:** release 2.1.0 ([b58da5a](https://github.com/xihan123/QDReadHook/commit/b58da5aa1d783e2a9cf06a7b3864e345d1c486e5))
* **master:** release 2.1.0 ([e0ce41d](https://github.com/xihan123/QDReadHook/commit/e0ce41d0f3c8ab31281ee14672e687db42f4424c))
* 其他 ([1898b9d](https://github.com/xihan123/QDReadHook/commit/1898b9d5983863c3ca8111b902a9def74ce99c9c))

## [2.1.0](https://github.com/xihan123/QDReadHook/compare/v2.1.0...v2.1.0) (2023-04-27)


### Miscellaneous

* **master:** release 2.1.0 ([b58da5a](https://github.com/xihan123/QDReadHook/commit/b58da5aa1d783e2a9cf06a7b3864e345d1c486e5))
* **master:** release 2.1.0 ([e0ce41d](https://github.com/xihan123/QDReadHook/commit/e0ce41d0f3c8ab31281ee14672e687db42f4424c))
* 其他 ([1898b9d](https://github.com/xihan123/QDReadHook/commit/1898b9d5983863c3ca8111b902a9def74ce99c9c))

## [2.1.0](https://github.com/xihan123/QDReadHook/compare/v2.1.0...v2.1.0) (2023-04-27)


### Miscellaneous

* **master:** release 2.1.0 ([e0ce41d](https://github.com/xihan123/QDReadHook/commit/e0ce41d0f3c8ab31281ee14672e687db42f4424c))

## [2.1.0](https://github.com/xihan123/QDReadHook/compare/v2.0.9...v2.1.0) (2023-04-27)


### Features

* **主设置:** `896`+ 试用模式弹框、真·免广告领取奖励 ([782d270](https://github.com/xihan123/QDReadHook/commit/782d270831f63d97f60c9e02fa5e724efbc4135c))
* **广告设置:** `896`+ 阅读页-最新页面弹框广告 ([327f2de](https://github.com/xihan123/QDReadHook/commit/327f2de57c8bbaf175cbe8dea76828dfbd789be3))
* **拦截设置:** `896`+ 异步主GDT广告任务、异步主游戏广告SDK任务、异步主游戏下载任务、异步子屏幕截图任务、部分环境检测 ([3983fc4](https://github.com/xihan123/QDReadHook/commit/3983fc401ea91303aa9eb1a287ebeb49e9500b22))
* **替换规则设置:** `896`+ 自定义设备信息 ([2853ead](https://github.com/xihan123/QDReadHook/commit/2853eadb1945b4d085e536240572f728c24db96b))


### Bug Fixes

* 免责声明逻辑 ([7165dff](https://github.com/xihan123/QDReadHook/commit/7165dff4814f7f9abe00cb6d53475ec57cf9f884))


### CI

* build.yml ([25a639c](https://github.com/xihan123/QDReadHook/commit/25a639c59a35f3d8677ad3263365fe43c9194849))
* release.yml ([258a623](https://github.com/xihan123/QDReadHook/commit/258a623aa247b457d7eb2756df93be653ffcd5a0))


### Miscellaneous

* **master:** release 2.0.9 ([360b229](https://github.com/xihan123/QDReadHook/commit/360b22919ac401c7d7b57724210c547078753fb2))
* **master:** release 2.0.9 ([98e245c](https://github.com/xihan123/QDReadHook/commit/98e245c5af1772535a5305c05edfaafc505f5c49))
* 其他 ([69042cf](https://github.com/xihan123/QDReadHook/commit/69042cfe9e76f87e9a658046f9a74803acdaa079))
* 更新依赖库 ([21d64cd](https://github.com/xihan123/QDReadHook/commit/21d64cdb95f2ec2233b2d60e8cfefea58b8203ee))
* 更新图片 ([1259d28](https://github.com/xihan123/QDReadHook/commit/1259d2807ecfe7516f6ace20116550a6a8bab324))

## [2.0.9](https://github.com/xihan123/QDReadHook/compare/v2.0.9...v2.0.9) (2023-04-18)


### Miscellaneous

* **master:** release 2.0.9 ([98e245c](https://github.com/xihan123/QDReadHook/commit/98e245c5af1772535a5305c05edfaafc505f5c49))

## [2.0.9](https://github.com/xihan123/QDReadHook/compare/v2.0.8...v2.0.9) (2023-04-18)


### Miscellaneous

* **master:** release 2.0.8 ([7d3311b](https://github.com/xihan123/QDReadHook/commit/7d3311b8e1021e200f364435f0f947aa429ffe03))
* **master:** release 2.0.8 ([b3baa6a](https://github.com/xihan123/QDReadHook/commit/b3baa6a90a7868c5d18469a11c7c80154ca06b66))
* **master:** release 2.0.8 ([0bc1030](https://github.com/xihan123/QDReadHook/commit/0bc103040fbeeb29cedd831ce77e5cda315164ce))
* 新增免责声明 ([b5b19d1](https://github.com/xihan123/QDReadHook/commit/b5b19d1e7fa1a46058ecddb75fd1e8d3345bd4a8))
* 更新依赖库 ([0bc7b1e](https://github.com/xihan123/QDReadHook/commit/0bc7b1e36ce835206cbd6036781256317d6d02a5))


### CI

* release.yml ([bccd8ef](https://github.com/xihan123/QDReadHook/commit/bccd8ef95aed602d33170b056074af87e85ebb42))

## [2.0.8](https://github.com/xihan123/QDReadHook/compare/v2.0.8...v2.0.8) (2023-04-18)


### Miscellaneous

* **master:** release 2.0.8 ([b3baa6a](https://github.com/xihan123/QDReadHook/commit/b3baa6a90a7868c5d18469a11c7c80154ca06b66))
* **master:** release 2.0.8 ([0bc1030](https://github.com/xihan123/QDReadHook/commit/0bc103040fbeeb29cedd831ce77e5cda315164ce))
* 新增免责声明 ([b5b19d1](https://github.com/xihan123/QDReadHook/commit/b5b19d1e7fa1a46058ecddb75fd1e8d3345bd4a8))
* 更新依赖库 ([0bc7b1e](https://github.com/xihan123/QDReadHook/commit/0bc7b1e36ce835206cbd6036781256317d6d02a5))

## [2.0.8](https://github.com/xihan123/QDReadHook/compare/v2.0.8...v2.0.8) (2023-04-16)


### Miscellaneous

* **master:** release 2.0.8 ([0bc1030](https://github.com/xihan123/QDReadHook/commit/0bc103040fbeeb29cedd831ce77e5cda315164ce))

## [2.0.8](https://github.com/xihan123/QDReadHook/compare/v2.0.7...v2.0.8) (2023-04-16)


### Features

* `896`、`900` 版本新增阅读页最后一页相关 ([e56e33f](https://github.com/xihan123/QDReadHook/commit/e56e33f9fc305f4370c21b3e02527be4bea0b2dd))
* 适配 `896`、`900` 版本 ([4666116](https://github.com/xihan123/QDReadHook/commit/4666116fb78120209a5485e9771147e631c951c5))


### Bug Fixes

* 可能出现的屏蔽失效 ([53fecd9](https://github.com/xihan123/QDReadHook/commit/53fecd9427b7882dbbdafb897efaf077a376aad2))


### Miscellaneous

* **master:** release 2.0.7 ([6952b49](https://github.com/xihan123/QDReadHook/commit/6952b493813436868d70f0997129a62cc87d9f00))
* **master:** release 2.0.7 ([4c83ba0](https://github.com/xihan123/QDReadHook/commit/4c83ba0d1d2205a90ef05dd458373130df4e2c25))
* 更新依赖库 ([8e395bc](https://github.com/xihan123/QDReadHook/commit/8e395bc0a6bf830e85245779028d6d5979a97259))
* 更新依赖库 ([a51d4ab](https://github.com/xihan123/QDReadHook/commit/a51d4ab136d1142ff9eecc224b80ac887b4f80b1))


### CI

* release.yml ([c2b07d6](https://github.com/xihan123/QDReadHook/commit/c2b07d6690cba39abff8649af6242a55d506a270))

## [2.0.7](https://github.com/xihan123/QDReadHook/compare/v2.0.7...v2.0.7) (2023-04-01)


### Miscellaneous

* **master:** release 2.0.7 ([4c83ba0](https://github.com/xihan123/QDReadHook/commit/4c83ba0d1d2205a90ef05dd458373130df4e2c25))

## [2.0.7](https://github.com/xihan123/QDReadHook/compare/v2.0.7...v2.0.7) (2023-04-01)


### Bug Fixes

* `890` 版本新"我"布局隐藏控件失效 ([3a31d74](https://github.com/xihan123/QDReadHook/commit/3a31d74aebd1ee03869803dd06f9ba8bc71d768f))


### Miscellaneous

* **master:** release 2.0.7 ([3f08b07](https://github.com/xihan123/QDReadHook/commit/3f08b079d2440825abeb4bfee97b8fdd3213964c))
* **master:** release 2.0.7 ([b35c104](https://github.com/xihan123/QDReadHook/commit/b35c10478159d77ee95332d80c14389cb5ae83eb))

## [2.0.7](https://github.com/xihan123/QDReadHook/compare/v2.0.7...v2.0.7) (2023-04-01)


### Miscellaneous

* **master:** release 2.0.7 ([b35c104](https://github.com/xihan123/QDReadHook/commit/b35c10478159d77ee95332d80c14389cb5ae83eb))

## [2.0.7](https://github.com/xihan123/QDReadHook/compare/v2.0.6...v2.0.7) (2023-04-01)


### Features

* 适配 `890` 版本 ([d5620a8](https://github.com/xihan123/QDReadHook/commit/d5620a818e4696323cfdb7051ed681a3dfe32032))


### Bug Fixes

* 配音导入对话框 ([e5a1140](https://github.com/xihan123/QDReadHook/commit/e5a11401bd32ba9886338f373518bf85f75b8889))


### Miscellaneous

* **master:** release 2.0.6 ([a0cb22d](https://github.com/xihan123/QDReadHook/commit/a0cb22d3c7a6e10292dac21bd667e800fcc57523))
* **master:** release 2.0.6 ([cfa49e2](https://github.com/xihan123/QDReadHook/commit/cfa49e263651a4610d954a016cd643adbaeb6d11))
* 更新依赖库 ([55ab25f](https://github.com/xihan123/QDReadHook/commit/55ab25f78da20f493f7ff26ac22c5aa544d8f73e))


### CI

* release.yml ([bced994](https://github.com/xihan123/QDReadHook/commit/bced9941c10b94e6235c1c9a0009a6160ce8de9f))

## [2.0.6](https://github.com/xihan123/QDReadHook/compare/v2.0.6...v2.0.6) (2023-03-20)


### Miscellaneous

* **master:** release 2.0.6 ([cfa49e2](https://github.com/xihan123/QDReadHook/commit/cfa49e263651a4610d954a016cd643adbaeb6d11))

## [2.0.6](https://github.com/xihan123/QDReadHook/compare/v2.0.5...v2.0.6) (2023-03-20)


### ⚠ BREAKING CHANGES

* 仅支持 `884`+ 版本拦截启动图页面的广告

### Features

* `884` 版本新增章评相关 ([a64057f](https://github.com/xihan123/QDReadHook/commit/a64057f55c76379435724ccb403eaf776a912008))
* `884` 版本章评导入导出配音以及一键导出表情包 ([ab70a69](https://github.com/xihan123/QDReadHook/commit/ab70a69410790291c1874a3d75bcf422c2b5df06))
* 仅支持 `884`+ 版本拦截启动图页面的广告 ([41de6e2](https://github.com/xihan123/QDReadHook/commit/41de6e21d123fa9b6e2a266450f4feaf18a5fecf))
* 适配 `884` 版本 ([4742bd6](https://github.com/xihan123/QDReadHook/commit/4742bd66bf6c249fe5848ce44fe4479d87e049b7))


### CI

* release.yml ([012ad09](https://github.com/xihan123/QDReadHook/commit/012ad09d9fb1e797e276ad597491caa785593690))


### Miscellaneous

* **master:** release 2.0.5 ([c7ae607](https://github.com/xihan123/QDReadHook/commit/c7ae6070df5619204387f087b8741b5dded8214d))
* 更新预览图 ([e9e4b8f](https://github.com/xihan123/QDReadHook/commit/e9e4b8fac213e289c39b2c0327f3168f9668fec2))

## [2.0.5](https://github.com/xihan123/QDReadHook/compare/v2.0.5...v2.0.5) (2023-03-03)


### Miscellaneous

* **master:** release 2.0.5 ([61a77a9](https://github.com/xihan123/QDReadHook/commit/61a77a990e8596abad8694d36cefc7c3d15ff877))

## [2.0.4](https://github.com/xihan123/QDReadHook/compare/v2.0.4...v2.0.4) (2023-02-23)


### Miscellaneous

* **master:** release 2.0.4 ([81327f9](https://github.com/xihan123/QDReadHook/commit/81327f91ae079a75a63ecfa4f5f5baafce1aea05))

## 2.0.4 (2023-02-23)


### Features

* **Intercept:** `872` 版本自选拦截初始化 ([fc74523](https://github.com/xihan123/QDReadHook/commit/fc74523ed05cfabe123c18efb24e4b50c1550298))
* **main:** `872` 版本新旧精选布局 ([922da5e](https://github.com/xihan123/QDReadHook/commit/922da5e69179f455beafe9370510cb224ba7afd7))
* **ViewHide:** `872` 版本自选精选-隐藏控件 ([d6554e9](https://github.com/xihan123/QDReadHook/commit/d6554e985c0673c56fd5379f185a1bffa1cf02e1))
* 最低支持版本改为Android 8 ([9a02bd8](https://github.com/xihan123/QDReadHook/commit/9a02bd80720120440b7077927f7c76582365a2c2))


### Miscellaneous

* Initial commit ([43517f5](https://github.com/xihan123/QDReadHook/commit/43517f5bffcb3a53c6970d514ee39da48d7a4d8f))


### CI

* automerge-action.yml ([6778db0](https://github.com/xihan123/QDReadHook/commit/6778db0e20b83e4bf40ed329b470bb66f217975d))
* build.yml ([a0e846c](https://github.com/xihan123/QDReadHook/commit/a0e846c5cdd5a09ab1d9ff0d64764ca5623058f9))
* release.yml ([6abc522](https://github.com/xihan123/QDReadHook/commit/6abc5221e4d5d626a5f12bdb21fcc59186b9f8ce))
* stale.yml ([6a4808c](https://github.com/xihan123/QDReadHook/commit/6a4808cacac71b8ad5e65406b05f6ce3d7df8964))

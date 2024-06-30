package cn.xihan.qdds

import android.app.Application
import cn.xihan.qdds.util.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.lazyModules

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2024/6/21 下午11:20
 * @介绍 :
 */
class MyApp : Application() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            lazyModules(appModule)
        }
    }
}
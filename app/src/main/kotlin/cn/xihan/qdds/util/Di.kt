@file:OptIn(KoinExperimentalAPI::class)

package cn.xihan.qdds.util

import cn.xihan.qdds.repository.RemoteRepository
import cn.xihan.qdds.service.MyService
import cn.xihan.qdds.service.MyServiceImpl
import cn.xihan.qdds.service.QdService
import cn.xihan.qdds.service.QdServiceImpl
import cn.xihan.qdds.ui.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.lazyModule

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2024/6/21 下午11:27
 * @介绍 :
 */
val appModule = lazyModule {
    singleOf(::provideHttpClient)
    singleOf(::MyServiceImpl) bind MyService::class
    singleOf(::QdServiceImpl) bind QdService::class
    singleOf(::RemoteRepository)
    viewModelOf(::MainViewModel)
}

private fun provideHttpClient(): HttpClient = HttpClient(OkHttp) {
    expectSuccess = true
    install(ContentNegotiation) {
        json(kJson)
    }
    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, request ->
            "Error Url: ${request.url}, ${exception.message}".loge()
            throw Throwable(exception.message ?: "Unknown error")
        }
    }
    install(ContentEncoding) {
        deflate(1.0F)
        gzip(0.9F)
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 10000
        connectTimeoutMillis = 10000
        socketTimeoutMillis = 10000
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                message.loge()
            }
        }
        level = LogLevel.ALL
    }
}






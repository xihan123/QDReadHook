@file:JvmName("Thread")

package cn.xihan.qdds.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.Closeable
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

typealias coroutineErrorListener = (throwable: Throwable) -> Unit

val Main: CoroutineDispatcher = Dispatchers.Main

val IO: CoroutineDispatcher = Dispatchers.IO

val Default: CoroutineDispatcher = Dispatchers.Default

val Unconfined: CoroutineDispatcher = Dispatchers.Unconfined

fun ioScope(errorHandler: coroutineErrorListener? = null) = SafeCoroutineScope(IO, errorHandler)

fun uiScope(errorHandler: coroutineErrorListener? = null) = SafeCoroutineScope(Main, errorHandler)

fun defaultScope(errorHandler: coroutineErrorListener? = null) =
    SafeCoroutineScope(Default, errorHandler)

fun customScope(dispatcher: CoroutineDispatcher, errorHandler: coroutineErrorListener? = null) =
    SafeCoroutineScope(dispatcher, errorHandler)

class SafeCoroutineScope(context: CoroutineContext, errorHandler: coroutineErrorListener? = null) :
    CoroutineScope, Closeable {

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + context + UncaughtCoroutineExceptionHandler(errorHandler)

    override fun close() {
        coroutineContext.cancelChildren()
    }
}

class UncaughtCoroutineExceptionHandler(private val errorHandler: coroutineErrorListener? = null) :
    CoroutineExceptionHandler, AbstractCoroutineContextElement(CoroutineExceptionHandler.Key) {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        exception.printStackTrace()

        errorHandler?.let {
            it(exception)
        }
    }
}

fun mainThread(block: suspend CoroutineScope.() -> Unit) = uiScope().launch(block = block)

fun <T> mainThread(block: suspend CoroutineScope.() -> T) = uiScope().async(block = block)

fun thread(block: suspend CoroutineScope.() -> Unit) = ioScope().launch(block = block)

fun <T> thread(block: suspend CoroutineScope.() -> T) = ioScope().async(block = block)

fun <T> mainThreadForResult(
    block: suspend CoroutineScope.() -> T, errorHandler: coroutineErrorListener? = null
): T {
    return runBlocking(Main + SupervisorJob() + UncaughtCoroutineExceptionHandler(errorHandler)) {
        mainThread(block).await()
    }
}

fun <T> threadForResult(
    errorHandler: coroutineErrorListener? = null,
    block: suspend () -> T
): T = runBlocking(IO + SupervisorJob() + UncaughtCoroutineExceptionHandler(errorHandler)) {
    block.invoke()
}

fun <T> threadForResultSuspend(
    errorHandler: coroutineErrorListener? = null,
    block: suspend CoroutineScope.() -> T
): T = runBlocking(IO + SupervisorJob() + UncaughtCoroutineExceptionHandler(errorHandler)) {
    thread(block).await()
}




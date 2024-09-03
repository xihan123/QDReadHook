package cn.xihan.qdds.service

import android.content.Context
import cn.xihan.qdds.model.BaseModel
import cn.xihan.qdds.model.BookModel
import cn.xihan.qdds.model.BookShelfModel
import cn.xihan.qdds.model.ReadingRecordModel
import cn.xihan.qdds.util.Option.optionEntity
import cn.xihan.qdds.util.Option.toast
import cn.xihan.qdds.util.loge
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.Closeable
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * @项目名 : QDReadHook
 * @作者 : MissYang
 * @创建时间 : 2024/8/15 00:47
 * @介绍 :
 */
interface CollectService {

    @Headers("Content-Type: application/json")
    @POST("api/v1/book")
    suspend fun collectBook(
        @Body bookModel: BookModel
    ): BaseModel<String?>

    @Headers("Content-Type: application/json")
    @POST("api/v1/reading_record")
    suspend fun collectReadingRecord(
        @Body readingRecordModel: ReadingRecordModel
    ): BaseModel<String?>

    @Headers("Content-Type: application/json")
    @POST("api/v1/bookshelf")
    suspend fun collectBookShelf(
        @Body bookShelfModel: BookShelfModel
    ): BaseModel<String?>

}

object Collect : KoinComponent {


    var userId: Long = 0L
    private val context: Context by inject()
    private val service: CollectService by inject()
    private var scopeRef: AtomicReference<Any> = AtomicReference()
    private val appGlobalScope: CoroutineScope
        get() {
            while (true) {
                val existing = scopeRef.get() as CoroutineScope?
                if (existing != null) {
                    return existing
                }
                val newScope = SafeCoroutineScope(Dispatchers.IO)
                if (scopeRef.compareAndSet(null, newScope)) {
                    return newScope
                }
            }
        }

    private class SafeCoroutineScope(context: CoroutineContext) : CoroutineScope, Closeable {
        override val coroutineContext: CoroutineContext =
            SupervisorJob() + context + UncaughtCoroutineExceptionHandler()

        override fun close() {
            coroutineContext.cancelChildren()
        }
    }

    fun toast(model: BaseModel<*>) {
        if (optionEntity.mainOption.enableCollectToast) {
            appGlobalScope.launch(Dispatchers.Main.immediate) {
                context.applicationContext.toast(model.message)
            }

        }
    }


    private suspend fun <T> collectService(
        block: suspend CollectService.() -> T,
    ): T = service.block()

    fun sendBook(
        bookId: Long,
        bookName: String,
        bookAuthor: String,
        bookStatus: String,
        bookDesc: String,
        bookWordCount: Long,
        bookCategory: String,
        bookSubCategory: String
    ) {
        appGlobalScope.launch {
            val bookModel = BookModel(
                bookId = bookId,
                title = bookName,
                author = bookAuthor,
                status = bookStatus,
                description = bookDesc,
                wordCount = bookWordCount,
                category = bookCategory,
                subcategory = bookSubCategory
            )
            collectService {
                val result = collectBook(bookModel)
                toast(result)
                "sendBook Result: $result".loge()
            }
        }
    }

    fun sendBookShelf(
        bookList: List<Long>,
    ) {
        if (userId == 0L) {
            return
        }
        appGlobalScope.launch {
            val bookShelfModel = BookShelfModel(
                userId = userId,
                bookList = bookList
            )
            collectService {
                val result = collectBookShelf(bookShelfModel)
                toast(result)
                "sendBookShelf Result: $result".loge()
            }
        }
    }

    fun sendReadingRecord(
        bookId: Long,
        startTime: Long,
        endTime: Long,
        duration: Long
    ) {
        if (userId == 0L) {
            return
        }
        appGlobalScope.launch {
            val readingRecordModel = ReadingRecordModel(
                bookId = bookId,
                userId = userId,
                startTime = startTime,
                endTime = endTime,
                duration = duration
            )
            collectService {
                val result = collectReadingRecord(readingRecordModel)
                toast(result)
                "sendReadingRecord Result: $result".loge()
            }
        }
    }


}

class UncaughtCoroutineExceptionHandler :
    CoroutineExceptionHandler, AbstractCoroutineContextElement(CoroutineExceptionHandler.Key) {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        exception.printStackTrace()
    }
}


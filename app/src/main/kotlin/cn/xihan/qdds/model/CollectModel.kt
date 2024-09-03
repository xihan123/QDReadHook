package cn.xihan.qdds.model


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class BookModel(
    @SerialName("author")
    var author: String = "",
    @SerialName("book_id")
    var bookId: Long = 0L,
    @SerialName("category")
    var category: String = "",
    @SerialName("description")
    var description: String = "",
    @SerialName("status")
    var status: String = "",
    @SerialName("subcategory")
    var subcategory: String = "",
    @SerialName("title")
    var title: String = "",
    @SerialName("word_count")
    var wordCount: Long = 0L
)

@Keep
@Serializable
data class BookShelfModel(
    @SerialName("book_list")
    var bookList: List<Long> = listOf(),
    @SerialName("user_id")
    var userId: Long = 0L
)

@Keep
@Serializable
data class ReadingRecordModel(
    @SerialName("book_id")
    var bookId: Long = 0L,
    @SerialName("duration")
    var duration: Long = 0L,
    @SerialName("end_time")
    var endTime: Long = 0L,
    @SerialName("start_time")
    var startTime: Long = 0L,
    @SerialName("user_id")
    var userId: Long = 0L
)
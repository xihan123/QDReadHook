package cn.xihan.qdds.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseModel<T>(
    @SerialName("Data") var `data`: T? = null,
    @SerialName("Message") var message: String = "",
    @SerialName("Result") var result: Int = 0
)

@Serializable
data class BaseDataModel(
    @SerialName("body") var body: String = "",
    @SerialName("method") var method: String = "",
    @SerialName("requestHeaders") var requestHeaders: Map<String, String> = emptyMap(),
    @SerialName("url") var url: String = ""
)

val BaseModel<*>.isSuccess
    get() = data != null || message.isBlank() || result == 0


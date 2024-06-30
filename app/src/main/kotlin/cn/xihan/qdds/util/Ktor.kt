package cn.xihan.qdds.util

import cn.xihan.qdds.model.BaseDataModel
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.requestApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType


suspend inline fun <reified T> BaseDataModel.customModelRequest(httpClient: HttpClient): ApiResponse<T> =
    httpClient.customRequest<T>(
        url = this@customModelRequest.url,
        method = this@customModelRequest.method,
        headers = this@customModelRequest.requestHeaders,
        body = this@customModelRequest.body
    )


suspend inline fun <reified T> HttpClient.customRequest(
    url: String,
    method: String = "GET",
    contentType: ContentType = ContentType.Application.Json,
    headers: Map<String, String> = emptyMap(),
    body: Any? = null
): ApiResponse<T> = requestApiResponse(url) {
    this.method = HttpMethod.parse(method)
    headers.takeIf { it.isNotEmpty() }?.forEach { (key, value) -> header(key, value) }
        ?: contentType(contentType)
    body?.let { setBody(it) }
}
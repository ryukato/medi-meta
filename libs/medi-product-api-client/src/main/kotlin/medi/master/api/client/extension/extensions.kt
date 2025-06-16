package medi.master.api.client.extension

import java.net.URLEncoder
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

fun Map<String, String>.toMultiValueMap(): MultiValueMap<String, String> {
    return LinkedMultiValueMap(this.entries.associate { (k, v) -> k to listOf(v) })
}

fun String.encodeQueryParameter(): String = URLEncoder.encode(this, "UTF-8")

fun String.toQueryParameterMap(): Map<String, String> {
    return this.split("&").associate {
        val (key, value) = it.split("=")
        key to value
    }
}

fun Map<String, Any>.excludeByKey(vararg keys: String): Map<String, Any> {
    return this.filterKeys { it !in keys }
}

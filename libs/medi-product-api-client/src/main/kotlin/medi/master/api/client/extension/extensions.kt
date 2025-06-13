package medi.master.api.client.extension

import java.net.URLEncoder
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

fun Map<String, String>.toMultiValueMap(): MultiValueMap<String, String> {
    return LinkedMultiValueMap(this.entries.associate { (k, v) -> k to listOf(v) })
}

fun String.encodeQueryParameter(): String = URLEncoder.encode(this, "UTF-8")

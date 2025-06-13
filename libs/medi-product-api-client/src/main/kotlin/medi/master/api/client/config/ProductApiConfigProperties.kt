package medi.master.api.client.config

import java.time.Duration
import medi.master.webclient.WebClientProperties
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gov-open-api.product")
data class ProductApiConfigProperties(
    val serviceKey: String,
    val host: String,
    val path: String,
    val defaultHeaders: Map<String, String> = emptyMap(),
    val responseTimeout: Duration = Duration.ofSeconds(10),
    val connectionTimeout: Duration = Duration.ofSeconds(10),
    val debugAll: Boolean = false
)

fun ProductApiConfigProperties.toWebClientProperties(): WebClientProperties {
    return WebClientProperties(
        host = host,
        basePath = path,
        defaultHeaders = defaultHeaders,
        connectionTimeout = connectionTimeout,
        responseTimeout = responseTimeout,
        debugAll = debugAll
    )
}

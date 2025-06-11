package medi.master.webclient

import java.time.Duration

data class WebClientProperties(
    val host: String,
    val basePath: String = "",
    val defaultHeaders: Map<String, String> = emptyMap(),
    val responseTimeout: Duration = Duration.ofSeconds(10),
    val connectionTimeout: Duration = Duration.ofSeconds(10),
    val debugAll: Boolean = false
)

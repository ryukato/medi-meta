package medi.master.webclient

import io.netty.channel.ChannelOption
import java.time.Duration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

object SpringWebClientFactory {
    fun createWebClient(
        properties: WebClientProperties
    ): WebClient {
        val host = properties.host
        val path = properties.basePath
        if (!host.startsWith("http") && !host.startsWith("https")) {
            throw IllegalArgumentException("Invalid base-url: does not starting with http or https")
        }
        val httpClientBuilder =
            buildHttpClient(
                responseTimeout = properties.responseTimeout,
                connectionTimeout = properties.connectionTimeout,
                debugAll = properties.debugAll
            )
        return buildWebClient(httpClientBuilder, host, path, properties.defaultHeaders)
    }

    private fun buildHttpClient(
        responseTimeout: Duration,
        connectionTimeout: Duration,
        debugAll: Boolean
    ): HttpClient {
        val httpClientBuilder = HttpClient.create()
            .responseTimeout(responseTimeout)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout.toMillis().toInt())
        if (debugAll) {
            httpClientBuilder.wiretap(true)
        }
        return httpClientBuilder
    }

    private fun buildWebClient(
        httpClientBuilder: HttpClient,
        host: String,
        path: String,
        defaultHeaders: Map<String, String>
    ): WebClient {
        val connector = ReactorClientHttpConnector(httpClientBuilder)
        return WebClient
            .builder()
            .baseUrl("$host/$path")
            .clientConnector(connector)
            .defaultHeaders { headers ->
                headers.setAll(defaultHeaders)
            }
            .build()
    }
}

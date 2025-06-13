package medi.master.api.client

import java.util.function.Predicate
import medi.master.api.client.config.ProductApiConfigProperties
import medi.master.api.client.config.toWebClientProperties
import medi.master.api.client.extension.toMultiValueMap
import medi.master.api.client.request.ProductQueryParameters
import medi.master.api.client.response.MedicalGenericResponse
import medi.master.api.client.response.MedicalItemDetailsResponse
import medi.master.api.client.response.MedicalItemResponse
import medi.master.webclient.SpringWebClientFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.core.publisher.Mono

class ProductApprovalApiClient(
    private val configProperties: ProductApiConfigProperties
) {
    private val baseUrl = "${configProperties.host}/${configProperties.path}"
    private var webClient: WebClient

    init {
        val factory = DefaultUriBuilderFactory(baseUrl)
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY

        webClient = SpringWebClientFactory.createWebClient(configProperties.toWebClientProperties())
            .mutate()
            .uriBuilderFactory(factory)
            .build()
    }

    suspend fun queryProductList(): MedicalGenericResponse<MedicalItemResponse>? {
        val queryParamMap = createDefaultQueryParameter().toMultiValueMap()
        val productQueryParameters = createDefaultPaginationParameter()
        queryParamMap.putAll(productQueryParameters.toQueryParameterMap())

        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(QUERY_PROD_LIST_PATH)
                    .queryParams(queryParamMap).build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _ ->
                Mono.error(RuntimeException("Client Error"))
            }
            .onStatus(isServiceUnavailable) { _ ->
                Mono.error(RuntimeException("Temporary Service Unavailable"))
            }
            .onStatus(is5xxServerError) { _ ->
                Mono.error(RuntimeException("Temporary Service Unavailable"))
            }
            .awaitBodyOrNull<MedicalGenericResponse<MedicalItemResponse>>()
        return response
    }

    suspend fun queryProductDetails(itemSeqNo: String): MedicalGenericResponse<MedicalItemDetailsResponse>? {
        val queryParamMap = createDefaultQueryParameter().toMultiValueMap()
        val productQueryParameters = createDefaultPaginationParameter()
        queryParamMap.putAll(productQueryParameters.toQueryParameterMap())
        queryParamMap.put("item_seq", listOf(itemSeqNo))

        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(QUERY_PROD_DETAIL_PATH)
                    .queryParams(queryParamMap).build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _ ->
                Mono.error(RuntimeException("Client Error"))
            }
            .onStatus(isServiceUnavailable) { _ ->
                Mono.error(RuntimeException("Temporary Service Unavailable"))
            }
            .onStatus(is5xxServerError) { _ ->
                Mono.error(RuntimeException("Temporary Service Unavailable"))
            }
            .awaitBodyOrNull<MedicalGenericResponse<MedicalItemDetailsResponse>>()
        return response
    }

    private fun createDefaultQueryParameter(): Map<String, String> {
        return mapOf(
            SERVICE_KEY_NAME to configProperties.serviceKey,
            RESPONSE_TYPE_NAME to JSON_RESPONSE_TYPE
        )
    }

    private fun createDefaultPaginationParameter(): ProductQueryParameters {
        return ProductQueryParameters(
            pageNo = 1,
            pageSize = 100,
        )
    }

    companion object {
        const val SERVICE_KEY_NAME = "serviceKey"
        const val RESPONSE_TYPE_NAME = "type"
        const val JSON_RESPONSE_TYPE = "json"
        const val QUERY_PROD_LIST_PATH = "/getDrugPrdtPrmsnInq06"
        const val QUERY_PROD_DETAIL_PATH = "/getDrugPrdtPrmsnDtlInq05"

        private val isServiceUnavailable =
            Predicate<HttpStatusCode> { statusCode -> statusCode.isSameCodeAs(HttpStatus.SERVICE_UNAVAILABLE) }

        private val is5xxServerError = Predicate<HttpStatusCode> { statusCode ->
            !statusCode.isSameCodeAs(HttpStatus.SERVICE_UNAVAILABLE) && statusCode.is5xxServerError
        }
    }
}

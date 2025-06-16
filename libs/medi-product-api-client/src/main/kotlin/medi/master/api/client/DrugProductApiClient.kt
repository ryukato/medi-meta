package medi.master.api.client

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.net.URI
import java.util.function.Predicate
import medi.master.api.client.config.ProductApiConfigProperties
import medi.master.api.client.config.toWebClientProperties
import medi.master.api.client.extension.excludeByKey
import medi.master.api.client.extension.toMultiValueMap
import medi.master.api.client.extension.toQueryParameterMap
import medi.master.api.client.request.ProductQueryParameters
import medi.master.api.client.response.BasePriceItemResponse
import medi.master.api.client.response.BundleItemResponse
import medi.master.api.client.response.ItemWrapper
import medi.master.api.client.response.MedicalGenericResponse
import medi.master.api.client.response.MedicalItemDetailsResponse
import medi.master.api.client.response.MedicalItemResponse
import medi.master.api.client.response.RetrieveStopSaleDetailsInformationResponse
import medi.master.api.client.response.RetrieveStopSaleInformationResponse
import medi.master.core.domain.common.excepion.InvalidRequestDataFetchException
import medi.master.core.domain.common.excepion.PermanentDataFetchException
import medi.master.core.domain.common.excepion.TemporaryDataFetchException
import medi.master.webclient.SpringWebClientFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.DefaultUriBuilderFactory
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono

class DrugProductApiClient(
    private val configProperties: ProductApiConfigProperties
) {
    private val baseUrl = configProperties.host
    private var webClient: WebClient

    private val xmlMapper = XmlMapper(JacksonXmlModule().apply {
        setDefaultUseWrapper(false)
    }).registerKotlinModule()

    init {
        val factory = DefaultUriBuilderFactory(baseUrl)
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY

        webClient = SpringWebClientFactory.createWebClient(configProperties.toWebClientProperties())
            .mutate()
            .uriBuilderFactory(factory)
            .build()
    }

    suspend fun queryProductList(): MedicalGenericResponse<MedicalItemResponse> {
        val queryParamMap = createQueryParameterMap()
        val response = webClient.get()
            .uri(createBuildUriFunction(QUERY_PROD_LIST_PATH, queryParamMap))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, responseErrorHandler())
            .awaitBody<MedicalGenericResponse<MedicalItemResponse>>()
        return response
    }

    suspend fun queryProductDetails(identifier: String): MedicalGenericResponse<MedicalItemDetailsResponse> {
        val queryParamMap = createQueryParameterMap(mapOf("item_seq" to identifier))

        val response = webClient.get()
            .uri(createBuildUriFunction(QUERY_PROD_DETAIL_PATH, queryParamMap))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, responseErrorHandler())
            .awaitBody<MedicalGenericResponse<MedicalItemDetailsResponse>>()
        return response
    }

    suspend fun queryBundleInformationByIdentifier(identifier: String): MedicalGenericResponse<ItemWrapper<BundleItemResponse>> {
        val queryParamMap = createQueryParameterMap(mapOf("trustIndutyCode" to identifier))
        val response = webClient.get()
            .uri(createBuildUriFunction(QUERY_PROD_BUNDLE_PATH, queryParamMap))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, responseErrorHandler())
            .awaitBody<MedicalGenericResponse<ItemWrapper<BundleItemResponse>>>()
        return response
    }

    suspend fun queryBasePriceInformationByEdiCode(ediCode: String): MedicalGenericResponse<ItemWrapper<BasePriceItemResponse>> {
        val queryParamMap = createQueryParameterMap(mapOf("mdsCd" to ediCode))
        val response = webClient.get()
            .uri(createBuildUriFunction(QUERY_PROD_BASE_PRICE_PATH, queryParamMap))
            .accept(MediaType.APPLICATION_XML)
            .retrieve()
            .onStatus(HttpStatusCode::isError, responseErrorHandler())
            .awaitBody<String>()
        val dataTypeRef = object : TypeReference<MedicalGenericResponse<ItemWrapper<BasePriceItemResponse>>>() {}
        return xmlMapper.readValue(response, dataTypeRef)
    }

    suspend fun queryRetrieveStopSaleList(): MedicalGenericResponse<ItemWrapper<RetrieveStopSaleInformationResponse>> {
        val queryParamMap = createQueryParameterMap()
        val response = webClient.get()
            .uri(createBuildUriFunction(QUERY_PROD_RETRIEVE_STOP_SALE_LIST_PATH, queryParamMap))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, responseErrorHandler())
            .awaitBody<MedicalGenericResponse<ItemWrapper<RetrieveStopSaleInformationResponse>>>()
        return response
    }

    suspend fun queryRetrieveStopSaleDetailsList(): MedicalGenericResponse<ItemWrapper<RetrieveStopSaleDetailsInformationResponse>> {
        val queryParamMap = createQueryParameterMap()
        val response = webClient.get()
            .uri(createBuildUriFunction(QUERY_PROD_RETRIEVE_STOP_SALE_DETAILS_LIST_PATH, queryParamMap))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, responseErrorHandler())
            .awaitBody<MedicalGenericResponse<ItemWrapper<RetrieveStopSaleDetailsInformationResponse>>>()
        return response
    }

    private fun createQueryParameterMap(
        additionalQueryParameters: Map<String, String> = emptyMap()
    ): MultiValueMap<String, String> {
        val queryParamMap = createDefaultQueryParameter().toMultiValueMap()
        val productQueryParameters = createDefaultPaginationParameter()
        queryParamMap.putAll(productQueryParameters.toQueryParameterMap())
        if (additionalQueryParameters.isNotEmpty()) {
            queryParamMap.putAll(additionalQueryParameters.toMultiValueMap())
        }
        return queryParamMap
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

    private fun createBuildUriFunction(
        path: String,
        queryParamMap: MultiValueMap<String, String>
    ): (UriBuilder) -> URI {
        return { uriBuilder: UriBuilder ->
            uriBuilder.path(path)
                .queryParams(queryParamMap)
                .build()
        }
    }

    private fun responseErrorHandler(): (ClientResponse) -> Mono<Throwable> {
        val functionName = Throwable().stackTrace[1].methodName
        return { response: ClientResponse ->
            if (response.statusCode().is4xxClientError) {
                Mono.error(
                    InvalidRequestDataFetchException(
                        apiPath = response.request().uri.path,
                        operationName = functionName,
                        queryParameters = response.request().uri.query.toQueryParameterMap()
                            .excludeByKey(SERVICE_KEY_NAME)
                    )
                )
            } else if (isServiceUnavailable.test(response.statusCode())) {
                Mono.error(
                    TemporaryDataFetchException(
                        apiPath = response.request().uri.path,
                        operationName = functionName,
                        queryParameters = response.request().uri.query.toQueryParameterMap()
                            .excludeByKey(SERVICE_KEY_NAME)
                    )
                )
            } else if (is5xxServerError.test(response.statusCode())) {
                Mono.error(
                    PermanentDataFetchException(
                        apiPath = response.request().uri.path,
                        operationName = functionName,
                        queryParameters = response.request().uri.query.toQueryParameterMap()
                            .excludeByKey(SERVICE_KEY_NAME)
                    )
                )
            } else {
                Mono.empty()
            }
        }
    }

    companion object {
        const val SERVICE_KEY_NAME = "serviceKey"
        const val RESPONSE_TYPE_NAME = "type"
        const val JSON_RESPONSE_TYPE = "json"
        const val QUERY_PROD_LIST_PATH = "/1471000/DrugPrdtPrmsnInfoService06/getDrugPrdtPrmsnInq06"
        const val QUERY_PROD_DETAIL_PATH = "/1471000/DrugPrdtPrmsnInfoService06/getDrugPrdtPrmsnDtlInq05"
        const val QUERY_PROD_BUNDLE_PATH = "/1471000/DrbBundleInfoService02/getDrbBundleList02"
        const val QUERY_PROD_BASE_PRICE_PATH = "/B551182/dgamtCrtrInfoService1.2/getDgamtList"
        const val QUERY_PROD_RETRIEVE_STOP_SALE_LIST_PATH =
            "/1471000/MdcinRtrvlSleStpgeInfoService04/getMdcinRtrvlSleStpgelList03"
        const val QUERY_PROD_RETRIEVE_STOP_SALE_DETAILS_LIST_PATH =
            "/1471000/MdcinRtrvlSleStpgeInfoService04/getMdcinRtrvlSleStpgeItem03"

        private val isServiceUnavailable =
            Predicate<HttpStatusCode> { statusCode -> statusCode.isSameCodeAs(HttpStatus.SERVICE_UNAVAILABLE) }

        private val is5xxServerError = Predicate<HttpStatusCode> { statusCode ->
            !statusCode.isSameCodeAs(HttpStatus.SERVICE_UNAVAILABLE) && statusCode.is5xxServerError
        }
    }
}

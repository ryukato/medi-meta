package medi.master.api.client

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.util.function.Predicate
import medi.master.api.client.config.ProductApiConfigProperties
import medi.master.api.client.config.toWebClientProperties
import medi.master.api.client.extension.toMultiValueMap
import medi.master.api.client.request.ProductQueryParameters
import medi.master.api.client.response.BasePriceItemResponse
import medi.master.api.client.response.BundleItemResponse
import medi.master.api.client.response.ItemWrapper
import medi.master.api.client.response.MedicalGenericResponse
import medi.master.api.client.response.MedicalItemDetailsResponse
import medi.master.api.client.response.MedicalItemResponse
import medi.master.api.client.response.RetrieveStopSaleInformationResponse
import medi.master.webclient.SpringWebClientFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.codec.xml.Jaxb2XmlDecoder
import org.springframework.http.codec.xml.Jaxb2XmlEncoder
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.core.publisher.Mono

class ProductApprovalApiClient(
    private val configProperties: ProductApiConfigProperties
) {
    private val baseUrl = configProperties.host
    private var webClient: WebClient
    private var webClientForXmlResponse: WebClient

    private val xmlMapper = XmlMapper(JacksonXmlModule().apply {
        setDefaultUseWrapper(false)
    }).registerKotlinModule()

    private val exchangeStrategies = ExchangeStrategies.builder()
        .codecs { configurer ->
            configurer.defaultCodecs().jaxb2Encoder(Jaxb2XmlEncoder())
            configurer.defaultCodecs().jaxb2Decoder(Jaxb2XmlDecoder())
        }
        .build()


    init {
        val factory = DefaultUriBuilderFactory(baseUrl)
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY

        webClient = SpringWebClientFactory.createWebClient(configProperties.toWebClientProperties())
            .mutate()
            .uriBuilderFactory(factory)
            .build()



        webClientForXmlResponse = SpringWebClientFactory.createWebClient(configProperties.toWebClientProperties())
            .mutate()
            .uriBuilderFactory(factory)
            .exchangeStrategies(exchangeStrategies)
            .build()
    }

    suspend fun queryProductList(): MedicalGenericResponse<MedicalItemResponse> {
        val queryParamMap = createDefaultQueryParameterMap()

        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(QUERY_PROD_LIST_PATH)
                    .queryParams(queryParamMap).build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, responseErrorHandler())
            .awaitBody<MedicalGenericResponse<MedicalItemResponse>>()
        return response
    }

    suspend fun queryProductDetails(identifier: String): MedicalGenericResponse<MedicalItemDetailsResponse> {
        val queryParamMap = createDefaultQueryParameterMap()
        queryParamMap.put("item_seq", listOf(identifier))

        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(QUERY_PROD_DETAIL_PATH)
                    .queryParams(queryParamMap).build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, responseErrorHandler())
            .awaitBody<MedicalGenericResponse<MedicalItemDetailsResponse>>()
        return response
    }

    suspend fun queryBundleInformationByIdentifier(identifier: String): MedicalGenericResponse<ItemWrapper<BundleItemResponse>> {
        val queryParamMap = createDefaultQueryParameterMap()
        queryParamMap.put("trustIndutyCode", listOf(identifier))
        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(QUERY_PROD_BUNDLE_PATH)
                    .queryParams(queryParamMap).build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, responseErrorHandler())
            .awaitBody<MedicalGenericResponse<ItemWrapper<BundleItemResponse>>>()
        return response
    }

    suspend fun queryBasePriceInformationByEdiCode(ediCode: String): MedicalGenericResponse<ItemWrapper<BasePriceItemResponse>> {
        val queryParamMap = createDefaultQueryParameterMap()
        queryParamMap.put("mdsCd", listOf(ediCode))
        val response = webClientForXmlResponse.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(QUERY_PROD_BASE_PRICE_PATH)
                    .queryParams(queryParamMap).build()
            }
            .accept(MediaType.APPLICATION_XML)
            .retrieve()
            .onStatus(HttpStatusCode::isError, responseErrorHandler())
            .awaitBody<String>()
        val dataTypeRef = object : TypeReference<MedicalGenericResponse<ItemWrapper<BasePriceItemResponse>>>() {}
        return xmlMapper.readValue(response, dataTypeRef)
    }

    suspend fun queryRetrieveStopSaleDetailsList(): MedicalGenericResponse<ItemWrapper<RetrieveStopSaleInformationResponse>> {
        val queryParamMap = createDefaultQueryParameterMap()
        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(QUERY_PROD_RETRIEVE_STOP_SALE_DETAILS_LIST_PATH)
                    .queryParams(queryParamMap).build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, responseErrorHandler())
            .awaitBody<MedicalGenericResponse<ItemWrapper<RetrieveStopSaleInformationResponse>>>()
        return response
    }

    private fun createDefaultQueryParameterMap(): MultiValueMap<String, String> {
        val queryParamMap = createDefaultQueryParameter().toMultiValueMap()
        val productQueryParameters = createDefaultPaginationParameter()
        queryParamMap.putAll(productQueryParameters.toQueryParameterMap())
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

    private fun responseErrorHandler(): (ClientResponse) -> Mono<Throwable> {
        val functionName = Throwable().stackTrace[1].methodName
        return { response: ClientResponse ->
            if (response.statusCode().is4xxClientError) {
                Mono.error(RuntimeException("Client Error - Fail to do $functionName"))
            } else if (isServiceUnavailable.test(response.statusCode())) {
                Mono.error(RuntimeException("Temporary Service Unavailable - Fail to do $functionName"))
            } else if (is5xxServerError.test(response.statusCode())) {
                Mono.error(RuntimeException("Temporary Service Unavailable - Fail to do $functionName"))
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
        const val QUERY_PROD_RETRIEVE_STOP_SALE_DETAILS_LIST_PATH =
            "/1471000/MdcinRtrvlSleStpgeInfoService04/getMdcinRtrvlSleStpgeItem03"

        private val isServiceUnavailable =
            Predicate<HttpStatusCode> { statusCode -> statusCode.isSameCodeAs(HttpStatus.SERVICE_UNAVAILABLE) }

        private val is5xxServerError = Predicate<HttpStatusCode> { statusCode ->
            !statusCode.isSameCodeAs(HttpStatus.SERVICE_UNAVAILABLE) && statusCode.is5xxServerError
        }
    }
}

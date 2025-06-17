@file:Suppress("SpellCheckingInspection")

package medi.master.api.client

import java.net.URLDecoder
import java.nio.charset.Charset
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_DUR_AGE_SPECIFIC_CONTRAINDICATIONS_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_DUR_CONCOMITANT_USE_CONTRAINDICATIONS_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_DUR_DOS_PRECAUTION_CONTRAINDICATIONS_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_DUR_DURATION_OF_USE_CAUTIONS_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_DUR_ELDERLY_PRECAUTIONS_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_DUR_PREGNANCY_CONTRAINDICATIONS_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_DUR_SPLITTING_PRECAUTION_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_DUR_THERAPEUTIC_DUPLICATION_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_PROD_BASE_PRICE_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_PROD_BUNDLE_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_PROD_DETAIL_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_PROD_LIST_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_PROD_PILL_IDENTIFICATION_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_PROD_RETRIEVE_STOP_SALE_DETAILS_LIST_PATH
import medi.master.api.client.DrugProductApiClient.Companion.QUERY_PROD_RETRIEVE_STOP_SALE_LIST_PATH
import medi.master.api.client.config.ProductApiConfigProperties
import medi.master.webclient.encodeQueryParameter
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType

class DrugProductApiClientTest {
    private val mockWebServer: MockWebServer = MockWebServer()

    @BeforeEach
    fun setUp() {
        mockWebServer.start()
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testQueryList() = runBlocking {
        val testSuccessResponse =
            ClassPathResource("test-data/medi-products-list.json").getContentAsString(Charset.defaultCharset())
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)
        val apiKey = "test".encodeQueryParameter()
        val host = "http://localhost:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryProductList()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_PROD_LIST_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        println(response)
    }

    @Test
    fun testQueryDetail() = runBlocking {
        val itemSeqNo = "195500005"
        val testSuccessResponse =
            ClassPathResource("test-data/single-medi-product-details.json").getContentAsString(Charset.defaultCharset())
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryProductDetails(itemSeqNo)
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_PROD_DETAIL_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertEquals(
            itemSeqNo,
            recordedRequest.requestUrl?.queryParameter("item_seq")
        )

        assertEquals(itemSeqNo, response.body.items.first().itemSequence)
    }

    @Test
    fun testQueryBundleInformation() = runBlocking {
        val identifier = "198000058"
        val testSuccessResponse =
            ClassPathResource("test-data/bundle-information-response.json").getContentAsString(Charset.defaultCharset())
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryBundleInformationByIdentifier(identifier)
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_PROD_BUNDLE_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertEquals(
            identifier,
            recordedRequest.requestUrl?.queryParameter("trustIndutyCode")
        )
        assertEquals(identifier, response.body.items.first().item.trustIndutyCode)
    }

    @Test
    fun testQueryBasePriceInformation() = runBlocking {
        val ediCode = "645101681"
        val testSuccessResponse =
            ClassPathResource("test-data/base-price-xml-response.xml").getContentAsString(Charset.defaultCharset())
        val mockServerResponse = createMockResponseForXml(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryBasePriceInformationByEdiCode(ediCode)
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_PROD_BASE_PRICE_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertEquals(
            ediCode,
            recordedRequest.requestUrl?.queryParameter("mdsCd")
        )

        assertEquals(ediCode, response.body.items.first().item.drugCode)
    }

    @Test
    fun testQueryRetrieveStopSaleList() = runBlocking {
        val testSuccessResponse =
            ClassPathResource("test-data/retrieve-stop-sale-list.json").getContentAsString(Charset.defaultCharset())
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryRetrieveStopSaleList()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_PROD_RETRIEVE_STOP_SALE_LIST_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertTrue(response.body.items.first().item.itemSequence.isNotBlank())
    }

    @Test
    fun testQueryRetrieveStopSaleDetailsList() = runBlocking {
        val testSuccessResponse =
            ClassPathResource("test-data/retrieve-stop-sale-details-list.json").getContentAsString(Charset.defaultCharset())
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryRetrieveStopSaleDetailsList()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_PROD_RETRIEVE_STOP_SALE_DETAILS_LIST_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertTrue(response.body.items.first().item.itemSequence.isNotBlank())
    }

    @Test
    fun testQueryRetrieveStopSaleDetailsListWithClientErrorResponse() = runBlocking {
        val testErrorResponse =
            ClassPathResource("test-data/default-error-response.json").getContentAsString(Charset.defaultCharset())
        val mockServerResponse = createClientErrorMockResponse(statusCode = 400, errorResponseBody = testErrorResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val error = assertThrows<RuntimeException> {
            apiClient.queryRetrieveStopSaleDetailsList()
        }
        assertTrue(error.message?.contains(QUERY_PROD_RETRIEVE_STOP_SALE_DETAILS_LIST_PATH) ?: false)
    }


    @Test
    fun testQueryRetrieveStopSaleDetailsListWithServerTemporaryErrorResponse() = runBlocking {
        val testErrorResponse =
            ClassPathResource("test-data/default-error-response.json").getContentAsString(Charset.defaultCharset())
        val mockServerResponse = createClientErrorMockResponse(statusCode = 503, errorResponseBody = testErrorResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val error = assertThrows<RuntimeException> {
            apiClient.queryRetrieveStopSaleDetailsList()
        }
        assertTrue(error.message?.contains(QUERY_PROD_RETRIEVE_STOP_SALE_DETAILS_LIST_PATH) ?: false)
    }

    @Test
    fun testQueryRetrieveStopSaleDetailsListWithServerErrorResponse() = runBlocking {
        val testErrorResponse =
            ClassPathResource("test-data/default-error-response.json").getContentAsString(Charset.defaultCharset())
        val mockServerResponse = createClientErrorMockResponse(statusCode = 500, errorResponseBody = testErrorResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val error = assertThrows<RuntimeException> {
            apiClient.queryRetrieveStopSaleDetailsList()
        }
        assertTrue(error.message?.contains(QUERY_PROD_RETRIEVE_STOP_SALE_DETAILS_LIST_PATH) ?: false)
    }


    @Test
    fun testQueryPillIdentification() = runBlocking {
        val identifier = "198000058"
        val testSuccessResponse =
            ClassPathResource("test-data/drug-pill-identification-response.json").getContentAsString(Charset.defaultCharset())
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryPillIdentification(identifier)
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_PROD_PILL_IDENTIFICATION_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertEquals(identifier, response.body.items.first().itemSequence)
    }

    @Test
    fun testQueryDurConcomitantUseContraindications() = runBlocking {
        val testSuccessResponse =
            ClassPathResource("test-data/dur-concomittant-use-contrainedication-response.json").getContentAsString(
                Charset.defaultCharset()
            )
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryDurConcomitantUseContraindications()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_DUR_CONCOMITANT_USE_CONTRAINDICATIONS_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertTrue(response.body.items.first().itemSequence.isNotBlank())

    }

    @Test
    fun testQueryDurPregnancyContraindications() = runBlocking {
        val testSuccessResponse =
            ClassPathResource("test-data/dur-pregnancy-contrainedication-response.json").getContentAsString(
                Charset.defaultCharset()
            )
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryDurPregnancyContraindications()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_DUR_PREGNANCY_CONTRAINDICATIONS_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertTrue(response.body.items.first().itemSequence.isNotBlank())
    }

    @Test
    fun testQueryDurDosagePrecautions() = runBlocking {
        val testSuccessResponse =
            ClassPathResource("test-data/dur-dos-precaution-response.json").getContentAsString(
                Charset.defaultCharset()
            )
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryDurDosagePrecautions()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_DUR_DOS_PRECAUTION_CONTRAINDICATIONS_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertTrue(response.body.items.first().itemSequence.isNotBlank())
    }

    @Test
    fun testQueryDurDurationOfUsePrecautions() = runBlocking {
        val testSuccessResponse =
            ClassPathResource("test-data/dur-duation-of-use-precautions-response.json").getContentAsString(
                Charset.defaultCharset()
            )
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryDurDurationOfUsePrecautions()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_DUR_DURATION_OF_USE_CAUTIONS_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertTrue(response.body.items.first().itemSequence.isNotBlank())
    }

    @Test
    fun testQueryElderlyPrecautions() = runBlocking {
        val testSuccessResponse =
            ClassPathResource("test-data/dur-elderly-precautions-response.json").getContentAsString(
                Charset.defaultCharset()
            )
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryElderlyPrecautions()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_DUR_ELDERLY_PRECAUTIONS_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertTrue(response.body.items.first().itemSequence.isNotBlank())
    }

    @Test
    fun testQueryAgeSpecificContraindications() = runBlocking {
        val testSuccessResponse =
            ClassPathResource("test-data/dur-age-specific-contraindications-response.json").getContentAsString(
                Charset.defaultCharset()
            )
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryDurAgeSpecificContraindications()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_DUR_AGE_SPECIFIC_CONTRAINDICATIONS_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertTrue(response.body.items.first().itemSequence.isNotBlank())
    }

    @Test
    fun testQueryDurTherapeuticDuplication() = runBlocking {
        val testSuccessResponse =
            ClassPathResource("test-data/dur-therapeutic-duplication-response.json").getContentAsString(
                Charset.defaultCharset()
            )
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryDurTherapeuticDuplication()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_DUR_THERAPEUTIC_DUPLICATION_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertTrue(response.body.items.first().itemSequence.isNotBlank())
    }

    //

    @Test
    fun testQueryDurSplittingPrecaution() = runBlocking {
        val testSuccessResponse =
            ClassPathResource("test-data/dur-splitting-precaution-response.json").getContentAsString(
                Charset.defaultCharset()
            )
        val mockServerResponse = createMockResponse(testSuccessResponse)
        mockWebServer.enqueue(mockServerResponse)

        val apiKey = "test".encodeQueryParameter()
        val host = "http://${mockWebServer.hostName}:${mockWebServer.port}"
        val apiClient = DrugProductApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryDurSplittingPrecaution()
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            QUERY_DUR_SPLITTING_PRECAUTION_PATH,
            recordedRequest.requestUrl?.pathSegments?.joinToString(prefix = "/", separator = "/")
        )
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertTrue(response.body.items.first().itemSequence.isNotBlank())
    }


    private fun createMockResponse(body: String): MockResponse {
        val mockServerResponse = MockResponse()
            .setBody(body)
            .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        return mockServerResponse
    }

    private fun createClientErrorMockResponse(
        statusCode: Int = 400,
        errorResponseBody: String = "",
    ): MockResponse {
        val mockServerResponse = MockResponse()
            .setResponseCode(statusCode)
            .setBody(errorResponseBody)
            .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        return mockServerResponse
    }

    private fun createMockResponseForXml(body: String): MockResponse {
        val mockServerResponse = MockResponse()
            .setBody(body)
            .addHeader("Content-Type", MediaType.APPLICATION_XML)
        return mockServerResponse
    }
}

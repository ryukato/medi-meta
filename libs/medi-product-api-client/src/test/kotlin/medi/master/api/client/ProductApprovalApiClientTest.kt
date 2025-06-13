package medi.master.api.client

import java.net.URLDecoder
import java.nio.charset.Charset
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import medi.master.api.client.config.ProductApiConfigProperties
import medi.master.webclient.encodeQueryParameter
import medi.master.webclient.toMultiValueMap
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType

class ProductApprovalApiClientTest {
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
        val apiClient = ProductApprovalApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryProductList()
        val recordedRequest = mockWebServer.takeRequest()
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
        val apiClient = ProductApprovalApiClient(
            configProperties = ProductApiConfigProperties(
                serviceKey = apiKey,
                host = host,
                path = ""
            )
        )

        val response = apiClient.queryProductDetails(itemSeqNo)
        val recordedRequest = mockWebServer.takeRequest()
        assertEquals(
            URLDecoder.decode(apiKey, Charset.defaultCharset()),
            recordedRequest.requestUrl?.queryParameter("serviceKey")
        )
        assertEquals(
            itemSeqNo,
            recordedRequest.requestUrl?.queryParameter("item_seq")
        )
        //
        println(response)
    }


    private fun createMockResponse(body: String): MockResponse {
        val mockServerResponse = MockResponse()
            .setBody(body)
            .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        return mockServerResponse
    }
}

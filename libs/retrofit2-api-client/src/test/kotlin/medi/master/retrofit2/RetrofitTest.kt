package medi.master.retrofit2

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.isError
import com.skydoves.sandwich.retrofit.serialization.deserializeErrorBody
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.http.GET
import retrofit2.http.Path

class RetrofitTest {
    private val mockWebServer: MockWebServer = MockWebServer()
    private val httpClient = HttpClientFactory().httpClient(true)

    @BeforeEach
    fun setUp() {
        mockWebServer.start()
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSuccessResponse() = runBlocking {
        val testResponseInJson = """
            {
                "isSuccess": true,
                "type": "TEST",
                "userId": "test-user1"
            }
        """.trimIndent()

        val mockServerResponse = MockResponse()
            .setResponseCode(200)
            .setBody(testResponseInJson)
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockServerResponse)

        val apiClient = RetrofitApiClientFactory().build(
            httpClient = httpClient,
            baseUrl = "http://localhost:${mockWebServer.port}",
            apiClientClass = TestApiClient::class.java,
            jacksonObjectMapper = jacksonObjectMapper()
        )
        assertNotNull(apiClient)

        val apiResponse = apiClient.getTestResponseInType()
        assertNotNull(apiResponse)
        val response = apiResponse.getOrNull()
        assertNotNull(response)
        assertEquals("test-user1", response!!.userId)
    }

    @Test
    fun testErrorResponse() = runBlocking {
        val testResponseInJson = """
            {
                "requestId": "test-request-id",
                "detail": "constraint violate request - constraint=EMAIL_VERIFICATION_COUNT, details=current count(1) is over limit(1) during 60 seconds",
                "instance": "/email-verifications",
                "status": 422,
                "timestamp": "2024-06-20T02:13:56.507190Z",
                "title": "Constraint violation - EMAIL_VERIFICATION_COUNT",
                "type": "/errors/violate-constraint"
            }
        """.trimIndent()
        val mockServerResponse = MockResponse()
            .setResponseCode(422)
            .setBody(testResponseInJson)
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockServerResponse)

        val apiClient = RetrofitApiClientFactory().build(
            httpClient = httpClient,
            baseUrl = "http://localhost:${mockWebServer.port}",
            apiClientClass = TestApiClient::class.java,
            jacksonObjectMapper()
        )
        assertNotNull(apiClient)

        val apiResponse = apiClient.getTestResponseInType()
        assertNotNull(apiResponse)
        assertTrue(apiResponse.isError)
        val errorResponse = apiResponse.deserializeErrorBody<SuccessResponse, ErrorResponse>()
        assertNotNull(errorResponse)
        assertEquals(422, errorResponse!!.status)
    }

    @Test
    fun testUserAccountResponse() = runBlocking {
        val testResponseInJson = """
            {
            "userName": "test"
        }
        """.trimIndent()
        val mockServerResponse = MockResponse()
            .setResponseCode(200)
            .setBody(testResponseInJson)
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockServerResponse)

        val apiClient = RetrofitApiClientFactory().build(
            httpClient = httpClient,
            baseUrl = "http://localhost:${mockWebServer.port}",
            apiClientClass = UserAccountApi::class.java,
            jacksonObjectMapper()
        )
        assertNotNull(apiClient)

        val apiResponse = apiClient.queryUserAccountByUserId("test-user1").getOrNull()
        assertNotNull(apiResponse)
    }
}

interface TestApiClient {
    @GET("/test/res")
    suspend fun getTestResponseInType(): ApiResponse<SuccessResponse>
}

data class SuccessResponse(
    val isSuccess: Boolean,
    val type: String,
    val userId: String
)

interface UserAccountApi {
    @GET("/users/{userId}/account")
    suspend fun queryUserAccountByUserId(@Path("userId") userId: String): ApiResponse<TestUserResponse>
}


@Serializable
data class ErrorResponse(
    val requestId: String,
    val status: Int,
    val type: String,
    val title: String?,
    val instance: String?,
    val detail: String?,
    val timestamp: String,
    val properties: Map<String, String>? = emptyMap()
)

data class TestUserResponse(
    val userName: String
)

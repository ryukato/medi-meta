package medi.master.retrofit2

import com.fasterxml.jackson.databind.ObjectMapper
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitApiClientFactory {
    fun <T> build(
        httpClient: OkHttpClient,
        baseUrl: String,
        apiClientClass: Class<T>,
        jacksonObjectMapper: ObjectMapper,
    ): T {
        val retrofit = retrofit(baseUrl, httpClient, jacksonObjectMapper)
        return retrofit.create(apiClientClass)
    }

    private fun retrofit(
        baseUrl: String,
        okHttp3Client: OkHttpClient,
        jacksonObjectMapper: ObjectMapper,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper))
            .client(okHttp3Client)
            .build()
    }


    private fun httpClient(
        enableLogging: Boolean,
    ): OkHttpClient {
        val logLevel =
            if (!enableLogging) HttpLoggingInterceptor.Level.NONE else HttpLoggingInterceptor.Level.BODY
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(logLevel)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor).build()
    }
}

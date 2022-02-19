package com.gaurav.nasa_pod.api

import androidx.lifecycle.LiveData
import com.gaurav.nasa_pod.BuildConfig
import com.gaurav.nasa_pod.api.calladapter.LiveDataCallAdapterFactory
import com.gaurav.nasa_pod.data.model.Apod
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {

    @GET("apod")
    fun getTodayContentLive(@Query("api_key") apiKey: String = BuildConfig.APOD_API_KEY): LiveData<ApiResponse<Apod>>

    @GET("apod")
    fun getContents(
        @Query("api_key") apiKey: String = BuildConfig.APOD_API_KEY,
        @Query("start_date") startDate: String,
    ): LiveData<ApiResponse<Array<Apod>>>

    @GET("apod")
    suspend fun getContentByDatePeriod(
        @Query("api_key") apiKey: String = BuildConfig.APOD_API_KEY,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): List<Apod>

    @GET("apod")
    suspend fun getTodayContent(@Query("api_key") apiKey: String = BuildConfig.APOD_API_KEY): Apod

    class ListingResponse(val data: List<Apod>)

    @GET("apod")
    fun getContents(
        @Query("api_key") apiKey: String = BuildConfig.APOD_API_KEY,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): LiveData<ApiResponse<Array<Apod>>>


    companion object {
        private const val BASE_URL = "https://api.nasa.gov/planetary/"

        fun create(): ApodApi {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL.toHttpUrlOrNull()!!)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApodApi::class.java)
        }

        fun createWithLiveDataAdapter(): ApodApi {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder().addInterceptor(logger).build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApodApi::class.java)
        }
    }
}
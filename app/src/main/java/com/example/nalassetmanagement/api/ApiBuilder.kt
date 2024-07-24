package com.example.nalassetmanagement.api

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiBuilder {
    private var mApiService: ApiService

    init {
        val interceptor = HttpLoggingInterceptor()
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mApiService = retrofit.create(ApiService::class.java)
    }

    companion object {
        private var mInstance: ApiBuilder? = null
        private val CONNECTION_TIME_OUT = 60000L
        private val READ_TIME_OUT = 60000L
        private val BASE_URL = "https://vuvanviet.com/"

        fun getInstance(): ApiBuilder? {
            synchronized(ApiBuilder::class.java) {
                if (mInstance == null) {
                    mInstance = ApiBuilder()
                }
            }
            return mInstance
        }
    }

    fun getApiService(): ApiService {
        return mApiService
    }
}

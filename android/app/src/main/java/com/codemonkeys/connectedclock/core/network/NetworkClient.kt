package com.codemonkeys.connectedclock.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {
    private val TIMEOUT_SEC = 4
    private lateinit var retrofit: Retrofit

    fun initRetrofitClient(baseUrl: String) {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build()
            ).build()
    }

    fun getRetrofitClient(): Retrofit {
        return retrofit
    }
}
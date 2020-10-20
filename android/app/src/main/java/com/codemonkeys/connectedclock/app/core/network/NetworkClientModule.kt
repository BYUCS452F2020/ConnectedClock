package com.codemonkeys.connectedclock.app.core.network

import com.codemonkeys.connectedclock.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object NetworkClientModule {
    private val TIMEOUT_SEC = 4

    @Provides
    @Singleton
    fun provideNetworkClient(): Retrofit {
        val baseUrl = BuildConfig.BASE_SERVER_URL
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build()
            ).build()
    }
}

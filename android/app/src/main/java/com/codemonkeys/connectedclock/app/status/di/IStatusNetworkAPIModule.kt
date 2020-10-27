package com.codemonkeys.connectedclock.app.status.di

import com.codemonkeys.connectedclock.app.status.model.IStatusNetworkAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@InstallIn(ApplicationComponent::class)
@Module
object IStatusNetworkAPIModule {
    @Provides
    fun providesIStatusNetworkAPIModule(retrofit: Retrofit): IStatusNetworkAPI {
        return retrofit.create<IStatusNetworkAPI>(IStatusNetworkAPI::class.java)
    }
}
package com.codemonkeys.connectedclock.app.zone.di

import com.codemonkeys.connectedclock.app.zone.model.IZoneNetworkAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@InstallIn(ApplicationComponent::class)
@Module
object IZoneNetworkAPIModule {
    @Provides
    fun provideIZoneNetworkAPIModule(retrofit: Retrofit): IZoneNetworkAPI {
        return retrofit.create<IZoneNetworkAPI>(IZoneNetworkAPI::class.java)
    }
}
package com.codemonkeys.connectedclock.app.group.di

import com.codemonkeys.connectedclock.app.group.model.IGroupNetworkAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@InstallIn(ApplicationComponent::class)
@Module
object IClockGroupNetworkAPIModule {
    @Provides
    fun providesIClockGroupNetworkAPIModule(retrofit: Retrofit): IGroupNetworkAPI {
        return retrofit.create<IGroupNetworkAPI>(IGroupNetworkAPI::class.java)
    }
}
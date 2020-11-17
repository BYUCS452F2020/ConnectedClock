package com.codemonkeys.connectedclock.app.zone.di

import com.codemonkeys.connectedclock.app.user.model.IUserNetworkAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@InstallIn(ApplicationComponent::class)
@Module
object IUserNetworkAPIModule {
    @Provides
    fun provideIZoneNetworkAPIModule(retrofit: Retrofit): IUserNetworkAPI {
        return retrofit.create<IUserNetworkAPI>(IUserNetworkAPI::class.java)
    }
}
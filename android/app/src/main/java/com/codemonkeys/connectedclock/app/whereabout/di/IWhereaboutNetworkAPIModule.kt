package com.codemonkeys.connectedclock.app.whereabout.di

import com.codemonkeys.connectedclock.app.whereabout.model.IWhereaboutNetworkAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@InstallIn(ApplicationComponent::class)
@Module
object IWhereaboutNetworkAPIModule {
    @Provides
    fun provideIWhereaboutNetworkAPIModule(retrofit: Retrofit): IWhereaboutNetworkAPI {
        return retrofit.create<IWhereaboutNetworkAPI>(IWhereaboutNetworkAPI::class.java)
    }
}
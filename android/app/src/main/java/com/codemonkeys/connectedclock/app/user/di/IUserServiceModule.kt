package com.codemonkeys.connectedclock.app.user.di

import com.codemonkeys.connectedclock.app.core.network.NetworkClientModule
import com.codemonkeys.connectedclock.app.user.model.ClientUserService
import com.codemonkeys.shared.user.IUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
object IUserServiceModule {
    @Provides
    fun provideIZoneServiceModule(): IUserService {
        return ClientUserService(NetworkClientModule.provideNetworkClient())
    }
}
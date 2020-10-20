package com.codemonkeys.connectedclock.app.status.di

import com.codemonkeys.connectedclock.app.core.network.NetworkClientModule
import com.codemonkeys.connectedclock.app.status.model.ClientStatusService
import com.codemonkeys.shared.status.IStatusService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

// The @InstallIn and @Module are used by Hilt for dependency injection.
// When we have an interface that is injected, we need to tell Hilt which concrete class to use for that
// interface. We do that by creating a Module class like below and returning a concrete instance
// of a class that implements the interface.
// https://developer.android.com/training/dependency-injection/hilt-android#hilt-modules
@InstallIn(ApplicationComponent::class)
@Module
object IStatusServiceModule {
    // Here we provide the concrete class ClientStatusService any time we need a IStatusService interface.
    @Provides
    fun provideIStatusServiceModule(): IStatusService {
        return ClientStatusService(NetworkClientModule.provideNetworkClient())
    }
}
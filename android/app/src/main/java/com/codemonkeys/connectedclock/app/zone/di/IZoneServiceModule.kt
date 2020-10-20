package com.codemonkeys.connectedclock.app.zone.di

import com.codemonkeys.connectedclock.app.core.network.NetworkClientModule
import com.codemonkeys.connectedclock.app.zone.model.ClientZoneService
import com.codemonkeys.shared.zone.IZoneService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

// The @InstallIn and @Module are used by Hilt for dependency injection.
// When we have an interface that is injected, we need to tell Hilt which concrete class to use for that
// interface. We do that by creating a Module class like below and returning a concrete instance
// of a class that implements the interface.
// https://developer.android.com/training/dependency-injection/hilt-android#hilt-modules
@InstallIn(ApplicationComponent::class)
@Module
object IZoneServiceModule {
    // Here we provide the concrete class ClientZoneService any time we need a IZoneService interface.
    @Provides
    fun provideIZoneServiceModule(): IZoneService {
        return ClientZoneService(NetworkClientModule.provideNetworkClient())
    }
}
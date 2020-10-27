package com.codemonkeys.connectedclock.app.geofence.di

import android.content.Context
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object GeofencingClientModule {
    // To construct GeofencingClient, we need Context, thankfully, Hilt provides it to us.
    // https://stackoverflow.com/questions/63072927/how-to-inject-application-context-in-a-repository-with-hilt
    @Singleton
    @Provides
    fun getGeofencingClient(@ApplicationContext context: Context): GeofencingClient {
        return LocationServices.getGeofencingClient(context)
    }
}
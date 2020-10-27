package com.codemonkeys.connectedclock.app

import android.app.Application
import com.codemonkeys.connectedclock.app.geofence.GeofenceManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ConnectedClockApplication : Application() {
    // We need to have a reference to geofenceManager here so that Hilt
    // creates the singleton instance of the class. We need an instance of the
    // class to exist so that it can update our geofences when our zones change.
    // We didn't have to include references to any other injected classes here
    // because they were referenced (and therefore created) in activities. However,
    // this class isn't referenced in any activities so it wouldn't be created otherwise.
    @Inject
    lateinit var geofenceManager: GeofenceManager
}
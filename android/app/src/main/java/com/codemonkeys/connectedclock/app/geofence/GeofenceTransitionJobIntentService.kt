package com.codemonkeys.connectedclock.app.geofence

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import dagger.hilt.android.AndroidEntryPoint

// Tutorials on Geofences:
// https://www.raywenderlich.com/7372-geofencing-api-tutorial-for-android
// https://developer.android.com/training/location/geofencing
@AndroidEntryPoint
class GeofenceTransitionJobIntentService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            Log.e(
                "ConnectedClockGeofence",
                GeofenceErrorMessages.getErrorString(this, geofencingEvent.errorCode)
            )
            return
        }
        handleEvent(geofencingEvent)
    }

    private fun handleEvent(event: GeofencingEvent) {
        val zoneId = if (event.triggeringGeofences.isNotEmpty() &&
            event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER
        ) {
            event.triggeringGeofences[0].requestId
        } else {
            ""
        }

        Log.d("ConnectedClockGeofence", "At zone '$zoneId'")
    }

    companion object {
        fun startJob(context: Context, intent: Intent) {
            enqueueWork(
                context,
                GeofenceTransitionJobIntentService::class.java,
                0,
                intent
            )
        }
    }
}
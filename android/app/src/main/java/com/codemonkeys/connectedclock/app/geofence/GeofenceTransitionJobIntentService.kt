package com.codemonkeys.connectedclock.app.geofence

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.codemonkeys.connectedclock.app.authorization.AuthorizationRepository
import com.codemonkeys.shared.whereabout.IWhereaboutService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// Tutorials on Geofences:
// https://www.raywenderlich.com/7372-geofencing-api-tutorial-for-android
// https://developer.android.com/training/location/geofencing
@AndroidEntryPoint
class GeofenceTransitionJobIntentService : JobIntentService() {

    @Inject
    lateinit var whereaboutService: IWhereaboutService

    @Inject
    lateinit var authorizationRepository: AuthorizationRepository

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
        val zoneID = if (event.triggeringGeofences.isNotEmpty() &&
            event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER
        ) {
            event.triggeringGeofences[0].requestId
        } else {
            null
        }

        Log.d("ConnectedClockGeofence", "At zone '$zoneID'")

        // TODO: Maybe I should observe this and only call it once we have the authToken??
        val authToken = authorizationRepository.getAuthToken().value
        if (authToken != null) {
            whereaboutService.updateUserWhereabout(authToken, zoneID)
        }
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
package com.codemonkeys.connectedclock.app.geofence

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.codemonkeys.connectedclock.app.zone.model.ZoneRepository
import com.codemonkeys.shared.zone.Zone
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

// Tutorials on Geofences:
// https://www.raywenderlich.com/7372-geofencing-api-tutorial-for-android
// https://developer.android.com/training/location/geofencing
@Singleton
class GeofenceManager @Inject constructor(
    private val zoneRepository: ZoneRepository,
    private val geofenceClient: GeofencingClient,
    @ApplicationContext private val context: Context
) {

    private val SEC_IN_MIN = 60
    private val MS_IN_SEC = 1000
    private val RESPONSIVENESS_MS = 5 * MS_IN_SEC
    private val pendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    init {
        zoneRepository.getZones().observeForever {zones ->
            this.clearGeofences()
            this.addGeofences(zones)
        }
    }

    fun clearGeofences() {
        geofenceClient.removeGeofences(pendingIntent)
    }


    fun addGeofences(zones: List<Zone>) {
        if (zones.isEmpty()) {
            return
        }

        val fences = zones.mapNotNull { buildFence(it) }
        val request = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(fences)
            .build()

        try {
            geofenceClient
                .addGeofences(request, pendingIntent)
                .addOnSuccessListener {
                    Log.d("ConnectedClockGeofence", "Successfully added ${fences.size} geofences")
                }.addOnFailureListener {
                    Log.e(
                        "ConnectedClockGeofence",
                        GeofenceErrorMessages.getErrorString(context, it)
                    )
                }
        }
        catch(e: SecurityException)
        {
            Log.e("ConnectedClockGeofence", e.message)
        }
    }

    private fun buildFence(zone: Zone): Geofence? {
        val lat = zone.lat
        val lng = zone.lng
        val radius = zone.radius?.toFloat()
        return if (lat != null && lng != null && radius != null) {
            Geofence.Builder()
                .setNotificationResponsiveness(RESPONSIVENESS_MS)
                .setCircularRegion(lat, lng, radius)
                .setRequestId(zone.zoneID)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build()
        } else {
            return null
        }
    }
}
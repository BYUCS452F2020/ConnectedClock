package com.codemonkeys.connectedclock.app.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint

// Tutorials on Geofences:
// https://www.raywenderlich.com/7372-geofencing-api-tutorial-for-android
// https://developer.android.com/training/location/geofencing
@AndroidEntryPoint
class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ConnectedClockGeofence", "Broadcast received!!!!")
        GeofenceTransitionJobIntentService.startJob(context, intent)
    }
}
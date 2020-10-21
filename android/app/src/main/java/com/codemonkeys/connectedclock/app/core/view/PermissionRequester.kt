package com.codemonkeys.connectedclock.app.core.view

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*
import kotlin.collections.HashMap

private val postPermissionLambdas = HashMap<Int, Pair<String, (Boolean)->Unit>>()

private fun getUniqueRequestCode(): Int {
    val uuidHash = UUID.randomUUID().hashCode()
    val requestCode = uuidHash and 0x0000FFFF // Request codes must only use lower 16 bits, so we mask it.
    return requestCode
}

fun requestPermissions(activity: Activity, permission: String, callback: (Boolean) -> Unit = {}) {
    val requestCode = getUniqueRequestCode()
    if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, arrayOf( permission), requestCode)
        postPermissionLambdas.put(requestCode, Pair(permission, callback))
    }
    else {
        checkPermissionAndCallLambda(activity, permission, callback)
    }
}

fun handleRequestedPermission(activity: Activity, requestCode: Int) {
    val postPermissionLambda = postPermissionLambdas[requestCode]
    postPermissionLambda?.let {
        val postPermission = postPermissionLambda.first
        val postLambda = postPermissionLambda.second
        checkPermissionAndCallLambda(activity, postPermission, postLambda)
        postPermissionLambdas.remove(requestCode)
    }
}

private fun checkPermissionAndCallLambda(activity: Activity, permission: String, lambda: (Boolean)->Unit) {
    val wasPermissionGranted = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    lambda(wasPermissionGranted)
}


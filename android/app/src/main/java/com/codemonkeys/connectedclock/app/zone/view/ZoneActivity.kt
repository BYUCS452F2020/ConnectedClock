package com.codemonkeys.connectedclock.app.zone.view

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import com.codemonkeys.connectedclock.R
import com.codemonkeys.connectedclock.app.core.view.handleRequestedPermission
import com.codemonkeys.connectedclock.app.zone.viewmodel.ZoneViewModel
import com.codemonkeys.shared.status.Status
import com.codemonkeys.shared.zone.Zone
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_zone.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.codemonkeys.connectedclock.app.core.view.requestPermissions as rqstPermissions

@AndroidEntryPoint
class ZoneActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel by viewModels<ZoneViewModel>()
    private lateinit var googleMap: GoogleMap
    // Normally, I wouldn't create any data in the UI like this, but here we need to create
    // the actual circles that appear in the UI. The circles need to handle being dragged around and highlighted, etc.
    // I also need to have a link between each circle and its associated zone.
    private val mapCircles: MutableMap<String, MapCircle> = HashMap()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_zone_options_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zone)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.activity_zone_zoneMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Here, we observe the LiveData from the ViewModels and update the UI whenever it is changed.
        this.viewModel.zones.observe(this) { this.updateZones(it) }
        this.viewModel.currentZoneID.observe(this) { this.updateCurrentZoneID(it) }
        this.viewModel.statuses.observe(this) { this.updateStatuses(it) }
        this.viewModel.currentStatusID.observe(this) { this.updateCurrentStatusID(it) }

        this.activity_zone_removeZoneButton.setOnClickListener { this.removeZoneButtonClicked() }
        this.activity_zone_zoneStatusSpinner.onItemSelectedListener =
            ZoneSpinnerItemSelectedListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.activity_zone_options_menu_settingsMenuItem -> {
//                val intent = Intent(this, SettingsActivity::class.java)
//                startActivity(intent)
                Toast.makeText(this, "Not implemented yet!", Toast.LENGTH_SHORT).show()
            }
            R.id.activity_zone_options_menu_saveMenuItem -> {
                val c = this
                // I am creating a coroutine which runs on the main UI thread.
                // This allows us to update the UI once we are done (Toasting message).
                // The viewModel.saveZonesAndStatuses() is suspendable and calls updateZones and updateStatuses
                // on the IO thread, so it doesn't block the main UI.
                // https://medium.com/androiddevelopers/coroutines-on-android-part-i-getting-the-background-3e0e54d20bb
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    viewModel.saveZonesAndStatuses()
                    Toast.makeText(c, "Saved Zones and Statuses", Toast.LENGTH_LONG).show()
                }
            }
            R.id.activity_zone_options_menu_refreshMenuItem -> {
                val c = this
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    viewModel.refreshZonesAndStatuses()
                    Toast.makeText(c, "Zones and Statuses Refreshed", Toast.LENGTH_LONG).show()
                }
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }


    inner class ZoneSpinnerItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            val statuses = this@ZoneActivity.viewModel.statuses.value
            statuses?.let {
                val status = statuses[position]
                viewModel.setCurrentZoneStatus(status.statusID)
            }
        }
    }

    private fun updateStatuses(statuses: MutableList<Status>?) {
        statuses?.let {
            val statusNames = this.viewModel.statuses.value?.map { it.statusName }
            statusNames?.let {
                val adapter = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_item,
                    statusNames
                )
                adapter.setDropDownViewResource(R.layout.zone_status_spinner_dialog)
                this.activity_zone_zoneStatusSpinner.adapter = adapter
            }
        }
    }

    private fun updateCurrentStatusID(currentStatusID: String?) {
        currentStatusID?.let {
            this.viewModel.statuses.value?.let {
                val position = it.indexOfFirst { status -> status.statusID == currentStatusID }
                this.activity_zone_zoneStatusSpinner.setSelection(position)
            }
        }
    }

    private fun removeZoneButtonClicked() {
        // We can call methods on the ViewModel to modify it.
        this.viewModel.removeCurrentZone()
    }

    private fun updateZones(zones: MutableList<Zone>?) {
        this.mapCircles.values.forEach { it.removeFromMap() }
        this.mapCircles.clear()
        zones?.forEach {
            val centerPosition = LatLng(it.lat ?: 0.0, it.lng ?: 0.0)
            val circle = MapCircle(it.zoneID, centerPosition, it.radius ?: 0.0)
            this.mapCircles.put(it.zoneID, circle)
        }
    }

    private fun updateCurrentZoneID(currentZoneID: String?) {
        this.mapCircles.values.forEach {
            it.setIsSelected(false)
        }
        if (currentZoneID != null) {
            this.mapCircles[currentZoneID]?.let {
                this.activity_zone_zoneCard.visibility = View.VISIBLE
                it.setIsSelected(true)
            }
        } else {
            this.activity_zone_zoneCard.visibility = View.GONE
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handleRequestedPermission(this, requestCode)
    }

    override fun onMapReady(gMap: GoogleMap) {
        this.googleMap = gMap

        with(this.googleMap) {
            uiSettings.isRotateGesturesEnabled = false
            uiSettings.isTiltGesturesEnabled = false
            uiSettings.isMapToolbarEnabled = false
            setPadding(12, 12, 12, 12)

            setOnMapLongClickListener { addZoneAtPoint(it) }

            setOnMapClickListener { viewModel.updateCurrentZoneID(null) }

            setOnCircleClickListener { viewModel.updateCurrentZoneID(mapCircles.values.firstOrNull { circle -> circle.circle == it }?.circleID) }

            setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                override fun onMarkerDragEnd(marker: Marker) {
                    onMarkerMove(marker, false, true)
                }

                override fun onMarkerDragStart(marker: Marker) {
                    onMarkerMove(marker, true, false)
                }

                override fun onMarkerDrag(marker: Marker) {
                    onMarkerMove(marker, false, false)
                }

            })
        }

        rqstPermissions(this, android.Manifest.permission.ACCESS_FINE_LOCATION) {
            if (it) {
                moveToCurrentLocation()
            }
        }
//        rqstPermissions(this, android.Manifest.permission.WAKE_LOCK)
//        rqstPermissions(this, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }

    fun addZoneAtPoint(point: LatLng) {
        val y = activity_zone_zoneMap.requireView().height * 1 / 2
        val x1 = activity_zone_zoneMap.requireView().width * 1 / 2
        val x2 = activity_zone_zoneMap.requireView().width * 7 / 8
        val p1 = this.googleMap.projection.fromScreenLocation(
            Point(y, x1)
        )
        val p2 = this.googleMap.projection.fromScreenLocation(
            Point(y, x2)
        )
        val radius = p1.distanceFrom(p2)

        this.viewModel.addZone(point.latitude, point.longitude, radius)
    }

    private fun onMarkerMove(marker: Marker, isMoveStart: Boolean, isMoveEnd: Boolean) {
        this.mapCircles.values.forEach {
            if (it.moveMarker(marker, isMoveStart, isMoveEnd))
                return
        }
        this.viewModel.updateCurrentZoneID(null)
    }

    private fun moveToCurrentLocation() {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (fineLocationPermission == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true

            val manager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val provider = manager.getBestProvider(Criteria(), true)
            provider?.let {
                val location = manager.getLastKnownLocation(provider)
                if (location != null) {
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(location.latitude, location.longitude),
                            16.0f
                        )
                    )
                }
            }
        }
    }

    private inner class MapCircle(
        val circleID: String,
        centerPosition: LatLng,
        radius: Double
    ) {
        val circle: Circle
        val centerMarker: Marker
        val radiusMarker: Marker

        private val SELECTED_STROKE_WIDTH = 20.0f;
        private val UNSELECTED_STROKE_WIDTH = 5.0f;
        private val SELECTED_STROKE_COLOR = 0xFFCC4444.toInt()
        private val UNSELECTED_STROKE_COLOR = 0xFF990000.toInt()
        private val SELECTED_FILL_COLOR = 0x99CC4444.toInt()
        private val UNSELECTED_FILL_COLOR = 0x99990000.toInt()

        init {
            this.circle = googleMap.addCircle(
                CircleOptions()
                    .center(centerPosition)
                    .radius(radius)
                    .strokeWidth(UNSELECTED_STROKE_WIDTH)
                    .strokeColor(UNSELECTED_STROKE_COLOR)
                    .fillColor(UNSELECTED_FILL_COLOR)
                    .clickable(true)
            )
            this.centerMarker = googleMap.addMarker(
                MarkerOptions()
                    .position(centerPosition)
                    .draggable(true)
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_circle))
            )
            this.radiusMarker = googleMap.addMarker(
                MarkerOptions()
                    .position(centerPosition.getPointAtDistance(radius))
                    .draggable(true)
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_circle))
            )
        }

        fun removeFromMap() {
            this.circle.remove()
            this.centerMarker.remove()
            this.radiusMarker.remove()
        }

        fun setIsSelected(isSelected: Boolean) {
            if (isSelected) {
                this.circle.strokeWidth = SELECTED_STROKE_WIDTH
                this.circle.strokeColor = SELECTED_STROKE_COLOR
                this.circle.fillColor = SELECTED_FILL_COLOR
            } else {
                this.circle.strokeWidth = UNSELECTED_STROKE_WIDTH
                this.circle.strokeColor = UNSELECTED_STROKE_COLOR
                this.circle.fillColor = UNSELECTED_FILL_COLOR
            }
        }


        fun update(centerPosition: LatLng?, radius: Double?) {
            centerPosition?.let {
                this.circle.center = it
            }
            radius?.let {
                this.circle.radius = it
            }
            this.centerMarker.position = this.circle.center
            this.radiusMarker.position =
                this.centerMarker.position.getPointAtDistance(this.circle.radius)
        }

        fun moveMarker(marker: Marker, isMoveStart: Boolean, isMoveEnd: Boolean): Boolean {
            val markerBelongsToZone = when (marker) {
                centerMarker -> {
                    update(centerMarker.position, null)
                    if (isMoveEnd) {
                        viewModel.moveCurrentZone(
                            centerMarker.position.latitude,
                            centerMarker.position.longitude
                        )
                    }
                    true
                }
                radiusMarker -> {
                    update(null, centerMarker.position.distanceFrom(radiusMarker.position))
                    if (isMoveEnd) {
                        viewModel.resizeCurrentZone(
                            centerMarker.position.distanceFrom(radiusMarker.position)
                        )
                    }
                    true
                }
                else -> {
                    false
                }
            }
            if (isMoveStart && markerBelongsToZone) {
                viewModel.updateCurrentZoneID(this.circleID)
            }
            return markerBelongsToZone
        }
    }
}

private fun LatLng.distanceFrom(other: LatLng): Double {
    val result = FloatArray(1)
    Location.distanceBetween(latitude, longitude, other.latitude, other.longitude, result)
    return result[0].toDouble()
}

private fun LatLng.getPointAtDistance(distance: Double): LatLng {
    val radiusOfEarth = 6371009.0
    val radiusAngle = (Math.toDegrees(distance / radiusOfEarth)
            / Math.cos(Math.toRadians(latitude)))
    return LatLng(latitude, longitude + radiusAngle)
}

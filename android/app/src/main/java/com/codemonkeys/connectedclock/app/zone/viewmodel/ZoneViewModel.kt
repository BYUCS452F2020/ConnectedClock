package com.codemonkeys.connectedclock.app.zone.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codemonkeys.connectedclock.app.status.model.StatusRepository
import com.codemonkeys.connectedclock.app.zone.model.ZoneRepository
import com.codemonkeys.shared.status.Status
import com.codemonkeys.shared.zone.Zone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class ZoneViewModel @ViewModelInject constructor(
    private val zoneRepository: ZoneRepository,
    private val statusRepository: StatusRepository
) : ViewModel() {

//    @Inject
//    lateinit var zoneRepository: ZoneRepository
//    @Inject
//    lateinit var statusRepository: StatusRepository

    // Some information must come from our repository because it is not transitory, it should
    // be persisted on the server at some point and should be there next time I open the app.
    // https://www.raywenderlich.com/10391019-livedata-tutorial-for-android-deep-dive#toc-anchor-002
    val zones: MutableLiveData<MutableList<Zone>> = zoneRepository.getZones()
    val statuses: MutableLiveData<MutableList<Status>> = statusRepository.getStatuses()

    // Other information, such as the currently selected zone isn't that important, so it doesn't
    // need to go in the repository. It can just reside in the viewModel.
    // Another way of thinking about it is the zones should be retained even when we go to a different
    // view, but currentZoneID can be forgotten when we leave.
    val currentZoneID = MutableLiveData<String?>()
    val currentStatusID = MutableLiveData<String>()

    private fun getCurrentZone(): Zone? {
        return this.zones.value?.let {
            it.find { it.zoneID == this.currentZoneID.value }
        }
    }

    fun updateCurrentZoneID(zoneID: String?) {
        this.currentZoneID.value = zoneID
        this.getCurrentZone()?.let {
            this.currentStatusID.value = it.statusID
        }
    }

    fun addZone(lat: Double, lng: Double, radius: Double) {
        this.zones.value?.let { zones ->
            this.statuses.value?.let { statuses ->
                statuses.firstOrNull()?.let {
                    val zone = Zone(UUID.randomUUID().toString(), lat, lng, radius, it.statusID)
                    zones.add(zone)

                    // This tells LiveData that we updated the collection, it doesn't know otherwise.
                    // https://stackoverflow.com/a/49022687/6634972
                    this.zones.value = this.zones.value

                    // We want to select the newly added zone
                    this.updateCurrentZoneID(zone.zoneID)
                }
            }
        }
    }

    fun removeCurrentZone() {
        this.getCurrentZone()?.let { zone ->
            this.zones.value?.let {
                it.remove(zone)
                this.zones.value = this.zones.value

                // We set the currentZoneID to null since we just deleted the current zone.
                this.updateCurrentZoneID(null)
            }
        }
    }

    fun moveCurrentZone(lat: Double, lng: Double) {
        this.getCurrentZone()?.let {
            it.lat = lat
            it.lng = lng
            // This tells LiveData that we updated the collection, it doesn't know otherwise.
            // https://stackoverflow.com/a/49022687/6634972
            this.zones.value = zones.value

            this.updateCurrentZoneID(it.zoneID)
        }
    }

    fun resizeCurrentZone(radius: Double) {
        this.getCurrentZone()?.let {
            it.radius = radius
            zones.value = zones.value

            this.updateCurrentZoneID(it.zoneID)
        }
    }

    fun setCurrentZoneStatus(statusID: String) {
        this.getCurrentZone()?.let {
            it.statusID = statusID
            this.currentStatusID.value = statusID
        }
    }

    suspend fun saveZonesAndStatuses() {
        withContext(Dispatchers.IO) {
            statusRepository.updateStatuses()
            zoneRepository.updateZones()
        }
    }

    suspend fun refreshZonesAndStatuses() {
        withContext(Dispatchers.IO) {
            statusRepository.refreshStatuses()
            zoneRepository.refreshZones()
        }
    }
}

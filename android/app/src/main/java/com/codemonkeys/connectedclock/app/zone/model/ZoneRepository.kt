package com.codemonkeys.connectedclock.app.zone.model

import androidx.lifecycle.MutableLiveData
import com.codemonkeys.connectedclock.app.authorization.AuthorizationRepository
import com.codemonkeys.shared.zone.IZoneService
import com.codemonkeys.shared.zone.Zone
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

// The repositories act as the single source of truth for their domains.
// They handle retrieving data from the server if it isn't present already, and
// they cache that data locally once it is retrieved.


// The @Singleton and @Inject annotations are used by Hilt for dependency injection.
// The @Singleton annotation tells Hilt that ZoneRepository should be a singleton, only one instance exists in the app.
// The @Inject annotation tells Hilt that this class can be injected into other classes.
//     It tells Hilt that anywhere we need to inject a ZoneRepository, use this class.
// https://codelabs.developers.google.com/codelabs/android-hilt/
// https://developer.android.com/training/dependency-injection/hilt-android#define-bindings
@Singleton
class ZoneRepository @Inject constructor(
    // Instead of hard-coding references to the service or the zone, we put parameters for them
    // in our constructor. The Hilt library handles dependency injection for us and gives us the
    // types that we need.
    private val zoneService: IZoneService,
    private val authorizationRepository: AuthorizationRepository
) {

    // We may need to use Android's Room library for SQLlite persistence later, but this works for now...
    // https://www.raywenderlich.com/10391019-livedata-tutorial-for-android-deep-dive#toc-anchor-002
    private var cachedZones: MutableLiveData<MutableList<Zone>>? = null

    // CoroutineScope tells us which threads to run async on (UI/IO/Computation),
    // It also gives the coroutine a lifecycle. So, for instance, if the async task
    // doesn't matter anyone once an activity is closed, it will cancel.
    // https://developer.android.com/topic/libraries/architecture/coroutines
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        // Not sure if this is correct, but I'm gonna try it...
        // I am observing the authToken from the authorizationRepository.
        // That way, any time the authToken gets updated, it will refresh the zones.
        authorizationRepository.getAuthToken().observeForever {
            refreshZones()
        }
    }

    fun getZones(): MutableLiveData<MutableList<Zone>> {
        cachedZones?.let {
            return it
        }

        val data = refreshZones()
        return data
    }

    fun refreshZones(): MutableLiveData<MutableList<Zone>> {

        if (cachedZones == null) {
            cachedZones = MutableLiveData()
        }
        val data = cachedZones as MutableLiveData
        val authToken = authorizationRepository.getAuthToken().value ?: ""

        // We return the live data immediately, but at some later point when the network
        // request finishes, it will set the data value to its result.
        //
        // I'm using Kotlin coroutines to make this part asynchronous.
        // https://www.raywenderlich.com/1423941-kotlin-coroutines-tutorial-for-android-getting-started
        // https://developer.android.com/kotlin/coroutines
        coroutineScope.launch {
            // https://blog.mindorks.com/livedata-setvalue-vs-postvalue-in-android
            data.postValue(zoneService.getZones(authToken) as MutableList<Zone>)
        }

        return data
    }

    // Update the server with the zone values we currently have in the repository
    suspend fun updateZones() {
        authorizationRepository.getAuthToken().value?.let { authToken ->
            cachedZones?.let { data ->
                data.value?.let { updatedZones ->
                    zoneService.updateZones(authToken, updatedZones)
                }
            }
        }
    }
}

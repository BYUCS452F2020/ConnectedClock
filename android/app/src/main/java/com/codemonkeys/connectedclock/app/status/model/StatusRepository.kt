package com.codemonkeys.connectedclock.app.status.model

import androidx.lifecycle.MutableLiveData
import com.codemonkeys.connectedclock.app.authorization.AuthorizationRepository
import com.codemonkeys.shared.status.IStatusService
import com.codemonkeys.shared.status.Status
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
class StatusRepository @Inject constructor(
    // Instead of hard-coding references to the service or the status, we put parameters for them
    // in our constructor. The Hilt library handles dependency injection for us and gives us the
    private val statusService: IStatusService,
    private val authorizationRepository: AuthorizationRepository
) {

    // We may need to use Android's Room library for SQLlite persistence later, but this works for now...
    // https://www.raywenderlich.com/10391019-livedata-tutorial-for-android-deep-dive#toc-anchor-002
    private var cachedStatuses: MutableLiveData<MutableList<Status>>? = null

    // CoroutineScope tells us which threads to run async on (UI/IO/Computation),
    // It also gives the coroutine a lifecycle. So, for instance, if the async task
    // doesn't matter anyone once an activity is closed, it will cancel.
    // https://developer.android.com/topic/libraries/architecture/coroutines
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        // This tries to refresh the statuses any time our authToken changes (we log in or out).
        // I'm not sure if it is the "correct" way, but it works well so far and keeps it up to date!
        authorizationRepository.getAuthToken().observeForever {
            refreshStatuses()
        }
    }

    fun getStatuses(): MutableLiveData<MutableList<Status>> {
        cachedStatuses?.let {
            return it
        }

        val data = refreshStatuses()
        return data
    }

    fun refreshStatuses(): MutableLiveData<MutableList<Status>> {
        if (cachedStatuses == null) {
            cachedStatuses = MutableLiveData()
        }

        val data = cachedStatuses as MutableLiveData
        val authToken = authorizationRepository.getAuthToken().value ?: ""

        coroutineScope.launch {
            data.postValue(statusService.getStatuses(authToken) as MutableList<Status>)
        }

        return data
    }

    // Update the server with the status values we currently have in the repository
    suspend fun updateStatuses() {
        authorizationRepository.getAuthToken().value?.let { authToken ->
            cachedStatuses?.let { data ->
                data.value?.let { updatedStatuses ->
                    statusService.updateStatuses(authToken, updatedStatuses)
                }
            }
        }
    }
}

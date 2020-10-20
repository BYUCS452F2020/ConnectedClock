package com.codemonkeys.connectedclock.app.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationRepository @Inject constructor() {

    private var cachedAuthToken: MutableLiveData<String>? = null

    fun getAuthToken(): LiveData<String> {
        cachedAuthToken?.let {
            return it
        }

        val data = MutableLiveData<String>()
        cachedAuthToken = data

        // TODO( "Using a hard-coded authToken here until we get login going...")
        // We should cache this authToken in app Preferences or something similar
        data.value = "cc95e238-1a1d-4238-b19b-0ef1dce406eb"

        return data
    }
}
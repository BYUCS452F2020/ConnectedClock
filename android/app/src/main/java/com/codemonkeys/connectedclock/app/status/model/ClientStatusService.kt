package com.codemonkeys.connectedclock.app.status.model

import com.codemonkeys.connectedclock.app.core.BaseClientService
import com.codemonkeys.connectedclock.app.zone.model.IZoneNetworkAPI
import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.status.IStatusService
import com.codemonkeys.shared.status.Status
import com.codemonkeys.shared.status.requests.UpdateStatusesRequest
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientStatusService @Inject constructor(
    private val networkAPI: IStatusNetworkAPI
) : IStatusService, @Inject BaseClientService() {

    // We don't want our ClientxxxxxxService to accept a request and return a response.
    // Instead, we want those details to be hidden from whatever calls these methods. The service
    // will be in charge of building the request and response.
    override fun getStatuses(authToken: String): List<Status> {
        val request = AuthorizedRequest(authToken)
        // The call to networkAPI.getZones() returns an apiCall that needs to be executed and then
        // checked to ensure we got a valid response. This executeApiCall function does that for us.
        val response = this.executeApiCall(networkAPI.getStatuses(request))
        return response.statuses
    }

    override fun updateStatuses(authToken: String, updatedStatuses: List<Status>) {
        val request = UpdateStatusesRequest(authToken, updatedStatuses)
        val response = this.executeApiCall(networkAPI.updateStatuses(request))
    }
}

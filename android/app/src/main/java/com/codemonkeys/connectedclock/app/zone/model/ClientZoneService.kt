package com.codemonkeys.connectedclock.app.zone.model

import com.codemonkeys.connectedclock.app.core.BaseClientService
import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.zone.IZoneService
import com.codemonkeys.shared.zone.Zone
import com.codemonkeys.shared.zone.requests.UpdateZonesRequest
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientZoneService @Inject constructor(
    private val networkAPI: IZoneNetworkAPI
) : IZoneService, BaseClientService() {

    // We don't want our ClientxxxxxxService to accept a request and return a response.
    // Instead, we want those details to be hidden from whatever calls these methods. The service
    // will be in charge of building the request and response.
    override fun getZones(authToken: String): List<Zone> {
        val request = AuthorizedRequest(authToken)
        // The call to networkAPI.getZones() returns an apiCall that needs to be executed and then
        // checked to ensure we got a valid response. This executeApiCall function does that for us.
        val response = this.executeApiCall(networkAPI.getZones(request))
        return response.zones
    }

    override fun updateZones(authToken: String, updatedZones: List<Zone>) {
        val request = UpdateZonesRequest(authToken, updatedZones)
        val response = this.executeApiCall(networkAPI.updateZones(request))
    }
}
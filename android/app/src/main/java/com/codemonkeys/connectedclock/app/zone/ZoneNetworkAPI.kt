package com.codemonkeys.connectedclock.app.zone

import com.codemonkeys.connectedclock.app.core.network.BaseNetworkAPI
import com.codemonkeys.connectedclock.app.core.network.NetworkClient
import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.zone.Zone
import com.codemonkeys.shared.zone.requests.UpdateZonesRequest

class ZoneNetworkAPI : BaseNetworkAPI() {
    companion object {
        val networkAPI = NetworkClient.getRetrofitClient().create<IZoneNetworkAPI>(IZoneNetworkAPI::class.java)
        val instance = ZoneNetworkAPI()
    }

    fun getZones(authToken: String) : List<Zone> {
        val request = AuthorizedRequest(authToken)
        val response = networkAPI.getZones(request)
        this.checkResponseForError(response)
        return response.zones
    }

    fun updateZones(authToken: String, updatedZones: List<Zone>) {
        val request = UpdateZonesRequest(authToken, updatedZones)
        val response = networkAPI.updateZones(request)
        this.checkResponseForError(response)
    }
}
package com.codemonkeys.connectedclock.zone

import com.codemonkeys.connectedclock.core.network.NetworkClient

class ZoneNetworkAPI {
    companion object {
        val networkAPI = NetworkClient.getRetrofitClient().create<Zone>(Zone::class.java)
        val instance = ZoneNetworkAPI()
    }

    fun getZones(authToken: String) {
        val request = AuthorizedRequest(authToken)
        return networkAPI.getZones(request)
    }

}
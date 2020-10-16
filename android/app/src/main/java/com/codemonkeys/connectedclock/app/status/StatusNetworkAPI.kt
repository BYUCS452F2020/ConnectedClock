package com.codemonkeys.connectedclock.app.status

import com.codemonkeys.connectedclock.app.core.network.BaseNetworkAPI
import com.codemonkeys.connectedclock.app.core.network.NetworkClient
import com.codemonkeys.connectedclock.app.zone.ZoneNetworkAPI
import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.status.Status
import com.codemonkeys.shared.status.requests.UpdateStatusesRequest

class StatusNetworkAPI : BaseNetworkAPI() {
    companion object {
        val networkAPI = NetworkClient.getRetrofitClient().create<IStatusNetworkAPI>(IStatusNetworkAPI::class.java)
        val instance = StatusNetworkAPI()
    }

    fun getStatuses(authToken: String): List<Status> {
        val request = AuthorizedRequest(authToken)
        val response = networkAPI.getStatuses(request)
        this.checkResponseForError(response)
        return response.statuses
    }

    fun updateStatuses(authToken: String, updatedStatuses: List<Status>) {
        val request = UpdateStatusesRequest(authToken, updatedStatuses)
        val response = networkAPI.updateStatuses(request)
        this.checkResponseForError(response)
    }
}
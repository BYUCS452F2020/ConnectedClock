package com.codemonkeys.connectedclock.app.whereabout

import com.codemonkeys.connectedclock.app.core.network.BaseNetworkAPI
import com.codemonkeys.connectedclock.app.core.network.NetworkClient
import com.codemonkeys.connectedclock.app.status.IStatusNetworkAPI
import com.codemonkeys.connectedclock.app.status.StatusNetworkAPI
import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.status.Status
import com.codemonkeys.shared.status.requests.UpdateStatusesRequest
import com.codemonkeys.shared.whereabout.IWhereaboutService
import com.codemonkeys.shared.whereabout.Whereabout
import com.codemonkeys.shared.whereabout.requests.UpdateUserWhereaboutRequest

class WhereaboutNetworkAPI : BaseNetworkAPI() {
    companion object {
        val networkAPI = NetworkClient.getRetrofitClient().create<IWhereaboutNetworkAPI>(
            IWhereaboutNetworkAPI::class.java)
        val instance = WhereaboutNetworkAPI()
    }

    fun getWhereabouts(authToken: String): List<Whereabout> {
        val request = AuthorizedRequest(authToken)
        val response = WhereaboutNetworkAPI.networkAPI.getWhereabouts(request)
        this.checkResponseForError(response)
        return response.whereabouts
    }

    fun updateUserWhereabout(authToken: String, currentZoneID: String?) {
        val request = UpdateUserWhereaboutRequest(authToken, currentZoneID)
        val response = WhereaboutNetworkAPI.networkAPI.updateUserWhereabout(request)
        this.checkResponseForError(response)
    }
}
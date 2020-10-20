package com.codemonkeys.connectedclock.app.whereabout.model

import com.codemonkeys.connectedclock.app.core.BaseClientService
import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.whereabout.Whereabout
import com.codemonkeys.shared.whereabout.requests.UpdateUserWhereaboutRequest

class WhereaboutNetworkAPI : BaseClientService() {
//    companion object {
//        val networkAPI = NetworkClient.getRetrofitClient().create<IWhereaboutNetworkAPI>(
//            IWhereaboutNetworkAPI::class.java)
//        val instance =
//            WhereaboutNetworkAPI()
//    }
//
//    fun getWhereabouts(authToken: String): List<Whereabout> {
//        val request = AuthorizedRequest(authToken)
//        val response = networkAPI.getWhereabouts(request)
//        this.checkResponseForError(response)
//        return response.whereabouts
//    }
//
//    fun updateUserWhereabout(authToken: String, currentZoneID: String?) {
//        val request = UpdateUserWhereaboutRequest(authToken, currentZoneID)
//        val response = networkAPI.updateUserWhereabout(request)
//        this.checkResponseForError(response)
//    }
}
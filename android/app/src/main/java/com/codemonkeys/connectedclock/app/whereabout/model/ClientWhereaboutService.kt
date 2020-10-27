package com.codemonkeys.connectedclock.app.whereabout.model

import com.codemonkeys.connectedclock.app.core.BaseClientService
import com.codemonkeys.connectedclock.app.zone.model.IZoneNetworkAPI
import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.whereabout.IWhereaboutService
import com.codemonkeys.shared.whereabout.Whereabout
import com.codemonkeys.shared.whereabout.requests.UpdateUserWhereaboutRequest
import com.codemonkeys.shared.zone.IZoneService
import com.codemonkeys.shared.zone.Zone
import com.codemonkeys.shared.zone.requests.UpdateZonesRequest
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientWhereaboutService @Inject constructor(
    private val networkAPI: IWhereaboutNetworkAPI
) : IWhereaboutService, BaseClientService() {

    override fun getWhereabouts(authToken: String): List<Whereabout> {
        val request = AuthorizedRequest(authToken)
        val response = this.executeApiCall(networkAPI.getWhereabouts(request))
        return response.whereabouts
    }

    override fun updateUserWhereabout(authToken: String, currentZoneID: String?) {
        val request = UpdateUserWhereaboutRequest(authToken, currentZoneID)
        val response = this.executeApiCall(networkAPI.updateUserWhereabout(request))
    }
}

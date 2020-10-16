package com.codemonkeys.connectedclock.app.whereabout

import com.codemonkeys.shared.whereabout.IWhereaboutService
import com.codemonkeys.shared.whereabout.Whereabout

class ClientWhereaboutService : IWhereaboutService {
    private val whereaboutNetworkAPI = WhereaboutNetworkAPI.instance

    override fun getWhereabouts(authToken: String): List<Whereabout> {
        return whereaboutNetworkAPI.getWhereabouts(authToken)
    }

    override fun updateUserWhereabout(authToken: String, currentZoneID: String?) {
        return whereaboutNetworkAPI.updateUserWhereabout(authToken, currentZoneID)
    }
}
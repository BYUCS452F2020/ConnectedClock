package com.codemonkeys.connectedclock.app.status

import com.codemonkeys.connectedclock.app.zone.ZoneNetworkAPI
import com.codemonkeys.shared.status.IStatusService
import com.codemonkeys.shared.status.Status

class ClientStatusService : IStatusService {
    val statusNetworkAPI = StatusNetworkAPI.instance

    override fun getStatuses(authToken: String): List<Status> {
        return statusNetworkAPI.getStatuses(authToken)
    }

    override fun updateStatuses(authToken: String, updatedStatuses: List<Status>) {
        statusNetworkAPI.updateStatuses(authToken, updatedStatuses)
    }
}
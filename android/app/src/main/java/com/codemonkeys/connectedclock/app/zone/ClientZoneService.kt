package com.codemonkeys.connectedclock.app.zone

import com.codemonkeys.shared.zone.IZoneService
import com.codemonkeys.shared.zone.Zone

class ClientZoneService : IZoneService{
    val zoneNetworkAPI = ZoneNetworkAPI.instance

    override fun getZones(authToken: String): List<Zone> {
        return zoneNetworkAPI.getZones(authToken)
    }

    override fun updateZones(authToken: String, updatedZones: List<Zone>) {
        return zoneNetworkAPI.updateZones(authToken, updatedZones)
    }
}
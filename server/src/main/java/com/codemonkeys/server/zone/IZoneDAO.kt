package com.codemonkeys.server.zone

import com.codemonkeys.shared.zone.Zone

interface IZoneDAO {
    fun getZones(groupID: String): List<Zone>

    fun updateZones(groupID: String, updatedZones: List<Zone>)
}
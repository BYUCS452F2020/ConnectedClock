package com.codemonkeys.server.zone

import com.codemonkeys.server.core.dao.BaseDynamoDAO
import com.codemonkeys.shared.zone.Zone

class ZoneDynamoDAO : BaseDynamoDAO(), IZoneDAO {
    override fun getZones(groupID: String): List<Zone> {
        TODO("Not yet implemented")
    }

    override fun updateZones(groupID: String, updatedZones: List<Zone>) {
        TODO("Not yet implemented")
    }

}

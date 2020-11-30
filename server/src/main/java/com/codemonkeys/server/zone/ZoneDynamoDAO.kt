package com.codemonkeys.server.zone

import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.codemonkeys.server.core.dao.BaseDynamoDAO
import com.codemonkeys.shared.zone.Zone

class ZoneDynamoDAO : BaseDynamoDAO(), IZoneDAO {
    override fun getZones(groupID: String): List<Zone> {
        this.useDynamoConnection {
            val table = it.getTable(TABLE_NAME)

            val spec = QuerySpec()
                .withKeyConditionExpression("PK = :pk and begins_with(SK, :sk)")
                .withValueMap(
                    ValueMap()
                        .withString(":pk", "GROUP-$groupID")
                        .withString(":sk", "ZONE")
                )

            val results = table.query(spec)
            return this.getQueryResults(Zone::class.java, results)
        }
        return emptyList()
    }

    override fun updateZones(groupID: String, updatedZones: List<Zone>) {
        val zonesToDelete = this.getZones(groupID)
        this.updateItems(Zone::class.java, TABLE_NAME, zonesToDelete, updatedZones, "GROUP-$groupID") {
            "ZONE-${it.zoneID}"
        }
    }

}

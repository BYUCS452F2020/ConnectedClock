package com.codemonkeys.server.whereabout

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.codemonkeys.server.core.dao.BaseDynamoDAO
import com.codemonkeys.server.status.StatusDynamoDAO
import com.codemonkeys.server.user.UserDynamoDAO
import com.codemonkeys.server.zone.ZoneDynamoDAO
import com.codemonkeys.shared.status.Status
import com.codemonkeys.shared.user.User
import com.codemonkeys.shared.whereabout.Whereabout
import com.codemonkeys.shared.zone.Zone

class WhereaboutDynamoDAO : BaseDynamoDAO(), IWhereaboutDAO {

    private fun getWhereabouts(users: List<User>, zones: List<Zone>, statuses: List<Status>): List<Whereabout> {
        val whereabouts = mutableListOf<Whereabout>()
        users.forEach { user ->
            val whereabout = Whereabout(user.userID, user.userName, user.clockHandIndex)
            if (user.currentZoneID != null) {
                val zone = zones.find { it.zoneID == user.currentZoneID }
                zone?.let {
                    val status = statuses.find { it.statusID == zone.statusID }
                    status?.let {
                        whereabout.clockHandAngle = it.clockHandAngle.toLong()
                        whereabout.currentStatusID = it.statusID
                    }
                }
            }
            whereabouts.add(whereabout)
        }
        return whereabouts
    }

    override fun getWhereabouts(groupID: String): List<Whereabout> {
        val statuses = StatusDynamoDAO().getStatuses(groupID)
        val zones = ZoneDynamoDAO().getZones(groupID)
        this.useDynamoConnection {
            val table = it.getTable(TABLE_NAME)

            val userSpec = QuerySpec()
                .withKeyConditionExpression("PK = :pk and begins_with(SK, :sk)")
                .withValueMap(
                    ValueMap()
                        .withString(":pk", "GROUP-$groupID")
                        .withString(":sk", "USER")
                )

            val results = table.query(userSpec)
            val users = this.getQueryResults(User::class.java, results)

            return getWhereabouts(users, zones, statuses)
        }
        return emptyList()
    }

    override fun updateUserWhereabout(userID: String, currentZoneID: String?) {
        this.useDynamoConnection {
            val table = it.getTable(TABLE_NAME)
            val user = UserDynamoDAO().getUser(userID)
            user?.let {

                val values = ValueMap()
                if (currentZoneID == null) {
                    values.withNull(":c")
                } else {
                    values.withString(":c", currentZoneID)
                }

                val updateSpec = UpdateItemSpec()
                    .withPrimaryKey("PK", "GROUP-${user.groupID}", "SK", "USER-$userID")
                    .withUpdateExpression("set currentZoneID = :c")
                    .withValueMap(values)

                table.updateItem(updateSpec)
            }
        }
    }
}
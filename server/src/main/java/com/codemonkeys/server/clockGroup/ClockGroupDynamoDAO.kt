package com.codemonkeys.server.clockGroup

import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.codemonkeys.server.core.dao.BaseDynamoDAO
import com.codemonkeys.shared.clockGroup.ClockGroup
import com.codemonkeys.shared.zone.Zone

class ClockGroupDynamoDAO : BaseDynamoDAO(), IClockGroupDAO {
    override fun createNewGroup(singleGroup: ClockGroup) {
        this.useDynamoConnection {
            val table = it.getTable(TABLE_NAME)
            val spec = PutItemSpec()
                .withItem(
                    Item()
                        .withPrimaryKey("PK", "GROUP-${singleGroup.groupID}", "SK", "GROUP")
                        .withString("groupID", singleGroup.groupID)
                        .withString("groupName", singleGroup.groupName)
                        .withString("groupPassword", singleGroup.groupPassword)
                )
            table.putItem(spec)
        }
    }

    override fun updateGroupInUser(groupID: String, userID: String) {
        TODO("Not yet implemented")
    }

    override fun getClockGroup(groupID: String): ClockGroup {
        this.useDynamoConnection {
            val table = it.getTable(TABLE_NAME)

            val spec = QuerySpec()
                .withKeyConditionExpression("PK = :pk and SK = :sk")
                .withValueMap(
                    ValueMap()
                        .withString(":pk", "GROUP-$groupID")
                        .withString(":sk", "GROUP")
                )

            val results = table.query(spec)
            val groups = this.getQueryResults(ClockGroup::class.java, results)

            if (groups.isNotEmpty()) {
                return groups[0]
            }
        }
        return ClockGroup(
            groupID = groupID,
            groupPassword = "",
            groupName = ""
        )
    }

    override fun getGroupIDViaUser(userID: String): String {
        TODO("Not yet implemented")
    }

    override fun getUserIDViaUserName(userName: String): String {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(groupID: String) {
        TODO("Not yet implemented")
    }

    override fun getGroupIDViaGroupName(groupName: String): String {
        TODO("Not yet implemented")
    }

    override fun setAuthTokenTable(authToken: String, groupID: String) {
        TODO("Not yet implemented")
    }
}
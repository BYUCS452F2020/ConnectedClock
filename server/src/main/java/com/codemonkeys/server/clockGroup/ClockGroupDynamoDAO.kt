package com.codemonkeys.server.clockGroup

import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec
import com.codemonkeys.server.core.dao.BaseDynamoDAO
import com.codemonkeys.shared.clockGroup.ClockGroup


class ClockGroupDynamoDAO : BaseDynamoDAO(), IClockGroupDAO {
    override fun createNewGroup(singleGroup: ClockGroup) {
        super.useDynamoConnection {
            val table = it.getTable(TABLE_NAME)
            val groupItem = Item()
            groupItem.withPrimaryKey("PK", "GROUP-" + singleGroup.groupID)
                     .withString("groupID", singleGroup.groupID)
                     .withString("groupName", singleGroup.groupName)
                     .withString("groupPassword", singleGroup.groupPassword)

            table.putItem(groupItem)
        }
    }

    override fun getClockGroup(groupID: String): ClockGroup {
        super.useDynamoConnection {
            val table = it.getTable(TABLE_NAME)

            val spec = GetItemSpec()
                .withPrimaryKey("PK", "GROUP-" + groupID)
                .withProjectionExpression("groupID, groupName, groupPassword")
                .withConsistentRead(true)
            val groupItem = table.getItem(spec)
            System.out.println(groupItem.toJSONPretty());

            return this.getItemResult(ClockGroup::class.java, groupItem)
        }
        return ClockGroup()
    }

    override fun updateGroupInUser(groupID: String, userID: String) {
        TODO("Not yet implemented")
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
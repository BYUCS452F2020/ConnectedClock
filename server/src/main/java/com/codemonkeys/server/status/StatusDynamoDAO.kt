package com.codemonkeys.server.status

import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.codemonkeys.server.core.dao.BaseDynamoDAO
import com.codemonkeys.shared.status.Status

class StatusDynamoDAO : BaseDynamoDAO(), IStatusDAO {
    override fun getStatuses(groupID: String): List<Status> {
        this.useDynamoConnection {
            val table = it.getTable(TABLE_NAME)

            val spec = QuerySpec()
                .withKeyConditionExpression("PK = :pk and begins_with(SK, :sk)")
                .withValueMap(
                    ValueMap()
                        .withString(":pk", "GROUP-$groupID")
                        .withString(":sk", "STATUS")
                )

            val results = table.query(spec)
            return this.getQueryResults(Status::class.java, results)
        }
        return emptyList()
    }

    override fun updateStatuses(groupID: String, updatedStatuses: List<Status>) {
        val statusesToDelete = this.getStatuses(groupID)
        this.updateItems(Status::class.java, TABLE_NAME, statusesToDelete, updatedStatuses, "GROUP-$groupID") {
            "STATUS-${it.statusID}"
        }
    }
}
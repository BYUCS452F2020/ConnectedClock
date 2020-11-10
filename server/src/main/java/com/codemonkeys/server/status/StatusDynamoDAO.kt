package com.codemonkeys.server.status

import com.codemonkeys.server.core.dao.BaseDynamoDAO
import com.codemonkeys.shared.status.Status

class StatusDynamoDAO : BaseDynamoDAO(), IStatusDAO {
    override fun getStatuses(groupID: String): List<Status> {
        TODO("Not yet implemented")
    }

    override fun updateStatuses(groupID: String, updatedStatuses: List<Status>) {
        TODO("Not yet implemented")
    }
}
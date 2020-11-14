package com.codemonkeys.server.status

import com.codemonkeys.shared.status.Status

interface IStatusDAO {
    fun getStatuses(groupID: String): List<Status>

    fun updateStatuses(groupID: String, updatedStatuses: List<Status>)
}
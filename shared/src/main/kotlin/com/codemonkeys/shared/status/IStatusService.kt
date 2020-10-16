package com.codemonkeys.shared.status

interface IStatusService {
    fun getStatuses(authToken: String): List<Status>

    fun updateStatuses(authToken: String, updatedStatuses: List<Status>)
}
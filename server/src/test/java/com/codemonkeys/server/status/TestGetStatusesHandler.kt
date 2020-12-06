package com.codemonkeys.server.status

import com.codemonkeys.server.authorization.AuthorizationTestResources
import com.codemonkeys.server.BaseDynamoTest
import com.codemonkeys.server.BaseSqlTest
import com.codemonkeys.server.status.handlers.GetStatusesHandler
import org.junit.Test
import org.junit.Assert.*

// class TestGetStatusesHandler : BaseSqlTest() {
class TestGetStatusesHandler : BaseDynamoTest() {
    @Test
    fun testGetStatusesHandler() {
        val getStatusesHandler = GetStatusesHandler()

        val group1StatusesResponse = getStatusesHandler.handleRequest(AuthorizationTestResources.GROUP_1_USER_AUTHTOKEN_REQUEST, null)
        assertEquals("Should return response with list of statuses for com.codemonkeys.server.user's group", StatusTestResources.GROUP_1_STATUSES_RESPONSE, group1StatusesResponse)


        val validGetStatusesResponseGroupID = getStatusesHandler.handleRequest(AuthorizationTestResources.GROUP_2_GROUP_AUTHTOKEN_REQUEST, null)
        assertEquals("Should return response with list of statuses for group", StatusTestResources.GROUP_2_STATUSES_RESPONSE, validGetStatusesResponseGroupID)


        val invalidGetStatusesResponse = getStatusesHandler.handleRequest(AuthorizationTestResources.INVALID_AUTHTOKEN_REQUEST, null)
        assertEquals("Should return Not Authorized response because of invalid authToken", AuthorizationTestResources.NOT_AUTHORIZED_RESPONSE, invalidGetStatusesResponse)
    }
}
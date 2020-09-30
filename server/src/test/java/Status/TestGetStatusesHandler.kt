package Status

import BaseTest
import org.junit.Test
import org.junit.Assert.*

class TestGetStatusesHandler : BaseTest() {
    @Test
    fun testGetStatusesHandler() {
        val getStatusesHandler = GetStatusesHandler()

        val validGetStatusesRequestUserID = GetStatusesRequest("e00f1c88-1d5b-4d32-be07-1018f39a26b2")
        val validGetStatusesResponseUserID = getStatusesHandler.handleRequest(validGetStatusesRequestUserID, null)
        val expectedValidGetStatusesResponseUserID = GetStatusesResponse(
            listOf(
                Status("04e3b648-c3dd-41da-a430-1dacde995b7d", 23.2, "Work", "98729fce-0809-43fe-b953-f48b14b07616"),
                Status( "12a20cb0-9e82-4d88-98f2-faaa9ff8c675", 11.0, "Shopping", "98729fce-0809-43fe-b953-f48b14b07616" ),
                Status("7be3c43c-9f4b-4f6d-bbeb-e639e8331ab9", 60.0, "Home", "98729fce-0809-43fe-b953-f48b14b07616"),
                Status("d32b0786-c6f1-4c70-a31f-a9efed1ef1f6", 75.0, "Away", "98729fce-0809-43fe-b953-f48b14b07616"),
                Status("ec488303-1152-4d8d-af55-db9b323be17e", 90.5, "School", "98729fce-0809-43fe-b953-f48b14b07616")
            )
        )
        assertEquals("Should return response with list of statuses for user's group", expectedValidGetStatusesResponseUserID, validGetStatusesResponseUserID)


        val validGetStatusesRequestGroupID = GetStatusesRequest("7af562ed-46bd-429b-8bcd-2d85e76d9a10")
        val validGetStatusesResponseGroupID = getStatusesHandler.handleRequest(validGetStatusesRequestGroupID, null)
        val expectedValidGetStatusesResponseGroupID = GetStatusesResponse(
            listOf(
                Status("32f85320-92e0-4382-a5ae-d71b562422c5", 12.7, "Mortal Peril", "2bc8f348-fce4-4df6-9795-deff8e721c7a"),
                Status("92a3db1e-99b6-45d1-92e6-2c47720e620e", 42.9, "Home", "2bc8f348-fce4-4df6-9795-deff8e721c7a"),
                Status("ed9ceea8-b059-4e00-b35e-83be6e63c497", 101.0, "School", "2bc8f348-fce4-4df6-9795-deff8e721c7a")
            )
        )
        assertEquals("Should return response with list of statuses for group", expectedValidGetStatusesResponseGroupID, validGetStatusesResponseGroupID)


        val invalidGetStatusesRequest = GetStatusesRequest("invalidauthtoken")
        val invalidGetStatusesResponse = getStatusesHandler.handleRequest(invalidGetStatusesRequest, null)
        val expectedInvalidGetStatusesResponse = GetStatusesResponse("Not Authorized")
        assertEquals("Should return response with list of statuses for user's group", expectedInvalidGetStatusesResponse, invalidGetStatusesResponse)
    }
}
package Status

import BaseTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class TestStatusDAO: BaseTest() {
    @Test
    fun testGetStatuses() {
        val statusDAO = StatusDAO()
        val statuses = statusDAO.getStatuses("98729fce-0809-43fe-b953-f48b14b07616")
        val expectedStatuses = listOf(
            Status("04e3b648-c3dd-41da-a430-1dacde995b7d", 23.2, "Work", "98729fce-0809-43fe-b953-f48b14b07616"),
            Status("12a20cb0-9e82-4d88-98f2-faaa9ff8c675", 11.0, "Shopping", "98729fce-0809-43fe-b953-f48b14b07616"),
            Status("7be3c43c-9f4b-4f6d-bbeb-e639e8331ab9", 60.0, "Home", "98729fce-0809-43fe-b953-f48b14b07616"),
            Status("d32b0786-c6f1-4c70-a31f-a9efed1ef1f6", 75.0, "Away", "98729fce-0809-43fe-b953-f48b14b07616"),
            Status("ec488303-1152-4d8d-af55-db9b323be17e", 90.5, "School", "98729fce-0809-43fe-b953-f48b14b07616")
        )
        assertEquals("Should only return statuses belonging to group", expectedStatuses, statuses)


        val emptyStatuses = statusDAO.getStatuses("invalidstatus")
        val expectedEmptyStatuses = listOf<Status>()
        assertEquals("A nonexistent groupID should return an empty list of statuses", expectedEmptyStatuses, emptyStatuses)
    }
}
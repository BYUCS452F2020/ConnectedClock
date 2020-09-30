package Status

import org.junit.Test
import org.junit.Assert.*

class TestStatusDAO {
    @Test
    fun testGetStatuses() {
        val envVariable = System.getenv("RDS_USERNAME")
        assertEquals("A", "B")
    }
}
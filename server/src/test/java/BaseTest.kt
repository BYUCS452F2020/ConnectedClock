import org.junit.Before

open class BaseTest {
    @Before
    fun setupDatabase() {
        val databasePreparer = DatabasePreparer()
        databasePreparer.prepareDatabase()
    }
}

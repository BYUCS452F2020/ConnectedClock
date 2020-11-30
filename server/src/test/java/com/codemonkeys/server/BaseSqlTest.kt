package com.codemonkeys.server

import org.junit.Assert
import org.junit.Before


open class BaseSqlTest {
    @Before
    fun setupDatabase() {
        val databasePreparer = SqlDatabasePreparer()
        databasePreparer.prepareDatabase()
    }

    // https://kotlinlang.org/docs/reference/generics.html
    // https://kotlinlang.org/docs/tutorials/kotlin-for-py/functional-programming.html Lambdas
    inline fun <reified E: Exception> assertThrowsException(errorMessage: String, expectedExceptionClass: Class<E>, statement: () -> Unit) {
        try {
            statement()
            Assert.assertTrue(errorMessage, false)
        }
        catch(e: Exception) {
            if (e !is E) {
                Assert.assertTrue("Should have thrown '${E::class.java}', but '$e' was thrown instead", false)
            }
        }
    }
}

package com.codemonkeys.server.user

import com.codemonkeys.server.authorization.AuthorizationSqlDAO
import com.codemonkeys.server.core.dao.BaseSqlDAO
import com.codemonkeys.shared.user.User
import com.codemonkeys.shared.user.requests.CreateUserRequest
import com.codemonkeys.shared.user.requests.LoginUserRequest
import com.codemonkeys.shared.user.requests.LogoutUserRequest
import com.codemonkeys.shared.user.requests.UpdateUserRequest
import com.codemonkeys.shared.user.responses.CreateUserResponse
import com.codemonkeys.shared.user.responses.LoginUserResponse
import com.codemonkeys.shared.user.responses.LogoutUserResponse
import com.codemonkeys.shared.user.responses.UpdateUserResponse
import java.util.UUID

class UserSqlDAO : BaseSqlDAO(), IUserDAO {

    override fun createUser(request: CreateUserRequest): CreateUserResponse {
        // Insert user into the database
        val connection = this.openConnection()
        try {
            this.insertObject(User::class.java, connection, "User", request.user)
            connection.commit()
        } catch (e: Exception) {
            print(e)
            connection.rollback()
            return CreateUserResponse(
                null,
                "Error inserting user"
            )
        } finally {
            this.closeConnection(connection)
        }

        // Insert authtoken for user into the database
        val authToken = UUID.randomUUID().toString()
        val authorizationDAO = AuthorizationSqlDAO()
        val success = authorizationDAO.insertAuthToken(authToken, request.user.userID, null)

        // Return a response with the token
        return if (success) {
            CreateUserResponse(authToken)
        } else {
            CreateUserResponse(
                null,
                "Error inserting authtoken"
            )
        }
    }

    private var GET_USER_BY_USERID = """
        SELECT *
            FROM User
            WHERE userID = ?;
    """

    override fun getUser(userID: String): User? {
        val connection = openConnection()
        try {
            val statement = connection.prepareStatement(GET_USER_BY_USERID)
            statement.setString(1, userID)
            val resultSet = statement.executeQuery()
            val users = this.getQueryResults(User::class.java, resultSet)

            return if (users.isNotEmpty()) {
                users[0]
            } else {
                null
            }
        } finally {
            connection.close()
        }
    }

    private var GET_USER_BY_USERNAME_AND_PASSWORD_SQL = """
        Select *
            From User u
                Where userName = ? and password = ?;
    """

    override fun loginUser(request: LoginUserRequest): LoginUserResponse {
        val connection = openConnection()

        val statement = connection.prepareStatement(GET_USER_BY_USERNAME_AND_PASSWORD_SQL)
        statement.setString(1, request.userName)
        statement.setString(2, request.password)
        try {
            val resultSet = statement.executeQuery()
            val users = this.getQueryResults<User>(User::class.java, resultSet)

            if (resultSet != null) {
                val user = users.first()
                val authToken = UUID.randomUUID().toString()
                val authorizationDAO = AuthorizationSqlDAO()
                val success = authorizationDAO.insertAuthToken(authToken, user.userID, user.groupID)

                return if (success) {
                    LoginUserResponse(authToken)
                } else {
                    return LoginUserResponse(
                        null,
                        "Error inserting authtoken"
                    )
                }
            } else {
                return LoginUserResponse(
                    null,
                    "Incorrect user information"
                )
            }
        } catch (e: Exception) {
            return LoginUserResponse(
                null,
                "Incorrect user information"
            )
        } finally {
            closeConnection(connection)
        }
    }

    override fun logoutUser(request: LogoutUserRequest): LogoutUserResponse {
        val authorizationDAO = AuthorizationSqlDAO()
        val success = authorizationDAO.deleteAuthToken(request.authToken)

        return if (success) {
            LogoutUserResponse()
        } else {
            LogoutUserResponse("Error logging out user")
        }
    }

    private var UPDATE_USER_SQL = """
       Update User
            Set password = ?
            Where userID = ?;
    """

    override fun updateUser(request: UpdateUserRequest): UpdateUserResponse {
        val authorizationDAO = AuthorizationSqlDAO()
        val userID = authorizationDAO.getUserIDFromAuthToken(request.authToken)
            ?: return UpdateUserResponse("Invalid credentials")

        val connection = openConnection()
        val statement = connection.prepareStatement(UPDATE_USER_SQL)
        statement.setString(1, request.newPassword)
        statement.setString(2, userID)

        return try {
            statement.execute()
            connection.commit()
            UpdateUserResponse()
        } catch (e: Exception) {
            UpdateUserResponse("Error updating user")
        } finally {
            closeConnection(connection)
        }
    }

}
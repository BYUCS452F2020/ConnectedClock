package user

import authorization.AuthorizationDAO
import core.dao.BaseDAO
import user.requests.CreateUserRequest
import user.requests.LoginUserRequest
import user.requests.LogoutUserRequest
import user.requests.UpdateUserRequest
import user.responses.CreateUserResponse
import user.responses.LoginUserResponse
import user.responses.LogoutUserResponse
import user.responses.UpdateUserResponse
import java.util.UUID

class UserDAO : BaseDAO() {

    fun createUser(request: CreateUserRequest): CreateUserResponse {
        // Insert user into the database
        val connection = this.openConnection()
        try {
            this.insertObject(User::class.java, connection, "User", request.user)
            connection.commit()
        } catch (e: Exception) {
            print(e)
            connection.rollback()
            return CreateUserResponse(null, "Error inserting user")
        } finally {
            this.closeConnection(connection)
        }

        // Insert authtoken for user into the database
        val authToken = UUID.randomUUID().toString()
        val authorizationDAO = AuthorizationDAO()
        val success = authorizationDAO.insertAuthToken(authToken, request.user.userID, null)

        // Return a response with the token
        return if (success) {
            CreateUserResponse(authToken)
        } else {
            CreateUserResponse(null, "Error inserting authtoken")
        }
    }

    private var GET_USER_BY_USERNAME_AND_PASSWORD_SQL = """
        Select *
            From User u
                Where userName = ? and password = ?;
    """

    fun loginUser(request: LoginUserRequest): LoginUserResponse {
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
                val authorizationDAO = AuthorizationDAO()
                val success = authorizationDAO.insertAuthToken(authToken, user.userID, user.groupID)

                return if (success) {
                    LoginUserResponse(authToken)
                } else {
                    return LoginUserResponse(null, "Error inserting authtoken")
                }
            } else {
                return LoginUserResponse(null, "Incorrect user information")
            }
        } catch (e: Exception) {
            return LoginUserResponse(null, "Incorrect user information")
        } finally {
            closeConnection(connection)
        }
    }

    fun logoutUser(request: LogoutUserRequest): LogoutUserResponse {
        val authorizationDAO = AuthorizationDAO()
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

    fun updateUser(request: UpdateUserRequest): UpdateUserResponse {
        val authorizationDAO = AuthorizationDAO()
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
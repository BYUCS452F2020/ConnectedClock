package com.codemonkeys.connectedclock.app.user.model

import com.codemonkeys.connectedclock.app.authorization.AuthorizationRepository
import com.codemonkeys.connectedclock.app.core.network.ServerException
import com.codemonkeys.connectedclock.app.group.model.ClockGroupRepository
import com.codemonkeys.shared.clockGroup.ClockGroup
import com.codemonkeys.shared.user.IUserService
import com.codemonkeys.shared.user.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userService: IUserService,
    private val authorizationRepository: AuthorizationRepository,
    private val groupRepository: ClockGroupRepository
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun getGroup(): ClockGroup? {
        return groupRepository.getGroup()
    }

    fun loginUser(username: String, password: String): Boolean {
        return try {
            val response = userService.loginUser(username, password)
            response.authToken?.let { authorizationRepository.setAuthToken(it) }
            true
        } catch (e: ServerException) {
            print(e.message)
            false
        }
    }

    fun registerUser(username: String, password: String, groupID: String, clockHand: Int): Boolean {
        return try {
            val response = userService.createUser(
                User(
                    UUID.randomUUID().toString(), groupID, username,
                    password, clockHand,null)
            )
            response.authToken?.let { authorizationRepository.setAuthToken(it) }
            true
        }
        catch (e: ServerException) {
            print(e.message)
            false
        }
    }

    fun logoutUser(authToken: String): Boolean {
        return try {
            val response = userService.logoutUser(authToken)
            authorizationRepository.setAuthToken(null)
            true
        }
        catch (e: ServerException) {
            print(e.message)
            false
        }
    }

    fun updateUser(authToken: String, currentPassword: String, newPassword: String): Boolean {
        return try {
            val response = userService.updateUser(authToken, currentPassword, newPassword)
            true
        }
        catch (e: ServerException) {
            print(e.message)
            false
        }
    }
}

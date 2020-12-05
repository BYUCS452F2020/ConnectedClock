package com.codemonkeys.connectedclock.app.zone.model

import com.codemonkeys.connectedclock.app.authorization.AuthorizationRepository
import com.codemonkeys.connectedclock.app.group.model.ClockGroupRepository
import com.codemonkeys.shared.clockGroup.ClockGroup
import com.codemonkeys.shared.user.IUserService
import com.codemonkeys.shared.user.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        val response = userService.loginUser(username, password)
        if (response.didErrorOccur) {
            print(response.errorMessage)
        } else {
            response.authToken?.let { authorizationRepository.setAuthToken(it) }
        }
        return response.didErrorOccur
    }

    fun registerUser(username: String, password: String, groupID: String, clockHand: Int): Boolean {
        val response = userService.createUser(
            User(
                UUID.randomUUID().toString(), groupID, username,
            password, clockHand,null)
        )
        if (response.didErrorOccur) {
            print(response.errorMessage)
        } else {
            response.authToken?.let { authorizationRepository.setAuthToken(it) }
        }
        return response.didErrorOccur
    }

    fun logoutUser(authToken: String): Boolean {
        val response = userService.logoutUser(authToken)
        if (response.didErrorOccur) {
            print(response.errorMessage)
        } else {
            authorizationRepository.setAuthToken(null)
        }
        return response.didErrorOccur
    }

    fun updateUser(authToken: String, currentPassword: String, newPassword: String): Boolean {
        val response = userService.updateUser(authToken, currentPassword, newPassword)
        if (response.didErrorOccur) {
            print(response.errorMessage)
        }
        return response.didErrorOccur
    }
}

package com.codemonkeys.connectedclock.app.zone.model

import com.codemonkeys.connectedclock.app.authorization.AuthorizationRepository
import com.codemonkeys.shared.user.IUserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userService: IUserService,
    private val authorizationRepository: AuthorizationRepository
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun loginUser(username: String, password: String) {
        coroutineScope.launch {
            val response = userService.loginUser(username, password)
            if (response.didErrorOccur) {
                print(response.errorMessage)
            } else {
                response.authToken?.let { authorizationRepository.setAuthToken(it) }
            }
        }
    }

    fun registerUser(username: String, password: String, groupID: String, clockHand: Int) {
        coroutineScope.launch {
            val response = userService.createUser(groupID, username, password, clockHand)
            if (response.didErrorOccur) {
                print(response.errorMessage)
            } else {
                response.authToken?.let { authorizationRepository.setAuthToken(it) }
            }
        }
    }

    fun logoutUser(authToken: String) {
        coroutineScope.launch {
            val response = userService.logoutUser(authToken)
            if (response.didErrorOccur) {
                print(response.errorMessage)
            } else {
                authorizationRepository.setAuthToken(null)
            }
        }
    }

    fun updateUser(authToken: String, currentPassword: String, newPassword: String) {
        coroutineScope.launch {
            val response = userService.updateUser(authToken, currentPassword, newPassword)
            if (response.didErrorOccur) {
                print(response.errorMessage)
            }
        }
    }
}

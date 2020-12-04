package com.codemonkeys.connectedclock.app.authorization.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.codemonkeys.connectedclock.app.authorization.AuthorizationRepository
import com.codemonkeys.connectedclock.app.user.model.ClientUserService
import com.codemonkeys.connectedclock.app.zone.model.UserRepository
import com.codemonkeys.shared.user.User
import com.codemonkeys.shared.user.requests.CreateUserRequest
import com.codemonkeys.shared.user.requests.LoginUserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class LoginViewModel @ViewModelInject constructor(
    val userRepository: UserRepository,
    val authorizationRepository: AuthorizationRepository
): ViewModel() {

    suspend fun loginUser(username: String, password: String): Boolean {
        var success = false;
        withContext(Dispatchers.IO) {
            success = userRepository.loginUser(username, password)
        }
        return success
    }

}
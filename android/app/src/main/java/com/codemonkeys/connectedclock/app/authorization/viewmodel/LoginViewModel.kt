package com.codemonkeys.connectedclock.app.authorization.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.codemonkeys.connectedclock.app.authorization.AuthorizationRepository
import com.codemonkeys.connectedclock.app.user.model.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
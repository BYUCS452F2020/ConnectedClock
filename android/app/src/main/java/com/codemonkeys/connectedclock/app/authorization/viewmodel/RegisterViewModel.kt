package com.codemonkeys.connectedclock.app.authorization.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.codemonkeys.connectedclock.app.authorization.AuthorizationRepository
import com.codemonkeys.connectedclock.app.group.model.ClockGroupRepository
import com.codemonkeys.connectedclock.app.user.model.ClientUserService
import com.codemonkeys.connectedclock.app.zone.model.UserRepository
import com.codemonkeys.shared.clockGroup.ClockGroup
import com.codemonkeys.shared.user.User
import com.codemonkeys.shared.user.requests.CreateUserRequest
import com.codemonkeys.shared.user.requests.LoginUserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class RegisterViewModel @ViewModelInject constructor(
    val userRepository: UserRepository,
    val authorizationRepository: AuthorizationRepository,
    val groupRepository: ClockGroupRepository
): ViewModel() {

    suspend fun registerUser(username: String, password: String, groupID: String, clockHand: Int): Boolean {
        var success = false;
        withContext(Dispatchers.IO) {
            success = userRepository.registerUser(username, password, groupID, clockHand)
        }
        return success
    }

    fun getGroup(): ClockGroup? {
        return groupRepository.getGroup()
    }
}
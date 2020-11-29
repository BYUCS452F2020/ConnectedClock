package com.codemonkeys.connectedclock.app.settings.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codemonkeys.connectedclock.app.authorization.AuthorizationRepository
import com.codemonkeys.connectedclock.app.group.model.ClockGroupRepository
import com.codemonkeys.connectedclock.app.status.model.StatusRepository
import com.codemonkeys.connectedclock.app.zone.model.UserRepository
import com.codemonkeys.shared.status.Status

class SettingViewModel @ViewModelInject constructor(
    private val statusRepository: StatusRepository,
    private val group: ClockGroupRepository,
    private val user: UserRepository,
    private val auth: AuthorizationRepository
) : ViewModel() {

    fun addStatus(statusName: String, clockHandAngle: Int){
        statusRepository.addStatueToCache(statusName, clockHandAngle)
    }

    fun removeStatus(position: Int){
        statusRepository.removeStatueFromCache(position)
    }

    fun getCurrentStatus() : MutableLiveData<MutableList<Status>>{
        return statusRepository.getStatuses()
    }

    fun logOutUser() {
        val authToken = auth.getAuthToken().toString()
        user.logoutUser(authToken)
    }

    suspend fun saveStatus() {
        // update the status to the AWS server
        statusRepository.updateStatuses()
    }

    fun setUserHandIndex(itemIndexVal: String) {
        // Todo: need function to update user hand index
    }

    fun getCurrentUserHandIndex() : Int{
        // Todo: need function to get user hand index
        return 1
    }

}
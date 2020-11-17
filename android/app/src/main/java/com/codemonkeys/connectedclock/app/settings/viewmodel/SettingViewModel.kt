package com.codemonkeys.connectedclock.app.settings.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codemonkeys.connectedclock.app.group.model.ClockGroupRepository
import com.codemonkeys.connectedclock.app.status.model.StatusRepository
import com.codemonkeys.connectedclock.app.zone.model.ZoneRepository
import com.codemonkeys.shared.status.Status

// settings view model will use other feature's repository
// need to use groupRepository to change the password
// need to use userRepository to get group id and set clockHandIndex
class SettingViewModel @ViewModelInject constructor(
    private val statusRepository: StatusRepository,
    private val group : ClockGroupRepository
) : ViewModel() {

    fun addStatus(statusName: String, clockHandAngle: Int){
        statusRepository.addStatueToCache(statusName, clockHandAngle)
//        statusRepository.updateStatuses()
    }

    fun removeStatus(position: Int){
        statusRepository.removeStatueFromCache(position)
//        statusRepository.updateStatuses()
    }

    fun getCurrentStatus() : MutableLiveData<MutableList<Status>>{
        return statusRepository.getStatuses()
    }

    fun logOutUser() {
        // log out user need to call the function in UserRepository
    }

    fun setUserHandIndex(itemIndexVal: String) {
        // use user repo to set the userHandIndex
    }

    fun getCurrentUserHandIndex() : Int{
        // user user repo to get current userHandIndex
        return 1
    }

}
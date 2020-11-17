package com.codemonkeys.connectedclock.app.group.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.codemonkeys.connectedclock.app.group.model.ClockGroupRepository

class ClockGroupViewModel @ViewModelInject constructor(
    private val clockGroupRepository: ClockGroupRepository
) : ViewModel() {

    fun createGroup(groupName : String, password: String){
        clockGroupRepository.createGroup(groupName, password)
    }
}
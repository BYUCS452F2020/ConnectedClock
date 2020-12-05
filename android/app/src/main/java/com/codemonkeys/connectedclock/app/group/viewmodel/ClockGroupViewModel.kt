package com.codemonkeys.connectedclock.app.group.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.codemonkeys.connectedclock.app.group.model.ClockGroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClockGroupViewModel @ViewModelInject constructor(
    private val clockGroupRepository: ClockGroupRepository
) : ViewModel() {

    suspend fun createGroup(groupName : String, password: String){
        withContext(Dispatchers.IO) {
            clockGroupRepository.createGroup(groupName, password)
        }
    }
}
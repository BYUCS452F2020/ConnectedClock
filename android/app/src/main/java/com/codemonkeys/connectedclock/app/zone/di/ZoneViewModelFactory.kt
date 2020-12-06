package com.codemonkeys.connectedclock.app.zone.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codemonkeys.connectedclock.app.status.model.StatusRepository
import com.codemonkeys.connectedclock.app.zone.model.ZoneRepository
import com.codemonkeys.connectedclock.app.zone.viewmodel.ZoneViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject

//class ZoneViewModelFactory @Inject constructor(private val zoneRepository: ZoneRepository, private val statusRepository: StatusRepository): ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ZoneViewModel::class.java)) {
//            return ZoneViewModel(zoneRepository, statusRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel Class")
//    }
//}
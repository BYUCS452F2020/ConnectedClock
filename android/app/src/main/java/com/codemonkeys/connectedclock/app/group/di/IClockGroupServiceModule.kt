package com.codemonkeys.connectedclock.app.group.di

import com.codemonkeys.connectedclock.app.core.network.NetworkClientModule
import com.codemonkeys.connectedclock.app.group.model.ClockGroupService
import com.codemonkeys.shared.clockGroup.IClockGroupService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
object IClockGroupServiceModule {
    @Provides
    fun provideIClockGroupServiceModule(): IClockGroupService {
        return ClockGroupService(NetworkClientModule.provideNetworkClient())
    }
}
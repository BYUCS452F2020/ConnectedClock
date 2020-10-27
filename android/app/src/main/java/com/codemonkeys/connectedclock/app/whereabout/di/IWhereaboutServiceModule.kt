package com.codemonkeys.connectedclock.app.whereabout.di

import com.codemonkeys.connectedclock.app.whereabout.model.ClientWhereaboutService
import com.codemonkeys.shared.whereabout.IWhereaboutService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
object IWhereaboutServiceModule {
    @Provides
    fun provideIWhereaboutServiceModule(whereaboutService: ClientWhereaboutService): IWhereaboutService {
        return whereaboutService
    }
}
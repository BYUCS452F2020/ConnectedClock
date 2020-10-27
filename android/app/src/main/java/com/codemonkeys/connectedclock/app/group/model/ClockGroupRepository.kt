package com.codemonkeys.connectedclock.app.group.model

import com.codemonkeys.connectedclock.app.authorization.AuthorizationRepository
import com.codemonkeys.shared.clockGroup.ClockGroup
import com.codemonkeys.shared.clockGroup.IClockGroupService
import com.codemonkeys.shared.zone.IZoneService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * the @Inject here means that this class is injectable, and the repository class
 * handle data operations
 */

@Singleton
class ClockGroupRepository @Inject constructor(
    private val clockGroupService: IClockGroupService,
    private val authorizationRepository: AuthorizationRepository
) {

    /**
     * at this point since we do not have a room page yet
     * we will not going to need this class yet
     */
    private val currentGroup: ClockGroup? = null

    fun createGroup(groupName: String, password: String) {
        // ?: means if value is a null value replace it with ""
        // example: val l: Int = if (b != null) b.length else -1
        // the same as : val l = b?.length ?: -1
        val authToken = authorizationRepository.getAuthToken().value ?: ""
        clockGroupService.createGroup(authToken, groupName, password)

    }
}
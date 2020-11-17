package com.codemonkeys.connectedclock.app.group.model

import com.codemonkeys.connectedclock.app.core.BaseClientService
import com.codemonkeys.connectedclock.app.zone.model.IZoneNetworkAPI
import com.codemonkeys.shared.clockGroup.ClockGroup
import com.codemonkeys.shared.clockGroup.IClockGroupService
import com.codemonkeys.shared.clockGroup.requests.CreateGroupRequest
import com.codemonkeys.shared.core.requests.AuthorizedRequest
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClockGroupService (private val retrofit: Retrofit) : IClockGroupService, @Inject BaseClientService()  {

    private val networkAPI = retrofit.create<IGroupNetworkAPI>(
        IGroupNetworkAPI::class.java
    )

    override fun addMemberToGroup(authToken: String, userNameToAdd: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun createGroup(authToken: String, groupName: String, groupPassword: String) {
        val request = CreateGroupRequest(authToken, groupName, groupPassword)
        // should try to handle error, maybe
        this.executeApiCall(networkAPI.createGroup(request))
    }

    override fun deleteGroup(authToken: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun deleteMemberFromGroup(authToken: String) {
        TODO("Not yet implemented")
    }

    override fun getGroup(authToken: String, groupPassword: String): ClockGroup {
        TODO("Not yet implemented")
    }

    override fun loginGroup(groupName: String, groupPassword: String): String {
        TODO("Not yet implemented")
    }
}
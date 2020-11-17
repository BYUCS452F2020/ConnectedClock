package com.codemonkeys.connectedclock.app.group.model

import com.codemonkeys.shared.clockGroup.requests.CreateGroupRequest
import com.codemonkeys.shared.core.responses.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * this interface is used by retrofit to specify therequest body
 */
interface IGroupNetworkAPI {

    @POST("createGroup")
    fun createGroup(
        @Body request: CreateGroupRequest
    ) : Call<BaseResponse>

}
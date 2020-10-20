package com.codemonkeys.connectedclock.app.whereabout.model

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.shared.whereabout.requests.UpdateUserWhereaboutRequest
import com.codemonkeys.shared.whereabout.responses.GetWhereaboutsResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface IWhereaboutNetworkAPI {
    @POST("GetWhereabouts")
    fun getWhereabouts(
        @Body request: AuthorizedRequest
    ) : GetWhereaboutsResponse

    @POST("UpdateUserWhereabout")
    fun updateUserWhereabout(
        @Body request: UpdateUserWhereaboutRequest
    ) : BaseResponse
}
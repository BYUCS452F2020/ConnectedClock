package com.codemonkeys.connectedclock.app.status

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.shared.status.requests.UpdateStatusesRequest
import com.codemonkeys.shared.status.responses.GetStatusesResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface IStatusNetworkAPI {
    @POST("GetStatuses")
    fun getStatuses(
        @Body request: AuthorizedRequest
    ) : GetStatusesResponse

    @POST("UpdateStatuses")
    fun updateStatuses(
        @Body request: UpdateStatusesRequest

    ) : BaseResponse
}
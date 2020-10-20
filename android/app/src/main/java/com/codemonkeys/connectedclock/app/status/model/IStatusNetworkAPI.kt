package com.codemonkeys.connectedclock.app.status.model

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.shared.status.requests.UpdateStatusesRequest
import com.codemonkeys.shared.status.responses.GetStatusesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IStatusNetworkAPI {
    // https://futurestud.io/tutorials/retrofit-synchronous-and-asynchronous-requests
    // We're doing synchronous calls here, we make it asynchronous in the repository.
    // That way the Client service can have the same signature as its interface.
    @POST("GetStatuses")
    fun getStatuses(
        @Body request: AuthorizedRequest
    ) : Call<GetStatusesResponse>

    @POST("UpdateStatuses")
    fun updateStatuses(
        @Body request: UpdateStatusesRequest

    ) : Call<BaseResponse>
}
package com.codemonkeys.connectedclock.app.zone.model

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.shared.zone.requests.UpdateZonesRequest
import com.codemonkeys.shared.zone.responses.GetZonesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IZoneNetworkAPI {

    // https://futurestud.io/tutorials/retrofit-synchronous-and-asynchronous-requests
    // We're doing synchronous calls here, we make it asynchronous in the repository.
    // That way the Client service can have the same signature as its interface.
    @POST("GetZones")
    fun getZones(
        @Body request: AuthorizedRequest
    ) : Call<GetZonesResponse>

    @POST("UpdateZones")
    fun updateZones(
        @Body request: UpdateZonesRequest
    ) : Call<BaseResponse>
}
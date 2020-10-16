package com.codemonkeys.connectedclock.app.zone

import com.codemonkeys.shared.core.requests.AuthorizedRequest
import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.shared.zone.requests.UpdateZonesRequest
import com.codemonkeys.shared.zone.responses.GetZonesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IZoneNetworkAPI {

    @POST("GetZones")
    fun getZones(
        @Body request: AuthorizedRequest
    ) : GetZonesResponse

    @POST("UpdateZones")
    fun updateZones(
        @Body request: UpdateZonesRequest
    ) : BaseResponse
}
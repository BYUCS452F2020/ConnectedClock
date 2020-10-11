package com.codemonkeys.connectedclock.zone

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IZoneNetworkAPI {

    @GET("GetZones")
    fun getZones(
        @Body request: AuthorizedRequest
    )

    @POST("UpdateZones")
    fun updateZones(
        @Body request: UpdateZonesRequest
    )
}
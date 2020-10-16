package com.codemonkeys.shared.zone

interface IZoneService {

    fun getZones(authToken: String): List<Zone>

    fun updateZones(authToken: String, updatedZones: List<Zone>)
}
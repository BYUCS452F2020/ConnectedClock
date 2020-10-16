package com.codemonkeys.shared.whereabout

interface IWhereaboutService {

    fun getWhereabouts(authToken: String): List<Whereabout>

    fun updateUserWhereabout(authToken: String, currentZoneID: String?)
}
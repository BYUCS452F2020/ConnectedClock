package com.codemonkeys.server.whereabout

import com.codemonkeys.shared.whereabout.Whereabout

interface IWhereaboutDAO {
    fun getWhereabouts(groupID: String): List<Whereabout>

    fun updateUserWhereabout(userID: String, currentZoneID: String?)
}
package com.codemonkeys.shared.whereabout

import com.codemonkeys.shared.core.dao.ColumnAnnotation

data class Whereabout(
    @ColumnAnnotation("userID") var userID: String = "",
    @ColumnAnnotation("username") var username: String = "",
    @ColumnAnnotation("clockHandIndex") var clockHandIndex: Int = -1,
    @ColumnAnnotation("clockHandAngle") var clockHandAngle: Long = 0, // This has to be long, because for some reason JDBC is returning a long and not an int.
    @ColumnAnnotation("statusID") var currentStatusID: String? = null
)
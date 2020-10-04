package Whereabout

import Core.DAO.ColumnAnnotation

data class Whereabout(
    @ColumnAnnotation("userID") var userID: String = "",
    @ColumnAnnotation("username") var username: String = "",
    @ColumnAnnotation("clockHandIndex") var clockHandIndex: Int = -1,
    @ColumnAnnotation("currentZoneID") var currentZoneID: String? = null
)
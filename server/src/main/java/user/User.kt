package user

import core.dao.ColumnAnnotation

data class User(
        @ColumnAnnotation("userID") var userID: String = "",
        @ColumnAnnotation("groupID") var groupID: String = "",
        @ColumnAnnotation("userName") var userName: String = "",
        @ColumnAnnotation("password") var password: String = "",
        @ColumnAnnotation("clockHandIndex") var clockHandIndex: Int = 0,
        @ColumnAnnotation("currentZoneID") var currentZoneID: String = ""
)
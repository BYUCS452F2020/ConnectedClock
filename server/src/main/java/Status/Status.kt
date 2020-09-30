package Status

import Core.DAO.ColumnAnnotation

// https://kotlinlang.org/docs/reference/data-classes.html
// Getters and setters are automatically generated for each of these properties.
data class Status(
    @ColumnAnnotation("statusID") val statusID: String,
    @ColumnAnnotation("clockHandAngle") var clockHandAngle: Int,
    @ColumnAnnotation("statusName") var statusName: String,
    @ColumnAnnotation("groupID") var groupID: String)
package status

import core.dao.ColumnAnnotation

// https://kotlinlang.org/docs/reference/data-classes.html
// Getters and setters are automatically generated for each of these properties since this is Kotlin.
// Classes marked as "data class" automatically generate toString, equals, and hashcode functions as well!
data class Status(
    // For my shortcut of using BaseDAO.getQueryResults to convert SQL resultSet to List<Object>,
    // your object must have a default constructor with no parameters. By adding default values to these
    // field parameters, we can have a constructor with no params.
    @ColumnAnnotation("statusID") var statusID: String = "",
    @ColumnAnnotation("clockHandAngle") var clockHandAngle: Double = 0.0,
    @ColumnAnnotation("statusName") var statusName: String = "",
    @ColumnAnnotation("groupID") var groupID: String = "")
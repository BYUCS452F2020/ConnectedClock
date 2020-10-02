package Zone

import Core.DAO.ColumnAnnotation

data class Zone(
    @ColumnAnnotation("zoneID") var zoneID: String = "",
    @ColumnAnnotation("lat") var lat: Double? = null,
    @ColumnAnnotation("lng") var lng: Double? = null,
    @ColumnAnnotation("radius") var radius: Double? = null,
    @ColumnAnnotation("statusID") var statusID: String = ""
)
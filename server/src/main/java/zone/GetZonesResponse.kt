package zone

import core.handler.BaseResponse

class GetZonesResponse: BaseResponse {
    val zones: List<Zone>

    constructor(_zones: List<Zone>): super() {
        this.zones = _zones
    }

    constructor(errorMessage: String): super(errorMessage){
        this.zones = listOf()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false

        other as GetZonesResponse

        if (this.zones != other.zones) return false
        if (this.didErrorOccur != other.didErrorOccur) return false
        if (this.errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = this.zones.hashCode()
        result = 31 * result + this.didErrorOccur.hashCode()
        result = 31 * result + (this.errorMessage?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "GetZonesResponse(zones=$zones, errorMessage=$errorMessage, didErrorOccur=$didErrorOccur)"
    }

}
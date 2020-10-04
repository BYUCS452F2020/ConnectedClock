package whereabout

import core.handler.BaseResponse

class GetWhereaboutsResponse: BaseResponse {
    val whereabouts: List<Whereabout>

    constructor(_whereabouts: List<Whereabout>): super() {
        this.whereabouts = _whereabouts
    }

    constructor(errorMessage: String): super(errorMessage){
        this.whereabouts = listOf()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false

        other as GetWhereaboutsResponse

        if (this.whereabouts != other.whereabouts) return false
        if (this.didErrorOccur != other.didErrorOccur) return false
        if (this.errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = this.whereabouts.hashCode()
        result = 31 * result + this.didErrorOccur.hashCode()
        result = 31 * result + (this.errorMessage?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "GetWhereaboutsResponse(whereabouts=$whereabouts, errorMessage=$errorMessage, didErrorOccur=$didErrorOccur)"
    }

}
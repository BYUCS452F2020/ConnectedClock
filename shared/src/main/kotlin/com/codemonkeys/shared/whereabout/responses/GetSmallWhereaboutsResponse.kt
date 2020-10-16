package com.codemonkeys.shared.whereabout.responses

import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.shared.whereabout.SmallWhereabout

class GetSmallWhereaboutsResponse: BaseResponse {
    val w: List<SmallWhereabout>

    constructor(_w: List<SmallWhereabout>): super() {
        this.w = _w
    }

    constructor(errorMessage: String): super(errorMessage){
        this.w = listOf()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false

        other as GetSmallWhereaboutsResponse

        if (this.w != other.w) return false
        if (this.didErrorOccur != other.didErrorOccur) return false
        if (this.errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = this.w.hashCode()
        result = 31 * result + this.didErrorOccur.hashCode()
        result = 31 * result + (this.errorMessage?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "GetWhereaboutsResponse(whereabouts=$w, errorMessage=$errorMessage, didErrorOccur=$didErrorOccur)"
    }

}

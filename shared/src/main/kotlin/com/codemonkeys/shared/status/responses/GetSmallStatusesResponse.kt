package com.codemonkeys.shared.status.responses

import com.codemonkeys.shared.core.responses.BaseResponse
import com.codemonkeys.shared.status.SmallStatus

class GetSmallStatusesResponse: BaseResponse {
    val s: List<SmallStatus>

    constructor(_s: List<SmallStatus>): super() {
        this.s = _s
    }

    constructor(errorMessage: String): super(errorMessage){
        this.s = listOf()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false

        other as GetSmallStatusesResponse

        if (this.s != other.s) return false
        if (this.didErrorOccur != other.didErrorOccur) return false
        if (this.errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = this.s.hashCode()
        result = 31 * result + this.didErrorOccur.hashCode()
        result = 31 * result + (this.errorMessage?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "GetStatusesResponse(statuses=$s, errorMessage=$errorMessage, didErrorOccur=$didErrorOccur)"
    }
}

package com.codemonkeys.shared.core.responses

open class BaseResponse {
    val errorMessage: String?
    val didErrorOccur: Boolean

    constructor() {
        this.errorMessage = null
        this.didErrorOccur = false
    }

    constructor(errorMsg: String) {
        this.errorMessage = errorMsg
        this.didErrorOccur = true
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseResponse

        if (errorMessage != other.errorMessage) return false
        if (didErrorOccur != other.didErrorOccur) return false

        return true
    }

    override fun hashCode(): Int {
        var result = errorMessage?.hashCode() ?: 0
        result = 31 * result + didErrorOccur.hashCode()
        return result
    }

    override fun toString(): String {
        return "BaseResponse(errorMessage=$errorMessage, didErrorOccur=$didErrorOccur)"
    }


}
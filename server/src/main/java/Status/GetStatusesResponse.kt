package Status

import Core.Handler.BaseResponse

// Getters and setters are automatically generated for each of these properties.
// If we create an object with a list of statuses, it does not have an errorMessage and didErrorOccur is false.
// However, if we construct GetStatusesResponse with a parameter for errorMessage, then didErrorOccur is true.
class GetStatusesResponse: BaseResponse {
    val statuses: List<Status>

    constructor(_statuses: List<Status>): super() {
        this.statuses = _statuses
    }

    constructor(errorMessage: String): super(errorMessage){
        statuses = listOf()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GetStatusesResponse

        if (statuses != other.statuses) return false
        if (didErrorOccur != other.didErrorOccur) return false
        if (errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = statuses.hashCode()
        result = 31 * result + didErrorOccur.hashCode()
        result = 31 * result + (errorMessage?.hashCode() ?: 0)
        return result
    }
}
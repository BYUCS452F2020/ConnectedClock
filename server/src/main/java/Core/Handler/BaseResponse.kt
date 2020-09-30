package Core.Handler

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
}
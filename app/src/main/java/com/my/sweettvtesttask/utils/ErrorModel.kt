package com.my.sweettvtesttask.utils

sealed class ErrorModel {

    abstract val message: String?

    enum class LocalErrorStatus {
        NO_INTERNET, SOMETHING_WRONG
    }

    data class ServerError(
        val code: Int?,
        override val message: String?
    ) : ErrorModel()

    data class LocalError(
        override val message: String?,
        val status: LocalErrorStatus
    ) : ErrorModel()

}
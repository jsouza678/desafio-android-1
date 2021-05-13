package com.picpay.desafio.android.domain.entity

sealed class ResponseHandler<out T> {
    object Loading: ResponseHandler<Nothing>()
    object Empty: ResponseHandler<Nothing>()
    data class Success<T>(var value: T): ResponseHandler<T>()
    data class Error(val apiError: ApiError<Throwable>): ResponseHandler<Nothing>()
}

sealed class ApiError<out T> {
    data class UnknownError(val code: Int?, val message: String?, val exception: Throwable?): ApiError<Throwable>()
    data class HttpError(val code: Int?, val message: String?, val exception: Throwable?): ApiError<Throwable>()
    data class NetworkError(val code: Int?, val message: String?, val exception: Throwable?): ApiError<Throwable>()
}

data class ErrorResponse(
    val message: String?
)
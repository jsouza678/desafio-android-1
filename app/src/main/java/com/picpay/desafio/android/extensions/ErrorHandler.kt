package com.picpay.desafio.android.extensions

import com.google.gson.Gson
import com.picpay.desafio.android.domain.entity.ApiError
import com.picpay.desafio.android.domain.entity.ErrorResponse
import com.picpay.desafio.android.utils.Constants.DEFAULT_ERROR
import com.picpay.desafio.android.utils.Constants.GENERIC_NETWORK_ERROR
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

@KoinApiExtension
class ErrorHandler : KoinComponent {

    private val gson: Gson by inject()

    fun getErrorFromApi(httpException: HttpException): ApiError.HttpError {
        val errorCode = httpException.code()
        return try {
            val body = httpException.response()?.errorBody()?.string() ?: ""
            val errorParsed = gson.fromJson(body, ErrorResponse::class.java)

            ApiError.HttpError(
                code = errorCode,
                message = errorParsed.message ?: GENERIC_NETWORK_ERROR,
                exception = httpException
            )
        } catch (e: Exception) {
            ApiError.HttpError(
                code = errorCode,
                message = DEFAULT_ERROR,
                exception = httpException.cause
            )
        }
    }
}

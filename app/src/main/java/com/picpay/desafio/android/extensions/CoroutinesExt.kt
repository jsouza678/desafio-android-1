package com.picpay.desafio.android.extensions

import com.picpay.desafio.android.domain.entity.ApiError
import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.utils.Constants.UNKNOWN_ERROR
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * R means Response, and D, Domain
 */
@KoinApiExtension
fun <R, D> launchAsyncFunction(
    blockToRun: suspend () -> Response<R>,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    mapFunction: suspend (R) -> D
): Flow<ResponseHandler<D>> = flow<ResponseHandler<D>> {
    runCatching {
        withContext(coroutineDispatcher) { blockToRun.invoke() }
    }.onLoading {
        emit(ResponseHandler.Loading)
    }.onSuccess { apiResponse ->
        apiResponse.body()?.let { responseBody ->
            emit(ResponseHandler.Success(mapFunction.invoke(responseBody)))
        } ?: run {
            emit(ResponseHandler.Empty)
        }
    }.onFailure { throwable ->
        emit(throwable.getErrorParsed())
    }
}

@KoinApiExtension
private fun Throwable.getErrorParsed(): ResponseHandler.Error =
    when (this) {
        is IOException -> ResponseHandler.Error(
            ApiError.NetworkError(
                code = null,
                message = this.message ?: UNKNOWN_ERROR,
                exception = this
            )
        )
        is HttpException -> ResponseHandler.Error(
            ErrorHandler().getErrorFromApi(this)
        )
        else -> ResponseHandler.Error(
            ApiError.UnknownError(
                code = null,
                message = this.message.toString(),
                exception = this
            )
        )
    }

@SinceKotlin("1.3")
private inline fun <T> Result<T>.onLoading(function: () -> Unit): Result<T> {
    function()
    return this
}

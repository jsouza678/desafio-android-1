package com.picpay.base.extensions

import com.picpay.base.utils.Constants.UNKNOWN_ERROR
import com.picpay.domain.entity.ApiError
import com.picpay.domain.entity.ResponseHandler
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import retrofit2.HttpException
import retrofit2.Response

/**
 * R means Response, and D, Domain
 */
@KoinApiExtension
fun <R, D> launchAsyncFunction(
    blockToRun: suspend () -> Response<R>,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    mapFunction: suspend (R) -> D,
    onComplete: suspend () -> Unit
): Flow<ResponseHandler<D>> = flow<ResponseHandler<D>> {
    runCatching {
        withContext(coroutineDispatcher) { blockToRun.invoke() }
    }.onLoading {
        emit(ResponseHandler.Loading)
    }.onSuccess { apiResponse ->
        apiResponse.body()?.let { responseBody ->
            emit(ResponseHandler.Success(mapFunction.invoke(responseBody)))
            onComplete.invoke()
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

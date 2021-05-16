package com.picpay.desafio.android.utils

import com.picpay.desafio.android.data.contacts.data.local.entity.UserLocal
import com.picpay.desafio.android.data.contacts.data.remote.response.UserResponse
import com.picpay.domain.entity.ResponseHandler
import com.picpay.domain.entity.User
import com.picpay.domain.entity.ApiError
import com.picpay.desafio.android.extensions.ErrorHandler
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

object FakeModels {

    val FAKE_EMPTY_CONTACTS: List<UserResponse>? = null
    val FAKE_CONTACTS_RESPONSE = listOf(
        UserResponse(
            id = 1,
            img = "test",
            name = "João Souza",
            username = "Souza"
        ),
        UserResponse(
            id = 2,
            img = "",
            name = null,
            username = null
        ),
        UserResponse(
            id = 3,
            img = " ",
            name = null,
            username = null
        )
    )
    val FAKE_CONTACTS_LOCAL = listOf(
        UserLocal(
            id = 1,
            img = "test",
            name = "João Souza",
            username = "Souza"
        ),
        UserLocal(
            id = 2,
            img = "Empty image url",
            name = "Empty name",
            username = "Empty username"
        ),
        UserLocal(
            id = 3,
            img = "Empty image url",
            name = "Empty name",
            username = "Empty username"
        )
    )
    val FAKE_CONTACTS = listOf(
        User(
            id = 1,
            img = "test",
            name = "João Souza",
            username = "Souza"
        ),
        User(
            id = 2,
            img = "Empty image url",
            name = "Empty name",
            username = "Empty username"
        ),
        User(
            id = 3,
            img = "Empty image url",
            name = "Empty name",
            username = "Empty username"
        )
    )
    val GENERIC_ERROR: Response<UserResponse> = Response.error(
        404,
        "{\"message\":\"Api Generic Error\"}"
            .toResponseBody("application/json".toMediaTypeOrNull())
    )
    val GENERIC_UNEXPECTED_ERROR: Response<UserResponse> = Response.error(
        409,
        "{\"xxxxxxxxx\":\"123 test\"}"
            .toResponseBody("application/json".toMediaTypeOrNull())
    )
    val GENERIC_ERROR_RESPONSE = ResponseHandler.Error(
        ApiError.HttpError(
            code = 404,
            message = Constants.GENERIC_NETWORK_ERROR,
            exception = ErrorHandler().getErrorFromApi(HttpException(GENERIC_ERROR)).exception
        )
    )
    val SUCCESS_CONTACTS_CALL = ResponseHandler.Success(FAKE_CONTACTS)
    val EMPTY_CONTACTS_CALL = ResponseHandler.Empty
}
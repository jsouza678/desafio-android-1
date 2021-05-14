package com.picpay.desafio.android.utils

import com.picpay.desafio.android.data.contacts.data.local.entity.UserLocal
import com.picpay.desafio.android.data.contacts.data.remote.response.UserResponse
import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User

object FakeModels {

    val FAKE_EMPTY_CONTACTS: List<UserResponse>? = null
    val FAKE_CONTACTS_RESPONSE = listOf(
        UserResponse(
            id = 1,
            img = "",
            name = "",
            username = ""
        ),
        UserResponse(
            id = 1,
            img = "",
            name = "",
            username = ""
        ),
        UserResponse(
            id = 1,
            img = "",
            name = "",
            username = ""
        )
    )
    val FAKE_CONTACTS_LOCAL = listOf(
        UserLocal(
            id = 1,
            img = "",
            name = "",
            username = ""
        ),
        UserLocal(
            id = 1,
            img = "",
            name = "",
            username = ""
        ),
        UserLocal(
            id = 1,
            img = "",
            name = "",
            username = ""
        )
    )
    val FAKE_CONTACTS = listOf(
        User(
            id = 1,
            img = "",
            name = "",
            username = ""
        ),
        User(
            id = 1,
            img = "",
            name = "",
            username = ""
        ),
        User(
            id = 1,
            img = "",
            name = "",
            username = ""
        )
    )
    val SUCCESS_CONTACTS_CALL = ResponseHandler.Success(FAKE_CONTACTS)
    val EMPTY_CONTACTS_CALL = ResponseHandler.Empty
}
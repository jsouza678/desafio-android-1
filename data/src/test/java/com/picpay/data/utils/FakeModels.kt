package com.picpay.data.utils

import com.picpay.data.contacts.data.local.entity.UserLocal
import com.picpay.data.contacts.data.remote.response.UserResponse
import com.picpay.domain.entity.User

object FakeModels {
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
}
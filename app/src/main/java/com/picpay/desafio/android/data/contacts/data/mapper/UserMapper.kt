package com.picpay.desafio.android.data.contacts.data.mapper

import com.picpay.desafio.android.data.contacts.data.remote.responses.UserResponse
import com.picpay.desafio.android.domain.entity.User

fun UserResponse.toDomainModel(): User =
    User(
        img = this.img,
        name = this.name,
        id = this.id,
        username = this.username
    )
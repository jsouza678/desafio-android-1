package com.picpay.data.contacts.data.mapper

import com.picpay.data.contacts.data.local.entity.UserLocal
import com.picpay.data.contacts.data.remote.response.UserResponse
import com.picpay.domain.entity.User

fun UserResponse.toDomainModel(): User =
    User(
        id = this.id,
        img = if (this.img.isNullOrBlank()) "Empty image url" else this.img,
        name = this.name ?: "Empty name",
        username = this.username ?: "Empty username"
    )

fun UserResponse.toDatabaseModel(): UserLocal =
    UserLocal(
        id = this.id,
        img = if (this.img.isNullOrBlank()) "Empty image url" else this.img,
        name = this.name ?: "Empty name",
        username = this.username ?: "Empty username"
    )

fun UserLocal.toDomainModel(): User =
    User(
        id = this.id,
        img = this.img,
        name = this.name,
        username = this.username
    )

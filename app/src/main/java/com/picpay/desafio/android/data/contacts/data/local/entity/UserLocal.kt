package com.picpay.desafio.android.data.contacts.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserLocal(
    @PrimaryKey val id: Int,
    val img: String?,
    val name: String,
    val username: String
)

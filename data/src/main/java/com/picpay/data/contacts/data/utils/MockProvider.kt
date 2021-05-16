package com.picpay.data.contacts.data.utils

import com.picpay.domain.entity.User
import java.util.Random

object MockProvider {
    private fun generateNickName(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..8)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun generateName(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..15)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun generateRandomUser() = User(
        id = Random().nextInt(),
        img = "empty",
        name = generateName(),
        username = generateNickName()
    )

    fun generateUsers(count: Int): List<User> {
        val userList = mutableListOf<User>()
        for (i in 0..count) {
            userList.add(generateRandomUser())
        }

        return userList
    }
}

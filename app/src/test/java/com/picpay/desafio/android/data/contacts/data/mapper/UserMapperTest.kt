package com.picpay.desafio.android.data.contacts.data.mapper

import com.picpay.desafio.android.data.contacts.data.remote.responses.UserResponse
import com.picpay.desafio.android.domain.entity.User
import org.junit.Assert
import org.junit.Test

class UserMapperTest {

    @Test
    fun `GIVEN an response contacts list WHEN the mapper function is called THEN it should return the correct domain model`() {
        val givenResponseList = FAKE_CONTACTS_RESPONSE

        val domainModelList = givenResponseList.map {
            it.toDomainModel()
        }

        Assert.assertEquals(domainModelList, FAKE_CONTACTS)
    }

    private companion object {
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
    }
}
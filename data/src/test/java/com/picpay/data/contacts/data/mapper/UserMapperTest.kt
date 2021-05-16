package com.picpay.data.contacts.data.mapper

import com.picpay.data.utils.FakeModels.FAKE_CONTACTS
import com.picpay.data.utils.FakeModels.FAKE_CONTACTS_LOCAL
import com.picpay.data.utils.FakeModels.FAKE_CONTACTS_RESPONSE
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

    @Test
    fun `GIVEN an response contacts list WHEN the mapper function is called THEN it should return the correct database model`() {
        val givenResponseList = FAKE_CONTACTS_RESPONSE

        val domainModelList = givenResponseList.map {
            it.toDatabaseModel()
        }

        Assert.assertEquals(domainModelList, FAKE_CONTACTS_LOCAL)
    }

    @Test
    fun `GIVEN an database contacts list WHEN the mapper function is called THEN it should return the correct domain model`() {
        val givenDatabaseList = FAKE_CONTACTS_LOCAL

        val domainModelList = givenDatabaseList.map {
            it.toDomainModel()
        }

        Assert.assertEquals(domainModelList, FAKE_CONTACTS)
    }
}

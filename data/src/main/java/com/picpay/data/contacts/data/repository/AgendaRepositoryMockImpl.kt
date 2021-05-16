package com.picpay.data.contacts.data.repository

import com.picpay.data.contacts.data.utils.MockProvider
import com.picpay.domain.entity.ResponseHandler
import com.picpay.domain.entity.User
import com.picpay.domain.repository.AgendaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

class AgendaRepositoryMockImpl : AgendaRepository {

    override suspend fun fetchContacts(): Flow<ResponseHandler<List<User>>> {

        return getContacts()
    }

    override suspend fun getContacts(): Flow<ResponseHandler<List<User>>> {

        return flowOf(
            ResponseHandler.Success(
                MockProvider.generateUsers(20)
            )
        )
    }

    override suspend fun getCachedContacts(): Flow<ResponseHandler<List<User>>> {

        return emptyFlow()
    }
}

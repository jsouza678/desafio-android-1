package com.picpay.desafio.android.data.contacts.data.repository

import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User
import com.picpay.desafio.android.domain.repository.AgendaRepository
import com.picpay.desafio.android.utils.MockProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

class AgendaRepositoryMockImpl: AgendaRepository {

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

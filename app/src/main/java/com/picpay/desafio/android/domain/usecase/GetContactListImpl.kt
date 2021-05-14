package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User
import com.picpay.desafio.android.domain.repository.AgendaRepository
import kotlinx.coroutines.flow.Flow

class GetContactListImpl(private val repository: AgendaRepository): GetContactList {

    override suspend fun getContacts(): Flow<ResponseHandler<List<User>>> {
        return repository.fetchContacts()
    }
}
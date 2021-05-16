package com.picpay.domain.usecase

import com.picpay.domain.entity.ResponseHandler
import com.picpay.domain.entity.User
import com.picpay.domain.repository.AgendaRepository
import kotlinx.coroutines.flow.Flow

class GetContactListImpl(private val repository: AgendaRepository) : GetContactList {

    override suspend fun getContacts(): Flow<ResponseHandler<List<User>>> {
        return repository.fetchContacts()
    }
}

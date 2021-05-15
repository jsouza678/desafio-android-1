package com.picpay.domain.repository

import com.picpay.domain.entity.ResponseHandler
import com.picpay.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface AgendaRepository {

    suspend fun fetchContacts(): Flow<ResponseHandler<List<User>>>

    suspend fun getContacts(): Flow<ResponseHandler<List<User>>>

    suspend fun getCachedContacts(): Flow<ResponseHandler<List<User>>>
}

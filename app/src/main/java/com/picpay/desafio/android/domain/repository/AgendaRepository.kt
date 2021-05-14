package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface AgendaRepository {

    suspend fun getContacts(): Flow<ResponseHandler<List<User>>>
}

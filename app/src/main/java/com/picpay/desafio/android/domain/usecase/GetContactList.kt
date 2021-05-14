package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface GetContactList {

    suspend fun getContacts(): Flow<ResponseHandler<List<User>>>
}
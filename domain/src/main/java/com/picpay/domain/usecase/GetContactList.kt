package com.picpay.domain.usecase

import com.picpay.domain.entity.ResponseHandler
import com.picpay.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface GetContactList {

    suspend fun getContacts(): Flow<ResponseHandler<List<User>>>
}

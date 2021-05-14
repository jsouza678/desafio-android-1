package com.picpay.desafio.android.data.contacts.data.repository

import com.picpay.desafio.android.data.contacts.data.mapper.toDomainModel
import com.picpay.desafio.android.data.contacts.data.remote.PicPayService
import com.picpay.desafio.android.data.contacts.data.remote.responses.UserResponse
import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User
import com.picpay.desafio.android.domain.repository.AgendaRepository
import com.picpay.desafio.android.extensions.launchAsyncFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class AgendaRepositoryImpl(private val service: PicPayService) : AgendaRepository {

    override suspend fun getContacts(): Flow<ResponseHandler<List<User>>> {

        return launchAsyncFunction<List<UserResponse>, List<User>>(
            blockToRun = { service.getUsers() },
            mapFunction = { responseList ->
                responseList.map { userResponse ->
                    userResponse.toDomainModel()
                }
            },
            coroutineDispatcher = Dispatchers.IO
        )
    }
}
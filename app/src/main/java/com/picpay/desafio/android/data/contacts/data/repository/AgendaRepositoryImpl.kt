package com.picpay.desafio.android.data.contacts.data.repository

import com.picpay.desafio.android.data.contacts.data.mapper.toDomainModel
import com.picpay.desafio.android.data.contacts.data.remote.PicPayService
import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User
import com.picpay.desafio.android.domain.repository.AgendaRepository
import com.picpay.desafio.android.extensions.launchAsyncFunction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import org.koin.core.KoinComponent
import org.koin.core.inject

class AgendaRepositoryImpl : AgendaRepository, KoinComponent {

    private val service: PicPayService by inject()
    private val coroutineDispatcher: CoroutineDispatcher by inject()

    override suspend fun getContacts(): Flow<ResponseHandler<List<User>>> {
        return launchAsyncFunction(
            blockToRun = { service.getUsersNew() },
            mapFunction = { responseList ->
                responseList.map { userResponse ->
                    userResponse.toDomainModel()
                }
            },
            coroutineDispatcher = coroutineDispatcher
        )
    }
}
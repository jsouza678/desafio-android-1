package com.picpay.desafio.android.data.contacts.data.repository

import androidx.lifecycle.Transformations
import androidx.lifecycle.asFlow
import com.picpay.desafio.android.data.contacts.data.local.dao.ContactsDao
import com.picpay.desafio.android.data.contacts.data.mapper.toDatabaseModel
import com.picpay.desafio.android.data.contacts.data.mapper.toDomainModel
import com.picpay.desafio.android.data.contacts.data.remote.PicPayService
import com.picpay.desafio.android.data.contacts.data.remote.response.UserResponse
import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User
import com.picpay.desafio.android.domain.repository.AgendaRepository
import com.picpay.desafio.android.extensions.launchAsyncFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class AgendaRepositoryImpl(
    private val service: PicPayService,
    private val dao: ContactsDao
) : AgendaRepository {

    override suspend fun fetchContacts(): Flow<ResponseHandler<List<User>>> {
        val shouldFetchFromApi = dao.getContacts().isEmpty()
        return if (shouldFetchFromApi) {
            getContacts()
        } else getCachedContacts()
    }

    override suspend fun getContacts(): Flow<ResponseHandler<List<User>>> {

        return launchAsyncFunction<List<UserResponse>, List<User>>(
            blockToRun = { service.getUsers() },
            mapFunction = { responseList ->
                val responseLocal = responseList.map { userResponse ->
                    userResponse.toDatabaseModel()
                }
                dao.insertContacts(*responseLocal.toTypedArray())

                dao.getContacts().map { it.toDomainModel() }

                responseList.map { it.toDomainModel() }
            },
            coroutineDispatcher = Dispatchers.IO
        )
    }

    override suspend fun getCachedContacts(): Flow<ResponseHandler<List<User>>> {

        return Transformations.map(dao.getContactsAsync()) { localList ->
            val mappedList = localList?.map { localUser ->
                localUser.toDomainModel()
            } ?: emptyList()

            ResponseHandler.Success(mappedList)
        }.asFlow()
    }
}

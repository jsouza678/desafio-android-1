package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.data.contacts.data.local.dao.ContactsDao
import com.picpay.desafio.android.data.contacts.data.remote.PicPayService
import com.picpay.desafio.android.data.contacts.data.repository.AgendaRepositoryMockImpl
import com.picpay.desafio.android.domain.repository.AgendaRepository
import org.koin.dsl.module

val contactsModule = module {

    single {
        AgendaRepositoryMockImpl(
            service = get<PicPayService>(),
            dao = get<ContactsDao>()
        ) as AgendaRepository
    }
}
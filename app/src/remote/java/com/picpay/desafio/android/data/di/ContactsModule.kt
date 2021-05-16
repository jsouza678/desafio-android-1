package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.data.contacts.data.local.dao.ContactsDao
import com.picpay.desafio.android.data.contacts.data.remote.PicPayService
import com.picpay.desafio.android.data.contacts.data.repository.AgendaRepositoryImpl
import com.picpay.domain.repository.AgendaRepository
import org.koin.dsl.module

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val contactsModule = module {

    single {
        AgendaRepositoryImpl(
            service = get<PicPayService>(),
            dao = get<ContactsDao>()
        ) as AgendaRepository
    }
}
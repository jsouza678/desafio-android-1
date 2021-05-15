package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.data.contacts.data.repository.AgendaRepositoryMockImpl
import com.picpay.desafio.android.domain.repository.AgendaRepository
import org.koin.dsl.module

val contactsModule = module {

    single {
        AgendaRepositoryMockImpl() as AgendaRepository
    }
}
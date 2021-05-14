package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.data.contacts.data.remote.PicPayService
import com.picpay.desafio.android.data.contacts.data.repository.AgendaRepositoryImpl
import com.picpay.desafio.android.domain.repository.AgendaRepository
import org.koin.dsl.module

val contactsModule = module {

    single {
        AgendaRepositoryImpl(get<PicPayService>()) as AgendaRepository
    }
}
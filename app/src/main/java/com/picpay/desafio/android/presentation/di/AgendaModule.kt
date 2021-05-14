package com.picpay.desafio.android.presentation.di

import com.picpay.desafio.android.domain.repository.AgendaRepository
import com.picpay.desafio.android.domain.usecase.GetContactList
import com.picpay.desafio.android.domain.usecase.GetContactListImpl
import com.picpay.desafio.android.presentation.home.AgendaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val agendaModule = module {

    single {
        GetContactListImpl(get<AgendaRepository>()) as GetContactList
    }

    viewModel {
        AgendaViewModel(get<GetContactList>())
    }
}
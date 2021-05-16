package com.picpay.agenda.presentation.di

import com.picpay.agenda.presentation.home.AgendaViewModel
import com.picpay.domain.repository.AgendaRepository
import com.picpay.domain.usecase.GetContactList
import com.picpay.domain.usecase.GetContactListImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val agendaModule = module {

    single {
        GetContactListImpl(get<AgendaRepository>()) as GetContactList
    }

    viewModel {
        AgendaViewModel(get<GetContactList>())
    }
}

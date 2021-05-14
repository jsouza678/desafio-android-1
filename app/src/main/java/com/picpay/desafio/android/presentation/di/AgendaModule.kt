package com.picpay.desafio.android.presentation.di

import com.picpay.desafio.android.domain.repository.AgendaRepository
import com.picpay.desafio.android.presentation.home.AgendaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val agendaModule = module {

    viewModel {
        AgendaViewModel(get<AgendaRepository>())
    }
}
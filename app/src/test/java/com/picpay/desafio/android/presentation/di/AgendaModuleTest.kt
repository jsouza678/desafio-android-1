package com.picpay.desafio.android.presentation.di

import com.picpay.desafio.android.data.di.contactsModule
import com.picpay.desafio.android.data.di.networkModule
import com.picpay.desafio.android.presentation.home.AgendaViewModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class AgendaModuleTest: KoinTest {

    private val viewModel: AgendaViewModel by inject()

    @Before
    fun setup() {
        startKoin {
            modules(networkModule, agendaModule, contactsModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `WHEN koin application is started it SHOULD instantiate viewModel`() {
        Assert.assertNotNull(viewModel)
    }

}
package com.picpay.desafio.android.data.di

import com.picpay.desafio.android.domain.repository.AgendaRepository
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class ContactsModuleTest : KoinTest {

    private val agendaRepository: AgendaRepository by inject()

    @Before
    fun setup() {
        startKoin {
            modules(
                contactsModule,
                networkModule
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `WHEN koin application is started it SHOULD instantiate repository`() {
        Assert.assertNotNull(agendaRepository)
    }
}

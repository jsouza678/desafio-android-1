package com.picpay.desafio.android.data.di

import android.app.Application
import com.picpay.domain.repository.AgendaRepository
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ContactsModuleTest : KoinTest {

    @Mock
    private lateinit var context: Application
    private val agendaRepository: AgendaRepository by inject()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(
                databaseModule,
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

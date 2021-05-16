package com.picpay.desafio.android.di

import android.app.Application
import com.picpay.data.di.databaseModule
import com.picpay.data.di.networkModule
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.data.di.contactsModule
import com.picpay.desafio.android.utils.Constants
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

    private val agendaRepository: AgendaRepository by inject()
    @Mock private lateinit var context: Application

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
            properties(
                mapOf(Constants.BASE_URL to BuildConfig.API_BASE_URL)
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

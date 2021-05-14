package com.picpay.desafio.android.presentation.di

import android.app.Application
import com.picpay.desafio.android.data.di.contactsModule
import com.picpay.desafio.android.data.di.databaseModule
import com.picpay.desafio.android.data.di.networkModule
import com.picpay.desafio.android.presentation.home.AgendaViewModel
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

class AgendaModuleTest: KoinTest {

    @Mock
    private lateinit var context: Application
    private val viewModel: AgendaViewModel by inject()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(
                networkModule,
                agendaModule,
                contactsModule,
                databaseModule
            )
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
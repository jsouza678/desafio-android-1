package com.picpay.desafio.android.di

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.agenda.presentation.di.agendaModule
import com.picpay.data.di.databaseModule
import com.picpay.data.di.networkModule
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.data.di.contactsModule
import com.picpay.desafio.android.utils.Constants
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GeneralModulesTest : KoinTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @Mock private lateinit var context: Application

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `GIVEN koin modules WHEN koin application is started it THEN it should instantiate these modules correctly`() {
        val koinModules = listOf(
            databaseModule,
            networkModule,
            agendaModule,
            contactsModule
        )

        startKoin {
            androidContext(context)
            modules(koinModules)
            properties(
                mapOf(Constants.BASE_URL to BuildConfig.API_BASE_URL)
            )
        }.checkModules()
    }
}

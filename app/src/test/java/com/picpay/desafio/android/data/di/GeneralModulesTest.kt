package com.picpay.desafio.android.data.di

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.presentation.di.agendaModule
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class GeneralModulesTest: KoinTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `GIVEN koin modules WHEN koin application is started it THEN it should instantiate these modules correctly`() {
        val koinModules = listOf(
            networkModule,
            agendaModule,
            contactsModule
        )

        startKoin {
            modules(koinModules)
        }.checkModules()
    }
}
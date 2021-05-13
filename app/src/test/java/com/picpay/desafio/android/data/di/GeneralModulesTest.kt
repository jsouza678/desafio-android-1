package com.picpay.desafio.android.data.di

import org.junit.After
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class GeneralModulesTest: KoinTest {

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `GIVEN koin modules WHEN koin application is started it THEN it should instantiate these modules correctly`() {
        val koinModules = listOf(
            networkModule
        )

        startKoin {
            modules(koinModules)
        }.checkModules()
    }
}
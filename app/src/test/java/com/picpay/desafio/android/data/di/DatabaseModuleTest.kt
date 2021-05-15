package com.picpay.desafio.android.data.di

import android.app.Application
import com.picpay.desafio.android.data.contacts.data.local.dao.ContactsDao
import com.picpay.desafio.android.data.contacts.data.local.database.AgendaDatabase
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

class DatabaseModuleTest: KoinTest {

    private val dao: ContactsDao by inject()
    private val database: AgendaDatabase by inject()
    @Mock private lateinit var context: Application

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(
                databaseModule
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `WHEN koin application is started it SHOULD instantiate database`() {
        Assert.assertNotNull(database)
    }

    @Test
    fun `WHEN koin application is started it SHOULD instantiate dao`() {
        Assert.assertNotNull(dao)
    }
}
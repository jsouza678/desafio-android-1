package com.picpay.desafio.android.data.di

import com.google.gson.Gson
import com.picpay.desafio.android.data.contacts.data.remote.PicPayService
import com.picpay.desafio.android.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit

class NetworkModuleTest : KoinTest {

    private val retrofit: Retrofit by inject()
    private val okHttpClient: OkHttpClient by inject()
    private val gson: Gson by inject()
    private val service: PicPayService by inject()

    @Before
    fun setup() {
        startKoin {
            modules(networkModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `WHEN koin application is started it SHOULD instantiate Retrofit`() {
        Assert.assertNotNull(retrofit)
    }

    @Test
    fun `WHEN koin application is started it SHOULD instantiate PicPayService`() {
        Assert.assertNotNull(service)
    }

    @Test
    fun `WHEN koin application is started it SHOULD instantiate Gson`() {
        Assert.assertNotNull(gson)
    }

    @Test
    fun `WHEN koin application is started it SHOULD instantiate OkHttpClient`() {
        Assert.assertNotNull(okHttpClient)
    }

    @Test
    fun `WHEN okHttpClient is instantiated it SHOULD have connect, write and read timeouts set with correct values`() {
        Assert.assertTrue(okHttpClient.connectTimeoutMillis == (Constants.DEFAULT_TIMEOUT * 1000).toInt())
        Assert.assertTrue(okHttpClient.readTimeoutMillis == (Constants.DEFAULT_TIMEOUT * 1000).toInt())
        Assert.assertTrue(okHttpClient.writeTimeoutMillis == (Constants.DEFAULT_TIMEOUT * 1000).toInt())
    }
}

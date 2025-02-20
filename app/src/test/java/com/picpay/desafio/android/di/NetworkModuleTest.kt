package com.picpay.desafio.android.di

import com.google.gson.Gson
import com.picpay.data.contacts.data.remote.PicPayService
import com.picpay.data.contacts.data.utils.Constants
import com.picpay.data.di.networkModule
import com.picpay.data.utils.Constants.INTERCEPTOR_INSTANCE
import com.picpay.data.utils.Constants.OKHTTP_INSTANCE
import com.picpay.data.utils.Constants.RETROFIT_INSTANCE
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit

class NetworkModuleTest : KoinTest {

    private val retrofit: Retrofit by inject(named(RETROFIT_INSTANCE))
    private val okHttpClient: OkHttpClient by inject(named(OKHTTP_INSTANCE))
    private val interceptor: HttpLoggingInterceptor by inject(named(INTERCEPTOR_INSTANCE))
    private val gson: Gson by inject()
    private val service: PicPayService by inject()

    @Before
    fun setup() {
        startKoin {
            modules(networkModule)
            properties(
                mapOf(BASE_URL to BuildConfig.API_BASE_URL)
            )
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
    fun `WHEN koin application is started it SHOULD instantiate interceptor`() {
        Assert.assertNotNull(interceptor)
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

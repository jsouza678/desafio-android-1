package com.picpay.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.data.contacts.data.remote.PicPayService
import com.picpay.data.contacts.data.utils.Constants.BASE_URL
import com.picpay.data.contacts.data.utils.Constants.DEFAULT_TIMEOUT
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val networkModule = module {

    single {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

        okHttpClient.build()
    }

    single {
        GsonBuilder().create()
    }

    single {
        val baseUrl = getProperty(BASE_URL)

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
    }

    single {
        getPicPayService(get<Retrofit>())
    }
}

private fun getPicPayService(retrofit: Retrofit): PicPayService =
    retrofit.create(PicPayService::class.java)

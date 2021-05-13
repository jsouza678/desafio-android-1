package com.picpay.desafio.android.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.contacts.data.remote.PicPayService
import com.picpay.desafio.android.utils.Constants.BASE_URL
import com.picpay.desafio.android.utils.Constants.DEFAULT_TIMEOUT
import org.koin.dsl.module
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
        Retrofit.Builder()
            .baseUrl(BASE_URL)
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
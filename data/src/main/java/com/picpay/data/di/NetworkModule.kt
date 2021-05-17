package com.picpay.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.data.contacts.data.remote.PicPayService
import com.picpay.data.contacts.data.utils.Constants.BASE_URL
import com.picpay.data.contacts.data.utils.Constants.DEFAULT_TIMEOUT
import com.picpay.data.utils.Constants.INTERCEPTOR_INSTANCE
import com.picpay.data.utils.Constants.OKHTTP_INSTANCE
import com.picpay.data.utils.Constants.RETROFIT_INSTANCE
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val networkModule = module {

    single(named(OKHTTP_INSTANCE)) {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

        okHttpClient.addInterceptor(get<HttpLoggingInterceptor>(named(INTERCEPTOR_INSTANCE)))
        okHttpClient.build()
    }

    single(named(INTERCEPTOR_INSTANCE)) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }

    single {
        GsonBuilder().create()
    }

    single(named(RETROFIT_INSTANCE)) {
        val baseUrl = getProperty(BASE_URL)

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get<OkHttpClient>(named(OKHTTP_INSTANCE)))
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
    }

    single {
        getPicPayService(get<Retrofit>(named(RETROFIT_INSTANCE)))
    }
}

private fun getPicPayService(retrofit: Retrofit): PicPayService =
    retrofit.create(PicPayService::class.java)

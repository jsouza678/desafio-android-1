package com.picpay.desafio.android.base

import android.app.Application
import com.picpay.desafio.android.data.di.contactsModule
import com.picpay.desafio.android.data.di.databaseModule
import com.picpay.desafio.android.data.di.networkModule
import com.picpay.desafio.android.presentation.di.agendaModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    contactsModule,
                    databaseModule,
                    networkModule,
                    agendaModule
                )
            )
        }
    }
}

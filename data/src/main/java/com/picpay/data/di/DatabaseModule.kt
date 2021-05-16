package com.picpay.data.di

import androidx.room.Room
import com.picpay.data.contacts.data.local.database.AgendaDatabase
import com.picpay.data.contacts.data.utils.Constants.DATABASE_NAME
import org.koin.dsl.module

val databaseModule = module {

    single {
        get<AgendaDatabase>().contactsDao()
    }

    single {
        Room.databaseBuilder(
            get(),
            AgendaDatabase::class.java,
            DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}

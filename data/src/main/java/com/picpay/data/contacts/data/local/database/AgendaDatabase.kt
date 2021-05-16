package com.picpay.data.contacts.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.data.contacts.data.local.dao.ContactsDao
import com.picpay.data.contacts.data.local.entity.UserLocal
import com.picpay.data.contacts.data.utils.Constants.DATABASE_VERSION

@Database(
    entities = [UserLocal::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AgendaDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
}

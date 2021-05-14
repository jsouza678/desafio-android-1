package com.picpay.desafio.android.data.contacts.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.data.contacts.data.local.entity.UserLocal

@Dao
interface ContactsDao {

    @Query("SELECT * FROM userLocal ORDER BY username ASC")
    fun getContactsAsync(): LiveData<List<UserLocal>?>

    @Query("SELECT * FROM userLocal ORDER BY username ASC")
    fun getContacts(): List<UserLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(vararg contactList: UserLocal)
}
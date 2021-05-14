package com.picpay.desafio.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.picpay.desafio.android.base.BaseViewModel
import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User
import com.picpay.desafio.android.domain.repository.AgendaRepository
import kotlinx.coroutines.flow.collect

class AgendaViewModel(private val repository: AgendaRepository): BaseViewModel() {

    private val contacts: MutableLiveData<ResponseHandler<List<User>>> = MutableLiveData()

    init {
        getContactData()
    }

    fun getContacts(): LiveData<ResponseHandler<List<User>>> = contacts

    private fun getContactData() {
        launch {
            repository.fetchContacts().collect {
                if (it is ResponseHandler.Success && it.value.isEmpty()) contacts.postValue(ResponseHandler.Empty)
                else contacts.postValue(it)
            }
        }
    }
}
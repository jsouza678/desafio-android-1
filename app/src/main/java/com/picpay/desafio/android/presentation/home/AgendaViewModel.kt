package com.picpay.desafio.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.picpay.desafio.android.base.BaseViewModel
import com.picpay.domain.entity.ResponseHandler
import com.picpay.domain.entity.User
import com.picpay.domain.usecase.GetContactList
import kotlinx.coroutines.flow.collect

class AgendaViewModel(private val useCase: GetContactList): BaseViewModel() {

    private val contacts: MutableLiveData<ResponseHandler<List<User>>> = MutableLiveData()

    init {
        getContactData()
    }

    fun getContacts(): LiveData<ResponseHandler<List<User>>> = contacts

    private fun getContactData() {
        launch {
            useCase.getContacts().collect {
                if (it is ResponseHandler.Success && it.value.isEmpty()) {
                    contacts.postValue(ResponseHandler.Empty)
                } else contacts.postValue(it)
            }
        }
    }
}
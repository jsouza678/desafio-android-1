package com.picpay.desafio.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.picpay.desafio.android.base.BaseViewModel
import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User
import com.picpay.desafio.android.domain.usecase.GetContactList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class AgendaViewModel(
    private val useCase: GetContactList,
    private val coroutineScope: CoroutineScope? = CoroutineScope(Dispatchers.IO)
): BaseViewModel() {

    private val contacts: MutableLiveData<ResponseHandler<List<User>>> = MutableLiveData()

    init {
        getContactData()
    }

    fun getContacts(): LiveData<ResponseHandler<List<User>>> = contacts

    internal fun getContactData() {
        launch(context = coroutineScope?.coroutineContext ?: Dispatchers.IO) {
            useCase.getContacts().collect {
                if (it is ResponseHandler.Success && it.value.isEmpty()) {
                    contacts.postValue(ResponseHandler.Empty)
                } else contacts.postValue(it)
            }
        }
    }
}
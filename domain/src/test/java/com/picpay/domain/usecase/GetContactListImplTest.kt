package com.picpay.domain.usecase

import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.spy
import com.picpay.domain.repository.AgendaRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.verify

class GetContactListImplTest {

    private val repository: AgendaRepository = spy()
    private val useCase: GetContactList by lazy {
        GetContactListImpl(repository)
    }

    @Test
    fun `WHEN usecase is called it SHOULD call fetch function on repository`() {
        runBlocking {
            useCase.getContacts()

            verify(repository, atLeastOnce()).fetchContacts()
        }
    }
}

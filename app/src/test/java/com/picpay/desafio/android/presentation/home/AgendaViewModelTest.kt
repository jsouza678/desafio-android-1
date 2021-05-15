package com.picpay.desafio.android.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User
import com.picpay.desafio.android.domain.usecase.GetContactList
import com.picpay.desafio.android.domain.usecase.GetContactListImpl
import com.picpay.desafio.android.utils.FakeModels
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AgendaViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    var useCase: GetContactList = mockk<GetContactListImpl>(relaxed = true)
    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)
    private val viewModel: AgendaViewModel = spyk(
        AgendaViewModel(
            useCase,
            testCoroutineScope
        )
    )
    @Mock lateinit var observer: Observer<ResponseHandler<List<User>>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel.getContacts().observeForever(observer)
    }

    @After
    fun tearDown() {
        unmockkAll()
        viewModel.getContacts().removeObserver(observer)
    }

    @Test
    fun `GIVEN a flow result with loading and success WHEN the getContactData is called THEN should return these two results`() =
        testDispatcher.runBlockingTest {
            val flow: Flow<ResponseHandler<List<User>>> = flow {
                emit(ResponseHandler.Loading)
                emit(ResponseHandler.Success(FakeModels.FAKE_CONTACTS))
            }

            coEvery {
                useCase.getContacts()
            } returns flow

            viewModel.getContactData()
            val flowResult = useCase.getContacts().toList()


            verify(observer, times(1)).onChanged(ResponseHandler.Loading)
            verify(observer, atLeastOnce()).onChanged(ResponseHandler.Success(FakeModels.FAKE_CONTACTS))
            Assert.assertEquals(flowResult.first(), ResponseHandler.Loading)
            Assert.assertEquals(flowResult.last(), ResponseHandler.Success(FakeModels.FAKE_CONTACTS))
        }

    @Test
    fun `GIVEN a flow result with loading and empty list WHEN the getContactData is called THEN should return these two results`() =
        testDispatcher.runBlockingTest {
            val flow: Flow<ResponseHandler<List<User>>> = flow {
                emit(ResponseHandler.Loading)
                emit(ResponseHandler.Success(emptyList<User>()))
            }

            coEvery {
                useCase.getContacts()
            } returns flow

            viewModel.getContactData()
            val flowResult = useCase.getContacts().toList()

            verify(observer, times(1)).onChanged(ResponseHandler.Loading)
            verify(observer, atLeastOnce()).onChanged(ResponseHandler.Empty)
            Assert.assertEquals(flowResult.first(), ResponseHandler.Loading)
            Assert.assertEquals(flowResult.last(), ResponseHandler.Success(emptyList<User>()))
        }

    @Test
    fun `GIVEN a flow result with loading and error WHEN the getContactData is called THEN should return these two results`() =
        testDispatcher.runBlockingTest {
            val flow: Flow<ResponseHandler<List<User>>> = flow {
                emit(ResponseHandler.Loading)
                emit(FakeModels.GENERIC_ERROR_RESPONSE)
            }

            coEvery {
                useCase.getContacts()
            } returns flow

            viewModel.getContactData()
            val flowResult = useCase.getContacts().toList()

            verify(observer, times(1)).onChanged(ResponseHandler.Loading)
            verify(observer, atLeastOnce()).onChanged(FakeModels.GENERIC_ERROR_RESPONSE)
            Assert.assertEquals(flowResult.first(), ResponseHandler.Loading)
            Assert.assertEquals(flowResult.last(), FakeModels.GENERIC_ERROR_RESPONSE)
        }
}
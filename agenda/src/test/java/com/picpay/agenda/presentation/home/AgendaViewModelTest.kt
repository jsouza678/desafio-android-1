package com.picpay.agenda.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.picpay.base.extensions.ErrorHandler
import com.picpay.base.utils.Constants
import com.picpay.data.contacts.data.remote.response.UserResponse
import com.picpay.domain.entity.ApiError
import com.picpay.domain.entity.ResponseHandler
import com.picpay.domain.entity.User
import com.picpay.domain.usecase.GetContactList
import com.picpay.domain.usecase.GetContactListImpl
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

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
                emit(ResponseHandler.Success(FAKE_CONTACTS))
            }

            coEvery {
                useCase.getContacts()
            } returns flow

            viewModel.getContactData()
            val flowResult = useCase.getContacts().toList()


            verify(observer, times(1)).onChanged(ResponseHandler.Loading)
            verify(observer, atLeastOnce()).onChanged(ResponseHandler.Success(FAKE_CONTACTS))
            Assert.assertEquals(flowResult.first(), ResponseHandler.Loading)
            Assert.assertEquals(flowResult.last(), ResponseHandler.Success(FAKE_CONTACTS))
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
                emit(GENERIC_ERROR_RESPONSE)
            }

            coEvery {
                useCase.getContacts()
            } returns flow

            viewModel.getContactData()
            val flowResult = useCase.getContacts().toList()

            verify(observer, times(1)).onChanged(ResponseHandler.Loading)
            verify(observer, atLeastOnce()).onChanged(GENERIC_ERROR_RESPONSE)
            Assert.assertEquals(flowResult.first(), ResponseHandler.Loading)
            Assert.assertEquals(flowResult.last(), GENERIC_ERROR_RESPONSE)
        }

    private companion object {
        val FAKE_CONTACTS = listOf(
            User(
                id = 1,
                img = "test",
                name = "João Souza",
                username = "Souza"
            ),
            User(
                id = 2,
                img = "Empty image url",
                name = "Empty name",
                username = "Empty username"
            ),
            User(
                id = 3,
                img = "Empty image url",
                name = "Empty name",
                username = "Empty username"
            )
        )

        val GENERIC_ERROR: Response<UserResponse> = Response.error(
            404,
            "{\"message\":\"Api Generic Error\"}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val GENERIC_ERROR_RESPONSE = ResponseHandler.Error(
            ApiError.HttpError(
                code = 404,
                message = Constants.GENERIC_NETWORK_ERROR,
                exception = ErrorHandler().getErrorFromApi(HttpException(GENERIC_ERROR)).exception
            )
        )
    }
}
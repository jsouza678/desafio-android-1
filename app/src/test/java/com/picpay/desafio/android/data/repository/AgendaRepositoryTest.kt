package com.picpay.desafio.android.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.picpay.data.contacts.data.local.dao.ContactsDao
import com.picpay.data.contacts.data.local.entity.UserLocal
import com.picpay.data.contacts.data.remote.PicPayService
import com.picpay.data.contacts.data.remote.response.UserResponse
import com.picpay.data.contacts.data.repository.AgendaRepositoryImpl
import com.picpay.data.di.databaseModule
import com.picpay.data.di.networkModule
import com.picpay.domain.entity.ApiError
import com.picpay.domain.entity.ResponseHandler
import com.picpay.domain.repository.AgendaRepository
import com.picpay.base.extensions.ErrorHandler
import com.picpay.base.utils.Constants
import com.picpay.desafio.android.data.di.contactsModule
import com.picpay.desafio.android.utils.FakeModels.EMPTY_CONTACTS_CALL
import com.picpay.desafio.android.utils.FakeModels.FAKE_CONTACTS_LOCAL
import com.picpay.desafio.android.utils.FakeModels.FAKE_CONTACTS_RESPONSE
import com.picpay.desafio.android.utils.FakeModels.FAKE_EMPTY_CONTACTS
import com.picpay.desafio.android.utils.FakeModels.SUCCESS_CONTACTS_CALL
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito.verify
import retrofit2.HttpException
import retrofit2.Response

@KoinApiExtension
class AgendaRepositoryTest : KoinTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val service: PicPayService = mock()
    private val dao: ContactsDao = mock()
    private val agendaRepository: AgendaRepository = spy(
        AgendaRepositoryImpl(
            service = service,
            dao = dao
        )
    )

    @Before
    fun setup() {
        startKoin {
            modules(
                databaseModule,
                networkModule,
                contactsModule
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        unmockkAll()
    }

    @Test
    fun `GIVEN empty contact list WHEN query is made THEN should fetch data from api and return loading and then success`() =
        runBlocking {
            val listReturned = emptyList<UserLocal>()
            val response = Response.success(FAKE_CONTACTS_RESPONSE)

            whenever(dao.getContacts()).thenReturn(listReturned)
            whenever(service.getUsers()).thenReturn(response)

            agendaRepository.fetchContacts()

            val flowResponse = agendaRepository.getContacts().toList()
            val loadingState = flowResponse.first()
            val dataState = flowResponse.last()

            assertSame(loadingState, ResponseHandler.Loading)
            assertEquals(dataState, SUCCESS_CONTACTS_CALL)
        }

    @Test
    fun `GIVEN empty contact list WHEN query is made THEN should not do the request`() {

        val listReturned = FAKE_CONTACTS_LOCAL

        whenever(dao.getContacts()).thenReturn(listReturned)

        runBlocking {
            agendaRepository.fetchContacts()

            verify(dao, atLeastOnce()).getContacts()
            verify(service, never()).getUsers()
        }
    }

    @Test
    fun `GIVEN contact success response WHEN api call is made THEN should return loading and then success`() =
        runBlocking {
            val response = Response.success(FAKE_CONTACTS_RESPONSE)

            whenever(service.getUsers()).thenReturn(response)

            val flowResponse = agendaRepository.getContacts().toList()
            val loadingState = flowResponse.first()
            val dataState = flowResponse.last()

            assertSame(loadingState, ResponseHandler.Loading)
            assertEquals(dataState, SUCCESS_CONTACTS_CALL)
        }

    @Test
    fun `GIVEN contact error response WHEN api call is made THEN should return loading and then generic error`() =
        runBlocking {
            val errorResponse: Response<UserResponse> = GENERIC_ERROR
            val exceptionResponse = HttpException(errorResponse)

            whenever(service.getUsers()).thenThrow(exceptionResponse)

            val flowResponse = agendaRepository.getContacts().toList()
            val loadingState = flowResponse.first()
            val errorState = flowResponse.last()

            assertSame(loadingState, ResponseHandler.Loading)
            assertEquals(errorState, GENERIC_ERROR_RESPONSE)
        }

    @Test
    fun `GIVEN contact error response WHEN api call is made THEN should return loading and then an error that could not be parsed`() =
        runBlocking {
            val errorResponse: Response<UserResponse> = GENERIC_UNEXPECTED_ERROR
            val exceptionResponse = HttpException(errorResponse)

            whenever(service.getUsers()).thenThrow(exceptionResponse)

            val flowResponse = agendaRepository.getContacts().toList()
            val loadingState = flowResponse.first()
            val errorState = flowResponse.last()

            assertSame(loadingState, ResponseHandler.Loading)
            assertEquals(errorState, GENERIC_UNEXPECTED_ERROR_RESPONSE)
        }

    @Test
    fun `GIVEN contact error response WHEN api call is made THEN should return loading and then an error that could not be empty`() =
        runBlocking {
            val response = Response.success(FAKE_EMPTY_CONTACTS)

            whenever(service.getUsers()).thenReturn(response)

            val flowResponse = agendaRepository.getContacts().toList()
            val loadingState = flowResponse.first()
            val dataState = flowResponse.last()

            assertSame(loadingState, ResponseHandler.Loading)
            assertEquals(dataState, EMPTY_CONTACTS_CALL)
        }

    private companion object {
        val GENERIC_ERROR: Response<UserResponse> = Response.error(
            404,
            "{\"message\":\"Api Generic Error\"}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val GENERIC_UNEXPECTED_ERROR: Response<UserResponse> = Response.error(
            409,
            "{\"xxxxxxxxx\":\"123 test\"}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        val GENERIC_ERROR_RESPONSE = ResponseHandler.Error(
            ApiError.HttpError(
                code = 404,
                message = Constants.GENERIC_NETWORK_ERROR,
                exception = ErrorHandler().getErrorFromApi(HttpException(GENERIC_ERROR)).exception
            )
        )
        val GENERIC_UNEXPECTED_ERROR_RESPONSE = ResponseHandler.Error(
            ApiError.HttpError(
                code = 409,
                message = Constants.DEFAULT_ERROR,
                exception = ErrorHandler().getErrorFromApi(HttpException(GENERIC_UNEXPECTED_ERROR)).exception
            )
        )
    }
}
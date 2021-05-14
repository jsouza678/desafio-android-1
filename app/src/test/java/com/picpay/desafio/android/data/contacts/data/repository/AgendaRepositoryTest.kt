package com.picpay.desafio.android.data.contacts.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.contacts.data.remote.PicPayService
import com.picpay.desafio.android.data.contacts.data.remote.responses.UserResponse
import com.picpay.desafio.android.data.di.contactsModule
import com.picpay.desafio.android.data.di.networkModule
import com.picpay.desafio.android.domain.entity.ApiError
import com.picpay.desafio.android.domain.entity.ResponseHandler
import com.picpay.desafio.android.domain.entity.User
import com.picpay.desafio.android.domain.repository.AgendaRepository
import com.picpay.desafio.android.extensions.ErrorHandler
import com.picpay.desafio.android.utils.Constants
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import retrofit2.HttpException
import retrofit2.Response

class AgendaRepositoryTest: KoinTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val service: PicPayService = mock()
    private val agendaRepository: AgendaRepository = spy(AgendaRepositoryImpl(service))

    @Before
    fun setup() {
        startKoin {
            modules(
                networkModule,
                contactsModule
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `GIVEN contact success response WHEN api call is made THEN should return loading and then success`() = runBlocking {
        val response = Response.success(FAKE_CONTACTS_RESPONSE)

        whenever(service.getUsers()).thenReturn(response)

        val flowResponse = agendaRepository.getContacts().toList()

        val loadingState = flowResponse.first()
        val dataState = flowResponse.last()

        assertSame(loadingState, ResponseHandler.Loading)
        assertEquals(dataState, SUCCESS_CONTACTS_CALL)
    }

    @Test
    fun `GIVEN contact error response WHEN api call is made THEN should return loading and then generic error`() = runBlocking {
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
    fun `GIVEN contact error response WHEN api call is made THEN should return loading and then an error that could not be parsed`() = runBlocking {
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
    fun `GIVEN contact error response WHEN api call is made THEN should return loading and then an error that could not be empty`() = runBlocking {
        val response = Response.success(FAKE_EMPTY_CONTACTS)

        whenever(service.getUsers()).thenReturn(response)

        val flowResponse = agendaRepository.getContacts().toList()

        val loadingState = flowResponse.first()
        val dataState = flowResponse.last()

        assertSame(loadingState, ResponseHandler.Loading)
        assertEquals(dataState, EMPTY_CONTACTS_CALL)
    }

    private companion object {
        val FAKE_EMPTY_CONTACTS: List<UserResponse>? = null
        val FAKE_CONTACTS_RESPONSE = listOf(
            UserResponse(
                id = 1,
                img = "",
                name = "",
                username = ""
            ),
            UserResponse(
                id = 1,
                img = "",
                name = "",
                username = ""
            ),
            UserResponse(
                id = 1,
                img = "",
                name = "",
                username = ""
            )
        )
        val FAKE_CONTACTS = listOf(
            User(
                id = 1,
                img = "",
                name = "",
                username = ""
            ),
            User(
                id = 1,
                img = "",
                name = "",
                username = ""
            ),
            User(
                id = 1,
                img = "",
                name = "",
                username = ""
            )
        )
        val SUCCESS_CONTACTS_CALL = ResponseHandler.Success(FAKE_CONTACTS)
        val EMPTY_CONTACTS_CALL = ResponseHandler.Empty
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
        ))
        val GENERIC_UNEXPECTED_ERROR_RESPONSE = ResponseHandler.Error(
            ApiError.HttpError(
            code = 409,
            message = Constants.DEFAULT_ERROR,
            exception = ErrorHandler().getErrorFromApi(HttpException(GENERIC_UNEXPECTED_ERROR)).exception
        ))
    }
}
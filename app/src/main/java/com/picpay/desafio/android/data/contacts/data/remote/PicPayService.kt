package com.picpay.desafio.android.data.contacts.data.remote

import com.picpay.desafio.android.data.contacts.data.remote.responses.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface PicPayService {

    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>
}
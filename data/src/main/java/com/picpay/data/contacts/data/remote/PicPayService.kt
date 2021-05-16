package com.picpay.data.contacts.data.remote

import com.picpay.data.contacts.data.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface PicPayService {

    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>
}
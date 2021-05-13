package com.picpay.desafio.android.data.contacts.data.remote

import com.picpay.desafio.android.data.contacts.data.remote.responses.UserResponse
import com.picpay.desafio.android.domain.entity.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface PicPayService {

    @GET("users")
    fun getUsersNew(): Response<List<UserResponse>>

    @GET("users")
    fun getUsers(): Call<List<User>>
}
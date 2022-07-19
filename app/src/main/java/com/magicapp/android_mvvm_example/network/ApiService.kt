package com.magicapp.android_mvvm_example.network

import com.magicapp.android_mvvm_example.database.entity.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}
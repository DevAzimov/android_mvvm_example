package com.magicapp.android_mvvm_example.repositories

import com.magicapp.android_mvvm_example.database.AppDatabase
import com.magicapp.android_mvvm_example.database.entity.User
import com.magicapp.android_mvvm_example.network.ApiService

class UserRepository(private val apiService: ApiService, private val appDatabase: AppDatabase) {
    suspend fun getRemoteUsers() = apiService.getUsers()

    suspend fun getLocalUsers() = appDatabase.userDao().getAllUsers()

    suspend fun addUsers(list: List<User>) = appDatabase.userDao().addUsers(list)
}
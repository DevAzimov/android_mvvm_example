package com.magicapp.android_mvvm_example.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.magicapp.android_mvvm_example.repositories.UserRepository
import com.magicapp.android_mvvm_example.utills.NetworkHelper

class ViewModelFactory(
    private val userRepository: UserRepository,
    val networkHelper: NetworkHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository, networkHelper) as T
        }
        throw IllegalArgumentException("Error")
    }
}
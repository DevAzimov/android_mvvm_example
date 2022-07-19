package com.magicapp.android_mvvm_example.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magicapp.android_mvvm_example.database.entity.User
import com.magicapp.android_mvvm_example.repositories.UserRepository
import com.magicapp.android_mvvm_example.utills.NetworkHelper
import com.magicapp.android_mvvm_example.utills.Resource
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository, private val networkHelper: NetworkHelper) : ViewModel() {
    private val liveData = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            liveData.postValue(Resource.loading(null))
            if (networkHelper.isInternetAvailable()){
                val remoteUsers = userRepository.getRemoteUsers()
                if (remoteUsers.isSuccessful){
                    userRepository.addUsers(remoteUsers.body()?: emptyList())
                    liveData.postValue(Resource.success(remoteUsers.body()))
                }else{
                 liveData.postValue(Resource.error(remoteUsers.errorBody()?.string() ?: "", null))
                }
            }else{
                liveData.postValue(Resource.success(userRepository.getLocalUsers()))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>> = liveData
}
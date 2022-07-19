package com.magicapp.android_mvvm_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.magicapp.android_mvvm_example.adapter.UserAdapter
import com.magicapp.android_mvvm_example.database.AppDatabase
import com.magicapp.android_mvvm_example.databinding.ActivityMainBinding
import com.magicapp.android_mvvm_example.network.ApiClient
import com.magicapp.android_mvvm_example.repositories.UserRepository
import com.magicapp.android_mvvm_example.utills.NetworkHelper
import com.magicapp.android_mvvm_example.utills.Status
import com.magicapp.android_mvvm_example.viewmodels.UserViewModel
import com.magicapp.android_mvvm_example.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userRepository = UserRepository(ApiClient.apiService, AppDatabase.getInstance(this))
        val networkHelper = NetworkHelper(this)

        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(userRepository, networkHelper)
        )[UserViewModel::class.java]

        userAdapter = UserAdapter()
        binding.rv.adapter = userAdapter

        binding.progress.visibility = View.VISIBLE
        userViewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.rv.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    userAdapter.submitList(it.data)
                    binding.progress.visibility = View.GONE
                    binding.rv.visibility = View.VISIBLE
                }
            }
        })
    }
}
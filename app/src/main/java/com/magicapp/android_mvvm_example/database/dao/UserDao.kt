package com.magicapp.android_mvvm_example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magicapp.android_mvvm_example.database.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<User>)

    @Query("select * from users")
    suspend fun getAllUsers(): List<User>
}
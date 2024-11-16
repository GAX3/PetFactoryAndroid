package com.example.petfactorybd.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.petfactorybd.database.entities.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE name = :name AND password = :password")
    suspend fun loginUser(name: String, password: String): User?

    @Query("SELECT * FROM users WHERE name = :name LIMIT 1")
    suspend fun getUserByName(name: String): User?
}

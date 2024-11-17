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

    @Query("DELETE FROM users WHERE name = :name AND password = :password")
    suspend fun deleteUserByName(name: String, password: String)

    //SELECT ALL
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getData(id: Int): User?

}

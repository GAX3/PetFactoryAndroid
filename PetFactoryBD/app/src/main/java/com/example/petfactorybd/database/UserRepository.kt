package com.example.petfactorybd.database

import androidx.lifecycle.LiveData
import com.example.petfactorybd.database.daos.UserDao
import com.example.petfactorybd.database.entities.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun loginUser(name: String, password: String): User? {
        return userDao.loginUser(name, password)
    }

    //If user Already exists
    suspend fun isUserExists(name: String): Boolean {
        return userDao.getUserByName(name) != null
    }

    suspend fun deleteUserByName(name: String, password: String) {
        userDao.deleteUserByName(name, password)
    }

    //ALL USER
    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    //getData
    suspend fun getData(id: Int): User? {
        return userDao.getData(id)
    }

    //LIVEDATA
    fun getUserById(userId: Int): LiveData<User> {
        return userDao.getUserById(userId)
    }

    suspend fun updateLevel(id: Int, level: Int) {
        return userDao.updateLevel(id, level)
    }

    suspend fun updateCoins(userId: Int, newCoins: Int) {
        userDao.updateCoins(userId, newCoins)
    }


}

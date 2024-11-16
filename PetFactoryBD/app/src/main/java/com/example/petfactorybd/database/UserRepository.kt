package com.example.petfactorybd.database

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
}

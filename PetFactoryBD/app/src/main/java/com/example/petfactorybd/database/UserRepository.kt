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

}

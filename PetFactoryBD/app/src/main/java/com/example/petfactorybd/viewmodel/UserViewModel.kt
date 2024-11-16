package com.example.petfactorybd.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petfactorybd.database.UserRepository
import com.example.petfactorybd.database.entities.User
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun registerUser(name: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val exists = repository.isUserExists(name)
            if (exists) {
                callback(false) // User already exists
            } else {
                Log.i("LOGGG", name.toString())
                Log.i("LOGGG", password.toString())
                repository.insertUser(User(name = name, password = password))
                callback(true) // User registered successfully
            }
        }
    }

    fun loginUser(name: String, password: String, callback: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.loginUser(name, password)
            Log.i("LOGGG U", user.toString())
            callback(user)
        }
    }

    fun deleteUserByName(name: String, password: String) {
        viewModelScope.launch {
            repository.deleteUserByName(name,password)
        }
    }

    //ALL USER
    fun fetchAllUsers(callback: (List<User>) -> Unit) {
        viewModelScope.launch {
            val users = repository.getAllUsers()
            callback(users)
        }
    }
}

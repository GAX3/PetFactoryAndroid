package com.example.petfactorybd.viewmodel

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
                repository.insertUser(User(name = name, password = password))
                callback(true) // User registered successfully
            }
        }
    }

    fun loginUser(name: String, password: String, callback: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.loginUser(name, password)
            callback(user)
        }
    }
}

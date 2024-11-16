package com.example.petfactorybd.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.petfactorybd.database.daos.UserDao
import com.example.petfactorybd.database.entities.User

@Database(entities = [User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

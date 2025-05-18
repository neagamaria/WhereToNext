package com.example.wheretonext

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wheretonext.daos.*
import com.example.wheretonext.models.*

@Database(entities = [User::class, Event::class, Category::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val eventDAO: EventDAO
    abstract val categoryDAO: CategoryDAO
}
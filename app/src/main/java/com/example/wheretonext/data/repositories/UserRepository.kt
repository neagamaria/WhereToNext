package com.example.wheretonext.data.repositories

import com.example.wheretonext.ApplicationController
import com.example.wheretonext.data.models.User

object UserRepository {
    suspend fun insert(entities: List<User>) {
        ApplicationController.instance?.appDatabase?.userDao?.insertAll(entities)
    }

    suspend fun getAllUsers() =
        ApplicationController.instance?.appDatabase?.userDao?.getAll() ?: listOf()
}
package com.example.wheretonext.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "first_name")
    val firstname: String,
    @ColumnInfo(name = "last_name")
    val lastname: String,
    val email: String,
    val password: String,
    val role: String
)
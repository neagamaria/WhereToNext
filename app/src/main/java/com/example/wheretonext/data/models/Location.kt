package com.example.wheretonext.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "location")
data class Location(
    @PrimaryKey
    val id: String,
    val name: String
)
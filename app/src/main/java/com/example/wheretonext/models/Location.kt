package com.example.wheretonext.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Location(
    @PrimaryKey
    val id: String,
    val name: String
)
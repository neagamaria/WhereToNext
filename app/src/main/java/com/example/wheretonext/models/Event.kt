package com.example.wheretonext.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
class Event (
    @PrimaryKey
    val id: String,
    val name: String,
    val location: String,
    val date: Date,
    val duration: Int,
    val categoryId: String
)
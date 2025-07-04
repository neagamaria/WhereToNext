package com.example.wheretonext.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text
import java.util.Date

@Entity(tableName = "event")
class Event (
    @PrimaryKey
    val id: String,
    val name: String,
    val locationId: String,
    val date: Date,
    val duration: Int,
    val categoryId: String,
    val description: String
)
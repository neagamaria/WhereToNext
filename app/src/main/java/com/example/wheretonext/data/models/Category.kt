package com.example.wheretonext.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    val id: String,
    val name: String
)

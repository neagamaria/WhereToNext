package com.example.wheretonext.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Category(
    @PrimaryKey
    val id: String,
    val name: String
)

@Entity
data class CategoryWithEvents (
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val events: List<Event>
)
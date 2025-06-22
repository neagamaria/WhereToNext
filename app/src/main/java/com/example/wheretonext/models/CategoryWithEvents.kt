package com.example.wheretonext.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class CategoryWithEvents (
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val events: List<Event>
)
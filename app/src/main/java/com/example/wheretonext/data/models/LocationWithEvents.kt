package com.example.wheretonext.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class LocationWithEvents(
    @Embedded val location: Location,
    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )
    val events: List<Event>
)
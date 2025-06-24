package com.example.wheretonext.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wheretonext.data.models.Event

@Dao
interface EventDAO {
    @Query("SELECT * FROM event")
    fun getAll(): List<Event>

    @Query("SELECT * FROM event WHERE id IN (:eventIds)")
    fun loadAllByIds(eventIds: IntArray): List<Event>

    @Query("SELECT * FROM event WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Event

    @Query("SELECT * FROM Event WHERE locationId = :locationId ORDER BY date ASC")
    fun getByLocation(locationId: String): List<Event>

    @Query("SELECT * FROM Event WHERE categoryId = :categoryId ORDER BY date ASC")
    suspend fun getByCategory(categoryId: String): List<Event>

    @Insert
    fun insertAll(events: List<Event>)

    @Insert
    suspend fun insert(event: Event)

    @Delete
    fun delete(event: Event)
}
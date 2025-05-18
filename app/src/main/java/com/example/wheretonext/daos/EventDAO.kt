package com.example.wheretonext.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.wheretonext.models.Event

@Dao
interface EventDAO {
    @Query("SELECT * FROM event")
    fun getAll(): List<Event>

    @Query("SELECT * FROM event WHERE id IN (:eventIds)")
    fun loadAllByIds(eventIds: IntArray): List<Event>

    @Query("SELECT * FROM event WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Event

    @Query("SELECT * FROM event WHERE location LIKE :location LIMIT 1")
    fun findByLocation(location: String): Event

    @Insert
    fun insertAll(vararg events: Event)

    @Insert
    suspend fun insert(event: Event)

    @Delete
    fun delete(event: Event)
}
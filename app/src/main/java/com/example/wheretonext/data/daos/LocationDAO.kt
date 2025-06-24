package com.example.wheretonext.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.wheretonext.data.models.Location
import com.example.wheretonext.data.models.LocationWithEvents

@Dao
interface LocationDAO {

    @Insert
    suspend fun insert(location: Location)

    @Insert
    suspend fun insertAll(locations: List<Location>)

    @Update
    suspend fun update(location: Location)

    @Delete
    suspend fun delete(location: Location)

    @Query("SELECT * FROM Location")
    suspend fun getAll(): List<Location>

    // Get Locations along with their related Events
    @Transaction
    @Query("SELECT * FROM Location")
    suspend fun getLocationsWithEvents(): List<LocationWithEvents>

    @Query("SELECT * FROM Location WHERE id = :id")
    suspend fun getById(id: String): Location?
}
package com.example.wheretonext.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.wheretonext.models.Category
import com.example.wheretonext.models.CategoryWithEvents
import com.example.wheretonext.models.Event

@Dao
interface CategoryDAO {
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE id IN (:categoryIds)")
    fun loadAllByIds(categoryIds: IntArray): List<Category>

    @Query("SELECT * FROM category WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Category

    @Transaction
    @Query("SELECT * FROM category")
    fun getCategoryWithEvents(): List<CategoryWithEvents>

    @Insert
    fun insertAll(vararg categories: Category)

    @Insert
    suspend fun insert(category: Category)

    @Delete
    fun delete(category: Category)
}
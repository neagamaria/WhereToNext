package com.example.wheretonext.data.repositories

import com.example.wheretonext.ApplicationController
import com.example.wheretonext.data.models.Category
import com.example.wheretonext.data.models.CategoryWithEvents

object CategoryRepository {
    suspend fun insert(entityModel: Category) {
        ApplicationController.instance?.appDatabase?.categoryDAO?.insert(entityModel)
    }

    suspend fun getAllCategoriesWithEvents():List<CategoryWithEvents> {
        return ApplicationController.instance?.appDatabase?.categoryDAO?.getCategoryWithEvents() ?: listOf()
    }

}
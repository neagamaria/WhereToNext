package com.example.wheretonext.repositories

import com.example.wheretonext.ApplicationController
import com.example.wheretonext.models.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object EventRepository {
    fun insert(entityModel: Event) {
        CoroutineScope(Dispatchers.IO).launch {
            ApplicationController.instance?.appDatabase?.eventDAO?.insert(entityModel)
        }
    }
}
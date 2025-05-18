package com.example.wheretonext

import android.app.Application
import androidx.room.Room

// singleton class
class ApplicationController: Application() {
    companion object {
        var instance: ApplicationController? = null
        private set
    }

    lateinit var appDatabase: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDatabase()
    }

    private fun initDatabase() {
        val db = Room.databaseBuilder(
            context = this,
            klass = AppDatabase::class.java,
            name = "localDatabase"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
}
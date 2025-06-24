package com.example.wheretonext.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wheretonext.daos.*
import com.example.wheretonext.data.daos.LocationDAO
import com.example.wheretonext.data.models.Category
import com.example.wheretonext.data.models.Event
import com.example.wheretonext.data.models.Location
import com.example.wheretonext.data.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

@Database(
    entities = [User::class, Event::class, Category::class, Location::class],
    version = 6)

@TypeConverters(
    Converters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDAO
    abstract val eventDAO: EventDAO
    abstract val categoryDAO: CategoryDAO
    abstract val locationDAO: LocationDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "where_to_next_db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                val database = getDatabase(context)

                                // Sample categories
                                val categories = listOf(
                                    Category("CAT1", "Music"),
                                    Category("CAT2", "Technology"),
                                    Category("CAT3", "Art")
                                )
                                database.categoryDAO.insertAll(categories)

                                val locations = listOf(
                                    Location("LOC1", "New York"),
                                    Location("LOC2", "Berlin"),
                                    Location("LOC3", "Tokyo")
                                )
                                database.locationDAO.insertAll(locations)

                                val events = listOf(
                                    Event("1", "Jazz Night", "LOC1", Date(), 120, "CAT1", "Live jazz music."),
                                    Event("2", "Tech Summit", "LOC2", Date(), 180, "CAT2", "Latest in tech."),
                                    Event("3", "Art Walk", "LOC3", Date(), 90, "CAT3", "Street gallery tour.")
                                )
                                database.eventDAO.insertAll(events)
                            }
                        }
                    })

                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
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
                                    Event("1", "Jazz Night", "LOC1", Date(), 3, "CAT1", "Immerse yourself in a soulful evening of live jazz featuring renowned musicians and emerging local talent. Enjoy smooth saxophone solos, rhythmic basslines, and a cozy ambiance in the heart of the city’s nightlife district."),
                                    Event("2", "Tech Summit", "LOC2", Date(), 8, "CAT2", "Join industry leaders, innovators, and tech enthusiasts at this year’s premier technology conference. Explore the latest advancements in AI, cybersecurity, and software development through keynote sessions, panels, and networking opportunities."),
                                    Event("3", "Art Walk", "LOC3", Date(), 4, "CAT3", "Stroll through the city’s vibrant arts district and experience an open-air gallery showcasing works by local painters, sculptors, and photographers. The Art Walk offers live demos, artist meet-and-greets, and interactive exhibits for all ages."),
                                    Event("4", "Foodie Fest", "LOC1", Date(), 5, "CAT1", "A celebration of global cuisine with food trucks, cooking demonstrations, and tasting booths from top chefs. Enjoy a culinary adventure right in the city center with live music and family-friendly activities."),
                                    Event("5", "Startup Pitch Night", "LOC2", Date(), 3, "CAT2", "Watch emerging startups pitch their ideas to investors and tech leaders. Gain insights into entrepreneurship and innovation while networking with aspiring founders and mentors."),
                                    Event("6", "Digital Art Expo", "LOC3", Date(), 6, "CAT3", "Explore the intersection of technology and creativity at this cutting-edge digital art exhibition. Featuring VR installations, interactive displays, and workshops from world-renowned digital artists."),
                                    Event("7", "Indie Music Jam", "LOC1", Date(), 4, "CAT1", "Experience a night of original music from local indie bands across multiple genres. Grab a drink, discover new talent, and vibe to live performances in an energetic open-air venue."),
                                    Event("8", "AI & Ethics Panel", "LOC2", Date(), 2, "CAT2", "A thought-provoking panel discussion on the ethical implications of artificial intelligence, featuring experts from academia, tech companies, and policy organizations.")
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
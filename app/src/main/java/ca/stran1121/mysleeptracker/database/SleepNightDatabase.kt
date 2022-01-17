package ca.stran1121.mysleeptracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ca.stran1121.mysleeptracker.database.dao.SleepNightDao
import ca.stran1121.mysleeptracker.database.entity.SleepNight

@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepNightDatabase: RoomDatabase() {
    abstract val sleepNightDao: SleepNightDao

    companion object {
        @Volatile
        private var INSTANCE: SleepNightDatabase? = null

        fun getInstance(context: Context): SleepNightDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepNightDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
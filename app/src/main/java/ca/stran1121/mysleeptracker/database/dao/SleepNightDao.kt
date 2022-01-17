package ca.stran1121.mysleeptracker.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ca.stran1121.mysleeptracker.database.entity.SleepNight

@Dao
interface SleepNightDao {

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId = :id")
    suspend fun get(id: Long): SleepNight?


    @Query("SELECT * FROM daily_sleep_quality_table")
    fun getAllNights(): LiveData<List<SleepNight>>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    suspend fun getTonight(): SleepNight?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(night: SleepNight)

    @Delete
    suspend fun delete(night: SleepNight)

    @Update
    suspend fun update(night: SleepNight)

    @Query("DELETE FROM daily_sleep_quality_table")
    suspend fun clear()

}
package ca.stran1121.mysleeptracker.sleeptracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.stran1121.mysleeptracker.database.dao.SleepNightDao
import java.lang.IllegalArgumentException

class SleepTrackViewModelFactory(
    val sleepNightDao: SleepNightDao,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SleepTrackerViewModel::class.java))
        {
            return SleepTrackerViewModel(sleepNightDao, application) as T
        }
        throw IllegalArgumentException("Wrong View Model")
    }

}
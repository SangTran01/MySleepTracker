package ca.stran1121.mysleeptracker.sleepDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ca.stran1121.mysleeptracker.database.dao.SleepNightDao
import java.lang.IllegalArgumentException

class SleepDetailViewModelFactory(
    private val sleepNightId: Long,
    private val sleepNightDao: SleepNightDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepDetailViewModel::class.java)) {
            return SleepDetailViewModel(sleepNightId, sleepNightDao) as T
        }

        throw IllegalArgumentException("Wrong View Model")
    }
}
package ca.stran1121.mysleeptracker.sleepDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.stran1121.mysleeptracker.database.dao.SleepNightDao
import ca.stran1121.mysleeptracker.database.entity.SleepNight
import kotlinx.coroutines.launch

class SleepDetailViewModel(sleepNightId: Long, sleepNightDao: SleepNightDao) : ViewModel() {

    private val _night = MutableLiveData<SleepNight>()
    val night: LiveData<SleepNight> get() = _night

    init {
        viewModelScope.launch {
            _night.value = sleepNightDao.get(sleepNightId)
        }
    }

    private val _navigateToSleepTracker = MutableLiveData<Boolean>()
    val navigateToSleepTracker : LiveData<Boolean> = _navigateToSleepTracker

    fun onAfterClose() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }
}
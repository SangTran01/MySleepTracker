package ca.stran1121.mysleeptracker.sleepquality

import androidx.lifecycle.*
import ca.stran1121.mysleeptracker.database.dao.SleepNightDao
import kotlinx.coroutines.launch

class SleepQualityViewModel(
    val sleepNightId: Long,
    val sleepNightDao: SleepNightDao
): ViewModel() {

    private val _sleepQuality = MutableLiveData<Int>()
    val sleepQuality : LiveData<Int> get() = _sleepQuality

    fun onSetSleepQuality(sleepQuality: Int) {
        _sleepQuality.value = sleepQuality

        viewModelScope.launch {
            //get sleepNight and update
            val sleepNight = sleepNightDao.get(sleepNightId)
            sleepNight?.let {
                it.sleepQuality = sleepQuality

                sleepNightDao.update(it)
            }
        }
    }
}
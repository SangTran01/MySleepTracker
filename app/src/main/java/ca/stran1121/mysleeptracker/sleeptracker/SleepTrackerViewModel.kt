package ca.stran1121.mysleeptracker.sleeptracker

import android.app.Application
import androidx.lifecycle.*
import ca.stran1121.mysleeptracker.database.dao.SleepNightDao
import ca.stran1121.mysleeptracker.database.entity.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.launch

class SleepTrackerViewModel(
    private val sleepNightDao: SleepNightDao,
    application: Application
) : AndroidViewModel(application) {

    private var tonight = MutableLiveData<SleepNight?>()

    val nights = sleepNightDao.getAllNights()

    init {
        initializeTonight()
    }

    val nightString = Transformations.map(nights) {
        formatNights(it, application.resources)
    }

    val startButtonVisible = Transformations.map(tonight) {
        it == null
    }

    val stopButtonVisible = Transformations.map(tonight) {
        it != null
    }

    val clearButtonVisible = Transformations.map(nights) {
        it?.isNotEmpty()
    }

    private val _navigateToSleepDetail = MutableLiveData<Long>()
    val navigateToSleepDetail: LiveData<Long> = _navigateToSleepDetail

    fun startNavigatingDetail(sleepNightId: Long) {
        _navigateToSleepDetail.value = sleepNightId
    }

    fun doneNavigatingDetail() {
        _navigateToSleepDetail.value = null
    }

    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigateToSleepQuality: LiveData<SleepNight> = _navigateToSleepQuality

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    private val _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: LiveData<Boolean> = _showSnackBarEvent

    fun showSnackBar() {
        _showSnackBarEvent.value = true
    }

    fun doneShowSnackBar() {
        _showSnackBarEvent.value = false
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonight()
        }
    }

    private suspend fun getTonight(): SleepNight? {
        var night = sleepNightDao.getTonight()

        if (night?.endTimeMilli != night?.startTimeMilli) {
            night = null
        }
        return night
    }

    fun onStartTracking() {
        viewModelScope.launch {
            val night = SleepNight()
            sleepNightDao.insert(night)

            tonight.value = getTonight()
        }
    }

    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            sleepNightDao.update(oldNight)
            //used to navigate
            _navigateToSleepQuality.value = oldNight
        }
    }

    fun onClear() {
        viewModelScope.launch {
            sleepNightDao.clear()
            tonight.value = null
        }
        showSnackBar()
    }
}
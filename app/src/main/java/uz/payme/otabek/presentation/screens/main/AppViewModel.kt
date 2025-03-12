package uz.payme.otabek.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import uz.payme.otabek.data.AppDataStore
import uz.payme.otabek.utils.ButtonNames

class AppViewModel(val dataStore: AppDataStore) : ViewModel() {
    private var job: Job? = null

    private var isStarted: Boolean = false
    private var isContinue: Boolean = false

    private val _timeUiState = MutableStateFlow(TimeUiState(time = 0L))
    val timeUiState: StateFlow<TimeUiState> = _timeUiState.asStateFlow()

    private val _buttonUiState = MutableStateFlow(
        ButtonUiState(
            firstButton = ButtonNames.INTERVAL.value, secondButton = ButtonNames.START.value
        )
    )
    val buttonUiState: StateFlow<ButtonUiState> = _buttonUiState.asStateFlow()

    init {
        getTime()
    }

    fun start() {
        if (!isStarted && !isContinue) {
            isStarted = true
            isContinue = true
            val startTime = System.currentTimeMillis() - _timeUiState.value.time
            startCoroutine(startTime = startTime)
            _buttonUiState.value = _buttonUiState.value.copy(secondButton = ButtonNames.STOP.value)
        } else if (isStarted && isContinue) {
            pause()
        } else if (isStarted) {
            proceed()
        }
    }

    fun pause() {
        if (isStarted && isContinue) {
            job?.cancel()
            job = null
            isContinue = false
            _buttonUiState.value = _buttonUiState.value.copy(
                firstButton = ButtonNames.RESET.value, secondButton = ButtonNames.CONTINUE.value
            )
        }
    }

    fun proceed() {
        if (!isStarted) return
        isContinue = true
        val startTime = System.currentTimeMillis() - _timeUiState.value.time
        startCoroutine(startTime = startTime)
        _buttonUiState.value = _buttonUiState.value.copy(
            firstButton = ButtonNames.INTERVAL.value, secondButton = ButtonNames.STOP.value
        )
    }

    fun reset() {
        if (isStarted && !isContinue) {
            job?.cancel()
            job = null
            isStarted = false
            isContinue = false
            _timeUiState.value = _timeUiState.value.copy(time = 0L)
            _buttonUiState.value = buttonUiState.value.copy(
                firstButton = ButtonNames.INTERVAL.value, secondButton = ButtonNames.START.value
            )
            clearTime()
        }
    }

    fun saveTime() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveTime(_timeUiState.value.time)
        }
    }

    fun getTime() {
        viewModelScope.launch(Dispatchers.IO) {
            val time = dataStore.getTime().first()
            _timeUiState.value = _timeUiState.value.copy(time = time)
        }
    }

    private fun clearTime() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveTime(0L)
        }
    }

    private fun startCoroutine(startTime: Long) {
        job = viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                _timeUiState.value =
                    _timeUiState.value.copy(time = System.currentTimeMillis() - startTime)
            }
        }
    }
}


data class TimeUiState(
    val time: Long
)

data class ButtonUiState(
    val firstButton: String,
    val secondButton: String,
)

class AppViewModelFactory(private val dataStore: AppDataStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            return AppViewModel(dataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
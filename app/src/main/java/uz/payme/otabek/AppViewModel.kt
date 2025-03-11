package uz.payme.otabek

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    private var job: Job? = null
    private var isStarted: Boolean = false

    private val _timeUiState = MutableStateFlow(TimeUiState(time = 0L))
    val timeUiState: StateFlow<TimeUiState> = _timeUiState.asStateFlow()

    private val _buttonUiState = MutableStateFlow(
        ButtonUiState(
            firstButton = ButtonNames.INTERVAL.value, secondButton = ButtonNames.START.value
        )
    )
    val buttonUiState: StateFlow<ButtonUiState> = _buttonUiState.asStateFlow()

    fun start() {
        val startTime = System.currentTimeMillis()
        if (!isStarted) {
            viewModelScope.launch(Dispatchers.Default) {
                if (job == null) {
                    while (isActive) {
                        job = launch {
                            _timeUiState.value =
                                _timeUiState.value.copy(time = System.currentTimeMillis() - startTime)
                        }
                    }
                }
            }
            isStarted = true
            _buttonUiState.value = _buttonUiState.value.copy(secondButton = ButtonNames.STOP.value)
        } else {
            job?.cancel()
            isStarted = false
            _buttonUiState.value =
                _buttonUiState.value.copy(secondButton = ButtonNames.CONTINUE.value)
        }
    }

    fun pause() {
        if (isStarted) {
            job?.cancel()
            isStarted = false
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
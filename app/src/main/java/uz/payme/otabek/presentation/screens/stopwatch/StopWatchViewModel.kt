package uz.payme.otabek.presentation.screens.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.payme.domain.repository.DataStoreRepository
import uz.payme.domain.usecase.GetTimeUseCase
import uz.payme.domain.usecase.GetWasRunningUseCase
import uz.payme.domain.usecase.SaveTimeUseCase
import uz.payme.domain.usecase.SaveWasRunningUseCase
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreenContract.Intent
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreenContract.Intent.ClickLeftButton
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreenContract.Intent.GetState
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreenContract.Intent.SaveState
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreenContract.Intent.Start
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreenContract.StopWatchUiStates
import uz.payme.otabek.utils.ButtonNames
import javax.inject.Inject

@HiltViewModel
class StopWatchViewModel @Inject constructor(
    private val saveTimeUseCase: SaveTimeUseCase,
    private val saveWasRunningUseCase: SaveWasRunningUseCase,
    private val getTimeUseCase: GetTimeUseCase,
    private val getWasRunningUseCase: GetWasRunningUseCase,
) : ViewModel() {
    private var job: Job? = null

    private var isStarted: Boolean = false
    private var isContinue: Boolean = false
    private var isFirstTime: Boolean = true

    private val _uiState = MutableStateFlow(StopWatchUiStates())
    val uiState: StateFlow<StopWatchUiStates> = _uiState.asStateFlow()

    fun eventDispatcher(intent: Intent) {
        when (intent) {
            Start -> start()
            SaveState -> saveState()
            GetState -> getState()
            ClickLeftButton -> {
                if (uiState.value.timeUiState.wasRunning) {
                    setCircle()
                } else reset()
            }
        }
    }

    private fun start() {
        if (!isStarted && !isContinue) {
            isStarted = true
            isContinue = true
            val startTime = System.currentTimeMillis() - _uiState.value.timeUiState.time
            startCoroutine(startTime = startTime)
            _uiState.value = _uiState.value.copy(
                buttonUiState = _uiState.value.buttonUiState.copy(secondButton = ButtonNames.STOP.value),
                timeUiState = _uiState.value.timeUiState.copy(wasRunning = true)
            )
        } else if (isStarted && isContinue) {
            pause()
        } else if (isStarted) {
            proceed()
        }
    }

    private fun pause() {
        if (isStarted && isContinue) {
            job?.cancel()
            job = null
            isContinue = false
            _uiState.value = _uiState.value.copy(
                buttonUiState = _uiState.value.buttonUiState.copy(
                    firstButton = ButtonNames.RESET.value, secondButton = ButtonNames.CONTINUE.value
                ), timeUiState = _uiState.value.timeUiState.copy(wasRunning = false)
            )
        }
    }

    private fun proceed() {
        if (!isStarted) return
        isContinue = true
        val startTime = System.currentTimeMillis() - _uiState.value.timeUiState.time
        startCoroutine(startTime = startTime)
        _uiState.value = _uiState.value.copy(
            buttonUiState = _uiState.value.buttonUiState.copy(
                firstButton = ButtonNames.INTERVAL.value, secondButton = ButtonNames.STOP.value
            ), timeUiState = _uiState.value.timeUiState.copy(wasRunning = true)
        )
    }

    private fun reset() {
        if (isStarted && !isContinue) {
            job?.cancel()
            job = null
            isStarted = false
            isContinue = false
            _uiState.value = _uiState.value.copy(
                timeUiState = _uiState.value.timeUiState.copy(time = 0L, wasRunning = false),
                buttonUiState = _uiState.value.buttonUiState.copy(
                    firstButton = ButtonNames.INTERVAL.value, secondButton = ButtonNames.START.value
                ),
                circlesUiState = _uiState.value.circlesUiState.copy(emptyList())
            )
            clearTime()
        }
    }

    private fun setCircle() {
        if (isStarted && isContinue) {
            _uiState.value = _uiState.value.copy(
                circlesUiState = _uiState.value.circlesUiState.copy(
                    list = mutableListOf<Long>().apply {
                        add(_uiState.value.timeUiState.time)
                        addAll(_uiState.value.circlesUiState.list)
                    })
            )
        }
    }

    private fun saveState() {
        viewModelScope.launch(Dispatchers.IO) {
            saveTimeUseCase.invoke(_uiState.value.timeUiState.time)
            saveWasRunningUseCase.invoke(isContinue)
        }
    }

    private fun getState() {
        if (!isFirstTime) return
        viewModelScope.launch(Dispatchers.IO) {
            val time = getTimeUseCase.invoke()
            val wasRunning = getWasRunningUseCase.invoke()
            _uiState.value = _uiState.value.copy(
                timeUiState = _uiState.value.timeUiState.copy(time = time, wasRunning = wasRunning)
            )
            if (wasRunning) {
                proceed()
            }
        }
        isFirstTime = false
    }


    private fun clearTime() {
        viewModelScope.launch(Dispatchers.IO) {
            saveTimeUseCase.invoke(0L)
        }
    }

    private fun startCoroutine(startTime: Long) {
        job = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                withContext(Dispatchers.Main) {
                    _uiState.value = _uiState.value.copy(
                        timeUiState = _uiState.value.timeUiState.copy(time = System.currentTimeMillis() - startTime)
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

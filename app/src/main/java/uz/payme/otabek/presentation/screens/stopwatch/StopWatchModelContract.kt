package uz.payme.otabek.presentation.screens.stopwatch

import androidx.compose.runtime.Stable
import uz.payme.otabek.utils.ButtonNames

interface StopWatchModelContract {
    @Stable
    data class UiStates(
        val timeUiState: TimeUiState = TimeUiState(),
        val buttonUiState: ButtonUiState = ButtonUiState(),
        val circlesUiState: CirclesUiState = CirclesUiState()
    )

    @Stable
    data class TimeUiState(
        val time: Long = 0L, val wasRunning: Boolean = true
    )

    @Stable
    data class ButtonUiState(
        val firstButton: String = ButtonNames.INTERVAL.value,
        val secondButton: String = ButtonNames.START.value
    )

    @Stable
    data class CirclesUiState(
        val list: List<Long> = emptyList()
    )

    sealed interface Intent {
        object Start : Intent
        object SaveState : Intent
        object GetState : Intent
        object ClickLeftButton : Intent
    }
}
package uz.payme.otabek.presentation.screens.weather

import androidx.compose.runtime.Stable
import uz.payme.otabek.utils.ButtonNames

interface WeatherModelContract {
    @Stable
    data class WeatherUiStates(
        val isLoading: Boolean = false,
        val temperature: Double? = null,
        val description: String? = null,
        val wind: Double? = null,
        val humidity: Int? = null,
    )

    sealed interface Intent {
        object Init : Intent
    }
}
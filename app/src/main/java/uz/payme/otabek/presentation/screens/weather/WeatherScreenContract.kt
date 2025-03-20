package uz.payme.otabek.presentation.screens.weather

import androidx.compose.runtime.Stable
import uz.payme.domain.models.ForecastModel
import uz.payme.domain.models.OneCallModel

interface WeatherScreenContract {
    @Stable
    data class WeatherUiStates(
        val isLoading: Boolean,
        val currentWeather: OneCallModel? = null,
        val currentForecast: ForecastModel? = null,
        val errorMessage: String? = null
    )

    sealed interface Intent {
        object Init : Intent
    }
}
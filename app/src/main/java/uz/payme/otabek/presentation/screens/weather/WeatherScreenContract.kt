package uz.payme.otabek.presentation.screens.weather

import androidx.compose.runtime.Stable
import uz.payme.otabek.data.network.CoordinatesResponse
import uz.payme.otabek.data.network.ForecastResponse

interface WeatherScreenContract {
    @Stable
    data class WeatherUiStates(
        val isLoading: Boolean = false,
        val currentWeather: CoordinatesResponse? = null,
        val currentForecast: ForecastResponse? = null,
        val errorMessage: String? = null
    )

    sealed interface Intent {
        object Init : Intent
    }
}
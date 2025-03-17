package uz.payme.otabek.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.payme.otabek.data.network.Coordinates
import uz.payme.otabek.data.network.Per3Hour
import uz.payme.otabek.data.repository.DataStoreRepository
import uz.payme.otabek.data.repository.OpenWeatherRepository
import uz.payme.otabek.presentation.screens.weather.WeatherModelContract.Intent.Init
import uz.payme.otabek.presentation.screens.weather.WeatherModelContract.WeatherUiStates
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: OpenWeatherRepository) : ViewModel() {
    private var job: Job? = null

    private val _uiState = MutableStateFlow(WeatherUiStates())
    val uiState: StateFlow<WeatherUiStates> = _uiState.asStateFlow()

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage = _errorMessage.asStateFlow()

    fun eventDispatcher(intent: WeatherModelContract.Intent) {
        when (intent) {
            Init -> {

            }
        }
    }

    private fun getForecastWeather(lat: Double, lon: Double): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            repository.getForecastWeather(lat.toString(), lon.toString()).onSuccess {
                _currentForecast.emit(it)
            }.onFailure {
                _errorMessage.emit(it.message.toString())
            }
        }
    }

    private suspend fun getForecast(lat: Double?, lon: Double?): List<Per3Hour> {
        return try {
            val response = repository.getForecastWeather(lat.toString(), lon.toString())
            if (response.isSuccess) {
                response.getOrNull()?.list ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            _errorMessage.emit(e.message.toString())
            emptyList()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

data class WeatherUiState(
    val markerPosition: Coordinates? = null,
    val cityName: String = "",
    val temperature: String = "",
    val temperaturePerHour: String = "",
    val description: String = "",
    val feelsLike: String = "",
    val temperatureMin: String = "",
    val temperatureMax: String = "",
    val widgetsUiState: WidgetsUiState = WidgetsUiState(),
    val code: String = "",
    val weatherId: Int = 0
) {

    data class WidgetsUiState(
        val id: String = "",
        val sealevel: String = "",
        val windSpeed: String = "",
        val feelsLike: String = "",
        val humidity: String = "",
        val sunrise: Long = 0,
        val sunset: Long = 0
    )
}

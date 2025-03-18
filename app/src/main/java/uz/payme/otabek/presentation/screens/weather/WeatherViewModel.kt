package uz.payme.otabek.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.payme.otabek.data.network.CoordinatesResponse
import uz.payme.otabek.data.network.ForecastResponse
import uz.payme.otabek.data.repository.OpenWeatherRepository
import uz.payme.otabek.presentation.screens.weather.WeatherScreenContract.Intent.Init
import uz.payme.otabek.presentation.screens.weather.WeatherScreenContract.WeatherUiStates
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: OpenWeatherRepository) : ViewModel() {
    private var job: Job? = null

    private val _weatherUiState = MutableStateFlow(WeatherUiStates(isLoading = false))
    val weatherUiState: StateFlow<WeatherUiStates> = _weatherUiState.asStateFlow()

    private val _currentWeather = MutableStateFlow<CoordinatesResponse?>(null)
    private val _currentForecast = MutableStateFlow<ForecastResponse?>(null)

    fun eventDispatcher(intent: WeatherScreenContract.Intent) {
        when (intent) {
            Init -> {
                _weatherUiState.value = WeatherUiStates(
                    isLoading = true, currentWeather = null, currentForecast = null, errorMessage = null
                )
                viewModelScope.launch {
                    val currentWeatherResult = repository.getCurrentWeather()
                    val forecastWeatherResult = repository.getForecastWeather()

                    if (currentWeatherResult.isSuccess && forecastWeatherResult.isSuccess) {
                        currentWeatherResult.onSuccess {
                            _currentWeather.value = it
                        }
                        forecastWeatherResult.onSuccess {
                            _currentForecast.value = it
                        }
                        _weatherUiState.value = WeatherUiStates(
                            isLoading = false,
                            currentWeather = _currentWeather.value,
                            currentForecast = _currentForecast.value,
                            errorMessage = null
                        )
                    } else {
                        val errorMessage = currentWeatherResult.exceptionOrNull()?.message
                            ?: forecastWeatherResult.exceptionOrNull()?.message
                        _weatherUiState.value = WeatherUiStates(
                            isLoading = false,
                            currentWeather = null,
                            currentForecast = null,
                            errorMessage = errorMessage
                        )
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
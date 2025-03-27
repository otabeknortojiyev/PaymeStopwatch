package uz.payme.otabek.presentation.screens.weather

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.payme.domain.models.ForecastModel
import uz.payme.domain.models.OneCallModel
import uz.payme.domain.usecase.GetCurrentWeatherUseCase
import uz.payme.domain.usecase.GetForecastWeatherUseCase
import uz.payme.otabek.presentation.screens.weather.WeatherScreenContract.Intent.Init
import uz.payme.otabek.presentation.screens.weather.WeatherScreenContract.WeatherUiStates
import javax.inject.Inject
import kotlin.onSuccess


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastWeatherUseCase: GetForecastWeatherUseCase
) : ViewModel() {
    private var job: Job? = null

    private val _weatherUiState = MutableStateFlow(WeatherUiStates(isLoading = false))
    val weatherUiState: StateFlow<WeatherUiStates> = _weatherUiState.asStateFlow()

    private val _currentWeather = MutableStateFlow<OneCallModel?>(null)
    private val _currentForecast = MutableStateFlow<ForecastModel?>(null)

    fun eventDispatcher(intent: WeatherScreenContract.Intent) {
        when (intent) {
            Init -> {
                _weatherUiState.value = WeatherUiStates(
                    isLoading = true, currentWeather = null, currentForecast = null, errorMessage = null
                )
                viewModelScope.launch {
                    val currentWeatherResult = getCurrentWeatherUseCase()

                    val forecastWeatherResult = getForecastWeatherUseCase()

                    currentWeatherResult.onSuccess { model ->
                        _currentWeather.emit(model)
                    }.onFailure { exception ->

                    }

                    forecastWeatherResult.onSuccess { model ->
                        _currentForecast.emit(model)
                    }.onFailure { exception ->

                    }

                    if (_currentWeather.value != null || _currentForecast.value != null) {
                        _weatherUiState.value = WeatherUiStates(
                            isLoading = false,
                            currentWeather = _currentWeather.value,
                            currentForecast = _currentForecast.value,
                            errorMessage = null
                        )
                    } else {
                        _weatherUiState.value = WeatherUiStates(
                            isLoading = false,
                            errorMessage = "Error!!!"
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
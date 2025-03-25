package uz.payme.data.repository

import uz.payme.data.models.weather_response.ForecastResponse
import uz.payme.data.models.weather_response.OneCallResponse


interface OpenWeatherRepository {
    suspend fun getCurrentWeather(): Result<OneCallResponse>
    suspend fun getForecastWeather(): Result<ForecastResponse>
}
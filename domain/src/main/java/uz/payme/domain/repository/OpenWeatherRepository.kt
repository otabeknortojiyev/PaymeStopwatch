package uz.payme.domain.repository

import uz.payme.domain.models.ForecastModel
import uz.payme.domain.models.OneCallModel

interface OpenWeatherRepository {
    suspend fun getCurrentWeather(): Result<OneCallModel>
    suspend fun getForecastWeather(): Result<ForecastModel>
}
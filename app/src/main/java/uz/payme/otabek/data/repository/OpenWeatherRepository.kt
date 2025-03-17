package uz.payme.otabek.data.repository

import uz.payme.otabek.data.network.ForecastResponse
import uz.payme.otabek.data.network.CoordinatesResponse

interface OpenWeatherRepository {
  suspend fun getCurrentWeather(lat: String, lon: String): Result<CoordinatesResponse>
  suspend fun getForecastWeather(lat: String, lon: String): Result<ForecastResponse>
}
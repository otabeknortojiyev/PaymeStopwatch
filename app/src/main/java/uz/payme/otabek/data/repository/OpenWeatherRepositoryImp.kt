package uz.payme.otabek.data.repository

import android.view.WindowManager.BadTokenException
import uz.payme.otabek.data.network.ForecastResponse
import uz.payme.otabek.data.network.WeatherApi
import uz.payme.otabek.data.network.CoordinatesResponse
import javax.inject.Inject

class OpenWeatherRepositoryImp @Inject constructor(private val weatherApi: WeatherApi) : OpenWeatherRepository {

    override suspend fun getCurrentWeather(lat: String, lon: String): Result<CoordinatesResponse> {
        return try {
            val response = weatherApi.current(lat, lon)
            if (response.cod in 200..299) {
                Result.success(response)
            } else {
                throw BadTokenException()
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getForecastWeather(lat: String, lon: String): Result<ForecastResponse> {
        return try {
            val response = weatherApi.forecast(lat, lon)
            if (response.cod?.toInt() in 200..299) {
                Result.success(response)
            } else {
                throw BadTokenException()
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
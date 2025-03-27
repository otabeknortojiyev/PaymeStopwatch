package uz.payme.data.repository.impl

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.payme.data.models.weather_response.ForecastResponse
import uz.payme.data.models.weather_response.OneCallResponse
import uz.payme.data.network.WeatherApi
import uz.payme.data.repository.OpenWeatherRepository
import uz.payme.data.utils.toResult
import javax.inject.Inject

class OpenWeatherRepositoryImp @Inject constructor(private val weatherApi: WeatherApi) : OpenWeatherRepository {

    private val gson = Gson()

    override suspend fun getCurrentWeather(): Result<OneCallResponse> = withContext(Dispatchers.IO) {
        try {
            weatherApi.current("41.311081", "69.240562").toResult(gson) {
                Result.success(it)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getForecastWeather(): Result<ForecastResponse> = withContext(Dispatchers.IO) {
        try {
            weatherApi.forecast("41.311081", "69.240562").toResult(gson) {
                Result.success(it)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

data class ErrorMessage(val message: String)
package uz.payme.data.repository.impl

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

        // TODO do not propagate exceptions, consider using boolean flag or another property

        weatherApi.current("41.311081", "69.240562").toResult(gson) {

            Result.success(it)

        }

    }

    override suspend fun getForecastWeather(): Result<ForecastResponse> = withContext(Dispatchers.IO) {

        weatherApi.forecast("41.311081", "69.240562").toResult(gson) {

            Result.success(it)

        }

    }

}

data class ErrorMessage(val message: String)
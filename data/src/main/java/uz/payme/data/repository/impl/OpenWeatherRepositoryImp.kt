package uz.payme.data.repository.impl

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.payme.data.models.weather_response.ForecastResponse
import uz.payme.data.models.weather_response.OneCallResponse
import uz.payme.data.network.WeatherApi
import uz.payme.data.repository.OpenWeatherRepository
import uz.payme.data.utils.safeCall
import uz.payme.data.utils.toResult
import javax.inject.Inject

class OpenWeatherRepositoryImp @Inject constructor(private val weatherApi: WeatherApi) : OpenWeatherRepository {

    private val gson = Gson()

    override suspend fun getCurrentWeather(lat: String, lon: String): Result<OneCallResponse> =
        withContext(Dispatchers.IO) {
            safeCall {
                weatherApi.current(lat, lon).toResult(gson) {
                    Result.success(it)
                }
            }
        }

    override suspend fun getForecastWeather(lat: String, lon: String): Result<ForecastResponse> =
        withContext(Dispatchers.IO) {
            safeCall {
                weatherApi.forecast(lat, lon).toResult(gson) {
                    Result.success(it)
                }
            }
        }
}
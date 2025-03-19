package uz.payme.data.repository

import android.view.WindowManager.BadTokenException
import uz.payme.otabek.data.network.ForecastResponse
import uz.payme.otabek.data.network.WeatherApi
import uz.payme.otabek.data.network.CoordinatesResponse
import javax.inject.Inject

class OpenWeatherRepositoryImp @Inject constructor(private val weatherApi: WeatherApi) : OpenWeatherRepository {

    override suspend fun getCurrentWeather(): Result<CoordinatesResponse> {

        // TODO do not propagate exceptions, consider using boolean flag or another property

        return try {
            val response = weatherApi.current("41.311081", "69.240562")
            // TODO magic constants
            // Constants -> OK(200) MiscellaneousPersistentWarningHTTP(299)
            if (response.cod in 200..299) {
                Result.success(response)
            } else {
                throw BadTokenException()
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getForecastWeather(): Result<ForecastResponse> {
        return try {
            val response = weatherApi.forecast("41.311081", "69.240562")
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
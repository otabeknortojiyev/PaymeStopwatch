package uz.payme.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.payme.data.mapper.toForecastModel
import uz.payme.data.mapper.toOneCallModel
import uz.payme.data.network.WeatherApi
import uz.payme.data.utils.toResult
import uz.payme.domain.models.ForecastModel
import uz.payme.domain.models.OneCallModel
import uz.payme.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class OpenWeatherRepositoryImp @Inject constructor(private val weatherApi: WeatherApi) : OpenWeatherRepository {

    private val gson = Gson()

    override suspend fun getCurrentWeather(): Result<OneCallModel> = withContext(Dispatchers.IO) {

        // TODO do not propagate exceptions, consider using boolean flag or another property

        weatherApi.current("41.311081", "69.240562").toResult(gson) {
            Result.success(it.toOneCallModel())
        }

    }

    override suspend fun getForecastWeather(): Result<ForecastModel> = withContext(Dispatchers.IO) {

        weatherApi.forecast("41.311081", "69.240562").toResult(gson) {

            Result.success(it.toForecastModel())

        }

    }

}

data class ErrorMessage(val message: String)
package uz.payme.domain.usecase.weather

import uz.payme.data.repository.OpenWeatherRepository
import uz.payme.domain.mapper.ForecastMapper
import uz.payme.domain.models.ForecastModel
import javax.inject.Inject

class GetForecastWeatherUseCase @Inject constructor(private val weatherRepository: OpenWeatherRepository) {
    suspend operator fun invoke(lat: String, lon: String): Result<ForecastModel> {
        return weatherRepository.getForecastWeather(lat = lat, lon = lon)
            .mapCatching { response -> ForecastMapper.map(response) }
    }
}
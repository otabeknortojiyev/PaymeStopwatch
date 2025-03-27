package uz.payme.domain.usecase

import uz.payme.data.repository.OpenWeatherRepository
import uz.payme.domain.mapper.ForecastMapper
import uz.payme.domain.models.ForecastModel
import javax.inject.Inject

class GetForecastWeatherUseCase @Inject constructor(private val weatherRepository: OpenWeatherRepository) {
    suspend operator fun invoke(): Result<ForecastModel> {
        return weatherRepository.getForecastWeather().mapCatching { response -> ForecastMapper.map(response) }
    }
}
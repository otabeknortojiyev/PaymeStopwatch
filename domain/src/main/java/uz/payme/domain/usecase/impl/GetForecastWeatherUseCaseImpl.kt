package uz.payme.domain.usecase.impl

import uz.payme.data.models.weather_response.ForecastResponse
import uz.payme.data.repository.OpenWeatherRepository
import uz.payme.domain.mapper.ForecastMapper
import uz.payme.domain.models.ForecastModel
import uz.payme.domain.usecase.GetForecastWeatherUseCase
import javax.inject.Inject

class GetForecastWeatherUseCaseImpl @Inject constructor(private val weatherRepository: OpenWeatherRepository) :
    GetForecastWeatherUseCase {
    override suspend fun invoke(): Result<ForecastModel> {
        return weatherRepository.getForecastWeather().mapCatching { response -> ForecastMapper.map(response) }
    }
}
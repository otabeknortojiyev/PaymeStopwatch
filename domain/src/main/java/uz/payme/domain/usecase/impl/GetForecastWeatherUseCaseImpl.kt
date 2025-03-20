package uz.payme.domain.usecase.impl

import uz.payme.domain.models.ForecastModel
import uz.payme.domain.repository.OpenWeatherRepository
import uz.payme.domain.usecase.GetForecastWeatherUseCase
import javax.inject.Inject

class GetForecastWeatherUseCaseImpl @Inject constructor(private val weatherRepository: OpenWeatherRepository) :
    GetForecastWeatherUseCase {
    override suspend fun invoke(): Result<ForecastModel> = weatherRepository.getForecastWeather()
}
package uz.payme.domain.usecase.impl

import uz.payme.domain.models.OneCallModel
import uz.payme.domain.repository.OpenWeatherRepository
import uz.payme.domain.usecase.GetCurrentWeatherUseCase
import javax.inject.Inject

class GetCurrentWeatherUseCaseImpl @Inject constructor(private val weatherRepository: OpenWeatherRepository) :
    GetCurrentWeatherUseCase {
    override suspend fun invoke(): Result<OneCallModel> = weatherRepository.getCurrentWeather()
}
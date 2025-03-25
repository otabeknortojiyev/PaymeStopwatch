package uz.payme.domain.usecase.impl

import uz.payme.data.models.weather_response.OneCallResponse
import uz.payme.data.repository.OpenWeatherRepository
import uz.payme.domain.mapper.OneCallMapper
import uz.payme.domain.models.OneCallModel
import uz.payme.domain.usecase.GetCurrentWeatherUseCase
import javax.inject.Inject

class GetCurrentWeatherUseCaseImpl @Inject constructor(private val weatherRepository: OpenWeatherRepository) :
    GetCurrentWeatherUseCase {
    override suspend fun invoke(): Result<OneCallModel> {
        return weatherRepository.getCurrentWeather().mapCatching { response -> OneCallMapper.map(response) }
    }
}
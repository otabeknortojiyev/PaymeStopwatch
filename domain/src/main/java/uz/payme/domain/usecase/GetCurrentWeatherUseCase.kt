package uz.payme.domain.usecase

import uz.payme.data.repository.OpenWeatherRepository
import uz.payme.domain.mapper.OneCallMapper
import uz.payme.domain.models.OneCallModel
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val weatherRepository: OpenWeatherRepository) {
    suspend operator fun invoke(): Result<OneCallModel> {
        return weatherRepository.getCurrentWeather().mapCatching { response -> OneCallMapper.map(response) }
    }
}
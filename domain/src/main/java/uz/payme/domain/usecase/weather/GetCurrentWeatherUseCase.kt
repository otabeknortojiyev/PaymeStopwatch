package uz.payme.domain.usecase.weather

import uz.payme.data.repository.OpenWeatherRepository
import uz.payme.domain.mapper.OneCallMapper
import uz.payme.domain.models.OneCallModel
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val weatherRepository: OpenWeatherRepository) {
    suspend operator fun invoke(lat: String, lon: String): Result<OneCallModel> {
        return weatherRepository.getCurrentWeather(lat = lat, lon = lon)
            .mapCatching { response -> OneCallMapper.map(response) }
    }
}
package uz.payme.domain.usecase

import uz.payme.domain.models.ForecastModel

interface GetForecastWeatherUseCase {
    suspend operator fun invoke(): Result<ForecastModel>
}
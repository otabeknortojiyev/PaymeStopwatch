package uz.payme.domain.usecase

import uz.payme.domain.models.OneCallModel

interface GetCurrentWeatherUseCase {
    suspend operator fun invoke(): Result<OneCallModel>
}
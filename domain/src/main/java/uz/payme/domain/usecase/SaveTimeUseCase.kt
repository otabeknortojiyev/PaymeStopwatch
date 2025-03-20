package uz.payme.domain.usecase

interface SaveTimeUseCase {
    suspend operator fun invoke(time: Long)
}
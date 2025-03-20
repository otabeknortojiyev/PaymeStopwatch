package uz.payme.domain.usecase

interface GetWasRunningUseCase {
    suspend operator fun invoke() : Boolean
}
package uz.payme.domain.usecase

interface GetIsFirstUseCase {
    suspend operator fun invoke() : Boolean
}
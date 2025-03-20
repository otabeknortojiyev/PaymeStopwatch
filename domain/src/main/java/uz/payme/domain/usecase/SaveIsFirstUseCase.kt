package uz.payme.domain.usecase

interface SaveIsFirstUseCase {
    suspend operator fun invoke(isFirst: Boolean)
}
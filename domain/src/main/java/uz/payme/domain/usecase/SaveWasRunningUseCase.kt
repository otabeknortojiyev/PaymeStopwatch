package uz.payme.domain.usecase

interface SaveWasRunningUseCase {

    suspend operator fun invoke(wasRunning: Boolean)

}
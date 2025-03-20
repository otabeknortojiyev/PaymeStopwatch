package uz.payme.domain.usecase.impl

import uz.payme.domain.repository.DataStoreRepository
import uz.payme.domain.usecase.SaveWasRunningUseCase
import javax.inject.Inject

class SaveWasRunningUseCaseImpl @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    SaveWasRunningUseCase {
    override suspend fun invoke(wasRunning: Boolean) = dataStoreRepository.saveWasRunning(wasRunning = wasRunning)
}
package uz.payme.domain.usecase

import uz.payme.data.repository.DataStoreRepository
import javax.inject.Inject

class SaveWasRunningUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(wasRunning: Boolean) = dataStoreRepository.saveWasRunning(wasRunning = wasRunning)
}
package uz.payme.domain.usecase

import kotlinx.coroutines.flow.first
import uz.payme.data.repository.DataStoreRepository
import javax.inject.Inject

class GetWasRunningUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(): Boolean = dataStoreRepository.getWasRunning().first()
}
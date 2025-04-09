package uz.payme.domain.usecase.stopwatch

import kotlinx.coroutines.flow.first
import uz.payme.data.repository.DataStoreRepository
import javax.inject.Inject

class GetTimeUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(): Long = dataStoreRepository.getTime().first()
}
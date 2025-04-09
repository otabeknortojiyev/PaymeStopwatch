package uz.payme.domain.usecase.stopwatch

import uz.payme.data.repository.DataStoreRepository
import javax.inject.Inject

class SaveTimeUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(time: Long) = dataStoreRepository.saveTime(time = time)
}
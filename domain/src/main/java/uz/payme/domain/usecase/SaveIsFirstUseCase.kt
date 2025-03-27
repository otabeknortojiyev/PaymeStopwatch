package uz.payme.domain.usecase

import uz.payme.data.repository.DataStoreRepository
import javax.inject.Inject

class SaveIsFirstUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(isFirst: Boolean) = dataStoreRepository.saveIsFirst(isFirst)
}
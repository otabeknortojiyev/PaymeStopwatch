package uz.payme.domain.usecase.impl

import uz.payme.domain.repository.DataStoreRepository
import uz.payme.domain.usecase.SaveIsFirstUseCase
import javax.inject.Inject

class SaveIsFirstUseCaseImpl @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    SaveIsFirstUseCase {
    override suspend fun invoke(isFirst: Boolean) = dataStoreRepository.saveIsFirst(isFirst)
}
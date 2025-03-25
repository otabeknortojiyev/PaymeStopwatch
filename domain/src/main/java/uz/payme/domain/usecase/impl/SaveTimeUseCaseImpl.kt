package uz.payme.domain.usecase.impl

import uz.payme.data.repository.DataStoreRepository
import uz.payme.domain.usecase.SaveTimeUseCase
import javax.inject.Inject

class SaveTimeUseCaseImpl @Inject constructor(private val dataStoreRepository: DataStoreRepository) : SaveTimeUseCase {
    override suspend fun invoke(time: Long) = dataStoreRepository.saveTime(time = time)
}
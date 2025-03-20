package uz.payme.domain.usecase.impl

import kotlinx.coroutines.flow.first
import uz.payme.domain.repository.DataStoreRepository
import uz.payme.domain.usecase.GetWasRunningUseCase
import javax.inject.Inject

class GetWasRunningUseCaseImpl @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    GetWasRunningUseCase {
    override suspend fun invoke(): Boolean = dataStoreRepository.getWasRunning().first()
}
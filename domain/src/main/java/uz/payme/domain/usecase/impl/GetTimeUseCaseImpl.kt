package uz.payme.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import uz.payme.domain.repository.DataStoreRepository
import uz.payme.domain.usecase.GetTimeUseCase
import javax.inject.Inject

class GetTimeUseCaseImpl @Inject constructor(private val dataStoreRepository: DataStoreRepository) : GetTimeUseCase {

    override suspend fun invoke(): Long = dataStoreRepository.getTime().first()

}
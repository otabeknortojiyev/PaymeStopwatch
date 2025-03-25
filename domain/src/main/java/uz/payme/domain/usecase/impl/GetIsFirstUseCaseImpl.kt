package uz.payme.domain.usecase.impl

import kotlinx.coroutines.flow.first
import uz.payme.data.repository.DataStoreRepository
import uz.payme.data.repository.OpenWeatherRepository
import uz.payme.domain.usecase.GetIsFirstUseCase
import javax.inject.Inject

class GetIsFirstUseCaseImpl @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    GetIsFirstUseCase {
    override suspend fun invoke(): Boolean = dataStoreRepository.getIsFirst().first()
}
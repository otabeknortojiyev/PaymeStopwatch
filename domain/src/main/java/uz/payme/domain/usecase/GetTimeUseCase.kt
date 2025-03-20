package uz.payme.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetTimeUseCase {

    suspend operator fun invoke() : Long

}
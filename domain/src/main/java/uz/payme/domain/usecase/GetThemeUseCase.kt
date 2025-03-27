package uz.payme.domain.usecase

import uz.payme.data.repository.UtilsRepository
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(private val utilsRepository: UtilsRepository) {
    operator fun invoke(): Boolean = utilsRepository.getTheme()

}
package uz.payme.domain.usecase

import uz.payme.data.repository.UtilsRepository
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(private val utilsRepository: UtilsRepository) {
    operator fun invoke(isDark: Boolean) = utilsRepository.setTheme(isDark = isDark)
}
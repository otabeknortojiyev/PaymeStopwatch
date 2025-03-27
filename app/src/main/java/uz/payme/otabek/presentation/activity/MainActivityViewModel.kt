package uz.payme.otabek.presentation.activity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.payme.domain.usecase.GetThemeUseCase
import uz.payme.domain.usecase.SetThemeUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
) : ViewModel() {
    fun getTheme(): Boolean = getThemeUseCase()
    fun setTheme(isDark: Boolean): Unit = setThemeUseCase(isDark = isDark)
}
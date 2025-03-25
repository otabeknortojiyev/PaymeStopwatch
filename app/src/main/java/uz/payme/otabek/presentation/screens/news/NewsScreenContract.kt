package uz.payme.otabek.presentation.screens.news

import androidx.compose.runtime.Stable
import uz.payme.domain.models.ForecastModel
import uz.payme.domain.models.OneCallModel

interface NewsScreenContract {

    @Stable
    data class NewsUiStates(
        val isLoading: Boolean,
        val errorMessage: String? = null
    )

    sealed interface Intent {
        object Init : Intent
    }
}
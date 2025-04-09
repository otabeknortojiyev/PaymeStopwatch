package uz.payme.otabek.presentation.screens.news

import androidx.compose.runtime.Stable
import uz.payme.domain.models.ForecastModel
import uz.payme.domain.models.NewsModel
import uz.payme.domain.models.OneCallModel

interface NewsScreenContract {

    @Stable
    data class NewsUiStates(
        val isLoading: Boolean, val news: List<NewsModel>, val errorMessage: String? = null
    )

    sealed interface Intent {
        data class Init(val query: String) : Intent
        data class Update(val data: NewsModel, val forFavorite: Boolean) : Intent
        data class Delete(val data: NewsModel) : Intent
        data object GetFavorites : Intent
    }
}
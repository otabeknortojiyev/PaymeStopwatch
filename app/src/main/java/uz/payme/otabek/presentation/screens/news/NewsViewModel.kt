package uz.payme.otabek.presentation.screens.news

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.Intent.Init
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.NewsUiStates
import uz.payme.otabek.presentation.screens.weather.WeatherScreenContract
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor() : ViewModel() {
    private var job: Job? = null

    private val _newsUiState = MutableStateFlow(NewsUiStates(isLoading = false))
    val newsUiState: StateFlow<NewsUiStates> = _newsUiState.asStateFlow()

    fun eventDispatcher(intent: NewsScreenContract.Intent) {
        when (intent) {
            Init -> {

            }
        }
    }
}
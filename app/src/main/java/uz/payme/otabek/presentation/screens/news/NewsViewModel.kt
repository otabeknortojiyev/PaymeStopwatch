package uz.payme.otabek.presentation.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uz.payme.domain.models.NewsModel
import uz.payme.domain.usecase.DeleteFavoriteNewsUseCase
import uz.payme.domain.usecase.GetFavoriteNewsUseCase
import uz.payme.domain.usecase.GetNewsUseCase
import uz.payme.domain.usecase.UpdateNewsUseCase
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.Intent.Delete
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.Intent.Init
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.Intent.Update
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.NewsUiStates
import uz.payme.otabek.presentation.screens.weather.WeatherScreenContract
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getFavoriteNewsUseCase: GetFavoriteNewsUseCase,
    private val updateFavoriteNewsUseCase: UpdateNewsUseCase,
    private val deleteFavoriteNewsUseCase: DeleteFavoriteNewsUseCase
) : ViewModel() {
    private var job: Job? = null

    private val _newsUiState = MutableStateFlow(NewsUiStates(isLoading = false, news = emptyList()))
    val newsUiState: StateFlow<NewsUiStates> = _newsUiState.asStateFlow()

    fun eventDispatcher(intent: NewsScreenContract.Intent) {
        when (intent) {
            is Init -> {
                getNewsUseCase.invoke(intent.query)
                    .onStart {
                        _newsUiState.value = _newsUiState.value.copy(isLoading = true)
                    }.onEach { result ->
                        result.onSuccess { list ->
                            _newsUiState.value = _newsUiState.value.copy(isLoading = false, news = list)
                        }.onFailure { throwable ->
                            _newsUiState.value = _newsUiState.value.copy(errorMessage = throwable.message)
                        }
                    }.launchIn(viewModelScope)
            }

            is Update -> {
                updateFavoriteNewsUseCase.invoke(data = intent.data)

            }

            is Delete -> {

            }
        }
    }

    private fun getNews() {

    }
}
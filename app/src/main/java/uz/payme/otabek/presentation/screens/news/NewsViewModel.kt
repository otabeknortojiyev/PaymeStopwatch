package uz.payme.otabek.presentation.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.payme.domain.usecase.news.DeleteFavoriteNewsUseCase
import uz.payme.domain.usecase.news.GetFavoriteNewsUseCase
import uz.payme.domain.usecase.news.GetNewsFromLocalUseCase
import uz.payme.domain.usecase.news.GetNewsFromNetworkUseCase
import uz.payme.domain.usecase.news.UpdateNewsUseCase
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.Intent.Delete
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.Intent.GetFavorites
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.Intent.Init
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.Intent.Update
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.NewsUiStates
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsFromNetworkUseCase: GetNewsFromNetworkUseCase,
    private val getNewsFromLocalUseCase: GetNewsFromLocalUseCase,
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
                getNewsFromNetworkUseCase.invoke(intent.query)
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
                    .onStart {
                        _newsUiState.value = _newsUiState.value.copy(isLoading = true)
                    }.onEach {
                        when (intent.forFavorite) {
                            true -> {
                                val newsList = getFavoriteNewsUseCase.invoke()
                                _newsUiState.value = _newsUiState.value.copy(news = newsList)
                            }

                            else -> {
                                getNewsFromLocalUseCase.invoke(intent.data.category)
                                    .onEach { newsList ->
                                        _newsUiState.value = _newsUiState.value.copy(news = newsList)
                                    }.launchIn(viewModelScope)
                            }
                        }
                    }.onCompletion {
                        _newsUiState.value = _newsUiState.value.copy(isLoading = false)
                    }
                    .launchIn(viewModelScope)
            }

            is Delete -> {
                deleteFavoriteNewsUseCase.invoke(data = intent.data)
                    .onStart {
                        _newsUiState.value = _newsUiState.value.copy(isLoading = true)
                    }.onEach {
                        getNewsFromLocalUseCase.invoke(intent.data.category)
                            .onEach { newsList ->
                                _newsUiState.value = _newsUiState.value.copy(news = newsList)
                            }.launchIn(viewModelScope)
                        _newsUiState.value = _newsUiState.value.copy(isLoading = false)
                    }.launchIn(viewModelScope)
            }

            GetFavorites -> {
                _newsUiState.value = _newsUiState.value.copy(isLoading = true)
                val newsList = getFavoriteNewsUseCase.invoke()
                _newsUiState.value = _newsUiState.value.copy(isLoading = false, news = newsList)
            }
        }
    }
}
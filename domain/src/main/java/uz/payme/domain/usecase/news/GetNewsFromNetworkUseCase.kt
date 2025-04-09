package uz.payme.domain.usecase.news

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.payme.data.repository.NewsRepository
import uz.payme.domain.mapper.NewsEntityMapper
import uz.payme.domain.models.NewsModel
import javax.inject.Inject

class GetNewsFromNetworkUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(query: String): Flow<Result<List<NewsModel>>> = flow {
        newsRepository.getNewsFromNetwork(query = query).collect { result ->
            emit(result.map { newsList ->
                newsList.map { NewsEntityMapper.map(it) }
            })
        }
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}
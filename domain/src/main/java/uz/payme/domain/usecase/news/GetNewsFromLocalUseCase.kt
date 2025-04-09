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

class GetNewsFromLocalUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(query: String): Flow<List<NewsModel>> = flow {
        newsRepository.getNewsFromLocal(query = query).collect { newsList ->
            emit(newsList.map { NewsEntityMapper.map(it) })
        }
    }.flowOn(Dispatchers.IO)
}
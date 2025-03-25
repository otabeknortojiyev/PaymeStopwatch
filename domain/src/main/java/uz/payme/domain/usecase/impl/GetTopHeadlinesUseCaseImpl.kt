package uz.payme.domain.usecase.impl

import uz.payme.data.repository.NewsRepository
import uz.payme.domain.mapper.NewsMapper
import uz.payme.domain.models.NewsModel
import uz.payme.domain.usecase.GetTopHeadlinesUseCase
import javax.inject.Inject

class GetTopHeadlinesUseCaseImpl @Inject constructor(private val newsRepository: NewsRepository) :
    GetTopHeadlinesUseCase {
    override suspend fun invoke(category: String?): Result<NewsModel> {
        return newsRepository.getTopHeadlines(category = category).mapCatching { response -> NewsMapper.map(response) }
    }
}
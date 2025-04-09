package uz.payme.domain.usecase.news

import kotlinx.coroutines.flow.Flow
import uz.payme.data.repository.NewsRepository
import uz.payme.data.utils.Category
import uz.payme.domain.mapper.NewsModelMapper
import uz.payme.domain.models.NewsModel
import javax.inject.Inject

class UpdateNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(data: NewsModel) : Flow<Unit> = newsRepository.updateNews(data = NewsModelMapper.map(data))
}
package uz.payme.domain.usecase.news

import uz.payme.data.repository.NewsRepository
import uz.payme.data.utils.Category
import uz.payme.domain.mapper.NewsModelMapper
import uz.payme.domain.models.NewsModel
import javax.inject.Inject

class DeleteFavoriteNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(data: NewsModel) = newsRepository.deleteNews(data = NewsModelMapper.map(data))
}
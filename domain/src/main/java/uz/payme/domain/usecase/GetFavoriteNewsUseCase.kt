package uz.payme.domain.usecase

import uz.payme.data.local.room.NewsEntity
import uz.payme.data.repository.NewsRepository
import uz.payme.domain.mapper.NewsEntityMapper
import uz.payme.domain.models.NewsModel
import javax.inject.Inject

class GetFavoriteNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(): List<NewsModel> = newsRepository.getFavorites().map { NewsEntityMapper.map(it) }
}
package uz.payme.domain.usecase

import uz.payme.domain.models.NewsModel

interface GetTopHeadlinesUseCase {
    suspend operator fun invoke(category: String? = null) : Result<NewsModel>
}
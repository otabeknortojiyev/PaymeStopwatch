package uz.payme.data.repository

import uz.payme.data.models.news_response.NewsResponse

interface NewsRepository {
    suspend fun getTopHeadlines(category: String? = null): Result<NewsResponse>
}
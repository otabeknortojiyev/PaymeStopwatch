package uz.payme.data.repository

import kotlinx.coroutines.flow.Flow
import uz.payme.data.local.room.NewsEntity

interface NewsRepository {
    fun getNews(query: String? = null): Flow<Result<List<NewsEntity>>>
    fun getFavorites(): List<NewsEntity>
    fun updateNews(data: NewsEntity)
    fun deleteNews(data: NewsEntity)
}
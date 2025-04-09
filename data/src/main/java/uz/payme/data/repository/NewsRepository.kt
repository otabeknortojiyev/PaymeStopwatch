package uz.payme.data.repository

import kotlinx.coroutines.flow.Flow
import uz.payme.data.local.room.NewsEntity

interface NewsRepository {
    fun getNewsFromNetwork(query: String): Flow<Result<List<NewsEntity>>>
    fun getNewsFromLocal(query: String): Flow<List<NewsEntity>>
    fun getFavorites(): List<NewsEntity>
    fun updateNews(data: NewsEntity) : Flow<Unit>
    fun deleteNews(data: NewsEntity) : Flow<Unit>
}
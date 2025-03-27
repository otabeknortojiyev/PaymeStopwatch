package uz.payme.data.repository.impl

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import uz.payme.data.local.room.NewsDao
import uz.payme.data.local.room.NewsEntity
import uz.payme.data.mapper.NewsResponseMapper
import uz.payme.data.models.news_response.NewsResponse
import uz.payme.data.network.NewsApi
import uz.payme.data.repository.NewsRepository
import uz.payme.data.utils.toResult
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi, private val newsDao: NewsDao
) : NewsRepository {

    private val gson = Gson()

    override fun getNews(query: String?): Flow<Result<List<NewsEntity>>> = flow {
        emit(Result.success(newsDao.getAll()))
        try {
            val result = newsApi.getEverything(query = query).toResult(gson) { response ->
                val list = NewsResponseMapper.map(response)
                newsDao.insert(list)
                Result.success(newsDao.getAll())
            }
            emit(result)
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getFavorites(): List<NewsEntity> = newsDao.getFavorites()

    override fun updateNews(data: NewsEntity) = newsDao.update(data = data)

    override fun deleteNews(data: NewsEntity) = newsDao.delete(data = data)
}
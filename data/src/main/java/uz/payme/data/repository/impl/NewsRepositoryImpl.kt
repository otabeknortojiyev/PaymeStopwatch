package uz.payme.data.repository.impl

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okio.FileNotFoundException
import okio.IOException
import uz.payme.data.local.room.NewsDao
import uz.payme.data.local.room.NewsEntity
import uz.payme.data.mapper.NewsResponseMapper
import uz.payme.data.models.news_response.NewsResponse
import uz.payme.data.network.NewsApi
import uz.payme.data.repository.NewsRepository
import uz.payme.data.utils.toResult
import java.net.SocketTimeoutException
import java.net.UnknownHostException
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

        }
    }.flowOn(Dispatchers.IO)

    override fun getFavorites(): List<NewsEntity> = newsDao.getFavorites()

    override fun updateNews(data: NewsEntity) = newsDao.update(data = data)

    override fun deleteNews(data: NewsEntity) = newsDao.delete(data = data)
}
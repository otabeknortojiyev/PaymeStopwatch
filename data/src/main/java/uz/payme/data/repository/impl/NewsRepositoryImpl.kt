package uz.payme.data.repository.impl

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.payme.data.models.news_response.NewsResponse
import uz.payme.data.network.NewsApi
import uz.payme.data.repository.NewsRepository
import uz.payme.data.utils.toResult
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsApi: NewsApi) : NewsRepository {

    private val gson = Gson()

    override suspend fun getTopHeadlines(category: String?): Result<NewsResponse> = withContext(Dispatchers.IO) {

        newsApi.getTopHeadlines().toResult(gson) {
            Result.success(it)
        }

    }

}
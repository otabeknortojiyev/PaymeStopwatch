package uz.payme.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.payme.data.models.news_response.NewsResponse

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("language") language: String = DEFAULT_LANGUAGE,
        @Query("category") category: String? = null,
        @Query("apiKey") apiKey: String = NEWS_API_KEY
    ) : Response<NewsResponse>

    companion object {
        private const val NEWS_API_KEY = "30dcfd9b0e9749ea8b06b1462e2a6f83"
        private const val DEFAULT_LANGUAGE = "en"
    }

}

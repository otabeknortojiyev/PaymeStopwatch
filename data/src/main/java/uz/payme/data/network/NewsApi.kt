package uz.payme.data.network

import com.google.gson.internal.GsonBuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.payme.data.models.news_response.NewsResponse

interface NewsApi {

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") query: String? = null
    ): Response<NewsResponse>
}

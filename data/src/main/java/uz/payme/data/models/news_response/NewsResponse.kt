package uz.payme.data.models.news_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("status")
    val status: String? = null,
    @SerialName("totalResults")
    val totalResults: Int? = null,
    @SerialName("articles")
    val articles: List<NewsArticle>? = null
)

@Serializable
data class NewsArticle(
    @SerialName("source")
    val source: Source? = null,
    @SerialName("author")
    val author: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("urlToImage")
    val urlToImage: String? = null,
    @SerialName("publishedAt")
    val publishedAt: String? = null,
    @SerialName("content")
    val content: String? = null
)

@Serializable
data class Source(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null
)

package uz.payme.domain.models

data class NewsModel(
    val url: String,
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val sourceName: String?,
    val isFavorite: Boolean
)
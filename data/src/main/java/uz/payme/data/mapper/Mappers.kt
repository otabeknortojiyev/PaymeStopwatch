package uz.payme.data.mapper

import uz.payme.data.local.room.NewsEntity
import uz.payme.data.models.news_response.NewsResponse

object NewsResponseMapper {
    fun map(response: NewsResponse): List<NewsEntity> {
        return response.articles?.map { article ->
            NewsEntity(
                url = article.url.orEmpty(),
                author = article.author,
                title = article.title,
                description = article.description,
                urlToImage = article.urlToImage,
                publishedAt = article.publishedAt,
                content = article.content,
                sourceName = article.source?.name,
                isFavorite = false
            )
        }.orEmpty()
    }
}

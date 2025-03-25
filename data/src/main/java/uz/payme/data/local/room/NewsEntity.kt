package uz.payme.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.payme.data.models.news_response.Source

@Entity("news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    val sourceName: String
)

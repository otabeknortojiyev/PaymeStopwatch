package uz.payme.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun get(): List<NewsEntity>

    @Insert
    fun insert(data: NewsEntity)

    @Delete
    fun delete(data: NewsEntity)
}
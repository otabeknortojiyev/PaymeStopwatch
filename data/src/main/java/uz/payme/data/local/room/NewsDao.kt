package uz.payme.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import uz.payme.data.utils.Category

@Dao
interface NewsDao {
    @Query("SELECT * FROM news WHERE category = :category")
    fun getAll(category: String): List<NewsEntity>

    @Query("SELECT * FROM news WHERE isFavorite = 1")
    fun getFavorites(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: List<NewsEntity>)

    @Update
    fun update(data: NewsEntity)

    @Delete
    fun delete(data: NewsEntity)
}
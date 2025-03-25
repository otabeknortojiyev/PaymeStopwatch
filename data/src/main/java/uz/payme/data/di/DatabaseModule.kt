package uz.payme.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.payme.data.local.room.NewsDao
import uz.payme.data.local.room.NewsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @[Provides Singleton]
    fun providesDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context = context, klass = NewsDatabase::class.java, name = "News.db"
        ).build()
    }

    @[Provides Singleton]
    fun providesNewsDao(database: NewsDatabase): NewsDao {
        return database.newsDao()
    }
}
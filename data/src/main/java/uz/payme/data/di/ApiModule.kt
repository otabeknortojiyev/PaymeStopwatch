package uz.payme.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.payme.data.network.NewsApi
import uz.payme.data.network.WeatherApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @[Provides Singleton]
    fun providesWeatherApi(@WeatherRetrofit retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)

    @[Provides Singleton]
    fun providesNewsApi(@NewsRetrofit retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)
}
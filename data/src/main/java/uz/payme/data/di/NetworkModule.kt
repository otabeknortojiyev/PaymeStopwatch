package uz.payme.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

const val WeatherOkHttpClient = "WeatherOkHttpClient"
const val NewsOkHttpClient = "NewsOkHttpClient"
const val WeatherRetrofit = "WeatherRetrofit"
const val NewsRetrofit = "NewsRetrofit"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    // TODO difference between network and application interceptors Retrofit

    private val json = Json { ignoreUnknownKeys = true }

    @[Provides Singleton Named(WeatherOkHttpClient)]
    fun providesWeatherOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .build()

    @[Provides Singleton Named(WeatherRetrofit)]
    fun providesWeatherRetrofit(@Named(WeatherOkHttpClient) okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @[Provides Singleton Named(NewsOkHttpClient)]
    fun providesNewsOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request
                    .newBuilder()
                    .header("X-Api-Key", "30dcfd9b0e9749ea8b06b1462e2a6f83")
                    .build()
                chain.proceed(newRequest)
            }.build()

    @[Provides Singleton Named(NewsRetrofit)]
    fun providesNewsRetrofit(@Named(NewsOkHttpClient) okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://newsapi.org/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
}
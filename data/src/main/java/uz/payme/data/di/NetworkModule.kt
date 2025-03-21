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
import uz.payme.data.network.WeatherApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkProvider {

    private val json = Json { ignoreUnknownKeys = true }

    @[Provides Singleton]
    fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(ChuckerInterceptor.Builder(context).build()).build()

    // TODO difference between network and application interceptors Retrofit

    @[Provides Singleton]
    fun providesWeatherRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl("https://api.openweathermap.org/").client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).build()

    @[Provides Singleton]
    fun providesWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)
}

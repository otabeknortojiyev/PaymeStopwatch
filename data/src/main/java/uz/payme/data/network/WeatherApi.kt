package uz.payme.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun current(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") lang: String = "ru",
        @Query("appid") appid: String = WEATHER_API_KEY
    ): Response<CoordinatesResponse>

    @GET("data/2.5/forecast")
    suspend fun forecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = WEATHER_API_KEY
    ): Response<ForecastResponse>

    companion object {
        private const val WEATHER_API_KEY = "7fd3fa2b3bf71545e2ff3b1a1f0871a0"
    }
}
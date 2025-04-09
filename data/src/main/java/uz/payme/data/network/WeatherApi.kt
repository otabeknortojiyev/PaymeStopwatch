package uz.payme.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.payme.data.BuildConfig
import uz.payme.data.models.weather_response.ForecastResponse
import uz.payme.data.models.weather_response.OneCallResponse

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun current(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") lang: String = "ru",
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY
    ): Response<OneCallResponse>

    @GET("data/2.5/forecast")
    suspend fun forecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY
    ): Response<ForecastResponse>
}
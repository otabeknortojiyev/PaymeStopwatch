package uz.payme.data.models.weather_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OneCallResponse(
    @SerialName("coord")
    val coordinates: Coordinates? = null,
    val weather: List<WeatherBody>? = null,
    val base: String? = null,
    val main: Main? = null,
    val visibility: Int? = null,
    val wind: Wind? = null,
    val rain: Rain? = null,
    val snow: Snow? = null,
    val clouds: Clouds? = null,
    val dt: Long? = null,
    val sys: Sys? = null,
    val timezone: Int? = null,
    val id: Int? = null,
    val name: String? = null,
    val cod: Int? = null
)

@Serializable
data class Coordinates(
    val lon: Double? = null,
    val lat: Double? = null
)

@Serializable
data class Main(
    val temp: Double? = null,
    @SerialName("feels_like")
    val feelsLike: Double? = null,
    val pressure: Int? = null,
    val humidity: Int? = null,
    @SerialName("temp_min")
    val tempMin: Double? = null,
    @SerialName("temp_max")
    val tempMax: Double? = null,
    @SerialName("sea_level")
    val seaLevel: Int? = null,
    @SerialName("grnd_level")
    val groundLevel: Int? = null
)

@Serializable
data class Wind(
    val speed: Double? = null,
    val deg: Double? = null,
    val gust: Double? = null
)

@Serializable
data class Rain(
    @SerialName("1h")
    val oneHour: Double? = null
)

@Serializable
data class Clouds(
    val all: Int? = null
)

@Serializable
data class Snow(
    @SerialName("1h")
    val oneHour: Double? = null
)

@Serializable
data class Sys(
    val type: Int? = null,
    val id: Int? = null,
    val message: String? = null,
    val country: String? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null
)

@Serializable
data class WeatherBody(
    val id: Int? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)
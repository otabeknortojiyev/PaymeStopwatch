package uz.payme.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordinatesResponse(
    @SerialName("coord")
    val coordinates: Coordinates? = null,
    @SerialName("weather")
    val weather: List<WeatherBody>? = null,
    @SerialName("base")
    val base: String? = null,
    @SerialName("main")
    val main: Main? = null,
    @SerialName("visibility")
    val visibility: Int? = null,
    @SerialName("wind")
    val wind: Wind? = null,
    @SerialName("rain")
    val rain: Rain? = null,
    @SerialName("snow")
    val snow: Snow? = null,
    @SerialName("clouds")
    val clouds: Clouds? = null,
    @SerialName("dt")
    val dt: Long? = null,
    @SerialName("sys")
    val sys: Sys? = null,
    @SerialName("timezone")
    val timezone: Int? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("cod")
    val cod: Int? = null
)

@Serializable
data class Coordinates(
    @SerialName("lon")
    val lon: Double? = null,
    @SerialName("lat")
    val lat: Double? = null
)

@Serializable
data class Main(
    @SerialName("temp")
    val temp: Double? = null,
    @SerialName("feels_like")
    val feelsLike: Double? = null,
    @SerialName("pressure")
    val pressure: Int? = null,
    @SerialName("humidity")
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
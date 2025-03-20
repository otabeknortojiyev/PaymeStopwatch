package uz.payme.domain.models

data class OneCallModel(
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

data class Coordinates(
    val lon: Double? = null,
    val lat: Double? = null
)

data class Main(
    val temp: Double? = null,
    val feelsLike: Double? = null,
    val pressure: Int? = null,
    val humidity: Int? = null,
    val tempMin: Double? = null,
    val tempMax: Double? = null,
    val seaLevel: Int? = null,
    val groundLevel: Int? = null
)

data class Wind(
    val speed: Double? = null,
    val deg: Double? = null,
    val gust: Double? = null
)

data class Rain(
    val oneHour: Double? = null
)

data class Clouds(
    val all: Int? = null
)

data class Snow(
    val oneHour: Double? = null
)

data class Sys(
    val type: Int? = null,
    val id: Int? = null,
    val message: String? = null,
    val country: String? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null
)

data class WeatherBody(
    val id: Int? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)
package uz.payme.domain.models

data class ForecastModel(
    val cod: String? = null,
    val list: List<Per3Hour>? = null,
)

data class Per3Hour(
    val dt: Long? = null,
    val main: Main? = null,
    val dtTxt: String? = null,
    val weather: List<WeatherCode>? = null,
)

data class WeatherCode(
    val id: Int? = null,
    val main: String,
    val description: String,
    val icon: String,
)
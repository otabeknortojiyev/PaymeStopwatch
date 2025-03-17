package uz.payme.otabek.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
  @SerialName("cod")
  val cod: String? = null,
  @SerialName("list")
  val list: List<Per3Hour>? = null,
)

@Serializable
data class Per3Hour(
  @SerialName("dt")
  val dt: Long? = null,
  @SerialName("main")
  val main: Main? = null,
  @SerialName("dt_txt")
  val dtTxt: String? = null,
  @SerialName("weather")
  val weather: List<WeatherCode>? = null,
)

@Serializable
data class WeatherCode(
  @SerialName("id")
  val id: Int? = null
)


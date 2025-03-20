package uz.payme.data.mapper

import uz.payme.data.network.CoordinatesResponse
import uz.payme.data.network.ForecastResponse
import uz.payme.domain.models.Clouds
import uz.payme.domain.models.Coordinates
import uz.payme.domain.models.ForecastModel
import uz.payme.domain.models.Main
import uz.payme.domain.models.OneCallModel
import uz.payme.domain.models.Per3Hour
import uz.payme.domain.models.Rain
import uz.payme.domain.models.Snow
import uz.payme.domain.models.Sys
import uz.payme.domain.models.WeatherBody
import uz.payme.domain.models.Wind

fun CoordinatesResponse.toOneCallModel(): OneCallModel {
    return OneCallModel(
        coordinates = this.coordinates as Coordinates?,
        weather = this.weather as List<WeatherBody>?,
        base = this.base,
        main = this.main as Main?,
        visibility = this.visibility,
        wind = this.wind as Wind?,
        rain = this.rain as Rain?,
        snow = this.snow as Snow?,
        clouds = this.clouds as Clouds?,
        dt = this.dt,
        sys = this.sys as Sys?,
        timezone = this.timezone,
        id = this.id,
        name = this.name,
        cod = this.cod
    )
}

fun ForecastResponse.toForecastModel() : ForecastModel {
    return ForecastModel(
        cod = this.cod,
        list = this.list as List<Per3Hour>?,
    )
}
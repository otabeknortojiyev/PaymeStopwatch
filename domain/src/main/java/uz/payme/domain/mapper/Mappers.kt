package uz.payme.domain.mapper

import uz.payme.data.models.news_response.NewsResponse
import uz.payme.data.models.weather_response.OneCallResponse
import uz.payme.data.models.weather_response.ForecastResponse
import uz.payme.domain.models.Clouds
import uz.payme.domain.models.Coordinates
import uz.payme.domain.models.ForecastModel
import uz.payme.domain.models.Main
import uz.payme.domain.models.NewsArticle
import uz.payme.domain.models.NewsModel
import uz.payme.domain.models.OneCallModel
import uz.payme.domain.models.Per3Hour
import uz.payme.domain.models.Rain
import uz.payme.domain.models.Snow
import uz.payme.domain.models.Source
import uz.payme.domain.models.Sys
import uz.payme.domain.models.WeatherBody
import uz.payme.domain.models.WeatherCode
import uz.payme.domain.models.Wind

object OneCallMapper {
    fun map(response: OneCallResponse): OneCallModel {
        return OneCallModel(
            coordinates = response.coordinates?.let { Coordinates(it.lon, it.lat) },
            weather = response.weather?.map { WeatherBody(it.id, it.main, it.description, it.icon) },
            base = response.base,
            main = response.main?.let {
                Main(
                    temp = it.temp,
                    feelsLike = it.feelsLike,
                    pressure = it.pressure,
                    humidity = it.humidity,
                    tempMin = it.tempMin,
                    tempMax = it.tempMax,
                    seaLevel = it.seaLevel,
                    groundLevel = it.groundLevel
                )
            },
            visibility = response.visibility,
            wind = response.wind?.let { Wind(it.speed, it.deg, it.gust) },
            rain = response.rain?.let { Rain(it.oneHour) },
            snow = response.snow?.let { Snow(it.oneHour) },
            clouds = response.clouds?.let { Clouds(it.all) },
            dt = response.dt,
            sys = response.sys?.let {
                Sys(
                    type = it.type,
                    id = it.id,
                    message = it.message,
                    country = it.country,
                    sunrise = it.sunrise,
                    sunset = it.sunset
                )
            },
            timezone = response.timezone,
            id = response.id,
            name = response.name,
            cod = response.cod
        )
    }
}

object ForecastMapper {
    fun map(response: ForecastResponse): ForecastModel {
        return ForecastModel(
            cod = response.cod,
            list = response.list?.map { per3Hour ->
                Per3Hour(
                    dt = per3Hour.dt,
                    main = per3Hour.main?.let {
                        Main(
                            temp = it.temp,
                            feelsLike = it.feelsLike,
                            pressure = it.pressure,
                            humidity = it.humidity,
                            tempMin = it.tempMin,
                            tempMax = it.tempMax,
                            seaLevel = it.seaLevel,
                            groundLevel = it.groundLevel
                        )
                    },
                    dtTxt = per3Hour.dtTxt,
                    weather = per3Hour.weather?.map { weatherCode ->
                        WeatherCode(
                            id = weatherCode.id,
                            main = weatherCode.main,
                            description = weatherCode.description,
                            icon = weatherCode.icon
                        )
                    }
                )
            }
        )
    }
}

object NewsMapper {
    fun map(response: NewsResponse): NewsModel {
        return NewsModel(
            status = response.status,
            totalResults = response.totalResults,
            articles = response.articles?.map { articles ->
                NewsArticle(
                    source = articles.source?.let {
                        Source(
                            id = it.id,
                            name = it.name
                        )
                    },
                    author = articles.author,
                    title = articles.title,
                    description = articles.description,
                    url = articles.url,
                    urlToImage = articles.urlToImage,
                    publishedAt = articles.publishedAt,
                    content = articles.content
                )
            }
        )
    }
}


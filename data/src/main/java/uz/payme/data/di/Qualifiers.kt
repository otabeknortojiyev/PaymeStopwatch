package uz.payme.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsOkHttpClient



@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsRetrofit
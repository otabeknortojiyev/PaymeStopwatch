package uz.payme.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.payme.data.repository.DataStoreRepository
import uz.payme.data.repository.NewsRepository
import uz.payme.data.repository.OpenWeatherRepository
import uz.payme.data.repository.UtilsRepository
import uz.payme.data.repository.impl.DataStoreRepositoryImpl
import uz.payme.data.repository.impl.NewsRepositoryImpl
import uz.payme.data.repository.impl.OpenWeatherRepositoryImp
import uz.payme.data.repository.impl.UtilsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    // TODO bind/provides

    @[Binds Singleton]
    fun bindDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository

    @[Binds Singleton]
    fun bindOpenWeatherRepository(impl: OpenWeatherRepositoryImp): OpenWeatherRepository

    @[Binds Singleton]
    fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository

    @[Binds Singleton]
    fun bindUtilsRepository(impl: UtilsRepositoryImpl): UtilsRepository
}
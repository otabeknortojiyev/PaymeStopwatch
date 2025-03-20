package uz.payme.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.payme.data.repository.DataStoreRepositoryImpl
import uz.payme.data.repository.OpenWeatherRepositoryImp
import uz.payme.domain.repository.DataStoreRepository
import uz.payme.domain.repository.OpenWeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    // TODO bind/provides

    @[Binds Singleton]
    fun bindDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository

    @[Binds Singleton]
    fun bindOpenWeatherRepository(impl: OpenWeatherRepositoryImp): OpenWeatherRepository
}
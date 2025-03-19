package uz.payme.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import uz.payme.otabek.data.repository.DataStoreRepository
import uz.payme.otabek.data.repository.DataStoreRepositoryImpl
import uz.payme.otabek.data.repository.OpenWeatherRepository
import uz.payme.otabek.data.repository.OpenWeatherRepositoryImp

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    // TODO bind/provides

    @[Binds Singleton]
    fun bindDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository

    @[Binds Singleton]
    fun bindOpenWeatherRepository(impl: OpenWeatherRepositoryImp): OpenWeatherRepository
}
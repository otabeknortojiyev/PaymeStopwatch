package uz.payme.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.payme.domain.usecase.GetCurrentWeatherUseCase
import uz.payme.domain.usecase.GetForecastWeatherUseCase
import uz.payme.domain.usecase.GetIsFirstUseCase
import uz.payme.domain.usecase.GetTimeUseCase
import uz.payme.domain.usecase.GetWasRunningUseCase
import uz.payme.domain.usecase.SaveIsFirstUseCase
import uz.payme.domain.usecase.SaveTimeUseCase
import uz.payme.domain.usecase.SaveWasRunningUseCase
import uz.payme.domain.usecase.impl.GetCurrentWeatherUseCaseImpl
import uz.payme.domain.usecase.impl.GetForecastWeatherUseCaseImpl
import uz.payme.domain.usecase.impl.GetIsFirstUseCaseImpl
import uz.payme.domain.usecase.impl.GetTimeUseCaseImpl
import uz.payme.domain.usecase.impl.GetWasRunningUseCaseImpl
import uz.payme.domain.usecase.impl.SaveIsFirstUseCaseImpl
import uz.payme.domain.usecase.impl.SaveTimeUseCaseImpl
import uz.payme.domain.usecase.impl.SaveWasRunningUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @[Binds ViewModelScoped]
    fun bindsSaveTimeUseCase(impl: SaveTimeUseCaseImpl): SaveTimeUseCase

    @[Binds ViewModelScoped]
    fun bindsGetTimeUseCase(impl: GetTimeUseCaseImpl): GetTimeUseCase

    @[Binds ViewModelScoped]
    fun bindsSaveWasRunningUseCase(impl: SaveWasRunningUseCaseImpl): SaveWasRunningUseCase

    @[Binds ViewModelScoped]
    fun bindsGetWasRunningUseCase(impl: GetWasRunningUseCaseImpl): GetWasRunningUseCase

    @[Binds ViewModelScoped]
    fun bindsSaveIsFirstUseCase(impl: SaveIsFirstUseCaseImpl): SaveIsFirstUseCase

    @[Binds ViewModelScoped]
    fun bindsGetIsFirstUseCase(impl: GetIsFirstUseCaseImpl): GetIsFirstUseCase

    @[Binds ViewModelScoped]
    fun bindsGetCurrentWeatherUseCase(impl: GetCurrentWeatherUseCaseImpl): GetCurrentWeatherUseCase

    @[Binds ViewModelScoped]
    fun bindsGetForecastWeatherUseCase(impl: GetForecastWeatherUseCaseImpl): GetForecastWeatherUseCase
}
package br.com.renatoarg.weatherapp2021

import br.com.renatoarg.weatherapp2021.data.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalUnsignedTypes
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(): HomeRepository = HomeRepository()
}

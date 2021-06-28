package br.com.renatoarg.weatherapp2021.modules

import br.com.renatoarg.data.api.cities.CitiesRepository
import br.com.renatoarg.data.database.ItemsRepository
import org.koin.dsl.module

@ExperimentalUnsignedTypes
val citiesModule = module(override = true) {

    fun provideCitiesRepository(itemsRepository: ItemsRepository) : CitiesRepository {
        return CitiesRepository(itemsRepository)
    }

    single { provideCitiesRepository(get()) }
}
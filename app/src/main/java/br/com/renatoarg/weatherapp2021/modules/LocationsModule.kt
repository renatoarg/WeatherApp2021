package br.com.renatoarg.weatherapp2021.modules

import br.com.renatoarg.data.api.locations.LocationsClient
import br.com.renatoarg.data.api.locations.LocationsRepository
import org.koin.dsl.module

@ExperimentalUnsignedTypes
val locationsModule = module(override = true) {
    single { LocationsClient() }
    single { LocationsRepository(get(), get()) }
}
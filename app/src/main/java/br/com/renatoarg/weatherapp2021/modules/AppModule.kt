package br.com.renatoarg.weatherapp2021.modules

import br.com.renatoarg.data.api.locations.LocationsClient
import br.com.renatoarg.data.api.locations.LocationsRepository
import br.com.renatoarg.data.api.home.HomeClient
import br.com.renatoarg.data.api.home.HomeRepository
import org.koin.dsl.module

@ExperimentalUnsignedTypes
val homeModule = module(override = true) {
    single { HomeClient() }
    single { HomeRepository(get()) }
}

@ExperimentalUnsignedTypes
val addPlaceModule = module(override = true) {
    single { LocationsClient() }
    single { LocationsRepository(get()) }
}
package br.com.renatoarg.weatherapp2021.modules

import br.com.renatoarg.data.api.home.HomeClient
import br.com.renatoarg.data.api.home.HomeRepository
import org.koin.dsl.module

@ExperimentalUnsignedTypes
val homeModule = module(override = true) {
    single { HomeClient() }
    single { HomeRepository(get(), get()) }
}
package br.com.renatoarg.weatherapp2021

import br.com.renatoarg.data.home.HomeClient
import br.com.renatoarg.data.home.HomeRepository
import br.com.renatoarg.weatherapp2021.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalUnsignedTypes
val homeModule = module(override = true) {
    single { HomeClient() }
    single { HomeRepository(get()) }
    viewModel { HomeViewModel(get()) }
}
package br.com.renatoarg.weatherapp2021.modules

import br.com.renatoarg.data.api.home.HomeClient
import br.com.renatoarg.data.api.home.HomeRepository
import br.com.renatoarg.data.sharedPreferences.SharedPreferencesHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

@ExperimentalUnsignedTypes
val sharedPrefsModule = module(override = true) {
    single { SharedPreferencesHelper(androidApplication()) }
}
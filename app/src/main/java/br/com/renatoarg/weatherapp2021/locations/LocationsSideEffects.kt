package br.com.renatoarg.weatherapp2021.locations

sealed class LocationsSideEffects {
    object OnShowToast : LocationsSideEffects()
}
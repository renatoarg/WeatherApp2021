package br.com.renatoarg.weatherapp2021.home

import br.com.renatoarg.data.entity.WeatherForLocation

sealed class HomeState {
    data class OnFetchWeatherForLocation(
        val result: WeatherForLocation
    ) : HomeState()
}
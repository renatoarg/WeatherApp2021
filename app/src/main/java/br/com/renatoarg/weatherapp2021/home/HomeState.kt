package br.com.renatoarg.weatherapp2021.home

import br.com.renatoarg.data.api.entity.WeatherForLocation
import com.airbnb.mvrx.MavericksState

data class HomeState(
    val weatherForLocation: WeatherForLocation? = null,
    val isLoading: Boolean = true
) : MavericksState
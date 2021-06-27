package br.com.renatoarg.weatherapp2021.home

import br.com.renatoarg.data.api.home.entity.WeatherForLocation
import com.airbnb.mvrx.MavericksState

data class HomeViewState(
    val weatherForLocation: WeatherForLocation? = null,
    val isLoading: Boolean = true,
    val httpErrorMessage: String? = null,
    val errorMessage: String? = null
) : MavericksState
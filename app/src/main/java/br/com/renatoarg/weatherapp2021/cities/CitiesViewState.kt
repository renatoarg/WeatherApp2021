package br.com.renatoarg.weatherapp2021.cities

import br.com.renatoarg.data.api.home.entity.Location
import br.com.renatoarg.data.api.home.entity.WeatherForLocation
import br.com.renatoarg.data.database.Item
import com.airbnb.mvrx.MavericksState

data class CitiesViewState(
    val locations: List<Item>? = null,
    val isLoading: Boolean = true,
    val httpErrorMessage: String? = null,
    val errorMessage: String? = null
) : MavericksState
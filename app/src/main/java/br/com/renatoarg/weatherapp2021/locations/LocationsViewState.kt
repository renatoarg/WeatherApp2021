package br.com.renatoarg.weatherapp2021.locations

import br.com.renatoarg.data.api.home.entity.Location
import com.airbnb.mvrx.MavericksState

data class LocationsViewState(
    val locations: List<Location> = emptyList(),
    val showEmptyLocations: Boolean = false,
    val isLoading: Boolean = true,
    val httpErrorMessage: String? = null,
    val errorMessage: String? = null
) : MavericksState
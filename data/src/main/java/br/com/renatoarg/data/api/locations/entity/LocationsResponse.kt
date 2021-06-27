package br.com.renatoarg.data.api.locations.entity

import br.com.renatoarg.data.api.home.entity.Location


data class LocationsResponse(
    val locations: List<Location> = emptyList()
)
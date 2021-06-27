package br.com.renatoarg.data.api.locations

import br.com.renatoarg.data.api.home.entity.Location
import br.com.renatoarg.data.api.locations.entity.LocationsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationsApi {
    @GET("/api/location/search")
    suspend fun queryLocations(
        @Query("query") query: String
    ): List<Location>
}
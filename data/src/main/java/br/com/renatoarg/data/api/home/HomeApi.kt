package br.com.renatoarg.data.api.home

import br.com.renatoarg.data.api.home.entity.WeatherForLocation
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {

    @GET("/api/location/{whereOnEarthId}")
    suspend fun getWeatherForWhereOnEarthId(
        @Path("whereOnEarthId") whereOnEarthId: Int
    ): WeatherForLocation
}
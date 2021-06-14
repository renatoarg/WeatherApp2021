package br.com.renatoarg.data.home

import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("/v4/timelines")
    suspend fun fetchTimelines(
        @Query("apikey") apikey: String,
        @Query("location") location: String,
        @Query("fields") fields: List<String>
    ): Welcome4
}
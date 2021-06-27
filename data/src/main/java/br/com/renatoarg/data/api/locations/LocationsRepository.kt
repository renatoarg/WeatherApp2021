package br.com.renatoarg.data.api.locations

import br.com.renatoarg.data.api.APIResponse
import br.com.renatoarg.data.api.Constants.Companion.UNEXPECTED_ERROR
import br.com.renatoarg.data.api.home.entity.Location
import br.com.renatoarg.data.api.locations.entity.LocationsResponse
import retrofit2.HttpException

class LocationsRepository(
    private val client: LocationsClient
) {
    suspend fun queryLocations(
        query: String
    ): APIResponse<List<Location>> {
        return try {
            val response = client.api.queryLocations(
                query
            )
            APIResponse(data = response)
        } catch (e: Exception) {
            handleError(e)
        }
    }

    private fun handleError(e: Exception): APIResponse<List<Location>> =
        when (e) {
            is HttpException -> createHttpErrorResponse(e)
            is NullPointerException -> APIResponse(data = null, errorMessage = "Something is null")
            else -> APIResponse(data = null, errorMessage = e.message ?: UNEXPECTED_ERROR)
        }

    private fun createHttpErrorResponse(e: HttpException): APIResponse<List<Location>> =
        APIResponse(
            data = null,
            errorMessage = e.localizedMessage ?: UNEXPECTED_ERROR,
            errorCode = e.code()
        )
}
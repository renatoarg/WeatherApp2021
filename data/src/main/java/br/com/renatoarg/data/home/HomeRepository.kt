package br.com.renatoarg.data.home

import br.com.renatoarg.data.APIResponse
import br.com.renatoarg.data.Constants.Companion.UNEXPECTED_ERROR
import br.com.renatoarg.data.entity.ConsolidatedWeather
import br.com.renatoarg.data.entity.WeatherForLocation
import retrofit2.HttpException

class HomeRepository(
    private val client: HomeClient
) {
    private var responseCache: WeatherForLocation? = null

    suspend fun fetchWeatherForLocation(
        whereOnEarthId: Int,
        refresh: Boolean = false
    ): APIResponse<WeatherForLocation> {
        return if (responseCache != null && !refresh) {
            APIResponse(data = responseCache)
        } else {
            try {
                responseCache = client.api.getWeatherForWhereOnEarthId(
                    whereOnEarthId
                )
                return APIResponse(data = responseCache)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleError(e: Exception): APIResponse<WeatherForLocation> =
        when (e) {
            is HttpException -> createHttpErrorResponse(e)
            is NullPointerException -> APIResponse(data = null, errorMessage = "Something is null")
            else -> APIResponse(data = null, errorMessage = e.message ?: UNEXPECTED_ERROR)
        }

    private fun createHttpErrorResponse(e: HttpException): APIResponse<WeatherForLocation> =
        APIResponse(
            data = null,
            errorMessage = e.localizedMessage ?: UNEXPECTED_ERROR,
            errorCode = e.code()
        )
}
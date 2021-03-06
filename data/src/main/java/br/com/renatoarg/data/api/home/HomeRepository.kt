package br.com.renatoarg.data.api.home

import androidx.lifecycle.asLiveData
import br.com.renatoarg.data.api.APIResponse
import br.com.renatoarg.data.api.Constants.Companion.UNEXPECTED_ERROR
import br.com.renatoarg.data.api.home.entity.WeatherForLocation
import br.com.renatoarg.data.database.Item
import br.com.renatoarg.data.database.ItemsRepository
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException

class HomeRepository(
    private val client: HomeClient,
    private val database: ItemsRepository
) {

    private var responseCache: WeatherForLocation? = null

    private var locationId: Int? = null
    val getLocationId: Int? get() = locationId

    suspend fun fetchWeatherForLocation(
        locationId: Int,
        refresh: Boolean = false
    ): APIResponse<WeatherForLocation> {
        return if (responseCache != null && !refresh) {
            APIResponse(data = responseCache)
        } else {
            try {
                this.locationId = locationId
                responseCache = client.api.getWeatherForWhereOnEarthId(
                    locationId
                )
                return APIResponse(data = responseCache)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    suspend fun onRefresh() {
        locationId?.let {
            fetchWeatherForLocation(it, true)
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

    suspend fun fetchMainLocationId(): Item? {
        val list = database.allItems.firstOrNull()
        return list?.firstOrNull()
    }
}
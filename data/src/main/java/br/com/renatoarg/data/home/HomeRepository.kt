package br.com.renatoarg.data.home

import br.com.renatoarg.data.APIResponse
import br.com.renatoarg.data.BuildConfig
import br.com.renatoarg.data.Constants.Companion.UNEXPECTED_ERROR
import retrofit2.HttpException

class HomeRepository(
    private val client: HomeClient
) {
    private var responseCache: Welcome4? = null

    suspend fun fetchTimelines(
        location: String,
        fields: List<String>,
        refresh: Boolean = false
    ): APIResponse<List<Timeline>> {
        return if (responseCache != null && !refresh) {
            APIResponse(data = responseCache?.data?.timelines)
        } else {
            try {
                responseCache = client.api.fetchTimelines(
                    apikey = BuildConfig.API_KEY,
                    location = location,
                    fields = fields
                )
                return APIResponse(data = responseCache!!.data.timelines)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleError(e: Exception): APIResponse<List<Timeline>> =
        when (e) {
            is HttpException -> createHttpErrorResponse(e)
            else -> APIResponse(data = null, errorMessage = e.message ?: UNEXPECTED_ERROR)
        }

    private fun createHttpErrorResponse(e: HttpException): APIResponse<List<Timeline>> =
        APIResponse(
            data = null,
            errorMessage = e.localizedMessage ?: UNEXPECTED_ERROR,
            errorCode = e.code()
        )
}
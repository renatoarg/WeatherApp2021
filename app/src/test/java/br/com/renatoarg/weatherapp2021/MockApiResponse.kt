package br.com.renatoarg.weatherapp2021

import br.com.renatoarg.data.api.APIResponse
import io.mockk.mockk

object MockAPIResponse {

    inline fun <reified T> getResponse(
        success: Boolean = true,
        errorMessage: String = "",
        errorCode: Int = 0,
        data: Any? = null
    ): APIResponse<T> {
        return if (success) {
            APIResponse(data as T?, errorMessage, errorCode)
        } else {
            APIResponse(data as T?, errorMessage, errorCode)
        }
    }
}
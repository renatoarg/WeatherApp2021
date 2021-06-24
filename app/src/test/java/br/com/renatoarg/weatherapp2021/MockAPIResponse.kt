package br.com.renatoarg.weatherapp2021

import br.com.renatoarg.data.APIResponse
import io.mockk.mockk

object MockAPIResponse {

    inline fun <reified T> getResponse(
        success: Boolean = true,
        errorMessage: String = "",
        errorCode: Int = 0,
        data: Any
    ): APIResponse<T> {
        return if (success) {
            APIResponse(data as T, errorMessage, errorCode)
        } else {
            APIResponse(mockk(), errorMessage, errorCode)
        }
    }
}
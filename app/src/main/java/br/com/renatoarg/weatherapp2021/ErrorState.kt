package br.com.renatoarg.weatherapp2021

sealed class ErrorState {

    data class OnError(
        val error: String
    ) : ErrorState()

    data class OnHttpError(
        val error: String
    ) : ErrorState()
}
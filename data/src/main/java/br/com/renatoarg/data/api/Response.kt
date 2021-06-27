package br.com.renatoarg.data.api

sealed class Response<out T> {

    data class Success<out T>(
        val data: T
    ) : Response<T>()

    data class Error(val errorMessage: String, val errorCode: Int) : Response<Nothing>()
    data class HttpError(val errorMessage: String) : Response<Nothing>()

}
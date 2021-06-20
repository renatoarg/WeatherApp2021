package br.com.renatoarg.data.api

sealed class BaseResponse<out T> {

    data class Success<out T>(
        val data: T
    ) : BaseResponse<T>()

    data class Error(val errorMessage: String, val errorCode: Int) : BaseResponse<Nothing>()
    data class HttpError(val errorMessage: String) : BaseResponse<Nothing>()

}
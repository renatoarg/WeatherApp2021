package br.com.renatoarg.data.api

open class APIResponse<T> constructor(
    val data: T? = null,
    val errorMessage: String = "",
    val errorCode: Int = 0
)
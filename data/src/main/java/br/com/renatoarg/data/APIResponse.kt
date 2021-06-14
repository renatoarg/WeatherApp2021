package br.com.renatoarg.data

open class APIResponse<T> constructor(
    val data: T? = null,
    val errorMessage: String = "",
    val errorCode: Int = 0
)
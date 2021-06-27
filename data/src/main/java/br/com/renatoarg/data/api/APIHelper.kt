package br.com.renatoarg.data.api

object APIHelper {
    inline fun <T> callApi(apiCall: () -> APIResponse<T>): Response<T> {
        val response = apiCall.invoke()
        response.data?.let {
            return Response.Success(it)
        } ?: run {
            return when (response.errorCode) {
                404 -> Response.HttpError(response.errorMessage)
                else -> Response.Error(response.errorMessage, response.errorCode)
            }
        }
    }
}
package br.com.renatoarg.data

object NetworkHelper {
    inline fun <T> makeApiCall(apiCall: () -> APIResponse<T>): BaseResponse<T> {
        val response = apiCall.invoke()
        response.data?.let {
            return BaseResponse.Success(it)
        } ?: run {
            return when (response.errorCode) {
                404 -> BaseResponse.HttpError(response.errorMessage)
                else -> BaseResponse.Error(response.errorMessage, response.errorCode)
            }
        }
    }
}
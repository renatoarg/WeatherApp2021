package br.com.renatoarg.data.api.base

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseApiClient {
    protected lateinit var retrofit: Retrofit

    fun create(
        restBaseUrl: String,
        httpClient: OkHttpClient,
        gson: Gson
    ) {
        retrofit = Retrofit.Builder()
            .baseUrl(restBaseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
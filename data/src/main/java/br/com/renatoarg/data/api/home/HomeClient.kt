package br.com.renatoarg.data.api.home

import br.com.renatoarg.data.BuildConfig
import br.com.renatoarg.data.api.base.BaseApiClient
import br.com.renatoarg.data.api.base.RequestInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class HomeClient : BaseApiClient() {

    private val baseUrl = "https://www.metaweather.com/"

    var baseClientHttp: OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.MINUTES)

    val api: HomeApi
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            baseClientHttp.addInterceptor(RequestInterceptor())
            if (BuildConfig.DEBUG) {
                baseClientHttp.addInterceptor(interceptor)
            }
            create(
                baseUrl, baseClientHttp.build(), GsonBuilder().create()
            )
            return retrofit.create(HomeApi::class.java)
        }
}
package com.staynight.moviedb.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    companion object{
        const val API_KEY = "c9ec85d0cffbdadef39254c01a5496b0"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val request = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}
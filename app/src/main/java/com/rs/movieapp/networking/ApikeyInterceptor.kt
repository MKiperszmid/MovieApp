package com.rs.movieapp.networking

import com.rs.movieapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApikeyInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val chainRequest = chain.request()
        val request = chainRequest.newBuilder()
        val originalUrl = chainRequest.url
        val apiKeyIntegrated = originalUrl.newBuilder().addQueryParameter("api_key", BuildConfig.TMDB_API_KEY).build()
        request.url(apiKeyIntegrated)
        return chain.proceed(request.build())
    }
}
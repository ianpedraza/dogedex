package com.ianpedraza.dogedex.framework.api

import okhttp3.Interceptor
import okhttp3.Response

object ApiServiceInterceptor : Interceptor {

    const val NEEDS_AUTH_HEADER_KEY = "needs_authentication"

    private var authenticationToken: String? = null

    fun setAuthenticationToken(sessionToken: String) {
        this.authenticationToken = sessionToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        if (request.headers.contains(Pair(NEEDS_AUTH_HEADER_KEY, "true"))) {
            if (authenticationToken == null) {
                throw RuntimeException("Need to be authenticated")
            } else {
                requestBuilder.addHeader("AUTH-TOKEN", authenticationToken!!)
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}

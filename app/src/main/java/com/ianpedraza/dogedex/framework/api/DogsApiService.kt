package com.ianpedraza.dogedex.framework.api

import com.ianpedraza.dogedex.framework.api.response.DogsListResponse
import retrofit2.http.GET

interface DogsApiService {
    @GET("dogs")
    suspend fun getAllDogs(): DogsListResponse
}

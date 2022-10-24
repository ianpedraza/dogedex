package com.ianpedraza.dogedex.framework.api

import com.ianpedraza.dogedex.framework.api.auth.response.SignupResponse
import com.ianpedraza.dogedex.framework.api.dogs.response.DogsListResponse
import com.ianpedraza.dogedex.framework.api.dto.SignUpDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DogsApi {
    @GET("dogs")
    suspend fun getAllDogs(): DogsListResponse

    @POST("sign_up")
    suspend fun signup(
        @Body
        signUpDTO: SignUpDTO
    ): SignupResponse
}

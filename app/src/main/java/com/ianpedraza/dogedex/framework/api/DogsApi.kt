package com.ianpedraza.dogedex.framework.api

import com.ianpedraza.dogedex.framework.api.auth.response.AuthResponse
import com.ianpedraza.dogedex.framework.api.dogs.response.DogsListResponse
import com.ianpedraza.dogedex.framework.api.dto.AddDogToUserDTO
import com.ianpedraza.dogedex.framework.api.dto.LoginDTO
import com.ianpedraza.dogedex.framework.api.dto.SignUpDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface DogsApi {
    @GET("dogs")
    suspend fun getAllDogs(): DogsListResponse

    @POST("sign_up")
    suspend fun signup(
        @Body
        signUpDTO: SignUpDTO
    ): AuthResponse

    @POST("sign_in")
    suspend fun login(
        @Body
        loginDTO: LoginDTO
    ): AuthResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST("add_dog_to_user")
    suspend fun addDogToUser(
        @Body
        addDogToUserDTO: AddDogToUserDTO
    ): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET("get_user_dogs")
    suspend fun getUserDogs(): DogsListResponse
}

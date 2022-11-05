package com.ianpedraza.dogedex.framework.api.dogs.response

import com.squareup.moshi.Json

data class DogApiResponse(
    val message: String,
    @field:Json(name = "is_success")
    val isSuccess: Boolean,
    val data: DogResponse
)

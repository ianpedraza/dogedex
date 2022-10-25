package com.ianpedraza.dogedex.framework.api.auth.response

import com.squareup.moshi.Json

data class AuthResponse(
    val message: String,

    @field:Json(name = "is_success")
    val isSuccess: Boolean,

    val data: UserDataResponse
)

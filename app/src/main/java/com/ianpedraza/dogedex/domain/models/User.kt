package com.ianpedraza.dogedex.domain.models

data class User(
    val id: Long,
    val email: String,
    val authenticationToken: String
)

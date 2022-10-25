package com.ianpedraza.dogedex.data.datasource

import com.ianpedraza.dogedex.domain.models.User

interface AuthDataSource {
    suspend fun signup(email: String, password: String): User
    suspend fun login(email: String, password: String): User
}

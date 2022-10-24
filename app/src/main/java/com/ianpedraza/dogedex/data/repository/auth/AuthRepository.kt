package com.ianpedraza.dogedex.data.repository.auth

import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signup(email: String, password: String): Flow<DataState<User>>
}

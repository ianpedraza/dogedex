package com.ianpedraza.dogedex.data.repository.auth

import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signup(email: String, password: String): Flow<DataState<User>>
    fun login(email: String, password: String): Flow<DataState<User>>
}

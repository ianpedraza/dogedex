package com.ianpedraza.dogedex.framework.api.auth

import com.ianpedraza.dogedex.data.datasource.AuthDataSource
import com.ianpedraza.dogedex.domain.mappers.UserDTOMapper
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.framework.api.DogsApi
import com.ianpedraza.dogedex.framework.api.dto.LoginDTO
import com.ianpedraza.dogedex.framework.api.dto.SignUpDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRemoteDataSource(
    private val service: DogsApi,
    private val mapper: UserDTOMapper
) : AuthDataSource {
    override suspend fun signup(email: String, password: String): User {
        return withContext(Dispatchers.IO) {
            val signUpDTO = SignUpDTO(email, password, password)
            val response = service.signup(signUpDTO)
            if (!response.isSuccess) throw Exception(response.message)
            mapper.fromResponseToDomainModel(response.data.user)
        }
    }

    override suspend fun login(email: String, password: String): User {
        return withContext(Dispatchers.IO) {
            val loginDTO = LoginDTO(email, password)
            val response = service.login(loginDTO)
            if (!response.isSuccess) throw Exception(response.message)
            mapper.fromResponseToDomainModel(response.data.user)
        }
    }
}

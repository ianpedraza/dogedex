package com.ianpedraza.dogedex.framework.api.auth

import com.ianpedraza.dogedex.data.datasource.AuthDataSource
import com.ianpedraza.dogedex.di.DispatchersModule.IoDispatcher
import com.ianpedraza.dogedex.domain.mappers.UserDTOMapper
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.framework.api.DogsApi
import com.ianpedraza.dogedex.framework.api.dto.LoginDTO
import com.ianpedraza.dogedex.framework.api.dto.SignUpDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRemoteDataSource
@Inject
constructor(
    private val service: DogsApi,
    private val mapper: UserDTOMapper,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) : AuthDataSource {
    override suspend fun signup(email: String, password: String): User {
        return withContext(dispatcher) {
            val signUpDTO = SignUpDTO(email, password, password)
            val response = service.signup(signUpDTO)
            if (!response.isSuccess) throw Exception(response.message)
            mapper.fromResponseToDomainModel(response.data.user)
        }
    }

    override suspend fun login(email: String, password: String): User {
        return withContext(dispatcher) {
            val loginDTO = LoginDTO(email, password)
            val response = service.login(loginDTO)
            if (!response.isSuccess) throw Exception(response.message)
            mapper.fromResponseToDomainModel(response.data.user)
        }
    }
}

package com.ianpedraza.dogedex.fakes

import com.ianpedraza.dogedex.core.data.repository.auth.AuthRepository
import com.ianpedraza.dogedex.core.domain.models.User
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeAuthRepository @Inject constructor() :
    com.ianpedraza.dogedex.core.data.repository.auth.AuthRepository {

    private val fakeUser = com.ianpedraza.dogedex.core.domain.models.User(
        id = 0L,
        email = "",
        authenticationToken = ""
    )
    private var response: DataState<com.ianpedraza.dogedex.core.domain.models.User> = DataState.Success(fakeUser)

    private fun setResponse(response: DataState<com.ianpedraza.dogedex.core.domain.models.User>) {
        this.response = response
    }

    override fun signup(email: String, password: String): Flow<DataState<com.ianpedraza.dogedex.core.domain.models.User>> = flow {
        emit(response)
    }

    override fun login(email: String, password: String): Flow<DataState<com.ianpedraza.dogedex.core.domain.models.User>> = flow {
        emit(response)
    }
}

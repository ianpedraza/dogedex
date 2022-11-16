package com.ianpedraza.dogedex.usecases

import com.ianpedraza.dogedex.data.repository.auth.AuthRepository
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase
@Inject
constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<DataState<User>> =
        repository.login(email, password)
}

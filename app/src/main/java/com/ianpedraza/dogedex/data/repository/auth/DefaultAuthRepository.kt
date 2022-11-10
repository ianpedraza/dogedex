package com.ianpedraza.dogedex.data.repository.auth

import com.ianpedraza.dogedex.data.datasource.AuthDataSource
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.NetworkUtils.Companion.makeNetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultAuthRepository
@Inject
constructor(
    private val dataSource: AuthDataSource
) : AuthRepository {
    override fun signup(email: String, password: String): Flow<DataState<User>> =
        makeNetworkCall { dataSource.signup(email, password) }

    override fun login(email: String, password: String): Flow<DataState<User>> =
        makeNetworkCall { dataSource.login(email, password) }
}

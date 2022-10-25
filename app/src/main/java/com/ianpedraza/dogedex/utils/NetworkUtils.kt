package com.ianpedraza.dogedex.utils

import com.ianpedraza.dogedex.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.UnknownHostException

class NetworkUtils {
    companion object {
        private const val UNAUTHORIZED_ERROR_CODE = 401

        private const val ERROR_SIGN_UP_MSG = "sign_up_error"
        private const val ERROR_SIGN_IN_MSG = "sign_in_error"
        private const val ERROR_USER_ALREADY_EXIST_MSG = "user_already_exist"
        private const val ERROR_USER_NOT_FOUND = "user_not_found"

        suspend fun <T> makeNetworkCall(
            call: suspend () -> T
        ): Flow<DataState<T>> = flow {
            emit(DataState.Loading)
            try {
                val data = call.invoke()
                emit(DataState.Success(data))
            } catch (e: UnknownHostException) {
                val errorMessage = R.string.error_no_internet_conecction
                emit(DataState.Error(errorMessage))
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    UNAUTHORIZED_ERROR_CODE -> R.string.error_wrong_user_or_password
                    else -> R.string.error_unkown
                }
                emit(DataState.Error(errorMessage))
            } catch (e: Exception) {
                val errorMessage = when (e.message) {
                    ERROR_SIGN_UP_MSG -> R.string.error_signing_up
                    ERROR_SIGN_IN_MSG -> R.string.error_login_in
                    ERROR_USER_ALREADY_EXIST_MSG -> R.string.user_already_exist
                    ERROR_USER_NOT_FOUND -> R.string.user_not_found
                    else -> R.string.error_unkown
                }
                emit(DataState.Error(errorMessage))
            }
        }
    }
}

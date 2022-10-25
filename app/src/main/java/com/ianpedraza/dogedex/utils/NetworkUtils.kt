package com.ianpedraza.dogedex.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.UnknownHostException

class NetworkUtils {
    companion object {
        private const val UNAUTHORIZED_ERROR_CODE = 401

        suspend fun <T> makeNetworkCall(
            call: suspend () -> T
        ): Flow<DataState<T>> = flow {
            emit(DataState.Loading)
            try {
                val data = call.invoke()
                emit(DataState.Success(data))
            } catch (e: UnknownHostException) {
                emit(DataState.Error(Exception("There's no internet connection")))
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    UNAUTHORIZED_ERROR_CODE -> "Wrong user or password"
                    else -> "There's an unknown error"
                }

                emit(DataState.Error(Exception(errorMessage)))
            } catch (e: Exception) {
                val errorMessage = when (e.message) {
                    "sign_up_error" -> "Error signing up"
                    "sign_in_error" -> "Error login in"
                    "user_already_exist" -> "User already exist"
                    else -> "There's an unknown error"
                }

                emit(DataState.Error(Exception(errorMessage)))
            }
        }
    }
}

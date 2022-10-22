package com.ianpedraza.dogedex.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

class NetworkUtils {
    companion object {
        suspend fun <T> makeNetworkCall(
            call: suspend () -> T
        ): Flow<DataState<T>> = flow {
            emit(DataState.Loading)
            try {
                val data = call.invoke()
                emit(DataState.Success(data))
            } catch (e: UnknownHostException) {
                emit(DataState.Error(Exception("There's no internet connection")))
            } catch (e: Exception) {
                emit(DataState.Error(Exception("There's an unknown error")))
            }
        }
    }
}

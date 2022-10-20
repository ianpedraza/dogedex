package com.ianpedraza.dogedex.data.repository

import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultDogsRepository(
    private val dataSource: DogsDataSource
) : DogsRepository {
    override suspend fun getAll(): Flow<DataState<List<Dog>>> = flow {
        emit(DataState.Loading)
        try {
            val data = dataSource.getAll()
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}

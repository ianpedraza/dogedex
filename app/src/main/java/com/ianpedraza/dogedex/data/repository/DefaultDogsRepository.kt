package com.ianpedraza.dogedex.data.repository

import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.NetworkUtils.Companion.makeNetworkCall
import kotlinx.coroutines.flow.Flow

class DefaultDogsRepository(
    private val dataSource: DogsDataSource
) : DogsRepository {
    override suspend fun getAll(): Flow<DataState<List<Dog>>> =
        makeNetworkCall { dataSource.getAll() }
}

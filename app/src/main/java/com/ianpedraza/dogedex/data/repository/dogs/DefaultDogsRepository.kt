package com.ianpedraza.dogedex.data.repository.dogs

import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.NetworkUtils.Companion.makeNetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultDogsRepository
@Inject
constructor(
    private val dataSource: DogsDataSource
) : DogsRepository {
    override fun getAll(): Flow<DataState<List<Dog>>> =
        makeNetworkCall { dataSource.getAll() }

    override fun getDogByMlId(mlDogId: String): Flow<DataState<Dog>> =
        makeNetworkCall { dataSource.getDogByMlId(mlDogId) }
}

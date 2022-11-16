package com.ianpedraza.dogedex.fakes

import com.ianpedraza.dogedex.core.data.repository.dogs.DogsRepository
import com.ianpedraza.dogedex.core.domain.models.Dog
import com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeDogsRepository @Inject constructor() :
    com.ianpedraza.dogedex.core.data.repository.dogs.DogsRepository {
    private var responseGetAll: DataState<List<com.ianpedraza.dogedex.core.domain.models.Dog>> = DataState.Success(com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData.getAllDogs())

    private fun setResponseGetAll(response: DataState<List<com.ianpedraza.dogedex.core.domain.models.Dog>>) {
        this.responseGetAll = response
    }

    private var responseGetDogByMlId: DataState<com.ianpedraza.dogedex.core.domain.models.Dog> =
        DataState.Success(com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData.getAllDogs().first())

    private fun setResponseGetDogByMlId(response: DataState<com.ianpedraza.dogedex.core.domain.models.Dog>) {
        this.responseGetDogByMlId = response
    }

    override fun getAll(): Flow<DataState<List<com.ianpedraza.dogedex.core.domain.models.Dog>>> = flow {
        emit(responseGetAll)
    }

    override fun getDogByMlId(mlDogId: String): Flow<DataState<com.ianpedraza.dogedex.core.domain.models.Dog>> = flow {
        emit(responseGetDogByMlId)
    }
}

package com.ianpedraza.dogedex

import com.ianpedraza.dogedex.core.data.repository.user.UserRepository
import com.ianpedraza.dogedex.core.domain.models.Dog
import com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository : com.ianpedraza.dogedex.core.data.repository.user.UserRepository {
    override fun addDogToUser(dogId: Long): Flow<DataState<Boolean>> = flow {
        emit(DataState.Success(true))
    }

    override fun getUserDogs(): Flow<DataState<List<com.ianpedraza.dogedex.core.domain.models.Dog>>> = flow {
        emit(DataState.Success(com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData.getAllDogs().takeLast(3)))
    }

    override fun getDogsCollection(): Flow<DataState<List<com.ianpedraza.dogedex.core.domain.models.Dog>>> = flow {
        emit(DataState.Success(com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData.getAllDogs()))
    }
}

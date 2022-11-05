package com.ianpedraza.dogedex.data.repository.dogs

import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    fun getAll(): Flow<DataState<List<Dog>>>
    fun getDogByMlId(mlDogId: String): Flow<DataState<Dog>>
}

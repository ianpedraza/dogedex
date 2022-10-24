package com.ianpedraza.dogedex.data.repository.dogs

import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    suspend fun getAll(): Flow<DataState<List<Dog>>>
}

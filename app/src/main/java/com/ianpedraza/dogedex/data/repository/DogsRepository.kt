package com.ianpedraza.dogedex.data.repository

import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    suspend fun getAll(): Flow<DataState<List<Dog>>>
}

package com.ianpedraza.dogedex.data.repository

import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.domain.models.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultDogsRepository(
    private val dataSource: DogsDataSource
) : DogsRepository {
    override suspend fun getAll(): List<Dog> {
        return withContext(Dispatchers.IO) {
            dataSource.getAll()
        }
    }
}

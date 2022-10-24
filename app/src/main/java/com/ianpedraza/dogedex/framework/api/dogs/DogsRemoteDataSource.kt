package com.ianpedraza.dogedex.framework.api.dogs

import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.domain.mappers.DogDTOMapper
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.framework.api.DogsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogsRemoteDataSource(
    private val service: DogsApi,
    private val mapper: DogDTOMapper
) : DogsDataSource {
    override suspend fun getAll(): List<Dog> {
        return withContext(Dispatchers.IO) {
            val response = service.getAllDogs()
            mapper.fromResponseListToDomainModelList(response.data.dogs)
        }
    }
}

package com.ianpedraza.dogedex.framework.api.dogs

import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.domain.mappers.DogDTOMapper
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.framework.api.DogsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogsRemoteDataSource
@Inject
constructor(
    private val service: DogsApi,
    private val mapper: DogDTOMapper,
    private val dispatcher: CoroutineDispatcher
) : DogsDataSource {
    override suspend fun getAll(): List<Dog> {
        return withContext(dispatcher) {
            val response = service.getAllDogs()
            mapper.fromResponseListToDomainModelList(response.data.dogs)
        }
    }

    override suspend fun getDogByMlId(mlDogId: String): Dog {
        return withContext(dispatcher) {
            val response = service.getDogByMlId(mlDogId)
            mapper.fromResponseToDomainModel(response.data.dog)
        }
    }
}

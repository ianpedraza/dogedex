package com.ianpedraza.dogedex.framework.api.user

import com.ianpedraza.dogedex.data.datasource.UserDataSource
import com.ianpedraza.dogedex.domain.mappers.DogDTOMapper
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.framework.api.DogsApi
import com.ianpedraza.dogedex.framework.api.dto.AddDogToUserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRemoteDataSource(
    private val service: DogsApi,
    private val mapper: DogDTOMapper
) : UserDataSource {
    override suspend fun addDogToUser(dogId: Long): Boolean {
        return withContext(Dispatchers.IO) {
            val addDogToUserDTO = AddDogToUserDTO(dogId)
            val response = service.addDogToUser(addDogToUserDTO)
            response.isSuccess
        }
    }

    override suspend fun getUserDogs(): List<Dog> {
        return withContext(Dispatchers.IO) {
            val response = service.getUserDogs()
            mapper.fromResponseListToDomainModelList(response.data.dogs)
        }
    }
}

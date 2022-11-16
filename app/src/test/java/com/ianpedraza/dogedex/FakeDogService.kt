package com.ianpedraza.dogedex

import com.ianpedraza.dogedex.core.framework.api.DefaultResponse
import com.ianpedraza.dogedex.core.framework.api.DogsApi
import com.ianpedraza.dogedex.core.framework.api.auth.response.AuthResponse
import com.ianpedraza.dogedex.core.framework.api.dogs.response.DogApiResponse
import com.ianpedraza.dogedex.core.framework.api.dogs.response.DogsListDataResponse
import com.ianpedraza.dogedex.core.framework.api.dogs.response.DogsListResponse
import com.ianpedraza.dogedex.core.framework.api.dto.AddDogToUserDTO
import com.ianpedraza.dogedex.core.framework.api.dto.DogDTO
import com.ianpedraza.dogedex.core.framework.api.dto.LoginDTO
import com.ianpedraza.dogedex.core.framework.api.dto.SignUpDTO
import com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData

class FakeDogService : com.ianpedraza.dogedex.core.framework.api.DogsApi {
    override suspend fun getAllDogs(): com.ianpedraza.dogedex.core.framework.api.dogs.response.DogsListResponse {
        val dogsDtoList = com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData.getAllDogs().map { dog ->
            dog.run {
                com.ianpedraza.dogedex.core.framework.api.dto.DogDTO(
                    id = id,
                    index = index,
                    createdAt = "",
                    dogType = type,
                    heightFemale = heightFemale,
                    heightMale = heightMale,
                    imageUrl = imageUrl,
                    lifeExpectancy = lifeExpectancy,
                    mlId = "",
                    nameEn = "",
                    nameEs = name,
                    temperamentEs = temperament,
                    temperamentEn = "",
                    updatedAt = "",
                    weightFemale = weightFemale,
                    weightMale = weightMale
                )
            }
        }
        val listResponse =
            com.ianpedraza.dogedex.core.framework.api.dogs.response.DogsListDataResponse(dogsDtoList)
        return com.ianpedraza.dogedex.core.framework.api.dogs.response.DogsListResponse(
            message = "",
            isSuccess = true,
            data = listResponse
        )
    }

    override suspend fun signup(signUpDTO: com.ianpedraza.dogedex.core.framework.api.dto.SignUpDTO): com.ianpedraza.dogedex.core.framework.api.auth.response.AuthResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(loginDTO: com.ianpedraza.dogedex.core.framework.api.dto.LoginDTO): com.ianpedraza.dogedex.core.framework.api.auth.response.AuthResponse {
        TODO("Not yet implemented")
    }

    override suspend fun addDogToUser(addDogToUserDTO: com.ianpedraza.dogedex.core.framework.api.dto.AddDogToUserDTO): com.ianpedraza.dogedex.core.framework.api.DefaultResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getUserDogs(): com.ianpedraza.dogedex.core.framework.api.dogs.response.DogsListResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getDogByMlId(mlDogId: String): com.ianpedraza.dogedex.core.framework.api.dogs.response.DogApiResponse {
        TODO("Not yet implemented")
    }
}

package com.ianpedraza.dogedex.data.datasource

import com.ianpedraza.dogedex.domain.models.Dog

interface UserDataSource {
    suspend fun addDogToUser(dogId: Long): Boolean
    suspend fun getUserDogs(): List<Dog>
}

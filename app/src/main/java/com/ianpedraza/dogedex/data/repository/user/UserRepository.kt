package com.ianpedraza.dogedex.data.repository.user

import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun addDogToUser(dogId: Long): Flow<DataState<Boolean>>
    fun getUserDogs(): Flow<DataState<List<Dog>>>
    fun getDogsCollection(): Flow<DataState<List<Dog>>>
}

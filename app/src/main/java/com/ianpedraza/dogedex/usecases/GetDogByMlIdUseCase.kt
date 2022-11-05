package com.ianpedraza.dogedex.usecases

import com.ianpedraza.dogedex.data.repository.dogs.DogsRepository
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow

class GetDogByMlIdUseCase(private val repository: DogsRepository) {
    operator fun invoke(mlDogId: String): Flow<DataState<Dog>> = repository.getDogByMlId(mlDogId)
}

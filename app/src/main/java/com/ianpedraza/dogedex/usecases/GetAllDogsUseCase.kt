package com.ianpedraza.dogedex.usecases

import com.ianpedraza.dogedex.data.repository.dogs.DogsRepository
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.Flow

class GetAllDogsUseCase(private val repository: DogsRepository) {
    suspend operator fun invoke(): Flow<DataState<List<Dog>>> = repository.getAll()
}

package com.ianpedraza.dogedex.usecases

import com.ianpedraza.dogedex.data.repository.DogsRepository
import com.ianpedraza.dogedex.domain.models.Dog

class GetAllDogsUseCase(private val repository: DogsRepository) {
    suspend operator fun invoke(): List<Dog> = repository.getAll()
}

package com.ianpedraza.dogedex.data.repository

import com.ianpedraza.dogedex.domain.models.Dog

interface DogsRepository {
    suspend fun getAll(): List<Dog>
}

package com.ianpedraza.dogedex.data.datasource

import com.ianpedraza.dogedex.domain.models.Dog

interface DogsDataSource {
    suspend fun getAll(): List<Dog>
    suspend fun getDogByMlId(mlDogId: String): Dog
}

package com.ianpedraza.dogedex.framework.dummy.dogs

import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.domain.models.Dog

class DogsLocalDataSource : DogsDataSource {
    override suspend fun getAll(): List<Dog> {
        return DummyData.getAllDogs()
    }

    override suspend fun getDogByMlId(mlDogId: String): Dog {
        TODO("Not yet implemented")
    }
}

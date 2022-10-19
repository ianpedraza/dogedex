package com.ianpedraza.dogedex.framework

import com.ianpedraza.dogedex.data.DummyData
import com.ianpedraza.dogedex.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.domain.models.Dog

class DogsLocalDataSource : DogsDataSource {
    override suspend fun getAll(): List<Dog> {
        return DummyData.dogsList
    }
}

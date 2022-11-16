package com.ianpedraza.dogedex.framework.api.dogs

import com.ianpedraza.dogedex.FakeDogService
import com.ianpedraza.dogedex.core.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.core.domain.mappers.DogDTOMapper
import com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DogsRemoteDataSourceTest {
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var dataSource: com.ianpedraza.dogedex.core.data.datasource.DogsDataSource

    @Before
    fun setup() {
        val mapper = com.ianpedraza.dogedex.core.domain.mappers.DogDTOMapper()
        val dogsApi = FakeDogService()
        dataSource = com.ianpedraza.dogedex.core.framework.api.dogs.DogsRemoteDataSource(
            dogsApi,
            mapper,
            dispatcher
        )
    }

    @Test
    fun getDogCollection_returnsSuccessTest(): Unit = runBlocking {
        val expected = com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData.getAllDogs()
        val result = dataSource.getAll()

        assertThat(result, IsEqual(expected))
    }
}

package com.ianpedraza.dogedex.data.repository.dogs

import com.ianpedraza.dogedex.core.data.datasource.DogsDataSource
import com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.core.Is
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DefaultDogsRepositoryTest {

    private val dogsDataSource: com.ianpedraza.dogedex.core.data.datasource.DogsDataSource = mock()
    private lateinit var repository: com.ianpedraza.dogedex.core.data.repository.dogs.DefaultDogsRepository

    @Before
    fun setup() {
        repository =
            com.ianpedraza.dogedex.core.data.repository.dogs.DefaultDogsRepository(dogsDataSource)
    }

    @Test
    fun getAllDogs_returnsSuccessTest(): Unit = runBlocking {
        val data = com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData.getAllDogs()

        whenever(dogsDataSource.getAll()).thenReturn(data)

        val result = repository.getAll().last()

        assertThat(result, Is(notNullValue()))
        assertThat(result, instanceOf(DataState.Success::class.java))

        val resultData = (result as? DataState.Success)?.data
        assertThat(resultData, IsEqual(data))
    }
}

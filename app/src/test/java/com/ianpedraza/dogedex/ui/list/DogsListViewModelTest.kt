package com.ianpedraza.dogedex.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ianpedraza.dogedex.FakeUserRepository
import com.ianpedraza.dogedex.MainCoroutineRule
import com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData
import com.ianpedraza.dogedex.usecases.GetDogsCollectionUseCase
import com.ianpedraza.dogedex.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.hamcrest.core.Is
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DogsListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DogsListViewModel

    @Before
    fun setup() {
        val repository = FakeUserRepository()
        val getDogsCollectionUseCase = GetDogsCollectionUseCase(repository)
        viewModel = DogsListViewModel(getDogsCollectionUseCase)
    }

    @Test
    fun getDogList_returnsSuccessTest() {
        val expectedData = com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData.getAllDogs()

        val result = viewModel.dogsList.value

        assertThat(result, Is(notNullValue()))
        assertThat(result, instanceOf(DataState.Success::class.java))

        val resultData = (result as? DataState.Success)?.data
        assertThat(resultData, IsEqual(expectedData))
    }

    @Test
    fun resetStatus_setStatusNullTest() {
        viewModel.reset()

        val status = viewModel.dogsList.value
        assertThat(status, Is(nullValue()))
    }
}

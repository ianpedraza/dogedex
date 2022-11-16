package com.ianpedraza.dogedex.ui.list

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.ianpedraza.dogedex.fakes.FakeUserRepository
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData
import com.ianpedraza.dogedex.usecases.GetDogsCollectionUseCase
import com.ianpedraza.dogedex.utils.DataState
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
class DogListScreenTest {

    @get:Rule
    var composeRule = createComposeRule()

    private lateinit var viewModel: DogsListViewModel
    private lateinit var repository: FakeUserRepository
    private lateinit var getDogsCollectionUseCase: GetDogsCollectionUseCase

    @Before
    fun setup() {
        repository = FakeUserRepository()
        getDogsCollectionUseCase = GetDogsCollectionUseCase(repository)
    }

    @Test
    fun loadingWheel_shows_getAllDogsTest() {
        repository.setResponse(DataState.Loading)
        viewModel = DogsListViewModel(getDogsCollectionUseCase)

        composeRule.setContent {
            DogListScreen(onAction = {}, viewModel = viewModel)
        }

        composeRule.onNodeWithTag(testTag = "loading-wheel")
            .assertIsDisplayed()
    }

    @Test
    fun errorDialog_shows_errorGettingDogsTest() {
        repository.setResponse(DataState.Error(R.string.error_getting_dogs))
        viewModel = DogsListViewModel(getDogsCollectionUseCase)

        composeRule.setContent {
            DogListScreen(onAction = {}, viewModel = viewModel)
        }

        // Problem with languages, we need espresso instead of compose rule
        /* composeRule.onNodeWithText(text = "Error while getting the dogs")
            .assertIsDisplayed() */

        composeRule.onNodeWithTag(testTag = "error-dialog")
            .assertIsDisplayed()
    }

    @Test
    fun showList_gettingAllDogs() {
        val data = com.ianpedraza.dogedex.core.framework.dummy.dogs.DummyData.getAllDogs().take(2)

        repository.setResponse(DataState.Success(data))
        viewModel = DogsListViewModel(getDogsCollectionUseCase)

        val dogName1 = "dog-Chihuahua"
        val dogName2 = "dog-Labrador"

        composeRule.setContent {
            DogListScreen(onAction = {}, viewModel = viewModel)
        }

        composeRule
            .onNodeWithTag(testTag = dogName1, useUnmergedTree = true)
            .assertIsDisplayed()

        composeRule
            .onNodeWithTag(testTag = dogName2, useUnmergedTree = true)
            .assertIsDisplayed()
    }
}

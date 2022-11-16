package com.ianpedraza.dogedex.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.core.di.UseCasesModule
import com.ianpedraza.dogedex.testuitls.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(com.ianpedraza.dogedex.core.di.UseCasesModule::class)
@HiltAndroidTest
class HomeFragmentTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    // @get:Rule
    // val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    // Hilt is not compatible with fragmentScenario, it needs a Activity to be attached

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Before
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun starts_showAllFab() {
        // val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.Theme_Dogedex)
        // scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.fabHomeTakePhoto))
            .check(matches(isDisplayed()))

        onView(withId(R.id.fabHomeDogsList))
            .check(matches(isDisplayed()))

        onView(withId(R.id.fab_home_settings))
            .check(matches(isDisplayed()))
    }

    @Test
    fun listFab_clicked_opensListScreen() {
        onView(withId(R.id.fabHomeDogsList)).perform(click())

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val string = context.getString(R.string.dog_collection)

        // onView(withText(string)).check(matches(isDisplayed()))
        composeTestRule.onNodeWithText(string).assertIsDisplayed()
    }

    @Test
    fun recognizedDog_goToDetails() {
        onView(withId(R.id.fabHomeTakePhoto)).perform(click())

        composeTestRule
            .onNodeWithTag(testTag = "close-details-screen")
            .assertIsDisplayed()
    }
}

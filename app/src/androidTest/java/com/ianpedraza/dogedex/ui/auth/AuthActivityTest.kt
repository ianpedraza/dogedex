package com.ianpedraza.dogedex.ui.auth

import android.Manifest
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.rule.GrantPermissionRule
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.core.di.UseCasesModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

@UninstallModules(com.ianpedraza.dogedex.core.di.UseCasesModule::class)
@HiltAndroidTest
class AuthActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val runtimePermissionsRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @get:Rule(order = 2)
    var androidComposeTestRule = createAndroidComposeRule<AuthActivity>()

    @Test
    fun loginUser_opensHomeFragment() {
        val context = androidComposeTestRule.activity

        androidComposeTestRule
            .onNodeWithText(context.getString(R.string.login))
            .assertIsDisplayed()

        androidComposeTestRule
            .onNodeWithTag(testTag = "email-field")
            .performTextInput("example@mail.com")

        androidComposeTestRule
            .onNodeWithTag(testTag = "password-field")
            .performTextInput("Password123")

        androidComposeTestRule
            .onNodeWithText(context.getString(R.string.login))
            .performClick()

        // onView(withId(R.id.fabHomeTakePhoto))
        //  .check(matches(isDisplayed()))

        // assertThat(ApiServiceInterceptor.getAuthenticationToken(), `is`(notNullValue()))
    }
}

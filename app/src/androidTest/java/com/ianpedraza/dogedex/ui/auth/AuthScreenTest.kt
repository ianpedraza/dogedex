package com.ianpedraza.dogedex.ui.auth

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.ianpedraza.dogedex.fakes.FakeAuthRepository
import com.ianpedraza.dogedex.fakes.FakeAuthValidator
import com.ianpedraza.dogedex.ui.auth.login.LoginViewModel
import com.ianpedraza.dogedex.ui.auth.signup.SignupViewModel
import com.ianpedraza.dogedex.usecases.LoginUseCase
import com.ianpedraza.dogedex.usecases.SignUpUseCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthScreenTest {

    @get:Rule
    var composeTestRule = createComposeRule()

    lateinit var loginViewModel: LoginViewModel
    lateinit var signupViewModel: SignupViewModel

    @Before
    fun setup() {
        val authValidator = FakeAuthValidator()
        val repository = FakeAuthRepository()

        val loginUseCase = LoginUseCase(repository)
        loginViewModel = LoginViewModel(loginUseCase, authValidator)

        val signUpUseCase = SignUpUseCase(repository)
        signupViewModel = SignupViewModel(signUpUseCase, authValidator)

        composeTestRule.setContent {
            AuthScreen(
                loginViewModel = loginViewModel,
                onUserLoggedIn = {},
                signupViewModel = signupViewModel,
                onUserRegistered = {}
            )
        }
    }

    @Test
    fun clickRegister_openSignUpScreenTest() {
        composeTestRule
            .onNodeWithTag(testTag = "login-button")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(testTag = "register-button")
            .performClick()

        composeTestRule
            .onNodeWithTag(testTag = "signup-button")
            .assertIsDisplayed()
    }

    @Test
    fun clickLogin_noEmail_showsEmailErrorTest() {
        composeTestRule
            .onNodeWithTag(testTag = "login-button")
            .performClick()

        composeTestRule
            .onNodeWithTag(testTag = "email-error")
            .assertIsDisplayed()
    }

    @Test
    fun clickLogin_noPassword_showsPasswordErrorTest() {
        composeTestRule
            .onNodeWithTag(testTag = "email-field")
            .performTextInput("example@mail.com")

        composeTestRule
            .onNodeWithTag(testTag = "login-button")
            .performClick()

        composeTestRule
            .onNodeWithTag(testTag = "password-error")
            .assertIsDisplayed()
    }
}

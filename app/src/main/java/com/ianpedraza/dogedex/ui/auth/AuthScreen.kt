package com.ianpedraza.dogedex.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.ui.auth.AuthNavDestinations.LoginScreenDestination
import com.ianpedraza.dogedex.ui.auth.AuthNavDestinations.SignUpScreenDestination
import com.ianpedraza.dogedex.ui.auth.login.LoginNavigationEvent
import com.ianpedraza.dogedex.ui.auth.login.LoginScreen
import com.ianpedraza.dogedex.ui.auth.login.LoginUiEvent
import com.ianpedraza.dogedex.ui.auth.login.LoginUiState
import com.ianpedraza.dogedex.ui.auth.login.LoginViewModel
import com.ianpedraza.dogedex.ui.auth.signup.SignUpNavigationEvent
import com.ianpedraza.dogedex.ui.auth.signup.SignUpScreen
import com.ianpedraza.dogedex.ui.auth.signup.SignUpUiEvent
import com.ianpedraza.dogedex.ui.auth.signup.SignUpUiState
import com.ianpedraza.dogedex.ui.auth.signup.SignupViewModel
import com.ianpedraza.dogedex.utils.DataState

@Composable
fun AuthScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onUserLoggedIn: (User) -> Unit,
    signupViewModel: SignupViewModel = hiltViewModel(),
    onUserRegistered: (User) -> Unit
) {
    val navController = rememberNavController()

    val loginUiState by loginViewModel.uiState.collectAsState()

    val signUpUiState by signupViewModel.uiState.collectAsState()

    AuthNavHost(
        navController = navController,
        loginUiState = loginUiState,
        onUserLoggedIn = onUserLoggedIn,
        onLoginUiEvent = { event -> handleLoginOnUiEvent(event, loginViewModel) },
        signUpUiState = signUpUiState,
        onSignUpUiEvent = { event -> handleSignUpUiEvent(event, signupViewModel) },
        onUserRegistered = onUserRegistered
    )
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    loginUiState: LoginUiState,
    onUserLoggedIn: (User) -> Unit,
    onLoginUiEvent: (LoginUiEvent) -> Unit,
    signUpUiState: SignUpUiState,
    onSignUpUiEvent: (SignUpUiEvent) -> Unit,
    onUserRegistered: (User) -> Unit
) {
    if (loginUiState.loginStatus is DataState.Success) {
        onUserLoggedIn(loginUiState.loginStatus.data)
    }

    if (signUpUiState.signUpStatus is DataState.Success) {
        onUserRegistered(signUpUiState.signUpStatus.data)
    }

    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination
    ) {
        composable(route = LoginScreenDestination) {
            LoginScreen(
                uiState = loginUiState,
                onUiEvent = onLoginUiEvent,
                onNavigationEvent = { event -> handleLoginNavigationEvent(event, navController) }
            )
        }

        composable(route = SignUpScreenDestination) {
            SignUpScreen(
                uiState = signUpUiState,
                onUiEvent = onSignUpUiEvent,
                onNavigationEvent = { event -> handleSignUpNavigationEvent(event, navController) }
            )
        }
    }
}

private fun handleLoginOnUiEvent(event: LoginUiEvent, viewModel: LoginViewModel) {
    when (event) {
        LoginUiEvent.OnErrorDialogDismiss -> viewModel.onLoginHandled()
        is LoginUiEvent.OnLoginButtonClicked -> viewModel.login(
            event.email,
            event.password
        )
    }
}

private fun handleSignUpUiEvent(event: SignUpUiEvent, viewModel: SignupViewModel) {
    when (event) {
        SignUpUiEvent.OnErrorDialogDismiss -> viewModel.onSignUpHandled()
        is SignUpUiEvent.OnSignUpButtonClicked -> viewModel.signUp(
            event.email,
            event.password,
            event.confirmPassword
        )
    }
}

private fun handleSignUpNavigationEvent(
    event: SignUpNavigationEvent,
    navController: NavHostController
) {
    when (event) {
        SignUpNavigationEvent.OnBackPressed -> navController.navigateUp()
    }
}

private fun handleLoginNavigationEvent(
    event: LoginNavigationEvent,
    navController: NavHostController
) {
    when (event) {
        LoginNavigationEvent.RegisterButtonClicked -> {
            navController.navigate(route = SignUpScreenDestination)
        }
    }
}

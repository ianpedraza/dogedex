package com.ianpedraza.dogedex.ui.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ianpedraza.dogedex.ui.auth.AuthNavDestinations.LoginScreenDestination
import com.ianpedraza.dogedex.ui.auth.AuthNavDestinations.SignUpScreenDestination
import com.ianpedraza.dogedex.ui.auth.login.LoginNavigationEvent
import com.ianpedraza.dogedex.ui.auth.login.LoginScreen
import com.ianpedraza.dogedex.ui.auth.login.LoginUiEvent
import com.ianpedraza.dogedex.ui.auth.login.LoginUiState
import com.ianpedraza.dogedex.ui.auth.signup.SignUpNavigationEvent
import com.ianpedraza.dogedex.ui.auth.signup.SignUpScreen
import com.ianpedraza.dogedex.ui.auth.signup.SignUpUiEvent
import com.ianpedraza.dogedex.ui.auth.signup.SignUpUiState

@Composable
fun AuthScreen(
    loginUiState: LoginUiState,
    onLoginUiEvent: (LoginUiEvent) -> Unit,
    signUpUiState: SignUpUiState,
    onSignUpUiEvent: (SignUpUiEvent) -> Unit
) {
    val navController = rememberNavController()

    AuthNavHost(
        navController = navController,
        loginUiState = loginUiState,
        onUiEvent = onLoginUiEvent,
        signUpUiState = signUpUiState,
        onSignUpUiEvent = onSignUpUiEvent
    )
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    loginUiState: LoginUiState,
    onUiEvent: (LoginUiEvent) -> Unit,
    signUpUiState: SignUpUiState,
    onSignUpUiEvent: (SignUpUiEvent) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination
    ) {
        composable(route = LoginScreenDestination) {
            LoginScreen(
                uiState = loginUiState,
                onUiEvent = onUiEvent,
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

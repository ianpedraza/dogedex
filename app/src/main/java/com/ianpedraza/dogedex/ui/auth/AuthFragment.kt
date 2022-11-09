package com.ianpedraza.dogedex.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.framework.api.ApiServiceInterceptor
import com.ianpedraza.dogedex.ui.auth.login.LoginUiEvent
import com.ianpedraza.dogedex.ui.auth.login.LoginViewModel
import com.ianpedraza.dogedex.ui.auth.signup.SignUpUiEvent
import com.ianpedraza.dogedex.ui.auth.signup.SignupViewModel
import com.ianpedraza.dogedex.ui.theme.DogedexTheme
import com.ianpedraza.dogedex.utils.ComposeFragmentExtensions.Companion.contentView
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private val navController: NavController get() = findNavController()

    private val loginViewModel: LoginViewModel by viewModels()
    private val signUpViewModel: SignupViewModel by viewModels()

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ComposeView = contentView {
        val loginUiState by loginViewModel.uiState
        val signUpUiState by signUpViewModel.uiState

        if (loginUiState.loginStatus is DataState.Success) {
            showSuccess((loginUiState.loginStatus as DataState.Success<User>).data)
        }

        if (signUpUiState.signUpStatus is DataState.Success) {
            showSuccess((signUpUiState.signUpStatus as DataState.Success<User>).data)
        }

        DogedexTheme {
            AuthScreen(
                loginUiState = loginUiState,
                onLoginUiEvent = ::onLoginUiEvent,
                signUpUiState = signUpUiState,
                onSignUpUiEvent = ::onSignUpUiEvent
            )
        }
    }

    private fun onSignUpUiEvent(event: SignUpUiEvent) {
        when (event) {
            SignUpUiEvent.OnErrorDialogDismiss -> signUpViewModel.onSignUpHandled()
            is SignUpUiEvent.OnSignUpButtonClicked -> signUpViewModel.signUp(
                event.email,
                event.password,
                event.confirmPassword
            )
        }
    }

    private fun onLoginUiEvent(event: LoginUiEvent) {
        when (event) {
            LoginUiEvent.OnErrorDialogDismiss -> loginViewModel.onLoginHandled()
            is LoginUiEvent.OnLoginButtonClicked -> loginViewModel.login(
                event.email,
                event.password
            )
        }
    }

    private fun showSuccess(user: User) {
        sharedPreferencesUtils.saveUser(user)
        ApiServiceInterceptor.setAuthenticationToken(user.authenticationToken)
        navController.navigate(AuthFragmentDirections.actionGlobalHomeFragment())
    }
}

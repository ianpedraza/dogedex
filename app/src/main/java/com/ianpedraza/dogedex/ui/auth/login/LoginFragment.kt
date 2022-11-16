package com.ianpedraza.dogedex.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.framework.api.ApiServiceInterceptor
import com.ianpedraza.dogedex.ui.theme.DogedexTheme
import com.ianpedraza.dogedex.utils.ComposeFragmentExtensions.Companion.contentView
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private val navController: NavController get() = findNavController()

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ComposeView = contentView {
        val uiState by viewModel.uiState.collectAsState()

        if (uiState.loginStatus is DataState.Success) {
            showSuccess((uiState.loginStatus as DataState.Success<User>).data)
        }

        DogedexTheme {
            LoginScreen(
                uiState = uiState,
                onUiEvent = ::handleOnUiEvent,
                onNavigationEvent = ::handleOnNavigationEvent
            )
        }
    }

    private fun handleOnUiEvent(event: LoginUiEvent) {
        when (event) {
            LoginUiEvent.OnErrorDialogDismiss -> onErrorDialogDismiss()
            is LoginUiEvent.OnLoginButtonClicked -> onLoginButtonClicked(
                event.email,
                event.password
            )
        }
    }

    private fun handleOnNavigationEvent(event: LoginNavigationEvent) {
        when (event) {
            LoginNavigationEvent.RegisterButtonClicked -> onRegisterButtonClicked()
        }
    }

    private fun onErrorDialogDismiss() {
        viewModel.onLoginHandled()
    }

    private fun onLoginButtonClicked(email: String, password: String) {
        viewModel.login(email, password)
    }

    private fun onRegisterButtonClicked() {
        // navController.navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    private fun showSuccess(user: User) {
        sharedPreferencesUtils.saveUser(user)
        ApiServiceInterceptor.setAuthenticationToken(user.authenticationToken)
        // navController.navigate(LoginFragmentDirections.actionGlobalHomeFragment())
    }
}

package com.ianpedraza.dogedex.ui.auth.signup

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
import com.ianpedraza.dogedex.ui.theme.DogedexTheme
import com.ianpedraza.dogedex.utils.ComposeFragmentExtensions.Companion.contentView
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private val viewModel: SignupViewModel by viewModels()

    private val navController: NavController get() = findNavController()

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ComposeView = contentView {
        DogedexTheme {
            val uiState by viewModel.uiState

            if (uiState.signUpStatus is DataState.Success) {
                showSuccess((uiState.signUpStatus as DataState.Success<User>).data)
            }

            SignUpScreen(
                uiState = uiState,
                onUiEvent = ::handleUiEvent,
                onNavigationEvent = ::onNavigationEvent
            )
        }
    }

    private fun onNavigationEvent(event: SignUpNavigationEvent) {
        when (event) {
            SignUpNavigationEvent.OnBackPressed -> navController.navigateUp()
        }
    }

    private fun handleUiEvent(event: SignUpUiEvent) {
        when (event) {
            SignUpUiEvent.OnErrorDialogDismiss -> onErrorDialogDismiss()
            is SignUpUiEvent.OnSignUpButtonClicked -> onLoginButtonClicked(
                event.email,
                event.password,
                event.confirmPassword
            )
        }
    }

    private fun onErrorDialogDismiss() {
        viewModel.onSignUpHandled()
    }

    private fun onLoginButtonClicked(email: String, password: String, confirmPassword: String) {
        viewModel.signUp(email, password, confirmPassword)
    }

    private fun showSuccess(user: User) {
        sharedPreferencesUtils.saveUser(user)
        ApiServiceInterceptor.setAuthenticationToken(user.authenticationToken)
        // navController.navigate(SignUpFragmentDirections.actionGlobalHomeFragment())
    }
}

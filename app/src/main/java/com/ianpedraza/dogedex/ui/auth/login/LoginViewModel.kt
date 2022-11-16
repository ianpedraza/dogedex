package com.ianpedraza.dogedex.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.usecases.LoginUseCase
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.emailvalidator.AuthValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val loginUseCase: LoginUseCase,
    private val authValidator: AuthValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val uiState: StateFlow<LoginUiState> get() = _uiState

    fun login(email: String, password: String) {
        resetErrors()

        if (!authValidator.isValidEmail(email)) {
            _uiState.value = _uiState.value.copy(
                emailError = R.string.error_email_is_not_valid
            )
            return
        }

        if (!authValidator.isValidPassword(password)) {
            _uiState.value = _uiState.value.copy(
                passwordError = R.string.error_password_is_not_valid
            )

            return
        }

        viewModelScope.launch {
            loginUseCase(email, password).onEach { dataState ->
                _uiState.value = _uiState.value.copy(
                    loginStatus = dataState
                )
            }.launchIn(viewModelScope)
        }
    }

    fun onLoginHandled() {
        _uiState.value = _uiState.value.copy(
            loginStatus = null
        )
    }

    private fun resetErrors() {
        _uiState.value = _uiState.value.copy(
            emailError = null,
            passwordError = null
        )
    }
}

data class LoginUiState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val loginStatus: DataState<User>? = null
)

sealed interface LoginUiEvent {
    data class OnLoginButtonClicked(val email: String, val password: String) : LoginUiEvent
    object OnErrorDialogDismiss : LoginUiEvent
}

sealed interface LoginNavigationEvent {
    object RegisterButtonClicked : LoginNavigationEvent
}

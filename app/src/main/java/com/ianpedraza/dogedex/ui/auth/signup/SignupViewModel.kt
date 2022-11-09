package com.ianpedraza.dogedex.ui.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.usecases.SignUpUseCase
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel
@Inject
constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(SignUpUiState())
    val uiState: State<SignUpUiState> get() = _uiState

    fun signUp(email: String, password: String, confirmPassword: String) {
        resetErrors()

        if (!Validators.isValidEmail(email)) {
            _uiState.value = _uiState.value.copy(
                emailError = R.string.error_email_is_not_valid
            )
            return
        }

        if (!Validators.isValidPassword(password)) {
            _uiState.value = _uiState.value.copy(
                passwordError = R.string.error_password_is_not_valid
            )
            return
        }

        if (!Validators.arePasswordsEqual(password, confirmPassword)) {
            _uiState.value = _uiState.value.copy(
                confirmPasswordError = R.string.error_passwords_do_not_match
            )
            return
        }

        viewModelScope.launch {
            signUpUseCase(email, password).onEach { dataState ->
                _uiState.value = _uiState.value.copy(
                    signUpStatus = dataState
                )
            }.launchIn(viewModelScope)
        }
    }

    fun onSignUpHandled() {
        _uiState.value = _uiState.value.copy(
            signUpStatus = null
        )
    }

    private fun resetErrors() {
        _uiState.value = _uiState.value.copy(
            emailError = null,
            passwordError = null,
            confirmPasswordError = null
        )
    }
}

data class SignUpUiState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val confirmPasswordError: Int? = null,
    val signUpStatus: DataState<User>? = null
)

sealed interface SignUpUiEvent {
    data class OnSignUpButtonClicked(
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : SignUpUiEvent

    object OnErrorDialogDismiss : SignUpUiEvent
}

sealed interface SignUpNavigationEvent {
    object OnBackPressed : SignUpNavigationEvent
}

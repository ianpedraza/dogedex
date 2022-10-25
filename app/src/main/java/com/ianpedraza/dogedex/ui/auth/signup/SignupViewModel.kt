package com.ianpedraza.dogedex.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val _emailError = MutableLiveData<Boolean>()
    val emailError: LiveData<Boolean> get() = _emailError

    private val _passwordError = MutableLiveData<Boolean>()
    val passwordError: LiveData<Boolean> get() = _passwordError

    private val _confirmPasswordError = MutableLiveData<Boolean>()
    val confirmPasswordError: LiveData<Boolean> get() = _confirmPasswordError

    private val _fieldsValidated = MutableLiveData<Pair<String, String>?>()
    val fieldsValidated: LiveData<Pair<String, String>?> get() = _fieldsValidated

    private val _signUpStatus = MutableLiveData<DataState<User>?>()
    val signUpStatus: LiveData<DataState<User>?> get() = _signUpStatus

    fun validateFields(email: String, password: String, confirmPassword: String) {
        resetErrors()

        if (!Validators.isValidEmail(email)) {
            _emailError.value = true
            return
        }

        if (!Validators.isValidPassword(password)) {
            _passwordError.value = true
            return
        }

        if (!Validators.arePasswordsEqual(password, confirmPassword)) {
            _confirmPasswordError.value = true
            return
        }

        _fieldsValidated.value = Pair(email, password)
    }

    fun onFieldsValidated() {
        _fieldsValidated.value = null
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            signUpUseCase(email, password).onEach { dataState ->
                _signUpStatus.value = dataState
            }.launchIn(viewModelScope)
        }
    }

    fun onSignUpHandled() {
        _signUpStatus.value = null
    }

    private fun resetErrors() {
        _fieldsValidated.value = null
        _emailError.value = false
        _passwordError.value = false
        _confirmPasswordError.value = false
    }
}

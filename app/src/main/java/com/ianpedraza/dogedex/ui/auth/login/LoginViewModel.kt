package com.ianpedraza.dogedex.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.ui.auth.managers.AuthResourcesManager
import com.ianpedraza.dogedex.usecases.LoginUseCase
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val resourcesManager: AuthResourcesManager,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> get() = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> get() = _passwordError

    private val _fieldsValidated = MutableLiveData<Pair<String, String>?>()
    val fieldsValidated: LiveData<Pair<String, String>?> get() = _fieldsValidated

    private val _loginStatus = MutableLiveData<DataState<User>?>()
    val loginStatus: LiveData<DataState<User>?> get() = _loginStatus

    fun validateFields(email: String, password: String) {
        resetErrors()

        if (!Validators.isValidEmail(email)) {
            _emailError.value = resourcesManager.getEmailIsNotValidError()
            return
        }

        if (!Validators.isValidPassword(password)) {
            _passwordError.value = resourcesManager.getPasswordIsNotValidError()
            return
        }

        _fieldsValidated.value = Pair(email, password)
    }

    fun onFieldsValidated() {
        _fieldsValidated.value = null
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).onEach { dataState ->
                _loginStatus.value = dataState
            }.launchIn(viewModelScope)
        }
    }

    fun onLoginHandled() {
        _loginStatus.value = null
    }

    private fun resetErrors() {
        _fieldsValidated.value = null
        _emailError.value = null
        _passwordError.value = null
    }
}

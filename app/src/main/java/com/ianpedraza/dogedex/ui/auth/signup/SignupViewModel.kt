package com.ianpedraza.dogedex.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianpedraza.dogedex.ui.auth.signup.managers.SignUpResourcesManager
import com.ianpedraza.dogedex.utils.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel
@Inject
constructor(
    private val resourcesManager: SignUpResourcesManager
) : ViewModel() {
    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _confirmPasswordError = MutableLiveData<String?>()
    val confirmPasswordError: LiveData<String?> = _confirmPasswordError

    fun validateFields(email: String, password: String, confirmPassword: String) {
        resetErrors()

        if (!Validators.isValidEmail(email)) {
            _emailError.value = resourcesManager.getEmailIsNotValidError()
            return
        }

        if (!Validators.isValidPassword(password)) {
            _passwordError.value = resourcesManager.getPasswordIsNotValidError()
            return
        }

        if (!Validators.arePasswordsEqual(password, confirmPassword)) {
            _confirmPasswordError.value = resourcesManager.getPasswordsDoNotMatchError()
            return
        }
    }

    private fun resetErrors() {
        _emailError.value = null
        _passwordError.value = null
        _confirmPasswordError.value = null
    }
}

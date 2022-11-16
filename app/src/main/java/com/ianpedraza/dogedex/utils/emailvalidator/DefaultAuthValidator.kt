package com.ianpedraza.dogedex.utils.emailvalidator

import android.util.Patterns
import javax.inject.Inject

class DefaultAuthValidator @Inject constructor() : AuthValidator {

    companion object {
        private const val PASSWORD_MIN_LENGTH = 8
    }

    override fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun isValidPassword(password: String?): Boolean {
        return !password.isNullOrEmpty() && password.length >= PASSWORD_MIN_LENGTH
    }

    override fun arePasswordsEqual(password1: String?, password2: String?): Boolean {
        return password1 == password2
    }
}

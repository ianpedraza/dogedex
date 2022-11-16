package com.ianpedraza.dogedex.fakes

import com.ianpedraza.dogedex.utils.emailvalidator.AuthValidator
import java.util.regex.Pattern

class FakeAuthValidator : AuthValidator {
    companion object {
        private const val PASSWORD_MIN_LENGTH = 8
        private const val EMAIL_ADDRESS_REGEX =
            "[a-zA-Z0-9+._%-+]{1,256}" + "@" +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "." +
                "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+"
    }

    override fun isValidEmail(email: String?): Boolean {
        val pattern = Pattern.compile(EMAIL_ADDRESS_REGEX)
        return !email.isNullOrEmpty() && pattern.matcher(email).matches()
    }

    override fun isValidPassword(password: String?): Boolean {
        return !password.isNullOrEmpty() && password.length >= PASSWORD_MIN_LENGTH
    }

    override fun arePasswordsEqual(password1: String?, password2: String?): Boolean {
        return password1 == password2
    }
}

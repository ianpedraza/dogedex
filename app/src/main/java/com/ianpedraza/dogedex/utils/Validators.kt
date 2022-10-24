package com.ianpedraza.dogedex.utils

import android.util.Patterns

class Validators {

    companion object {
        private const val PASSWORD_MIN_LENGTH = 8

        fun isValidEmail(email: String?): Boolean {
            return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String?): Boolean {
            return !password.isNullOrEmpty() && password.length >= PASSWORD_MIN_LENGTH
        }

        fun arePasswordsEqual(password1: String?, password2: String?): Boolean {
            return password1 == password2
        }
    }
}

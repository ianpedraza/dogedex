package com.ianpedraza.dogedex.utils.emailvalidator

interface AuthValidator {
    fun isValidEmail(email: String?): Boolean
    fun isValidPassword(password: String?): Boolean
    fun arePasswordsEqual(password1: String?, password2: String?): Boolean
}


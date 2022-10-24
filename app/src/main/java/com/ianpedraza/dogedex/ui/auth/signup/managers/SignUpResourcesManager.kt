package com.ianpedraza.dogedex.ui.auth.signup.managers

interface SignUpResourcesManager {
    fun getEmailIsNotValidError(): String
    fun getPasswordIsNotValidError(): String
    fun getPasswordsDoNotMatchError(): String
}

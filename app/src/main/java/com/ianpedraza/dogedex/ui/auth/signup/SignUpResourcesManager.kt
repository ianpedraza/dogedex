package com.ianpedraza.dogedex.ui.auth.signup

interface SignUpResourcesManager {
    fun getEmailIsNotValidError(): String
    fun getPasswordIsNotValidError(): String
    fun getPasswordsDoNotMatchError(): String
}

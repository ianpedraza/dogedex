package com.ianpedraza.dogedex.ui.auth.managers

interface AuthResourcesManager {
    fun getEmailIsNotValidError(): String
    fun getPasswordIsNotValidError(): String
    fun getPasswordsDoNotMatchError(): String
}

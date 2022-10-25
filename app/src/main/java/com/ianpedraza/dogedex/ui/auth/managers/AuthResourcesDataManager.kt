package com.ianpedraza.dogedex.ui.auth.managers

import android.content.res.Resources
import com.ianpedraza.dogedex.R

class AuthResourcesDataManager(
    private val resources: Resources
) : AuthResourcesManager {
    override fun getEmailIsNotValidError(): String =
        resources.getString(R.string.error_email_is_not_valid)

    override fun getPasswordIsNotValidError(): String =
        resources.getString(R.string.error_password_is_not_valid)

    override fun getPasswordsDoNotMatchError(): String =
        resources.getString(R.string.error_passwords_do_not_match)
}

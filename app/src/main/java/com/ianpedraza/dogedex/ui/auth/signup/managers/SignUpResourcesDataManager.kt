package com.ianpedraza.dogedex.ui.auth.signup.managers

import android.content.res.Resources
import com.ianpedraza.dogedex.R

class SignUpResourcesDataManager(
    private val resources: Resources
) : SignUpResourcesManager {
    override fun getEmailIsNotValidError(): String =
        resources.getString(R.string.error_email_is_not_valid)

    override fun getPasswordIsNotValidError(): String =
        resources.getString(R.string.error_password_is_not_valid)

    override fun getPasswordsDoNotMatchError(): String =
        resources.getString(R.string.error_passwords_do_not_match)
}

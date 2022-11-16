package com.ianpedraza.dogedex.ui.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.framework.api.ApiServiceInterceptor
import com.ianpedraza.dogedex.ui.theme.DogedexTheme
import com.ianpedraza.dogedex.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    // private val navController: NavController
    // get() = Navigation.findNavController(this, R.id.navHostFragment)

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogedexTheme {
                AuthScreen(
                    onUserLoggedIn = ::showSuccess,
                    onUserRegistered = ::showSuccess
                )
            }
        }
    }

    private fun showSuccess(user: User) {
        sharedPreferencesUtils.saveUser(user)
        ApiServiceInterceptor.setAuthenticationToken(user.authenticationToken)
        // navController.navigate(AuthActivityDirections.actionGlobalHomeFragment())
    }
}

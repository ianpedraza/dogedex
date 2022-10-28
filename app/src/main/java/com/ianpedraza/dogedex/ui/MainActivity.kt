package com.ianpedraza.dogedex.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.ianpedraza.dogedex.NavGraphDirections
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.databinding.ActivityMainBinding
import com.ianpedraza.dogedex.framework.api.ApiServiceInterceptor
import com.ianpedraza.dogedex.utils.SharedPreferencesUtils
import com.ianpedraza.dogedex.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navHostFragment.navController
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val topLevelFragments = setOf(R.id.loginFragment, R.id.homeFragment)

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
        checkUserLoggedIn()
    }

    private fun setupUI() {
        appBarConfiguration = AppBarConfiguration.Builder(topLevelFragments).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    private fun checkUserLoggedIn() {
        val user = sharedPreferencesUtils.getUser() ?: return
        ApiServiceInterceptor.setAuthenticationToken(user.authenticationToken)
        navController.navigate(NavGraphDirections.actionGlobalHomeFragment())
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()
}

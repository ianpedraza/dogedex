package com.ianpedraza.dogedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.databinding.FragmentSettingsBinding
import com.ianpedraza.dogedex.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!

    private val navController: NavController get() = findNavController()

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.buttonSettingsLogout.setOnClickListener { showLogoutAlertDialog() }
    }

    private fun showLogoutAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.do_you_wanna_logout))
            .setPositiveButton(getString(R.string.logout)) { _, _ -> logout() }
            .setNegativeButton(getString(android.R.string.cancel), null)
            .create()
            .show()
    }

    private fun logout() {
        sharedPreferencesUtils.removeUser()
        navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToAuthFragment())
    }
}

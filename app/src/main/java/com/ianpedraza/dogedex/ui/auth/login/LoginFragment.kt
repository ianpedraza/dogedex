package com.ianpedraza.dogedex.ui.auth.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.databinding.FragmentLoginBinding
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    private val navController: NavController get() = findNavController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeObservers()
    }

    private fun setupUI() {
        binding.buttonLoginRegister.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.textInputEditTextLoginEmail.text.toString()
            val password = binding.textInputEditTextLoginPassword.text.toString()
            viewModel.validateFields(email, password)
        }
    }

    private fun subscribeObservers() {
        viewModel.emailError.observe(viewLifecycleOwner) { emailError ->
            binding.textInputLayoutLoginEmail.error = if (emailError) {
                getString(R.string.error_email_is_not_valid)
            } else {
                null
            }
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { passwordError ->
            binding.textInputLayoutLoginPassword.error = if (passwordError) {
                getString(R.string.error_password_is_not_valid)
            } else {
                null
            }
        }

        viewModel.fieldsValidated.observe(viewLifecycleOwner) { fields ->
            fields?.let {
                viewModel.login(fields.first, fields.second)
                viewModel.onFieldsValidated()
            }
        }

        viewModel.loginStatus.observe(viewLifecycleOwner) { dataState ->
            dataState?.let {
                when (dataState) {
                    is DataState.Error -> showError(dataState.exception)
                    DataState.Loading -> showLoading()
                    is DataState.Success -> showSuccess(dataState.data)
                }
                viewModel.onLoginHandled()
            }
        }
    }

    private fun showError(error: Exception) {
        binding.progressBarLogin.showView(false)
        binding.buttonLogin.showView()
        showErrorDialog(error.message)
    }

    private fun showErrorDialog(message: String?) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.there_was_an_error))
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .create()
            .show()
    }

    private fun showLoading() {
        binding.buttonLogin.showView(false)
        binding.progressBarLogin.showView()
    }

    private fun showSuccess(user: User) {
        binding.progressBarLogin.showView(false)
        binding.buttonLogin.showView(false)
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToListFragment())
    }
}

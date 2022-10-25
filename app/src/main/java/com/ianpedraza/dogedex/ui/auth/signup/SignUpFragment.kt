package com.ianpedraza.dogedex.ui.auth.signup

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ianpedraza.dogedex.NavGraphDirections
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.databinding.FragmentSignUpBinding
import com.ianpedraza.dogedex.domain.models.User
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.SharedPreferencesUtils
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.showView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding get() = _binding!!

    private val viewModel: SignupViewModel by viewModels()

    private val navController: NavController get() = findNavController()

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeObservers()
    }

    private fun setupUI() {
        binding.buttonSignup.setOnClickListener {
            val email = binding.textInputEditTextSignUpEmail.text.toString()
            val password = binding.textInputEditTextSignUpPassword.text.toString()
            val confirmPassword = binding.textInputEditTextSignUpConfirmPassword.text.toString()
            viewModel.validateFields(email, password, confirmPassword)
        }
    }

    private fun subscribeObservers() {
        viewModel.emailError.observe(viewLifecycleOwner) { emailError ->
            binding.textInputLayoutSignUpEmail.error = if (emailError) {
                getString(R.string.error_email_is_not_valid)
            } else {
                null
            }
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { passwordError ->
            binding.textInputLayoutSignUpPassword.error = if (passwordError) {
                getString(R.string.error_password_is_not_valid)
            } else {
                null
            }
        }

        viewModel.confirmPasswordError.observe(viewLifecycleOwner) { confirmPasswordError ->
            binding.textInputLayoutSignUpConfirmPassword.error = if (confirmPasswordError) {
                getString(R.string.error_passwords_do_not_match)
            } else {
                null
            }
        }

        viewModel.fieldsValidated.observe(viewLifecycleOwner) { fields ->
            fields?.let {
                viewModel.signup(fields.first, fields.second)
                viewModel.onFieldsValidated()
            }
        }

        viewModel.signUpStatus.observe(viewLifecycleOwner) { dataState ->
            dataState?.let {
                when (dataState) {
                    is DataState.Error -> showError(dataState.exception)
                    DataState.Loading -> showLoading()
                    is DataState.Success -> showSuccess(dataState.data)
                }
                viewModel.onSignUpHandled()
            }
        }
    }

    private fun showError(error: Exception) {
        binding.progressBarSignUp.showView(false)
        binding.buttonSignup.showView()
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
        binding.buttonSignup.showView(false)
        binding.progressBarSignUp.showView()
    }

    private fun showSuccess(user: User) {
        sharedPreferencesUtils.saveUser(user)
        binding.progressBarSignUp.showView(false)
        binding.buttonSignup.showView(false)
        navController.navigate(NavGraphDirections.actionGlobalListFragment())
    }
}

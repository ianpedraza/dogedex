package com.ianpedraza.dogedex.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ianpedraza.dogedex.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding get() = _binding!!

    private val viewModel: SignupViewModel by viewModels()

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
            binding.textInputLayoutSignUpEmail.error = emailError
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { passwordError ->
            binding.textInputLayoutSignUpPassword.error = passwordError
        }

        viewModel.confirmPasswordError.observe(viewLifecycleOwner) { confirmPasswordError ->
            binding.textInputLayoutSignUpConfirmPassword.error = confirmPasswordError
        }
    }
}

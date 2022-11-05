package com.ianpedraza.dogedex.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.databinding.FragmentDogDetailBinding
import com.ianpedraza.dogedex.utils.DataState
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.fromUrl
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.showToast
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogDetailFragment : Fragment() {

    private var _binding: FragmentDogDetailBinding? = null
    private val binding: FragmentDogDetailBinding get() = _binding!!

    private val args: DogDetailFragmentArgs by navArgs()

    private val navController: NavController get() = findNavController()

    private val viewModel: DogDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeObservers()
    }

    private fun setupUI() {
        setupDog()

        binding.fabDoneButton.setOnClickListener {
            viewModel.addDog(args.dog.id)
        }

        binding.fabDoneButton.showView(args.isRecognized)
    }

    private fun subscribeObservers() {
        viewModel.addDogStatus.observe(viewLifecycleOwner) { dataState ->
            dataState?.let {
                handleAddDog(dataState)
            }
        }
    }

    private fun handleAddDog(dataState: DataState<Boolean>) {
        when (dataState) {
            is DataState.Error -> showError(dataState.error)
            DataState.Loading -> showLoading()
            is DataState.Success -> goBackToCamera()
        }
    }

    private fun goBackToCamera() {
        viewModel.handledAddDog()
        navController.navigateUp()
    }

    private fun showLoading() {
        binding.progressBarDetail.showView()
    }

    private fun showError(@StringRes error: Int) {
        binding.progressBarDetail.showView(false)
        requireContext().showToast(error)
    }

    private fun setupDog() {
        binding.progressBarDetail.visibility = View.VISIBLE

        with(args.dog) {
            binding.textViewDetailName.text = name
            binding.textViewDetailIndex.text = getString(R.string.format_index, index)
            binding.textViewDetailType.text = type
            binding.textViewDetailLifeExpectancy.text =
                getString(R.string.format_life_expectancy, lifeExpectancy)

            binding.textViewDetailTemperament.text = temperament
            binding.textViewDetailFemaleWeight.text = weightFemale
            binding.textViewDetailFemaleHeight.text =
                getString(R.string.format_height, heightFemale)

            binding.textViewDetailMaleWeight.text = weightMale
            binding.textViewDetailMaleHeight.text = getString(R.string.format_height, heightMale)
            binding.imageViewDetailDog.fromUrl(imageUrl)
        }

        binding.progressBarDetail.visibility = View.GONE
    }
}

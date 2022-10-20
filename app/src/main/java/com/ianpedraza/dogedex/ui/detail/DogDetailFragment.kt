package com.ianpedraza.dogedex.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.databinding.FragmentDogDetailBinding
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.fromUrl

class DogDetailFragment : Fragment() {

    private var _binding: FragmentDogDetailBinding? = null
    private val binding: FragmentDogDetailBinding get() = _binding!!

    private val args: DogDetailFragmentArgs by navArgs()

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
    }

    private fun setupUI() {
        setupDog()

        binding.fabDoneButton.setOnClickListener {
            findNavController().navigateUp()
        }
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

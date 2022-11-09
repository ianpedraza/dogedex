package com.ianpedraza.dogedex.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ianpedraza.dogedex.ui.theme.DogedexTheme
import com.ianpedraza.dogedex.utils.ComposeFragmentExtensions.Companion.contentView
import com.ianpedraza.dogedex.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogDetailComposeFragment : Fragment() {

    private val args: DogDetailComposeFragmentArgs by navArgs()

    private val viewModel: DogDetailViewModel by viewModels()

    private val navController: NavController get() = findNavController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ComposeView = contentView {
        val status by viewModel.addDogStatus

        if (status is DataState.Success) {
            goBack()
        }

        DogedexTheme {
            DogDetailScreen(
                dog = args.dog,
                dataState = status,
                onButtonClicked = { onButtonClicked(args.dog.id, args.isRecognized) },
                onErrorDialogDismiss = ::resetStatus
            )
        }
    }

    private fun resetStatus() {
        viewModel.resetAddSogStatus()
    }

    private fun onButtonClicked(dogId: Long, isRecognition: Boolean) {
        if (isRecognition) {
            viewModel.addDog(dogId)
        } else {
            goBack()
        }
    }

    private fun goBack() {
        viewModel.resetAddSogStatus()
        navController.navigateUp()
    }
}

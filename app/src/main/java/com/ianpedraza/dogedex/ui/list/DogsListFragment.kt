package com.ianpedraza.dogedex.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.ui.theme.DogedexTheme
import com.ianpedraza.dogedex.utils.ComposeFragmentExtensions.Companion.contentView
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class DogsListFragment : Fragment() {

    private val navController: NavController get() = findNavController()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ComposeView = contentView {
        DogedexTheme {
            DogListScreen(
                onAction = ::onAction
            )
        }
    }

    private fun onAction(action: Action) {
        when (action) {
            is Action.OnClick -> openDog(action.dog)
        }
    }

    private fun openDog(dog: Dog) {
        navController.navigate(
            DogsListFragmentDirections.actionListFragmentToDogDetailComposeFragment(
                dog
            )
        )
    }
}

package com.ianpedraza.dogedex.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

class ComposeFragmentExtensions {
    companion object {
        fun Fragment.requireContentView(
            compositionStrategy: ViewCompositionStrategy = ViewCompositionStrategy.DisposeOnDetachedFromWindow,
            context: Context = requireContext(),
            content: @Composable () -> Unit
        ): ComposeView {
            val view = ComposeView(context)
            view.setViewCompositionStrategy(compositionStrategy)
            view.setContent(content)
            return view
        }

        fun Fragment.contentView(
            compositionStrategy: ViewCompositionStrategy = ViewCompositionStrategy.DisposeOnDetachedFromWindow,
            context: Context = requireContext(),
            content: @Composable () -> Unit
        ): ComposeView {
            val view = ComposeView(context)
            view.setViewCompositionStrategy(compositionStrategy)
            view.setContent(content)
            return view
        }
    }
}

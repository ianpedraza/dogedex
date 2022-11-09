package com.ianpedraza.dogedex.ui.composables

import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.ianpedraza.dogedex.R

private const val EMPTY_STRING = ""

@Composable
fun AppTopBar(
    @StringRes titleRes: Int? = null,
    onBackPressed: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            val title = if (titleRes != null) {
                stringResource(titleRes)
            } else {
                EMPTY_STRING
            }

            Text(title)
        },
        navigationIcon = if (onBackPressed != null) {
            {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else {
            null
        },
        backgroundColor = colorResource(R.color.color_primary),
        contentColor = colorResource(R.color.white)
    )
}

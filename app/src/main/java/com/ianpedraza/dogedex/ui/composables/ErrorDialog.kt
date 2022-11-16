package com.ianpedraza.dogedex.ui.composables

import androidx.annotation.StringRes
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.ianpedraza.dogedex.R

@Composable
fun ErrorDialog(
    @StringRes error: Int,
    onErrorDialogDismiss: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.semantics { testTag = "error-dialog" },
        onDismissRequest = { },
        title = {
            Text(text = stringResource(R.string.error_dialog_title))
        },
        text = {
            Text(stringResource(error))
        },
        confirmButton = {
            Button(onClick = onErrorDialogDismiss) {
                Text(text = stringResource(android.R.string.ok))
            }
        }
    )
}

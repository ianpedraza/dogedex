package com.ianpedraza.dogedex.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthField(
    modifier: Modifier = Modifier,
    errorModifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    @StringRes error: Int? = null
) {
    Column {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            visualTransformation = visualTransformation,
            singleLine = true,
            isError = error != null
        )

        error?.let {
            Text(
                modifier = errorModifier,
                text = stringResource(error),
                color = Color.Red
            )
        }
    }
}

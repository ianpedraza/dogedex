package com.ianpedraza.dogedex.ui.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.ui.composables.AppTopBar
import com.ianpedraza.dogedex.ui.composables.AuthField
import com.ianpedraza.dogedex.ui.composables.ErrorDialog
import com.ianpedraza.dogedex.ui.composables.LoadingWheel
import com.ianpedraza.dogedex.utils.DataState

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onUiEvent: (SignUpUiEvent) -> Unit,
    onNavigationEvent: (SignUpNavigationEvent) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                titleRes = R.string.app_name,
                onBackPressed = { onNavigationEvent(SignUpNavigationEvent.OnBackPressed) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Content(
                uiState = uiState,
                onUiEvent = onUiEvent
            )
        }
    }
}

@Composable
private fun Content(
    uiState: SignUpUiState,
    onUiEvent: (SignUpUiEvent) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            label = stringResource(id = R.string.email),
            placeholder = stringResource(id = R.string.email),
            onValueChange = { email = it },
            error = uiState.emailError
        )

        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = password,
            label = stringResource(id = R.string.password),
            placeholder = stringResource(id = R.string.password),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { password = it },
            error = uiState.passwordError
        )

        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = confirmPassword,
            label = stringResource(id = R.string.confirm_password),
            placeholder = stringResource(id = R.string.confirm_password),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { confirmPassword = it },
            error = uiState.confirmPasswordError
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
                .semantics { testTag = "signup-button" },
            onClick = {
                onUiEvent(
                    SignUpUiEvent.OnSignUpButtonClicked(
                        email,
                        password,
                        confirmPassword
                    )
                )
            }
        ) {
            Text(
                stringResource(R.string.sign_up),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }

    if (uiState.signUpStatus is DataState.Loading) {
        LoadingWheel()
    }

    if (uiState.signUpStatus is DataState.Error) {
        ErrorDialog(
            error = uiState.signUpStatus.error,
            onErrorDialogDismiss = { onUiEvent(SignUpUiEvent.OnErrorDialogDismiss) }
        )
    }
}

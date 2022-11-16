package com.ianpedraza.dogedex.ui.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
fun LoginScreen(
    uiState: LoginUiState,
    onUiEvent: (LoginUiEvent) -> Unit,
    onNavigationEvent: (LoginNavigationEvent) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                titleRes = R.string.app_name
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Content(
                uiState,
                onUiEvent,
                onNavigationEvent
            )
        }
    }
}

@Composable
private fun Content(
    uiState: LoginUiState,
    onUiEvent: (LoginUiEvent) -> Unit,
    onNavigationEvent: (LoginNavigationEvent) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            modifier = Modifier
                .fillMaxWidth()
                .semantics { testTag = "email-field" },
            errorModifier = Modifier.semantics { testTag = "email-error" },
            value = email,
            label = stringResource(id = R.string.email),
            placeholder = stringResource(id = R.string.email),
            onValueChange = { email = it },
            error = uiState.emailError
        )

        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .semantics { testTag = "password-field" },
            errorModifier = Modifier.semantics { testTag = "password-error" },
            value = password,
            label = stringResource(id = R.string.password),
            placeholder = stringResource(id = R.string.password),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { password = it },
            error = uiState.passwordError
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
                .semantics {
                    testTag = "login-button"
                },
            onClick = { onUiEvent(LoginUiEvent.OnLoginButtonClicked(email, password)) }
        ) {
            Text(
                stringResource(R.string.login),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(R.string.do_not_have_an_account),
            textAlign = TextAlign.Center
        )

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .semantics { testTag = "register-button" },
            onClick = { onNavigationEvent(LoginNavigationEvent.RegisterButtonClicked) }
        ) {
            Text(
                text = stringResource(R.string.register),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }

    if (uiState.loginStatus is DataState.Loading) {
        LoadingWheel()
    }

    if (uiState.loginStatus is DataState.Error) {
        ErrorDialog(
            error = uiState.loginStatus.error,
            onErrorDialogDismiss = { onUiEvent(LoginUiEvent.OnErrorDialogDismiss) }
        )
    }
}

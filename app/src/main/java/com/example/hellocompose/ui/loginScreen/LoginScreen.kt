package com.example.hellocompose.ui.loginScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hellocompose.R
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.example.hellocompose.ui.util.*
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets


@OptIn(ExperimentalAnimatedInsets::class)
@Composable
fun loginScreen(
    loading: Boolean,
    showError: Pair<Boolean, String>,
    onLoginClicl: (
        name: String,
        surname: String,
        phone: String,
        email: String,
        dateOfBirth: String
    ) -> Unit,
    closeErrorView: () -> Unit,
    setDecorFitsSystemWindows: () -> Unit = {}
) {
    setDecorFitsSystemWindows()
    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Box() {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {}
            if (!loading) {
                Column(
                    modifier = Modifier
                        .padding(28.dp)
                        .verticalScroll(rememberScrollState())

                ) {
                    headerText(stringResource(R.string.registration))
                    var name by rememberSaveable { mutableStateOf("") }
                    var nameErrorText by rememberSaveable { mutableStateOf("") }
                    var nameContainsError by rememberSaveable { mutableStateOf(false) }
                    val nameColor = if (nameContainsError) Color.Red else Color.Unspecified
                    infoEnterField(
                        color = nameColor,
                        errorText = nameErrorText,
                        value = name,
                        valueContainsError = nameContainsError,
                        textHeader = stringResource(R.string.name),
                        onvalueChanged = {
                            name = it
                            if (name.isNotEmpty()) {
                                nameErrorText = ""
                                nameContainsError = false
                            }
                        }
                    )

                    var surName by remember { mutableStateOf("") }
                    var surNameErrorText by remember { mutableStateOf("") }
                    var surNameContainsError by remember { mutableStateOf(false) }
                    val surNameColor = if (surNameContainsError) Color.Red else Color.Unspecified
                    infoEnterField(
                        color = surNameColor,
                        errorText = surNameErrorText,
                        value = surName,
                        valueContainsError = surNameContainsError,
                        textHeader = stringResource(R.string.surname)
                    ) {
                        surName = it
                        if (surName.isNotEmpty()) {
                            surNameErrorText = ""
                            surNameContainsError = false
                        }
                    }

                    var phoneNumber by remember { mutableStateOf("") }
                    var phoneNumberErrorText by remember { mutableStateOf("") }
                    var phoneNumberContainsError by remember { mutableStateOf(false) }
                    val phoneNumberColor =
                        if (phoneNumberContainsError) Color.Red else Color.Unspecified
                    infoEnterField(
                        color = phoneNumberColor,
                        errorText = phoneNumberErrorText,
                        value = phoneNumber,
                        valueContainsError = phoneNumberContainsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textHeader = stringResource(R.string.telefon)
                    ) {
                        phoneNumber = it
                        val pattern = "^[1-9][0-9]{9,14}$".toRegex()
                        if (pattern.matches(phoneNumber)) {
                            phoneNumberErrorText = ""
                            phoneNumberContainsError = false
                        }
                    }

                    var email by remember { mutableStateOf("") }
                    var emailErrorText by remember { mutableStateOf("") }
                    var emailContainsError by remember { mutableStateOf(false) }
                    val color = if (emailContainsError) Color.Red else Color.Unspecified

                    infoEnterField(
                        color = color,
                        errorText = emailErrorText,
                        value = email,
                        valueContainsError = emailContainsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        textHeader = "e-Mail"
                    ) {
                        email = it
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            emailErrorText = ""
                            emailContainsError = false
                        }
                    }

                    var birthDate by remember { mutableStateOf("") }
                    var birthDateErrorText by remember { mutableStateOf("") }
                    var birthDateContainsError by remember { mutableStateOf(false) }
                    val birthDateColor =
                        if (birthDateContainsError) Color.Red else Color.Unspecified
                    birthDateButton(
                        birthDateColor,
                        birthDateErrorText,
                        color,
                        birthDate,
                        birthDateContainsError,
                        onDateSelected = {
                            birthDate = it.toString()
                            birthDateErrorText = ""
                            birthDateContainsError = false
                        }
                    )

                    val context = LocalContext.current.resources
                    registerButton(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
                        if (email.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                                .matches()
                        ) {
                            emailContainsError = true
                            emailErrorText = context.getString(R.string.enter_correct_email)
                        } else {
                            emailContainsError = false
                            emailErrorText = ""
                        }

                        if (name.isEmpty()) {
                            nameContainsError = true
                            nameErrorText = context.getString(R.string.this_field_is_required)
                        } else {
                            nameContainsError = false
                            nameErrorText = ""
                        }

                        if (surName.isEmpty()) {
                            surNameContainsError = true
                            surNameErrorText = context.getString(R.string.this_field_is_required)
                        } else {
                            surNameContainsError = false
                            surNameErrorText = ""
                        }
                        val pattern = "^[1-9][0-9]{9,14}$".toRegex()
                        if (phoneNumber.isEmpty() || !pattern.matches(phoneNumber)) {
                            phoneNumberContainsError = true
                            phoneNumberErrorText =
                                context.getString(R.string.this_field_is_required)
                        } else {
                            phoneNumberContainsError = false
                            phoneNumberErrorText = ""
                        }
                        if (birthDate.isEmpty()) {
                            birthDateContainsError = true
                            birthDateErrorText = context.getString(R.string.this_field_is_required)
                        } else {
                            birthDateContainsError = false
                            birthDateErrorText = ""
                        }

                        if (!(birthDate.isEmpty()
                                    && surName.isEmpty()
                                    && name.isEmpty()
                                    ) && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                                .matches()
                            && pattern.matches(phoneNumber)
                        ) {
                            onLoginClicl(
                                name,
                                surName,
                                phoneNumber,
                                email,
                                birthDate,
                            )
                        }

                    }
                    authorizeText()
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.Center)
                )
            }
            if (showError.first) {
                Snackbar(
                    modifier = Modifier
                        .padding(4.dp, bottom = 50.dp)
                        .align(Alignment.BottomCenter),
                    actionOnNewLine = true,
                    action = {
                        TextButton(onClick = {
                            closeErrorView()
                        }) {
                            Text(text = "Remove")
                        }
                    }
                ) {
                    Text(text = showError.second)
                }
            }
        }
    }
}

@ExperimentalAnimatedInsets
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    HelloComposeTheme {
//        loginScreen(vm = )
    }
}


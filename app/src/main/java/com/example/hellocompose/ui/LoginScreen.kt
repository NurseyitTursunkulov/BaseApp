package com.example.hellocompose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hellocompose.R
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.example.hellocompose.ui.theme.itemsColor
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import com.example.hellocompose.ui.util.birthDateButton
import com.example.hellocompose.ui.util.headerText
import com.example.hellocompose.ui.util.infoEnterField
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.buttons
import com.vanpra.composematerialdialogs.datetime.datepicker.datepicker


@OptIn(ExperimentalAnimatedInsets::class)
@Composable
fun loginScreen(vm: MainViewModel) {
//    val name: String by vm.state.observeAsState("")
//    Log.d("Nurs", name)
    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Column(
            modifier = Modifier
                .padding(28.dp)
                .verticalScroll(rememberScrollState())
        ) {
            headerText(stringResource(R.string.registration))


            var name by remember { mutableStateOf("") }
            var nameErrorText by remember { mutableStateOf("") }
            var nameContainsError by remember { mutableStateOf(false) }
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
            val phoneNumberColor = if (phoneNumberContainsError) Color.Red else Color.Unspecified
            infoEnterField(
                color = phoneNumberColor,
                errorText = phoneNumberErrorText,
                value = phoneNumber,
                valueContainsError = phoneNumberContainsError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textHeader = stringResource(R.string.telefon)
            ) {
                phoneNumber = it
                if (phoneNumber.isNotEmpty()) {
                    phoneNumberErrorText = ""
                    phoneNumberContainsError = false
                }
            }

//            Spacer(Modifier.height(16.dp))
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
            val birthDateColor = if (birthDateContainsError) Color.Red else Color.Unspecified
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
            Spacer(Modifier.height(32.dp))



/* This should be called in an onClick or an Effect */
            val context = LocalContext.current.resources
            OutlinedButton(
                onClick = {
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

                    if (phoneNumber.isEmpty()) {
                        phoneNumberContainsError = true
                        phoneNumberErrorText = context.getString(R.string.this_field_is_required)
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


//                    vm.login(
//                        name = name,
//                        surname = surname,
//                        phone = telefon,
//                        email = email,
//                        dateOfBirth = "2021-05-13"
//                    )
                },
                modifier = Modifier
                    .navigationBarsWithImePadding()
                    .align(alignment = Alignment.CenterHorizontally),  //avoid the oval shape
                border = BorderStroke(1.dp, itemsColor),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = itemsColor)
            ) {
                val style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    letterSpacing = 0.15.sp
                )
                Text(stringResource(R.string.register), style = style)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.already_signed_in))
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    stringResource(R.string.autorize),
                    color = Color.Blue,
                    modifier = Modifier.clickable {

                    })
            }


            Spacer(Modifier.height(96.dp))
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
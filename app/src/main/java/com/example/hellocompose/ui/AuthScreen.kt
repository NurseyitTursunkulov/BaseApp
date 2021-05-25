package com.example.hellocompose.ui

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hellocompose.R
import com.example.hellocompose.ui.theme.*
import com.example.hellocompose.ui.theme.itemsColor3
import com.example.hellocompose.ui.util.MyOutlinedTextField
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimatedInsets
@Composable
fun authScreen(
    phone: String = "",
    onNumberSendClicked: (telefonNumber: String) -> Unit,
) {
    val vm = getViewModel<MainViewModel>()
    val loading = vm.showLoading.observeAsState(false)
    val showError: Pair<Boolean,String> by vm.showError.observeAsState(initial =  Pair(false,""))


    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Box() {
            Surface(
//                color = Color.Black.copy(alpha = 0.1f),
                modifier = Modifier.fillMaxSize()
            ) {}
            if (loading.value!=true) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(stringResource(R.string.authorization), style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.padding(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_group_5),
                        contentDescription = "null" // decorative element
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(stringResource(R.string.when_sms_did_not_come))
                        val horizontalGradient = Brush.horizontalGradient(
                            colors = listOf(
                                buttonGradientStart,
                                buttonGradientMiddle,
                                buttonGradientEnd,
                            ),
                            0f,
                            250f
                        )
                        Spacer(modifier = Modifier.padding(24.dp))
                        Text(
                            text = stringResource(R.string.send_new_code),
                            style = typography.body2.copy(color = Color.White),
                            modifier = Modifier
//                        .padding(12.dp)
                                .clickable(onClick = {})
                                .clip(RoundedCornerShape(4.dp))
                                .background(brush = horizontalGradient)
                                .padding(12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    var telefon by remember { mutableStateOf("") }
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(4.dp))
                    ) {
                        MyOutlinedTextField(value = telefon,
                            onValueChange = { telefon = it },
//                            visualTransformation = NumberTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(4.dp))
                                .navigationBarsWithImePadding(),
                            trailingIcon = {
                                Text(
                                    "продолжить", color = Color.White, modifier = Modifier
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(
                                                    itemsColor3,
                                                    MaterialTheme.colors.primary,
                                                    itemsColor1
                                                )
                                            )
                                        )
                                        .height(TextFieldDefaults.MinHeight - 2.dp)
                                        .padding(horizontal = 16.dp, vertical = 16.dp)
                                        .clickable {
                                            vm.getToken(telefon)
//                                            onNumberSendClicked(telefon)
                                        }
                                )
                            })

                    }
                    Spacer(modifier = Modifier.padding(156.dp))
                    Text("fd")
                }
            } else {
                Log.d("Nurs", "loadind")
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
                            vm.showError.postValue(Pair(false,""))
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
fun DefaultPreview() {
    HelloComposeTheme {
        authScreen(""){}
    }
}


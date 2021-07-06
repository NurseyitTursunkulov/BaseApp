package com.example.hellocompose.ui.authScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.hellocompose.ui.theme.*
import com.example.hellocompose.ui.util.*
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

@ExperimentalAnimatedInsets
@Composable
fun authScreen(
    loading :Boolean,
    showError: Pair<Boolean, String>,
    sendNewSmsCodeButtonEnabled: Boolean,
    disableSendNewCodeView:()->Unit,
    enableSendNewCodeView:()->Unit,
    sendNewSmsCode:()->Unit,
    getToken:(smsCode:String)->Unit,
    detachErrorSnackbar:()->Unit
) {

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Box() {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {}
            if (loading != true) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    headerView()
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val (button, text) = createRefs()
                        val context = LocalContext.current
                        val scope = rememberCoroutineScope()
                        val START_COUNT_TIME = intPreferencesKey("example_counter")
                        if (sendNewSmsCodeButtonEnabled) {
                            var textCounting: String by remember {
                                mutableStateOf("")
                            }
                            LaunchedEffect(Unit) {
                                countRemainedSeconds(context, START_COUNT_TIME, updateTextCount = {
                                    textCounting = it
                                },
                                    countingFinished = {
                                        enableSendNewCodeView()
                                    })
                            }
                            countTimerWithDisabledButton(textCounting)
                        } else {
                            requestNewSmsText(modifier = Modifier.constrainAs(text) {
                                start.linkTo(parent.start, margin = 0.dp)
                                linkTo(
                                    top = parent.top,
                                    bottom = parent.bottom,
                                )
                            })

                            requestNewSmsButton(Modifier.constrainAs(button) {
                                end.linkTo(parent.end, margin = 0.dp)
                            }) {
                                scope.launch {
                                    context.dataStore.edit { settings ->
                                        settings[START_COUNT_TIME] =
                                            TimeUnit.MILLISECONDS
                                                .toSeconds(Date().time)
                                                .toInt()
                                    }
                                }
                                disableSendNewCodeView()
                                sendNewSmsCode()
                            }
                        }
                    }
                    sendSMSCodeView { smsCode ->
                        getToken(smsCode)
                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.Center)
                )
            }
            if (showError.first) {
                showErrorSnackbar(
                    Modifier.align(Alignment.BottomCenter), showError.second
                ) {
                    detachErrorSnackbar()
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
//        authScreen("") {}
    }
}


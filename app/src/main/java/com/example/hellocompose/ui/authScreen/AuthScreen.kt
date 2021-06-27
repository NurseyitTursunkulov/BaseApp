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
    authScreenPresenter: AuthScreenPresenter
) {

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Box() {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {}
            if (authScreenPresenter.loading != true) {
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
                        if (authScreenPresenter.sendNewSmsCodeButtonEnabled) {
                            var textCounting: String by remember {
                                mutableStateOf("")
                            }
                            scope.launch {
                                countRemainedSeconds(context, START_COUNT_TIME, updateTextCount = {
                                    textCounting = it
                                },
                                    countingFinished = authScreenPresenter.countingFinished
                                )
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
                                authScreenPresenter.enableNewSmsCodeButton()
                                authScreenPresenter.sendNewSmsCode()
                            }
                        }
                    }
                    sendSMSCodeView(onSendButtonClick = authScreenPresenter.onSendButtonClick)
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.Center)
                )
            }
            if (authScreenPresenter.showError.first) {
                showErrorSnackbar(
                    Modifier.align(Alignment.BottomCenter), authScreenPresenter.showError.second,
                    onOkClick = authScreenPresenter.errorSnackBarOnOkClick
                )
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


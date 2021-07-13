package com.example.hellocompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.hellocompose.ui.authScreen.authScreen
import com.example.hellocompose.ui.changeStatusBarColor
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

@ExperimentalComposeUiApi
class MainActivity3 :
    ComponentActivity() {
    @ExperimentalAnimatedInsets
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            // A surface container using the 'background' color from the theme
            ProvideWindowInsets {
                HelloComposeTheme {
                    changeStatusBarColor()
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.navigationBarsWithImePadding()
                    ) {
                        val sendNewSmsCodeButtonEnabled = remember { mutableStateOf(false) }
                        val loading = remember { mutableStateOf(false) }

                        authScreen(
                            loading =loading.value!!,
                            errorState = Pair(false, ""),
                            sendNewSmsCodeButtonEnabled = sendNewSmsCodeButtonEnabled.value,
                            disableSendNewCodeView = {
                                sendNewSmsCodeButtonEnabled.value = true
                            },
                            enableSendNewCodeView = {
                                sendNewSmsCodeButtonEnabled.value = true
                            },
                            sendNewSmsCode = {
                                sendNewSmsCodeButtonEnabled.value = true
                                loading.value = true
                                             Log.d("Nurs","sendNewSmsCode")
                            },
                            getToken = {
//                                sendNewSmsCodeButtonEnabled.value = true
                                Log.d("Nurs","getToken")
//                                loading.value = true
                            },
                            detachErrorSnackbar = {

                            },
                            getLastSavedTime = {
                                10
                            },
                            showError = {}
                        )
                    }

                }
            }
//            DefaultPreview2()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
        Greeting("Android")
}
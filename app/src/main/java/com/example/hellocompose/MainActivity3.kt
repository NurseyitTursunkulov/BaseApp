package com.example.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import com.example.hellocompose.ui.authScreen.authScreen
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.google.accompanist.insets.ExperimentalAnimatedInsets

class MainActivity3 :
    ComponentActivity() {
    @ExperimentalAnimatedInsets
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                // A surface container using the 'background' color from the theme
            HelloComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val sendNewSmsCodeButtonEnabled = mutableStateOf(false)
                    authScreen(
                        loading = false,
                        showError = Pair(false, ""),
                        sendNewSmsCodeButtonEnabled = sendNewSmsCodeButtonEnabled.value,
                        disableSendNewCodeView = {
                            sendNewSmsCodeButtonEnabled.value = true
                        },
                        enableSendNewCodeView = {
                            sendNewSmsCodeButtonEnabled.value = false
                        },
                        sendNewSmsCode = { },
                        getToken = { },
                        detachErrorSnackbar = {

                        },
                        getLastSavedTime = {
                            10
                        }
                    )
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
package com.example.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.hellocompose.domain.NavigationState
import com.example.hellocompose.ui.*
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val mainViewModel: MainViewModel by viewModel()

    @OptIn(ExperimentalAnimatedInsets::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            changeStatusBarColor()
            HelloComposeTheme {
                ProvideWindowInsets (windowInsetsAnimationsEnabled = true) {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        Greeting(mainViewModel)
                    }
                }
            }
        }
    }
}

@ExperimentalAnimatedInsets
@Composable
fun Greeting(exampleViewModel: MainViewModel = getViewModel()) {
    var entryPointState = exampleViewModel.entryPointLiveData.observeAsState()
    when (entryPointState.value?.getContentIfNotHandled()) {
        is NavigationState.NavigateToMainScreen -> {
            MainScreen(exampleViewModel)
        }
        is NavigationState.NavigateToLoginScreen -> {
            authScreen(
                onNumberSendClicked = {}
            )
        }
        is NavigationState.ShowLoading -> {
            splashScreen()
        }
        is NavigationState.ShowError -> {
            Text("ShowError")
        }
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HelloComposeTheme {
        splashScreen()
    }
}
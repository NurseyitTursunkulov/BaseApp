package com.example.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.hellocompose.domain.NavigationState
import com.example.hellocompose.ui.*
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
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
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        val state = mainViewModel.navigationLiveData.observeAsState()
                        state.value?.getContentIfNotHandled()?.let {
                            MainComposable(
                                navigationState = it,
                                setDecorFitsSystemWindows = {
                                    WindowCompat.setDecorFitsSystemWindows(
                                        window,
                                        false
                                    )
                                },
                                removeDecorFitsSystemWindows = {
                                    WindowCompat.setDecorFitsSystemWindows(
                                        window,
                                        true
                                    )
                                },
                                vm = mainViewModel
                            )

                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimatedInsets
@Composable
fun MainComposable(
    navigationState: NavigationState,
    setDecorFitsSystemWindows: () -> Unit = {},
    removeDecorFitsSystemWindows: () -> Unit = {},
    vm :MainViewModel
//    loading: Boolean,
//    showError: String,
//    onLoginClicl: (
//        name: String,
//        surname: String,
//        phone: String,
//        email: String,
//        dateOfBirth: String
//    ) -> Unit,
//    exampleViewModel: MainViewModel = getViewModel()
) {
    when (navigationState) {
        is NavigationState.NavigateToMainScreen -> {
            MainScreen(removeDecorFitsSystemWindows, vm)
        }
        is NavigationState.NavigateToLoginScreen -> {
            loginScreen( vm,setDecorFitsSystemWindows )
        }
        is NavigationState.ShowLoading -> {
            splashScreen()
        }
        is NavigationState.ShowError -> {
            Text("ShowError")
        }
        is NavigationState.NavigateToVerifyBySmsScreen -> {
            authScreen(vm = vm, onNumberSendClicked = {
            })
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
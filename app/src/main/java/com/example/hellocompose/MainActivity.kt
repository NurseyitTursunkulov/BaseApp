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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hellocompose.domain.NavigationState
import com.example.hellocompose.ui.*
import com.example.hellocompose.ui.authScreen.authScreen
import com.example.hellocompose.ui.loginScreen.loginScreen
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
                        val navController = rememberNavController()
                        val loading: Boolean by mainViewModel.showLoading.observeAsState(false)
                        val showError: Pair<Boolean, String> by mainViewModel.showError.observeAsState(
                            Pair(false, "")
                        )
                        val sendNewSmsCodeButtonEnabled: Boolean by mainViewModel.sendNewCodeEnabled.observeAsState(
                            true
                        )
                        NavHost(navController = navController, startDestination = "profile") {
                            composable("profile") { splashScreen() }
                            composable("NavigateToMainScreen") {
                                MainScreen(removeDecorFitsSystemWindows = {
                                    WindowCompat.setDecorFitsSystemWindows(
                                        window,
                                        true
                                    )
                                }, loading = loading,
                                updateSettings = {

                                })

                            }
                            composable("NavigateToLoginScreen") {
                                loginScreen(
                                    loading,
                                    showError,
                                    setDecorFitsSystemWindows = {
                                        WindowCompat.setDecorFitsSystemWindows(
                                            window,
                                            false
                                        )
                                    },
                                    closeErrorView = {
                                        mainViewModel.showError.postValue(
                                            Pair(
                                                false,
                                                ""
                                            )
                                        )
                                    },
                                    onLoginClicl = {name,
                                                    surName,
                                                    phoneNumber,
                                                    email,
                                                    birthDate->
                                        mainViewModel.login(
                                            name,
                                            surName,
                                            phoneNumber,
                                            email,
                                            birthDate
                                        )
                                    }
                                )

                            }
                            composable("ShowLoading") { splashScreen() }
                            composable("ShowError") { Text("ShowError") }
                            composable("NavigateToVerifyBySmsScreen") {
                                authScreen(
                                    loading,
                                    showError,
                                    sendNewSmsCodeButtonEnabled,
                                    disableSendNewCodeView = {
                                        mainViewModel.sendNewCodeEnabled.postValue(
                                            false
                                        )
                                    },
                                    enableSendNewCodeView = {
                                        mainViewModel.sendNewCodeEnabled.postValue(
                                            true
                                        )
                                    },
                                    sendNewSmsCode = { mainViewModel.sendNewSmsCode() },
                                    getToken = { smsCode -> mainViewModel.getToken(smsCode) },
                                    detachErrorSnackbar = {
                                        mainViewModel.showError.postValue(
                                            Pair(
                                                false,
                                                ""
                                            )
                                        )
                                    }

                                )
                            }
                        }

                        state.value?.getContentIfNotHandled()?.let {
                            when (it) {
                                is NavigationState.NavigateToMainScreen -> {
                                    navController.navigate("NavigateToMainScreen")
                                }
                                is NavigationState.NavigateToLoginScreen -> {
                                    navController.navigate("NavigateToLoginScreen") {
                                        launchSingleTop = true
                                        popUpTo("profile") { inclusive = true }
                                    }
                                }
                                is NavigationState.ShowLoading -> {
                                    navController.navigate("ShowLoading")
                                }
                                is NavigationState.ShowError -> {
                                    navController.navigate("ShowError")
                                }
                                is NavigationState.NavigateToVerifyBySmsScreen -> {
                                    navController.navigate("NavigateToVerifyBySmsScreen"){
                                        launchSingleTop = true
                                        popUpTo("profile") { inclusive = true }
                                    }
                                }
                            }
                        }
                    }
                }
            }
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
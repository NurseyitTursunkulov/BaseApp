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
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.hellocompose.domain.NavigationState
import com.example.hellocompose.ui.*
import com.example.hellocompose.ui.authScreen.AuthScreenPresenterImpl
import com.example.hellocompose.ui.authScreen.authScreen
import com.example.hellocompose.ui.loginScreen.LoginScreenPresenterImpl
import com.example.hellocompose.ui.loginScreen.loginScreen
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.example.hellocompose.ui.util.navigationObserver
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
                        val navController = rememberNavController()
                        val loading: Boolean by mainViewModel.showLoading.observeAsState(false)
                        val showError: Pair<Boolean, String> by mainViewModel.showError.observeAsState(
                            Pair(false, "")
                        )
                        val sendNewSmsCodeButtonEnabled: Boolean by mainViewModel.sendNewCodeEnabled.observeAsState(
                            true
                        )
                        val graph = createGraph(
                                navController,
                                loading,
                                showError,
                                sendNewSmsCodeButtonEnabled
                            )
                        NavHost(navController = navController, graph = graph)

                        val state = mainViewModel.navigationLiveData.observeAsState()
                        state.value?.getContentIfNotHandled()?.let {
                            navigationObserver(
                                graph = graph,
                                navController = navController,
                                navigationState = it,
                            )

                        }
                    }
                }
            }
        }
    }

    @ExperimentalAnimatedInsets
    @Composable
     fun createGraph(
        navController: NavHostController,
        loading: Boolean,
        showError: Pair<Boolean, String>,
        sendNewSmsCodeButtonEnabled: Boolean
    ) = navController.createGraph(startDestination = "profile", builder = {
        composable("profile") { splashScreen() }
        composable("NavigateToMainScreen") {
            MainScreen(removeDecorFitsSystemWindows = {
                WindowCompat.setDecorFitsSystemWindows(
                    window,
                    true
                )
            }, vm = mainViewModel)

        }
        composable("NavigateToLoginScreen") {
            loginScreen(
                setDecorFitsSystemWindows = {
                    WindowCompat.setDecorFitsSystemWindows(
                        window,
                        false
                    )
                },
                loginScreenPresenter = LoginScreenPresenterImpl(
                    loading, showError, mainViewModel
                )
            )

        }
        composable("ShowLoading") { splashScreen() }
        composable("ShowError") { Text("ShowError") }
        composable("NavigateToVerifyBySmsScreen") {
            authScreen(
                AuthScreenPresenterImpl(
                    loading,
                    showError,
                    sendNewSmsCodeButtonEnabled,
                    mainViewModel
                )
            )
        }
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HelloComposeTheme {
        splashScreen()
    }
}
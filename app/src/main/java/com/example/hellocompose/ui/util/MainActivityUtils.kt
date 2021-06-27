package com.example.hellocompose.ui.util

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.example.hellocompose.domain.NavigationState
import com.example.hellocompose.ui.MainScreen
import com.example.hellocompose.ui.authScreen.AuthScreenPresenterImpl
import com.example.hellocompose.ui.authScreen.authScreen
import com.example.hellocompose.ui.loginScreen.LoginScreenPresenterImpl
import com.example.hellocompose.ui.loginScreen.loginScreen
import com.example.hellocompose.ui.splashScreen
import com.google.accompanist.insets.ExperimentalAnimatedInsets


fun navigationObserver(
    graph: NavGraph,
    navController: NavHostController,
    navigationState: NavigationState
) {
    navController.graph = graph
    when (navigationState) {
        is NavigationState.NavigateToMainScreen -> {
            navController.navigate("NavigateToMainScreen")
        }
        is NavigationState.NavigateToLoginScreen -> {
            navController.navigate("NavigateToLoginScreen"){
                launchSingleTop = true
                popUpTo("profile"){ inclusive = true }
            }
        }
        is NavigationState.ShowLoading -> {
            navController.navigate("ShowLoading")
        }
        is NavigationState.ShowError -> {
            navController.navigate("ShowError")
        }
        is NavigationState.NavigateToVerifyBySmsScreen -> {
            navController.navigate("NavigateToVerifyBySmsScreen")
        }
    }
}
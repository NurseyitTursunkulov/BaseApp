package com.example.hellocompose.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getViewModel


@Composable
fun MainScreen(
    removeDecorFitsSystemWindows: () -> Unit = {}
) {
    removeDecorFitsSystemWindows()
    val vm = getViewModel<MainViewModel>()
    val items = listOf(
        Screen.Profile,
        Screen.QrCode,
        Screen.Main
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            createBottomNavBar(navController, items)
        }
    ) {

        NavHost(navController, startDestination = Screen.Profile.route) {
            composable(Screen.Profile.route) {
//                exampleViewModel.makeSuspendCall()
                SettingsScreen(vm)
            }
            composable(Screen.QrCode.route) {
                QRCodeScreen(vm)
            }
            composable(Screen.Main.route) {
                Text("ooo")
            }
        }
    }
}
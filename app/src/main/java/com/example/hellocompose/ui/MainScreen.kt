package com.example.hellocompose.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun MainScreen(
    removeDecorFitsSystemWindows: () -> Unit = {},
    vm :MainViewModel
) {
    removeDecorFitsSystemWindows()
    val items = listOf(
        ScreenNavigation.Profile,
        ScreenNavigation.QrCode,
        ScreenNavigation.Main
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            createBottomNavBar(navController, items)
        }
    ) {

        NavHost(navController, startDestination = ScreenNavigation.Profile.route) {
            composable(ScreenNavigation.Profile.route) {
                SettingsScreen(vm)
            }
            composable(ScreenNavigation.QrCode.route) {
                QRCodeScreen(vm)
            }
            composable(ScreenNavigation.Main.route) {
                Text("ooo")
            }
        }
    }
}
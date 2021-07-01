package com.example.hellocompose.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hellocompose.ui.settingsScreen.SettingsScreenPresenter


@Composable
fun MainScreen(
    loading: Boolean, updateSettings: () -> Unit,
    removeDecorFitsSystemWindows: () -> Unit = {}
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
                SettingsScreen(loading, updateSettings)
            }
            composable(ScreenNavigation.QrCode.route) {
                QRCodeScreen()
            }
            composable(ScreenNavigation.Main.route) {
                Text("ooo")
            }
        }
    }
}
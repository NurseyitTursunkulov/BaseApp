package com.example.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import com.example.hellocompose.ui.Screen
import com.example.hellocompose.ui.SettingsScreen
import com.example.hellocompose.ui.changeStatusBarColor
import com.example.hellocompose.ui.createBottomNavBar
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.example.hellocompose.ui.theme.itemsColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            changeStatusBarColor()
            HelloComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
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
                SettingsScreen()
            }
            composable(Screen.QrCode.route) {
                Text("ddh")
            }
            composable(Screen.Main.route) {
                Text("ooo")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HelloComposeTheme {
        Greeting("Android")
    }
}
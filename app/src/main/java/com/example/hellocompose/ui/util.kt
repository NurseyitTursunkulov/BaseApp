package com.example.hellocompose.ui

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import com.example.hellocompose.ui.theme.bottomNavBackColor
import com.example.hellocompose.ui.theme.selectedItemBackColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
 fun changeStatusBarColor( color: Color = Color.Transparent) {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme

        systemUiController.setSystemBarsColor(
            color = color,
            darkIcons = useDarkIcons
        )

        // setStatusBarsColor() and setNavigationBarsColor() also exist
    }
}

@Composable
fun createBottomNavBar(
    navController: NavHostController,
    items: List<Screen>
) {
    BottomNavigation(backgroundColor = bottomNavBackColor) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        items.forEach { screen ->
            val bottomNavigationItemModifier = if (currentRoute == screen.route) {
                Modifier.background(selectedItemBackColor)
            } else {
                Modifier
            }
            BottomNavigationItem(
                modifier = bottomNavigationItemModifier,
                icon = {
                    Icon(screen.imageVector, stringResource(screen.resourceId))
                },
                label = { Text(stringResource(screen.resourceId),fontSize = 12.sp,maxLines = 1) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo = navController.graph.startDestination
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

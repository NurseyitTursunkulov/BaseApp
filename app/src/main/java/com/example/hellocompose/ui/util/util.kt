package com.example.hellocompose.ui

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import com.example.hellocompose.ui.theme.bottomNavBackColor
import com.example.hellocompose.ui.theme.itemsColor
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
//                modifier = bottomNavigationItemModifier,
                icon = {
                    Icon(screen.imageVector, stringResource(screen.resourceId))
                },
                label = { Text(stringResource(screen.resourceId),fontSize = 12.sp,maxLines = 1) },
                selected = currentRoute == screen.route,
                selectedContentColor = itemsColor,
                unselectedContentColor = Color.Black,
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

class NumberTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return numberFilter(text)
    }
}

fun numberFilter(text: AnnotatedString): TransformedText {

    // +XXX XXX XXX XXX
    val trimmed = if (text.text.length >= 12) text.text.substring(0..11) else text.text
    var out = ""
    for (i in trimmed.indices) {
        if (i==0) out += "+"
        out += trimmed[i]
        if (i % 3 == 2 && i != 11) out += " "
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 0) return offset
            if (offset <= 2) return offset +1
            if (offset <= 5) return offset +2
            if (offset <= 8) return offset +3
            if (offset <= 12) return offset +4
            return 16
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <=0) return offset
            if (offset <=3) return offset -1
            if (offset <=7) return offset -2
            if (offset <=11) return offset -3
            if (offset <=15) return offset -4
            return 11
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}
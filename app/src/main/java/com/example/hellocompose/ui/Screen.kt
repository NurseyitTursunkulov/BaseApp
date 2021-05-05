package com.example.hellocompose.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hellocompose.R


sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val imageVector: ImageVector
) {
    object Profile : Screen("profile", R.string.profile, Icons.Outlined.QrCode2)
    object QrCode : Screen("qr-code", R.string.qr_code, Icons.Outlined.PersonPin)
    object Main : Screen("main", R.string.main, Icons.Outlined.LocalFlorist)
}
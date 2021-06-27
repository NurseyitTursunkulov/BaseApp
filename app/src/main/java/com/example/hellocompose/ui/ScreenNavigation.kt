package com.example.hellocompose.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hellocompose.R


sealed class ScreenNavigation(
    val route: String,
    @StringRes val resourceId: Int,
    val imageVector: ImageVector
) {
    object Profile : ScreenNavigation("profile", R.string.profile, Icons.Outlined.QrCode2)
    object QrCode : ScreenNavigation("qr-code", R.string.qr_code, Icons.Outlined.PersonPin)
    object Main : ScreenNavigation("main", R.string.main, Icons.Outlined.LocalFlorist)
}
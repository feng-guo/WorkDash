package com.example.workdash.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    var route: String,
    var title: String,
    var icon: ImageVector
){
    object Home: BottomBarScreen(
        route = "home_screen",
        title = "Home",
        icon = Icons.Default.Home
    )
    object UserInfo: BottomBarScreen(
        route = "userinfo_screen",
        title = "UserInfo",
        icon = Icons.Default.Person
    )
    object Setting: BottomBarScreen(
        route = "settings_screen",
        title = "Settings",
        icon = Icons.Default.Settings
    )

}

package com.example.workdash.routes

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
        route = ScreenRoute.Home.route,
        title = "Home",
        icon = Icons.Default.Home
    )
    object UserInfo: BottomBarScreen(
        route = ScreenRoute.UserInfo.route,
        title = "UserInfo",
        icon = Icons.Default.Person
    )


}

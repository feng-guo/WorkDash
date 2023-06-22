package com.example.workdash.models

sealed class ScreenRoute(val route: String){
    object Login: ScreenRoute(route = "login_screen")
    object UserInfo: ScreenRoute(route = "userinfo_screen")
    object Home: ScreenRoute(route = "home_screen")
    object Settings: ScreenRoute(route = "settings_screen")
}
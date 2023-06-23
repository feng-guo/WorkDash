package com.example.workdash

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.workdash.models.BottomBarScreen
import com.example.workdash.models.ScreenRoute
import com.example.workdash.screen.EmpolyerScreen.CurrentJobPostsEmployerScreen
import com.example.workdash.screen.HomeScreen
import com.example.workdash.screen.LoginScreen
import com.example.workdash.screen.SettingScreen
import com.example.workdash.screen.UserInfo

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(
            route = ScreenRoute.Login.route
        ){
            LoginScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.UserInfo.route
        ){
            UserInfo(navController = navController)
        }
        composable(
            route = ScreenRoute.Settings.route
        ){
            SettingScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.Home.route
        ){
            HomeScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.CurrentJobPostsEmployer.route
        ){
            CurrentJobPostsEmployerScreen(navController = navController)
        }
    }
}
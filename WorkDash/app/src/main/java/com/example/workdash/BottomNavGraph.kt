package com.example.workdash

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.workdash.routes.BottomBarScreen
import com.example.workdash.routes.IS_WORKER_ARG
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.screen.EmployerScreen.AddLocationEmployerScreen
import com.example.workdash.screen.EmployerScreen.AddPostEmployerScreen
import com.example.workdash.screen.EmployerScreen.ChooseLocationEmployerScreen
import com.example.workdash.screen.EmployerScreen.CurrentJobPostsEmployerScreen
import com.example.workdash.screen.EmployerScreen.JobDetailsEmployerScreen
import com.example.workdash.screen.EmployerScreen.SignUpEmployerScreen
import com.example.workdash.screen.HomeScreen
import com.example.workdash.screen.LoginScreen
import com.example.workdash.screen.SettingScreen
import com.example.workdash.screen.UserInfo
import com.example.workdash.screen.WorkerScreen.JobDetailsWorkerScreen
import com.example.workdash.screen.WorkerScreen.UserDetailsWorkerScreen
import com.example.workdash.screen.WorkerScreen.ListOfJobs
import com.example.workdash.screen.WorkerScreen.ListOfJobsApplied
import com.example.workdash.screen.EmployerScreen.WorkerRating

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(
            route = ScreenRoute.Login.route,
            arguments = listOf(navArgument(IS_WORKER_ARG) {
                type = NavType.BoolType
            })
        ){
            Log.d("Args", it.arguments?.getBoolean(IS_WORKER_ARG).toString())
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
        composable(
            route = ScreenRoute.JobDetailsEmployer.route
        ){
            JobDetailsEmployerScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.ChooseLocationEmployer.route
        ){
            ChooseLocationEmployerScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.AddLocationEmployer.route
        ){
            AddLocationEmployerScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.AddPostEmployer.route
        ){
            AddPostEmployerScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.JobDetailsWorker.route
        ) {
            JobDetailsWorkerScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.UserDetailsWorker.route
        ) {
            UserDetailsWorkerScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.ListOfJobs.route
        ) {
            ListOfJobs(navController = navController)
        }
        composable(
            route = ScreenRoute.ListOfJobsApplied.route
        ) {
            ListOfJobsApplied(navController = navController)
        }
        composable(
            route = ScreenRoute.SignUpEmployer.route
        ) {
            SignUpEmployerScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.WorkerRating.route
        ) {
            WorkerRating(navController = navController)
        }
    }
}
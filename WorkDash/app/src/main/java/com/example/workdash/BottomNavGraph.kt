package com.example.workdash

//import com.example.workdash.screen.JobDetailsWorkerScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.workdash.routes.BottomBarScreen
import com.example.workdash.routes.ID_ARG
import com.example.workdash.routes.IS_WORKER_ARG
import com.example.workdash.routes.JOB_ID_ARG
import com.example.workdash.routes.LOCATION_ID_ARG
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.screen.EmployerScreen.AddLocationEmployerScreen
import com.example.workdash.screen.EmployerScreen.AddPostEmployerScreen
import com.example.workdash.screen.EmployerScreen.ChooseLocationEmployerScreen
import com.example.workdash.screen.EmployerScreen.CurrentJobPostsEmployerScreen
import com.example.workdash.screen.EmployerScreen.JobDetailsEmployerScreen
import com.example.workdash.screen.EmployerScreen.SignUpEmployerScreen
import com.example.workdash.screen.HomeScreen
import com.example.workdash.screen.LoginScreen
import com.example.workdash.screen.Rating
import com.example.workdash.screen.Report
import com.example.workdash.screen.UserInfo
import com.example.workdash.screen.WorkerScreen.InProcessWorkerScreen
import com.example.workdash.screen.WorkerScreen.JobDetailsWorkerScreen
import com.example.workdash.screen.WorkerScreen.ListOfJobs
import com.example.workdash.screen.WorkerScreen.ListOfJobsApplied
import com.example.workdash.screen.WorkerScreen.MapOfJobs
import com.example.workdash.screen.WorkerScreen.Quiz
import com.example.workdash.screen.WorkerScreen.UserDetailsWorkerScreen

val IS_WORKER_NAV_ARG = navArgument(IS_WORKER_ARG) {
    type = NavType.BoolType
}
val LOCATION_ID_NAV_ARG = navArgument(LOCATION_ID_ARG) {
    type = NavType.StringType
}
val JOB_ID_NAV_ARG = navArgument(JOB_ID_ARG) {
    type = NavType.StringType
}
val ID_NAV_ARG = navArgument(ID_ARG) {
    type = NavType.StringType
}
@RequiresApi(Build.VERSION_CODES.O)
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
            arguments = listOf(IS_WORKER_NAV_ARG)
        ){
            LoginScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.UserInfo.route
        ){
            UserInfo(navController = navController)
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
            route = ScreenRoute.JobDetailsEmployer.route,
            arguments = listOf(JOB_ID_NAV_ARG, LOCATION_ID_NAV_ARG)
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
            route = ScreenRoute.AddPostEmployer.route,
            arguments = listOf(LOCATION_ID_NAV_ARG)
        ){
            AddPostEmployerScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.JobDetailsWorker.route,
            arguments = listOf(JOB_ID_NAV_ARG, LOCATION_ID_NAV_ARG)
        ) {
            JobDetailsWorkerScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.InProcessWorker.route,
            arguments = listOf(JOB_ID_NAV_ARG, LOCATION_ID_NAV_ARG)
        ) {
            InProcessWorkerScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.UserDetailsWorker.route
        ) {
            UserDetailsWorkerScreen(navController = navController)
            //Payment()
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
            route = ScreenRoute.Rating.route,
            arguments = listOf(ID_NAV_ARG)
        ) {
            Rating(navController = navController)
        }
        composable(
            route = ScreenRoute.MapOfJobs.route,
            arguments = listOf(LOCATION_ID_NAV_ARG)
        ) {
            MapOfJobs(navController = navController)
        }
        composable(
            route = ScreenRoute.Quiz.route
        ) {
            Quiz(navController = navController)
        }
        composable(
            route = ScreenRoute.Report.route,
            arguments = listOf(ID_NAV_ARG)
        ) {
            Report(navController = navController)
        }
    }
}
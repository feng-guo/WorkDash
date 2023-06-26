package com.example.workdash.routes

sealed class ScreenRoute(val route: String){
    object Login: ScreenRoute(route = "login_screen")
    object UserInfo: ScreenRoute(route = "userinfo_screen")
    object Home: ScreenRoute(route = "home_screen")
    object Settings: ScreenRoute(route = "settings_screen")
//Employer page routes
    object CurrentJobPostsEmployer: ScreenRoute(route = "current_job_posts_employer_screen")
    object JobDetailsEmployer: ScreenRoute(route = "job_details_employer_screen")
    object ChooseLocationEmployer: ScreenRoute(route = "choose_location_employer_screen")
    object AddLocationEmployer: ScreenRoute(route = "add_location_employer_screen")
    object AddPostEmployer: ScreenRoute(route = "add_post_employer_screen")

}

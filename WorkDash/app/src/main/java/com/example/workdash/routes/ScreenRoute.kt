package com.example.workdash.routes

const val IS_WORKER_ARG = "isWorker"
sealed class ScreenRoute(val route: String){
    object Login: ScreenRoute(route = "login_screen/{$IS_WORKER_ARG}") {
        fun passIsWorker(isWorker: Boolean): String {
            return "login_screen/$isWorker"
        }
    }
    object UserInfo: ScreenRoute(route = "userinfo_screen")
    object Home: ScreenRoute(route = "home_screen")
    object Settings: ScreenRoute(route = "settings_screen")
//Employer page routes
    object CurrentJobPostsEmployer: ScreenRoute(route = "current_job_posts_employer_screen")
    object JobDetailsEmployer: ScreenRoute(route = "job_details_employer_screen")
    object ChooseLocationEmployer: ScreenRoute(route = "choose_location_employer_screen")
    object AddLocationEmployer: ScreenRoute(route = "add_location_employer_screen")
    object AddPostEmployer: ScreenRoute(route = "add_post_employer_screen")
    object SignUpEmployer: ScreenRoute(route = "sign_up_employer_screen")
// Worker page routes
    object JobDetailsWorker: ScreenRoute(route = "job_details_worker_screen")
    object UserDetailsWorker: ScreenRoute(route = "user_details_worker_screen")
    object ListOfJobs: ScreenRoute(route = "list_of_jobs")
    object ListOfJobsApplied: ScreenRoute(route = "list_of_jobs_applied")

    object WorkerRating: ScreenRoute(route = "worker_rating")


}

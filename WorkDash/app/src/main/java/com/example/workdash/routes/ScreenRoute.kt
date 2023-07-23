package com.example.workdash.routes

const val IS_WORKER_ARG = "isWorker"
const val LOCATION_ID_ARG = "locationId"
const val JOB_ID_ARG = "jobId"
const val JOB_STATE_ARG = "jobState"
const val ID_ARG = "id"
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
    object JobDetailsEmployer: ScreenRoute(route = "job_details_employer_screen/{$JOB_ID_ARG}/{$LOCATION_ID_ARG}") {
        fun passJobIdAndLocationId(jobId: String, locationId: String): String {
            return "job_details_employer_screen/$jobId/$locationId"
        }
    }
    object ChooseLocationEmployer: ScreenRoute(route = "choose_location_employer_screen")
    object AddLocationEmployer: ScreenRoute(route = "add_location_employer_screen")
    object AddPostEmployer: ScreenRoute(route = "add_post_employer_screen/{$LOCATION_ID_ARG}") {
        fun passLocationId(locationId: String): String {
            return "add_post_employer_screen/$locationId"
        }
    }
    object SignUpEmployer: ScreenRoute(route = "sign_up_employer_screen")
// Worker page routes
    object JobDetailsWorker: ScreenRoute(route = "job_details_worker_screen/{$JOB_ID_ARG}/{$LOCATION_ID_ARG}") {
        fun passJobIdAndLocationId(jobId: String, locationId: String): String {
            return "job_details_worker_screen/$jobId/$locationId"
        }
    }
    object InProcessWorker: ScreenRoute(route = "in_process_worker_screen/{$JOB_ID_ARG}/{$LOCATION_ID_ARG}") {
        fun passJobIdAndLocationId(jobId: String, locationId: String): String {
            return "in_process_worker_screen/$jobId/$locationId"
        }
    }
    object UserDetailsWorker: ScreenRoute(route = "user_details_worker_screen")
    object ListOfJobs: ScreenRoute(route = "list_of_jobs")
    object ListOfJobsApplied: ScreenRoute(route = "list_of_jobs_applied")
    object Rating: ScreenRoute(route = "rating/{$ID_ARG}") {
        fun passId(id: String): String {
            return "rating/$id"
        }
    }
    object MapOfJobs: ScreenRoute(route = "map_of_jobs")
    object Quiz: ScreenRoute(route = "quiz")
    object Report: ScreenRoute(route = "report/{$ID_ARG}") {
        fun passId(id: String): String {
            return "report/$id"
        }
    }
}

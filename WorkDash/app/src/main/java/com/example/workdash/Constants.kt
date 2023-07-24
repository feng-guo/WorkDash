package com.example.workdash

object Constants {
    object TableNames {
        const val JOB_TABLE_NAME = "Jobs"
        const val JOB_APPLICATION_TABLE_NAME = "JobApplications"
        const val LOCATION_TABLE_NAME = "Locations"
        const val COORDINATE_TABLE_NAME = "Coordinates"
        const val ID_TABLE_NAME = "IDs"
        const val MATCHED_JOB_NAME = "matchedJob"
        const val API_KEY_NAME = "ApiKeys"
        const val REPORT_TABLE_NAME = "Reports"
        const val RATING_TABLE_NAME = "Ratings"
        const val USER_PROFILE = "userProfile"
    }

    object UserProfile {
        const val UID = "uid"
        const val IS_WORKER = "isWorker"
        const val NAME= "name"
        const val EMAIL= "EMAIL"
        const val PHONE= "phone"
        const val ADDRESS= "address"
        const val SALARY= "salary"
        const val IS_VERIFIED= "isVerified"
        const val WORK_DAYS= "workDays"
        const val START_TIME = "startTime"
        const val END_TIME= "endTime"
        const val SELECTED_ID= "selectedId"
    }
    object IdNames {
        const val JOB_ID_NAME = "jobId"
        const val JOB_APPLICATION_ID_NAME = "jobApplicationId"
        const val LOCATION_ID_NAME = "locationId"
        const val USER_ID_NAME = "userId"
    }
    object MatchedJob {
        const val JOB_ID = "jobId"
        const val EMPLOYEE_ID = "employeeId"
        const val CHECK_IN_STATE = "checkInState"
        const val CHECK_OUT_STATE = "checkOutState"
        const val CHECK_IN_TIME = "checkInTime"
        const val CHECK_OUT_TIME = "checkOutTime"
        const val JOB_EMPLOYEE = "jobId_employeeId"
    }

}
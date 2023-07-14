package com.example.workdash

object Constants {
    object TableNames {
        const val JOB_TABLE_NAME = "Jobs"
        const val JOB_APPLICATION_TABLE_NAME = "JobApplications"
        const val LOCATION_TABLE_NAME = "Locations"
        const val ID_TABLE_NAME = "IDs"
        const val MATCHED_JOB_NAME = "matchedJob"
    }

    object IdNames {
        const val JOB_ID_NAME = "jobId"
        const val JOB_APPLICATION_ID_NAME = "jobApplicationId"
        const val LOCATION_ID_NAME = "locationId"
    }
    object MatchedJob {
        const val JOB_ID = "jobId"
        const val EMPLOYEE_ID = "employeeId"
        const val CHECK_IN_STATE = "checkInState"
        const val CHECK_OUT_STATE = "checkOutState"
        const val CHECK_IN_TIME = "checkInTime"
        const val CHECK_OUT_TIME = "checkOutTime"
    }

}
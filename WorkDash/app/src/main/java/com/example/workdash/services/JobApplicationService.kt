package com.example.workdash.services

import com.example.workdash.Constants
import com.example.workdash.models.JobApplicationModel

object JobApplicationService {
    fun applyToJob(jobId: String) {
        val jobApplicationId = IdGeneratorService.generateJobApplicationId()
        val employeeId = UserService.getCurrentUserId()
        val jobApplicationModel = JobApplicationModel(jobApplicationId, jobId, employeeId, "Pending")
        saveJobApplication(jobApplicationModel)
    }

    private fun saveJobApplication(jobApplication: JobApplicationModel) {
        DatabaseService.writeToDbTable(Constants.TableNames.JOB_APPLICATION_TABLE_NAME, jobApplication.jobId, jobApplication)
    }

    fun acceptApplication(jobApplication: JobApplicationModel) {
        JobService.incrementTotalFilledPositions(jobApplication.jobId)
        jobApplication.applicationStatus = "Accepted"
        saveJobApplication(jobApplication)
    }

    fun rejectApplication(jobApplication: JobApplicationModel) {
        jobApplication.applicationStatus = "Rejected"
        saveJobApplication(jobApplication)
    }

}
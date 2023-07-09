package com.example.workdash.services

import com.example.workdash.Constants
import com.example.workdash.models.JobModel

object JobService {
    fun createJob(locationId: String, jobName: String, schedule: String, payPerHour: Long, certificationsRequired: String, totalPositionsRequired: Long ) {
        val jobId = IdGeneratorService.generateJobId()
        //TODO change pending to a constant
        val jobModel = JobModel(locationId, jobId, jobName, "Pending", schedule, payPerHour, certificationsRequired, totalPositionsRequired, 0)
        saveJob(jobModel)
    }

    private fun saveJob(job: JobModel) {
        DatabaseService.writeToDbTable(Constants.TableNames.JOB_TABLE_NAME, job.jobId, job)
    }

    fun getJobFromId(jobId: String): JobModel {
        val jobModel = JobModel()
        return DatabaseService.readSingleObjectFromDbTableWithId(Constants.TableNames.JOB_TABLE_NAME, jobId, jobModel)
    }

    fun incrementTotalFilledPositions(jobId: String) {
        val jobModel = getJobFromId(jobId)
        jobModel.totalPositionsFilled++
        saveJob(jobModel)
    }
}
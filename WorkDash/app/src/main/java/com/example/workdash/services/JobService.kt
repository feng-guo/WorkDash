package com.example.workdash.services

import com.example.workdash.Constants.IdNames.JOB_ID_NAME
import com.example.workdash.Constants.TableNames.JOB_TABLE_NAME
import com.example.workdash.models.JobModel

object JobService {
    fun createJob(locationId: String, jobName: String, schedule: String, payPerHour: Long, certificationsRequired: String, totalPositionsRequired: Long ) {
        var jobId: String
        val lmd = { retrievedId : Long ->
            jobId = retrievedId.toString()
            val jobModel = JobModel(locationId, jobId, jobName,  schedule, payPerHour, certificationsRequired, totalPositionsRequired)
            saveJob(jobModel)
        }
        IdGeneratorService.generateJobId(lmd)
    }

    private fun saveJob(job: JobModel) {
        DatabaseService.writeToDbTable(JOB_TABLE_NAME, job.jobId, job)
    }

    fun getJobFromId(jobId: String, callback: (jobModel: JobModel?) -> Unit) {
        val jobModel = JobModel()
        DatabaseService.readSingleObjectFromDbTableWithId(JOB_TABLE_NAME, JOB_ID_NAME, jobId, jobModel, callback)
    }

    fun incrementTotalFilledPositions(jobId: String) {
        val lmd = { job: JobModel? ->
            job?.totalPositionsFilled = job?.totalPositionsFilled!! + 1
            saveJob(job)
        }
        getJobFromId(jobId, lmd)
    }
}